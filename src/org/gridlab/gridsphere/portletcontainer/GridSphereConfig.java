/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * <code>GridSphereConfig</code> represents the <code>gridsphere.properties</code> properties file
 * used for maintaing properties about the GridSphere portlet container.
 */
public class GridSphereConfig {

    protected static PortletLog log = SportletLog.getInstance(GridSphereConfig.class);

    protected static ServletConfig config = null;
    protected static ServletContext context = null;
    protected static Properties props = null;

    /**
     * Default constructor disallows instantiation
     */
    private GridSphereConfig() {
    }

    public static void setServletConfig(ServletConfig servletConfig) {
        config = servletConfig;
        context = config.getServletContext();
    }

    public static ServletConfig getServletConfig() {
        return config;
    }

    public static ServletContext getServletContext() {
        return context;
    }

    public static void setServletContext(ServletContext ctx) {
        context = ctx;
    }

    public static String getProperty(String key) {
        if (key == null) throw new IllegalArgumentException("property key cannot be null!");
        if (props == null) {
            InputStream propsStream = context.getResourceAsStream("/WEB-INF/gridsphere.properties");
            props = new Properties();
            try {
                props.load(propsStream);
            } catch (IOException e) {
                log.error("Unable to load gridsphere.properties", e);
            }
        }
        return props.getProperty(key);
    }


}
