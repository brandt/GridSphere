/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.*;
import org.gridlab.gridsphere.portletcontainer.impl.descriptor.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

/**
 * The <code>ApplicationPortletImpl</code> is an implementation of the <code>ApplicationPortlet</code> interface
 * that uses Castor for XML to Java data bindings.
 * <p>
 * The <code>ApplicationPortlet</code> represents the portlet application instance
 * defined in the portlet descriptor file.
 */
class ApplicationPortletImpl implements ApplicationPortlet {

    private PortletLog log = SportletLog.getInstance(ApplicationPortletImpl.class);

    private String applicationPortletID = "";
    private String portletName = "";
    private String servletName = "";
    private List concretePortlets = new ArrayList();
    private ApplicationPortletConfig appPortletConfig = null;
    private String webAppName = null;
    private PortletDispatcher portletDispatcher = null;

    /**
     * Default constructor is private
     */
    private ApplicationPortletImpl() {}

    /**
     * Constructs an instance of ApplicationPortletImpl
     *
     * @param pdd the <code>PortletDeploymentDescriptor</code>
     * @param portletDef a <code>SportletDefinition</code>
     * @param webApplication the ui application name for this application portlet
     * @param context the <code>ServletContext</code> containing this application portlet
     */
    public ApplicationPortletImpl(PortletDeploymentDescriptor pdd, SportletDefinition portletDef,
                                  String webApplication, ServletContext context) throws PortletException {

        this.webAppName = webApplication;
        this.appPortletConfig = portletDef.getApplicationSportletConfig();

        // Set cache information
        /*
        CacheInfo cacheInfo = appDescriptor.getCacheInfo();
        if (cacheInfo == null) {
            cacheInfo = new CacheInfo("true", -1);
        }
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
        }*/

        // Set concrete portlet information
        List concPortletDefs = portletDef.getConcreteSportletList();
        Iterator it = concPortletDefs.iterator();
        while (it.hasNext()) {
            ConcreteSportletDefinition concSportlet = (ConcreteSportletDefinition) it.next();
            ConcretePortlet concretePortlet = new ConcreteSportlet(pdd, appPortletConfig, concSportlet);
            concretePortlets.add(concretePortlet);
        }
        applicationPortletID = appPortletConfig.getApplicationPortletID();
        portletName = appPortletConfig.getPortletName();
        servletName = appPortletConfig.getServletName();

        log.debug("Creating request dispatcher for " + servletName);
        RequestDispatcher rd = context.getNamedDispatcher(servletName);
        if (rd == null) {
            String msg = "Unable to create a dispatcher for portlet: " + portletName + "\n";
            msg += "Make sure the servletName: " + servletName + " is the servlet-name defined in web.xml";
            log.error(msg);
            throw new PortletException(msg);
        }
        portletDispatcher = new PortletDispatcher(rd, appPortletConfig);
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
    public ApplicationPortletConfig getApplicationPortletConfig() {
        return appPortletConfig;
    }

    /**
     * Sets the PortletApplication, the portlet descriptor class that defines the portlet application
     *
     * @param appPortletConfig the PortletApplication
     */
    public void setApplicationPortletConfig(ApplicationPortletConfig appPortletConfig) {
        this.appPortletConfig = appPortletConfig;
    }

    /**
     * Returns a PortletDispatcher for this ApplicationPortlet
     *
     * @return PortletDispatcher the proxy portlet for this ApplicationPortlet
     */
    public PortletDispatcher getPortletDispatcher() {
        return portletDispatcher;
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
            if (c.getConcretePortletID().equals(concretePortletID)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Returns the id of a PortletApplication
     *
     * @return the id of the PortletApplication
     */
    public String getApplicationPortletID() {
        return applicationPortletID;
    }

    /**
     * Returns the name of a PortletApplication
     *
     * @return name of the PortletApplication
     */
    public String getApplicationPortletName() {
        return portletName;
    }

    /**
     * Returns the name of a servlet associated with this portlet defined in ui.xml as <servlet-name>
     *
     * @return the servlet name
     */
    public String getServletName() {
        return servletName;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Application Portlet:\n");
        sb.append("Portlet Name: " + portletName + "\n");
        sb.append("Servlet Name: " + servletName + "\n");
        sb.append("App Portlet ID: " + applicationPortletID + "\n");
        if (portletDispatcher == null) {
            sb.append("Portlet dispatcher: NULL");
        } else {
            sb.append("Portlet dispatcher: OK");
        }
        return sb.toString();
    }
}
