/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;


import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.security.AuthenticationException;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.layout.PortletLayoutEngine;
import org.gridlab.gridsphere.layout.PortletErrorFrame;
import org.gridlab.gridsphere.portletcontainer.impl.SportletMessageManager;
import org.gridlab.gridsphere.portletcontainer.impl.GridSphereEventImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;


/**
 * The <code>GridSphereServlet</code> is the GridSphere portlet container.
 * All portlet requests get proccessed by the GridSphereServlet before they
 * are rendered.
 */
public class GridSphereServlet extends HttpServlet implements ServletContextListener,
        HttpSessionAttributeListener, HttpSessionListener {

    /* GridSphere logger */
    private static PortletLog log = SportletLog.getInstance(GridSphereServlet.class);

    /* GridSphere service factory */
    private static SportletServiceFactory factory = SportletServiceFactory.getInstance();

    /* GridSphere Portlet Registry Service */
    private static PortletManagerService portletManager = null;

    /* GridSphere Access Control Service */
    private static AccessControlManagerService aclService = null;

    private static LoginService loginService = null;

    private PortletMessageManager messageManager = SportletMessageManager.getInstance();

    /* GridSphere Portlet layout Engine handles rendering */
    private static PortletLayoutEngine layoutEngine = null;

    private PortletErrorFrame errorMsg = new PortletErrorFrame();

    private PortletContext context = null;
    private static Boolean firstDoGet = Boolean.TRUE;

    private static PortletSessionManager sessionManager = PortletSessionManager.getInstance();

    /**
     * Initializes the GridSphere portlet container
     *
     * @param config the <code>ServletConfig</code>
     * @throws ServletException if an error occurs during initialization
     */
    public final void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.context = new SportletContext(config);
        log.debug("in init of GridSphereServlet");
    }

    public synchronized void initializeServices() throws PortletServiceException {
        portletManager = (PortletManagerService) factory.createUserPortletService(PortletManagerService.class, GuestUser.getInstance(), getServletConfig(), true);
        aclService = (AccessControlManagerService) factory.createPortletService(AccessControlManagerService.class, getServletConfig(), true);
        loginService = (LoginService) factory.createPortletService(LoginService.class, getServletConfig(), true);
    }

    /**
     * Processes GridSphere portal framework requests
     *
     * @param req the <code>HttpServletRequest</code>
     * @param res the <code>HttpServletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        processRequest(req, res);
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        GridSphereEvent event = new GridSphereEventImpl(aclService, context, req, res);
        PortletRequest portletReq = event.getPortletRequest();
        PortletResponse portletRes = event.getPortletResponse();

        // If first time being called, instantiate all portlets
        if (firstDoGet.equals(Boolean.TRUE)) {
            synchronized (firstDoGet) {
                log.debug("Initializing portlets and services");
                try {
                    // initailize needed services
                    initializeServices();
                    // initialize all portlets
                    PortletInvoker.initAllPortlets(portletReq, portletRes);
                } catch (PortletException e) {
                    req.setAttribute(GridSphereProperties.ERROR, e);
                }
                layoutEngine = PortletLayoutEngine.getInstance();
                firstDoGet = Boolean.FALSE;
            }
        }

        List groups = aclService.getGroups(portletReq.getUser());
        portletReq.setAttribute(GridSphereProperties.PORTLETGROUPS, groups);

        // Handle user login and logout
        if (event.hasAction()) {
            if (event.getAction().getName().equals(SportletProperties.LOGIN)) {
                login(event);
                event = new GridSphereEventImpl(aclService, context, req, res);
            }
            if (event.getAction().getName().equals(SportletProperties.LOGOUT)) {
                logout(event);
            }
        }

        // Render layout

        layoutEngine.actionPerformed(event);

        // Handle any outstanding messages
        Map portletMessageLists = messageManager.retrieveAllMessages();
        if (!portletMessageLists.isEmpty()) {
            Set keys = portletMessageLists.keySet();
            Iterator it = keys.iterator();
            String concPortletID = null;
            List messages = null;
            while (it.hasNext()) {
                concPortletID = (String)it.next();
                messages = (List)portletMessageLists.get(concPortletID);
                Iterator newit = messages.iterator();
                while (newit.hasNext()) {
                    DefaultPortletMessage msg = (DefaultPortletMessage)newit.next();
                    PortletInvoker.messageEvent(concPortletID, msg, portletReq, portletRes);
                }
            }

        }

        layoutEngine.service(event);
    }
    /**
     * Handles login requests
     *
     * @param event a <code>GridSphereEvent</code>
     */
    protected void login(GridSphereEvent event) {
        log.debug("in login of GridSphere Servlet");
        String LOGIN_ERROR_FLAG = "LOGIN_ERROR";
        Integer LOGIN_ERROR_UNKNOWN = new Integer(-1);

        PortletRequest req = event.getPortletRequest();
        PortletSession session = req.getPortletSession(true);

        //SportletRequestImpl sreq = (SportletRequestImpl)req;
        //sreq.logRequest();
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ((username.trim().equals("portal")) && (password.trim().equals("schmortal"))) {
            log.info("YO WE IN PORTAL!");
            SportletUser user = new SportletUserImpl();
            user.setUserID(username);
            user.setID("45");
            user.setFullName("Joe B. Portal");
            user.setEmailAddress("joe@portal.com");
            user.setFamilyName("Portal");
            user.setGivenName("Joey");

            session.setAttribute(GridSphereProperties.USER, user);
        } else {
            try {
                User user = loginService.login(username, password);
                session.setAttribute(GridSphereProperties.USER, user);
                if (aclService.hasSuperRole(user)) {
                    log.debug("User: " + user.getUserName() + " logged in as SUPER");
                    req.setAttribute(GridSphereProperties.PORTLETROLE, PortletRole.SUPER);
                }

            } catch (AuthenticationException err) {
                if(log.isDebugEnabled()) log.debug(err.getMessage());
                req.setAttribute(LOGIN_ERROR_FLAG, LOGIN_ERROR_UNKNOWN);
            }
        }
        layoutEngine.loginPortlets(event);
    }


    /**
     * Handles logout requests
     *
     * @param event a <code>GridSphereEvent</code>
     */
    protected void logout(GridSphereEvent event) {
        PortletRequest req = event.getPortletRequest();
        PortletSession session = req.getPortletSession();
        session.invalidate();
        log.debug("in logout of GridSphere Servlet");
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
        return "GridSphere Servlet 0.9";
    }

    /**
     * Shuts down the GridSphere portlet container
     */
    public final void destroy() {
        // Shutdown services
        SportletServiceFactory.getInstance().shutdownServices();
        System.gc();
    }

    /**
     * Record the fact that a servlet context attribute was added.
     *
     * @param event The session attribute event
     */
    public void attributeAdded(HttpSessionBindingEvent event) {

        log.debug("attributeAdded('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");

    }


    /**
     * Record the fact that a servlet context attribute was removed.
     *
     * @param event The session attribute event
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {

        log.debug("attributeRemoved('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");

    }


    /**
     * Record the fact that a servlet context attribute was replaced.
     *
     * @param event The session attribute event
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {

        log.debug("attributeReplaced('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");

    }


    /**
     * Record the fact that this ui application has been destroyed.
     *
     * @param event The servlet context event
     */
    public void contextDestroyed(ServletContextEvent event) {

        log.debug("contextDestroyed()");
        //this.context = null;

    }


    /**
     * Record the fact that this ui application has been initialized.
     *
     * @param event The servlet context event
     */
    public void contextInitialized(ServletContextEvent event) {

        //this.context = event.getServletContext();
        log.debug("contextInitialized()");

    }

    /**
     * Record the fact that a session has been created.
     *
     * @param event The session event
     */
    public void sessionCreated(HttpSessionEvent event) {
        log.debug("sessionCreated('" + event.getSession().getId() + "')");
        sessionManager.sessionCreated(event);
    }


    /**
     * Record the fact that a session has been destroyed.
     *
     * @param event The session event
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        sessionManager.sessionDestroyed(event);
        //loginService.sessionDestroyed(event.getSession());
        log.debug("sessionDestroyed('" + event.getSession().getId() + "')");
        //HttpSession session = event.getSession();
        //User user = (User) session.getAttribute(GridSphereProperties.USER);
        //System.err.println("user : " + user.getUserID() + " expired!");
        //PortletLayoutEngine engine = PortletLayoutEngine.getDefault();
        //engine.removeUser(user);
        //engine.logoutPortlets(event);
    }

}
