/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 17, 2003
 * Time: 11:57:10 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.PermissionDeniedException;
import org.gridlab.gridsphere.services.security.AuthenticationException;
import org.gridlab.gridsphere.services.security.password.Password;
import org.gridlab.gridsphere.services.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.security.password.PasswordBean;
import org.gridlab.gridsphere.services.security.password.PasswordInvalidException;
import org.gridlab.gridsphere.portlets.core.beans.UserManagerBean;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

public class UserManagerPortlet extends AbstractPortlet {

    private static PortletLog _log = SportletLog.getInstance(UserManagerPortlet.class);

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        _log.info("Exiting init()");
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        _log.info("Exiting initConcrete()");
    }

    public void actionPerformed(ActionEvent event) throws PortletException {
        _log.debug("Entering actionPerformed()");
        PortletAction action = event.getAction();
        //'Get the portlet request and response
        PortletRequest request = event.getPortletRequest();
        PortletResponse response = event.getPortletResponse();
        // Get instance of user manager bean
        UserManagerBean userManagerBean = getUserManagerBean(request, response);
        // Then perform given action
        userManagerBean.doAction(action);
        _log.debug("Exiting actionPerformed()");
    }

    public void doView(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        _log.debug("Entering doView()");
        // Get instance of user manager bean
        UserManagerBean userManagerBean = getUserManagerBean(request, response);
        // If no action performed, then perform list users
        if (userManagerBean.getActionPerformed() == null) {
            userManagerBean.doListUsers();
        }
        // Get next page to display
        String nextPage = userManagerBean.getNextPage();
        // Include the given page
        getPortletConfig().getContext().include(nextPage, request, response);
        _log.debug("Exiting doView()");
    }

    public void doEdit(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        _log.debug("Entering doEdit()");
        PrintWriter out = response.getWriter();
        out.println("Edit mode not yet implemented.");
        _log.debug("Exiting doEdit()");
    }

    public void doTitle(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        _log.debug("Entering doTitle()");
        // Get instance of user manager bean
        UserManagerBean userManagerBean = getUserManagerBean(request, response);
        // Get next title to display
        String title = userManagerBean.getNextTitle();
        // Print the given title
        response.getWriter().println(title);
        _log.debug("Exiting doTitle()");
    }

    private UserManagerBean getUserManagerBean(PortletRequest request,
                                               PortletResponse response)
            throws PortletException {
        _log.debug("Entering getUserManagerBean()");
        UserManagerBean userManagerBean =
                (UserManagerBean)request.getAttribute(UserManagerBean.ATTRIBUTE_USER_MANAGER_BEAN);
        if (userManagerBean == null) {
            _log.debug("Creating instance of user manager bean");
            PortletConfig config = getPortletConfig();
            userManagerBean = new UserManagerBean(config, request, response);
            request.setAttribute(UserManagerBean.ATTRIBUTE_USER_MANAGER_BEAN, userManagerBean);
        }
        _log.debug("Exiting getUserManagerBean()");
        return userManagerBean;
    }
}
