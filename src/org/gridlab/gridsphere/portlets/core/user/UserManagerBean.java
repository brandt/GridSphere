/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.user;

import org.gridlab.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.security.password.Password;
import org.gridlab.gridsphere.services.core.security.password.PasswordEditor;
import org.gridlab.gridsphere.services.core.security.acl.*;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.ActionEventHandler;
import org.gridlab.gridsphere.provider.PortletBean;
import org.gridlab.gridsphere.provider.ui.beans.*;

import javax.servlet.UnavailableException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import java.security.acl.Group;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;

public class UserManagerBean extends ActionEventHandler {

    // JSP pages used by this portlet
    public static final String PAGE_USER_LIST = "/jsp/usermanager/userList.jsp";
    public static final String PAGE_USER_VIEW = "/jsp/usermanager/userView.jsp";
    public static final String PAGE_USER_EDIT = "/jsp/usermanager/userEdit.jsp";
    public static final String PAGE_USER_DELETE = "/jsp/usermanager/userDelete.jsp";
    public static final String PAGE_USER_DELETE_CONFIRM = "/jsp/usermanager/userDeleteConfirm.jsp";

    // Portlet services
    private UserManagerService userManagerService = null;
    private PasswordManagerService passwordManagerService = null;
    private AccessControlManagerService aclManagerService = null;

    // User variables
    private List userList = null;
    private User user = null;
    private Password password = new PasswordEditor();
    private PortletRole userRole = PortletRole.USER;
    // User id always in page
    private HiddenFieldBean userIDBean = null;
    // User view
    private TextBean userIDViewBean = null;
    private TextBean userNameBean = null;
    private TextBean familyNameBean = null;
    private TextBean givenNameBean = null;
    private TextBean fullNameBean = null;
    private TextBean emailAddressBean = null;
    private TextBean organizationBean = null;
    private TextBean userRoleBean = null;
    // User edit
    private TextFieldBean userNameEditBean = null;
    private TextFieldBean familyNameEditBean = null;
    private TextFieldBean givenNameEditBean = null;
    private TextFieldBean fullNameEditBean = null;
    private TextFieldBean emailAddressEditBean = null;
    private TextFieldBean organizationEditBean = null;
    private DropDownListBean userRoleEditBean = null;
    private PasswordBean passwordEditBean = null;
    private PasswordBean confirmPasswordEditBean = null;

    public UserManagerBean() {
    }

    public void init(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        super.init(config, request, response);
        initServices();
    }

    private void initServices()
            throws PortletException {
        this.log.debug("Entering initServices()");
        this.userManagerService = (UserManagerService)getPortletService(UserManagerService.class);
        this.aclManagerService = (AccessControlManagerService)getPortletService(AccessControlManagerService.class);
        this.passwordManagerService = (PasswordManagerService)getPortletService(PasswordManagerService.class);
        this.log.debug("Exiting initServices()");
    }

    public void doViewAction()
            throws PortletException {
        doListUser();
    }

    public void doListUser()
            throws PortletException {
        listUser();
        setTitle("User Account Manager [List Users]");
        setPage(PAGE_USER_LIST);
    }

    public void doViewUser()
            throws PortletException {
        loadUser();
        viewUser();
        setTitle("User Account Manager [View User]");
        setPage(PAGE_USER_VIEW);
    }

    public void doNewUser()
            throws PortletException {
        editUser();
        setTitle("User Account Manager [New User]");
        setPage(PAGE_USER_EDIT);
    }

    public void doEditUser()
            throws PortletException {
        loadUser();
        editUser();
        setTitle("User Account Manager [Edit User]");
        setPage(PAGE_USER_EDIT);
    }

    public void doConfirmEditUser()
            throws PortletException {
        loadUser();
        updateUser();
        try {
            validateUser();
            saveUser();
        } catch (PortletException e) {
            storeUserEdit();
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
            setTitle("User Account Manager [Edit User]");
            setPage(PAGE_USER_EDIT);
            return;
        }
        viewUser();
        setTitle("User Account Manager [View User]");
        setPage(PAGE_USER_VIEW);
    }

    public void doCancelEditUser()
            throws PortletException {
        doListUser();
    }

