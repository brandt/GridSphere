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
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.impl.GridSphereEventImpl;
import org.gridlab.gridsphere.services.registry.PortletManagerService;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.LoginService;
import org.gridlab.gridsphere.services.security.AuthenticationException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class GridSphereServlet extends HttpServlet implements ServletContextListener,
        HttpSessionAttributeListener, HttpSessionListener {

    /* GridSphere logger */
    private static PortletLog log = SportletLog.getInstance(GridSphereServlet.class);

    /* GridSphere service factory */
    private static SportletServiceFactory factory = SportletServiceFactory.getInstance();

    /* GridSphere Portlet Registry Service */
    private static PortletManagerService portletManager = null;

    /* GridSphere Access Control Service */
    //private static AccessControlService aclService = null;

    private static LoginService loginService = null;

    /* GridSphere User Portlet Manager handles portlet lifecycle */
    //private static UserPortletManager userPortletManager = null;

    /* GridSphere Portlet layout Engine handles rendering */
    private static PortletLayoutEngine layoutEngine = null;

    private static List userSessions = new Vector();

    private PortletContext context = null;
    private static boolean firstDoGet = true;

    public final void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.context = new SportletContext(config);
        log.debug("in init of GridSphereServlet");
        // Create an instance of the registry service used by the UserPortletManager
        try {
            portletManager = (PortletManagerService) factory.createPortletService(PortletManagerService.class, config, true);
        } catch (PortletServiceUnavailableException e) {
            log.error("Failed to get registry instance in GridSphere: ", e);
            throw new ServletException("Unable to get portlet instance: " + e.getMessage());
        } catch (PortletServiceNotFoundException e) {
            log.error("Failed to find registry service instance in GridSphere: ", e);
            throw new ServletException("Unable to locate portlet registry service: " + e.getMessage());
        }

        // Create an instance of the ACL service
        /*
        try {
            aclService = (AccessControlService) factory.createPortletService(AccessControlService.class, config, true);
        } catch (PortletServiceUnavailableException e) {
            log.error("Failed to get ACL service instance in GridSphere: ", e);
            throw new ServletException("Unable to get ACL service instance: " + e.getMessage());
        } catch (PortletServiceNotFoundException e) {
            log.error("Failed to find ACL service instance in GridSphere: ", e);
            throw new ServletException("Unable to getACL service: " + e.getMessage());
        }
        */

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
        //userPortletManager = UserPortletManager.getInstance();

        // Get an instance of the PortletLayoutEngine
        layoutEngine = PortletLayoutEngine.getInstance();

        try {
            layoutEngine.init();
        } catch (Exception e) {
            log.error("Unable to initialize Portlet PortletLayout Engine: ", e);
            throw new ServletException("Unable to initialize Portlet PortletLayout Engine: " + e.getMessage());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        GridSphereEvent event = new GridSphereEventImpl(context, req, res);

        PortletRequest portletReq = event.getPortletRequest();
        PortletResponse portletRes = event.getPortletResponse();

        // If first time being called, instantiate all portlets
        if (firstDoGet) {
            portletManager.initAllPortletWebApplications(portletReq, portletRes);
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
        // All these are we application names
        System.err.println(context.getServletContextName()); // -- Description

        System.err.println(req.getServletPath());

        System.err.println(req.getContextPath());



        /* This is where we get ACL info and update sportlet request */
        PortletRequest sreq = event.getPortletRequest();
        User user = sreq.getUser();
        List roles = new ArrayList();
        List groups = new ArrayList();
        if (user instanceof GuestUser) {
            roles.add(PortletRole.GUEST);
            groups.add(PortletGroup.BASE);
            sreq.setAttribute(GridSphereProperties.PORTLETROLES, roles);
            sreq.setAttribute(GridSphereProperties.PORTLETGROUPS, groups);
        } else {
            /*
            try {
                groups = aclService.getGroups(user);
            } catch (PortletServiceException e) {
                log.error("Unable to get groups from ACL Manager Service");
            }
            Iterator git = groups.iterator();
            PortletGroup group = null;
            while (git.hasNext()) {
                group = (PortletGroup)git.next();
                roles = aclService.getRolesInGroup(sreq.getUser(), group);
                sreq.setRoles(group, roles);
            }
            */
        }

        // Render layout

        layoutEngine.actionPerformed(event);

        layoutEngine.service(event);
    }

    public void login(GridSphereEvent event) {
        log.debug("in login of GridSphere Servlet");
        String LOGIN_ERROR_FLAG = "LOGIN_ERROR";
        Integer LOGIN_ERROR_UNKNOWN = new Integer(-1);

        PortletRequest req = event.getPortletRequest();
        PortletSession session = req.getPortletSession(true);

        String username = (String) req.getParameter("username");
        String password = (String) req.getParameter("password");

        if ((username.trim().equals("portal")) && (password.trim().equals("schmortal"))) {
            log.info("YO WE IN PORTAL!");
            SportletUser user = new SportletUserImpl();
            user.setUserID(username);
            user.setID("45");
            user.setFullName("Joe B. Portal");
            user.setEmailAddress("joe@portal.com");
            user.setFamilyName("Portal");
            user.setGivenName("Joey");

            System.err.println("Creating new user");
            session.setAttribute(GridSphereProperties.USER, (User) user);
        } else {
            log.info("normally the login method of UserManagerService gets called right now -- when it works");
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

    public void logout(GridSphereEvent event) {
        PortletRequest req = event.getPortletRequest();
        PortletSession session = req.getPortletSession();
        session.invalidate();
        log.debug("in logout of GridSphere Servlet");
        layoutEngine.logoutPortlets(event);
    }

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
        User user = (User) session.getAttribute(GridSphereProperties.USER);
        PortletLayoutEngine engine = PortletLayoutEngine.getInstance();
        engine.removeUser(user);
    }

}
