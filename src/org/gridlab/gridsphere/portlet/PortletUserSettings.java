/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import org.gridlab.gridsphere.portlet.Client;

import java.util.Enumeration;
import java.util.Locale;

/**
 * The PortletUserSettings interface provides the portlet with its dynamic configuration.
 * The configuration holds information about the portlet that is valid per concrete portlet per user,
 * and is maintained by the administrator. The portlet can therefore only read the dynamic configuration.
 * Only when the portlet is in CONFIGURE mode, it has write access to the dynamic configuration data
 */
public interface PortletUserSettings {

    /**
     * Returns the value of the attribute with the given name, or null if no such attribute exists.
     *
     * @param name the name of the attribute
     * @return the value of the attribute
     */
    public String getAttribute(String name);

    /**
     * Returns an enumeration of all available attributes names.
     *
     * @return an enumeration of all available attributes names
     */
    public Enumeration getAttributeNames();

    /**
     * Returns the title of this window.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getTitle(Locale locale, Client client);

    /**
     * Removes the attribute with the given name.
     *
     * @param name the attribute name
     */
    public void removeAttribute(String name);

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value);

    /**
     * Stores all attributes.
     */
    public void store();

}
