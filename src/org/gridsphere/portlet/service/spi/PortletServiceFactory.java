/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletServiceFactory.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portlet.service.spi;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceCollection;
import org.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceDefinition;
import org.gridsphere.portlet.service.spi.impl.SportletServiceConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import java.util.*;
import java.net.URL;

/**
 * The <code>PortletServiceFactory</code> provides a factory for the creation
 * of portlet services. The <code>PortletServiceFactory</code> is also
 * responsible for portlet service lifecycle management including
 * initialization and shutdown.
 */
public class PortletServiceFactory  {

    private static PortletServiceFactory instance = null;

    // Maintain a single copy of each service instantiated
    // as a classname and PortletServiceProvider pair
    private static Hashtable initServices = new Hashtable();

    // Hash of all services key = service interface name, value = SportletServiceDefinition
    private static Hashtable allServices = new Hashtable();

    // Hash of all user services
    private static Hashtable serviceContexts = new Hashtable();

    // Hash of all user services
    private static Hashtable classLoaders = new Hashtable();

    private static Hashtable webappServices = new Hashtable();

    //public static String servicesMappingPath = null;

    public static URL servicesMappingStream = null;

    /**
     * Private constructor. Use getDefault() instead.
     */
    private PortletServiceFactory() {
        servicesMappingStream = this.getClass().getResource("/org/gridsphere/portlet/service/spi/impl/portlet-services-mapping.xml");
    }

    public static synchronized PortletServiceFactory getInstance() {
        if (instance == null) {
            instance = new PortletServiceFactory();
        }
        return instance;
    }

    /**
     * Umarshalls services from the descriptor file found in servicesPath
     * using the mapping file specified
     *
     * @param ctx the servlet context
     * @param servicesPath the path to the portlet services descriptor file
     */
    public static void addServices(ServletContext ctx, SportletServiceCollection serviceCollection) throws PortletServiceException {
        // check if services path represents a single file or a directory
        Log log = LogFactory.getLog(PortletServiceFactory.class);
        List services = serviceCollection.getPortletServicesList();
        Iterator it = services.iterator();
        while (it.hasNext()) {
            SportletServiceDefinition serviceDef = (SportletServiceDefinition) it.next();
            allServices.put(serviceDef.getServiceInterface(), serviceDef);
            log.debug("adding service: " + serviceDef.getServiceInterface() + " service def: " + serviceDef.toString());
            serviceContexts.put(serviceDef.getServiceInterface(), ctx);
        }
        it = services.iterator();
        while (it.hasNext()) {
            SportletServiceDefinition serviceDef = (SportletServiceDefinition) it.next();
            if (serviceDef.isLoadOnStartup()) {
                log.debug("loading service : " + serviceDef.getServiceInterface());
                try {
                    createPortletService(Class.forName(serviceDef.getServiceInterface()), true);
                } catch (ClassNotFoundException e) {
                    log.error("Unable to find class : " + serviceDef.getServiceImplementation());
                }
            }
        }
    }

    /**
     * Umarshalls services from the descriptor file found in servicesPath
     * using the mapping file specified
     *
     * @param webappName the name of the web application
     * @param ctx the servlet context
     * @param servicesPath the path to the portlet services descriptor file
     * @param loader the class loader
     */
    public static void addServices(String webappName, ServletContext ctx, SportletServiceCollection serviceCollection, ClassLoader loader) throws PortletServiceException {
        Log log = LogFactory.getLog(PortletServiceFactory.class);

        List services = serviceCollection.getPortletServicesList();
        List webapplist = new ArrayList();
        Iterator it = services.iterator();
        while (it.hasNext()) {
            SportletServiceDefinition serviceDef = (SportletServiceDefinition) it.next();

            allServices.put(serviceDef.getServiceInterface(), serviceDef);
            log.debug("adding service: " + serviceDef.getServiceInterface() + " service def: " + serviceDef.toString());
            serviceContexts.put(serviceDef.getServiceInterface(), ctx);
            classLoaders.put(serviceDef.getServiceInterface(), loader);
            webapplist.add(serviceDef.getServiceInterface());
        }
        webappServices.put(webappName, webapplist);
        it = services.iterator();
        while (it.hasNext()) {
            SportletServiceDefinition serviceDef = (SportletServiceDefinition) it.next();
            if (serviceDef.isLoadOnStartup()) {
                log.debug("loading service : " + serviceDef.getServiceInterface());
                try {
                    createPortletService(Class.forName(serviceDef.getServiceInterface(), true, loader), true);
                } catch (ClassNotFoundException e) {
                    log.error("Unable to find class : " + serviceDef.getServiceImplementation());
                }
            }
        }
    }

