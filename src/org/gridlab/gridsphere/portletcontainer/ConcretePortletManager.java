/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.PortletWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The PortletRegistry service is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public class ConcretePortletManager {

    private static ApplicationPortletManager appPortletMgr = ApplicationPortletManager.getInstance();
    private static ConcretePortletManager concPortletMgr = new ConcretePortletManager();
    private static PortletRegistryManager registry = PortletRegistryManager.getInstance();

    private ConcretePortletManager() {}

    public static ConcretePortletManager getInstance() {
        return concPortletMgr;
    }

    public void initConcretePortlets(HttpServletRequest req, HttpServletResponse res) {

        // First initialize the application portlets
        appPortletMgr.initApplicationPortlets(req, res);

        // Second initialize all concrete portlets for each application portlet
        Collection appPortlets = registry.getAllApplicationPortlets();
        org.gridlab.gridsphere.portletcontainer.PortletWrapper portletWrapper = null;
        try {
            Iterator it = appPortlets.iterator();
            while (it.hasNext()) {
                ApplicationPortlet appPortlet = (ApplicationPortlet)it.next();
                portletWrapper = appPortlet.getPortletWrapper();
                List concPortlets = appPortlet.getConcretePortlets();
                Iterator concIt = concPortlets.iterator();
                PortletSettings settings = null;
                while (concIt.hasNext()) {
                    ConcretePortlet concPortlet = (ConcretePortlet)concIt.next();
                    settings = concPortlet.getSportletSettings();
                    portletWrapper.initConcrete(settings, req, res);
                }
            }
        } catch (Exception e) {
            //throw new PortletLifecycleException(e);
        }
    }

    public void destroyConcretePortlets(HttpServletRequest req, HttpServletResponse res) {

        // First destroy all concrete portlets for each application portlet
        Collection appPortlets = registry.getAllApplicationPortlets();
        org.gridlab.gridsphere.portletcontainer.PortletWrapper portletWrapper = null;
        try {
            Iterator it = appPortlets.iterator();
            while (it.hasNext()) {
                ApplicationPortlet appPortlet = (ApplicationPortlet)it.next();
                portletWrapper = appPortlet.getPortletWrapper();
                List concPortlets = appPortlet.getConcretePortlets();
                Iterator concIt = concPortlets.iterator();
                PortletSettings settings = null;
                while (concIt.hasNext()) {
                    ConcretePortlet concPortlet = (ConcretePortlet)concIt.next();
                    settings = concPortlet.getSportletSettings();
                    portletWrapper.destroyConcrete(settings, req, res);
                }
            }
        } catch (Exception e) {
            //throw new PortletLifecycleException(e);
        }

        // Second destroy the application portlets
        appPortletMgr.destroyApplicationPortlets(req, res);
    }

    public void initConcretePortlet(String concretePortletID, HttpServletRequest req, HttpServletResponse res) {
        String appID = getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper portletWrapper = appPortlet.getPortletWrapper();
        ConcretePortlet concPortlet = appPortlet.getConcretePortlet(concretePortletID);
        PortletSettings settings = concPortlet.getSportletSettings();
        try {
            portletWrapper.initConcrete(settings, req, res);
        } catch (Exception e) {
            // do something here
        }
    }

    public void destroyConcretePortlet(String concretePortletID, HttpServletRequest req, HttpServletResponse res) {
        String appID = getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        ConcretePortlet concPortlet = appPortlet.getConcretePortlet(concretePortletID);
        PortletSettings settings = concPortlet.getSportletSettings();
        try {
            wrapper.destroyConcrete(settings, req, res);
        } catch (Exception e) {
            // do something here
        }
    }

    protected String getApplicationPortletID(String concretePortletID) {
        int i = concretePortletID.lastIndexOf(".");
        if (i < 0) return "";
        return concretePortletID.substring(0, i);
    }

}
