/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @version $Id: GridSphereServlet.java 4956 2006-07-26 16:15:56Z novotny $
*/
package org.gridsphere.servlets;


import org.gridsphere.layout.PortletLayoutEngine;
import org.gridsphere.layout.PortletPageFactory;
import org.gridsphere.portlet.*;
import org.gridsphere.portlet.impl.*;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceCollection;
import org.gridsphere.portletcontainer.impl.GridSphereEventImpl;
import org.gridsphere.portletcontainer.impl.SportletMessageManager;
import org.gridsphere.portletcontainer.impl.PortletSessionManager;
import org.gridsphere.portletcontainer.impl.PortletServiceDescriptor;
import org.gridsphere.portletcontainer.*;
import org.gridsphere.services.core.registry.PortletManagerService;
import org.gridsphere.services.core.security.group.GroupManagerService;
import org.gridsphere.services.core.security.group.PortletGroup;
import org.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridsphere.services.core.security.role.RoleManagerService;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.user.LoginService;
import org.gridsphere.services.core.user.UserManagerService;
import org.gridsphere.services.core.request.RequestService;
import org.gridsphere.services.core.request.GenericRequest;
import org.gridsphere.services.core.tracker.TrackerService;
import org.gridsphere.services.core.portal.PortalConfigService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.security.Principal;
import java.net.SocketException;


/**
 * The <code>GridSphereServlet</code> is the GridSphere portlet container.
 * All portlet requests get proccessed by the GridSphereServlet before they
 * are rendered.
 */
