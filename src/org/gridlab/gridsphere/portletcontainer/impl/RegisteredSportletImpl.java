/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDeploymentDescriptor;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletConfig;
import org.gridlab.gridsphere.portlet.impl.SportletSettings;
import org.gridlab.gridsphere.services.PortletRegistryService;

import javax.servlet.ServletConfig;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RegisteredSportletImpl implements RegisteredSportlet {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(RegisteredSportletImpl.class);

    private AbstractPortlet abstractPortlet = null;
    private PortletConfig portletConfig = null;
    private PortletSettings portletSettings = null;
    private String portletName = "Undefined Portlet";

    public RegisteredSportletImpl(ServletConfig servletConfig) throws Exception {
        configure(servletConfig);
    }

    /**
     * Configures the PortletConfig and PortletSettings objects from the portlet.xml file
     */
    protected synchronized void configure(ServletConfig config) throws Exception {
        FileInputStream fistream = null;
        log.info("configure() in Portlet");
        String appRoot = config.getServletContext().getRealPath("");
        String portletConfigFile = config.getInitParameter("portlet.xml");
        if (portletConfigFile == null) {
            portletConfigFile = "/WEB-INF/conf/portlet.xml";
        }
        String fullPath = appRoot + portletConfigFile;
        try {
            fistream = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            log.error("Can't find file: " + fullPath);
            throw new Exception("Unable to create registered portlet");
        }
        // Now parse portlet.xml and stick relevant info into portletSettings and portletConfig
        portletConfig = new SportletConfig(config);

        // Here is where we need to have a Portal Deployment Descriptor that is marshalled into an object
        //PortletDeploymentDescriptor pdd = new PortletDeploymentDescriptorImpl();
        //portletSettings = new SportletSettings(pdd);

        String portletClass = "org.gridlab.gridsphere.portlets.HelloWorld";
        try {
            abstractPortlet = (AbstractPortlet)Class.forName(portletClass).newInstance();
        } catch (Exception e) {
            log.error("Unable to create AbstractPortlet: " + portletClass, e);
            throw new Exception("Unable to create registered portlet");
        }
    }

    public PortletConfig getPortletConfig() {
        return portletConfig;
    }

    public void setPortletConfig(PortletConfig portletConfig) {
        this.portletConfig = portletConfig;
    }

    public PortletSettings getPortletSettings() {
        return portletSettings;
    }

    public void setPortletSettings(PortletSettings portletSettings) {
        this.portletSettings = portletSettings;
    }

    public String getPortletName() {
        return portletName;
    }

    public void setPortletName(String portletName) {
        this.portletName = portletName;
    }

    public AbstractPortlet getActivePortlet() {
        return abstractPortlet;
    }

    public void setActivePortlet(AbstractPortlet abstractPortlet) {
        this.abstractPortlet = abstractPortlet;
    }

}
