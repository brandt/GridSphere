/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Locale;

/**
 * The SortletConfig class provides the portlet with its configuration.
 * The configuration holds information about the portlet that is valid for all users,
 * and is maintained by the administrator. The portlet can therefore only read the configuration.
 * Only when the portlet is in CONFIGURE mode, it has write access to the configuration data
 * (the rest of the configuration is managed by the portlet container directly).
 */
public class SportletConfig implements PortletConfig {

    private ServletConfig servletConfig = null;
    private PortletContext context = null;
    private String portletName = null;

    /**
     * Constructor creates a PortletConfig from a portlet.xml file
     */
    public SportletConfig(ServletConfig servletConfig) throws Exception {
        this.servletConfig = servletConfig;
        this.context = new SportletContext(servletConfig);
    }

    /**
     * Returns the portlet context.
     *
     * @return the portlet context
     */
    public PortletContext getContext() {
        return context;
    }

    /**
     * Returns the name of the portlet.
     *
     * @return the name of the portlet
     */
    public String getName() {
        return portletName;
    }

    /**
     * Returns whether the portlet supports the given mode for the given client.
     *
     * @param mode the portlet mode
     * @param client the given client
     *
     * @return true if the window supports the given state, false otherwise
     */
    public boolean supports(Portlet.Mode mode, Client client) {
        // XXX: FILL ME IN
        return false;
    }

    /**
     * Returns whether the portlet window supports the given state
     *
     * @param state the portlet window state
     * @return true if the window supports the given state, false otherwise
     */
    public boolean supports(PortletWindow.State state) {
        // XXX: FILL ME IN
        return false;
    }

    // Overridden GenericServlet methods
    public final String getInitParameter(String name) {
        return servletConfig.getInitParameter(name);
    }

    public final Enumeration getInitParameterNames() {
        return servletConfig.getInitParameterNames();
    }

    public final ServletContext getServletContext() {
        return servletConfig.getServletContext();
    }

    public final String getServletName() {
        return servletConfig.getServletName();
    }

}