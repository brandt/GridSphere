/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portlet.service.spi;

import javax.servlet.ServletContext;
import java.util.Enumeration;

/**
 * The <code>PortletServiceConfig</code> is the interface through which
 * portlet services access the configuration settings from the services
 * descriptor file.
 */
public interface PortletServiceConfig {

    /**
     * Returns the init parameter with the given name.
     *
     * @param name the name of the requested init parameter.
     * @return the init parameter
     */
    public String getInitParameter(String name);

    /**
     * Returns the init parameter with the given name.
     *
     * @param name  the name of the init parameter.
     * @param value the value of the init parameter
     */
    public void setInitParameter(String name, String value);

    /**
     * Returns the init parameter with the given name. It returns the given default
     * value if the parameter is not found.
     *
     * @param name         the name of the requested init parameter.
     * @param defaultValue the default value to return.
     * @return the init parameter value if exists, otherwise defaultValue
     */
    public String getInitParameter(String name, String defaultValue);

    /**
     * Returns an enumeration with the names of all init parameters provided in the portlet service configuration.
     *
     * @return an enumeration of the init parameters
     */
    public Enumeration getInitParameterNames();

    /**
     * Returns the servlet configuration
     *
     * @return the servlet configuration
     */
    public ServletContext getServletContext();

}
