/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;

import java.util.Collection;
import java.util.Set;
import java.util.List;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The PortletRegistry service is responsible for reading in the associated portlet.xml file and
 * creating a RegisteredPortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of RegisteredPortlet objects.
 */
public interface PortletRegistryService extends PortletService {

    /**
     * Returns the collection of registered portlets
     *
     * @return the registered portlets
     */
    public Collection getRegisteredPortlets();

    /**
     * Return the registered portlet IDs
     *
     * @return the registered portlet IDs
     */
    public Set getRegisteredPortletIDs();

    /**
     * Return a registered portlet given its identifier
     *
     * @return the portletID
     */
    public RegisteredPortlet getRegisteredPortlet(String portletID);

    public Collection getRegisteredePortletsByGroup(PortletGroup group);

    public Collection getRegisteredPortletsbyRole(PortletRole role);

    public AbstractPortlet getActivePortlet(String portletID);

    /**
     * Registers a portlet with the PortletRegistryService
     *
     * @param registeredPortlet the registered portlet
     * @return the portletID
     */
    public String registerPortlet(RegisteredPortlet registeredPortlet);

    /**
     * Unregisters a portlet with the PortletRegistryService
     *
     * @param portletID the portlet ID
     */
    public void unregisterPortlet(String portletID);

}