    public void doDeleteUser()
            throws PortletException {
        loadUser();
        viewUser();
        setTitle("User Account Manager [Delete User]");
        setPage(PAGE_USER_DELETE);
    }

    public void doConfirmDeleteUser()
            throws PortletException {
        loadUser();
        viewUser();
        deleteUser();
        setTitle("User Account Manager [Deleted User]");
        setPage(PAGE_USER_DELETE_CONFIRM);
    }

    public void doCancelDeleteUser()
            throws PortletException {
        doListUser();
    }

    private User getUser() {
        return this.user;
    }

    private User listUser() {
        this.userList = this.userManagerService.getUsers();
        TableBean tableBean = null;
        if (this.userList.size() == 0) {
            String message = "No user accounts in database";
            tableBean = createTableBeanWithMessage(message);
        } else {
            // Create headers
            List headers = new Vector();
            headers.add("User ID");
            headers.add("User Name");
            headers.add("Full Name");
            headers.add("Email Address");
            headers.add("Organization");
            // Create table with headers
            tableBean = createTableBeanWithHeaders(headers);
            // Add rows to table
            Iterator userIterator = this.userList.iterator();
            while (userIterator.hasNext()) {
                // Get next user
                User user = (User)userIterator.next();
                // Create new table row
                TableRowBean rowBean = new TableRowBean();
                // User id
                PortletURI userIDLink = createPortletActionURI("doViewUser");
                userIDLink.addParameter("userID", user.getID());
                ActionLinkBean userIDLinkBean =
                        new ActionLinkBean(userIDLink, "doViewUser", user.getID());
                TableCellBean cellBean = createTableCellBean(userIDLinkBean);
                rowBean.add(cellBean);
                // User name
                TextBean userNameBean = new TextBean(user.getUserName());
                cellBean = createTableCellBean(userNameBean);
                rowBean.add(cellBean);
                // Full name
                TextBean fullNameBean = new TextBean(user.getFullName());
                cellBean = createTableCellBean(fullNameBean);
                rowBean.add(cellBean);
                // Email address
                TextBean emailAddressBean = new TextBean(user.getEmailAddress());
                cellBean = createTableCellBean(emailAddressBean);
                rowBean.add(cellBean);
                // Organization
                TextBean organizationBean = new TextBean(user.getOrganization());
                cellBean = createTableCellBean(organizationBean);
                rowBean.add(cellBean);
                // Add row to table
                tableBean.add(rowBean);
            }
        }
        // All done
        tableBean.store("userList", request);
        return user;
    }

    private User loadUser() {
        System.err.println("Calling loadUser()!");
        String userID = getActionPerformedParameter("userID");
        this.user = this.userManagerService.getUser(userID);
        if (this.user != null) {
            this.password = this.passwordManagerService.getPassword(this.user);
            this.userRole = this.aclManagerService.getRoleInGroup(this.user, SportletGroup.CORE);
        }
        return user;
    }

    private void viewUser() {
        log.debug("Entering viewUser()");

        if (this.user == null) {

            // Clear user attributes
            this.userIDBean = new HiddenFieldBean("userID", "");
            this.userNameBean = new TextBean("");
            this.familyNameBean = new TextBean("");
            this.givenNameBean = new TextBean("");
            this.fullNameBean = new TextBean("");
            this.emailAddressBean = new TextBean("");
            this.organizationBean = new TextBean("");
            this.userRoleBean = new TextBean("");

        } else {

            // Set attributes
            this.userIDBean = new HiddenFieldBean("userID", this.user.getID());
            this.userNameBean = new TextBean(this.user.getUserName());
            this.familyNameBean = new TextBean(this.user.getFamilyName());
            this.givenNameBean = new TextBean(this.user.getGivenName());
            this.fullNameBean = new TextBean(this.user.getFullName());
            this.emailAddressBean = new TextBean(this.user.getEmailAddress());
            this.organizationBean = new TextBean(this.user.getOrganization());
            this.userRoleBean = new TextBean(this.userRole.toString());
        }
        // Store beans
        storeUserView();

        log.debug("Exiting viewUser()");
    }

