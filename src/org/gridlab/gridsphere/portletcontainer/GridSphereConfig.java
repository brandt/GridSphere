/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;
import java.io.File;
import java.net.URL;

/**
 * <code>GridSphereConfig</code> represents the <code>gridsphere.properties</code> properties file
 * used for maintaing properties about the GridSphere portlet conatiner.
 */
public class GridSphereConfig implements GridSphereConfigProperties {

    public static final String pathtype = System.getProperty("file.separator");

    private static GridSphereConfig instance;
    protected static ResourceBundle configBundle = null;
    protected static final String projectname = "gridsphere";
    private Class clazz = this.getClass();

    static {
        try {
            configBundle = ResourceBundle.getBundle("gridsphere.gridsphere");
        } catch (MissingResourceException mre) {
            System.err.println("Config: Missing gridsphere.properties file " + mre.toString());
        }
        String gridspheredir;
        String tomcathome = configBundle.getString("TOMCAT_HOME");
        String catalinahome = configBundle.getString("CATALINA_HOME");
        if (catalinahome != "") {
            gridspheredir = catalinahome + pathtype + projectname;
        } else {
            gridspheredir = tomcathome + pathtype + projectname;
        }
    }

    /**
     * Default constructor disallows instantiation
     */
    private GridSphereConfig() {
        Class clazz = this.getClass();
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
