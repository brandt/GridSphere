/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.registry;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;

import java.util.List;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The PortletRegistry service is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public interface PortletRegistryService extends PortletService {

    /**
     * Adds a portlet web application to the registry
     *
     * @param the web application name
     */
    public void addPortletWebApplication(User user, String webApplicationName);

    /**
     * Removes a portlet web application from the registry
     *
     * @param the web application name
     */
    public void removePortletWebApplication(User user, String webApplicationName);

    /**
     * Reloads a portlet web application from the registry
     *
     * @param the web application name
     */
    public void reloadPortletWebApplication(User user, String webApplicationName);

    /**
     * Lists all the portlet web applications in the registry
     *
     * @return the list of web application names
     */
    public List listPortletWebApplications();

}
