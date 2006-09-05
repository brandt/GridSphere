/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ApplicationPortletImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.impl;

import org.gridsphere.portlet.PortletException;
import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portletcontainer.*;
import org.gridsphere.portletcontainer.impl.descriptor.ApplicationSportletConfig;
import org.gridsphere.portletcontainer.impl.descriptor.ConcreteSportletDefinition;
import org.gridsphere.portletcontainer.impl.descriptor.PortletDeploymentDescriptor;
import org.gridsphere.portletcontainer.impl.descriptor.SportletDefinition;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The <code>ApplicationPortletImpl</code> is an implementation of the <code>ApplicationPortlet</code> interface
 * that uses Castor for XML to Java data bindings.
 * <p/>
 * The <code>ApplicationPortlet</code> represents the portlet application instance
 * defined in the portlet descriptor file.
 */
class ApplicationPortletImpl implements ApplicationPortlet {

    private PortletLog log = SportletLog.getInstance(ApplicationPortletImpl.class);

    private String applicationPortletID = "";
    private String portletName = "";
    private String portletClass = "";
    private String servletName = "";
    private List concretePortlets = new ArrayList();
    private ApplicationSportletConfig appPortletConfig = null;
    private String webAppName = null;
    private PortletDispatcher portletDispatcher = null;

    protected PortletStatus status = PortletStatus.Success;
    protected String statusMessage = "GridSphere Portlet loaded successfully";

    /**
     * Default constructor is private
     */
    private ApplicationPortletImpl() {
    }

    /**
     * Constructs an instance of ApplicationPortletImpl
     *
     * @param pdd            the <code>PortletDeploymentDescriptor</code>
     * @param portletDef     a <code>SportletDefinition</code>
     * @param webApplication the ui application name for this application portlet
     * @param context        the <code>ServletContext</code> containing this application portlet
     */
    public ApplicationPortletImpl(PortletDeploymentDescriptor pdd, SportletDefinition portletDef,
                                  String webApplication, ServletContext context) throws PortletException {

        this.webAppName = webApplication;
        this.appPortletConfig = portletDef.getApplicationSportletConfig();

        // Set concrete portlet information
        List concPortletDefs = portletDef.getConcreteSportletList();
        Iterator it = concPortletDefs.iterator();
        while (it.hasNext()) {
            ConcreteSportletDefinition concSportlet = (ConcreteSportletDefinition) it.next();
            ConcretePortlet concretePortlet = new ConcreteSportlet(pdd, appPortletConfig, concSportlet);
            concretePortlets.add(concretePortlet);
        }
        portletClass = appPortletConfig.getApplicationPortletID();

        applicationPortletID = portletClass;
        portletName = appPortletConfig.getPortletName();
        servletName = appPortletConfig.getServletName();

        log.debug("Creating request dispatcher for " + servletName);
        RequestDispatcher rd = context.getNamedDispatcher(servletName);
        if (rd == null) {
            String msg = "Unable to create a dispatcher for portlet: " + portletName + "\n";
            msg += "Make sure the servletName: " + servletName + " is the servlet-name defined in web.xml";
            throw new PortletException(msg);
        }
        portletDispatcher = new SportletDispatcher(rd, appPortletConfig);
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
        this.appPortletConfig = (ApplicationSportletConfig) appPortletConfig;
    }

    /**
     * Returns a PortletDispatcher for this ApplicationPortlet
     *
     * @return PortletDispatcher the proxy portlet for this ApplicationPortlet
     */
    public PortletDispatcher getPortletDispatcher(PortletRequest req, PortletResponse res) {
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
     *         or null if no concrete portlet with the supplied ID exists
     */
    public ConcretePortlet getConcretePortlet(String concretePortletID) {
        Iterator it = concretePortlets.iterator();
        while (it.hasNext()) {
            ConcretePortlet c = (ConcretePortlet) it.next();
            if ((c.getConcretePortletID().equals(concretePortletID)) || (c.getPortletName().equals(concretePortletID))) {
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
     * Returns the portlet class
     *
     * @return the portlet class
     */
    public String getApplicationPortletClassName() {
        return portletClass;
    }

    public void setApplicationPortletStatus(PortletStatus status) {
        this.status = status;
    }

    public void setApplicationPortletStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public PortletStatus getApplicationPortletStatus() {
        return status;
    }

    public String getApplicationPortletStatusMessage() {
        return statusMessage;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\t Application Portlet:\n");
        sb.append("\t Portlet Name: " + portletName + "\n");
        sb.append("\t Servlet Name: " + servletName + "\n");
        sb.append("\t Web App Name: " + webAppName + "\n");
        sb.append("\t App Portlet ID: " + applicationPortletID + "\n");
        /*
        if (portletDispatcher == null) {
            sb.append("\t Portlet dispatcher: NULL");
        } else {
            sb.append("\t Portlet dispatcher: OK");
        }
        */
        return sb.toString();
    }
}
