/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortletException;
import org.gridlab.gridsphere.portletcontainer.PortletDispatcher;
import org.gridlab.gridsphere.portletcontainer.descriptor.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * An application portlet represents the portlet application defined in the portlet.xml
 * ApplicationPortlet is mostly a proxy for the PortletApplication class used by Castor
 *
 * @see <code>org.gridlab.gridsphere.portletcontainer.descriptor.PortletApplication</code>
 */
class ApplicationPortletImpl implements ApplicationPortlet {

    private PortletLog log = SportletLog.getInstance(ApplicationPortletImpl.class);

    private PortletDeploymentDescriptor portletDD = null;
    private String applicationPortletID = "";
    private String portletName = "";
    private String servletName = "";
    private Cacheable cacheable = null;
    private SupportsModes supportedModes = null;
    private List allowedStates = null;
    private List concretePortlets = null;
    private ApplicationPortletDescriptor appDescriptor = null;
    private ServletContext context;
    private String webAppName = null;
    private PortletDispatcher portletWrapper = null;

    public ApplicationPortletImpl(PortletDeploymentDescriptor pdd, PortletDefinition portletDef, String webApplication, ServletContext context) {
        this.portletDD = pdd;
        this.context = context;
        this.webAppName = webApplication;
        this.appDescriptor = portletDef.getApplicationPortletDescriptor();

        // Set cache information
        CacheInfo cacheInfo = appDescriptor.getCacheInfo();
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
        Iterator it = concreteApps.iterator();
        concretePortlets = new Vector();
        while (it.hasNext()) {
            ConcretePortletDescriptor concApp = (ConcretePortletDescriptor) it.next();
            try {
                ConcretePortlet concretePortlet = new ConcretePortletImpl(pdd, appDescriptor, concApp);
                concretePortlets.add(concretePortlet);
            } catch (ConcretePortletException e) {
                log.error("Unable to create concrete portlet: " + concApp.getConcretePortletInfo().getName(), e);
            }
        }
        applicationPortletID = appDescriptor.getID();
        portletName = appDescriptor.getPortletName();
        servletName = appDescriptor.getServletName();

        log.info("Creating request dispatcher for " + servletName);
        RequestDispatcher rd = context.getNamedDispatcher(servletName);
        if (rd == null) {
            log.error("Unable to create a dispatcher for portlet: " + portletName);
            log.error("Make sure the servletName: " + servletName + " is the servlet-name defined in web.xml");
        }
        portletWrapper = new PortletDispatcher(rd, appDescriptor);
    }

    /**
     * Return the web application name associated with this application portlet
     *
     * @return the web application name
     */
    public String getWebApplicationName() {
        return webAppName;
    }

    /**
     * Return the PortletApplication, the portlet descriptor class that defines the portlet application
     *
     * @return the PortletApplication
     */
    public ApplicationPortletDescriptor getApplicationPortletDescriptor() {
        return appDescriptor;
    }

    /**
     * Returns a PortletDispatcher for this ApplicationPortlet
     *
     * @return PortletDispatcher the proxy portlet for this ApplicationPortlet
     */
    public PortletDispatcher getPortletWrapper() {
        return portletWrapper;
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
            ConcretePortlet c = (ConcretePortlet) it.next();
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
        return applicationPortletID;
    }

    /**
     * Returns the name of a PortletApplication
     *
     * @returns name of the PortletApplication
     */
    public String getPortletName() {
        return portletName;
    }

    /**
     * Returns the name of a servlet associated with this portlet defined in web.xml as <servlet-name>
     *
     * @returns the servlet name
     */
    public String getServletName() {
        return servletName;
    }

    /**
     * Saves the supplied application portlet descriptor to serialize any changes that have been made
     *
     * @param appDescriptor the application portlet descriptor
     * @throws IOException if an I/O error ooccurs
     */
    public void saveDescriptor(ApplicationPortletDescriptor appDescriptor) throws IOException {
        this.appDescriptor = appDescriptor;
        portletDD.setApplicationPortletDescriptor(appDescriptor);
        try {
            portletDD.save();
        } catch (DescriptorException e) {
            log.error("Unable to save application portlet descriptor! " + applicationPortletID, e);
        }
    }

}
