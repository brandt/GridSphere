package org.gridsphere.services.core.registry;

import org.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridsphere.portletcontainer.PortletWebApplication;
import org.gridsphere.portlet.service.PortletService;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:jnovotny@ucsd.edu">Jason Novotny</a>
 * @version $Id$
 */
public interface PortletRegistryService extends PortletService {

    public void addWebApplication(PortletWebApplication webApp);
    
    public PortletWebApplication getWebApplication(String webappName);

    public void addApplicationPortlet(ApplicationPortlet appPortlet);

    public void removeApplicationPortlet(ApplicationPortlet applicationPortlet);

    public ApplicationPortlet getApplicationPortlet(String applicationPortletID);

    public Collection getAllApplicationPortlets();

    public List getApplicationPortlets(String webApplicationName);

    public String getApplicationPortletID(String concretePortletID);

    public void logRegistry();
}
