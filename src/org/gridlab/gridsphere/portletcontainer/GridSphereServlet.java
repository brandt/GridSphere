/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.layout.PortletLayoutEngine;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;
import org.gridlab.gridsphere.services.container.registry.UserPortletManager;
import org.gridlab.gridsphere.portletcontainer.impl.GridSphereEventImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class GridSphereServlet extends HttpServlet {

    /* GridSphere logger */
    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(GridSphereServlet.class);

    /* GridSphere service factory */
    private static SportletServiceFactory factory = SportletServiceFactory.getInstance();

    /* GridSphere Portlet Registry Service */
    private static PortletRegistryService registryService = null;

    /* GridSphere User Portlet Manager handles portlet lifecycle */
    private static UserPortletManager userPortletManager = null;

    /* GridSphere Portlet layout Engine handles rendering */
    private static PortletLayoutEngine layoutEngine = null;

    private ServletContext context = null;
    private static boolean firstDoGet = true;

    public final void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.context = config.getServletContext();

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

        GridSphereEvent event = new GridSphereEventImpl(getServletConfig(), req, res);

        //PortletRequestParser requestParser = new PortletRequestParser(req);
        /*
        if (requestParser.hasPortletAction()) {

            System.err.println("Received ACTION: " + requestParser.toString());

            String portletID = requestParser.getConcretePortletID();

            //  invoke actionPerformed method
            DefaultPortletAction portletAction = requestParser.getPortletAction();
            userPortletManager.actionPerformed(portletID, portletAction, req, res);
            // Get the active portlet instance

        }
        */

        // Render layout
        try {
            //User user = requestParser.getUser();
            layoutEngine.service(event);
        } catch (PortletException e) {
            handleException(res, e);
        }

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

    private final void handleException(HttpServletResponse res, Throwable t) {
        // make sure that the stack trace makes it the log
        log.error("GridSphere: handleException: " + t.getMessage());

        String mimeType = "text/plain";
        try {
            PrintWriter out = res.getWriter();
            out.print(t.getMessage());
            t.printStackTrace(out);
        } catch (IOException e) {
            log.error("Couldn't even write the error to screen!: " + e.getMessage());
        }
    }


}