    private void storeUserView() {
        // Set user attributes
        PortletRequest portletRequest = getPortletRequest();

        // Store beans
        this.userIDBean.store("userID", portletRequest);
        this.userNameBean.store("userName", portletRequest);
        this.familyNameBean.store("familyName", portletRequest);
        this.givenNameBean.store("givenName", portletRequest);
        this.fullNameBean.store("fullName", portletRequest);
        this.emailAddressBean.store("emailAddress", portletRequest);
        this.organizationBean.store("organization", portletRequest);
        this.userRoleBean.store("userRole", portletRequest);
    }

    private void editUser() {
        log.debug("Entering editUser()");
        // Set user attributes
        PortletRequest portletRequest = getPortletRequest();

        // Set user role here, it's value is set only if
        // user is not null
        userRoleEditBean = new DropDownListBean("userRole");
        userRoleEditBean.add("Administrative Role", "admin");
        userRoleEditBean.add("User Role", "user");
        userRoleEditBean.add("Guest Role", "guest");

        if (this.user == null) {
            log.debug("Editing new user");
            this.userIDBean = new HiddenFieldBean("userID", "");
            this.userNameEditBean = new TextFieldBean("userName", "");
            this.familyNameEditBean = new TextFieldBean("familyName", "");
            this.givenNameEditBean = new TextFieldBean("givenName", "");
            this.fullNameEditBean = new TextFieldBean("fullName", "");
            this.emailAddressEditBean = new TextFieldBean("emailAddress", "");
            this.organizationEditBean = new TextFieldBean("organization", "");
        } else {
            log.debug("Editing existing user");
            this.userIDBean = new HiddenFieldBean("userID", this.user.getID());
            this.userNameEditBean = new TextFieldBean("userName", this.user.getUserName());
            this.userNameEditBean.setDisabled(true);
            this.userNameEditBean.setReadonly(true);
            this.familyNameEditBean = new TextFieldBean("familyName", this.user.getFamilyName());
            this.givenNameEditBean = new TextFieldBean("givenName", this.user.getGivenName());
            this.fullNameEditBean = new TextFieldBean("fullName", this.user.getFullName());
            this.emailAddressEditBean = new TextFieldBean("emailAddress", this.user.getEmailAddress());
            this.organizationEditBean = new TextFieldBean("organization", this.user.getOrganization());
        }
        // Password always blank first page
        this.passwordEditBean = new PasswordBean("password", "", false, false, 20, 16);
        // Confirm always blank first page
        this.confirmPasswordEditBean = new PasswordBean("confirmPassword", "", false, false, 20, 16);

        // Store beans
        storeUserEdit();

        log.debug("Exiting editUser()");
    }

    private void updateUser() {
        log.debug("Entering updateUser()");
        // Retrieve user attributes from form
        this.userIDBean = getHiddenFieldBean("userID");
        this.userNameEditBean = getTextFieldBean("userName");
        this.familyNameEditBean = getTextFieldBean("familyName");
        this.givenNameEditBean = getTextFieldBean("givenName");
        this.fullNameEditBean = getTextFieldBean("fullName");
        this.emailAddressEditBean = getTextFieldBean("emailAddress");
        this.organizationEditBean = getTextFieldBean("organization");
        this.userRoleEditBean = getDropDownListBean("userRole");
        this.passwordEditBean = getPasswordBean("password");
        this.confirmPasswordEditBean = getPasswordBean("confirmPassword");
        log.debug("Exiting updateUser()");
    }

    private void validateUser()
            throws PortletException {
        log.debug("Entering validateUser()");
        StringBuffer message = new StringBuffer();
        boolean isInvalid = false;
        // Validate user name
        String userName = this.userNameEditBean.getValue();
        if (userName.equals("")) {
            message.append("User name cannot be blank\n");
            isInvalid = true;
        } else if (this.userManagerService.existsUserName(userName)) {
            message.append("A user already exists with the same user name, please use a different name.\n");
            isInvalid = true;
        }
        // Validate family name
        String familyName = this.familyNameEditBean.getValue();
        if (familyName.equals("")) {
            message.append("Family name cannot be blank\n");
            isInvalid = true;
        }
        // Validate given name
        String givenName = this.givenNameEditBean.getValue();
        if (givenName.equals("")) {
            message.append("Given name cannot be blank\n");
            isInvalid = true;
        }

        isInvalid = isInvalidPassword(message);
        // Throw exception if error was found
        if (isInvalid) {
            throw new PortletException(message.toString());
        }
        log.debug("Exiting validateUser()");
    }