public class GridSphereServlet extends HttpServlet implements ServletContextListener,
        HttpSessionAttributeListener, HttpSessionListener {

    /* GridSphere logger */
    private PortletLog log = SportletLog.getInstance(GridSphereServlet.class);

    /* GridSphere Portlet Registry Service */
    private PortletManagerService portletManager = null;

    /* GridSphere Access Control Service */
    private RoleManagerService roleService = null;
    private GroupManagerService groupService = null;

    private UserManagerService userManagerService = null;

    private PortalConfigService portalConfigService = null;

    private LoginService loginService = null;

    private TrackerService trackerService = null;

    private PortletMessageManager messageManager = SportletMessageManager.getInstance();

    /* GridSphere Portlet layout Engine handles rendering */
    private PortletLayoutEngine layoutEngine = null;

    /* creates cookie requests */
    private RequestService requestService = null;

    private PortletContext context = null;
    private Boolean firstDoGet = Boolean.TRUE;

    private PortletSessionManager sessionManager = PortletSessionManager.getInstance();

    //private static PortletRegistry registry = PortletRegistry.getInstance();
    private static final String COOKIE_REQUEST = "cookie-request";
    private int COOKIE_EXPIRATION_TIME = 60 * 60 * 24 * 7;  // 1 week (in secs)

    private boolean isTCK = false;

    /**
     * Initializes the GridSphere portlet container
     *
     * @param config the <code>ServletConfig</code>
     * @throws ServletException if an error occurs during initialization
     */
    public final void init(ServletConfig config) throws ServletException {
        log.debug("in init of GridSphereServlet");
        super.init(config);
        this.context = new SportletContext(config);
        String descriptorPath = config.getServletContext().getRealPath("/WEB-INF/GridSphereServices.xml");
        // add core gridsphere services to ServiceFactory
        PortletServiceDescriptor descriptor = null;
        try {
            log.debug("loading from: " + descriptorPath);
            descriptor = new PortletServiceDescriptor(descriptorPath);
            SportletServiceCollection serviceCollection = descriptor.getServiceCollection();
            PortletServiceFactory.addServices("gridsphere", config.getServletContext(), serviceCollection, Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            //log.error("error unmarshalling " + servicesPath + " using " + servicesMappingPath + " : " + e.getMessage());
            throw new PortletServiceException("error unmarshalling " + descriptorPath, e);
        }
    }

    public synchronized void initializeServices() throws PortletServiceException {
        requestService = (RequestService) PortletServiceFactory.createPortletService(RequestService.class, true);

        roleService = (RoleManagerService) PortletServiceFactory.createPortletService(RoleManagerService.class, true);
        groupService = (GroupManagerService) PortletServiceFactory.createPortletService(GroupManagerService.class, true);

        // create root user in default group if necessary
        log.debug("Creating user manager service");
        userManagerService = (UserManagerService) PortletServiceFactory.createPortletService(UserManagerService.class, true);
        portalConfigService = (PortalConfigService)PortletServiceFactory.createPortletService(PortalConfigService.class, true);
        loginService = (LoginService) PortletServiceFactory.createPortletService(LoginService.class, true);
        log.debug("Creating portlet manager service");

        layoutEngine = PortletLayoutEngine.getInstance();

        portletManager = (PortletManagerService) PortletServiceFactory.createPortletService(PortletManagerService.class, true);
        trackerService = (TrackerService) PortletServiceFactory.createPortletService(TrackerService.class, true);

    }

    /**
     * Processes GridSphere portal framework requests
     *
     * @param req the <code>HttpServletRequest</code>
     * @param res the <code>HttpServletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        processRequest(req, res);
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // set content to UTF-8 for il8n and compression if supported
        req.setCharacterEncoding("utf-8");

        long startTime = System.currentTimeMillis();
        GridSphereEvent event = new GridSphereEventImpl(context, req, res);
        PortletRequest portletReq = event.getPortletRequest();

        // If first time being called, instantiate all portlets
        if (firstDoGet.equals(Boolean.TRUE)) {
            firstDoGet = Boolean.FALSE;

            log.debug("Initializing services");
            try {
                // initialize needed services
                initializeServices();
                updateDatabase();
                if (isTCK) req.getSession().setAttribute(SportletProperties.LAYOUT_PAGE, PortletPageFactory.TCK_PAGE);
            } catch (Exception e) {
                log.error("GridSphere initialization failed!", e);
                RequestDispatcher rd = req.getRequestDispatcher("/jsp/errors/init_error.jsp");
                req.setAttribute("error", e);
                rd.forward(req, res);
                return;
            }

            if ((userManagerService.getNumUsers() == 0) || (roleService.getUsersInRole(PortletRole.ADMIN)).size() == 0) {
                req.getSession().setAttribute(SportletProperties.LAYOUT_PAGE, PortletPageFactory.SETUP_PAGE);
            }

        }

        req.setAttribute("context", context);

        // check to see if user has been authorized by means of container managed authorization
        checkWebContainerAuthorization(event);

        setUserAndGroups(event);

        String userName;
        User user = portletReq.getUser();
        if (user == null) {
            userName = "guest";
        } else {
            userName = user.getUserName();
        }

        String trackme = req.getParameter(TrackerService.TRACK_PARAM);
        if (trackme != null) {
            trackerService.trackURL(trackme, req.getHeader("user-agent"), userName);
            String url = req.getParameter(TrackerService.REDIRECT_URL);
            if (url != null) {
                System.err.println("redirect: " + url);
                res.sendRedirect(url);
            }
         }

        checkUserHasCookie(event);

        // Used for TCK tests
        if (isTCK) setTCKUser(portletReq);

        // Handle user login and logout
        if (event.hasAction()) {
            String actionName = event.getAction().getName();
            if (actionName.equals(SportletProperties.LOGIN)) {
                login(event);
                long endTime = System.currentTimeMillis();
                //System.err.println("Page render time = " + (endTime - startTime) + " (ms) request= " + req.getQueryString());
                //return;
            }
            if (actionName.equals(SportletProperties.LOGOUT)) {
                logout(event);
                long endTime = System.currentTimeMillis();
                System.err.println("Page render time = " + (endTime - startTime) + " (ms) request= " + req.getQueryString());
                return;
            }
            if (trackerService.hasTrackingAction(actionName)) {
                trackerService.trackURL(actionName, req.getHeader("user-agent"), userName);
            }
        }

        layoutEngine.actionPerformed(event);


        // is this a file download operation?
        if (isDownload(req)) {
            try {
                downloadFile(req, res);
                return;
            } catch (PortletException e) {
                log.error("Unable to download file!", e);
                req.setAttribute(SportletProperties.FILE_DOWNLOAD_ERROR, e);
            }
        }

        // Handle any outstanding messages
        // This needs work certainly!!!
        Map portletMessageLists = messageManager.retrieveAllMessages();
        if (!portletMessageLists.isEmpty()) {
            Set keys = portletMessageLists.keySet();
            Iterator it = keys.iterator();
            String concPortletID;
            List messages;
            while (it.hasNext()) {
                concPortletID = (String) it.next();
                messages = (List) portletMessageLists.get(concPortletID);
                Iterator newit = messages.iterator();
                while (newit.hasNext()) {
                    PortletMessage msg = (PortletMessage) newit.next();
                    layoutEngine.messageEvent(concPortletID, msg, event);
                }

            }
            messageManager.removeAllMessages();
        }

        setUserAndGroups(event);

        // Used for TCK tests
        if (isTCK) setTCKUser(portletReq);

        layoutEngine.service(event);

        //log.debug("Session stats");
        //userSessionManager.dumpSessions();

        //log.debug("Portlet service factory stats");
        //factory.logStatistics();
        long endTime = System.currentTimeMillis();
        System.err.println("Page render time = " + (endTime - startTime) + " (ms) request= " + req.getQueryString());
    }

    /**
     * Method to set the response headers to perform file downloads to a browser
     *
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @throws org.gridsphere.portlet.PortletException
     */
    public void downloadFile(HttpServletRequest req, HttpServletResponse res) throws PortletException, IOException {
        try {
            String fileName = (String) req.getAttribute(SportletProperties.FILE_DOWNLOAD_NAME);
            String path = (String) req.getAttribute(SportletProperties.FILE_DOWNLOAD_PATH);
            Boolean deleteFile = (Boolean)req.getAttribute(SportletProperties.FILE_DELETE);
            if (deleteFile == null) deleteFile = Boolean.FALSE;
            if (fileName == null) return;
            log.debug("in downloadFile");
            log.debug("filename: " + fileName + " filepath= " + path);
            File file = (File) req.getAttribute(SportletProperties.FILE_DOWNLOAD_BINARY);
            if (file == null) {
                file = new File(path + fileName);
            }
            FileDataSource fds = new FileDataSource(file);
            log.debug("filename: " + fileName + " filepath= " + path + " content type=" + fds.getContentType());
            res.setContentType(fds.getContentType());
            res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            res.setHeader("Content-Length", String.valueOf(file.length()));
            DataHandler handler = new DataHandler(fds);
            handler.writeTo(res.getOutputStream());
            if (deleteFile.booleanValue()) {
                file.delete();
            }
        } catch (FileNotFoundException e) {
            throw new PortletException("Unable to find file!", e);
        } catch (SecurityException e) {
            // this gets thrown if a security policy applies to the file. see java.io.File for details.
            throw new PortletException("A security error occurred!", e);
        } catch (SocketException e) {
            throw new PortletException("A socket error occurred!", e);
        } finally {
            req.removeAttribute(SportletProperties.FILE_DOWNLOAD_NAME);
            req.removeAttribute(SportletProperties.FILE_DOWNLOAD_PATH);
            req.removeAttribute(SportletProperties.FILE_DELETE);
            req.removeAttribute(SportletProperties.FILE_DOWNLOAD_BINARY);
        }
    }

    public boolean isDownload(HttpServletRequest req) {
        return (req.getAttribute(SportletProperties.FILE_DOWNLOAD_NAME) != null);
    }

    public void setTCKUser(PortletRequest req) {
        //String tck = (String)req.getPortletSession(true).getAttribute("tck");
        String[] portletNames = req.getParameterValues("portletName");
        if ((isTCK) || (portletNames != null)) {
            log.info("Setting a TCK user");
            SportletUserImpl u = new SportletUserImpl();
            u.setUserName("tckuser");
            u.setUserID("tckuser");
            u.setID("500");
            List groupList = new ArrayList();

            req.setAttribute(SportletProperties.PORTLET_USER, u);
            req.setAttribute(SportletProperties.PORTLETGROUPS, groupList);
            req.setAttribute(SportletProperties.PORTLET_ROLE, new ArrayList());
            isTCK = true;
        }
    }

    public void setUserAndGroups(GridSphereEvent event) {
        // Retrieve user if there is one
        PortletRequest req = event.getPortletRequest();
        PortletSession session = req.getPortletSession();
        User user = null;
        String uid = (String) session.getAttribute(SportletProperties.PORTLET_USER);
        if (uid != null) {
            user = userManagerService.getUser(uid);
        }
        List groups = new ArrayList();
        List roles = new ArrayList();
        if (user != null) {
            UserPrincipal userPrincipal = new UserPrincipal(user.getUserName());
            event.getPortletRequest().setAttribute(SportletProperties.PORTLET_USER_PRINCIPAL, userPrincipal);
            List mygroups = groupService.getGroups(user);
            Iterator it = mygroups.iterator();
            while (it.hasNext()) {
                PortletGroup g = (PortletGroup) it.next();
                groups.add(g.getName());
            }
            List proles = roleService.getRolesForUser(user);
            it = proles.iterator();
            while (it.hasNext()) {
                roles.add(((PortletRole)it.next()).getName());
            }

        }
        // set user, role and groups in request

        req.setAttribute(SportletProperties.PORTLET_USER, user);
        req.setAttribute(SportletProperties.PORTLETGROUPS, groups);
        req.setAttribute(SportletProperties.PORTLET_ROLE, roles);
    }

    // Dmitry Gavrilov (2005-03-17)
    // FIX for web container authorization
    private void checkWebContainerAuthorization(GridSphereEvent event) {
        PortletSession session = event.getPortletRequest().getPortletSession();
        if (session.getAttribute(SportletProperties.PORTLET_USER) != null) return;
        if(!(event.hasAction() && event.getAction().getName().equals(SportletProperties.LOGOUT))) {
            PortletRequest portletRequest = event.getPortletRequest();
            Principal principal = portletRequest.getUserPrincipal();
            if(principal != null) {
                // fix for OC4J. it must work in Tomcat also
                int indeDelimeter = principal.getName().lastIndexOf('/');
                indeDelimeter = (indeDelimeter > 0) ? (indeDelimeter + 1) : 0;
                String login = principal.getName().substring(indeDelimeter);
                User user = userManagerService.getLoggedInUser(login);
                if (user != null) {
                    setUserSettings(event, user);
                }
            }
        }
    }

    protected void checkUserHasCookie(GridSphereEvent event) {
        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();
        if (user == null) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie c = cookies[i];
                    System.err.println("found a cookie:");
                    System.err.println("name=" + c.getName());
                    System.err.println("value=" + c.getValue());
                    if (c.getName().equals("gsuid")) {

                        String cookieVal = c.getValue();
                        int hashidx = cookieVal.indexOf("#");
                        if (hashidx > 0) {
                            String uid = cookieVal.substring(0, hashidx);

                            System.err.println("uid = " + uid);

                            String reqid = cookieVal.substring(hashidx+1);
                            System.err.println("reqid = " + reqid);

                            GenericRequest genreq = requestService.getRequest(reqid, COOKIE_REQUEST);
                            if (genreq != null) {

                                if (genreq.getUserID().equals(uid)) {
                                    User newuser = userManagerService.getUser(uid);
                                    if (newuser != null) {
                                        System.err.println("in checkUserHasCookie-- seting user settings!!");
                                        setUserSettings(event, newuser);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected void setUserCookie(GridSphereEvent event) {
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();

        User user = req.getUser();
        GenericRequest request = requestService.createRequest(COOKIE_REQUEST);
        Cookie cookie = new Cookie("gsuid", user.getID() + "#" + request.getOid());
        request.setUserID(user.getID());
        long time = Calendar.getInstance().getTime().getTime() + COOKIE_EXPIRATION_TIME * 1000;
        request.setLifetime(new Date(time));
        requestService.saveRequest(request);

        // COOKIE_EXPIRATION_TIME is specified in secs
        cookie.setMaxAge(COOKIE_EXPIRATION_TIME);
        res.addCookie(cookie);
        //System.err.println("adding a  cookie");
    }

    protected void removeUserCookie(GridSphereEvent event) {
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (c.getName().equals("gsuid")) {
                    int idx = c.getValue().indexOf("#");
                    if (idx > 0) {
                        String reqid = c.getValue().substring(idx+1);
                        //System.err.println("reqid= " + reqid);
                        GenericRequest request = requestService.getRequest(reqid, COOKIE_REQUEST);
                        if (request != null) requestService.deleteRequest(request);
                    }
                    c.setMaxAge(0);
                    res.addCookie(c);
                }
            }
        }

    }

    /**
     * Handles login requests
     *
     * @param event a <code>GridSphereEvent</code>
     */
    protected void login(GridSphereEvent event) throws AuthenticationException, AuthorizationException {
        String LOGIN_ERROR_FLAG = "LOGIN_FAILED";
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        try {

            User user = loginService.login(req);

            setUserSettings(event, user);

            String query = event.getAction().getParameter("queryString");

            String remme = req.getParameter("remlogin");
            if (remme != null) {
                setUserCookie(event);
            } else {
                removeUserCookie(event);
            }

            PortletURI uri = res.createURI();
            if (query != null) {
                uri.addParameter("cid", query);
            }
            req.getSession().setAttribute(SportletProperties.LAYOUT_PAGE, PortletPageFactory.USER_PAGE);
            layoutEngine.loginPortlets(event);

            String realuri = uri.toString().substring("http".length());
            Boolean useSecureRedirect = Boolean.valueOf(portalConfigService.getProperty("use.https.redirect"));
            if (useSecureRedirect.booleanValue()) {
                realuri = "https" + realuri;
            } else {
                realuri = "http" + realuri;
            }
            res.sendRedirect(uri.toString());
        } catch (AuthorizationException err) {
            log.debug(err.getMessage());
            req.setAttribute(LOGIN_ERROR_FLAG, err.getMessage());
        } catch (AuthenticationException err) {
            log.debug(err.getMessage());
            req.setAttribute(LOGIN_ERROR_FLAG, err.getMessage());
        } catch (IOException e) {
            log.error("Unable to perform a redirect!", e);
        }
    }

    public void setUserSettings(GridSphereEvent event, User user) {
        PortletRequest req = event.getPortletRequest();
        PortletSession session = req.getPortletSession(true);

        req.setAttribute(SportletProperties.PORTLET_USER, user);
        session.setAttribute(SportletProperties.PORTLET_USER, user.getID());
        if (user.getAttribute(User.LOCALE) != null) {
            session.setAttribute(User.LOCALE, new Locale((String)user.getAttribute(User.LOCALE), "", ""));
        }
        setUserAndGroups(event);
    }

    /**
     * Handles logout requests
     *
     * @param event a <code>GridSphereEvent</code>
     */
    protected void logout(GridSphereEvent event) {
        getServletContext().log("in logout of GridSphere Servlet");
        PortletRequest req = event.getPortletRequest();
        removeUserCookie(event);
        PortletSession session = req.getPortletSession();
        layoutEngine.logoutPortlets(event);
        req.removeAttribute(SportletProperties.PORTLET_USER);
        req.removeAttribute(SportletProperties.PORTLET_USER_PRINCIPAL);
        //System.err.println("in logout of GS, calling invalidate on s=" + session.getId());
        session.invalidate();
        try {
            PortletResponse res = event.getPortletResponse();
            res.sendRedirect(res.createURI().toString());
        } catch (IOException e) {
            log.error("Unable to do a redirect!", e);
        }
    }

    /**
     * @see #doGet
     */
    public final void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doGet(req, res);
    }

    /**
     * Return the servlet info.
     *
     * @return a string with the servlet information.
     */
    public final String getServletInfo() {
        return "GridSphere Servlet";
    }

    /**
     * Shuts down the GridSphere portlet container
     */
    public final void destroy() {
        log.debug("in destroy: Shutting down services");
        //userSessionManager.destroy();
        layoutEngine.destroy();
        // Shutdown services
        PortletServiceFactory.shutdownServices();
        System.gc();
    }

    /**
     * Record the fact that a servlet context attribute was added.
     *
     * @param event The session attribute event
     */
    public void attributeAdded(HttpSessionBindingEvent event) {
        try {
            log.debug("attributeAdded('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");
        } catch (IllegalStateException e) {
            // do nothing
        }
    }


    /**
     * Record the fact that a servlet context attribute was removed.
     *
     * @param event The session attribute event
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {
        try {
            log.debug("attributeRemoved('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");
        } catch (IllegalStateException e) {
            // do nothing
        }

    }


    /**
     * Record the fact that a servlet context attribute was replaced.
     *
     * @param event The session attribute event
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {
        try {
            log.debug("attributeReplaced('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");
        } catch (IllegalStateException e) {
            // do nothing
        }

    }


    /**
     * Record the fact that this ui application has been destroyed.
     *
     * @param event The servlet context event
     */
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext ctx = event.getServletContext();
        log.debug("contextDestroyed()");
        log.debug("contextName: " + ctx.getServletContextName());
        log.debug("context path: " + ctx.getRealPath(""));
    }


    /**
     * Record the fact that this ui application has been initialized.
     *
     * @param event The servlet context event
     */
    public void contextInitialized(ServletContextEvent event) {
        System.err.println("in contextInitialized of GridSphereServlet");
        ServletContext ctx = event.getServletContext();
        log.debug("contextName: " + ctx.getServletContextName());
        log.debug("context path: " + ctx.getRealPath(""));

    }

    /**
     * Record the fact that a session has been created.
     *
     * @param event The session event
     */
    public void sessionCreated(HttpSessionEvent event) {
        System.err.println("sessionCreated('" + event.getSession().getId() + "')");
        sessionManager.sessionCreated(event);
    }


    /**
     * Record the fact that a session has been destroyed.
     *
     * @param event The session event
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        sessionManager.sessionDestroyed(event);
        System.err.println("sessionDestroyed('" + event.getSession().getId() + "')");
    }

    public void updateDatabase() {

    }
}
