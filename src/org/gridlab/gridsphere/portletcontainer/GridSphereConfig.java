/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.ResourceBundle;

/**
 * <code>GridSphereConfig</code> represents the <code>gridsphere.properties</code> properties file
 * used for maintaing properties about the GridSphere portlet container.
 */
public class GridSphereConfig implements GridSphereConfigProperties {

    public static final String pathtype = System.getProperty("file.separator");
    protected static ServletConfig config = null;
    protected static ResourceBundle configBundle = null;
    protected static String contextPath = "";
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

    public static String getContextPath() {
        return contextPath;
    }

    public static void setContextPath(String contextPath) {
        GridSphereConfig.contextPath = contextPath;
    }

    /**
     * Default constructor disallows instantiation
     */
    private GridSphereConfig() {
    }

}
