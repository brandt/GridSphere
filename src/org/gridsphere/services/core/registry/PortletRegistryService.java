package org.gridsphere.services.core.registry;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridsphere.portletcontainer.PortletWebApplication;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface PortletRegistryService extends PortletService {

    public void addWebApplication(PortletWebApplication webApp);
    
    public PortletWebApplication getWebApplication(String webappName);

    public void addApplicationPortlet(ApplicationPortlet appPortlet);

    public void removeApplicationPortlet(ApplicationPortlet applicationPortlet);

    public ApplicationPortlet getApplicationPortlet(String applicationPortletID);

    public Collection<ApplicationPortlet> getAllApplicationPortlets();

    public List<ApplicationPortlet> getApplicationPortlets(String webApplicationName);

    public String getApplicationPortletID(String concretePortletID);

    public void logRegistry();
}
