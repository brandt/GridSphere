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
    private UserManagerService userManagerService = null;
    private PasswordManagerService passwordManagerService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        PortletContext context = config.getContext();
        try {
            userManagerService = (UserManagerService) context.getService(UserManagerService.class);
            passwordManagerService = (PasswordManagerService) context.getService(PasswordManagerService.class);
        } catch (PortletServiceUnavailableException e) {
            throw new UnavailableException("Service instance unavailable: " + e.toString());
        } catch (PortletServiceNotFoundException e) {
            throw new UnavailableException("Service instance not found: " + e.toString());
        }
        System.err.println("init() in UserManagerPortlet");
    }

    public void actionPerformed(ActionEvent event) throws PortletException {
        PortletAction action = event.getAction();
        if (action instanceof DefaultPortletAction) {
            //'Get the portlet request and response
            PortletRequest request = event.getPortletRequest();
            PortletResponse response = event.getPortletResponse();
            // Create instance of user manager bean
            UserManagerBean userManagerBean = getUserManagerBean(request, response);
            //'Then process the appropriate action
            DefaultPortletAction defaultAction = (DefaultPortletAction)action;
            String actionName = action.toString();
            _log.debug("Action performed " + actionName);
            if (action.equals(UserManagerBean.ACTION_USER_LIST)) {
                userManagerBean.doListUsers();
            } else if (action.equals(UserManagerBean.ACTION_USER_VIEW)) {
                userManagerBean.doViewUser();
            } else if (action.equals(UserManagerBean.ACTION_USER_EDIT)) {
                userManagerBean.doEditUser();
            } else if (action.equals(UserManagerBean.ACTION_USER_EDIT_CONFIRM)) {
                userManagerBean.doConfirmEditUser();
            } else if (action.equals(UserManagerBean.ACTION_USER_DELETE)) {
                userManagerBean.doDeleteUser();
            } else if (action.equals(UserManagerBean.ACTION_USER_DELETE_CONFIRM)) {
                userManagerBean.doConfirmDeleteUser();
            } else {
                userManagerBean.doListUsers();
            }
        }
    }

    public void doView(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        UserManagerBean userManagerBean = getUserManagerBean(request, response);
        String nextJspPage = userManagerBean.getNextJspPage();
        getPortletConfig().getContext().include(nextJspPage, request, response);
    }

    public void doEdit(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("Edit mode not yet implemented.");
    }

    public void doTitle(PortletRequest request, PortletResponse response)
            throws PortletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("User Manager Portlet");
    }
    private UserManagerBean getUserManagerBean(PortletRequest request,
                                               PortletResponse response)
            throws PortletException {
        UserManagerBean userManagerBean =
                (UserManagerBean)request.getAttribute(UserManagerBean.ATTRIBUTE_USER_MANAGER_BEAN);
        if (userManagerBean == null) {
            PortletConfig config = getPortletConfig();
            userManagerBean = new UserManagerBean(config, request, response);
            request.setAttribute(UserManagerBean.ATTRIBUTE_USER_MANAGER_BEAN, userManagerBean);
        }
        return userManagerBean;
    }
}
