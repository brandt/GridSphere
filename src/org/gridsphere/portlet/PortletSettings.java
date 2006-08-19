/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletSettings.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * The <code>PortletSettings</code> interface provides the portlet with its
 * dynamic configuration. The configuration holds information about the portlet
 * that is valid per concrete portlet for all users, and is maintained by the
 * administrator. The portlet can therefore only read the dynamic configuration.
 * Only when the portlet is in <code>CONFIGURE</code> mode, it has write access
 * to the dynamic configuration data.
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
     * Returns the short title of this window for the provided locale,
     * or <code>null</code> if none exists.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getTitleShort(Locale locale, Client client);


    /**
     * Returns the description of this window for the provided locale,
     * or <code>null</code> if none exists.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getDescription(Locale locale, Client client);

    /**
     * Returns the keywords of this window for the provided locale,
     * or <code>null</code> if none exists.
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
     */
    public void removeAttribute(String name);

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name  the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value);

    /**
     * Stores all attributes.
     *
     * @throws IOException if the streaming causes an I/O problem
     */
    public void store() throws IOException;

    /**
     * Returns the portlet application settings
     *
     * @return the portlet application settings
     */
    public PortletApplicationSettings getApplicationSettings();

}
