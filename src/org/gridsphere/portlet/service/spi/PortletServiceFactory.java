/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portlet.service.spi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.impl.PortletServiceConfigImpl;
import org.gridsphere.portlet.service.spi.impl.descriptor.PortletServiceCollection;
import org.gridsphere.portlet.service.spi.impl.descriptor.PortletServiceDefinition;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * The <code>PortletServiceFactory</code> provides a factory for the creation
 * of portlet services. The <code>PortletServiceFactory</code> is also
 * responsible for portlet service lifecycle management including
 * initialization and shutdown.
 */
public class PortletServiceFactory {

    private static PortletServiceFactory instance = null;

    // Maintain a single copy of each service instantiated
    // as a classname and PortletServiceProvider pair
    private static Hashtable<String, PortletServiceProvider> initServices = new Hashtable<String, PortletServiceProvider>();

    // Hash of all services key = service interface name, value = SportletServiceDefinition
    private static Hashtable<String, PortletServiceDefinition> allServices = new Hashtable<String, PortletServiceDefinition>();

    // Hash of all user services
    private static Hashtable<String, ServletContext> serviceContexts = new Hashtable<String, ServletContext>();

    // Hash of all user services
    private static Hashtable<String, ClassLoader> classLoaders = new Hashtable<String, ClassLoader>();

    private static Hashtable<String, List<String>> webappServices = new Hashtable<String, List<String>>();

    //public static String servicesMappingPath = null;

    public static URL servicesMappingStream = null;

    // contains springBeans

    public static Hashtable<String, WebApplicationContext> springBeans = new Hashtable<String, WebApplicationContext>();


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
     * @param webappName        the name of the web application
     * @param ctx               the servlet context
     * @param serviceCollection the collection of portlet service definitions
     * @param loader            the class loader
     * @throws PortletServiceException if an error occurs instantiating the service class
     */
    public static void addServices(String webappName, ServletContext ctx, PortletServiceCollection serviceCollection, ClassLoader loader) throws PortletServiceException {
        Log log = LogFactory.getLog(PortletServiceFactory.class);

        List<PortletServiceDefinition> services = serviceCollection.getPortletServicesList();
        List<String> webapplist = new ArrayList<String>();

        for (PortletServiceDefinition serviceDef : services) {
            allServices.put(serviceDef.getServiceInterface(), serviceDef);
            log.debug("adding service: " + serviceDef.getServiceInterface() + " service def: " + serviceDef.toString());
            serviceContexts.put(serviceDef.getServiceInterface(), ctx);
            classLoaders.put(serviceDef.getServiceInterface(), loader);
            webapplist.add(serviceDef.getServiceInterface());
        }
        webappServices.put(webappName, webapplist);

        for (PortletServiceDefinition serviceDef : services) {
            if (serviceDef.isLoadOnStartup()) {
                log.debug("loading service : " + serviceDef.getServiceInterface());
                try {
                    createPortletService(Class.forName(serviceDef.getServiceImplementation(), true, loader), true);
                } catch (ClassNotFoundException e) {
                    log.error("Unable to find class : " + serviceDef.getServiceImplementation());
                }
            }
        }
    }

    /**
     * Adds spring beans defined in a portlet application's aaplicationContext.xml to the springbeans HashMap
     * to be access using the createSpringService method
     *
     * @param ctx the Servlet Context
     */
    public static synchronized void addSpringServices(ServletContext ctx) {
        Log log = LogFactory.getLog(PortletServiceFactory.class);
        try {
            WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
            String[] beanDefs = context.getBeanDefinitionNames();
            for (int i = 0; i < beanDefs.length; i++) {
                springBeans.put(beanDefs[i], context);
            }
            log.info("Loaded applicationContext.xml for webapp " + ctx.getServletContextName() + ".");
        } catch (IllegalStateException e) {
            log.error("No applicationContext.xml found for: " + ctx.getServletContextName());
        }
    }

    /**
     * Returns a Spring (www.springframework.org) service defined in applicationContext.xml by its
     * bean name
     *
     * @param beanName the bean name identifying the spring service
     * @return the Spring service defined in applicationContext.xml or null if none exists
     */
    public static Object createSpringService(String beanName) {
        WebApplicationContext ctx = springBeans.get(beanName);
        return (ctx != null) ? ctx.getBean(beanName) : null;
    }


    /**
     * createPortletServiceFactory instantiates the given class and initializes it.
     *
     * @param service          the class of the service
     * @param useCachedService if true will us an existing service instance if one exists, false will create a new instance
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
            psp = initServices.get(serviceName);
            if (psp != null) return psp;
        }

        PortletServiceDefinition def = allServices.get(serviceName);
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

        ServletContext ctx = serviceContexts.get(serviceName);
        PortletServiceConfig portletServiceConfig = new PortletServiceConfigImpl(def, ctx);

        ClassLoader loader = classLoaders.get(serviceName);
        try {
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
            PortletServiceProvider psp = initServices.get(service.getName());
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
        Enumeration<String> keys = initServices.keys();
        while (keys.hasMoreElements()) {
            String serviceName = keys.nextElement();
            PortletServiceProvider psp = initServices.get(serviceName);
            log.info("Shutting down service: " + serviceName + " impl: " + psp.getClass().getName());
            psp.destroy();
        }
    }

    /**
     * Shuts down portlet services for a given webapp managed by this factory
     *
     * @param webappName containing the services to shutdown
     */
    public static void shutdownServices(String webappName) {
        Log log = LogFactory.getLog(PortletServiceFactory.class);

        // Calls destroy() on all services we know about
        List<String> services = webappServices.get(webappName);
        if (services == null) return;
        log.info("Shutting down  portlet services for webapp: " + webappName);
        List<String> remServices = new ArrayList<String>();
        for (String iface : services) {
            // Check standard portlet services that belong to this webapp
            Enumeration<String> keys = initServices.keys();
            while (keys.hasMoreElements()) {
                String serviceName = keys.nextElement();
                if (serviceName.equals(iface)) {
                    PortletServiceProvider psp = initServices.get(serviceName);
                    if (psp != null) {
                        log.info("Shutting down service: " + serviceName + " impl: " + psp.getClass().getName());
                        psp.destroy();
                        remServices.add(serviceName);
                    }
                }
            }
        }

        for (String serviceName : remServices) {
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