    private boolean isInvalidPassword(StringBuffer message) {
        // Validate password
        String passwordValue = this.passwordEditBean.getValue();
        String confirmPasswordValue = this.confirmPasswordEditBean.getValue();
        // If user is new and no password provided, error
        if (this.user == null) {
            if (passwordValue.length() == 0) {
                message.append("All new users must have a password.\n");
                return true;
            }
        // If user already exists and password unchanged, no problem
        } else if (passwordValue.length() == 0 &&
                   confirmPasswordValue.length() == 0) {
            return false;
        }
        // Otherwise, password must match confirmation
        if (!passwordValue.equals(confirmPasswordValue)) {
            message.append("Password must match confirmation\n");
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

    private void storeUserEdit() {
        // Set user attributes
        PortletRequest portletRequest = getPortletRequest();
        // Store the beans
        this.userIDBean.store("userID", portletRequest);
        this.userNameEditBean.store("userName", portletRequest);
        this.familyNameEditBean.store("familyName", portletRequest);
        this.givenNameEditBean.store("givenName", portletRequest);
        this.fullNameEditBean.store("fullName", portletRequest);
        this.emailAddressEditBean.store("emailAddress", portletRequest);
        this.organizationEditBean.store("organization", portletRequest);
        this.userRoleEditBean.store("userRole", portletRequest);
        this.passwordEditBean.store("password", portletRequest);
        this.confirmPasswordEditBean.store("confirmPassword", portletRequest);
   }

    private void saveUser()
            throws PortletException {
        log.debug("Entering saveUser()");
        // Account request
        AccountRequest accountRequest = null;
        // If user is new
        if (this.user == null) {
            log.debug("Creating new user");
            // Create new account request
            accountRequest = this.userManagerService.createAccountRequest();
            accountRequest.setUserName(this.userNameEditBean.getValue());
        } else {
            log.debug("Updating existing user");
            // Create edit account request
            accountRequest = this.userManagerService.createAccountRequest(user);
        }
        // Edit account attributes
        editAccountRequest(accountRequest);
        // Submit changes
        this.userManagerService.submitAccountRequest(accountRequest);
        this.user = this.userManagerService.approveAccountRequest(accountRequest);
        // Save user role
        saveUserRole();
        log.debug("Exiting saveUser()");
    }

    private void editAccountRequest(AccountRequest accountRequest) {
        log.debug("Entering editAccountRequest()");
        accountRequest.setFamilyName(this.familyNameEditBean.getValue());
        accountRequest.setGivenName(this.givenNameEditBean.getValue());
        accountRequest.setFullName(this.fullNameEditBean.getValue());
        accountRequest.setEmailAddress(this.emailAddressEditBean.getValue());
        accountRequest.setOrganization(this.organizationEditBean.getValue());
        String passwordValue = this.passwordEditBean.getValue();
        // Save password parameters if password was altered
        if (passwordValue.length() > 0) {
            accountRequest.setPasswordValue(passwordValue);
        }
        log.debug("Exiting editAccountRequest()");
    }


    private void saveUserRole()
            throws PortletException {
        log.debug("Entering saveUserRole()");
        // Create appropriate access request
        GroupRequest accessRequest = this.aclManagerService.createGroupRequest();
        accessRequest.setUser(this.user);
        accessRequest.setGroup(SportletGroup.CORE);
        // If super role was chosen
        if (this.userRole.equals(PortletRole.SUPER)) {
            this.log.debug("Granting super role");
            // Grant super role
            this.aclManagerService.grantSuperRole(user);
            this.log.debug("Granting admin role in base group");
        } else {
            // Revoke super role
            this.aclManagerService.revokeSuperRole(user);
            this.log.debug("Granting " + userRole + " role in base group");
            // Grant chosen role in base group
            accessRequest.setRole(this.userRole);
        }
        // Submit changes
        this.aclManagerService.submitGroupRequest(accessRequest);
        this.aclManagerService.approveGroupRequest(accessRequest);
        log.debug("Exiting saveUserRole()");
    }

    private void deleteUser() {
        if (this.user != null) {
            log.debug("Deleting user...");
            this.userManagerService.deleteAccount(user);
        }
    }
}
