/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;

import java.util.Collection;
import java.util.Set;
import java.util.List;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The PortletRegistry service is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public interface PortletRegistryService extends PortletService {

    public void loadPortlets() throws PortletRegistryServiceException;

    /**
     * Returns the collection of application portlets
     *
     * @return the application portlets
     */
    public List getApplicationPortlets();

    /**
     * Return a application portlet given its identifier
     *
     * @param applicationPortletID the application portlet ID
     * @return the application portlet
     */
    public ApplicationPortlet getApplicationPortlet(String applicationPortletID);

    /**
     * Returns the collection of concrete portlets
     *
     * @return the concrete portlets
     */
    public List getConcretePortlets();

    /**
     * Returns the collection of locally managed registered portlets
     *
     * @return the local concrete portlets
     */
    public List getLocalConcretePortlets();

    /**
     * Return a concrete portlet given its identifier
     *
     * @param concretePortletID the concrete portlet ID
     * @return the concrete portlet
     */
    public ConcretePortlet getConcretePortlet(String concretePortletID);

    /**
     * Return an active portlet that acts as a wrapper for an abstract portlet
     *
     */
    public AbstractPortlet getActivePortlet(String concretePortletID);

    /**
     * Registers a application portlet with the PortletRegistryService
     *
     * @param registryID the string identifier for this ApplicationPortlet
     * @param registeredPortlet the registered portlet
     * @return the portletID
     */
    public String registerApplicationPortlet(String registryID, ApplicationPortlet appPortlet);

    /**
     * Registers a list of application portlets with the PortletRegistryService
     *
     * @param registryID the string identifier for this ApplicationPortlet
     * @param a List containing ApplicationPortlet components
     * @return the portletID
     */
    public void registerApplicationPortlets(String registryID, List applicationPortlets);

    /**
     * Unregisters a portlet application with the PortletRegistryService
     *
     * @param registryID the string identifier for this ApplicationPortlet
     * @param portletID the portlet ID
     */
    public void unregisterApplicationPortlet(String registryID, String portletID);

}
