/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceDescriptor;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceCollection;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceDefinition;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;

import javax.servlet.ServletConfig;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The PortletServiceCacheFactory provides a singleton factory to create portlet services.
 *
 * Uses the double-check locking variant of the singleton pattern to allow for concurrent threads.
 */
public class SportletServiceFactory implements PortletServiceFactory {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(SportletServiceFactory.class);

    private static SportletServiceFactory instance = new SportletServiceFactory();

    // Maintain a single copy of each service instantiated
    // as a classname and PortletServiceProvider pair
    private Hashtable initServices = new Hashtable();

    // Hash of all services key = service interface name, value = SportletServiceDefinition
    private Hashtable allServices = new Hashtable();

    private String ServiceMappingPath;

    /**
     * Private constructor. Use getInstance() instead.
     */
    private SportletServiceFactory() {
        // Reads in the service definitions from the xml file and stores them in allServices
        // organized according to service interface keys and service definition values
        GridSphereConfig gsc = GridSphereConfig.getInstance();
        String servicesPath = gsc.getProperty(GridSphereConfigProperties.PORTLET_SERVICES_XML);
        ServiceMappingPath = gsc.getProperty(GridSphereConfigProperties.PORTLET_SERVICES_MAPPING_XML);
        addServices(servicesPath, ServiceMappingPath);
    }

    private void addServices(String servicesPath, String mappingPath) {
        SportletServiceDescriptor descriptor = null;
        try {
            descriptor = new SportletServiceDescriptor(servicesPath, mappingPath);
        } catch (IOException e) {
            log.error("IO error unmarshalling " + servicesPath + " using " + mappingPath + " : " + e.getMessage());
        } catch (DescriptorException e) {
            log.error("Unable to unmarshall " + servicesPath + " using " + mappingPath + " : " + e.getMessage());
        }
        SportletServiceCollection serviceCollection = descriptor.getServiceCollection();
        List services = serviceCollection.getPortletServicesList();
        Iterator it = services.iterator();
        while (it.hasNext()) {
            SportletServiceDefinition serviceDef = (SportletServiceDefinition)it.next();
            allServices.put(serviceDef.getInterface(), serviceDef);
            log.info("adding service: " + serviceDef.getInterface() + " service def: " + serviceDef.toString());
        }
    }

    public static SportletServiceFactory getInstance() {
        return instance;
    }

    /**
     * createPortletServiceFactory instantiates the given class and initializes it. The portlet service properties
     * file must be specified in the ServletContext as an InitParameter with the "PortletServices.properties" key.
     * If no properties file is found or any error occurs an exception is thrown.
     *
     * @param service the class of the service
     * @param servletConfig the servlet configuration
     * @param boolean reuse a previous initialized service if true, otherwise create a new service instance if false
     * @return the instantiated portlet service
     * @throws PortletServiceUnavailableException if the portlet service is unavailable
     * @throws PortletServiceNotFoundException if the PortletService is not found
     */
    public PortletService createPortletService(Class service,
                                               ServletConfig servletConfig,
                                               boolean useCachedService)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException {

        PortletServiceProvider psp = null;

        // see if we already have an instance of this service
        if (service == null) {
            throw new PortletServiceUnavailableException("Received null service class");
        }

        // if init'ed service exists then use it
        if (useCachedService) {
            psp = (PortletServiceProvider)initServices.get(service);
            if (psp != null) return psp;
        }

        String serviceName = service.getName();

        SportletServiceDefinition def = (SportletServiceDefinition)allServices.get(serviceName);
        if (def == null) {
            log.error("Unable to find portlet service interface: " + serviceName +
                    " . Please check PortletServices.xml file for proper service entry");
            throw new PortletServiceNotFoundException("Unable to find portlet service: " + serviceName);
        }

        String serviceImpl = def.getImplementation();
        if (serviceImpl == null) {
            log.error("Unable to find implementing portlet service: " + serviceName +
                    " . Please check PortletServices.xml file for proper service entry");
            throw new PortletServiceNotFoundException("Unable to find implementing portlet service for interface: " + serviceName);
        }

        Properties configProperties = def.getConfigProperties();
        List configList = def.getConfigParamList();

        PortletServiceConfig portletServiceConfig =
                new SportletServiceConfig(service, configProperties, servletConfig);
        try {
            psp = (PortletServiceProvider)Class.forName(serviceImpl).newInstance();
        } catch (Exception e) {
            log.error("Unable to create portlet service: " + serviceImpl, e);
            throw new PortletServiceNotFoundException("Unable to create portlet service: " + serviceImpl);
        }
        psp.init(portletServiceConfig);
        initServices.put(service, psp);
        return psp;
    }

    /**
     * createPortletServiceFactory instantiates the given class and initializes it. If the portlet serviceProperties
     * is null then the location of the must be specified in the ServletContext as an InitParameter with the
     * "PortletServices.properties" key. If no properties file is found or any error occurs an exception is thrown.
     *
     * @param service the class of the service
     * @param serviceProperties the service properties
     * @param servletConfig the servlet configuration
     * @param boolean reuse a previous initialized service if true, otherwise create a new service instance if false
     * @return the instantiated portlet service
     * @throws PortletServiceUnavailableException if the portlet service is unavailable
     * @throws PortletServiceNotFoundException if the PortletService is not found
     */
    public PortletService createPortletService(Class service,
                                               String servicesXMLPath,
                                               ServletConfig servletConfig,
                                               boolean useCachedService)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException {
        if (service == null) {
            throw new PortletServiceUnavailableException("Received null service class");
        }
        if (!allServices.contains(service.getName())) {
            addServices(servicesXMLPath, ServiceMappingPath);
        }
        return createPortletService(service, servletConfig, useCachedService);
    }

    public Enumeration getActiveServices() {
        return initServices.keys();
    }

    /**
     * This is used by the portlet container to shutdown a service
     *
     * @param service the service class to shutdown
     */
    public void shutdownService(Class service) {
        if (initServices.containsKey(service)) {
            log.info("Shutting down service: " + service.getName());
            PortletServiceProvider psp = (PortletServiceProvider) initServices.get(service);
            psp.destroy();
        }
    }

    /**
     * This is used by the portlet container to shutdown all instantiated services
     */
    public void shutdownServices() {
        // Calls destroy() on all services we know about
        Enumeration keys = initServices.keys();
        log.info("Shutting down all services:");
        while (keys.hasMoreElements()) {
            Class service = (Class) keys.nextElement();
            PortletServiceProvider psp = (PortletServiceProvider) initServices.get(service);
            log.info("Shutting down service: " + service.getName() + " impl: " + psp.getClass().getName());
            psp.destroy();
        }
    }

}
