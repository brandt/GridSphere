/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.PortletConfig;

import javax.servlet.ServletConfig;
import java.util.Enumeration;
import java.util.Properties;

/**
 * All interfaces in this Service Provider Interface (SPI) package are needed by all
 * implementations of services. PortletServiceConfig is the interface through which
 * portlet services access the configuration passed to them by the portlet container.
 */
public class SportletServiceConfig implements PortletServiceConfig {

    private Class service;
    private Properties configProperties;
    private ServletConfig servletConfig;

    public SportletServiceConfig(Class service,
                                 Properties configProperties,
                                 ServletConfig servletConfig) {
        this.service = service;
        this.configProperties = configProperties;
        this.servletConfig = servletConfig;
    }

    /**
     * Gets the init parameter with the given name.
     *
     * @param name the name of the requested init parameter.
     * @return the init parameter
     */
    public String getInitParameter(String name) {
        return configProperties.getProperty(name);
    }

    /**
     * Gets an enumeration with the names of all init parameters provided in the portlet service configuration.
     *
     * @return an enumeration of the init parameters
     */
    public Enumeration getInitParameterNames() {
        return configProperties.keys();
    }

    /**
     * Return the ServletConfig object
     *
     * @return the servlet config
     */
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

}
