/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletRegistry.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.registry.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridsphere.portletcontainer.PortletWebApplication;
import org.gridsphere.services.core.registry.PortletRegistryService;

import java.util.*;

/**
 * The <code>PortletRegistry</code> is simply a static <code>Map</code> that keeps track
 * of all the application portlets known to the container.
 */
public class PortletRegistryServiceImpl implements PortletRegistryService, PortletServiceProvider {

    private Log log = LogFactory.getLog(PortletRegistryServiceImpl.class);

    private static Map<String, ApplicationPortlet> allApplicationPortlets = null;
    private static Map<String, PortletWebApplication> webApps = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        allApplicationPortlets = new Hashtable<String, ApplicationPortlet>();
        webApps = new Hashtable<String, PortletWebApplication>();
    }

    public void destroy() {
        allApplicationPortlets = null;
        webApps = null;
    }

    public void addWebApplication(PortletWebApplication webApp) {
        log.debug("Adding PortleWebapp to registry: " + webApp.getWebApplicationName());
        webApps.put(webApp.getWebApplicationName(), webApp);
    }

    public PortletWebApplication getWebApplication(String webappName) {
        return webApps.get(webappName);
    }

    /**
     * Adds an application portlet to the registry
     *
     * @param appPortlet an <code>ApplicationPortlet</code>
     */
    public void addApplicationPortlet(ApplicationPortlet appPortlet) {
        String key = appPortlet.getApplicationPortletID();
        if (key.indexOf("#") < 0) {
            key = appPortlet.getWebApplicationName() + "#" + appPortlet.getApplicationPortletID();
            log.debug("adding application portlet key to registry: " + key);
        }
        if (allApplicationPortlets.get(key) != null) {
            log.debug("Replacing existing app portlet: " + key);
        }
        allApplicationPortlets.put(key, appPortlet);
    }

    /**
     * Removes an application portlet from the registry
     *
     * @param applicationPortlet the application portlet
     */
    public void removeApplicationPortlet(ApplicationPortlet applicationPortlet) {
        allApplicationPortlets.remove(applicationPortlet.getApplicationPortletID());
    }

    /**
     * Return the application portlet with the corresponding id
     *
     * @param applicationPortletID the application portlet id
     * @return an application portlet
     */
    public ApplicationPortlet getApplicationPortlet(String applicationPortletID) {
        int idx = applicationPortletID.indexOf("#");
        if (idx > 0) {
            return (ApplicationPortlet) allApplicationPortlets.get(applicationPortletID);
        }
        Collection<ApplicationPortlet> coll = allApplicationPortlets.values();

        for (ApplicationPortlet app : coll) {
            if (app.getApplicationPortletID().equals(applicationPortletID)) return app;
            if (app.getApplicationPortletClassName().equals(applicationPortletID)) return app;
        }
        log.debug("Unable to find " + applicationPortletID + " in registry");
        return null;
    }


    /**
     * Returns all application portlets from the registry
     *
     * @return the collection of application portlets
     */
    public Collection<ApplicationPortlet> getAllApplicationPortlets() {
        return allApplicationPortlets.values();
    }


    /**
     * Returns all application portlets from the supplied portlet web application
     *
     * @param webApplicationName the portlet web application name
     * @return the list of application portlets
     */
    public List<ApplicationPortlet> getApplicationPortlets(String webApplicationName) {
        List<ApplicationPortlet> webappPortlets = new Vector<ApplicationPortlet>();
        Set<String> set = allApplicationPortlets.keySet();
        ApplicationPortlet appPortlet;
        for (String concretePortletID : set) {
            appPortlet = (ApplicationPortlet) allApplicationPortlets.get(concretePortletID);
            if (appPortlet.getWebApplicationName().equals((webApplicationName))) {
                webappPortlets.add(appPortlet);
            }
        }
        return webappPortlets;
    }

    /**
     * Returns the application portlet id given a concrete portlet id
     *
     * @param concretePortletID the concrete portlet id
     * @return the application portlet id or an empty string
     */
    public String getApplicationPortletID(String concretePortletID) {
        int i = concretePortletID.lastIndexOf(".");
        // do nothing if id is on form <web app name>#<portlet name>
        if (concretePortletID.indexOf("#") > 0) return concretePortletID;
        if (i < 0) return concretePortletID;
        // check to see if it has number at the end
        String numStr = concretePortletID.substring(i + 1);
        String appID = "";
        try {
            Integer.parseInt(numStr);
            appID = concretePortletID.substring(0, i);
        } catch (NumberFormatException e) {
            appID = concretePortletID;
        }
        return appID;
    }

    public void logRegistry() {
        log.debug("Displaying Portlet registry contents:\n");
        for (String s : allApplicationPortlets.keySet()) {
            String appID = (String) s;
            ApplicationPortlet appPortlet = (ApplicationPortlet) allApplicationPortlets.get(appID);
            log.debug("\tApplication portlet : " + appPortlet.getApplicationPortletID() + "\n");
            log.debug("\t" + appPortlet + "\n");
        }


    }
}
