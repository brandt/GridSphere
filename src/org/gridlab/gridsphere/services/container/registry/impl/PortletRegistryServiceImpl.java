/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletApplication;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDeploymentDescriptor;
import org.gridlab.gridsphere.portletcontainer.impl.RegisteredSportletImpl;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The Portlet base class is responsible for reading in the associated portlet.xml file and
 * creating a RegisteredPortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of RegisteredPortlet objects.
 */
public class PortletRegistryServiceImpl implements PortletRegistryService, PortletServiceProvider {

    private static PortletServiceFactory factory = SportletServiceFactory.getInstance();
    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletRegistryServiceImpl.class);

    private static Map allPortlets = new Hashtable();
    private static int portletCount = 0;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.info("in init()");

        // load in portlet.xml file
        String appRoot = config.getServletConfig().getServletContext().getRealPath("");
        String portletConfigFile = config.getInitParameter("portlet.xml");
        String portletMappingFile = config.getInitParameter("portlet-mapping-file.xml");
        if (portletConfigFile == null) {
            portletConfigFile = "/WEB-INF/conf/portlet.xml";
        }
        if (portletMappingFile == null) {
            portletMappingFile = "/WEB-INF/conf/portlet-mapping.xml";
        }
        String fullPath = appRoot + portletConfigFile;
        String mappingPath = appRoot + portletMappingFile;
        try {
            FileInputStream fistream = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            log.error("Can't find file: " + fullPath);
            throw new PortletServiceUnavailableException("Unable to create registered portlet");
        }
        // Now parse portlet.xml and stick relevant info into portletSettings and portletConfig
        //portletConfig = new SportletConfig(config);

        // Here is where we need to have a Portal Deployment Descriptor that is marshalled into an object
        PortletDeploymentDescriptor pdd = null;
        try {
            pdd = new PortletDeploymentDescriptor(fullPath, mappingPath);
        } catch (Exception e) {
            throw new PortletServiceUnavailableException("Unable to create PortletDeploymentDescriptor");
        }
        //portletSettings = new SportletSettings(pdd);

        //String portletClass = "org.gridlab.gridsphere.portlets.HelloWorld";
        Iterator portletApps = pdd.getPortletApp().iterator();
        while (portletApps.hasNext()) {
            PortletApplication portletApp = (PortletApplication) portletApps.next();
            try {
                RegisteredPortlet registeredPortlet = new RegisteredSportletImpl(portletApp);
                String portletID = getUniqueID(registeredPortlet);
                allPortlets.put(portletID, registeredPortlet);
            } catch (Exception e) {
                throw new PortletServiceUnavailableException("Unable to create registered portlet");
            }
        }
    }

    public void destroy() {
        log.info("in destroy()");
    }

    /**
     * Returns the collection of registered portlets
     *
     * @return the registered portlets
     */
    public Collection getRegisteredPortlets() {
        return allPortlets.values();
    }

    /**
     * Return the registered portlet IDs
     *
     * @return the registered portlet IDs
     */
    public Set getRegisteredPortletIDs() {
        return allPortlets.keySet();
    }

    /**
     * Return a registered portlet given its identifier
     *
     * @return the portletID
     */
    public RegisteredPortlet getRegisteredPortlet(String portletID) {
        return (RegisteredPortlet) allPortlets.get(portletID);
    }

    /**
     * Registers a portlet with the PortletRegistryService
     *
     * @param registeredPortlet the registered portlet
     */
    public String registerPortlet(RegisteredPortlet registeredPortlet) {
        String portletID = getUniqueID(registeredPortlet);
        allPortlets.put(portletID, registeredPortlet);
        log.info("Registering portlet: " + portletID + " name: " + registeredPortlet.getPortletName());
        return portletID;
    }

    /**
     * Unregisters a portlet with the PortletRegistryService
     *
     * @param portletID the portlet ID
     */
    public void unregisterPortlet(String portletID) {
        log.info("Unregistering portlet: " + portletID);
        allPortlets.remove(portletID);
    }

    /**
     * Returns a unique ID used in tagging a RegisteredPortlet
     *
     * @return a unique ID expressed as a String
     */
    protected String getUniqueID(RegisteredPortlet portlet) {
        portletCount++;
        return "portal id - " + portletCount;
    }

}
