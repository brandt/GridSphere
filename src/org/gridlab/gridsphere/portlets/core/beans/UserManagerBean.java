/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 20, 2003
 * Time: 1:07:28 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core.beans;

import org.gridlab.gridsphere.services.security.password.PasswordInvalidException;
import org.gridlab.gridsphere.services.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.security.password.Password;
import org.gridlab.gridsphere.services.security.password.PasswordBean;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.PermissionDeniedException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portletcontainer.descriptor.Role;

import javax.servlet.UnavailableException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.security.acl.Group;
import java.io.PrintWriter;

public class UserManagerBean extends PortletBean {

    // Portlet request attributes used within this portlet
    public static final String ATTRIBUTE_USER_MANAGER_PAGE = "userManagerPage";
    public static final String ATTRIBUTE_USER_MANAGER_BEAN = "userManagerBean";
    // JSP pages used by this portlet
    public static final String PAGE_USER_LIST = "/jsp/userList.jsp";
    public static final String PAGE_USER_VIEW = "/jsp/userView.jsp";
    public static final String PAGE_USER_EDIT = "/jsp/userEdit.jsp";
    public static final String PAGE_USER_DELETE = "/jsp/userDelete.jsp";
    public static final String PAGE_USER_DELETE_CONFIRM = "/jsp/userDeleteConfirm.jsp";
    // Portlet request actions available within this portlet
    public static final String ACTION_USER_LIST = "userList";
    public static final String ACTION_USER_VIEW = "userView";
    public static final String ACTION_USER_EDIT = "userEdit";
    public static final String ACTION_USER_EDIT_CONFIRM = "userEditConfirm";
    public static final String ACTION_USER_EDIT_CANCEL = "userEditCancel";
    public static final String ACTION_USER_DELETE = "userDelete";
    public static final String ACTION_USER_DELETE_CONFIRM = "userDeleteConfirm";
    public static final String ACTION_USER_DELETE_CANCEL = "userDeleteCancel";
    // Portlet request actions available within this portlet
    public static final DefaultPortletAction PACTION_USER_LIST
            = new DefaultPortletAction(ACTION_USER_LIST);
    public static final DefaultPortletAction PACTION_USER_VIEW
            = new DefaultPortletAction(ACTION_USER_VIEW);
    public static final DefaultPortletAction PACTION_USER_EDIT
            = new DefaultPortletAction(ACTION_USER_EDIT);
    public static final DefaultPortletAction PACTION_USER_EDIT_CONFIRM
            = new DefaultPortletAction(ACTION_USER_EDIT_CONFIRM);
    public static final DefaultPortletAction PACTION_USER_EDIT_CANCEL
            = new DefaultPortletAction(ACTION_USER_EDIT_CANCEL);
    public static final DefaultPortletAction PACTION_USER_DELETE
            = new DefaultPortletAction(ACTION_USER_DELETE);
    public static final DefaultPortletAction PACTION_USER_DELETE_CONFIRM
            = new DefaultPortletAction(ACTION_USER_DELETE_CONFIRM);
    public static final DefaultPortletAction PACTION_USER_DELETE_CANCEL
            = new DefaultPortletAction(ACTION_USER_DELETE_CANCEL);
    // Portlet services
    private UserManagerService userManagerService = null;
    private PasswordManagerService passwordManagerService = null;
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
    private String passwordValue = null;
    private String passwordConfirmation = null;
    private Date datePasswordExpires = null;
    private long passwordLifetime = -1;
    // ACL attributes
    private Role role = null;

    public UserManagerBean() {
        super();
    }

