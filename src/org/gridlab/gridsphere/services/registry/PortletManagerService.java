/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.registry;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.event.WindowEvent;

import java.util.List;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The PortletRegistry service is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public interface PortletManagerService extends PortletService {

    public void initAllPortletWebApplications(PortletRequest req, PortletResponse res) throws PortletException;

    public void initPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws PortletException;

    public void destroyPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws PortletException;

    /**
     * Removes a portlet web application from the registry
     *
     * @param the web application name
     */
    public void removePortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws PortletException;

    public void installPortletWebApplication(String webApplicationName, PortletRequest req, PortletResponse res) throws PortletException;

    /**
     * Lists all the portlet web applications in the registry
     *
     * @return the list of web application names
     */
    public List listPortletWebApplications();

}
