/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;
import org.gridlab.gridsphere.services.container.registry.PortletUserRegistryService;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryServiceImpl;
import org.gridlab.gridsphere.event.impl.ActionEventImpl;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.layout.*;


import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class GridSphere extends HttpServlet {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(GridSphere.class);

    private static SportletServiceFactory factory = SportletServiceFactory.getInstance();

    private static PortletRegistryService registryService = null;
    private static PortletUserRegistryService userRegistryService = null;
    private static PortletLayoutService layoutService = null;
    private static PortletLayoutEngine layoutEngine = null;

    private static Collection concretePortlets = new HashSet();

    private static PortletContext portletContext = null;

    private static boolean firstDoGet = true;

    private static Throwable initFailure = null;


    public final void init(ServletConfig config) throws ServletException {
        super.init(config);
        synchronized (this.getClass()) {
            configure(config);
            userRegistryService.initializePortlets();
        }
    }

    protected static synchronized void configure(ServletConfig config) {

        log.info("configure() in GridSphere");

        // Start services
        try {
            portletContext = new SportletContext(config);

            registryService = PortletRegistryServiceImpl.getInstance();

            userRegistryService =
                    (PortletUserRegistryService) factory.createPortletService(PortletUserRegistryService.class, config, true);

            //layoutEngine = PortletLayoutEngine.getInstance(portletConfig);

            // used for debugging to reload descriptor each time
            //layoutEngine.setAutomaticReload(true);

        } catch (Exception e) {
            initFailure = e;
            log.error("Unable to instantiate services in GridSphere", e);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        // Need to translate servlet objects into portlet counterparts--
        PortletRequest portletRequest = new SportletRequest(req);
        PortletResponse portletResponse = new SportletResponse(res, req);

        layoutEngine = new PortletLayoutEngine();
        ActionEvent actionEvent = null;
        AbstractPortlet activePortlet = null;

        try {

            if (initFailure != null) {
                System.err.println(initFailure.getMessage());
                throw initFailure;
            }

            GridSpherePortletAction gspAction = new GridSpherePortletAction(portletRequest);

            if (gspAction.hasAction()) {

                System.err.println("Received ACTION: " + gspAction.toString());

                String portletID = gspAction.getConcretePortletID();

                // Get the active portlet instance
                activePortlet = registryService.getActivePortlet(portletID);

                // Get the user's portlet settings
                PortletSettings portletSettings = userRegistryService.getPortletSettings(portletRequest, portletID);

                // Set the portlet data (when this works)

                //PortletData portletData = userRegistryService.getPortletData(req, portletID);
                //req.setAttribute(GridSphereProperties.PORTLETDATA, portletData);

                DefaultPortletAction portletAction = gspAction.getPortletAction();
                actionEvent = new ActionEventImpl(portletAction, portletRequest, portletResponse);

                // do actionPerformed
                // This should do an action and recycle the cache.
                // for local portlet actionPerformed and remove cache entry
                // for remote portlet retrieve URL and put in cache (do action and service)
                activePortlet.actionPerformed(actionEvent);

                // or invoke actionPerformed on remote portlet
            }

            // Render layout
            doRender(portletRequest, portletResponse);

        } catch (Exception e) {
            handleException(portletResponse, e);
        } catch (Throwable t) {
            handleException(portletResponse, t);
        }
    }

    public void doRender(PortletRequest req, PortletResponse res) throws IOException, ServletException {
        // Make a layout
        log.info("in doRender()");
        layoutEngine.doRender(portletContext, req, res);
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
        // Shutdown local portlets
        userRegistryService.shutdownPortlets();

        // Shutdown services
        SportletServiceFactory.getInstance().shutdownServices();
        System.gc();
    }

    private final void handleException(PortletResponse res, Throwable t) {
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