    public UserManagerBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        super(config, request, response);
    }

    protected void initServices(PortletConfig config)
            throws PortletException {
        PortletContext context = config.getContext();
        this.userManagerService =
                (UserManagerService)context.getService(UserManagerService.class);
        this.passwordManagerService =
                (PasswordManagerService)context.getService(PasswordManagerService.class);
    }

    public void doListUsers()
            throws PortletException {
        // Load user list
        loadUserList();
        // Add list user action
        createUserListURI();
        // Add view user action
        createUserViewURI();
        // Add edit user action
        createUserEditURI();
        // Add delete user action
        createUserDeleteURI();
        // Set next page attribute
        setNextJspPage(PAGE_USER_LIST);
    }

    public void doViewUser()
            throws PortletException {
        // Load user so we can edit attributes
        loadUser();
        // Add list user action
        createUserListURI();
        // Add edit user action
        createUserEditURI();
        // Add delete user action
        createUserDeleteURI();
        // Set next page attribute
        setNextJspPage(PAGE_USER_VIEW);
    }

    public void doEditUser()
            throws PortletException {
        // Load user so we can edit attributes
        loadUser();
        // Add confirm action for editing user
        createUserEditConfirmURI();
        // Add cancel action for returning to previous page
        // which should be either user list or view
        createUserEditCancelURI();
        // Set next page attribute
        setNextJspPage(PAGE_USER_EDIT);
    }

    public void doConfirmEditUser()
            throws PortletException {
        try {
            // Load user so we have original attributes (for existing users)
            loadUser();
            // First apply user parameters
            editUser();
            // Save user
            saveUser();
            // Add list user action
            createUserListURI();
            // Add edit user action
            createUserEditURI();
            // Add delete user action
            createUserDeleteURI();
            // Set next page attribute
            setNextJspPage(PAGE_USER_VIEW);
        } catch (PortletException e) {
            // Set form validation
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
            // Add confirm action for editing user
            createUserEditConfirmURI();
            // Add cancel action for returning to previous page
            // which should be either user list or view
            createUserEditCancelURI();
            // Set next page attribute
            setNextJspPage(PAGE_USER_EDIT);
        }
    }

    public void doDeleteUser()
            throws PortletException {
        // Load user so we can have attributes
        loadUser();
        // Add confirm action for deleting user
        createUserDeleteConfirmURI();
        // Add cancel action for returning to previous page
        // which should be either user list or view
        createUserDeleteCancelURI();
        // Set next page attribute
        setNextJspPage(PAGE_USER_DELETE);
    }

    public void doConfirmDeleteUser()
            throws PortletException {
        // Load user so we can have attributes
        loadUser();
        // Delete user
        deleteUser();
        // Add list user action
        createUserListURI();
        // Set next page attribute
        setNextJspPage(PAGE_USER_DELETE_CONFIRM);
    }

    public String getUserListURI() {
        return (String)request.getAttribute(ACTION_USER_LIST);
    }

    public void createUserListURI() {
        PortletURI listURI = response.createURI();
        listURI.addAction(PACTION_USER_LIST);
        request.setAttribute(ACTION_USER_LIST, listURI.toString());
    }

    public String getUserViewURI() {
        return (String)request.getAttribute(ACTION_USER_VIEW);
    }

    public void createUserViewURI() {
        PortletURI viewURI = response.createURI();
        viewURI.addAction(PACTION_USER_VIEW);
        request.setAttribute(ACTION_USER_VIEW, viewURI.toString());
    }

    public String getUserEditURI() {
        return (String)request.getAttribute(ACTION_USER_EDIT);
    }

    public void createUserEditURI() {
        PortletURI editURI = response.createURI();
        editURI.addAction(PACTION_USER_EDIT);
        request.setAttribute(ACTION_USER_EDIT, editURI.toString());
    }

    public String getUserEditConfirmURI() {
        return (String)request.getAttribute(ACTION_USER_EDIT_CONFIRM);
    }

    public void createUserEditConfirmURI() {
        PortletURI editConfirmURI = response.createURI();
        editConfirmURI.addAction(PACTION_USER_EDIT_CONFIRM);
        request.setAttribute(ACTION_USER_EDIT_CONFIRM, editConfirmURI.toString());
    }

    public String getUserEditCancelURI() {
        return (String)request.getAttribute(ACTION_USER_EDIT_CANCEL);
    }

    public void createUserEditCancelURI() {
        PortletURI editURI = response.createReturnURI();
        request.setAttribute(ACTION_USER_EDIT_CANCEL, editURI.toString());
    }

    public String getUserDeleteURI() {
        return (String)request.getAttribute(ACTION_USER_DELETE);
    }

    public void createUserDeleteURI() {
        PortletURI deleteURI = response.createURI();
        deleteURI.addAction(PACTION_USER_DELETE);
        request.setAttribute(ACTION_USER_DELETE, deleteURI.toString());
    }

    public String getUserDeleteConfirmURI() {
        return (String)request.getAttribute(ACTION_USER_DELETE_CONFIRM);
    }

    public void createUserDeleteConfirmURI() {
        PortletURI deleteConfirmURI = response.createURI();
        deleteConfirmURI.addAction(PACTION_USER_DELETE_CONFIRM);
        request.setAttribute(ACTION_USER_DELETE_CONFIRM, deleteConfirmURI.toString());
    }

    public String getUserDeleteCancelURI() {
        return (String)request.getAttribute(ACTION_USER_DELETE_CANCEL);
    }

    public void createUserDeleteCancelURI() {
        PortletURI cancelURI = response.createReturnURI();
        request.setAttribute(ACTION_USER_DELETE_CANCEL, cancelURI.toString());
    }

    public String getNextJspPage() {
        return (String)request.getAttribute(ATTRIBUTE_USER_MANAGER_PAGE);
    }

    public void setNextJspPage(String jspPage) {
        request.setAttribute(ATTRIBUTE_USER_MANAGER_PAGE, jspPage);
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

    public Role getUserRole() {
        return this.role;
    }

    public void setUserRole(Role role) {
        this.role = role;
    }

    public void setUserRole(String roleName) {
    }

    private boolean existsUser(String userName) {
        return this.userManagerService.userExists(userName);
    }

    private void loadUserList() {
        this.userList = this.userManagerService.getAllUsers();
    }

    private void loadUser() {
        // Load user
        String userID = getParameter("userID");
        User user = this.userManagerService.getUserByID(userID);
        if (user != null) {
            // Save user attributes
            setUser(user);
            // Then load password
            loadPassword();
        }
    }

    private void editUser()
            throws PortletException {
        // Get user parameters
        setUserID(getParameter("userID"));
        setUserName(getParameter("userName"));
        setFamilyName(getParameter("familyName"));
        setGivenName(getParameter("givenName"));
        setFullName(getParameter("fullName"));
        setEmailAddress(getParameter("emailAddress"));
        setOrganization(getParameter("organization"));
        // Validate user parameters
        if (this.userName.equals("")) {
            throw new PortletException("User name can't be blank.");
        }
        if (this.givenName.equals("")) {
            throw new PortletException("Given name can't be blank.");
        }
        if (this.userName.equals("")) {
            throw new PortletException("Given name can't be blank.");
        }
        // Then edit password
        editPassword();
    }

    private void saveUser()
            throws PortletException {
        // Get account request
        AccountRequest account = null;
        if (this.user == null) {
            account = this.userManagerService.createAccountRequest();
            // Save id created for this account request
            setUserID(account.getID());
        } else {
            account = this.userManagerService.changeAccountRequest(this.user);
        }
        // Set account request attributes
        account.setUserID(getUserName());
        account.setFamilyName(getFamilyName());
        account.setGivenName(getGivenName());
        account.setFullName(getFullName());
        account.setEmailAddress(getEmailAddress());
        account.setOrganization(getOrganization());
        // Approvate account request
        User approver = getPortletUser();
        try {
            this.userManagerService.approveAccountRequest(approver, account, null);
        } catch (PermissionDeniedException e) {
            throw new PortletException(e.getMessage());
        }
        // Load user if currently null
        if (this.user == null) {
            this.user = this.userManagerService.getUserByID(getUserID());
        }
        // Then save password
        savePassword();
    }

    private void deleteUser() {
        try {
            User approver = getPortletUser();
            this.userManagerService.removeUser(approver, userName);
        } catch (PermissionDeniedException e) {
        }
        // Blank out user id to be safe (but keep other attributes for display).
        setUserID("");
    }

    private void loadPassword() {
        this.password = this.passwordManagerService.getPassword(this.user);
        setPassword(password);
    }

    private void editPassword()
            throws PortletException {
        // Apply password parameters
        // Note: We access request.getParameter() directly
        // in case password was not changed. See logic below.
        setPasswordValue(this.request.getParameter("passwordValue"));
        setPasswordConfirmation(this.request.getParameter("passwordConfirmation"));
        setPasswordLifetime(getParameterAsLng("passwordLifetime", -1));
        // Validate password parameters if password was altered
        if (this.passwordValue != null) {
            if (!this.passwordValue.equals(passwordConfirmation)) {
                throw new PortletException("Password must match confirmation.");
            }
            try {
                this.passwordManagerService.validatePassword(passwordValue);
            } catch (PasswordInvalidException e) {
                throw new PortletException(e.getMessage());
            }
        }
    }

    private void savePassword()
            throws PortletException {
        // Save password parameters if password was altered
        if (this.passwordValue != null) {
            PasswordBean passwordBean = new PasswordBean();
            passwordBean.setUser(getUser());
            passwordBean.setValue(getPasswordValue());
            passwordBean.setLifetime(getPasswordLifetime());
            try {
                this.passwordManagerService.savePassword(passwordBean);
            } catch (PasswordInvalidException e) {
                throw new PortletException(e.getMessage());
            }
        }
    }

    private void deletePassword() {
        if (this.user != null) {
            this.passwordManagerService.deletePassword(this.user);
        }
    }
}
