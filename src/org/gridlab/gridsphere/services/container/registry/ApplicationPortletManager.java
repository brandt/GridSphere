/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry;

import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.PortletWrapper;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The PortletRegistry service is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public class ApplicationPortletManager {

    private static ApplicationPortletManager instance = new ApplicationPortletManager();

    private PortletRegistryManager registry = PortletRegistryManager.getInstance();

    private ApplicationPortletManager() {}

    public static ApplicationPortletManager getInstance() {
        return instance;
    }

    public void initApplicationPortlets(HttpServletRequest req, HttpServletResponse res) {
        Collection appPortlets = registry.getAllApplicationPortlets();
        PortletWrapper portletWrapper = null;
        try {
            Iterator it = appPortlets.iterator();
            while (it.hasNext()) {
                ApplicationPortlet appPortlet = (ApplicationPortlet)it.next();
                portletWrapper = appPortlet.getPortletWrapper();
                portletWrapper.init(req, res);
            }
        } catch (Exception e) {
            //throw new PortletLifecycleException(e);
        }
    }

    public void destroyApplicationPortlets(HttpServletRequest req, HttpServletResponse res) {
        Collection appPortlets = registry.getAllApplicationPortlets();
        PortletWrapper portletWrapper = null;
        try {
            Iterator it = appPortlets.iterator();
            while (it.hasNext()) {
                ApplicationPortlet appPortlet = (ApplicationPortlet)it.next();
                portletWrapper = appPortlet.getPortletWrapper();
                portletWrapper.destroy(req, res);
            }
        } catch (Exception e) {
            //throw new PortletLifecycleException(e);
        }
    }

    public void initApplicationPortlet(String applicationPortletID, HttpServletRequest req, HttpServletResponse res) {
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(applicationPortletID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        try {
            wrapper.init(req, res);
        } catch (Exception e ) {
           //throw new PortletLifecycleException(e);
        }
    }

    public void destroyApplicationPortlet(String applicationPortletID, HttpServletRequest req, HttpServletResponse res) {
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(applicationPortletID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        try {
            wrapper.destroy(req, res);
        } catch (Exception e ) {
           //throw new PortletLifecycleException(e);
        }
    }

}
