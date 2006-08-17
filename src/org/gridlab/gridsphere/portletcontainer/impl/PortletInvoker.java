/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletInvoker.java 4848 2006-06-10 18:06:15Z novotny $
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.registry.PortletRegistryService;
import org.gridlab.gridsphere.portletcontainer.PortletDispatcher;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * The <code>PortletInvoker</code> provides static lifecycle routines for performing portlet operations on
 * concrete portlets.
 *
 * @see org.gridlab.gridsphere.portletcontainer.impl.SportletDispatcher
 */
public class PortletInvoker {

    private PortletLog log = SportletLog.getInstance(PortletInvoker.class);
    private PortletRegistryService registry = null;

    public PortletInvoker() {
        try {
            registry = (PortletRegistryService)PortletServiceFactory.createPortletService(PortletRegistryService.class, true);
        } catch (PortletServiceException e) {
            log.error("Unable to init services! ", e);
        }
    }

    /**
     * Initializes an application portlet
     *
     * @param concretePortletID the concrete portlet id
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void init(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in init " + concretePortletID);
        String appID = getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            // really want to try with a new dispatcher to see
            //PortletDispatcher(RequestDispatcher rd, ApplicationPortletConfig portletApp)
            PortletDispatcher portletDispatcher = appPortlet.getPortletDispatcher(req, res);
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
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void initConcrete(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in initConcrete " + concretePortletID);
        String appID = getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher portletDispatcher = appPortlet.getPortletDispatcher(req, res);
            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(concretePortletID);
            PortletSettings settings = concPortlet.getPortletSettings();
            // init the concrete portlet
            if (settings != null) {
                portletDispatcher.initConcrete(settings, req, res);
            } else {
                log.info("not invoking initConcrete on portlet " + concPortlet.getConcretePortletID());
            }
        } else {
            log.info("in initConcrete: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Shuts down an application portlet
     *
     * @param concretePortletID the concrete portlet id
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void destroy(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in destroy " + concretePortletID);
        String appID = getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
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
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void destroyConcrete(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in destroyConcrete " + concretePortletID);
        String appID = getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(concretePortletID);
            PortletSettings settings = concPortlet.getPortletSettings();
            // destroy the concrete portlet
            if (settings != null) {
                dispatcher.destroyConcrete(settings, req, res);
            } else {
                log.info("not invoking destroyConcrete on portlet " + concPortlet.getConcretePortletID());
            }
        } else {
            log.info("in destroyConcrete: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Initializes a concrete portlet instance for a user
     *
     * @param concretePortletID the concrete portlet id
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void login(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in login " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.login(req, res);
        } else {
            log.info("in login: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Shutds down a concrete portlet instance for a user
     *
     * @param concretePortletID the concrete portlet id
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void logout(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.info("in logout " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.logout(req, res);
        } else {
            log.info("in logout: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs service method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void service(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in service " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.service(req, res);
        } else {
            log.info("in service: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs action performed method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void actionPerformed(String concretePortletID, DefaultPortletAction action, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in actionPerformed " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.actionPerformed(action, req, res);
        } else {
            log.info("in actionPerformed: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs doTitle method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void doTitle(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in doTitle " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.doTitle(req, res);
        } else {
            throw new PortletException("Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs window event method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void windowEvent(String concretePortletID, WindowEvent winEvent, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in windowEvent " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.windowEvent(winEvent, req, res);
        } else {
            log.info("in windowEvent: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Performs message event method on a concrete portlet instance
     *
     * @param concretePortletID the concrete portlet id
     * @param req               the <code>PortletRequest</code>
     * @param res               the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void messageEvent(String concretePortletID, PortletMessage msgEvent, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        log.debug("in messageEvent " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        if (appPortlet != null) {
            PortletDispatcher dispatcher = appPortlet.getPortletDispatcher(req, res);
            dispatcher.messageEvent(concretePortletID, msgEvent, req, res);
        } else {
            log.info("in messageEvent: Unable to find portlet in registry: " + concretePortletID);
        }
    }

    /**
     * Initializes all application portlets
     *
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public synchronized void initPortlet(String concretePortletID, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        // Initialize all concrete portlets for each application portlet
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(concretePortletID);
        PortletDispatcher portletDispatcher = null;

        log.debug("init app portlet" + appPortlet.getApplicationPortletID());
        portletDispatcher = appPortlet.getPortletDispatcher(req, res);
        if (portletDispatcher == null) {
            throw new PortletException("Unable to get a dispatcher for application portlet: " + appPortlet);
        }
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
            if (settings != null) {
                portletDispatcher.initConcrete(settings, req, res);
            } else {
                log.info("not invoking initConcrete on portlet " + concPortlet.getConcretePortletID());
            }
        }

        if (log.isDebugEnabled()) registry.logRegistry();
    }

    /**
     * Initializes all application portlets
     *
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public synchronized void initAllPortlets(PortletRequest req, PortletResponse res) throws IOException, PortletException {
        // Initialize all concrete portlets for each application portlet
        Collection appPortlets = registry.getAllApplicationPortlets();
        PortletDispatcher portletDispatcher = null;

        // make a clone of appPortlets since JSR PortletServlet will add more portlets to the
        // registry causing ConcurrentModificationExceptions to be thrown!

        List appPortletsCopy = new Vector(appPortlets);

        Iterator it = appPortletsCopy.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            log.debug("init app portlet" + appPortlet.getApplicationPortletID());
            portletDispatcher = appPortlet.getPortletDispatcher(req, res);
            if (portletDispatcher == null) {
                throw new PortletException("Unable to get a dispatcher for application portlet: " + appPortlet);
            }
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
                if (settings != null) {
                    portletDispatcher.initConcrete(settings, req, res);
                } else {
                    log.info("not invoking initConcrete on portlet " + concPortlet.getConcretePortletID());
                }
            }
        }
        if (log.isDebugEnabled()) registry.logRegistry();
    }

    /**
     * Initializes all application portlets in a portlet web application
     *
     * @param webApplicationName the name of the portlet web application
     * @param req                the <code>PortletRequest</code>
     * @param res                the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void initPortletWebApp(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        // Initialize all concrete portlets for each application portlet
        Collection appPortlets = registry.getApplicationPortlets(webApplicationName);
        PortletDispatcher portletDispatcher = null;
        Iterator it = appPortlets.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            portletDispatcher = appPortlet.getPortletDispatcher(req, res);
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
                if (settings != null) {
                    portletDispatcher.initConcrete(settings, req, res);
                } else {
                    log.info("not invoking initConcrete on portlet " + concPortlet.getConcretePortletID());
                }
            }
        }
    }

    /**
     * Shuts down all application portlets
     *
     * @param req the <code>PortletRequest</code>
     * @param res the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void destroyAllPortlets(PortletRequest req, PortletResponse res) throws IOException, PortletException {
        // First destroy all concrete portlets for each application portlet
        Collection appPortlets = registry.getAllApplicationPortlets();
        PortletDispatcher portletDispatcher = null;
        Iterator it = appPortlets.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            portletDispatcher = appPortlet.getPortletDispatcher(req, res);
            List concPortlets = appPortlet.getConcretePortlets();
            Iterator concIt = concPortlets.iterator();
            PortletSettings settings = null;
            log.info("destroying application portlet " + appPortlet.getApplicationPortletID());
            while (concIt.hasNext()) {
                ConcretePortlet concPortlet = (ConcretePortlet) concIt.next();
                settings = concPortlet.getPortletSettings();
                // destroy the concrete portlet
                log.info("destroying concrete portlet " + concPortlet.getConcretePortletID());
                if (settings != null) {
                    portletDispatcher.destroyConcrete(settings, req, res);
                } else {
                    log.info("not invoking destroyConcrete on portlet " + concPortlet.getConcretePortletID());
                }
            }
        }
        // destroy the application portlet
        portletDispatcher.destroy(req, res);
    }

    /**
     * Shuts down all application portlets in a portlet web application
     *
     * @param webApplicationName the name of the portlet web application
     * @param req                the <code>PortletRequest</code>
     * @param res                the <code>PortletResponse</code>
     * @throws IOException      if an I/O error occurs
     * @throws PortletException if a portlet/servlet error occurs
     */
    public void destroyPortletWebApp(String webApplicationName, PortletRequest req, PortletResponse res) throws IOException, PortletException {
        // First destroy all concrete portlets for each application portlet
        log.debug("in destroyPortletWebApp " + webApplicationName);
        List appPortlets = registry.getApplicationPortlets(webApplicationName);
        PortletDispatcher portletDispatcher = null;
        Iterator it = appPortlets.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            portletDispatcher = appPortlet.getPortletDispatcher(req, res);
            List concPortlets = appPortlet.getConcretePortlets();
            Iterator concIt = concPortlets.iterator();
            PortletSettings settings = null;
            log.info("destroying application portlet " + appPortlet.getApplicationPortletID());
            while (concIt.hasNext()) {
                ConcretePortlet concPortlet = (ConcretePortlet) concIt.next();
                settings = concPortlet.getPortletSettings();
                // destroy the concrete portlet
                log.info("destroying concrete portlet " + concPortlet.getConcretePortletID());
                if (settings != null) {
                    portletDispatcher.destroyConcrete(settings, req, res);
                } else {
                    log.info("not invoking destroyConcrete on portlet " + concPortlet.getConcretePortletID());
                }
            }
        }
        // destroy the application portlet
        portletDispatcher.destroy(req, res);
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
