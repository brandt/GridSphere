/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortletException;
import org.gridlab.gridsphere.portletcontainer.impl.RegisteredSportlet;
import org.gridlab.gridsphere.portletcontainer.descriptor.*;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container, layout manager and any other services that require an active portlet.
 * The PortletInfo base class is responsible for reading in the associated portlet.xml file and
 * creating a RegisteredPortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of RegisteredPortlet objects.
 */
public class PortletRegistryServiceImpl implements PortletRegistryService, PortletServiceProvider {

    private static PortletServiceFactory factory = SportletServiceFactory.getInstance();
    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletRegistryServiceImpl.class);

    private static Map allPortlets = new Hashtable();

    /**
     * The init method is responsible for parsing portlet.xml and creating RegisteredPortlet objects based on the
     * entries. The RegisteredPortlets are managed by the PortletRegistryService.
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.info("in init()");

        // Must have latest role and group information from ACL service
        List knownGroups, knownRoles;
        try {
            AccessControlService aclService = (AccessControlService) factory.createPortletService(AccessControlService.class, config.getServletConfig(), true);
            knownGroups = aclService.getAllGroups();
            knownRoles = aclService.getAllRoles();
        } catch (PortletServiceException e) {
            throw new PortletServiceUnavailableException("Unable to get instance of AccessControlService");
        }

        // SINCE ACL SERVICE DOESN"T WORK YET
        knownGroups = new Vector();
        knownRoles = new Vector();

        // Now parse portlet.xml and place into PortletDeploymentDescriptor
        PortletDeploymentDescriptor pdd = null;
        try {
            pdd = new PortletDeploymentDescriptor(config.getServletConfig());
        } catch (PortletDeploymentDescriptorException e) {
            throw new PortletServiceUnavailableException("Unable to create PortletDeploymentDescriptor: " + e.getMessage());
        }

        // Every PortletDefinition has a PortletApplication and possibly multiple ConcretePortletApplication's
        Iterator portletDefs = pdd.getPortletDef().iterator();
        try {
        while (portletDefs.hasNext()) {
            PortletDefinition portletDef = (PortletDefinition) portletDefs.next();
            PortletApplication portletApp = portletDef.getPortletApp();
            Iterator concreteIt = portletDef.getConcreteApps().iterator();
            while (concreteIt.hasNext()) {
                RegisteredPortlet registeredPortlet = new RegisteredSportlet(portletApp, (ConcretePortletApplication)concreteIt.next(),
                        knownGroups, knownRoles);

                // use the portlet app ID as the portlet ID
                String portletID = registeredPortlet.getPortletAppID();
                log.info("id: " + portletID);
                if (allPortlets.containsKey(portletID)) {
                    log.info("another concrete");
                    List v = (Vector)allPortlets.get(portletID);
                    v.add(registeredPortlet);
                } else {
                    log.info("creating concrete");
                    List v = new Vector();
                    v.add(registeredPortlet);
                    allPortlets.put(portletID, v);
                }
            }
        }


        } catch (RegisteredPortletException e) {
                throw new PortletServiceUnavailableException("Unable to create registered portlet: " + e.getMessage());
        }
    }

    public void destroy() {
        log.info("in destroy()");
    }

    /**
     * Returns the collection of registered portlets
     *
     * @return the registered portlets
     */
    public List getRegisteredPortlets() {
        List r = new Vector();
        Iterator it = allPortlets.keySet().iterator();
        while (it.hasNext()) {
            String id = (String)it.next();
            List l = (List)allPortlets.get(id);
            Iterator newIt = l.iterator();
            r.addAll(l);
        }
        return r;
    }

    public AbstractPortlet getActivePortlet(String concretePortletID) {
        int index = concretePortletID.lastIndexOf(".");
        String appID = concretePortletID.substring(0, index);
        if (allPortlets.containsKey(appID)) {
            List l = (Vector)allPortlets.get(appID);
            Iterator it = l.iterator();
            while (it.hasNext()) {
                RegisteredPortlet p = (RegisteredPortlet)it.next();
                if (p.getConcretePortletAppID().equals(concretePortletID)) {
                    return p.getActivePortlet();
                }
            }
        }
        return null;
    }

    /**
     * Return the collection of portlets identified by the allowed Groups
     *
     * @param group the PortletGroup
     * @return the collection of portlets identified by the allowed Groups
     */
    public Collection getRegisteredPortletsByGroup(PortletGroup group) {
        return null;
    }

    /**
     * Return the collection of portlets identified by the allowed Roles
     *
     * @param role the PortletRole
     * @return the collection of portlets identified by the allowed Roles
     */
    public Collection getRegisteredPortletsbyRole(PortletRole role) {
        return null;
    }

    /**
     * Return a registered portlet given its identifier
     *
     * @return the portletID
     */
    public RegisteredPortlet getRegisteredPortlet(String concreteID) {
        int index = concreteID.lastIndexOf(".");
        String appID = concreteID.substring(0, index);
        if (allPortlets.containsKey(appID)) {
            List v = (Vector)allPortlets.get(appID);
            while (v.iterator().hasNext()) {
                RegisteredPortlet p = (RegisteredPortlet)v.iterator().next();
                if (p.getConcretePortletAppID().equals(concreteID)) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Registers a portlet with the PortletRegistryService
     *
     * @param registeredPortlet the registered portlet
     * @return  the concrete portlet ID of the registered portlet
     */
    public String registerPortlet(RegisteredPortlet registeredPortlet) {
        String portletAppID = registeredPortlet.getPortletAppID();
        if (allPortlets.containsKey(portletAppID)) {
            List v = (Vector)allPortlets.get(portletAppID);
            v.add(registeredPortlet);
        } else {
            List v = new Vector();
            v.add(registeredPortlet);
            allPortlets.put(portletAppID, v);
        }
        String concreteID = registeredPortlet.getConcretePortletAppID();
        log.info("Registering portlet: " + concreteID + " name: " + registeredPortlet.getPortletName());
        return concreteID;
    }

    /**
     * Unregisters a portlet with the PortletRegistryService
     *
     * @param portletID the concrete portlet app ID
     */
    public void unregisterPortlet(String concretePortletID) {
        log.info("Unregistering portlet: " + concretePortletID);
        int index = concretePortletID.lastIndexOf(".");
        String portletAppID = concretePortletID.substring(0, index);
        if (allPortlets.containsKey(portletAppID)) {
            List l = (Vector)allPortlets.get(portletAppID);
            Iterator it = l.iterator();
            while (it.hasNext()) {
                RegisteredPortlet p = (RegisteredPortlet)it.next();
                if (p.getConcretePortletAppID().equals(concretePortletID)) {
                    l.remove(p);
                    break;
                }
            }
        }
    }

}
