/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.Enumeration;
import java.util.Locale;
import java.util.List;

/**
 * The PortletSettings interface provides the portlet with its dynamic configuration.
 * The configuration holds information about the portlet that is valid per concrete portlet for all users,
 * and is maintained by the administrator. The portlet can therefore only read the dynamic configuration.
 * Only when the portlet is in CONFIGURE mode, it has write access to the dynamic configuration data
 */
public interface PortletSettings {

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
     * Returns the portlet's default locale.
     *
     * @return the portlet's default locale
     */
    public Locale getDefaultLocale();

    /**
     * Returns the short title of this window for the provided locale, or null if none exists.
     * NOTE: THIS IS NOT PART OF THE WPS PORTLET API 4.1
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getTitleShort(Locale locale, Client client);


    /**
     * Returns the description of this window for the provided locale, or null if none exists.
     * NOTE: THIS IS NOT PART OF THE WPS PORTLET API 4.1
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getDescription(Locale locale, Client client);

    /**
     * Returns the keywords of this window for the provided locale, or null if none exists.
     * NOTE: THIS IS NOT PART OF THE WPS PORTLET API 4.1
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getKeywords(Locale locale, Client client);

    /**
     * Returns this portlets concrete ID. Used internally in Action tags
     * to signal the portlet container which portlet needs to be executed
     *
     * @return the concrete portlet ID
     */
    public String getConcretePortletID();

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

    /**
     * Returns the portlet application settings
     *
     * @return the portlet application settings
     */
    public PortletApplicationSettings getApplicationSettings();

}
