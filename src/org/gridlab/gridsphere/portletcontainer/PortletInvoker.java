/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.io.IOException;

/**
 * The <code>PortletInvoker</code> provides static lifecycle routines for performing portlet operations on
 * concrete portlets.
 *
 * @see org.gridlab.gridsphere.portletcontainer.PortletDispatcher
 */
public class PortletInvoker {

    private static PortletLog log = SportletLog.getInstance(PortletInvoker.class);
    private static PortletRegistry registry = PortletRegistry.getInstance();

    /**
     * Initializes an application portlet
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void init(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in init " + concretePortletID);
        String appID = getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            // really want to try with a new dispatcher to see
            //PortletDispatcher(RequestDispatcher rd, ApplicationPortletConfig portletApp)
            PortletDispatcher portletDispatcher = appPortlet.getPortletDispatcher();
            // init the application portlet
            portletDispatcher.init(req, res);
        } else {
            log.info("in init: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Initializes a concrete portlet
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void initConcrete(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in initConcrete " + concretePortletID);
        String appID = getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher portletDispatcher = appPortlet.getPortletDispatcher();
            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(concretePortletID);
            PortletSettings settings = concPortlet.getPortletSettings();
            // init the concrete portlet
            portletDispatcher.initConcrete(settings, req, res);
        } else {
            log.info("in initConcrete: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Shuts down an application portlet
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void destroy(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in destroy " + concretePortletID);
        String appID = getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher();
            // destroy the application portlet
            dispatcher.destroy(req, res);
        } else {
            log.info("in destroy: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Shuts down a concrete portlet
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void destroyConcrete(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in destroyConcrete " + concretePortletID);
        String appID = getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher();
            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(concretePortletID);
            PortletSettings settings = concPortlet.getPortletSettings();
            // destroy the concrete portlet
            dispatcher.destroyConcrete(settings, req, res);
        } else {
            log.info("in destroyConcrete: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Initializes a concrete portlet instance for a user
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void login(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in login " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher();
            dispatcher.login(req, res);
        } else {
            log.info("in login: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Shutds down a concrete portlet instance for a user
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void logout(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in logout " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher();
            dispatcher.logout(req, res);
        } else {
            log.info("in logout: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs service method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void service(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in service " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher();
            dispatcher.service(req, res);
        } else {
            log.info("in service: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs action performed method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void actionPerformed(String concretePortletID, DefaultPortletAction action, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in actionPerformed " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher();
            dispatcher.actionPerformed(action, req, res);
        } else {
            log.info("in actionPerformed: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs doTitle method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void doTitle(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in doTitle " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher();
            dispatcher.doTitle(req, res);
        } else {
            log.info("in doTitle: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs window event method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void windowEvent(String concretePortletID, WindowEvent winEvent, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in windowEvent " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher();
            dispatcher.windowEvent(winEvent, req, res);
        } else {
            log.info("in windowEvent: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs message event method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void messageEvent(String concretePortletID, DefaultPortletMessage msgEvent, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in messageEvent " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher();
            dispatcher.messageEvent(msgEvent, req, res);
        } else {
            log.info("in messageEvent: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Initializes all application portlets
     *
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void initAllPortlets(PortletRequest req, PortletResponse res) throws IOException, PortletException {
        // Initialize all concrete portlets for each application portlet
        Collection appPortlets = registry.getAllApplicationPortlets();
        PortletDispatcher portletDispatcher = null;
        Iterator it = appPortlets.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            portletDispatcher = appPortlet.getPortletDispatcher();
            List concPortlets = appPortlet.getConcretePortlets();
            Iterator concIt = concPortlets.iterator();
            PortletSettings settings = null;
            // initialize the application portlet
            log.info("initializing application portlet " + appPortlet.getApplicationPortletID());
            portletDispatcher.init(req, res);
            while (concIt.hasNext()) {
                ConcretePortlet concPortlet = (ConcretePortlet) concIt.next();
                settings = concPortlet.getPortletSettings();
                // initialize the concrete portlet
                log.info("initializing concrete portlet " + concPortlet.getConcretePortletID());
                portletDispatcher.initConcrete(settings, req, res);
            }
        }
    }

    /**
     * Initializes all application portlets in a portlet web application
     *
     * @param webApplicationName the name of the portlet web application
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void initPortletWebApp(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        // Initialize all concrete portlets for each application portlet
        Collection appPortlets = registry.getApplicationPortlets(webApplicationName);
        PortletDispatcher portletDispatcher = null;
        Iterator it = appPortlets.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            portletDispatcher = appPortlet.getPortletDispatcher();
            List concPortlets = appPortlet.getConcretePortlets();
            Iterator concIt = concPortlets.iterator();
            PortletSettings settings = null;
            // initialize the application portlet
            log.info("initializing application portlet " + appPortlet.getApplicationPortletID());
            portletDispatcher.init(req, res);
            while (concIt.hasNext()) {
                ConcretePortlet concPortlet = (ConcretePortlet) concIt.next();
                settings = concPortlet.getPortletSettings();
                // initialize the concrete portlet
                log.info("initializing concrete portlet " + concPortlet.getConcretePortletID());
                portletDispatcher.initConcrete(settings, req, res);
            }
        }
    }

    /**
     * Shuts down all application portlets
     *
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void destroyAllPortlets(PortletRequest req, PortletResponse res) throws IOException, PortletException {
        // First destroy all concrete portlets for each application portlet
        Collection appPortlets = registry.getAllApplicationPortlets();
        PortletDispatcher portletDispatcher = null;
        Iterator it = appPortlets.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            portletDispatcher = appPortlet.getPortletDispatcher();
            List concPortlets = appPortlet.getConcretePortlets();
            Iterator concIt = concPortlets.iterator();
            PortletSettings settings = null;
            log.info("destroying application portlet " + appPortlet.getApplicationPortletID());
            while (concIt.hasNext()) {
                ConcretePortlet concPortlet = (ConcretePortlet) concIt.next();
                settings = concPortlet.getPortletSettings();
                // destroy the concrete portlet
                log.info("destroying concrete portlet " + concPortlet.getConcretePortletID());
                portletDispatcher.destroyConcrete(settings, req, res);
            }
        }
        // destroy the application portlet
        portletDispatcher.destroy(req, res);
    }

    /**
     * Shuts down all application portlets in a portlet web application
     *
     * @param webApplicationName the name of the portlet web application
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public static final void destroyPortletWebApp(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        // First destroy all concrete portlets for each application portlet
        Collection appPortlets = registry.getApplicationPortlets(webApplicationName);
        PortletDispatcher portletDispatcher = null;
        Iterator it = appPortlets.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            portletDispatcher = appPortlet.getPortletDispatcher();
            List concPortlets = appPortlet.getConcretePortlets();
            Iterator concIt = concPortlets.iterator();
            PortletSettings settings = null;
            log.info("destroying application portlet " + appPortlet.getApplicationPortletID());
            while (concIt.hasNext()) {
                ConcretePortlet concPortlet = (ConcretePortlet) concIt.next();
                settings = concPortlet.getPortletSettings();
                // destroy the concrete portlet
                log.info("destroying concrete portlet " + concPortlet.getConcretePortletID());
                portletDispatcher.destroyConcrete(settings, req, res);
            }
            // destroy the application portlet
            portletDispatcher.destroy(req, res);
        }

    }

    /**
     * Returns the application portlet id from the supplied concrete portlet id
     *
     * @param concretePortletID the concrete portlet id
     * @return the application portlet id
     */
    protected static String getApplicationPortletID(String concretePortletID) {
        int i = concretePortletID.lastIndexOf(".");
        if (i < 0) return "";
        return concretePortletID.substring(0, i);
    }

}
