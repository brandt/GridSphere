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
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.LoginService;
import org.gridlab.gridsphere.services.security.AuthenticationException;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Iterator;

public class LoginPortlet extends AbstractPortlet {

    private UserManagerService userService = null;

    public static final String LOGIN_ERROR_FLAG = "LOGIN_ERROR";
    public static final Integer LOGIN_ERROR_UNKNOWN = new Integer(-1);

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
            log.info("YO WE IN ACTION PERFORMED!");
            DefaultPortletAction action = (DefaultPortletAction) _action;
            if (action.getName().equals(SportletProperties.LOGIN)) {
                log.info("YO WE IN DEFAULT ACTION!");
                PortletRequest req = evt.getPortletRequest();

                String username = (String) req.getParameter("username");
                String password = (String) req.getParameter("password");
                System.err.println("name = " + username + " password= " + password);

                // Retrieve portlet session from request
                PortletSession session = req.getPortletSession(true);

                // VERY IMPORTANT USER CHECK
                // CHECK INPUT FIELDS THOROUGHLY STRING LENGTH, VERIFY CREDENTIALS, ETC

                /*
                Issues to deal with:
                Do we support username/password auth?
                where does User object come from?
                Can't just call UserManagerService.getUser(loginname) since anyone  could use this to
                hijack user objects
                The only calls the UserManagerService should be allowed to provide that
                return User objects should require a requestingUser object to use to make ACL
                decisions.
                Still need some kind of getUser(name, password) deal (or passes password to retrieve credentials.
                Another possibility is to send a LOGIN action and have the framework take care of retriveing the
                User object and putting it in the session. Then those service methods required to do this
                are never exposed in the interface. e.g use the manager class which the service wraps.
                General problem of hiding access to certain classes needs to be resolved.

                One simple solution to avoid coding authorization service is to heve the two UserManagerService
                methods:
                public void login(username, password) which sets the session with the user object internally
                public void logout(User) which removes the user from the session.
                */

                if ((username.trim().equals("portal")) && (password.trim().equals("schmortal"))) {
                    log.info("YO WE IN PORTAL!");
                    //User user = userService.loginUser(nam, password);
                    SportletUser user = new SportletUserImpl();
                    user.setUserID(username);
                    user.setID("45");
                    user.setFullName("Joe B. Portal");
                    user.setEmailAddress("joe@portal.com");
                    user.setFamilyName("Portal");
                    user.setGivenName("Joey");

                    System.err.println("Creating new user");
                    session.setAttribute(GridSphereProperties.USER, (User) user);
                } else {
                    log.info("YO WE NOT IN PORTAL!");
                    try {
                        // Retrieve login parameters from request
                        //Map parameters = req.getParameterMap();
                        // Attempt to login portlet user
                        //User user = loginService.login(parameters);
                        User user = userService.login(username, password);
                        // Attach portlet user to portlet session
                        session.setAttribute(GridSphereProperties.USER, user);
                    } catch (AuthenticationException err) {
                        if(log.isDebugEnabled()) {
                            log.debug(err.getMessage());
                        }
                        req.setAttribute(LOGIN_ERROR_FLAG, LOGIN_ERROR_UNKNOWN);
                    }
                }
            }
        }
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        PortletURI loginURI = response.createURI();
        DefaultPortletAction loginAction = new DefaultPortletAction(SportletProperties.LOGIN);
        loginURI.addAction(loginAction);
        request.setAttribute("login", loginURI.toString());
        User user = request.getUser();
        if (user instanceof GuestUser) {
            log.info("YO WE IN GUEST USER!");
            getPortletConfig().getContext().include("/jsp/login.jsp", request, response);
        } else {
            log.info("YO WE IN VIEW USER!");
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
