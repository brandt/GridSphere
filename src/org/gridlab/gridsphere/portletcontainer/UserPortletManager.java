/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.*;
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
public class UserPortletManager {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(UserPortletManager.class);

    private static ConcretePortletManager concPortletMgr = ConcretePortletManager.getInstance();
    private static UserPortletManager userPortletMgr = new UserPortletManager();
    private static PortletRegistryManager registry = PortletRegistryManager.getInstance();

    private UserPortletManager() {}

    public static UserPortletManager getInstance() {
        return userPortletMgr;
    }

    public void init(HttpServletRequest req, HttpServletResponse res) throws PortletException {
        concPortletMgr.initConcretePortlets(req, res);
    }

    public void destroy(HttpServletRequest req, HttpServletResponse res) throws PortletException {
        concPortletMgr.destroyConcretePortlets(req, res);
    }

    public void initUserPortlets(HttpServletRequest req, HttpServletResponse res) throws PortletException {
        User user = (User)req.getSession().getAttribute(GridSphereProperties.USER);
        //if (userPortletsHash
        // foreach portlet p in User u
        //   login(p.id, req, res);
    }

    public void destroyUserPortlets(HttpServletRequest req, HttpServletResponse res) throws PortletException {
            // foreach portlet p in User u
        //   logout(p.id, req, res);
    }

    public void initUserPortlet(String concretePortletID, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        login(concretePortletID, req, res);
    }

    public void service(String concretePortletID, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.service(concretePortletID, req, res);
    }

    public void actionPerformed(String concretePortletID, PortletAction action, HttpServletRequest req, HttpServletResponse res)
            throws PortletException {
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.actionPerformed(action, req, res);
    }

    public void doTitle(String concretePortletID, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.doTitle(req, res);
    }

    public void windowEvent(String concretePortletID, WindowEvent winEvent, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.windowEvent(winEvent, req, res);
    }

    public void destroyUserPortlet(String concretePortletID, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        logout(concretePortletID, req, res);
    }

    protected void login(String concretePortletID, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.login(req, res);
    }

    protected void logout(String concretePortletID, HttpServletRequest req, HttpServletResponse res) throws PortletException {
        String appID = registry.getApplicationPortletID(concretePortletID);
        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
        PortletWrapper wrapper = appPortlet.getPortletWrapper();
        wrapper.logout(req, res);
    }

}
