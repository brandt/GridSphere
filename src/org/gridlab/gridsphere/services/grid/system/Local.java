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

    static {
        try {
            loadProperties();
        } catch (MissingResourceException e) {
        }
    }

    private Local() {
    }

    public static void loadProperties()
            throws MissingResourceException {
        String properties = "gridportlets.gridportlets";
        try {
            System.out.println("Grid: Loading properties file");
            _properties = ResourceBundle.getBundle(properties);
        } catch (MissingResourceException e) {
            _logger.error("Error getting properties file", e);
            throw e;
        }
    }

    public static String getProperty(String name) {
        try {
            String value = _properties.getString(name);
            return value;
        } catch (MissingResourceException e) {
            _logger.error("Error getting property [" + name + "]", e);
            return null;
        }
    }

    public static String getProperty(String name, String def) {
        try {
            String value = _properties.getString(name);
            return value;
        } catch (MissingResourceException e) {
            return def;
        }
    }

    public static String getConfigPath() {
        String path = getProperty("GRIDPORTLETS_HOME", "");
        StringBuffer buffer = new StringBuffer(path);
        buffer.append(FileSeparator);
        buffer.append("gridlab");
        return buffer.toString();
    }

    public static String getJdoCastorPath() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getConfigPath());
        buffer.append(FileSeparator);
        buffer.append(getProperty("GRIDPORTLETS_JDO_CASTOR", ""));
        buffer.append(FileSeparator);
        return buffer.toString();
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
