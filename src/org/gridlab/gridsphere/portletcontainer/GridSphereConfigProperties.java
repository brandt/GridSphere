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
     *  Location of Tomcat
     */
    public static final String TOMCAT_HOME = "TOMCAT_HOME";

    /**
     *  Location of Catalina
     */
    public static final String CATALINA_HOME = "CATALINA_HOME";

    /**
     * Location of GridSphere directory used to store persistent data
     */
    public static final String GRIDSPHERE_HOME = "GRIDSPHERE_HOME";

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
     * Directory containing GridSphere test files
     */
    public static final String GRIDSPHERE_TEST_DIR = "GRIDSPHERE_TEST_DIR";

    /**
     * Directory containing GridSphere Castor mapping files
     */
    public static final String GRIDSPHERE_PORTLET_MAPPING = "GRIDSPHERE_PORTLET_MAPPING";

    /**
     * Directory containing GridSphere Castor mapping files
     */
    public static final String GRIDSPHERE_SERVICES_MAPPING = "GRIDSPHERE_SERVICES_MAPPING";


    /**
     * Directory containing GridSphere Castor mapping files
     */
    public static final String GRIDSPHERE_SERVICES = "GRIDSPHERE_SERVICES";


    /**
     * Directory containing GridSphere database configuration files
     */
    public static final String GRIDSPHERE_DATABASE_DIR = "GRIDSPHERE_DATABASE_DIR";

    /**
     * Configured SQL based databse to use
     */
    public static final String GRIDSPHERE_DATABASE_CONFIG = "GRIDSPHERE_DATABASE_CONFIG";

    /**
     * Name of databse
     */
    public static final String GRIDSPHERE_DATABASE_NAME = "GRIDSPHERE_DATABASE_NAME";


    /**
     * Directory containing user layouts
     */
    public static final String GRIDSPHERE_LAYOUT_DIR = "GRIDSPHERE_LAYOUT_DIR";

    /**
     * Directory containing user layouts
     */
    public static final String GRIDSPHERE_LAYOUT_MAPPING = "GRIDSPHERE_LAYOUT_MAPPING";


    /**
     * Directory containing user layouts
     */
    public static final String GRIDSPHERE_NEW_USER_LAYOUT = "GRIDSPHERE_NEW_USER_LAYOUT";

    /**
     * Directory containing user layouts
     */
    public static final String GRIDSPHERE_GUEST_USER_LAYOUT = "GRIDSPHERE_GUEST_USER_LAYOUT";

}
