/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.ResourceBundle;
import java.io.*;

/**
 * <code>GridSphereConfig</code> represents the <code>gridsphere.properties</code> properties file
 * used for maintaing properties about the GridSphere portlet container.
 */
public class GridSphereConfig implements GridSphereConfigProperties {

    public static final String pathtype = System.getProperty("file.separator");
    protected static ServletConfig config = null;
    protected static ServletContext context = null;
    protected static ResourceBundle configBundle = null;
    protected static String contextPath = "";
    protected static String servletPath = "";
    public static final String PROJECT_NAME = "gridsphere";


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

    public static void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    /**
     * Default constructor disallows instantiation
     */
    private GridSphereConfig() {
    }

}
