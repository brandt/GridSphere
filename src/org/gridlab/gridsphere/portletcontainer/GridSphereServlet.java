/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.layout.PortletLayoutEngine;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.impl.GridSphereEventImpl;
import org.gridlab.gridsphere.portletcontainer.impl.GridSphereEventImpl;
import org.gridlab.gridsphere.portletcontainer.impl.SportletMessageManager;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.security.AuthenticationException;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;

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

    private PortletContext context = null;
    private static boolean firstDoGet = true;

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
        // Create an instance of the registry service used by the UserPortletManager
        try {
            portletManager = (PortletManagerService) factory.createUserPortletService(PortletManagerService.class, GuestUser.getInstance(), config, true);
        } catch (PortletServiceUnavailableException e) {
            log.error("Failed to get registry instance in GridSphere: ", e);
            throw new ServletException("Unable to get portlet instance: " + e.getMessage());
        } catch (PortletServiceNotFoundException e) {
            log.error("Failed to find registry service instance in GridSphere: ", e);
            throw new ServletException("Unable to locate portlet registry service: " + e.getMessage());
        }

        //Create an instance of the ACL service
        try {
            aclService = (AccessControlManagerService) factory.createPortletService(AccessControlManagerService.class, config, true);
        } catch (PortletServiceUnavailableException e) {
            log.error("Failed to get ACL service instance in GridSphere: ", e);
            throw new ServletException("Unable to get ACL service instance: " + e.getMessage());
        } catch (PortletServiceNotFoundException e) {
            log.error("Failed to find ACL service instance in GridSphere: ", e);
            throw new ServletException("Unable to getACL service: " + e.getMessage());
        }

        try {
            loginService = (LoginService) factory.createPortletService(LoginService.class, config, true);
        } catch (PortletServiceUnavailableException e) {
            log.error("Failed to get registry instance in GridSphere: ", e);
            throw new ServletException("Unable to get portlet instance: " + e.getMessage());
        } catch (PortletServiceNotFoundException e) {
            log.error("Failed to find registry service instance in GridSphere: ", e);
            throw new ServletException("Unable to locate portlet registry service: " + e.getMessage());
        }

        // Get an instance of the UserPortletManager
        //userPortletManager = UserPortletManager.getDefault();

        // Get an instance of the PortletLayoutEngine
        layoutEngine = PortletLayoutEngine.getInstance();

        try {
            layoutEngine.init();
        } catch (Exception e) {
            log.error("Unable to initialize Portlet PortletLayout Engine: ", e);
            throw new ServletException("Unable to initialize Portlet PortletLayout Engine: " + e.getMessage());
        }
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

        GridSphereEvent event = new GridSphereEventImpl(context, req, res);

        PortletRequest portletReq = event.getPortletRequest();
        PortletResponse portletRes = event.getPortletResponse();

        // If first time being called, instantiate all portlets
        if (firstDoGet) {
            //PortletInvoker dispatcher = event.getPortletEventDispatcher();
            PortletInvoker.initAllPortlets(portletReq, portletRes);
            firstDoGet = false;
        }

        // Handle user login and logout
        if (event.hasAction()) {
            if (event.getAction().getName().equals(SportletProperties.LOGIN)) {
                login(event);
            }
            if (event.getAction().getName().equals(SportletProperties.LOGOUT)) {
                logout(event);
            }
        }

        /* This is where we get ACL info and update sportlet request */
        User user = portletReq.getUser();
        PortletRole role = PortletRole.GUEST;
        List groups = new ArrayList();
        if (user instanceof GuestUser) {
            groups.add(SportletGroup.BASE);
        } else {
            groups = aclService.getGroups(user);
            Iterator git = groups.iterator();
            PortletGroup group = null;
            while (git.hasNext()) {
                group = (PortletGroup)git.next();
                role = aclService.getRoleInGroup(portletReq.getUser(), group);
            }
        }

        portletReq.setAttribute(GridSphereProperties.PORTLETROLES, role);
        portletReq.setAttribute(GridSphereProperties.PORTLETGROUPS, groups);

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
        //layoutEngine.logoutPortlets(event);
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

        log.info("attributeAdded('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");

    }


    /**
     * Record the fact that a servlet context attribute was removed.
     *
     * @param event The session attribute event
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {

        log.info("attributeRemoved('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");

    }


    /**
     * Record the fact that a servlet context attribute was replaced.
     *
     * @param event The session attribute event
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {

        log.info("attributeReplaced('" + event.getSession().getId() + "', '" +
                event.getName() + "', '" + event.getValue() + "')");

    }


    /**
     * Record the fact that this web application has been destroyed.
     *
     * @param event The servlet context event
     */
    public void contextDestroyed(ServletContextEvent event) {

        log.info("contextDestroyed()");
        //this.context = null;

    }


    /**
     * Record the fact that this web application has been initialized.
     *
     * @param event The servlet context event
     */
    public void contextInitialized(ServletContextEvent event) {

        //this.context = event.getServletContext();
        log.info("contextInitialized()");

    }

    /**
     * Record the fact that a session has been created.
     *
     * @param event The session event
     */
    public void sessionCreated(HttpSessionEvent event) {
        log.info("sessionCreated('" + event.getSession().getId() + "')");
    }


    /**
     * Record the fact that a session has been destroyed.
     *
     * @param event The session event
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        log.info("sessionDestroyed('" + event.getSession().getId() + "')");
        HttpSession session = event.getSession();
        //User user = (User) session.getAttribute(GridSphereProperties.USER);
        //System.err.println("user : " + user.getUserID() + " expired!");
        //PortletLayoutEngine engine = PortletLayoutEngine.getDefault();
        //engine.removeUser(user);
        //engine.logoutPortlets(event);
    }

}
