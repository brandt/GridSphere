/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.PortletWrapper;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.event.WindowEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;
import java.util.Map;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The PortletRegistry service is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public class PortletEventDispatcher {

    private PortletLog log = SportletLog.getInstance(PortletEventDispatcher.class);

    private PortletRegistryManager registry = PortletRegistryManager.getInstance();
    private PortletRequest req = null;
    private PortletResponse res = null;

    public PortletEventDispatcher(PortletRequest req, PortletResponse res) {
        this.req = req;
        this.res = res;
    }

    public void portletLogin(String concretePortletID) throws PortletException {
        log.info("in portletLogin " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.login(req, res);
    }

    public void portletLogout(String concretePortletID) throws PortletException {
        log.info("in portletLogout " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.logout(req, res);
    }

    public void portletService(String concretePortletID) throws PortletException {
        log.info("in portletService " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.service(concretePortletID, req, res);
    }

    public void portletAction(String concretePortletID, DefaultPortletAction action) throws PortletException {
        log.info("in portletAction " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.actionPerformed(action, req, res);
    }

    public void portletTitle(String concretePortletID) throws PortletException {
        log.info("in portletTitle " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.doTitle(req, res);
    }

    public void portletWindowEvent(String concretePortletID, WindowEvent winEvent) throws PortletException {
        log.info("in portletWindowEvent " + concretePortletID);
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.windowEvent(winEvent, req, res);
    }
}
