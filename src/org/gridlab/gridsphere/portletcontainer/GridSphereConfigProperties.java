/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

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
     * Location of GridSphere web application
     */
    public static final String GRIDSPHERE_WEBAPP = "GRIDSPHERE_WEBAPP";

    /**
     * Location of GridSphere directory used to store persistent data
     */
    public static final String GRIDSPHERE_HOME = "GRIDSPHERE_HOME";

    /**
     * Portlet deployment descriptor mapping file
     */
    public static final String PORTLET_MAPPING_XML = "PORTLET_MAPPING_XML";

    /**
     * Portlet services description file
     */
    public static final String PORTLET_SERVICES = "PORTLET_SERVICES";

    /**
     * Portlet services description file
     */
    public static final String PORTLET_SERVICES_XML = "PORTLET_SERVICES_XML";

    /**
     * Portlet services mapping file
     */
    public static final String PORTLET_SERVICES_MAPPING_XML = "PORTLET_SERVICES_MAPPING_XML";

    /**
     * LayoutEngine properties
     */
    public static final String LAYOUT_XML = "LAYOUT_XML";
    public static final String LAYOUT_MAPPING_XML = "LAYOUT_MAPPING_XML";
    public static final String USER_LAYOUT_DIR = "USER_LAYOUT_DIR";

    /**
     * Configuration ofor the used persistence database
     */
    public static final String PERSISTENCE_CONFIGFILE = "PERSISTENCE_CONFIGFILE";
    public static final String PERSISTENCE_DBNAME = "PERSISTENCE_DBNAME";

    /**
     *  Configuration for the standard UI Theme
     */
    public static final String UI_THEME = "UI_THEME";
}
