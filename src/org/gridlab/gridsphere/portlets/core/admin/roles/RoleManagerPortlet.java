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
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;

import javax.servlet.UnavailableException;
import java.util.ArrayList;
import java.util.List;

public class RoleManagerPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String ROLES_LIST = "admin/roles/doViewRolesList.jsp";
    public static final String ROLES_EDIT = "admin/roles/doEditRole.jsp";
    public static final String ROLES_CREATE = "admin/roles/doCreateRole.jsp";

    // Portlet services
    private AccessControlManagerService aclManagerService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);

        this.aclManagerService = (AccessControlManagerService) this.getConfig().getContext().getSpringService("AccessControlManagerService");
                
        DEFAULT_HELP_PAGE = "admin/roles/help.jsp";
        DEFAULT_VIEW_PAGE = "doListRoles";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doListRoles(FormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        List roleList = this.aclManagerService.getRoles();
        req.setAttribute("roleList", roleList);
        List coreRolesList = new ArrayList();
        coreRolesList.add(PortletRole.GUEST.getName());
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
            role = aclManagerService.getRole(roleOid);
            HiddenFieldBean roidHF = evt.getHiddenFieldBean("roidHF");
            roidHF.setValue(roleOid);
            TextFieldBean roleNameTF = evt.getTextFieldBean("roleNameTF");
            roleNameTF.setValue(role.getName());
        }

        ListBoxBean roleListLB = evt.getListBoxBean("roleListLB");
        ListBoxItemBean guest = new ListBoxItemBean();
        guest.setValue(PortletRole.GUEST.getName());
        ListBoxItemBean user = new ListBoxItemBean();
        user.setValue(PortletRole.USER.getName());
        ListBoxItemBean admin = new ListBoxItemBean();
        admin.setValue(PortletRole.ADMIN.getName());
        ListBoxItemBean sup = new ListBoxItemBean();
        sup.setValue(PortletRole.SUPER.getName());
        if (role != null) {
            if (role.getPriority() == PortletRole.GUEST.getPriority()) guest.setSelected(true);
            if (role.getPriority() == PortletRole.USER.getPriority()) user.setSelected(true);
            if (role.getPriority() == PortletRole.ADMIN.getPriority()) admin.setSelected(true);
            if (role.getPriority() == PortletRole.SUPER.getPriority()) sup.setSelected(true);
        }
        roleListLB.addBean(guest);
        roleListLB.addBean(user);
        roleListLB.addBean(admin);
        roleListLB.addBean(sup);

        setNextState(req, ROLES_EDIT);
    }

    public void doDeleteRole(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        String roleOid = evt.getAction().getParameter("roleOID");
        PortletRole role = aclManagerService.getRole(roleOid);
        if (roleOid != null) {
            aclManagerService.deleteRole(role);
            createSuccessMessage(evt, this.getLocalizedText(req, "ROLE_DELETE_MSG") + ": " + role.getName());
        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void doSaveRole(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        TextFieldBean roleNameTF = evt.getTextFieldBean("roleNameTF");
        // check if role name is already taken
        if (aclManagerService.getRoleByName(roleNameTF.getValue()) != null) {
            createErrorMessage(evt, this.getLocalizedText(req, "ROLE_EXISTS_MSG"));
            return;
        }

        ListBoxBean priorityLB = evt.getListBoxBean("roleListLB");
        String priority = priorityLB.getSelectedValue();
        HiddenFieldBean roidHF = evt.getHiddenFieldBean("roidHF");
        PortletRole role = aclManagerService.getRole(roidHF.getValue());
        if (role != null) {
            role.setName(roleNameTF.getValue());
            role.setID(PortletRole.toPortletRole(priority).getPriority());
        } else {
            role = new PortletRole(roleNameTF.getValue(), priority);
        }
        aclManagerService.saveRole(role);
        createSuccessMessage(evt, this.getLocalizedText(req, "ROLE_CREATE_MSG") + ": " + role.getName());
    }

    public void doNewRole(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        ListBoxBean priorityLB = evt.getListBoxBean("priorityLB");
        ListBoxItemBean guestLB = new ListBoxItemBean();
        guestLB.setValue(PortletRole.GUEST.getText(req.getLocale()));
        ListBoxItemBean userLB = new ListBoxItemBean();
        userLB.setValue(PortletRole.USER.getText(req.getLocale()));
        ListBoxItemBean adminLB = new ListBoxItemBean();
        adminLB.setValue(PortletRole.ADMIN.getText(req.getLocale()));
        ListBoxItemBean superLB = new ListBoxItemBean();
        superLB.setValue(PortletRole.SUPER.getText(req.getLocale()));
        priorityLB.addBean(guestLB);
        priorityLB.addBean(userLB);
        priorityLB.addBean(adminLB);
        priorityLB.addBean(superLB);
        setNextState(req, ROLES_CREATE);
    }

    public void doSaveNewRole(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        TextFieldBean roleNameTF = evt.getTextFieldBean("roleNameTF");
        ListBoxBean priorityLB = evt.getListBoxBean("priorityLB");
        String priority = priorityLB.getSelectedValue();
        int rp = Integer.valueOf(priority).intValue();
        // check if role name is already taken
        if (aclManagerService.getRoleByName(roleNameTF.getValue()) != null) {
            createErrorMessage(evt, this.getLocalizedText(req, "ROLE_EXISTS_MSG"));
            return;
        }
        aclManagerService.createRole(roleNameTF.getValue(), rp);
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
