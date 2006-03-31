/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

/**
 * The <code>GridSphereConfigProperties</code> provides GridSphere portlet container properties from the
 * <code>gridsphere.properties</code> properties file.
 */
public interface GridSphereConfigProperties {

    /**
     * Location of GridSphere directory used to store persistent data
     */
    public static final String GRIDSPHERE_INFO = "GridSphere/2.2";

    /**
     * JSR 168 Portlet API version info
     */
    public final static int JSR_MAJOR_VERSION = 1;
    public final static int JSR_MINOR_VERSION = 0;

    /**
     * GridSphere/WebSphere Portlet API version info
     */
    public final static int GS_MAJOR_VERSION = 4;
    public final static int GS_MINOR_VERSION = 2;

    /**
     * Location of Tomcat
     */
    public static final String TOMCAT_HOME = "TOMCAT_HOME";

    /**
     * Location of Catalina
     */
    public static final String CATALINA_HOME = "CATALINA_HOME";

    /**
     * Location of GridSphere directory used to store persistent data
     */
    public static final String GRIDSPHERE_HOME = "GRIDSPHERE_HOME";

    /**
     * Location of GridSphere test directory used to store persistent data
     */
    public static final String TEST_HOME = "TEST_HOME";

    /**
     * Location of GridSphere directory used to store persistent data
     */
    public static final String GRIDSPHERE_VERSION = "GRIDSPHERE_VERSION";

    /**
     * Location of GridSphere directory used to store persistent data
     */
    public static final String GRIDSPHERE_RELEASE = "GRIDSPHERE_RELEASE";

    /**
     * Directory containing GridSphere configuration files
     */
    public static final String GRIDSPHERE_CONFIG_DIR = "GRIDSPHERE_CONFIG_DIR";

    /**
     * Directory containing GridSphere Castor mapping files
     */
    public static final String PORTLET_MAPPING = "PORTLET_MAPPING";

    /**
     * Directory containing GridSphere Castor mapping files
     */
    public static final String SERVICES_MAPPING = "SERVICES_MAPPING";


    /**
     * Directory containing GridSphere Castor mapping files
     */
    public static final String SERVICES_DESCRIPTOR = "SERVICES_DESCRIPTOR";

    /**
     * Configured SQL based databse to use
     */
    public static final String DATABASE_CONFIG = "DATABASE_CONFIG";

    /**
     * Name of databse
     */
    public static final String DATABASE_NAME = "DATABASE_NAME";

    /**
     * Directory containing user layouts
     */
    public static final String LAYOUT_DIR = "LAYOUT_DIR";

    /**
     * Directory containing user layouts
     */
    public static final String LAYOUT_MAPPING = "LAYOUT_MAPPING";

    /**
     * Default theme to use
     */
    public static final String DEFAULT_THEME = "DEFAULT_THEME";


    /**
     * Directory containing user layouts
     */
    public static final String NEW_USER_LAYOUT = "NEW_USER_LAYOUT";

    /**
     * Directory containing user layouts
     */
    public static final String GUEST_USER_LAYOUT = "GUEST_USER_LAYOUT";

    public static final String GPDK_MAPPING = "GPDK_MAPPING";
}
