package org.gridlab.gridsphere.services.grid.system;

import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.ResourceBundle;
import java.util.ListResourceBundle;
import java.util.MissingResourceException;
import java.net.InetAddress;

public class Local {

    public static String FileSeparator = System.getProperty("file.separator");

    private static PortletLog _logger = null;
    private static ResourceBundle _properties = null;

    /***
    static {
        loadProperties();
    }
    ***/

    private Local() {
    }

    public static void loadProperties() {
        String properties = "gridportlets.gridportlets";
        try {
            _logger.info("Loading properties file");
            _properties = ResourceBundle.getBundle(properties);
        } catch (MissingResourceException e) {
            _logger.error("Error getting properties file", e);
        }
    }

    public static String getProperty(String name) {
        if (_properties == null) {
            return "";
        }
        try {
            String value = _properties.getString(name);
            return value;
        } catch (MissingResourceException e) {
            _logger.error("Error getting property [" + name + "]", e);
            return null;
        }
    }

    public static String getProperty(String name, String def) {
        if (_properties == null) {
            return def;
        }
        try {
            String value = _properties.getString(name);
            return value;
        } catch (MissingResourceException e) {
            return def;
        }
    }

    public static String getHomePath() {
        return getProperty("GRIDPORTLETS_HOME",
            "~/gridsphere/webapps/gridportlets");
    }

    public static String getJdoCastorPath() {
        return getProperty("GRIDPORTLETS_JDO_CASTOR",
            "~/gridsphere/webapps/gridportlets/WEB-INF/jdo/castor");
    }

    public static String getLocalHost() {
        String localHost = null;
        try {
            localHost = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            _logger.error("Cannot get local host name!", e);
            try {
                localHost = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e2) {
                _logger.error("Cannot get local host address!", e2);
                localHost = "localhost";
            }
        }
        return localHost;
    }
}
