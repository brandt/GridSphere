/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.GuestUser;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.impl.PortletProperties;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.services.user.UserManagerService;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginPortlet extends AbstractPortlet {

    private UserManagerService userService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        PortletContext context = config.getContext();
        try {
            userService = (UserManagerService) context.getService(UserManagerService.class);
        } catch (PortletServiceUnavailableException e) {
            throw new UnavailableException("Service instance unavailable: " + e.toString());
        } catch (PortletServiceNotFoundException e) {
            throw new UnavailableException("Service instance not found: " + e.toString());
        }
        System.err.println("init() in LoginPortlet");
    }

    public void actionPerformed(ActionEvent evt) throws PortletException {
        PortletAction _action = evt.getAction();
        System.err.println("actionPerformed() in LoginPortlet");

        if (_action instanceof DefaultPortletAction) {
            DefaultPortletAction action = (DefaultPortletAction) _action;
            if (action.getName().equals(PortletProperties.LOGIN)) {
                PortletRequest req = evt.getPortletRequest();

                String username = (String) req.getParameter("username");
                String password = (String) req.getParameter("password");
                System.err.println("name = " + username + " password= " + password);

                // VERY IMPORTANT USER CHECK
                // CHECK INPUT FIELDS THOROUGHLY STRING LENGTH, VERIFY CREDENTIALS, ETC
                if ((username.trim().equals("portal")) && (password.trim().equals("schmortal"))) {
                    //User user = userService.loginUser(name);
                    SportletUser user = new SportletUserImpl();
                    user.setUserID(username);
                    user.setID("45");
                    user.setFullName("Joe B. Portal");
                    user.setEmailAddress("joe@portal.com");
                    user.setFamilyName("Portal");
                    user.setGivenName("Joey");

                    System.err.println("Creating new user");
                    PortletSession session = req.getPortletSession(true);
                    session.setAttribute(GridSphereProperties.USER, (User) user);

                    // now login to User Portal Registry service
                    //userPortletManager.loginPortlets(req);
                    // req.setAttribute(LOGIN)
                }
            }
        }
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PortletURI loginURI = response.createURI();
        DefaultPortletAction loginAction = new DefaultPortletAction(PortletProperties.LOGIN);
        loginURI.addAction(loginAction);
        request.setAttribute("login", loginURI.toString());
        User user = request.getUser();
        if (user instanceof GuestUser) {
            getPortletConfig().getContext().include("/jsp/login.jsp", request, response);
        } else {
            getPortletConfig().getContext().include("/jsp/viewuser.jsp", request, response);
        }
    }

    public void doEdit(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("in edit mode");
    }

    public void doTitle(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        User user = request.getUser();
        PrintWriter out = response.getWriter();
        if (user instanceof GuestUser) {
            out.println("Login");
        } else {
            out.println("Welcome, " + user.getFullName());
        }
    }

}
