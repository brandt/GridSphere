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
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import javax.servlet.ServletConfig;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
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

    private static SportletServiceFactory instance;

    // Maintain a single copy of each service instantiated
    // as a classname and PortletServiceProvider pair
    private Hashtable services;

    /**
     * Private constructor. Use getInstance() instead.
     */
    private SportletServiceFactory() {
        services = new Hashtable();
    }

    /**
     * doSync is used to ensure only one thread has created an instance
     */
    private synchronized static void doSync() {
    }

    public static SportletServiceFactory getInstance() {
        if (instance == null) {
            SportletServiceFactory.doSync();
            instance = new SportletServiceFactory();
        }
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

        Properties serviceProperties = new Properties();
        GridSphereConfig gsc = GridSphereConfig.getInstance();
        String serviceProps = gsc.getProperty("PORTLET_SERVICE_PROPS");
        //String appRoot = servletConfig.getServletContext().getRealPath("");
        //String serviceProps = servletConfig.getInitParameter("PortletServices.properties");
        //String propsPath = appRoot + "/" + serviceProps;
        FileInputStream fistream = null;
        try {
            fistream = new FileInputStream(serviceProps);
            serviceProperties.load(fistream);
        } catch (IOException e) {
            log.error("Unable to find properties file: " + serviceProps);
            log.error("Make sure location is specified in server.xml " + serviceProps);
            throw new PortletServiceUnavailableException("Unable to find/load properties file: " + serviceProps);
        }
        return createPortletService(service, serviceProperties, servletConfig, useCachedService);

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
                                               Properties serviceProperties,
                                               ServletConfig servletConfig,
                                               boolean useCachedService)
            throws PortletServiceUnavailableException, PortletServiceNotFoundException {

        PortletServiceProvider psp = null;

        // see if we already have an instance of this service
        if (service == null) {
            throw new PortletServiceUnavailableException("Received null service class");
        }
        if (useCachedService) {
            psp = (PortletServiceProvider)services.get(service);
            if (psp != null) return psp;
        }
        String serviceName = service.getName();

        if (serviceProperties == null) {
            log.error("Received null service properties");
            throw new PortletServiceUnavailableException("Received null service properties");
        }
        String serviceImpl = serviceProperties.getProperty(serviceName);
        if (serviceImpl == null) {
            log.error("Unable to find implementing portlet service: " + serviceName +
                    " . Please check PortletServices.properties file for proper service entry");
            throw new PortletServiceNotFoundException("Unable to find implementing portlet service: " + serviceName);
        }
        PortletServiceConfig portletServiceConfig =
                new SportletServiceConfig(service, serviceProperties, servletConfig);
        try {
            psp = (PortletServiceProvider)Class.forName(serviceImpl).newInstance();
        } catch (Exception e) {
            log.error("Unable to create portlet service: " + serviceImpl, e);
            throw new PortletServiceNotFoundException("Unable to create portlet service: " + serviceImpl);
        }
        psp.init(portletServiceConfig);
        services.put(service, psp);
        return psp;
    }

    public Enumeration getActiveServices() {
        return services.keys();
    }

    /**
     * This is used by the portlet container to shutdown a service
     *
     * @param service the service class to shutdown
     */
    public void shutdownService(Class service) {
        if (services.containsKey(service)) {
            log.info("Shutting down service: " + service.getName());
            PortletServiceProvider psp = (PortletServiceProvider) services.get(service);
            psp.destroy();
        }
    }

    /**
     * This is used by the portlet container to shutdown all instantiated services
     */
    public void shutdownServices() {
        // Calls destroy() on all services we know about
        Enumeration keys = services.keys();
        log.info("Shutting down all services:");
        while (keys.hasMoreElements()) {
            Class service = (Class) keys.nextElement();
            PortletServiceProvider psp = (PortletServiceProvider) services.get(service);
            log.info("Shutting down service: " + service.getName() + " impl: " + psp.getClass().getName());
            psp.destroy();
        }
    }

}
