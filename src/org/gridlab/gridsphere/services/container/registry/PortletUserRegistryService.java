/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portletcontainer.RegisteredPortlet;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
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
public interface PortletUserRegistryService extends PortletService {

    public void login(PortletRequest request);

    public void logout(PortletRequest request);

    /**
     * Returns the collection of registered portlets
     *
     * @return the registered portlets
     */
    public List getPortlets(PortletRequest request);

    public void addPortlet(PortletRequest request, String concretePortletID);

    public void removePortlet(PortletRequest request, String concretePortletID);

    public PortletData getPortletData(PortletRequest request, String concretePortletID);

    public PortletSettings getPortletSettings(PortletRequest request, String concretePortletID);

    public void doAction(PortletRequest req, PortletResponse res);

}
