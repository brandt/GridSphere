

package org.gridlab.gridsphere.portlets.core.admin.portlets;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.services.core.user.UserSessionManager;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.portletcontainer.PortletSessionManager;

import javax.servlet.UnavailableException;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * The
 */
public class SessionManagerPortlet extends ActionPortlet {

    private UserSessionManager userSessionMgr = null;
    private PortletSessionManager portletSessionMgr = null;

    private UserManagerService userManager = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);

        this.userManager = (UserManagerService) config.getContext().getSpringService("UserManagerService");
        this.userSessionMgr = (UserSessionManager) config.getContext().getSpringService("UserSessionManager");
        portletSessionMgr = PortletSessionManager.getInstance();
        DEFAULT_VIEW_PAGE = "showSessions";
    }


    public void showSessions(FormEvent event) throws PortletException {

        PortletRequest req = event.getPortletRequest();
        List uids = userSessionMgr.getUserIds();
        List names = new ArrayList();
        Iterator it = uids.iterator();
        while (it.hasNext()) {
            String uid = (String)it.next();
            User user = userManager.getUser(uid);
            names.add(user);
        }
        int numSessions = portletSessionMgr.getNumSessions();

        req.setAttribute("num_sessions", String.valueOf(numSessions));

        req.setAttribute("uids", names);
        setNextState(req, "admin/session/view.jsp");


    }


}
