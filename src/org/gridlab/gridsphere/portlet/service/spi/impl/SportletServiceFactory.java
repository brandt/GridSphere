/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.GuestUser;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceCollection;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceDefinition;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceDescriptor;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.services.core.user.impl.GridSphereUserManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * The PortletServiceCacheFactory provides a singleton factory to create portlet services.
 *
 * Uses the double-check locking variant of the singleton pattern to allow for concurrent threads.
 */
public class SportletServiceFactory implements PortletServiceFactory {

    private static PortletLog log = SportletLog.getInstance(SportletServiceFactory.class);

    private static SportletServiceFactory instance = new SportletServiceFactory();

    private static GridSphereUserManager userManager = GridSphereUserManager.getInstance();

    // Maintain a single copy of each service instantiated
    // as a classname and PortletServiceProvider pair
    private Hashtable initServices = new Hashtable();

    // Hash of all services key = service interface name, value = SportletServiceDefinition
    private Hashtable allServices = new Hashtable();

    private static Hashtable serviceFactories = new Hashtable();

    private String serviceMappingPath;

    /**
     * Private constructor. Use getInstance() instead.
     */
    private SportletServiceFactory() {
        // Reads in the service definitions from the xml file and stores them in allServices
        // organized according to service interface keys and service definition values
        String servicesPath = GridSphereConfig.getProperty(GridSphereConfigProperties.PORTLET_SERVICES_XML);
        serviceMappingPath = GridSphereConfig.getProperty(GridSphereConfigProperties.PORTLET_SERVICES_MAPPING_XML);
        addServices(servicesPath, serviceMappingPath);

    }

    private SportletServiceFactory(ServletContext context) {
        String webApplicationName = context.getServletContextName();
        // get the servlet context for the coreportlets webapp
        String contextURIPath = "/" + webApplicationName;
        ServletContext ctx = context.getContext(contextURIPath);
        if (ctx == null) System.err.println("Unable to get ServletContext for: " + contextURIPath);
        // load in the PortletServices.xml file
        String servicesPath = ctx.getRealPath("") + "/WEB-INF/PortletServices.xml";
        addServices(servicesPath, serviceMappingPath);
    }


    protected void addServices(String servicesPath, String mappingPath) {
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
            SportletServiceDefinition serviceDef = (SportletServiceDefinition) it.next();
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
            psp = (PortletServiceProvider) initServices.get(service);
            if (psp != null) return psp;
        }

        String serviceName = service.getName();
        SportletServiceDefinition def = (SportletServiceDefinition) allServices.get(serviceName);
        if (def == null) {
            log.error("Unable to find portlet service interface: " + serviceName +
                    " . Please check PortletServices.xml file for proper service entry");
            throw new PortletServiceNotFoundException("Unable to find portlet service: " + serviceName);
        }

        // if user is required then pass in Guest user privileges
        if (def.getUserRequired()) {
            return createPortletUserService(service, GuestUser.getInstance(), servletConfig, useCachedService);
        }

        /* Create the service implementation */
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
            psp = (PortletServiceProvider) Class.forName(serviceImpl).newInstance();
        } catch (Exception e) {
            log.error("Unable to create portlet service: " + serviceImpl, e);
            throw new PortletServiceNotFoundException("Unable to create portlet service: " + serviceImpl);
        }

        try {
            psp.init(portletServiceConfig);
        } catch (PortletServiceUnavailableException e) {
            log.error("Unable to initialize portlet service: " + serviceImpl, e);
            throw new PortletServiceNotFoundException("Unable to initialize portlet service: " + serviceImpl);
        }

        initServices.put(service, psp);
        return psp;
    }

    /**
     * createPortletServiceFactory instantiates the given class and initializes it
     *
     * @param service the class of the service
     * @param servletConfig the servlet configuration
     * @param boolean reuse a previous initialized service if true, otherwise create a new service instance if false
     * @return the instantiated portlet service
     * @throws PortletServiceUnavailableException if the portlet service is unavailable
     * @throws PortletServiceNotFoundException if the PortletService is not found
     */
    public PortletService createPortletUserService(Class service, User user,
                                                   ServletConfig servletConfig,
                                                   boolean useCachedService)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException {

        PortletServiceProvider psp = null;

        String serviceName = service.getName();

        SportletServiceDefinition def = (SportletServiceDefinition) allServices.get(serviceName);
        if (def == null) {
            log.error("Unable to find portlet service interface: " + serviceName +
                    " . Please check PortletServices.xml file for proper service entry");
            throw new PortletServiceNotFoundException("Unable to find portlet service: " + serviceName);
        }
        if (!def.getUserRequired()) {
            return createPortletService(service, servletConfig, useCachedService);
        } else if (user == null) {
            throw new PortletServiceNotFoundException("Unable to create service: " + serviceName + " user is null");
        }

        /* Create the service implementation */
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

        // Create an authroizer for the secure service
        PortletServiceAuthorizer auth = new SportletServiceAuthorizer(user, userManager);

        // instantiate wrapper with user and impl
        try {
            Class c = Class.forName(serviceImpl);
            Class[] parameterTypes = new Class[]{PortletServiceAuthorizer.class};
            Object[] obj = new Object[]{auth};
            Constructor con = c.getConstructor(parameterTypes);
            psp = (PortletServiceProvider) con.newInstance(obj);
        } catch (Exception e) {
            log.error("Unable to create portlet service wrapper: " + serviceImpl, e);
            throw new PortletServiceNotFoundException("Unable to create portlet service: " + serviceName);
        }

        try {
            psp.init(portletServiceConfig);
        } catch (PortletServiceUnavailableException e) {
            log.error("Unable to initialize portlet service: " + serviceImpl, e);
            throw new PortletServiceNotFoundException("Unable to initialize portlet service: " + serviceImpl);
        }

        return psp;
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
