/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.Enumeration;

/**
 * The PortletApplicationSettings interface provides the portlet with the application's dynamic configuration.
 * The configuration holds information about the portlet application that is valid per concrete portlet
 * application for all users, and is maintained by the administrator. The portlet can therefore only read the
 * dynamic configuration. Only when the portlet is in CONFIGURE mode, it has write access to the dynamic
 * configuration data
 */
public interface PortletApplicationSettings {

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
     * Removes the attribute with the given name.
     *
     * @param name the attribute name
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void removeAttribute(String name) throws AccessDeniedException;

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name the attribute name
     * @param value the attribute value
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void setAttribute(String name, String value) throws AccessDeniedException;

    /**
     * Stores all attributes.
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void store() throws AccessDeniedException;

}
