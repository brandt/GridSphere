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
     * Location of GridSphere web application
     */
    public static final String GRIDSPHERE_WEBAPP = "GRIDSPHERE_WEBAPP";

    /**
     * Location of GridSphere directory used to store persistent data
     */
    public static final String GRIDSPHERE_HOME = "GRIDSPHERE_HOME";

    /**
     * Portlet deployment descriptor properties
     */
    public static final String PORTLET_XML = "PORTLET_XML";
    public static final String PORTLET_MAPPING_XML = "PORTLET_MAPPING_XML";

    /**
     * LayoutEngine properties
     */
    public static final String LAYOUT_XML = "LAYOUT_XML";
    public static final String LAYOUT_MAPPING_XML = "LAYOUT_MAPPING_XML";
    public static final String USER_LAYOUT_DIR = "USER_LAYOUT_DIR";

}
