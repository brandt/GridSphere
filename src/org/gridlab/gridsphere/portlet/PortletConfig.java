/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import javax.servlet.ServletConfig;

/**
 * The PortletConfig interface provides the portlet with its configuration.
 * The configuration holds information about the portlet that is valid for all users,
 * and is maintained by the administrator. The portlet can therefore only read the configuration.
 * Only when the portlet is in CONFIGURE mode, it has write access to the configuration data
 * (the rest of the configuration is managed by the portlet container directly).
 */
public interface PortletConfig extends ServletConfig {

    /**
     * Returns the portlet context.
     *
     * @return the portlet context
     */
    public PortletContext getContext();

    /**
     * Returns the name of the portlet.
     *
     * @return the name of the portlet
     */
    public String getName();

    /**
     * Returns whether the portlet supports the given mode for the given client.
     *
     * @param mode the portlet mode
     * @param client the given client
     *
     * @return true if the window supports the given state, false otherwise
     */
    public boolean supports(Portlet.Mode mode, Client client);


    /**
     * Returns whether the portlet window supports the given state
     *
     * @param state the portlet window state
     * @return true if the window supports the given state, false otherwise
     */
    public boolean supports(PortletWindow.State state);

}