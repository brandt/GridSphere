package org.gridsphere.services.core.filter.impl;

import org.gridsphere.services.core.filter.PortalFilterConfig;

import javax.servlet.ServletConfig;
import java.util.Properties;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public class PortalFilterConfigImpl implements PortalFilterConfig {

    private Properties configProperties;

    public PortalFilterConfigImpl(Properties configProperties) {
        this.configProperties = configProperties;
    }

    public String getInitParameter(String name) {
        return configProperties.getProperty(name);
    }

}
