/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @version $Id$
*/
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletSettings;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;

/**
 * A <code>ConcretePortlet</code> provides the portlet container with
 * information used to create and manage the portlet's lifecycle. A
 * <code>ConcretePortlet</code> is responsible for parsing the portlet.xml
 * file for portlet settings and portlet configuration information.
 * The <code>ConcretePortlet</code> also maintains an instantiated portlet
 * that is managed by the portlet container.
 *
 * @see org.gridlab.gridsphere.portlet.PortletSettings
 */
public interface ConcretePortlet {

    /**
     * Returns the concrete portlet application ID
     *
     * @return the concrete portlet application ID
     */
    public String getConcretePortletID();

    /**
     * Returns the concrete portlet configuration
     *
     * @return the concrete portlet configuration
     */
    public ConcretePortletConfig getConcretePortletConfig();

    /**
     * Sets the concrete portlet configuration
     *
     * @param concPortletConfig the concrete portlet configuration
     */
    public void setConcretePortletConfig(ConcretePortletConfig concPortletConfig);

    /**
     * Returns the portlet settings for this concrete portlet
     *
     * @return the portlet settings
     */
    public PortletSettings getPortletSettings();

    /**
     * Return the name of this portlet
     *
     * @return the portlet name
     */
    public String getPortletName();

    public String getDescription(Locale locale);

    public String getDisplayName(Locale locale);

    /**
     * Saves any concrete portlet changes to the descriptor
     *
     * @throws IOException if an I/O error occurs
     */
    public void save() throws IOException;

    public String toString();

}
