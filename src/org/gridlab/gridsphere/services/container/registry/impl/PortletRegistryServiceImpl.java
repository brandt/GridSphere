/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.PortletWebApplication;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container, layout manager and any other services that require an active portlet.
 * The PortletInfo base class is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public class PortletRegistryServiceImpl implements PortletRegistryService, PortletServiceProvider {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletRegistryServiceImpl.class);
    private static PortletRegistryService registryService = null;
    private ServletContext context = null;

    private PortletRegistryManager manager = PortletRegistryManager.getInstance();

    // A multi-valued hashtable with a webapp key and a List value containing portletAppID's
    private Map webapps = new Hashtable();

    /**
     * The init method is responsible for parsing portlet.xml and creating ConcretePortlet objects based on the
     * entries. The RegisteredPortlets are managed by the PortletRegistryService.
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.info("in init()");
        this.context = config.getServletConfig().getServletContext();
        String webapp = "coreportlets";
        addPortletWebApplication(webapp);
    }

    public void destroy() {
        log.info("in destroy()");
    }

    /**
     * Adds a portlet web application to the registry
     *
     * @param the web application name
     */
    public void addPortletWebApplication(String webApplicationName) {
        PortletWebApplication portletWebApp = new PortletWebApplication(webApplicationName, context);
        Collection appPortlets = portletWebApp.getAllApplicationPortlets();
        Iterator it = appPortlets.iterator();
        while (it.hasNext()) {
            //appIds.add(appPortlets[i].getPortletAppID());
            ApplicationPortlet appPortlet = (ApplicationPortlet)it.next();
            webapps.put(webApplicationName, appPortlet);
            manager.addApplicationPortlet(appPortlet);
        }
    }

    /**
     * Remnoves a portlet web application from the registry
     *
     * @param the web application name
     */
    public void removePortletWebApplication(String webApplicationName) {
        ArrayList appIds = (ArrayList)webapps.get(webApplicationName);
        for (int i = 0; i < appIds.size(); i++) {
            manager.removeApplicationPortlet((String)appIds.get(i));
        }
        webapps.remove(webApplicationName);
    }

    /**
     * Lists all the portlet web applications in the registry
     *
     * @return the list of web application names
     */
    public String[] listPortletWebApplications() {
        return (String[])webapps.keySet().toArray();
    }

}
