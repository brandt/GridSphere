/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.ConfigParam;
import org.gridlab.gridsphere.portlet.AccessDeniedException;
import org.gridlab.gridsphere.portlet.PortletApplicationSettings;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.descriptor.ConcretePortletDescriptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The PortletApplicationSettings interface provides the portlet with the application's dynamic configuration.
 * The configuration holds information about the portlet application that is valid per concrete portlet
 * application for all users, and is maintained by the administrator. The portlet can therefore only read the
 * dynamic configuration. Only when the portlet is in CONFIGURE mode, it has write access to the dynamic
 * configuration data
 */
public class SportletApplicationSettings implements PortletApplicationSettings {

    protected ConcretePortlet concPortlet = null;
    protected Hashtable store = new Hashtable();

    /**
     * SportletApplicationSettings constructor
     * Create a PortletApplicationSettings object from a concrete portlet
     *
     * @param concPortlet the concrete portlet
     */
    public SportletApplicationSettings(ConcretePortlet concPortlet) {
        this.concPortlet = concPortlet;
        store = concPortlet.getPortletContextHash();
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
     * @throws IOException if the streaming causes an I/O problem
     */
    public void store() throws AccessDeniedException, IOException {
        ConcretePortletDescriptor concDescriptor = concPortlet.getConcretePortletDescriptor();
        Enumeration enum = store.elements();
        ArrayList list = new ArrayList();
        while (enum.hasMoreElements()) {
            String key = (String) enum.nextElement();
            String value = (String) store.get(key);
            ConfigParam parms = new ConfigParam(key, value);
            list.add(parms);
        }
        concDescriptor.setContextParamList(list);
        concPortlet.saveDescriptor(concDescriptor);
    }

}
