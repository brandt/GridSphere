/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.roles;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.core.security.group.GroupManagerService;
import org.gridlab.gridsphere.services.core.security.group.impl.UserGroup;
import org.gridlab.gridsphere.services.core.security.role.RoleManagerService;

import javax.servlet.UnavailableException;
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
    private GroupManagerService groupManagerService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        log.debug("Entering initServices()");
        try {
            this.roleManagerService = (RoleManagerService) config.getContext().getService(RoleManagerService.class);
            this.groupManagerService = (GroupManagerService) config.getContext().getService(GroupManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        log.debug("Exiting initServices()");
        DEFAULT_HELP_PAGE = "admin/roles/help.jsp";
        DEFAULT_VIEW_PAGE = "doListRoles";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doListRoles(FormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        List roleList = this.roleManagerService.getRoles();
        req.setAttribute("roleList", roleList);
        List coreRolesList = new ArrayList();
        coreRolesList.add(PortletRole.USER.getName());
        coreRolesList.add(PortletRole.ADMIN.getName());
        coreRolesList.add(PortletRole.SUPER.getName());
        req.setAttribute("coreRoleList", coreRolesList);
        setNextState(req, ROLES_LIST);
    }

    public void doEditRole(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        String roleOid = evt.getAction().getParameter("roleOID");
        PortletRole role = null;
        if (roleOid != null) {
            role = roleManagerService.getRole(roleOid);
            HiddenFieldBean roidHF = evt.getHiddenFieldBean("roidHF");
            roidHF.setValue(roleOid);
            TextFieldBean roleNameTF = evt.getTextFieldBean("roleNameTF");
            roleNameTF.setValue(role.getName());
            TextFieldBean roleDescTF = evt.getTextFieldBean("roleDescTF");
            roleDescTF.setValue(role.getDescription());
        }
        setNextState(req, ROLES_EDIT);
    }

    public void doDeleteRole(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        String roleOid = evt.getAction().getParameter("roleOID");
        PortletRole role = roleManagerService.getRole(roleOid);
        if (roleOid != null) {
            // check if users with this role exists before deleting it
            // if so then chanege the role to be the predefined GS role with the same priority
            List users = roleManagerService.getUsersInRole(role);
            if (!users.isEmpty()) {
                Iterator it = users.iterator();
                while (it.hasNext()) {
                    User u = (User)it.next();
                    List groupEntries = groupManagerService.getGroupEntries(u);
                    Iterator geIt = groupEntries.iterator();
                    while (geIt.hasNext()) {
                        UserGroup ge = (UserGroup)geIt.next();
                        if (ge.getRole().getName().equalsIgnoreCase(role.getName())) {
                            UserGroup groupReq = groupManagerService.editGroupEntry(ge);
                            groupManagerService.saveGroupEntry(groupReq);
                        }
                    }
                }
            }
            roleManagerService.deleteRole(role);
            createSuccessMessage(evt, this.getLocalizedText(req, "ROLE_DELETE_MSG") + ": " + role.getName());

        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void doSaveRole(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        TextFieldBean roleNameTF = evt.getTextFieldBean("roleNameTF");
        // check if role name is already taken
        if (roleManagerService.getRoleByName(roleNameTF.getValue()) != null) {
            createErrorMessage(evt, this.getLocalizedText(req, "ROLE_EXISTS_MSG"));
            return;
        }
        TextFieldBean roleDescTF = evt.getTextFieldBean("roleDescTF");

        HiddenFieldBean roidHF = evt.getHiddenFieldBean("roidHF");
        PortletRole role = roleManagerService.getRole(roidHF.getValue());
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

    public void doSaveNewRole(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        TextFieldBean roleNameTF = evt.getTextFieldBean("roleNameTF");

        // check if role name is already taken
        if (roleManagerService.getRoleByName(roleNameTF.getValue()) != null) {
            createErrorMessage(evt, this.getLocalizedText(req, "ROLE_EXISTS_MSG"));
            return;
        }
        PortletRole role = new PortletRole(roleNameTF.getValue());
        roleManagerService.saveRole(role);
        createSuccessMessage(evt, this.getLocalizedText(req, "ROLE_CREATE_MSG") + ": " + roleNameTF.getValue());
    }

    private void createErrorMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        String msgOld = msgBox.getValue();
        msgBox.setValue((msgOld!=null?msgOld:"")+msg);
    }

    private void createSuccessMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_SUCCESS);
        msgBox.setValue(msg);
    }


}
