/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import javax.servlet.ServletConfig;
import java.io.File;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * GridSphere properties file
 */
public class GridSphereConfig {

    public static final String pathtype = System.getProperty("file.separator");

    private static GridSphereConfig instance = null;
    protected ResourceBundle configBundle = null;
    protected String gridspheredir = null;
    protected String tomcathome = null;
    protected final String projectname = "gridsphere";

    private GridSphereConfig() {
        try {
            configBundle = ResourceBundle.getBundle("gridsphere.gridsphere");
        } catch (MissingResourceException mre) {
            System.err.println("Config: Missing gridsphere.properties file " + mre.toString());
        }

        String tomcathome = configBundle.getString("TOMCAT_HOME");
        gridspheredir = tomcathome + pathtype + projectname;
        File gsfile = new File(gridspheredir);

        /* Create the base directory if necessary */
        if (!gsfile.exists()) {
            System.err.println("Can't find directory " + gridspheredir);
        }
    }

    public static GridSphereConfig getInstance() {
        if (instance == null) {
            instance = new GridSphereConfig();
        }
        return instance;
    }

    /**
     * Return the value for the given property key
     *
     * @return <code>String</code> the value for the given property key
     */
    public String getProperty(String type) {
        return configBundle.getString(type);
    }

}
