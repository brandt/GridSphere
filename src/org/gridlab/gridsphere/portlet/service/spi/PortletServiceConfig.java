/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi;

import javax.servlet.ServletConfig;
import java.util.Enumeration;

/**
 * All interfaces in this Service Provider Interface (SPI) package are needed by all
 * implementations of services. PortletServiceConfig is the interface through which
 * portlet services access the configuration passed to them by the portlet container.
 */
public interface PortletServiceConfig {

    /**
     * Gets the init parameter with the given name.
     *
     * @param name the name of the requested init parameter.
     * @return the init parameter
     */
    public String getInitParameter(String name);

    /**
     * Gets an enumeration with the names of all init parameters provided in the portlet service configuration.
     *
     * @return an enumeration of the init parameters
     */
    public Enumeration getInitParameterNames();

    /**
     * Return the ServletConfig object
     *
     * @return the servlet config
     */
    public ServletConfig getServletConfig();

}
