/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletResponseImpl;
import org.gridlab.gridsphere.portlet.impl.SportletRequestImpl;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.services.ServletParsingService;
import org.gridlab.gridsphere.services.PortletRegistryService;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The Portlet base class is responsible for reading in the associated portlet.xml file and
 * creating a RegisteredPortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of RegisteredPortlet objects.
 */
public class PortletRegistryServiceImpl implements PortletRegistryService, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(PortletRegistryServiceImpl.class);
    private static Map allPortlets = new Hashtable();
    private static int portletCount = 0;
    private static RegisteredPortlet reg = null;

    public void init(PortletServiceConfig config) {
        log.info("in init()");
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
        return (RegisteredPortlet)allPortlets.get(portletID);
    }

    /**
     * Registers a portlet with the PortletRegistryService
     *
     * @param registeredPortlet the registered portlet
     */
    public String registerPortlet(RegisteredPortlet registeredPortlet) {
        reg = registeredPortlet;
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
