/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.descriptor.*;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;

import java.util.*;

/**
 * The PortletApplicationSettings interface provides the portlet with the application's dynamic configuration.
 * The configuration holds information about the portlet application that is valid per concrete portlet
 * application for all users, and is maintained by the administrator. The portlet can therefore only read the
 * dynamic configuration. Only when the portlet is in CONFIGURE mode, it has write access to the dynamic
 * configuration data
 */
public abstract class SportletApplicationSettings implements PortletApplicationSettings {

    protected Hashtable store = new Hashtable();
    protected PortletDeploymentDescriptor pdd = null;
    protected ConcretePortletApplication portletApp = null;
    protected boolean hasConfigurePermission = false;

    /**
     * SportletApplicationSettings constructor
     */
    public SportletApplicationSettings(PortletDeploymentDescriptor pdd, ConcretePortletApplication portletApp,
                                       boolean hasConfigurePermission) {
        this.portletApp = portletApp;
        this.hasConfigurePermission = hasConfigurePermission;

        // Stick <context-param> in store
  /*      Iterator contextParamsIt = portletApp.getContextParamList().iterator();
        while (contextParamsIt.hasNext()) {
            ConfigParam configParam = (ConfigParam)contextParamsIt.next();
            store.put(configParam.getParamName(), configParam.getParamValue());
        }*/
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
        if (!hasConfigurePermission) {
            throw new AccessDeniedException("User is unauthorized to remove portlet application settings");
        }
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
        if (!hasConfigurePermission) {
            throw new AccessDeniedException("User is unauthorized to set portlet application settings");
        }
        store.put(name, value);
    }

    /**
     * Stores all attributes.
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void store() throws AccessDeniedException {
        if (!hasConfigurePermission) {
            throw new AccessDeniedException("User is unauthorized to store portlet application settings");
        }
        Enumeration enum = store.elements();
        Vector list = new Vector();
/*        while (enum.hasMoreElements()) {
            String key = (String)enum.nextElement();
            String value = (String)store.get(key);
            ConfigParam parms = new ConfigParam(key, value);
            list.add(parms);
        }
        portletApp.setContextParamList(list);
        pdd.save();*/
    }

}
