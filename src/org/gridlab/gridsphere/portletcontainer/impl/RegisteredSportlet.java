/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletConfig;
import org.gridlab.gridsphere.portlet.impl.SportletSettings;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletApplication;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDeploymentDescriptor;
import org.gridlab.gridsphere.portletcontainer.descriptor.ConcretePortletApplication;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;

import javax.servlet.ServletConfig;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

/**
 * A RegisteredSportlet provides the portlet container with information used to create and manage the
 * portlet's lifecycle. A RegisteredPortlet is responsible for parsing the portlet.xml file for
 * portlet settings and portlet configuration information. The RegisteredPortlet also maintains an instantiated
 * portlet that is managed by the portlet container.
 */
public class RegisteredSportlet implements RegisteredPortlet {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(RegisteredSportlet.class);

    private AbstractPortlet abstractPortlet = null;
    private PortletConfig portletConfig = null;
    private PortletSettings portletSettings = null;
    private String portletName = "Undefined PortletInfo";
  /*
    public RegisteredSportlet(ServletConfig servletConfig) throws Exception {
        configure(servletConfig);
    }
  */
    public RegisteredSportlet(PortletApplication portletApp, ConcretePortletApplication concreteApp) throws Exception {
        configure(portletApp, concreteApp);
    }

    protected synchronized void configure(PortletApplication portletApp, ConcretePortletApplication concreteApp) throws Exception {
        String portletClass = null;
        String uid = concreteApp.getUID();
        portletName = concreteApp.getName();
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
        String portletMappingFile = config.getInitParameter("portlet-mapping.xml");
        if (portletConfigFile == null) {
            portletConfigFile = "/WEB-INF/conf/portlet.xml";
        }
        if (portletMappingFile == null) {
            portletMappingFile = "/WEB-INF/conf/portlet-mapping.xml";
        }
        String portletFilePath = appRoot + portletConfigFile;
        String mappingFilePath = appRoot + portletMappingFile;

        // Now parse portlet.xml and stick relevant info into portletSettings and portletConfig
        portletConfig = new SportletConfig(config);

        // Create Access Control Service
        AccessControlService aclService = (AccessControlService)portletConfig.getContext().getService(AccessControlService.class);
        List allGroups = aclService.getAllGroups();
        List allRoles = aclService.getAllRoles();

        PortletDeploymentDescriptor pdd = new PortletDeploymentDescriptor(portletFilePath, mappingFilePath);

        //String portletClass = "org.gridlab.gridsphere.portlets.HelloWorld";
        Iterator portletApps = pdd.getPortletDef().iterator();
        while (portletApps.hasNext()) {
            ConcretePortletApplication portletApp = (ConcretePortletApplication) portletApps.next();

            // create SportletSettings for each <portlet-app> definition
            portletSettings = new SportletSettings(portletApp, allGroups, allRoles);
            String portletClass = portletApp.getUID();
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
