/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SportletApplicationSettings.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet.impl;

import org.gridsphere.portlet.PortletApplicationSettings;
import org.gridsphere.portletcontainer.impl.ConcreteSportlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The <code>SportletApplicationSettings</code> implements the
 * <code>SportletApplicationSettings</code> interface provides the portlet
 * with the application's dynamic configuration. The configuration holds
 * information about the portlet application that is valid per concrete portlet
 * application for all users, and is maintained by the administrator.
 * The portlet can therefore only read the dynamic configuration. Only when
 * the portlet is in <code>CONFIGURE</code> mode, it has write access to the dynamic
 * configuration data
 */
public class SportletApplicationSettings implements PortletApplicationSettings {

    protected ConcreteSportlet concPortlet = null;
    protected Hashtable store = new Hashtable();

    /**
     * Disallow default instantiation
     */
    private SportletApplicationSettings() {
    }

    /**
     * Constructs an instance of SportletApplicationSettings from a concrete portlet
     *
     * @param concPortlet the concrete portlet
     */
    public SportletApplicationSettings(ConcreteSportlet concPortlet) {
        this.concPortlet = concPortlet;
        store = concPortlet.getContextAttributes();
    }

    /**
     * Returns the value of the attribute with the given name, or
     * <code>null</code> if no such attribute exists.
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
     * Removes the attribute with the given name.
     *
     * @param name the attribute name
     */
    public void removeAttribute(String name) {
        store.remove(name);
    }

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name  the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value) {
        store.put(name, value);
    }

    /**
     * Stores all attributes
     *
     * @throws IOException if an I/O error occurs saving the data
     */
    public void store() throws IOException {
        concPortlet.setContextAttributes(store);
        concPortlet.save();
    }

}
