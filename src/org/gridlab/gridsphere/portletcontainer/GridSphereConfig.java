/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <code>GridSphereConfig</code> represents the <code>gridsphere.properties</code> properties file
 * used for maintaing properties about the GridSphere portlet container.
 */
public class GridSphereConfig implements GridSphereConfigProperties {

    public static final String pathtype = System.getProperty("file.separator");

    protected static ResourceBundle configBundle = null;
    protected static final String projectname = "gridsphere";

    static {
        try {
            configBundle = ResourceBundle.getBundle("gridsphere.gridsphere");
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
