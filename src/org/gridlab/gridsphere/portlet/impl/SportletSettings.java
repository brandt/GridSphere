/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.AccessDeniedException;
import org.gridlab.gridsphere.portlet.Client;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDeploymentDescriptor;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;

/**
 * The SportletSettings class provides the portlet with its dynamic configuration.
 * The configuration holds information about the portlet that is valid per concrete portlet for all users,
 * and is maintained by the administrator. The portlet can therefore only read the dynamic configuration.
 * Only when the portlet is in CONFIGURE mode, it has write access to the dynamic configuration data
 */
public class SportletSettings implements PortletSettings {

    private Hashtable store = new Hashtable();

    public SportletSettings(PortletDeploymentDescriptor pdd) {
        // Load settings from portlet deployment descriptor
        // Somehow get locale specific Titles from portlet.xml
    }

    /**
     * Returns the value of the attribute with the given name, or null if no such attribute exists.
     *
     * @param name the name of the attribute
     * @return the value of the attribute
     */
    public String getAttribute(String name) {
        return (String) store.get(name);
    }

    /**
     * Returns an enumeration of all available attributes names.
     *
     * @return an enumeration of all available attributes names
     */
    public Enumeration getAttributeNames() {
        return store.keys();
    }

    /**
     * Returns the title of this window.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getTitle(Locale locale, Client client) {
        // XXX: FILL ME IN
        return null;
    }

    /**
     * Removes the attribute with the given name.
     *
     * @param name the attribute name
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void removeAttribute(String name) throws AccessDeniedException {
        store.remove(name);
    }

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name the attribute name
     * @param value the attribute value
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void setAttribute(String name, String value) throws AccessDeniedException {
        store.put(name, value);
    }

    /**
     * Stores all attributes.
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void store() throws AccessDeniedException {
        // XXX: FILL ME IN
    }

    /**
     * Loads all attributes
     */
    protected void load() {
        // XXX: FILL ME IN
    }


}