    /**
     * createPortletServiceFactory instantiates the given class and initializes it.
     *
     * @param service        the class of the service
     * @return the instantiated portlet service
     * @throws PortletServiceUnavailableException
     *          if the portlet service is unavailable
     * @throws PortletServiceNotFoundException
     *          if the PortletService is not found
     */
    public static PortletService createPortletService(Class service,
                                               boolean useCachedService)
        throws PortletServiceUnavailableException, PortletServiceNotFoundException {

        Log log = LogFactory.getLog(PortletServiceFactory.class);

        PortletServiceProvider psp = null;

        // see if we already have an instance of this service
        if (service == null) {
            throw new IllegalArgumentException("Received null service class");
        }

        String serviceName = service.getName();
        // if init'ed service exists then use it
        if (useCachedService) {
            psp = (PortletServiceProvider) initServices.get(serviceName);
            if (psp != null) return psp;
        }

        SportletServiceDefinition def = (SportletServiceDefinition) allServices.get(serviceName);
        if (def == null) {
            log.error("Unable to find portlet service interface: " + serviceName +
                    " . Please check PortletServices.xml file for proper service entry");
            throw new PortletServiceNotFoundException("Unable to find portlet service: " + serviceName);
        }

        /* Create the service implementation */
        String serviceImpl = def.getServiceImplementation();
        if (serviceImpl == null) {
            log.error("Unable to find implementing portlet service: " + serviceName +
                    " . Please check PortletServices.xml file for proper service entry");
            throw new PortletServiceNotFoundException("Unable to find implementing portlet service for interface: " + serviceName);
        }

        ServletContext ctx = (ServletContext) serviceContexts.get(serviceName);
        PortletServiceConfig portletServiceConfig = new SportletServiceConfig(def, ctx);

        try {
            ClassLoader loader = (ClassLoader) classLoaders.get(serviceName);
            if (loader != null) {
                psp = (PortletServiceProvider) Class.forName(serviceImpl, true, loader).newInstance();
            } else {
                psp = (PortletServiceProvider) Class.forName(serviceImpl).newInstance();
            }
        } catch (InstantiationException e) {
            // InstantiationException - if this Class represents an abstract class, an interface, an array class, a primitive type, or void; or if the class has no nullary constructor; or if the instantiation fails for some other reason. 
            log.error("Unable to create portlet service: " + serviceImpl, e);
            throw new PortletServiceUnavailableException("Unable to create portlet service: " + serviceImpl + " Class represents an abstract class, an interface, an array class, a primitive type, or void; or if the class has no nullary constructor; or if the instantiation fails for some other reason.", e);
        } catch (IllegalAccessException e) {
            // IllegalAccessException - if the class or its nullary constructor is not accessible. 
            log.error("Unable to create portlet service: " + serviceImpl, e);
            throw new PortletServiceUnavailableException("Unable to create portlet service: " + serviceImpl + " class or its nullary constructor is not accessible.", e);
        } catch (ClassNotFoundException e) {
            log.error("Unable to create portlet service: " + serviceImpl, e);
            throw new PortletServiceUnavailableException("Unable to create portlet service: " + serviceImpl + " Class not found.", e);
        } catch (ExceptionInInitializerError e) {
            // the initialization provoked by this method fails.
            log.error("Unable to create portlet service: " + serviceImpl, e);
            throw new PortletServiceUnavailableException("Unable to create portlet service: " + serviceImpl + " the initialization provoked by this method fails.", e);
        } catch (Exception e) {
            log.error("Unable to create portlet service: " + serviceImpl, e);
            throw new PortletServiceUnavailableException("Unable to create portlet service: " + serviceImpl, e);
        }

        try {
            psp.init(portletServiceConfig);
        } catch (PortletServiceUnavailableException e) {
            log.error("Unable to initialize portlet service: " + serviceImpl, e);
            throw new PortletServiceUnavailableException("Unable to initialize portlet service: " + serviceImpl, e);
        }

        initServices.put(serviceName, psp);
        return psp;
    }

    /**
     * Destroys a portlet service identified by its class
     *
     * @param service the service class to shutdown
     */
    public static void shutdownService(Class service) {
        Log log = LogFactory.getLog(PortletServiceFactory.class);

        if (initServices.containsKey(service.getName())) {
            log.info("Shutting down service: " + service.getName());
            PortletServiceProvider psp = (PortletServiceProvider) initServices.get(service);
            psp.destroy();
        }
    }

    /**
     * Shuts down all portlet services managed by this factory
     */
    public static void shutdownServices() {
        Log log = LogFactory.getLog(PortletServiceFactory.class);

        // Calls destroy() on all services we know about
        log.info("Shutting down all portlet services:");
        Enumeration keys = initServices.keys();
        while (keys.hasMoreElements()) {
            String serviceName = (String) keys.nextElement();
            PortletServiceProvider psp = (PortletServiceProvider) initServices.get(serviceName);
            log.info("Shutting down service: " + serviceName + " impl: " + psp.getClass().getName());
            psp.destroy();
        }
    }

    /**
     * Shuts down portlet services for a given webapp managed by this factory
     */
    public static void shutdownServices(String webappName) {
        Log log = LogFactory.getLog(PortletServiceFactory.class);

        // Calls destroy() on all services we know about
        List services = (List) webappServices.get(webappName);
        if (services == null) return;
        log.info("Shutting down  portlet services for webapp: " + webappName);
        List remServices = new ArrayList();
        Iterator it = services.iterator();
        while (it.hasNext()) {
            String iface = (String) it.next();
            // Check standard portlet services that belong to this webapp
            Enumeration keys = initServices.keys();
            while (keys.hasMoreElements()) {
                String serviceName = (String) keys.nextElement();
                if (serviceName.equals(iface)) {
                    PortletServiceProvider psp = (PortletServiceProvider) initServices.get(serviceName);
                    if (psp != null) {
                        log.info("Shutting down service: " + serviceName + " impl: " + psp.getClass().getName());
                        psp.destroy();
                        remServices.add(serviceName);
                    }
                }
            }
        }

        it = remServices.iterator();
        while (it.hasNext()) {
            String serviceName = (String) it.next();
            initServices.remove(serviceName);
            allServices.remove(serviceName);
            classLoaders.remove(serviceName);
            serviceContexts.remove(serviceName);
        }
        webappServices.remove(webappName);
    }

    /*
    public void logStatistics() {
        Enumeration e = null;
        if (initServices != null) {
            log.debug("printing inited services");
            e = initServices.keys();
            String ser = "services:\n";
            while (e.hasMoreElements()) {
                String s = (String) e.nextElement();
                ser += s + "\n";
            }
            log.debug(ser);
        }
    }
    */
}
