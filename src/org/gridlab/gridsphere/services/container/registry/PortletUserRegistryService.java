/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletConfig;
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
public interface PortletUserRegistryService extends PortletService {

    public void loginPortlets(PortletRequest request);

    public void logoutPortlets(PortletRequest request);

    public void reloadPortlets() throws PortletRegistryServiceException;

    public void initializePortlets();

    public void shutdownPortlets();

    /**
     * Returns the collection of registered portlets
     *
     * @return the registered portlets
     */
    public List getPortlets(PortletRequest request);

    public void addPortlet(PortletRequest request, String concretePortletID);

    public void removePortlet(PortletRequest request, String concretePortletID);

    public PortletConfig getPortletConfig(ServletConfig servletConfig, String concretePortletID);

    public PortletData getPortletData(PortletRequest request, String concretePortletID);

    public PortletSettings getPortletSettings(PortletRequest request, String concretePortletID);

    //public List getSupportedModes(PortletRequest request, String concretePortletID);

}
