package org.gridsphere.portlets.core.admin.users;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portletcontainer.impl.PortletSessionManager;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.services.core.user.User;
import org.gridsphere.services.core.user.UserManagerService;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * The
 */
public class SessionManagerPortlet extends ActionPortlet {

    private PortletSessionManager portletSessionMgr = PortletSessionManager.getInstance();
    private UserManagerService userManager = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        try {
            userManager = (UserManagerService) createPortletService(UserManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to get portlet manager instance", e);
        }
        DEFAULT_VIEW_PAGE = "doView";
    }


    public void doView(RenderFormEvent event) throws PortletException {

        PortletRequest req = event.getRenderRequest();
        List names = new ArrayList();

        for (HttpSession session : portletSessionMgr.getSessions()) {
            String uid = (String) session.getAttribute(SportletProperties.PORTLET_USER);
            if (uid != null) {
                User user = userManager.getUser(uid);
                names.add(user);
            }
        }
        int numSessions = portletSessionMgr.getNumSessions();

        req.setAttribute("num_sessions", String.valueOf(numSessions));

        req.setAttribute("uids", names);
        setNextState(req, "admin/session/viewsessions.jsp");
    }


}
