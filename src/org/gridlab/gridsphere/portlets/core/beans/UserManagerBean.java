/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 20, 2003
 * Time: 1:07:28 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core.beans;

import org.gridlab.gridsphere.services.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.security.password.Password;
import org.gridlab.gridsphere.services.security.password.PasswordBean;
import org.gridlab.gridsphere.services.security.acl.*;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;

import javax.servlet.UnavailableException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import java.security.acl.Group;
import java.io.PrintWriter;

public class UserManagerBean extends PortletBean {

    // Portlet log
    private static PortletLog _log = SportletLog.getInstance(UserManagerBean.class);
    // Portlet request attributes used within this portlet
    public static final String ATTRIBUTE_USER_MANAGER_PAGE = "userManagerPage";
    public static final String ATTRIBUTE_USER_MANAGER_BEAN = "userManagerBean";
    // JSP pages used by this portlet
    public static final String PAGE_USER_LIST = "/jsp/usermanager/userList.jsp";
    public static final String PAGE_USER_VIEW = "/jsp/usermanager/userView.jsp";
    public static final String PAGE_USER_EDIT = "/jsp/usermanager/userEdit.jsp";
    public static final String PAGE_USER_DELETE = "/jsp/usermanager/userDelete.jsp";
    public static final String PAGE_USER_DELETE_CONFIRM = "/jsp/usermanager/userDeleteConfirm.jsp";
    // Portlet actions available within this portlet
    public static final String ACTION_USER_LIST = "userList";
    public static final String ACTION_USER_VIEW = "userView";
    public static final String ACTION_USER_EDIT = "userEdit";
    public static final String ACTION_USER_EDIT_CONFIRM = "userEditConfirm";
    public static final String ACTION_USER_EDIT_CANCEL = "userEditCancel";
    public static final String ACTION_USER_DELETE = "userDelete";
    public static final String ACTION_USER_DELETE_CONFIRM = "userDeleteConfirm";
    public static final String ACTION_USER_DELETE_CANCEL = "userDeleteCancel";
    // Portlet action
    private String actionPerformed = null;
    private String nextPage = PAGE_USER_LIST;
    // Portlet services
    private UserManagerService userManagerService = null;
    private PasswordManagerService passwordManagerService = null;
    private AccessControlManagerService aclManagerService = null;
    // Form validation
    private boolean isFormInvalid = false;
    private String formInvalidMessage = new String();
    // Current list of users
    private List userList = null;
    // Profile attributes
    private User user = null;
    private String userID = new String();
    private String userName = new String();
    private String familyName = new String();
    private String givenName = new String();
    private String fullName = new String();
    private String emailAddress = new String();
    private String organization = new String();
    // Password attributes
    private Password password = null;
    private String passwordValue = new String();
    private String passwordConfirmation = new String();
    private Date datePasswordExpires = null;
    private long passwordLifetime = -1;
    // ACL attributes
    private PortletRole baseGroupRole = PortletRole.USER;

    public UserManagerBean() {
        super();
    }

