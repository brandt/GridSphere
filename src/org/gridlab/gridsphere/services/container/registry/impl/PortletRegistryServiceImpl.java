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
import org.gridlab.gridsphere.portletcontainer.*;
import org.gridlab.gridsphere.portletcontainer.impl.ConcretePortletImpl;
import org.gridlab.gridsphere.portletcontainer.impl.ApplicationPortletImpl;
import org.gridlab.gridsphere.portletcontainer.descriptor.*;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryService;
import org.gridlab.gridsphere.services.container.registry.PortletRegistryServiceException;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.*;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container, layout manager and any other services that require an active portlet.
 * The PortletInfo base class is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public class PortletRegistryServiceImpl implements PortletRegistryService {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletRegistryServiceImpl.class);

    private static PortletRegistryService registryService = null;

    /**
     * The registry structure is contained in the following Map:
     *  "localhost" contains all local portlets
     *      ApplicationPortlets
     *          ConcretePortlets
     *
     *  "remote IP" contains portlets that are registered from remote IP
     */
    private static Map allPortlets = new Hashtable();

    /**
     * The init method is responsible for parsing portlet.xml and creating ConcretePortlet objects based on the
     * entries. The RegisteredPortlets are managed by the PortletRegistryService.
     */
    public PortletRegistryServiceImpl() throws PortletRegistryServiceException {
        log.info("in init()");

        // Must have latest role and group information from ACL service
        /*
        AccessControlService aclService = null;
        List knownGroups, knownRoles;
        try {
            aclService = (AccessControlService) factory.createPortletService(AccessControlService.class, config.getServletConfig(), true);
        } catch (PortletServiceException e) {
            throw new PortletServiceUnavailableException("Unable to get instance of AccessControlService");
        }
        */

        loadPortlets();
    }

    public void loadPortlets() throws PortletRegistryServiceException {
        // Now parse portlet.xml and place into PortletDeploymentDescriptor
        PortletDeploymentDescriptor pdd = null;
        try {
            pdd = new PortletDeploymentDescriptor();
        } catch (PortletDeploymentDescriptorException e) {
            throw new PortletRegistryServiceException("Unable to create PortletDeploymentDescriptor: " + e.getMessage());
        } catch (IOException e) {
            throw new PortletRegistryServiceException("IO error creating PortletDeploymentDescriptor: " + e.getMessage());
        }

        // Every PortletDefinition has a PortletApplication and possibly multiple ConcretePortletApplication's
        Iterator portletDefs = pdd.getPortletDef().iterator();

        Map localPortlets = new Hashtable();

        String webapp = GridSphereConfig.getInstance().getProperty(GridSphereConfigProperties.GRIDSPHERE_WEBAPP);

        // Create separate classloader for portlets
        // Get the directory (URL) of the reloadable class
        URL[] urls = null;
        ClassLoader thisClassLoader = PortletRegistryServiceImpl.class.getClassLoader();
        try {
            // Convert the file object to a URL
            //File dir = new File(webapp + "/WEB-INF/lib/portlets/coreportlets.jar");
            File dir = new File("/Users/novotny/gridsphere/build/lib/portlets/coreportlets.jar");
            File dir2 = new File("/Users/novotny/gridsphere/build/lib/portlet-api.jar");
            File dir3 = new File("/Users/novotny/Jakarta/jakarta-tomcat-4.0.3/common/lib/servlet.jar");
            File dir4 = new File("/Users/novotny/gridsphere/lib/log4j-1.2.7.jar");
            System.err.println("reading in : " + dir.getAbsolutePath());
            URL url = dir.toURL();
            URL url2 = dir2.toURL();
            URL url3 = dir3.toURL();
            URL url4 = dir4.toURL();
            urls = new URL[]{url};
        } catch (MalformedURLException e) {
            log.error("Unable to create URL for classloader: ", e);
        }
        ClassLoader classLoader = null;
        try {
            // Create a new class loader with the directory
            classLoader = new URLClassLoader(urls, thisClassLoader);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        while (portletDefs.hasNext()) {
            PortletDefinition portletDef = (PortletDefinition) portletDefs.next();

            ApplicationPortlet portletApp = new ApplicationPortletImpl(portletDef, classLoader);
            String portletAppID = portletApp.getPortletAppID();
            /*
            Iterator concreteIt = portletApp.getConcretePortlets().iterator();

            while (concreteIt.hasNext()) {
            ConcretePortlet concretePortlet = (ConcretePortlet)concreteIt.next();
            // use the portlet app ID as the portlet ID
            String portletID = concretePortlet.getConcretePortletAppID();
            log.info("id: " + portletID);
            if (localPortlets.containsKey(portletID)) {
            log.info("another concrete");
            List v = (Vector)localPortlets.get(portletID);
            v.add(concretePortlet);
            } else {
            log.info("creating concrete");
            List v = new Vector();
            v.add(concretePortlet);
            localPortlets.put(portletID, v);
            }
            }
            */
            localPortlets.put(portletAppID, portletApp);
        }
        allPortlets.put("localhost", localPortlets);
    }

    public static PortletRegistryService getInstance() throws PortletRegistryServiceException {
        if (registryService == null) registryService = new PortletRegistryServiceImpl();
        return registryService;
    }

    /**
     * Returns the collection of application portlets
     *
     * @return the application portlets
     */
    public List getApplicationPortlets() {
        List portletsAppsList = new Vector();
        Iterator it = allPortlets.keySet().iterator();
        while (it.hasNext()) {
            String registryID = (String) it.next();
            Map groupPortlets = (Map) allPortlets.get(registryID);
            Iterator newIt = groupPortlets.keySet().iterator();
            while (newIt.hasNext()) {
                String portletAppID = (String) newIt.next();
                ApplicationPortlet appPortlet = (ApplicationPortlet) groupPortlets.get(portletAppID);
                portletsAppsList.add(appPortlet);
            }
        }
        return portletsAppsList;
    }

    /**
     * Return a application portlet given its identifier
     *
     * @param applicationPortletID the application portlet ID
     * @return the application portlet
     */
    public ApplicationPortlet getApplicationPortlet(String applicationPortletID) {
        Iterator it = allPortlets.keySet().iterator();
        while (it.hasNext()) {
            String registryID = (String) it.next();
            Map groupPortlets = (Map) allPortlets.get(registryID);
            Iterator newIt = groupPortlets.keySet().iterator();
            while (newIt.hasNext()) {
                String portletAppID = (String) newIt.next();
                if (portletAppID.equals(applicationPortletID)) {
                    return (ApplicationPortlet) groupPortlets.get(portletAppID);
                }
            }
        }
        return null;
    }

    /**
     * Returns the collection of concrete portlets
     *
     * @return the concrete portlets
     */
    public List getConcretePortlets() {
        List r = new Vector();
        Iterator it = allPortlets.keySet().iterator();
        while (it.hasNext()) {
            // get registry ID
            String registryID = (String) it.next();
            Map portletGroup = (Map) allPortlets.get(registryID);
            Iterator newIt = portletGroup.keySet().iterator();
            while (newIt.hasNext()) {
                String portletAppID = (String) newIt.next();
                ApplicationPortlet appPortlet = (ApplicationPortlet) portletGroup.get(portletAppID);
                List l = appPortlet.getConcretePortlets();
                r.addAll(l);
            }
        }
        return r;
    }

    /**
     * Returns the collection of locally managed registered portlets
     *
     * @return the local concrete portlets
     */
    public List getLocalConcretePortlets() {
        List r = new Vector();
        Map localPortlets = (Map) allPortlets.get("localhost");
        Iterator it = localPortlets.keySet().iterator();
        while (it.hasNext()) {
            String portletAppID = (String) it.next();
            ApplicationPortlet appPortlet = (ApplicationPortlet) localPortlets.get(portletAppID);
            List l = appPortlet.getConcretePortlets();
            r.addAll(l);
        }
        return r;
    }

    public AbstractPortlet getActivePortlet(String concretePortletID) {
        int index = concretePortletID.lastIndexOf(".");
        String portletAppID = concretePortletID.substring(0, index);
        Iterator it = allPortlets.keySet().iterator();
        while (it.hasNext()) {
            String registryID = (String) it.next();
            Map groupPortlets = (Map) allPortlets.get(registryID);
            if (groupPortlets.containsKey(portletAppID)) {
                ApplicationPortlet appPortlet = (ApplicationPortlet) groupPortlets.get(portletAppID);
                ConcretePortlet concPortlet = appPortlet.getConcretePortlet(concretePortletID);
                if (concPortlet != null) {
                    return concPortlet.getAbstractPortlet();
                }
            }
        }
        return null;
    }

    public List getActivePortlets(List concretePortletIDs) {
        List active = new Vector();
        Iterator it = concretePortletIDs.iterator();
        while (it.hasNext()) {
            String concretePortletID = (String) it.next();
            AbstractPortlet ab = getActivePortlet(concretePortletID);
            if (ab != null) active.add(ab);
        }
        return active;
    }

    /**
     * Return a registered portlet given its identifier
     *
     * @return the portletID
     */
    public ConcretePortlet getConcretePortlet(String concreteID) {
        int index = concreteID.lastIndexOf(".");
        String appID = concreteID.substring(0, index);
        Iterator registries = allPortlets.keySet().iterator();
        while (registries.hasNext()) {
            String registryID = (String) registries.next();
            Map groupPortlets = (Map) allPortlets.get(registryID);
            if (groupPortlets.containsKey(appID)) {
                ApplicationPortlet appPortlet = (ApplicationPortlet) groupPortlets.get(appID);
                List concPortlets = appPortlet.getConcretePortlets();
                Iterator it = concPortlets.iterator();
                while (it.hasNext()) {
                    ConcretePortlet p = (ConcretePortlet) it.next();
                    if (p.getConcretePortletAppID().equals(concreteID)) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Registers a portlet with the PortletRegistryService
     *
     * @param registryID the string identifier for this ApplicationPortlet
     * @param applicationPortlet the application portlet
     * @return the application portlet ID of the application portlet
     */
    public String registerApplicationPortlet(String registryID, ApplicationPortlet applicationPortlet) {
        String portletAppID = applicationPortlet.getPortletAppID();
        Map groupPortlets = null;
        if (allPortlets.containsKey(registryID)) {
            groupPortlets = (Map) allPortlets.get(registryID);
        } else {
            groupPortlets = new Hashtable();
        }
        groupPortlets.put(applicationPortlet.getPortletAppID(), applicationPortlet);
        allPortlets.put(registryID, groupPortlets);
        log.info("Registering portlet application: " + portletAppID + " name: " + applicationPortlet.getName());
        return portletAppID;
    }

    /**
     * Registers a list of application portlets with the PortletRegistryService
     *
     * @param registryID the string identifier for this ApplicationPortlet
     * @param a List containing ApplicationPortlet components
     * @return the portletID
     */
    public void registerApplicationPortlets(String registryID, List applicationPortlets) {
        Iterator it = applicationPortlets.iterator();
        while (it.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) it.next();
            registerApplicationPortlet(registryID, appPortlet);
        }
    }

    /**
     * Unregisters a portlet with the PortletRegistryService
     *
     * @param registryID the string identifier for this ApplicationPortlet
     * @param portletID the application portlet ID
     */
    public void unregisterApplicationPortlet(String registryID, String portletAppID) {
        log.info("Unregistering portlet application: " + portletAppID);
        if (allPortlets.containsKey(registryID)) {
            Map groupPortlets = (Map) allPortlets.get(registryID);
            if (groupPortlets.containsKey(portletAppID)) {
                groupPortlets.remove(portletAppID);
            }
        }
    }

}
