/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletSettings;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.descriptor.*;
import org.gridlab.gridsphere.portletcontainer.impl.Cacheable;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortletException;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * An application portlet represents the portlet application defined in the portlet.xml
 * ApplicationPortlet is mostly a proxy for the PortletApplication class used by Castor
 *
 * @see <code>org.gridlab.gridsphere.portletcontainer.descriptor.PortletApplication</code>
 */
public class ApplicationPortletImpl implements ApplicationPortlet {

    private PortletLog log = SportletLog.getInstance(ApplicationPortletImpl.class);

    private String uid = "";
    private String name = "";
    private Cacheable cacheable = null;
    private SupportsModes supportedModes = null;
    private List allowedStates = null;
    //private PortletDefinition portletDef = null;
    private List concretePortlets = null;
    private PortletConfig portletConfig = null;
    private PortletApplication portletApp = null;

    public ApplicationPortletImpl(PortletDefinition portletDef, ClassLoader classLoader) {
        //this.portletDef = portletDef;
        Iterator it;
        this.portletApp = portletDef.getPortletApp();
        PortletInfo portletInfo = portletApp.getPortletInfo();

        // Set cache information
        CacheInfo cacheInfo = portletInfo.getCacheInfo();
        /*
        if (cacheInfo == null) {
            cacheInfo = new CacheInfo("true", -1);
        }*/
        cacheable = new Cacheable();
        cacheable.setExpiration(cacheInfo.getExpires());
        String shared = cacheInfo.getShared();
        if ((shared.equalsIgnoreCase("true")) ||
            (shared.equalsIgnoreCase("t")) ||
            (shared.equalsIgnoreCase("yes")) ||
            (shared.equalsIgnoreCase("y"))) {
            cacheable.setShared(true);
        } else {
            cacheable.setShared(false);
        }

        // Set concrete portlet information
        List concreteApps = portletDef.getConcreteApps();
        it = concreteApps.iterator();
        concretePortlets = new Vector();
        while (it.hasNext()) {
            ConcretePortletApplication concApp = (ConcretePortletApplication)it.next();
            try {
                ConcretePortlet concretePortlet = new ConcretePortletImpl(portletApp, concApp, classLoader);
                concretePortlets.add(concretePortlet);
            } catch (ConcretePortletException e) {
                log.error("Unable to create concrete portlet: " + concApp.getName(), e);
            }
        }
        uid = portletApp.getUID();
        name = portletApp.getName();
    }

    /**
     * Return the PortletApplication, the portlet descriptor class that defines the portlet application
     *
     * @return the PortletApplication
     */
    public PortletApplication getPortletApplication() {
        return portletApp;
    }

    /**
     * Return the ConcretePortlets associated with this ApplicationPortlet
     *
     * @return the ConcretePortlets associated with this ApplicationPortlet
     */
    public List getConcretePortlets() {
        return concretePortlets;
    }

    /**
     * Return the ConcretePortlet associated with this ApplicationPortlet
     *
     * @param concretePortletID the concrete portlet ID associated with this ApplicationPortlet
     * @return the ConcretePortlet associated with this ApplicationPortlet with the provided concretePortletID
     * or null if no concrete portlet with the supplied ID exists
     */
    public ConcretePortlet getConcretePortlet(String concretePortletID) {
        Iterator it = concretePortlets.iterator();
        while (it.hasNext()) {
            ConcretePortlet c = (ConcretePortlet)it.next();
            if (c.getConcretePortletAppID().equals(concretePortletID)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Returns the id of a PortletApplication
     *
     * @returns the id of the PortletApplication
     */
    public String getPortletAppID() {
        return uid;
    }

    /**
     * Returns the name of a PortletApplication
     *
     * @returns name of the PortletApplication
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of portlet configuration parameters that are used in the PortletConfig class
     *
     * @return the list of portlet config parameters
     */
    //public PortletConfig getPortletConfig() {
    //    return portletConfig;
    //}

    /**
     * Returns the list of allowed portlet window states e.g. MINIMIZED, MAXIMIZED, RESIZING
     *
     * @return modes the list of allowed portlet window states
     * @see <code>PortletWindow.State</code>
     */
    //public List getAllowedPortletWindowStates() {
    //    return allowedStates;
    //}

    /**
     * Return the cacheable portlet info consisting of:
     * expires: -1 = never expires 0 = always expires # = number of seconds until expiration
     * shared: true if portlet output shared among all users or false if not
     */
    //public Cacheable getCacheablePortletInfo() {
    //    return cacheable;
    //}

    /**
     * Returns the list of supported portlet modes e.g. EDIT, VIEW, HELP, CONFIGURE
     *
     * @return modes the list of allowed portlet modes
     * @see <code>Portlet.Mode</code>
     */
    //public SupportsModes getSupportedPortletModes() {
    //    return supportedModes;
    //}
}
