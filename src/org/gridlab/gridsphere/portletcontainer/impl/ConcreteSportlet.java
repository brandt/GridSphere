/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletSettings;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortletConfig;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortletConfig;
import org.gridlab.gridsphere.portletcontainer.impl.descriptor.PortletDeploymentDescriptor;
import org.gridlab.gridsphere.portletcontainer.impl.descriptor.ConcreteSportletDefinition;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * The <code>ConcreteSportlet</code> is an implementation of the <code>ConcretePortlet</code> that provides methods
 * for accessing concrete portlet objects obtained from the portlet deployment descriptors.
 */
class ConcreteSportlet implements ConcretePortlet {

    private static PortletLog log = SportletLog.getInstance(ConcreteSportlet.class);

    private PortletDeploymentDescriptor portletDD = null;
    private ConcretePortletConfig concSportletConfig = null;
    private Hashtable contextHash = null;
    private String portletName = "Undefined PortletInfo";
    private String concreteID = null;
    private List languageList = null;
    private String defaultLocale = "en_US";
    private SportletSettings portletSettings = null;

    /**
     * Constructs an instance of ConcreteSportlet
     *
     * @param appPortletConfig an application portlet configuration
     * @param concSportlet a concrete portlet descriptor
     */
    public ConcreteSportlet(ApplicationPortletConfig appPortletConfig, ConcreteSportletDefinition concSportlet) {
        this.concSportletConfig = concSportlet.getConcreteSportletConfig();
        String appID, appname, cappname;
        int index;

        // Get PortletApplication UID  e.g. classname.number
        appID = appPortletConfig.getApplicationPortletID();
        index = appID.lastIndexOf(".");
        appname = appID.substring(0, index);
        String appNo = appID.substring(index + 1);

        concreteID = concSportlet.getConcretePortletID();

        // Get ConcretePortletConfig UID e.g. classname.number.number
        index = concreteID.lastIndexOf(".");
        String cappNo = concreteID.substring(0, index);
        index = cappNo.lastIndexOf(".");
        cappNo = cappNo.substring(index + 1);
        cappname = concreteID.substring(0, index);

        // Check that cappID = appID and cappname = appname
        if ((!appNo.equals(cappNo)) || (!appname.equals(cappname))) {
            log.error("<portlet-app uid=" + appname + appNo + " does not match <concrete-portlet-app uid=" + cappname + cappNo);
        }
        portletName = concSportletConfig.getName();

        contextHash = concSportlet.getContextAttributes();

        // Get locale information
        defaultLocale = concSportletConfig.getDefaultLocale();
        languageList = concSportletConfig.getLanguageList();
        portletSettings = new SportletSettings(this);
    }

    /**
     * Returns the sportlet settings for this concrete portlet
     *
     * @return the sportlet settings
     */
    public PortletSettings getPortletSettings() {
        return portletSettings;
    }

    /**
     * Return the concrete portlet configuration
     *
     * @return the concrete portlet configuration
     */
    public ConcretePortletConfig getConcretePortletConfig() {
        return concSportletConfig;
    }

    /**
     * Sets the concrete portlet configuration
     *
     * @param concPortletConfig the concrete portlet configuration
     */
    public void setConcretePortletConfig(ConcretePortletConfig concPortletConfig) {
        this.concSportletConfig = concPortletConfig;
    }

    /**
     * Returns the concrete portlet id
     *
     * @return the concrete portlet id
     */
    public String getConcretePortletID() {
        return concreteID;
    }

    /**
     * Returns the portlet context attributes that are used in the
     * <code>PortletApplicationSettings</code> class
     *
     * @return the <code>Hashtable</code> containing portlet context attributes
     */
    public Hashtable getContextAttributes() {
        return contextHash;
    }

    /**
     * Sets the portlet context attributes that are used in the
     * <code>PortletApplicationSettings</code> class
     *
     * @param contextHash the Hashtable containing portlet context attributes
     */
    public void setContextAttributes(Hashtable contextHash) {
        this.contextHash = contextHash;
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
     * gets the default locale of a portlet
     *
     * @return the default locale of the portlet
     */
    public String getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * Returns the language info of a portlet
     *
     * @return language info of the portlet
     */
    public List getLanguageList() {
        return languageList;
    }

    /**
     * Saves any concrete portlet changes to the descriptor
     *
     * @throws IOException if an I/O error occurs
     */
    public void save() throws IOException {
        try {
            portletDD.setConcretePortlet(this);
            portletDD.save();
        } catch (DescriptorException e) {
            log.error("Unable to save concrete portlet descriptor! " + concreteID, e);
        }
    }

}
