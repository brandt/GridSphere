/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: RoleManagerPortlet.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portlets.core.admin.roles;

import org.gridsphere.portlet.User;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portletui.beans.*;
import org.gridsphere.services.core.security.role.RoleManagerService;
import org.gridsphere.services.core.security.role.PortletRole;

import javax.portlet.PortletException;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class RoleManagerPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String ROLES_LIST = "admin/roles/doViewRolesList.jsp";
    public static final String ROLES_EDIT = "admin/roles/doEditRole.jsp";
    public static final String ROLES_CREATE = "admin/roles/doCreateRole.jsp";

    // Portlet services
    private RoleManagerService roleManagerService = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        roleManagerService = (RoleManagerService) createPortletService(RoleManagerService.class);
        DEFAULT_HELP_PAGE = "admin/roles/help.jsp";
        DEFAULT_VIEW_PAGE = "doListRoles";
    }

    public void doListRoles(RenderFormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getRenderRequest();
        List roleList = this.roleManagerService.getRoles();
        req.setAttribute("roleList", roleList);
        List coreRolesList = new ArrayList();
        coreRolesList.add(PortletRole.USER.getName());
        coreRolesList.add(PortletRole.ADMIN.getName());

        req.setAttribute("coreRoleList", coreRolesList);
        setNextState(req, ROLES_LIST);
    }

    public void doEditRole(ActionFormEvent evt) {
        PortletRequest req = evt.getActionRequest();
        String roleName = evt.getAction().getParameter("roleName");

        PortletRole role = null;
        if (roleName != null) {
            role = roleManagerService.getRole(roleName);
            HiddenFieldBean roleHF = evt.getHiddenFieldBean("roleHF");
            roleHF.setValue(roleName);
            TextFieldBean roleNameTF = evt.getTextFieldBean("roleNameTF");
            roleNameTF.setValue(role.getName());
            TextFieldBean roleDescTF = evt.getTextFieldBean("roleDescTF");
            roleDescTF.setValue(role.getDescription());
        } else {
            HiddenFieldBean isNewRoleHF = evt.getHiddenFieldBean("isNewRoleHF");
            isNewRoleHF.setValue("true");
        }
        setNextState(req, ROLES_EDIT);
    }

    public void doDeleteRole(ActionFormEvent evt) {
        PortletRequest req = evt.getActionRequest();
        String roleName = evt.getAction().getParameter("roleName");
        PortletRole role = roleManagerService.getRole(roleName);
        if (roleName != null) {
            // remove users in role first
            List users = roleManagerService.getUsersInRole(role);
            if (!users.isEmpty()) {
                Iterator it = users.iterator();
                while (it.hasNext()) {
                    User user = (User)it.next();
                    roleManagerService.deleteUserInRole(user, role);
                }
            }
            roleManagerService.deleteRole(role);
            createSuccessMessage(evt, this.getLocalizedText(req, "ROLE_DELETE_MSG") + ": " + role.getName());

        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void doSaveRole(ActionFormEvent evt) {
        PortletRequest req = evt.getActionRequest();
        boolean isNewRole = false;
        HiddenFieldBean isNewRoleHF = evt.getHiddenFieldBean("isNewRoleHF");
        if (isNewRoleHF.getValue().equals("true")) isNewRole = true;

        TextFieldBean roleNameTF = evt.getTextFieldBean("roleNameTF");
        // check if role name is already taken
        if ((roleManagerService.getRole(roleNameTF.getValue()) != null) && (isNewRole)) {
            createErrorMessage(evt, this.getLocalizedText(req, "ROLE_EXISTS_MSG"));
            setNextState(req, ROLES_EDIT);
            return;
        }
        TextFieldBean roleDescTF = evt.getTextFieldBean("roleDescTF");

        HiddenFieldBean roleHF = evt.getHiddenFieldBean("roleHF");
        PortletRole role = roleManagerService.getRole(roleHF.getValue());
        if (role != null) {
            role.setName(roleNameTF.getValue());
            role.setDescription(roleDescTF.getValue());
        } else {
            role = new PortletRole(roleNameTF.getValue());
            role.setDescription(roleDescTF.getValue());
        }
        roleManagerService.saveRole(role);
        createSuccessMessage(evt, this.getLocalizedText(req, "ROLE_CREATE_MSG") + ": " + role.getName());
    }

}
