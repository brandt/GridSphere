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
import org.gridlab.gridsphere.portletcontainer.RegisteredPortletException;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;

import javax.servlet.ServletConfig;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

/**
 * A RegisteredSportlet provides the portlet container with information used to create and manage the
 * portlet's lifecycle. A RegisteredPortlet is created given a PortletApplication and a corresponding
 * ConcretePortletApplication provided by the PortletDeploymentDescriptor. The RegisteredPortlet parses
 * the information provided and provides PortletSettings. The RegisteredPortlet also maintains an instantiated
 * portlet that is managed by the portlet container.
 */
public class RegisteredSportlet implements RegisteredPortlet {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(RegisteredSportlet.class);

    private AbstractPortlet abstractPortlet = null;
    private PortletSettings portletSettings = null;
    private String portletName = "Undefined PortletInfo";
    private String portletClass = "Unknown Portlet Class";
    private String appID = null;
    private String concreteID = null;

    public RegisteredSportlet(PortletApplication portletApp, ConcretePortletApplication concreteApp,
                              List knownGroups, List knownRoles) throws RegisteredPortletException {

        log.info("in RegisteredSportlet construcor");
        int index;
        String appname, cappname;

        // Get PortletApplication UID  e.g. classname.number
        appID = portletApp.getUID();
        index = appID.lastIndexOf(".");
        appname = appID.substring(0, index);
        String appNo = appID.substring(index+1);

        // Get ConcretePortletApplication UID e.g. classname.number.number
        concreteID = concreteApp.getUID();
        index = concreteID.lastIndexOf(".");
        String concreteNo = concreteID.substring(index+1);
        String cappNo = concreteID.substring(0, index);
        index = cappNo.lastIndexOf(".");
        cappNo = cappNo.substring(index+1);
        cappname = concreteID.substring(0, index);

        // Check that cappID = appID and cappname = appname
        if ((!appNo.equals(cappNo)) || (!appname.equals(cappname))) {
            log.error("<portlet-app uid=" + appname + appNo + " does not match <concrete-portlet-app uid=" + cappname + cappNo);
            throw new RegisteredPortletException("<portlet-app uid=" + appname + appNo + " does not match <concrete-portlet-app uid=" + cappname + cappNo);
        }

        portletClass = cappname;
        portletName = concreteApp.getName();

        try {
            abstractPortlet = (AbstractPortlet) Class.forName(portletClass).newInstance();
        } catch (Exception e) {
            log.error("Unable to create AbstractPortlet: " + portletClass, e);
            throw new RegisteredPortletException("Unable to create instance of portlet: " + portletClass);
        }

        portletSettings = new SportletSettings(concreteApp, knownGroups, knownRoles);
    }

    public String getPortletAppID() {
        return appID;
    }

    public String getConcretePortletAppID() {
        return concreteID;
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

    public String getPortletClass() {
        return portletClass;
    }

}
