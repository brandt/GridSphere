/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <code>GridSphereConfig</code> represents the <code>gridsphere.properties</code> properties file
 * used for maintaing properties about the GridSphere portlet container.
 */
public class GridSphereConfig implements GridSphereConfigProperties {

    public static final String pathtype = System.getProperty("file.separator");
    protected static ServletConfig config = null;
    protected static ResourceBundle configBundle = null;
    public static final String PROJECT_NAME = "gridsphere";

    public static void setServletConfig(ServletConfig servletConfig) {
        config = servletConfig;
    }

    public static ServletConfig getServletConfig() {
        return config;
    }

    public static ServletContext getServletContext() {
        return config.getServletContext();
    }

    static {
        try {
            configBundle = ResourceBundle.getBundle(PROJECT_NAME + "." + PROJECT_NAME);
        } catch (MissingResourceException mre) {
            System.err.println("Config: Missing gridsphere.properties file " + mre.toString());
        }
    }

    /**
     * Default constructor disallows instantiation
     */
    private GridSphereConfig() {
    }

    /**
     * Return the value for the given property key
     *
     * @return <code>String</code> the value for the given property key
     */
    public static final String getProperty(String type) {
        String configString = configBundle.getString(type);
        if ((configString != null) && (type.endsWith("DIR"))) {
            if (!configString.endsWith(pathtype)) configString += pathtype;
        }
        return configString;
    }

}
