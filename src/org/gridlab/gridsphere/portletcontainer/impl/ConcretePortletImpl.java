/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portletcontainer.descriptor.*;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortletException;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;

import javax.servlet.ServletConfig;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A ConcreteSportlet provides the portlet container with information used to create and manage the
 * portlet's lifecycle. A ConcretePortlet is created given a PortletApplication and a corresponding
 * ConcretePortletApplication provided by the PortletDeploymentDescriptor. The ConcretePortlet parses
 * the information provided and provides PortletSettings. The ConcretePortlet also maintains an instantiated
 * portlet that is managed by the portlet container.
 */
public class ConcretePortletImpl implements ConcretePortlet {

    private static PortletLog log = SportletLog.getInstance(ConcretePortletImpl.class);

    private ConcretePortletApplication concreteApp = null;
    private AbstractPortlet abstractPortlet = null;
    private Map configMap = null;
    private Map contextMap = null;
    private String portletName = "Undefined PortletInfo";
    private String portletClass = "Unknown Portlet Class";
    private String appID = null;
    private String concreteID = null;
    private Owner owner;
    private List roleList = new Vector();
    private List groupList = new Vector();
    private List languageList = new Vector();
    private String defaultLocale = "en_US";
    private PortletGroup ownerGroup = SportletGroup.getBaseGroup();
    private PortletRole ownerRole = SportletRole.getGuestRole();
    private SportletSettings sportletSettings = null;

    /**
     * Create a ConcreteSportlet
     */
    public ConcretePortletImpl(PortletApplication portletApp, ConcretePortletApplication concreteApp, ClassLoader classLoader) throws ConcretePortletException {

        log.info("in ConcretePortletImpl construcor");

        int index;
        Iterator it;
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
            throw new ConcretePortletException("<portlet-app uid=" + appname + appNo + " does not match <concrete-portlet-app uid=" + cappname + cappNo);
        }

        portletClass = cappname;
        portletName = concreteApp.getName();

        log.info("creating new class: " + portletClass);
        try {
            Class cls =  classLoader.loadClass(portletClass);
            abstractPortlet = (AbstractPortlet)classLoader.loadClass(portletClass).newInstance();

            //abstractPortlet = (AbstractPortlet) Class.forName(portletClass).newInstance();
        } catch (Exception e) {
            log.error("Unable to create AbstractPortlet: " + portletClass, e);
            throw new ConcretePortletException("Unable to create instance of portlet: " + portletClass);
        }

         // SINCE ACL SERVICE DOESN"T WORK YET
        Vector knownGroups = new Vector();
        Vector knownRoles = new Vector();
        //knownGroups = aclService.getAllGroups();
        //knownRoles = aclService.getAllRoles();

        // Get PortletConfig params
        List contextList = concreteApp.getContextParamList();
        contextMap = new Hashtable(contextList.size());
        it = contextList.iterator();
        while (it.hasNext()) {
            ConfigParam param = (ConfigParam)it.next();
            contextMap.put(param.getParamName(), param.getParamValue());
        }

        ConcretePortletInfo concPortInfo = concreteApp.getConcretePortletInfo();

        // Get locale information
        defaultLocale = concPortInfo.getDefaultLocale();
        languageList = concPortInfo.getLanguageList();

        // Get PortletConfig params
        List configList = concPortInfo.getConfigParamList();
        configMap = new Hashtable(configList.size());
        it = configList.iterator();
        while (it.hasNext()) {
            ConfigParam param = (ConfigParam)it.next();
            configMap.put(param.getParamName(), param.getParamValue());
        }

        // Get groups list
        List groups = concPortInfo.getGroupList();

        // Make sure groups exist
        while (knownGroups.iterator().hasNext()) {
            PortletGroup pg = (PortletGroup)knownGroups.iterator().next();
            while (groups.iterator().hasNext()) {
                if (pg.getName().equalsIgnoreCase((String)groups.iterator().next())) {
                    groupList.add(pg);
                    break;
                }
            }
        }

        // groupList should at least contain BASE group if empty
        if (groupList.isEmpty()) {
            groupList.add(SportletGroup.getBaseGroup());
        }

        // Get roles list
        List roles = concPortInfo.getRoleList();
        // Make sure roles exist
        while (knownRoles.iterator().hasNext()) {
            PortletRole pr = (PortletRole)knownRoles.iterator().next();
            while (roles.iterator().hasNext()) {
                if (pr.getRoleName().equalsIgnoreCase((String)roles.iterator().next())) {
                    roleList.add(pr);
                    break;
                }
            }
        }

        // roleList should at least contain GUEST role if empty
        if (roleList.isEmpty()) {
            roleList.add(SportletRole.getGuestRole());
        }

        owner = concPortInfo.getOwner();
        String groupName = owner.getGroupName();
        it = knownGroups.iterator();

        while (it.hasNext()) {
            PortletGroup group = (PortletGroup)it.next();
            if (group.getName().equalsIgnoreCase(groupName)) {
                ownerGroup = group;
                break;
            }
        }

        String roleName = owner.getGroupName();
        it = knownRoles.iterator();

        while (it.hasNext()) {
            PortletRole role = (PortletRole)it.next();
            if (role.getRoleName().equalsIgnoreCase(groupName)) {
                ownerRole = role;
                break;
            }
        }

        owner.setGroup(ownerGroup);
        owner.setRole(ownerRole);

        sportletSettings = new SportletSettings(concreteApp);
    }

    /**
     * Returns the sportlet settings for this concrete portlet
     *
     * @return the sportlet settings
     */
    public SportletSettings getSportletSettings() {
        return sportletSettings;
    }

    /**
     * Return the concrete portlet application that defines this concrete portlet in the portlet.xml
     * descriptor.
     *
     * @return the concrete portlet application
     */
    public ConcretePortletApplication getConcretePortletApplication() {
        return concreteApp;
    }


    public String getConcretePortletAppID() {
        return concreteID;
    }

    /**
     * Returns the portlet config for this portlet
     *
     * @return the portlet config
     */
    public Map getPortletConfig() {
        return configMap;
    }

    /**
     * Returns the map of portlet context parameters that are used in the PortletConfig class
     *
     * @return the map of portlet context parameters keys are variable name and values are variable values
     */
    public Map getPortletContext() {
        return contextMap;
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
    public AbstractPortlet getAbstractPortlet() {
        return abstractPortlet;
    }

    public String getPortletClass() {
        return portletClass;
    }


    /**
     * Return the Owner of the concrete portlet that can reconfigure the settings
     *
     * @return the concrete portlet owner
     */
    public Owner getPortletOwner() {
        return owner;
    }

       /**
     * Returns the list of supported groups
     * NOTE: THIS IS NOT PART OF THE WPS PORTLET API 4.1
     *
     * @return the list of supported PortletGroup objects
     */
    public List getSupportedGroups() {
        return groupList;
    }

    /**
     * Returns the list of supported roles
     * NOTE: THIS IS NOT PART OF THE WPS PORTLET API 4.1
     *
     * @return the list of supported PortletRole objects
     */
    public List getSupportedRoles() {
        return roleList;
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

}
