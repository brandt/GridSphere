/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletSettings;
import org.gridlab.gridsphere.portlet.impl.GuestUser;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;


import javax.servlet.UnavailableException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginPortlet extends AbstractPortlet {

    private UserManagerService userService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        PortletContext context = config.getContext();
        try {
            userService = (UserManagerService)context.getService(UserManagerService.class);
        } catch (PortletServiceUnavailableException e) {
            throw new UnavailableException("Service instance unavailable: " + e.toString());
        } catch (PortletServiceNotFoundException e) {
            throw new UnavailableException("Service instance not found: " + e.toString());
        }
        System.err.println("init() in LoginPortlet");
    }

    public void actionPerformed(ActionEvent evt) {
        PortletAction _action = evt.getAction();

        if (_action instanceof DefaultPortletAction) {
            DefaultPortletAction action = (DefaultPortletAction)_action;
            if (action.getName().equals(PortletAction.LOGIN)) {
                PortletRequest req = evt.getPortletRequest();

                String name = (String)req.getAttribute("name");
                String password = (String)req.getAttribute("password");

                if ((name == "portal") || (password == "shmortal")) {
                    //User user = userService.loginUser(name);
                    SportletUser user = new SportletUserImpl();
                    user.setUserID(name);
                    user.setFullName("Joe B. Portal");
                    user.setEmailAddress("joe@portal.com");

                    PortletSession session = req.getPortletSession(true);
                    session.setAttribute(GridSphereProperties.USER, (User)user);
                }
            }
        }

    }

    public void service(PortletRequest request, PortletResponse response) throws PortletException, IOException {

        request.setAttribute("portletRequest", request);
        request.setAttribute("portletResponse", response);
        request.setAttribute("portletSettings", portletSettings);

        User user = request.getUser();
        if (user instanceof GuestUser) {
            getPortletConfig().getContext().include("/jsp/login.jsp", request, response);
        } else {
            getPortletConfig().getContext().include("/jsp/showstatus.jsp", request, response);
        }

    }

}
