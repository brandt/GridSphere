/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.registry;

import org.gridlab.gridsphere.portlet.PortletAction;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.PortletWrapper;
import org.gridlab.gridsphere.services.container.registry.impl.PortletRegistryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The PortletRegistryService acts as a repository for portlets and makes them available to the portlet
 * container. The PortletRegistry service is responsible for reading in the associated portlet.xml file and
 * creating a ConcretePortlet object which represents the portlet. The PortletRegistryService maintains
 * a Set of RegisteredPortlets and provides operations for the registration, unregistration and querying
 * of ConcretePortlet objects.
 */
public class UserPortletManager {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(UserPortletManager.class);

    private static ConcretePortletManager concPortletMgr = ConcretePortletManager.getInstance();
    private static UserPortletManager userPortletMgr = new UserPortletManager();
    private static PortletRegistryManager registry = PortletRegistryManager.getInstance();

    private UserPortletManager() {}

    public static UserPortletManager getInstance() {
        return userPortletMgr;
    }

    public void init(HttpServletRequest req, HttpServletResponse res) {
        log.info("in init()");
        concPortletMgr.initConcretePortlets(req, res);
    }

    public void destroy(HttpServletRequest req, HttpServletResponse res) {
        log.info("in destroy()");
        concPortletMgr.destroyConcretePortlets(req, res);
    }

    public void initUserPortlets(HttpServletRequest req, HttpServletResponse res) {
        // foreach portlet p in User u
        //   login(p.id, req, res);
    }

    public void destroyUserPortlets(HttpServletRequest req, HttpServletResponse res) {
            // foreach portlet p in User u
        //   logout(p.id, req, res);
    }

    public void initUserPortlet(String concretePortletID, HttpServletRequest req, HttpServletResponse res) {
        log.info("in initUserPortlet()");
        login(concretePortletID, req, res);
    }

    public void service(String concretePortletID, HttpServletRequest req, HttpServletResponse res) {
        log.info("in service()");
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        try {
            wrapper.service(concretePortletID, req, res);
        } catch (Exception e) {
            // do something here
        }
    }

    public void actionPerformed(String concretePortletID, PortletAction action, HttpServletRequest req, HttpServletResponse res) {
        log.info("in actionPerformed()");
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        try {
            wrapper.actionPerformed(action, req, res);
        } catch (Exception e) {
            // do something here
        }
    }

    public void doTitle(String concretePortletID, HttpServletRequest req, HttpServletResponse res) {
        log.info("in doTitle()");
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        try {
            wrapper.doTitle(req, res);
        } catch (Exception e) {
            // do something here
        }
    }

    public void destroyUserPortlet(String concretePortletID, HttpServletRequest req, HttpServletResponse res) {
        log.info("in destroyUserPortlet()");
        logout(concretePortletID, req, res);
    }

    protected void login(String concretePortletID, HttpServletRequest req, HttpServletResponse res) {
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        try {
            wrapper.login(req, res);
        } catch (Exception e) {
            // do something here
        }
    }

    protected void logout(String concretePortletID, HttpServletRequest req, HttpServletResponse res) {
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        try {
            wrapper.logout(req, res);
        } catch (Exception e) {
            // do something here
        }
    }

}