    public UserManagerBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        super(config, request, response);
        initServices();
    }

    protected void initServices()
            throws PortletException {
        _log.debug("Entering initServices()");
        this.userManagerService = (UserManagerService)getPortletService(UserManagerService.class);
        this.aclManagerService = (AccessControlManagerService)getPortletService(AccessControlManagerService.class);
        this.passwordManagerService = (PasswordManagerService)getPortletService(PasswordManagerService.class);
        _log.debug("Exiting initServices()");
    }

    public void doAction(PortletAction action)
            throws PortletException {
        if (action instanceof DefaultPortletAction) {
            // Save action to be performed
            String actionName = ((DefaultPortletAction)action).getName();
            setActionPerformed(actionName);
            _log.debug("Action performed = " + actionName);
            // Perform appropriate action
            if (actionName.equals(ACTION_USER_LIST)) {
                doListUsers();
            } else if (actionName.equals(ACTION_USER_VIEW)) {
                doViewUser();
            } else if (actionName.equals(ACTION_USER_EDIT)) {
                doEditUser();
            } else if (actionName.equals(ACTION_USER_EDIT_CONFIRM)) {
                doConfirmEditUser();
            } else if (actionName.equals(ACTION_USER_DELETE)) {
                doDeleteUser();
            } else if (actionName.equals(ACTION_USER_DELETE_CONFIRM)) {
                doConfirmDeleteUser();
            } else {
                doListUsers();
            }
        } else {
            _log.debug("Action not default portlet action!");
            doListUsers();
        }
    }

    public void doListUsers()
            throws PortletException {
        // Load user list
        loadUserList();
        // Set next page attribute
        setNextPage(PAGE_USER_LIST);
    }

    public void doViewUser()
            throws PortletException {
        // Load user so we can edit attributes
        loadUser();
        // Set next page attribute
        setNextPage(PAGE_USER_VIEW);
    }

    public void doEditUser()
            throws PortletException {
        // Load user so we can edit attributes
        loadUser();
        // Set next page attribute
        setNextPage(PAGE_USER_EDIT);
    }

    public void doConfirmEditUser()
            throws PortletException {
        try {
            // Load user so we have original attributes (for existing users)
            loadUser();
            // Apply user parameters
            editUser();
            // Save user
            saveUser();
            // Set next page attribute
            setNextPage(PAGE_USER_VIEW);
        } catch (PortletException e) {
            // Set form validation
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
            // Set next page attribute
            setNextPage(PAGE_USER_EDIT);
        }
    }

    public void doDeleteUser()
            throws PortletException {
        // Load user so we can have attributes
        loadUser();
        // Set next page attribute
        setNextPage(PAGE_USER_DELETE);
    }

    public void doConfirmDeleteUser()
            throws PortletException {
        // Load user so we can have attributes
        loadUser();
        // Delete user
        deleteUser();
        // Set next page attribute
        setNextPage(PAGE_USER_DELETE_CONFIRM);
    }

    public PortletURI getUserListURI() {
        return getPortletActionURI(ACTION_USER_LIST);
    }

    public PortletURI getUserViewURI() {
        return getPortletActionURI(ACTION_USER_VIEW);
    }

    public PortletURI getUserEditURI() {
        return getPortletActionURI(ACTION_USER_EDIT);
    }
    public PortletURI getUserEditConfirmURI() {
        return getPortletActionURI(ACTION_USER_EDIT_CONFIRM);
    }

    public PortletURI getUserEditCancelURI() {
        return getPortletActionURI(ACTION_USER_EDIT_CANCEL);
    }

    public PortletURI getUserDeleteURI() {
        return getPortletActionURI(ACTION_USER_DELETE);
    }

    public PortletURI getUserDeleteConfirmURI() {
        return getPortletActionURI(ACTION_USER_DELETE_CONFIRM);
    }

    public PortletURI getUserDeleteCancelURI() {
        return getPortletActionURI(ACTION_USER_DELETE_CANCEL);
    }

    public String getActionPerformed() {
        return this.actionPerformed;
    }

    public void setActionPerformed(String action) {
        this.actionPerformed = action;
    }

    public String getNextTitle() {
        return "User Account Manager";
        /***
        if (this.actionPerformed == null) {
            return "User Account Manager: List user accounts";
        } else if (this.actionPerformed.equals(ACTION_USER_LIST)) {
            return "User Account Manager: List user accounts";
        } else if (this.actionPerformed.equals(ACTION_USER_VIEW)) {
            return "User Account Manager: View user account";
        } else if (this.actionPerformed.equals(ACTION_USER_EDIT)) {
            return "User Account Manager: Edit user account";
        } else if (this.actionPerformed.equals(ACTION_USER_EDIT_CONFIRM)) {
            return "User Account Manager: Edited ser account";
        } else if (this.actionPerformed.equals(ACTION_USER_DELETE)) {
            return "User Account Manager: Delete user account";
        } else if (this.actionPerformed.equals(ACTION_USER_DELETE_CONFIRM)) {
            return "User Account Manager: Deleted user account";
        } else {
            return "User Account Manager: List user accounts";
        }
        ***/
    }

    public String getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(String nextPage) {
        _log.debug("Setting next page to " + nextPage);
        this.nextPage = nextPage;
    }

    public boolean isFormInvalid() {
        return this.isFormInvalid;
    }

    public void setIsFormInvalid(boolean flag) {
        this.isFormInvalid = flag;
    }

    public String getFormInvalidMessage() {
        return this.formInvalidMessage;
    }

    public void setFormInvalidMessage(String message) {
        this.formInvalidMessage = message;
    }

    public List getUserList() {
        return this.userList;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userID = user.getID();
        this.userName = user.getUserID();
        this.familyName = user.getFamilyName();
        this.givenName = user.getGivenName();
        this.emailAddress = user.getEmailAddress();
        this.organization = user.getOrganization();
    }

    public boolean isNewUser() {
        return (this.user == null);
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return this.givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Password getPassword() {
        return this.password;
    }

    public void setPassword(Password password) {
        this.password = password;
        this.datePasswordExpires = password.getDateExpires();
        this.passwordLifetime = password.getLifetime();
    }

    public String getPasswordValue() {
        return this.passwordValue;
    }

    public void setPasswordValue(String password) {
        this.passwordValue = password;
    }

    public String getPasswordConfirmation() {
        return this.passwordConfirmation;
    }

    public void setPasswordConfirmation(String confirmation) {
        this.passwordConfirmation = confirmation;
    }

    public long getPasswordLifetime() {
        return this.passwordLifetime;
    }

    public void setPasswordLifetime(long lifetime) {
        this.passwordLifetime = lifetime;
    }

    public Date getDatePasswordExpires() {
        return this.datePasswordExpires;
    }

    public void setDatePasswordExpires(String expires) {
        Date dateExpires = new Date(expires);
        this.datePasswordExpires = dateExpires;
    }

    public List getPasswordLifetimeOptions() {
        return new Vector();
    }

    public PortletRole getRoleInBaseGroup() {
        return this.baseGroupRole;
    }

    public void setRoleInBaseGroup(PortletRole role) {
        this.baseGroupRole = role;
    }

    public void setRoleInBaseGroup(String role) {
        try {
            this.baseGroupRole = PortletRole.toPortletRole(role);
            _log.debug("Set base role to " + baseGroupRole);
        } catch (Exception e) {
            _log.error("Unable to instantiate role " + role, e);
            this.baseGroupRole = PortletRole.USER;
        }
    }

    public List getAllRolesInBaseGroup() {
        return getAllRolesInGroup(PortletGroup.BASE);
    }

    public List getAllRolesInGroup(PortletGroup group) {
        List allRoles = new Vector();
        if (group.equals(PortletGroup.SUPER)) {
            allRoles.add(PortletRole.SUPER);
        } else {
            allRoles.add(PortletRole.GUEST);
            allRoles.add(PortletRole.USER);
            allRoles.add(PortletRole.ADMIN);
            if (group.equals(PortletGroup.BASE)) {
                allRoles.add(PortletRole.SUPER);
            }
        }
        return allRoles;
    }

    private boolean existsUserWithLoginName(String userName) {
        return this.userManagerService.existsUserName(userName);
    }

    private void loadUserList() {
        this.userList = this.userManagerService.getUsers();
    }

    private void loadUser() {
        // Get user id
        String userID = getParameter("userID");
        // Load user
        loadUser(userID);
    }

    private void loadUser(String userID) {
        User user = this.userManagerService.getUser(userID);
        if (user != null) {
            // Save user attributes
            setUser(user);
            // Then load password
            loadPassword();
            // Load user acl
            loadAccessRights();
        }
    }

    private void loadPassword() {
        Password password = this.passwordManagerService.getPassword(this.user);
        if (password != null) {
            setPassword(password);
        }
    }

    private void loadAccessRights() {
        if (this.aclManagerService.hasSuperRole(this.user)) {
            this.baseGroupRole = PortletRole.SUPER;
        } else {
            this.baseGroupRole =
                    this.aclManagerService.getRoleInGroup(this.user, PortletGroup.BASE);
            if (this.baseGroupRole == null) {
                this.baseGroupRole = PortletRole.USER;
            }
        }
    }

    private void editUser()
            throws PortletException {
        // Get user parameters
        setUserID(getParameter("userID"));
        setUserName(getParameter("userName"));
        setFamilyName(getParameter("familyName"));
        setGivenName(getParameter("givenName"));
        resetFullName();
        setEmailAddress(getParameter("emailAddress"));
        setOrganization(getParameter("organization"));
        // Validate user
        validateUser();
        // Then edit password
        editPassword();
        // Then edit access rights
        editAccessRights();
    }

    private void resetFullName() {
        StringBuffer buffer = new StringBuffer();
        if (this.givenName.length() > 0) {
            buffer.append(this.givenName);
            buffer.append(" ");
        }
        buffer.append(this.familyName);
        this.fullName = buffer.toString();
    }

    private void validateUser()
            throws PortletException {
        // Validate user parameters
        if (this.userName.equals("")) {
            throw new PortletException("User name can't be blank.");
        }
        if (this.familyName.equals("")) {
            throw new PortletException("Family name can't be blank.");
        }
        if (this.givenName.equals("")) {
            throw new PortletException("Given name can't be blank.");
        }
    }

    private void editPassword()
            throws PortletException {
        setPasswordValue(getParameter("passwordValue"));
        setPasswordConfirmation(getParameter("passwordConfirmation"));
        setPasswordLifetime(getParameterAsLng("passwordLifetime", -1));
        // validate password parameters
        validatePassword();
    }

    private void validatePassword()
            throws PortletException {
        if (this.passwordValue.length() == 0) {
            // New users must be given a password
            if (this.user == null) {
                throw new PortletException("New users must be given a password.");
            }
        } else {
            // Validate password parameters if password was altered
            if (!this.passwordValue.equals(passwordConfirmation)) {
                throw new PortletException("Password must match confirmation.");
            }
            try {
                this.passwordManagerService.validatePassword(passwordValue);
            } catch (InvalidPasswordException e) {
                throw new PortletException(e.getMessage());
            }
        }
    }

    private void editAccessRights()
            throws PortletException {
        setRoleInBaseGroup(getParameter("baseGroupRole"));
    }

    private void saveUser()
            throws PortletException {
        // Get account user
        User user = getUser();
        AccountRequest accountRequest = null;
        // If user is new
        if (user == null) {
            // Create new account request
            accountRequest = this.userManagerService.createAccountRequest();
            // Save id created for this account request
            setUserID(accountRequest.getID());
        } else {
            // Create edit account request
            accountRequest = this.userManagerService.createAccountRequest(user);
        }
        // Edit account profile attributes
        editAccountRequestProfile(accountRequest);
        // Edit account password attributes
        editAccountRequestPassword(accountRequest);
        // Submit account request
        this.userManagerService.submitAccountRequest(accountRequest);
        // Approve account request
        user = this.userManagerService.approveAccountRequest(accountRequest);
        // Reset user
        setUser(user);
        // Save user access rights
        saveAccessRights();
    }

    private void editAccountRequestProfile(AccountRequest accountRequest) {
        accountRequest.setUserName(getUserName());
        accountRequest.setFamilyName(getFamilyName());
        accountRequest.setGivenName(getGivenName());
        accountRequest.setFullName(getFullName());
        accountRequest.setEmailAddress(getEmailAddress());
        accountRequest.setOrganization(getOrganization());
    }

    private void editAccountRequestPassword(AccountRequest accountRequest) {
        String passwordValue = getPasswordValue();
        // Save password parameters if password was altered
        if (passwordValue.length() > 0) {
            accountRequest.setPasswordValue(passwordValue);
            accountRequest.setPasswordDateExpires(getDatePasswordExpires());
        }
    }

    private void saveAccessRights()
            throws PortletException {
        // Given user
        User user = getUser();
        // Chosen role in base group
        PortletRole role = getRoleInBaseGroup();
        // Create appropriate access request
        GroupRequest accessRequest = this.aclManagerService.createGroupRequest();
        accessRequest.setUser(user);
        accessRequest.setGroup(PortletGroup.BASE);
        // If super role was chosen
        if (role.equals(PortletRole.SUPER)) {
            _log.debug("Granting super role");
            // Grant super role
            this.aclManagerService.grantSuperRole(user);
            _log.debug("Granting admin role in base group");
            // Set admin role in base group
            accessRequest.setRole(PortletRole.ADMIN);
        } else {
            // Revoke super role
            this.aclManagerService.revokeSuperRole(user);
            _log.debug("Granting " + role + " role in base group");
            // Grant chosen role in base group
            accessRequest.setRole(role);
        }
        this.aclManagerService.submitGroupRequest(accessRequest);
        this.aclManagerService.approveGroupRequest(accessRequest);
    }

    private void deleteUser() {
        // Delete our user
        User user = getUser();
        if (user != null) {
            this.userManagerService.deleteAccount(user);
            // Blank out user id to be safe.
            setUserID("");
        }
    }
}
