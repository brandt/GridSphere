/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portlet.impl.SportletConfig;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletApplication;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDeploymentDescriptor;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;

import javax.servlet.ServletConfig;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * A RegisteredSportlet provides the portlet container with information used to create and manage the
 * portlet's lifecycle. A RegsiteredPortlet is responsible for parsing the portlet.xml file for
 * portlet settings and portlet configuration information. The RegisteredPortlet also maintains an instantiated
 * portlet that is managed by the portlet container.
 */
public class RegisteredSportlet implements RegisteredPortlet {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(RegisteredSportlet.class);

    private AbstractPortlet abstractPortlet = null;
    private PortletConfig portletConfig = null;
    private PortletSettings portletSettings = null;
    private String portletName = "Undefined PortletInfo";

    public RegisteredSportlet(ServletConfig servletConfig) throws Exception {
        configure(servletConfig);
    }

    public RegisteredSportlet(PortletApplication app) throws Exception {
        configure(app);
    }

    protected synchronized void configure(PortletApplication app) throws Exception {
        String portletClass = null;
        String uid = app.getUid();
        portletName = app.getName();
        try {
            abstractPortlet = (AbstractPortlet) Class.forName(uid).newInstance();
        } catch (Exception e) {
            log.error("Unable to create AbstractPortlet: " + portletClass, e);
            throw new Exception("Unable to create registered portlet");
        }
    }

    /**
     * Configures the PortletConfig and PortletSettings objects from the portlet.xml file
     */
    protected synchronized void configure(ServletConfig config) throws Exception {
        FileInputStream fistream = null;
        log.info("configure() in PortletInfo");
        String appRoot = config.getServletContext().getRealPath("");
        String portletConfigFile = config.getInitParameter("portlet.xml");
        String portletMappingFile = config.getInitParameter("portlet-mapping-file.xml");
        if (portletConfigFile == null) {
            portletConfigFile = "/WEB-INF/conf/portlet.xml";
        }
        if (portletMappingFile == null) {
            portletMappingFile = "/WEB-INF/conf/portletdefinition-mapping.xml";
        }
        String fullPath = appRoot + portletConfigFile;
        String mappingPath = appRoot + portletMappingFile;
        try {
            fistream = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            log.error("Can't find file: " + fullPath);
            throw new Exception("Unable to create registered portlet");
        }
        // Now parse portlet.xml and stick relevant info into portletSettings and portletConfig
        portletConfig = new SportletConfig(config);

        // Here is where we need to have a Portal Deployment Descriptor that is marshalled into an object
        String xmlFile = config.getInitParameter("portlet.xml");

        PortletDeploymentDescriptor pdd = new PortletDeploymentDescriptor(fullPath, mappingPath);
        //portletSettings = new SportletSettings(pdd);

        //String portletClass = "org.gridlab.gridsphere.portlets.HelloWorld";
        Iterator portletApps = pdd.getPortletApp().iterator();
        while (portletApps.hasNext()) {
            PortletApplication portletApp = (PortletApplication) portletApps.next();
            String portletClass = portletApp.getUid();
            portletName = portletApp.getName();
            try {
                abstractPortlet = (AbstractPortlet) Class.forName(portletClass).newInstance();
            } catch (Exception e) {
                log.error("Unable to create AbstractPortlet: " + portletClass, e);
                throw new Exception("Unable to create registered portlet");
            }
        }
    }

    /**
     * Returns the portlet configuration for this portlet
     *
     * @return the portlet configuration
     */
    public PortletConfig getPortletConfig() {
        return portletConfig;
    }

    /**
     * Returns the portlet settings for this portlet
     *
     * @return the portlet settings
     */
    public PortletSettings getPortletSettings() {
        return portletSettings;
    }

    /**
     * Return the name of this portlet
     *
     * @return the portlet name
     */
    public String getPortletName() {
        return portletName;
    }

    /**
     * Return the instantiated abstract portlet instance
     *
     * @return the instantiated abstract portlet instance
     */
    public AbstractPortlet getActivePortlet() {
        return abstractPortlet;
    }

}
