/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * GridSphere properties file
 */
public class GridSphereConfig implements GridSphereConfigProperties {

    public static final String pathtype = System.getProperty("file.separator");

    private static GridSphereConfig instance = null;
    protected ResourceBundle configBundle = null;
    protected String gridspheredir = null;
    protected String tomcathome = null;
    protected String catalinahome = null;
    protected final String projectname = "gridsphere";

    private GridSphereConfig() {
        try {
            configBundle = ResourceBundle.getBundle("gridsphere.gridsphere");
        } catch (MissingResourceException mre) {
            System.err.println("Config: Missing gridsphere.properties file " + mre.toString());
        }

        tomcathome = configBundle.getString("TOMCAT_HOME");
        String catalinahome = configBundle.getString("CATALINA_HOME");
        if (catalinahome != "") {
            gridspheredir = catalinahome + pathtype + projectname;
        } else {
            gridspheredir = tomcathome + pathtype + projectname;
        }

        /*
        File gsfile = new File(gridspheredir);
        if (!gsfile.exists()) {
            gsfile.mkdir();
        }
        */
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
