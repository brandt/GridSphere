/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.ConfigParam;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletSettings;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortletException;
import org.gridlab.gridsphere.portletcontainer.descriptor.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * A ConcretePortlet provides the portlet container with information used to create and manage the
 * portlet's lifecycle. A ConcretePortlet is created given a PortletApplication and a corresponding
 * ConcretePortletDescriptor provided by the PortletDeploymentDescriptor. The ConcretePortlet parses
 * the information provided and provides PortletSettings. The ConcretePortlet also maintains an instantiated
 * portlet that is managed by the portlet container.
 */
class ConcretePortletImpl implements ConcretePortlet {

    private static PortletLog log = SportletLog.getInstance(ConcretePortletImpl.class);

    private PortletDeploymentDescriptor portletDD = null;
    private ConcretePortletDescriptor concDescriptor = null;
    private AbstractPortlet abstractPortlet = null;
    private Hashtable configHash = null;
    private Hashtable contextHash = null;
    private String servletName = "Undefined Servlet";
    private String portletName = "Undefined PortletInfo";
    private String portletClass = "Unknown Portlet Class";
    private String appID = null;
    private String concreteID = null;
    private AllowedAccess access = null;
    private List languageList = new Vector();
    private String defaultLocale = "en_US";
    private PortletGroup ownerGroup = SportletGroup.BASE;
    private PortletRole ownerRole = PortletRole.GUEST;
    private SportletSettings portletSettings = null;
    private ApplicationPortletDescriptor portletApp = null;

    /**
     * Create a ConcreteSportlet
     */
    public ConcretePortletImpl(PortletDeploymentDescriptor pdd, ApplicationPortletDescriptor appDescriptor, ConcretePortletDescriptor concDescriptor) throws ConcretePortletException {
        this.portletApp = appDescriptor;
        this.concDescriptor = concDescriptor;
        int index;
        Iterator it;
        String appname, cappname;

        // Get PortletApplication UID  e.g. classname.number
        appID = appDescriptor.getID();
        index = appID.lastIndexOf(".");
        appname = appID.substring(0, index);
        String appNo = appID.substring(index + 1);

        // Get ConcretePortletDescriptor UID e.g. classname.number.number
        concreteID = concDescriptor.getID();
        index = concreteID.lastIndexOf(".");
        String concreteNo = concreteID.substring(index + 1);
        String cappNo = concreteID.substring(0, index);
        index = cappNo.lastIndexOf(".");
        cappNo = cappNo.substring(index + 1);
        cappname = concreteID.substring(0, index);

        // Check that cappID = appID and cappname = appname
        if ((!appNo.equals(cappNo)) || (!appname.equals(cappname))) {
            log.error("<portlet-app uid=" + appname + appNo + " does not match <concrete-portlet-app uid=" + cappname + cappNo);
            throw new ConcretePortletException("<portlet-app uid=" + appname + appNo + " does not match <concrete-portlet-app uid=" + cappname + cappNo);
        }

        portletClass = cappname;
        portletName = concDescriptor.getConcretePortletInfo().getName();
        servletName = appDescriptor.getServletName();

        // Get PortletConfig params
        List contextList = concDescriptor.getContextParamList();
        contextHash = new Hashtable(contextList.size());
        it = contextList.iterator();
        while (it.hasNext()) {
            ConfigParam param = (ConfigParam) it.next();
            contextHash.put(param.getParamName(), param.getParamValue());
        }

        ConcretePortletInfo concPortInfo = concDescriptor.getConcretePortletInfo();

        // Get locale information
        defaultLocale = concPortInfo.getDefaultLocale();
        languageList = concPortInfo.getLanguageList();

        // Get PortletConfig params
        List configList = concPortInfo.getConfigParamList();
        configHash = new Hashtable(configList.size());
        it = configList.iterator();
        while (it.hasNext()) {
            ConfigParam param = (ConfigParam) it.next();
            configHash.put(param.getParamName(), param.getParamValue());
        }

        access = concPortInfo.getAllowedAccess();

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
     * Return the concrete portlet application that defines this concrete portlet in the portlet.xml
     * descriptor.
     *
     * @return the concrete portlet application
     */
    public ConcretePortletDescriptor getConcretePortletDescriptor() {
        return concDescriptor;
    }


    public String getConcretePortletAppID() {
        return concreteID;
    }

    /**
     * Returns the portlet config for this portlet
     *
     * @return the portlet config
     */
    public Hashtable getPortletConfigHash() {
        return configHash;
    }

    /**
     * Returns the map of portlet context parameters that are used in the PortletConfig class
     *
     * @return the map of portlet context parameters keys are variable name and values are variable values
     */
    public Hashtable getPortletContextHash() {
        return contextHash;
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
/*
    public AbstractPortlet getAbstractPortlet(PortletContext ctx, PortletRequest req, PortletResponse res) {
        RequestDispatcher rd = ctx.getNamedDispatcher(servletName);
        return new PortletDispatcher(portletApp, rd, req, res);
    }
    */

    public String getPortletClass() {
        return portletClass;
    }

    /**
     * Returns the allowed access defining who can access this portlet
     * NOTE: THIS IS NOT PART OF THE WPS PORTLET API 4.1
     *
     * @return the list of supported PortletGroup objects
     */
    public AllowedAccess getAllowedAccess() {
        return access;
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
     * Saves the supplied concrete portlet descriptor to serialize any changes that have been made
     *
     * @param concreterDescriptor the concrete portlet descriptor
     * @throws IOException if an I/O error ooccurs
     */
    public void saveDescriptor(ConcretePortletDescriptor concreteDescriptor) throws IOException {
        this.concDescriptor = concDescriptor;
        portletDD.setConcretePortletDescriptor(concreteDescriptor);
        try {
            portletDD.save();
        } catch (DescriptorException e) {
            log.error("Unable to save concrete portlet descriptor! " + concreteID, e);
        }
    }

}
