/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.users;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.HiddenFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.core.security.password.Password;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.security.acl.GroupRequest;

import javax.servlet.UnavailableException;
import java.util.List;

public class UserManagerPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String DO_VIEW_USER_LIST = "admin/users/doViewUserList.jsp";
    public static final String DO_VIEW_USER_VIEW = "admin/users/doViewUserView.jsp";
    public static final String DO_VIEW_USER_EDIT = "admin/users/doViewUserEdit.jsp";
    public static final String DO_VIEW_USER_DELETE = "admin/users/doViewUserDelete.jsp";
    public static final String DO_VIEW_USER_DELETE_CONFIRM = "admin/users/doViewUserDeleteConfirm.jsp";

    // Portlet services
    private UserManagerService userManagerService = null;
    private PasswordManagerService passwordManagerService = null;
    private AccessControlManagerService aclManagerService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        this.log.debug("Entering initServices()");
        try {
            this.userManagerService = (UserManagerService)config.getContext().getService(UserManagerService.class);
            this.aclManagerService = (AccessControlManagerService)config.getContext().getService(AccessControlManagerService.class);
            this.passwordManagerService = (PasswordManagerService)config.getContext().getService(PasswordManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        this.log.debug("Exiting initServices()");

        DEFAULT_VIEW_PAGE = "doViewListUser";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doViewListUser(FormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        List userList = this.userManagerService.getUsers();
        req.setAttribute("userList", userList);
        setNextTitle(req, "User Account Manager [List Users]");
        setNextState(req, DO_VIEW_USER_LIST);
    }

    public void doViewViewUser(FormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        User user = loadUser(evt);
        req.setAttribute("user", user);
        HiddenFieldBean hf = evt.getHiddenFieldBean("userID");
        hf.setValue(user.getID());
        PortletRole role = aclManagerService.getRoleInGroup(user, PortletGroupFactory.GRIDSPHERE_GROUP);
        req.setAttribute("role", role.toString());
        setNextTitle(req, "User Account Manager [View User]");
        setNextState(req, DO_VIEW_USER_VIEW);
    }

    public void doViewNewUser(FormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        setNextTitle(req, "User Account Manager [New User]");
        setNextState(req, DO_VIEW_USER_EDIT);
        log.debug("in doViewNewUser");
    }

    public void doViewEditUser(FormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        setNextTitle(req, "User Account Manager [Edit User]");
        setNextState(req, DO_VIEW_USER_EDIT);
    }

    public void doViewConfirmEditUser(FormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        //User user = loadUser(evt);
        try {
            validateUser(evt);
            User user = saveUser(evt);
            req.setAttribute("user", user);
            PortletRole role = aclManagerService.getRoleInGroup(user, PortletGroupFactory.GRIDSPHERE_GROUP);
            req.setAttribute("role", role.toString());
            setNextTitle(req, "User Account Manager [View User]");
            setNextState(req, DO_VIEW_USER_VIEW);
        } catch (PortletException e) {
            FrameBean err = evt.getFrameBean("errorFrame");
            err.setValue(e.getMessage());
            setNextTitle(req, "User Account Manager [Edit User]");
            setNextState(req, DO_VIEW_USER_EDIT);
        }
    }

    public void doViewCancelEditUser(FormEvent evt)
            throws PortletException {
        doViewListUser(evt);
    }

    public void doViewDeleteUser(FormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        HiddenFieldBean hf = evt.getHiddenFieldBean("userID");
        String userId = hf.getValue();
        User user = this.userManagerService.getUser(userId);
        req.setAttribute("user", user);
        PortletRole role = aclManagerService.getRoleInGroup(user, PortletGroupFactory.GRIDSPHERE_GROUP);
        req.setAttribute("role", role.toString());
        setNextTitle(req, "User Account Manager [Delete User]");
        setNextState(req, DO_VIEW_USER_DELETE);
    }

    public void doViewConfirmDeleteUser(FormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getPortletRequest();
        HiddenFieldBean hf = evt.getHiddenFieldBean("userID");
        String userId = hf.getValue();
        User user = this.userManagerService.getUser(userId);
        req.setAttribute("user", user);
        PortletRole role = aclManagerService.getRoleInGroup(user, PortletGroupFactory.GRIDSPHERE_GROUP);
        req.setAttribute("role", role.toString());
        this.userManagerService.deleteAccount(user);
        setNextTitle(req, "User Account Manager [Deleted User]");
        setNextState(req, DO_VIEW_USER_DELETE_CONFIRM);
    }

    public void doViewCancelDeleteUser(FormEvent evt)
            throws PortletException {
        doViewListUser(evt);
    }

    private User loadUser(FormEvent event) {
        log.debug("Entering loadUser()");
        String userID = event.getAction().getParameter("userID");
        User user = this.userManagerService.getUser(userID);
        event.getTextFieldBean("familyName").setValue(user.getFamilyName());
        event.getTextFieldBean("givenName").setValue(user.getGivenName());
        event.getTextFieldBean("familyName").setValue(user.getFamilyName());
        event.getTextFieldBean("emailAddress").setValue(user.getEmailAddress());
        event.getTextFieldBean("organization").setValue(user.getOrganization());
        Password pwd = this.passwordManagerService.getPassword(user);
        event.getPasswordBean("password").setValue(pwd.getValue());
        log.debug("Loading user with id " + userID);
        log.debug("Exiting loadUser()");
        return user;
    }

    private void validateUser(FormEvent event)
            throws PortletException {
        log.debug("Entering validateUser()");
        StringBuffer message = new StringBuffer();
        boolean isInvalid = false;
        // Validate user name
        String userName = event.getTextFieldBean("userName").getValue();
        if (userName.equals("")) {
            message.append("User name cannot be blank<br>");
            isInvalid = true;
        } else if (this.userManagerService.existsUserName(userName)) {
            message.append("A user already exists with the same user name, please use a different name.\n");
            isInvalid = true;
        }
        // Validate family name
        String familyName = event.getTextFieldBean("familyName").getValue();
        if (familyName.equals("")) {
            message.append("Family name cannot be blank<br>");
            isInvalid = true;
        }
        // Validate given name
        String givenName = event.getTextFieldBean("givenName").getValue();
        if (givenName.equals("")) {
            message.append("Given name cannot be blank<br>");
            isInvalid = true;
        }

        isInvalid = isInvalidPassword(event, message);
        // Throw exception if error was found
        if (isInvalid) {
            throw new PortletException(message.toString());
        }
        log.debug("Exiting validateUser()");
    }

    private boolean isInvalidPassword(FormEvent event, StringBuffer message) {
        // Validate password
        String passwordValue = event.getPasswordBean("password").getValue();
        String confirmPasswordValue = event.getPasswordBean("confirmPassword").getValue();

        // If user already exists and password unchanged, no problem
        if (passwordValue.length() == 0 ||
                   confirmPasswordValue.length() == 0) {
            message.append("Password cannot be empty<br>");
            return true;
        }
        // Otherwise, password must match confirmation
        if (!passwordValue.equals(confirmPasswordValue)) {
            message.append("Password must match confirmation<br>");
            return true;
        // If they do match, then validate password with our service
        } else {
            try {
                this.passwordManagerService.validatePassword(passwordValue);
            } catch (InvalidPasswordException e) {
                message.append(e.getMessage());
                return true;
            }
        }
        return false;
    }

    private User saveUser(FormEvent event)
            throws PortletException {
        log.debug("Entering saveUser()");
        // Account request
        AccountRequest accountRequest = null;

        // Create edit account request
        accountRequest = this.userManagerService.createAccountRequest();

        // Edit account attributes
        editAccountRequest(event, accountRequest);
        // Submit changes
        this.userManagerService.submitAccountRequest(accountRequest);
        User newuser = this.userManagerService.approveAccountRequest(accountRequest);
        // Save user role
        saveUserRole(event, newuser);
        log.debug("Exiting saveUser()");
        return newuser;
    }

    private void editAccountRequest(FormEvent event, AccountRequest accountRequest) throws PortletException {
        log.debug("Entering editAccountRequest()");
        accountRequest.setUserName(event.getTextFieldBean("userName").getValue());
        accountRequest.setFamilyName(event.getTextFieldBean("familyName").getValue());
        accountRequest.setGivenName(event.getTextFieldBean("givenName").getValue());
        accountRequest.setFullName(event.getTextFieldBean("fullName").getValue());
        accountRequest.setEmailAddress(event.getTextFieldBean("emailAddress").getValue());
        accountRequest.setOrganization(event.getTextFieldBean("organization").getValue());
        String passwordValue = event.getPasswordBean("password").getValue();
        // Save password parameters if password was altered
        if (passwordValue.length() > 0) {
            accountRequest.setPasswordValue(passwordValue);
        }
        log.debug("Exiting editAccountRequest()");
    }

    private void saveUserRole(FormEvent event, User user)
            throws PortletException {
        log.debug("Entering saveUserRole()");
        PortletRequest req = event.getPortletRequest();
        // Get selected role
        PortletRole selectedRole = getSelectedUserRole(event);
        req.setAttribute("role", selectedRole);
        // If super role was chosen
        if (selectedRole.equals(PortletRole.SUPER)) {
            this.log.debug("Granting super role");
            // Grant super role
            this.aclManagerService.grantSuperRole(user);
        } else {
            // Revoke super role (in case they had it)
            this.aclManagerService.revokeSuperRole(user);
            // Create appropriate access request
            GroupRequest groupRequest = this.aclManagerService.createGroupRequest();
            groupRequest.setUser(user);
            groupRequest.setGroup(PortletGroupFactory.GRIDSPHERE_GROUP);
            groupRequest.setRole(selectedRole);
            this.log.debug("Granting " + selectedRole + " role in gridsphere");
            // Submit changes
            this.aclManagerService.submitGroupRequest(groupRequest);
            this.aclManagerService.approveGroupRequest(groupRequest);
        }
        log.debug("Exiting saveUserRole()");
    }

    private PortletRole getSelectedUserRole(FormEvent event) {
        // Iterate through list, return selected value
        ListBoxBean roleListBean = event.getListBoxBean("userRole");
        List userRoleList = roleListBean.getSelectedValues();
        if (userRoleList.size() == 0) {
            this.log.debug("No role was selected, setting to user");
            // Impossible, but if not selected return user role
            return PortletRole.USER;
        } else {
            // Otherwise, return the first selected value
            String userRoleItem = (String)userRoleList.get(0);
            this.log.debug("Selected role was " + userRoleItem);
            return PortletRole.toPortletRole(userRoleItem);
        }
    }

}
