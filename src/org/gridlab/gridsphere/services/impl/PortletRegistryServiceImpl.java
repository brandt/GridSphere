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

public class PortletRegistryServiceImpl implements PortletRegistryService, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(PortletRegistryServiceImpl.class);
    private Map allPortlets = new Hashtable();
    private static int portletCount = 0;

    public void init(PortletServiceConfig config) {
        log.info("in init()");
    }

    public void destroy() {
       log.info("in destroy()");
    }

    public Set getRegisteredPortlets() {
        return allPortlets.entrySet();
    }

    public Set getRegisteredPortletIDs() {
        return allPortlets.keySet();
    }

    public RegisteredPortlet getRegisteredPortlet(String portletID) {
        return (RegisteredPortlet)allPortlets.get(portletID);
    }

    public String registerPortlet(RegisteredPortlet registeredPortlet) {
        String portletID = getUniqueID(registeredPortlet);
        allPortlets.put(portletID, registeredPortlet);
        log.info("Registering portlet: " + portletID);
        return portletID;
    }

    public void unregisterPortlet(String portletID) {
        log.info("Unregistering portlet: " + portletID);
        allPortlets.remove(portletID);
    }

    protected String getUniqueID(RegisteredPortlet portlet) {
        portletCount++;
        return "portal id - " + portletCount;
    }

}
