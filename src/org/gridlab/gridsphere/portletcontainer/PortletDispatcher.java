/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.event.MessageEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Iterator;
import java.util.Collection;
import java.util.List;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The PortletRegistry service is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public class PortletDispatcher {

    private static PortletLog log = SportletLog.getInstance(PortletDispatcher.class);
    private static PortletRegistryManager registry = PortletRegistryManager.getInstance();

    public static void portletLogin(String concretePortletID, PortletRequest req, PortletResponse res) throws PortletException {
        log.info("in portletLogin " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.login(req, res);
    }

    public static final void portletLogout(String concretePortletID, PortletRequest req, PortletResponse res) throws PortletException {
        log.info("in portletLogout " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.logout(req, res);
    }

    public static final void portletService(String concretePortletID, PortletRequest req, PortletResponse res) throws PortletException {
        log.info("in portletService " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.service(req, res);
    }

    public static final void portletAction(String concretePortletID, DefaultPortletAction action, PortletRequest req, PortletResponse res) throws PortletException {
        log.info("in portletAction " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.actionPerformed(action, req, res);
    }

    public static final void portletTitle(String concretePortletID, PortletRequest req, PortletResponse res) throws PortletException {
        log.info("in portletTitle " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.doTitle(req, res);
    }

    public static final void portletWindowEvent(String concretePortletID, WindowEvent winEvent, PortletRequest req, PortletResponse res) throws PortletException {
        log.info("in portletWindowEvent " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.windowEvent(winEvent, req, res);
    }

    public static final void portletMessageEvent(String concretePortletID, MessageEvent msgEvent, PortletRequest req, PortletResponse res) throws PortletException {
        log.info("in portletMessageEvent " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.messageEvent(msgEvent, req, res);
    }

    public static final void initAllPortlets(PortletRequest req, PortletResponse res) {
            // Initialize all concrete portlets for each application portlet
            Collection appPortlets = registry.getAllApplicationPortlets();
            PortletWrapper portletWrapper = null;
            try {
                Iterator it = appPortlets.iterator();
                while (it.hasNext()) {
                    ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
                    portletWrapper = appPortlet.getPortletWrapper();
                    List concPortlets = appPortlet.getConcretePortlets();
                    Iterator concIt = concPortlets.iterator();
                    PortletSettings settings = null;
                    // initialize the application portlet
                    portletWrapper.init(req, res);
                    while (concIt.hasNext()) {
                        ConcretePortlet concPortlet = (ConcretePortlet) concIt.next();
                        settings = concPortlet.getPortletSettings();
                        // initialize the concrete portlet
                        portletWrapper.initConcrete(settings, req, res);
                    }
                }
            } catch (Exception e) {
                //throw new PortletLifecycleException(e);
            }
        }

        public static final void initPortlets(String webApplicationName, PortletRequest req, PortletResponse res) {
            // Initialize all concrete portlets for each application portlet
            Collection appPortlets = registry.getApplicationPortlets(webApplicationName);
            PortletWrapper portletWrapper = null;
            try {
                Iterator it = appPortlets.iterator();
                while (it.hasNext()) {
                    ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
                    portletWrapper = appPortlet.getPortletWrapper();
                    List concPortlets = appPortlet.getConcretePortlets();
                    Iterator concIt = concPortlets.iterator();
                    PortletSettings settings = null;
                    // initialize the application portlet
                    portletWrapper.init(req, res);
                    while (concIt.hasNext()) {
                        ConcretePortlet concPortlet = (ConcretePortlet) concIt.next();
                        settings = concPortlet.getPortletSettings();
                        // initialize the concrete portlet
                        portletWrapper.initConcrete(settings, req, res);
                    }
                }
            } catch (Exception e) {
                //throw new PortletLifecycleException(e);
            }
        }

        public static final void destroyAllPortlets(PortletRequest req, PortletResponse res) {

            // First destroy all concrete portlets for each application portlet
            Collection appPortlets = registry.getAllApplicationPortlets();
            PortletWrapper portletWrapper = null;
            try {
                Iterator it = appPortlets.iterator();
                while (it.hasNext()) {
                    ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
                    portletWrapper = appPortlet.getPortletWrapper();
                    List concPortlets = appPortlet.getConcretePortlets();
                    Iterator concIt = concPortlets.iterator();
                    PortletSettings settings = null;

                    while (concIt.hasNext()) {
                        ConcretePortlet concPortlet = (ConcretePortlet) concIt.next();
                        settings = concPortlet.getPortletSettings();
                        // destroy the concrete portlet
                        portletWrapper.destroyConcrete(settings, req, res);
                    }
                }
                // destroy the application portlet
                portletWrapper.destroy(req, res);
            } catch (Exception e) {
                //throw new PortletLifecycleException(e);
            }
        }

        public static final void destroyPortlets(String webApplicationName, PortletRequest req, PortletResponse res) {

            // First destroy all concrete portlets for each application portlet
            Collection appPortlets = registry.getApplicationPortlets(webApplicationName);
            PortletWrapper portletWrapper = null;
            try {
                Iterator it = appPortlets.iterator();
                while (it.hasNext()) {
                    ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
                    portletWrapper = appPortlet.getPortletWrapper();
                    List concPortlets = appPortlet.getConcretePortlets();
                    Iterator concIt = concPortlets.iterator();
                    PortletSettings settings = null;
                    while (concIt.hasNext()) {
                        ConcretePortlet concPortlet = (ConcretePortlet) concIt.next();
                        settings = concPortlet.getPortletSettings();
                        // destroy the concrete portlet
                        portletWrapper.destroyConcrete(settings, req, res);
                    }
                    // destroy the application portlet
                    portletWrapper.destroy(req, res);
                }
            } catch (Exception e) {
                //throw new PortletLifecycleException(e);
            }
        }

        public static final void initPortlet(String concretePortletID, PortletRequest req, PortletResponse res) {
            String appID = getApplicationPortletID(concretePortletID);
            ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
            PortletWrapper portletWrapper = appPortlet.getPortletWrapper();
            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(concretePortletID);
            PortletSettings settings = concPortlet.getPortletSettings();
            try {
                // init the application portlet
                portletWrapper.init(req, res);
                // init the concrete portlet
                portletWrapper.initConcrete(settings, req, res);
            } catch (Exception e) {
                // do something here
            }
        }

        public static final void destroyPortlet(String concretePortletID, PortletRequest req, PortletResponse res) {
            String appID = getApplicationPortletID(concretePortletID);
            ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
            PortletWrapper wrapper = appPortlet.getPortletWrapper();
            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(concretePortletID);
            PortletSettings settings = concPortlet.getPortletSettings();
            try {
                // destroy the concrete portlet
                wrapper.destroyConcrete(settings, req, res);
                // destroy the application portlet
                wrapper.destroy(req, res);
            } catch (Exception e) {
                // do something here
            }
        }

        protected static String getApplicationPortletID(String concretePortletID) {
            int i = concretePortletID.lastIndexOf(".");
            if (i < 0) return "";
            return concretePortletID.substring(0, i);
        }

}
