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
import org.gridlab.gridsphere.services.container.parsing.ServletParsingService;
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

    private static PortletConfig portletConfig = null;
    private static PortletRegistryService registryService = null;
    private static PortletUserRegistryService userRegistryService = null;
    private static ServletParsingService parseService = null;
    private static PortletLayoutService layoutService = null;
    private static PortletLayoutEngine layoutEngine = null;

    private static Collection abstractPortlets = new HashSet();
    private static Collection registeredPortlets = null;

    private static boolean firstDoGet = true;

    private static Throwable initFailure = null;


    public final void init(ServletConfig config) throws ServletException {
        super.init(config);
        synchronized (this.getClass()) {
            configure(config);
            registerPortlets();
        }
    }

    protected static synchronized void configure(ServletConfig config) {

        log.info("configure() in GridSphere");

        // Start services
        try {
            parseService =
                    (ServletParsingService) factory.createPortletService(ServletParsingService.class, config, true);
            registryService =
                    (PortletRegistryService) factory.createPortletService(PortletRegistryService.class, config, true);
            userRegistryService =
                    (PortletUserRegistryService) factory.createPortletService(PortletUserRegistryService.class, config, true);

            // Get a portlet config object
            portletConfig = new SportletConfig(config);

            layoutEngine = PortletLayoutEngine.getInstance(portletConfig);

            // used for debugging to reload descriptor each time
            layoutEngine.setAutomaticReload(true);

        } catch (Exception e) {
            initFailure = e;
            log.error("Unable to instantiate services in GridSphere", e);
        }
    }

    public void registerPortlets() {

        Iterator it = registryService.getRegisteredPortlets().iterator();
        try {
        while (it.hasNext()) {
            RegisteredPortlet regPortlet = (RegisteredPortlet) it.next();
            System.err.println("portlet name: " + regPortlet.getPortletName());

            AbstractPortlet abPortlet = regPortlet.getActivePortlet();
            abstractPortlets.add(abPortlet);
            //PortletConfig portletConfig = abPortlet.getPortletConfig();
            PortletSettings portletSettings = regPortlet.getPortletSettings(false);
            abPortlet.init(portletConfig);
            abPortlet.initConcrete(portletSettings);
        }
        } catch (UnavailableException e) {
            initFailure = e;
            log.error("Caught Unavailable exception: ", e);
        }
    }

    public void setupNewUser(SportletRequest sreq, PortletResponse res) {

        //PortletSettings settings = userRegistryService.getPortletSettings(portletRequest, portletID);
        //sreq.setPortletSettings(settings);

    }


    /**
     * Provide user login. Calls login() method on all portlets that are
     * in a users profile
     * For now, just login all known portlets
     */
    public final void doLogin(PortletRequest req, PortletResponse res) {
        Iterator it = userRegistryService.getPortlets(req).iterator();
        while (it.hasNext()) {
            AbstractPortlet ab = (AbstractPortlet) it.next();
            ab.login(req);
        }
    }

    /**
     * Log a user out of the portal. Calls logout() method on all portlets that are
     * in a users profile
     * For now, just logout of all known portlets
     */
    public final void doLogout(PortletRequest req, PortletResponse res) {
        Iterator it = userRegistryService.getPortlets(req).iterator();
        while (it.hasNext()) {
            AbstractPortlet ab = (AbstractPortlet) it.next();
            ab.logout(req.getPortletSession());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {


        // Need to translate servlet objects into portlet counterparts--

        //props.setPreviousMode(props.getPreviousMode());
        SportletRequest portletRequest = new SportletRequest(req);
        portletRequest.logRequest();

       /*
        SportletSettings sportletSettings;

        PortletWindow portletWindow = new SportletWindow();

        Portlet.Mode mode = Portlet.Mode.VIEW;
        Portlet.Mode previousMode = Portlet.Mode.VIEW;
        Portlet.ModeModifier modeModifier = Portlet.ModeModifier.CURRENT;


        portletRequest.setAttribute(GridSphereProperties.PORTLETSETTINGS, portletSettings);
        portletRequest.setAttribute(GridSphereProperties.PORTLETWINDOW, portletWindow);
        portletRequest.setAttribute(GridSphereProperties.MODEMODIFIER, modeModifier);
        portletRequest.setAttribute(GridSphereProperties.PORTLETMODE, mode);
        portletRequest.setAttribute(GridSphereProperties.PREVIOUSMODE, previousMode);


        portletRequest.setAttribute(GridSphereProperties.CLIENT, client);
         */



        //portletRequest = parseService.getPortletRequest(regPortlet, req);
        //portletResponse = parseService.getPortletResponse(res);
        PortletResponse portletResponse = new SportletResponse(res, portletRequest);

        try {

            if (initFailure != null) {
                System.err.println(initFailure.getMessage());
                throw initFailure;
            }

            // Here is some code for handling actions
            DefaultPortletAction action = (DefaultPortletAction)req.getAttribute(GridSphereProperties.ACTION);
            if (action == null) {
                doRender(portletRequest, portletResponse);
            }
                //setupNewUser(portletRequest, portletResponse);

            String portletID = action.getPortletID();
            PortletSettings portletSettings = userRegistryService.getPortletSettings(portletRequest, portletID);
            PortletData portletData = userRegistryService.getPortletData(portletRequest, portletID);

            if (action.getName().equals(PortletAction.LOGIN)) {
                doLogin(portletRequest, portletResponse);
            }

            if (action.getName().equals(PortletAction.LOGOUT)) {
                doLogout(portletRequest, portletResponse);
            }

            ActionEvent actionEvent = new ActionEventImpl(action, ActionEvent.ACTION_NOTYETPERFORMED,
                                                          portletRequest, portletResponse);

            AbstractPortlet activePortlet = registryService.getActivePortlet(portletID);
            activePortlet.actionNotYetPerformed(actionEvent);

            actionEvent.setEventType(ActionEvent.ACTION_PERFORMED);
            activePortlet.actionPerformed(actionEvent);

            // Render layout
            doRender(portletRequest, portletResponse);
            //doPortletLifecycle(portletRequest, portletResponse);

        } catch (Exception e) {
            handleException(portletResponse, e);
        } catch (Throwable t) {
            handleException(portletResponse, t);
        }
    }

    public void doRender(PortletRequest req, PortletResponse res) throws IOException, ServletException {
        // Make a layout
        log.info("in doRender()");
        layoutEngine.doRender(req, res);
    }

    public void doPortletLifecycle(PortletRequest req, PortletResponse res) throws IOException, ServletException {
        // Check if user is guest
        User user = req.getUser();

        // If GuestUser then only display login portlet
        if (user instanceof GuestUser) {

            // Show LoginPortlet

        }

        // Now we want to display a portlet!

        // These are the following eight calls that get invoked during the portlet lifecycle

        // create a new instance -- maybe instances are maintained by PortletRegistryService
        // then all instances are retrieved.
        //PortletAdapter helloPortlet = new HelloWorld();


        // Hmmm... need some kind of tags for identifying login and logout actions -- these are special so
        // portlet.login/logout get called-- also need to keep track of user logged in, so we can invoke
        // portlet.service method.
        // Cheap hack is to check some kind of request/session attribute
        // would rather have some isLoggedIn/Off method...
        // possibly since we maintain user objects as sportletUserImpls

        // This should be done during first GET after other portlets (servlets) have registered to PortletRegistry service
        //helloPortlet.init(portletConfig);
        //helloPortlet.initConcrete(portletSettings);

        // This should be called during a portlet login after the user logs in. (after obtaining a valid user object from
        // the LoginSportlet

        //helloPortlet.login(portletRequest);

        // For now, use cheesy request parameters.. not sure what else...


        // Here is some code for handling actions
        DefaultPortletAction action = (DefaultPortletAction)req.getAttribute(GridSphereProperties.ACTION);
        ActionEvent actionEvent = new ActionEventImpl(action, ActionEvent.ACTION_NOTYETPERFORMED, req, res);
        String portletID = action.getPortletID();

        AbstractPortlet coolPortlet = registryService.getActivePortlet(portletID);
        coolPortlet.actionNotYetPerformed(actionEvent);

        actionEvent.setEventType(ActionEvent.ACTION_PERFORMED);
        coolPortlet.actionPerformed(actionEvent);

        // Check for LOGIN
        /*
        String login = (String) req.getAttribute(GridSphereProperties.LOGIN);
        if (login != null) {
            doLogin(req, res);
        }
        */
        // Check for LOGOFF
        /*
        String logoff = (String) req.getAttribute(GridSphereProperties.LOGOFF);
        if (logoff != null) {
            doLogout(req, res);
        }
        */

        // This gets called right here in servlets's service method-- just invokes portlet service method of the active portlet
        // Hmm... may invoke service of all methods if caching etc. is up to each portlet. definitely want to cache!!
        // Hmm.. what if include is really slow and so even if jsp handles caching it doesn't matter? We'll see...
        //helloPortlet.service(portletRequest, portletResponse);

        PrintWriter out = res.getWriter();
        //ServletOutputStream out = portletResponse.getOutputStream();
        out.println("<html><body>");

        Iterator it = abstractPortlets.iterator();
        while (it.hasNext()) {
            AbstractPortlet ab = (AbstractPortlet) it.next();

            // First execute portlet business logic
            ab.actionPerformed(actionEvent);
            out.println("<b>portlet</b>");
            // Second forward to presentation logic
            ab.service(req, res);
        }

        out.println("</body></html>");
        //helloPortlet.logout(portletRequest.getPortletSession());
        //helloPortlet.destroyConcrete(portletSettings);
        //helloPortlet.destroy(portletConfig);

        //portletConfig.getContext().include("/jsp/hello.jsp", portletRequest, portletResponse);
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
        // Shut down all PortletInfo Services.
        log.info("Shutting down GridSphere");

        // Destroy all portlets
        Iterator abstractIt = abstractPortlets.iterator();
        Iterator registeredIt = abstractPortlets.iterator();
        while (abstractIt.hasNext() && registeredIt.hasNext()) {
            AbstractPortlet ab = (AbstractPortlet) abstractIt.next();
            RegisteredPortlet reg = (RegisteredPortlet) registeredIt.next();
            ab = reg.getActivePortlet();
            PortletSettings portletSettings = reg.getPortletSettings(false);
            PortletConfig portletConfig = reg.getPortletConfig();
            ab.destroy(portletConfig);
            ab.destroyConcrete(portletSettings);
        }

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
