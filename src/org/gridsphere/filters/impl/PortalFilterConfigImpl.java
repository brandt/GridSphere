package org.gridsphere.filters.impl;

import org.gridsphere.filters.PortalFilterConfig;

import javax.servlet.ServletConfig;
import java.util.Properties;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public class PortalFilterConfigImpl implements PortalFilterConfig {

    private ServletConfig config;
    private Properties configProperties;

    public PortalFilterConfigImpl(ServletConfig config, Properties configProperties) {
        this.config = config;
        this.configProperties = configProperties;
    }

    public ServletConfig getServletConfig() {
        return config;
    }

    public String getInitParameter(String name) {
        return configProperties.getProperty(name);
    }

}
