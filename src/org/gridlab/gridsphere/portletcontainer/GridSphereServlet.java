/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.layout.PortletLayoutEngine;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletContext;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.impl.GridSphereEventImpl;
import org.gridlab.gridsphere.services.registry.PortletRegistryService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;


public class GridSphereServlet extends HttpServlet implements ServletContextListener,
        HttpSessionAttributeListener, HttpSessionListener {

    /* GridSphere logger */
    private static PortletLog log = SportletLog.getInstance(GridSphereServlet.class);

    /* GridSphere service factory */
    private static SportletServiceFactory factory = SportletServiceFactory.getInstance();

    /* GridSphere Portlet Registry Service */
    private static PortletRegistryService registryService = null;

    /* GridSphere User Portlet Manager handles portlet lifecycle */
    private static UserPortletManager userPortletManager = null;

    /* GridSphere Portlet layout Engine handles rendering */
    private static PortletLayoutEngine layoutEngine = null;

    private static List userSessions = new Vector();

    private PortletContext context = null;
    private static boolean firstDoGet = true;

    public final void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.context = new SportletContext(config);

        // Create an instance of the registry service used by the UserPortletManager
        try {
            registryService = (PortletRegistryService) factory.createPortletService(PortletRegistryService.class, config, true);
        } catch (PortletServiceUnavailableException e) {
            log.error("Failed to get registry instance in GridSphere: ", e);
            throw new ServletException("Unable to get portlet instance: " + e.getMessage());
        } catch (PortletServiceNotFoundException e) {
            log.error("Failed to find registry service instance in GridSphere: ", e);
            throw new ServletException("Unable to locate portlet registry service: " + e.getMessage());
        }

        // Get an instance of the UserPortletManager
        userPortletManager = UserPortletManager.getInstance();

        // Get an instance of the PortletLayoutEngine
        layoutEngine = PortletLayoutEngine.getInstance();

        try {
            layoutEngine.init();
        } catch (Exception e) {
            log.error("Unable to initialize Portlet Layout Engine: ", e);
            throw new ServletException("Unable to initialize Portlet Layout Engine: " + e.getMessage());
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        // If first time being called, instantiate all portlets
        if (firstDoGet) {
            userPortletManager.init(req, res);
            firstDoGet = false;
        }

        System.err.println(context.getServletContextName());

        System.err.println(req.getServletPath());

        System.err.println(req.getContextPath());

        GridSphereEvent event = new GridSphereEventImpl(context, req, res);

        // Render layout
        layoutEngine.service(event);
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
        User user = (User)session.getAttribute(GridSphereProperties.USER);
        PortletLayoutEngine engine = PortletLayoutEngine.getInstance();
        engine.removeUser(user);
    }

}
