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
import java.io.PrintWriter;

public class AccessControllerBean extends PortletBean {

    // Portlet log
    private static PortletLog _log = SportletLog.getInstance(AccessControllerBean.class);
    // Portlet request attributes used within this portlet
    public static final String ATTRIBUTE_ACL_MANAGER_PAGE = "aclManagerPage";
    public static final String ATTRIBUTE_ACL_MANAGER_BEAN = "aclManagerBean";
    // JSP pages used by this portlet
    public static final String PAGE_GROUP_LIST = "/jsp/aclmanager/groupList.jsp";
    public static final String PAGE_GROUP_VIEW = "/jsp/aclmanager/groupView.jsp";
    public static final String PAGE_GROUP_EDIT = "/jsp/aclmanager/groupEdit.jsp";
    public static final String PAGE_GROUP_DELETE = "/jsp/aclmanager/groupDelete.jsp";
    public static final String PAGE_GROUP_DELETE_CONFIRM = "/jsp/aclmanager/groupDeleteConfirm.jsp";

    public static final String PAGE_GROUP_ENTRY_VIEW = "/jsp/aclmanager/groupEntryView.jsp";
    public static final String PAGE_GROUP_ENTRY_EDIT = "/jsp/aclmanager/groupEntryEdit.jsp";
    public static final String PAGE_GROUP_ENTRY_ADD = "/jsp/aclmanager/groupEntryAdd.jsp";
    public static final String PAGE_GROUP_ENTRY_ADD_CONFIRM = "/jsp/aclmanager/groupEntryAddConfirm.jsp";
    public static final String PAGE_GROUP_ENTRY_REMOVE = "/jsp/aclmanager/groupEntryRemove.jsp";
    public static final String PAGE_GROUP_ENTRY_REMOVE_CONFIRM = "/jsp/aclmanager/groupEntryRemoveConfirm.jsp";

    // Portlet actions available within this portlet
    public static final String ACTION_GROUP_LIST = "groupList";
    public static final String ACTION_GROUP_VIEW = "groupView";
    public static final String ACTION_GROUP_EDIT = "groupEdit";
    public static final String ACTION_GROUP_EDIT_CONFIRM = "groupEditConfirm";
    public static final String ACTION_GROUP_EDIT_CANCEL = "groupEditCancel";
    public static final String ACTION_GROUP_DELETE = "groupDelete";
    public static final String ACTION_GROUP_DELETE_CONFIRM = "groupDeleteConfirm";
    public static final String ACTION_GROUP_DELETE_CANCEL = "groupDeleteCancel";

    public static final String ACTION_GROUP_ENTRY_VIEW = "groupEntryView";
    public static final String ACTION_GROUP_ENTRY_EDIT = "groupEntryEdit";
    public static final String ACTION_GROUP_ENTRY_EDIT_CONFIRM = "groupEntryConfirm";
    public static final String ACTION_GROUP_ENTRY_EDIT_CANCEL = "groupEntryCancel";
    public static final String ACTION_GROUP_ENTRY_ADD = "groupEntryAdd";
    public static final String ACTION_GROUP_ENTRY_ADD_CONFIRM = "groupEntryAddConfirm";
    public static final String ACTION_GROUP_ENTRY_ADD_CANCEL = "groupEntryAddCancel";
    public static final String ACTION_GROUP_ENTRY_REMOVE = "groupEntryRemove";
    public static final String ACTION_GROUP_ENTRY_REMOVE_CONFIRM = "groupEntryRemoveConfirm";
    public static final String ACTION_GROUP_ENTRY_REMOVE_CANCEL = "groupEntryRemoveCancel";
    // Portlet action
    private String actionPerformed = null;
    private String nextPage = PAGE_GROUP_LIST;
    // Portlet services
    private UserManagerService userManagerService = null;
    private AccessControlManagerService aclManagerService = null;
    // Form validation
    private boolean isFormInvalid = false;
    private String formInvalidMessage = new String();
    // Current list of users
    private List userList = null;

    // Group and entry properties
    private List groupList = new Vector();
    private PortletGroup group = null;
    private String groupId = new String();
    private String groupName = new String();
    private List groupRequestList = new Vector();
    private List groupEntryList = new Vector();
    private List usersNotInGroupList = new Vector();
    private List groupRoles = new Vector();
    private GroupEntry groupEntry = null;
    private String groupEntryID = new String();
    private User groupEntryUser = null;
    private PortletGroup groupEntryGroup = null;
    private PortletRole groupEntryRole = null;

    public AccessControllerBean() {
        super();
    }

    public AccessControllerBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        super(config, request, response);
        init(config);
    }

    protected void initServices(PortletConfig config)
            throws PortletException {
        _log.debug("Entering initServices()");
        PortletContext context = config.getContext();
        this.userManagerService =
                (UserManagerService)context.getService(UserManagerService.class);
        this.aclManagerService =
                (AccessControlManagerService)context.getService(AccessControlManagerService.class);
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
            if (actionName.equals(ACTION_GROUP_LIST)) {
                doListGroups();
            } else if (actionName.equals(ACTION_GROUP_VIEW)) {
                doViewGroup();
            } else if (actionName.equals(ACTION_GROUP_EDIT)) {
                doEditGroup();
            } else if (actionName.equals(ACTION_GROUP_EDIT_CONFIRM)) {
                doConfirmEditGroup();
            } else if (actionName.equals(ACTION_GROUP_DELETE)) {
                doDeleteGroup();
            } else if (actionName.equals(ACTION_GROUP_DELETE_CONFIRM)) {
                doConfirmDeleteGroup();
            } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_VIEW)) {
                doViewGroupEntry();
            } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_EDIT)) {
                doEditGroupEntry();
            } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_EDIT_CONFIRM)) {
                doConfirmEditGroupEntry();
            } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_ADD)) {
                doAddGroupEntry();
            } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_ADD_CONFIRM)) {
                doConfirmAddGroupEntry();
            } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_REMOVE)) {
                doRemoveGroupEntry();
            } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_REMOVE_CONFIRM)) {
                doConfirmRemoveGroupEntry();
            } else {
                doListGroups();
            }
        } else {
            _log.debug("Action not default portlet action!");
            doListGroups();
        }
    }

    public void doListGroups()
            throws PortletException {
        _log.debug("Entering doListGroups");
        // Load group list
        loadGroupList();
        // Set actions
        createGroupListURI();
        createGroupViewURI();
        createGroupEditURI();
        createGroupDeleteURI();
        // Set next page attribute
        setNextPage(PAGE_GROUP_LIST);
        _log.debug("Exiting doListGroups");
    }

    public void doViewGroup()
            throws PortletException {
        _log.debug("Entering doViewGroup");
        // Load group so we can edit attributes
        loadGroup();
        // Set actions
        createGroupListURI();
        createGroupViewURI();
        createGroupEditURI();
        createGroupDeleteURI();
        createActionURI(ACTION_GROUP_ENTRY_VIEW);
        createActionURI(ACTION_GROUP_ENTRY_ADD);
        createActionURI(ACTION_GROUP_ENTRY_REMOVE);
        // Set next page attribute
        setNextPage(PAGE_GROUP_VIEW);
        _log.debug("Exiting doViewGroup");
    }

    public void doEditGroup()
            throws PortletException {
        _log.debug("Entering doEditGroup");
        // Load group so we can edit attributes
        loadGroup();
        // Set actions
        createGroupEditConfirmURI();
        createGroupEditCancelURI();
        // Set next page attribute
        setNextPage(PAGE_GROUP_EDIT);
        _log.debug("Exiting doEditGroup");
    }

    public void doConfirmEditGroup()
            throws PortletException {
        _log.debug("Entering doConfirmEditGroup");
        try {
            // Load group so we have original attributes (for existing groups)
            loadGroup();
            // Apply group parameters
            editGroup();
            // Save group
            saveGroup();
            // View group
            doViewGroup();
        } catch (PortletException e) {
            // Set form validation
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
            // Set actions
            createGroupEditConfirmURI();
            createGroupEditCancelURI();
            // Set next page attribute
            setNextPage(PAGE_GROUP_EDIT);
        }
        _log.debug("Exiting doConfirmEditGroup");
    }

    public void doDeleteGroup()
            throws PortletException {
        _log.debug("Entering doDeleteGroup");
        // Load group so we can have attributes
        loadGroup();
        // Set actions
        createGroupDeleteConfirmURI();
        createGroupDeleteCancelURI();
        // Set next page attribute
        setNextPage(PAGE_GROUP_DELETE);
        _log.debug("Exiting doDeleteGroup");
    }

    public void doConfirmDeleteGroup()
            throws PortletException {
        _log.debug("Entering doConfirmDeleteGroup");
        // Load group so we can have attributes
        loadGroup();
        // Delete group
        deleteGroup();
        // Set actions
        createGroupListURI();
        createGroupEditURI();
        // Set next page attribute
        setNextPage(PAGE_GROUP_DELETE_CONFIRM);
        _log.debug("Exiting doConfirmDeleteGroup");
    }

    public void doViewGroupEntry()
            throws PortletException {
        // Load access right
        loadGroupEntry();
        // Set actions
        createActionURI(ACTION_GROUP_LIST);
        createActionURI(ACTION_GROUP_VIEW);
        createActionURI(ACTION_GROUP_ENTRY_EDIT);
        createActionURI(ACTION_GROUP_ENTRY_REMOVE);
        // Set next page attribute
        setNextPage(PAGE_GROUP_ENTRY_VIEW);
    }

    public void doEditGroupEntry()
            throws PortletException {
        // Load access right
        loadGroupEntry();
        // Set actions
        createActionURI(ACTION_GROUP_ENTRY_EDIT_CONFIRM);
        createActionURI(ACTION_GROUP_ENTRY_EDIT_CANCEL);
        // Set next page attribute
        setNextPage(PAGE_GROUP_ENTRY_EDIT);
    }

    public void doConfirmEditGroupEntry()
            throws PortletException {
        // Load access right
        loadGroupEntry();
        // Edit access right
        editGroupEntry();
        // Save access right
        saveGroupEntry();
        // View access right
        doViewGroupEntry();
    }

    public void doAddGroupEntry()
            throws PortletException {
        // Load users not in group
        loadUserNotGroupList();
        // Set actions
        createActionURI(ACTION_GROUP_ENTRY_ADD_CONFIRM);
        createActionURI(ACTION_GROUP_ENTRY_ADD_CANCEL);
        // Set next page attribute
        setNextPage(PAGE_GROUP_ENTRY_ADD);
    }

    public void doConfirmAddGroupEntry()
            throws PortletException {
        addGroupEntries();
        doViewGroupEntry();
    }

    public void doRemoveGroupEntry()
            throws PortletException {
        // Load access rights
        loadGroupEntryList();
        // Set actions
        createActionURI(ACTION_GROUP_ENTRY_REMOVE_CONFIRM);
        createActionURI(ACTION_GROUP_ENTRY_REMOVE_CANCEL);
        // Set next page attribute
        setNextPage(PAGE_GROUP_ENTRY_REMOVE);
    }

    public void doConfirmRemoveGroupEntry()
            throws PortletException {
        removeGroupEntries();
        doViewGroupEntry();
    }

    public String getActionURI(String name) {
        return (String)request.getAttribute(name);
    }

    public void createActionURI(String name) {
        PortletURI listURI = response.createURI();
        listURI.addAction(new DefaultPortletAction(name));
        request.setAttribute(name, listURI.toString());
    }

    public String getGroupListURI() {
        return (String)request.getAttribute(ACTION_GROUP_LIST);
    }

    public void createGroupListURI() {
        PortletURI listURI = response.createURI();
        listURI.addAction(new DefaultPortletAction(ACTION_GROUP_LIST));
        request.setAttribute(ACTION_GROUP_LIST, listURI.toString());
    }

    public String getGroupViewURI() {
        return (String)request.getAttribute(ACTION_GROUP_VIEW);
    }

    public void createGroupViewURI() {
        PortletURI viewURI = response.createURI();
        viewURI.addAction(new DefaultPortletAction(ACTION_GROUP_VIEW));
        request.setAttribute(ACTION_GROUP_VIEW, viewURI.toString());
    }

    public String getGroupEditURI() {
        return (String)request.getAttribute(ACTION_GROUP_EDIT);
    }

    public void createGroupEditURI() {
        PortletURI editURI = response.createURI();
        editURI.addAction(new DefaultPortletAction(ACTION_GROUP_EDIT));
        request.setAttribute(ACTION_GROUP_EDIT, editURI.toString());
    }

    public String getGroupEditConfirmURI() {
        return (String)request.getAttribute(ACTION_GROUP_EDIT_CONFIRM);
    }

    public void createGroupEditConfirmURI() {
        PortletURI editConfirmURI = response.createURI();
        editConfirmURI.addAction(new DefaultPortletAction(ACTION_GROUP_EDIT_CONFIRM));
        request.setAttribute(ACTION_GROUP_EDIT_CONFIRM, editConfirmURI.toString());
    }

    public String getGroupEditCancelURI() {
        return (String)request.getAttribute(ACTION_GROUP_EDIT_CANCEL);
    }

    public void createGroupEditCancelURI() {
        PortletURI cancelURI = response.createReturnURI();
        cancelURI.addAction(new DefaultPortletAction(ACTION_GROUP_EDIT_CANCEL));
        request.setAttribute(ACTION_GROUP_EDIT_CANCEL, cancelURI.toString());
    }

    public String getGroupDeleteURI() {
        return (String)request.getAttribute(ACTION_GROUP_DELETE);
    }

    public void createGroupDeleteURI() {
        PortletURI deleteURI = response.createURI();
        deleteURI.addAction(new DefaultPortletAction(ACTION_GROUP_DELETE));
        request.setAttribute(ACTION_GROUP_DELETE, deleteURI.toString());
    }

    public String getGroupDeleteConfirmURI() {
        return (String)request.getAttribute(ACTION_GROUP_DELETE_CONFIRM);
    }

    public void createGroupDeleteConfirmURI() {
        PortletURI deleteConfirmURI = response.createURI();
        deleteConfirmURI.addAction(new DefaultPortletAction(ACTION_GROUP_DELETE_CONFIRM));
        request.setAttribute(ACTION_GROUP_DELETE_CONFIRM, deleteConfirmURI.toString());
    }

    public String getGroupDeleteCancelURI() {
        return (String)request.getAttribute(ACTION_GROUP_DELETE_CANCEL);
    }

    public void createGroupDeleteCancelURI() {
        PortletURI cancelURI = response.createReturnURI();
        cancelURI.addAction(new DefaultPortletAction(ACTION_GROUP_DELETE_CANCEL));
        request.setAttribute(ACTION_GROUP_DELETE_CANCEL, cancelURI.toString());
    }

    public String getActionPerformed() {
        return this.actionPerformed;
    }

    public void setActionPerformed(String action) {
        this.actionPerformed = action;
    }

    public String getNextTitle() {
        if (this.actionPerformed == null) {
            return "Access Control Manager: List portlet groups";
        } else if (this.actionPerformed.equals(ACTION_GROUP_LIST)) {
            return "Access Control Manager: List portlet groups";
        } else if (this.actionPerformed.equals(ACTION_GROUP_VIEW)) {
            return "Access Control Manager: View portlet group";
        } else if (this.actionPerformed.equals(ACTION_GROUP_EDIT)) {
            return "Access Control Manager: Edit portlet group";
        } else if (this.actionPerformed.equals(ACTION_GROUP_EDIT_CONFIRM)) {
            return "Access Control Manager: Edited portlet group";
        } else if (this.actionPerformed.equals(ACTION_GROUP_DELETE)) {
            return "Access Control Manager: Delete portlet group";
        } else if (this.actionPerformed.equals(ACTION_GROUP_DELETE_CONFIRM)) {
            return "Access Control Manager: Deleted portlet group";
        } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_VIEW)) {
            return "Access Control Manager: View portlet group entry";
        } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_EDIT)) {
            return "Access Control Manager: Edit portlet group entry";
        } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_EDIT_CONFIRM)) {
            return "Access Control Manager: View portlet group entry";
        } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_ADD)) {
            return "Access Control Manager: Add portlet group entry";
        } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_ADD_CONFIRM)) {
            return "Access Control Manager: View portlet group entry";
        } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_REMOVE)) {
            return "Access Control Manager: Remove portlet group entry";
        } else if (this.actionPerformed.equals(ACTION_GROUP_ENTRY_REMOVE_CONFIRM)) {
            return "Access Control Manager: View portlet group entry";
        } else {
            return "Access Control Manager: List portlet groups";
        }
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

    public List getGroupList() {
        return this.groupList;
    }

    public void setGroupList(List groupList) {
        this.groupList = groupList;
    }

    public PortletGroup getGroup() {
        return this.group;
    }

    public void setGroup(PortletGroup group) {
        this.group = group;
        this.groupId = group.getID();
        this.groupName = group.getName();
    }

    public String getGroupID() {
        return this.groupId;
    }

    public void setGroupID(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List getGroupEntryList() {
        return this.groupEntryList;
    }

    public void setGroupEntryList(List groupEntryList) {
        this.groupEntryList = groupEntryList;
    }

    public List getUsersNotInGroup() {
        return this.usersNotInGroupList;
    }

    public void setUsersNotInGroupList(List userList) {
        this.usersNotInGroupList = userList;
    }

    private void loadGroupList() {
        this.groupList = this.aclManagerService.getGroups();
    }

    private void loadGroup() {
        // Get group id
        String groupID = getParameter("groupID");
        // Load group
        loadGroup(groupID);
    }

    private void loadGroup(String groupID) {
        PortletGroup group = this.aclManagerService.getGroup(groupID);
        if (group != null) {
            // Save group attributes
            setGroup(group);
            // Load users in group
            loadGroupEntryList();
        }
    }

    private void loadGroupEntryList() {
        this.groupEntryList = this.aclManagerService.getGroupEntries(this.group);
    }

    private void loadUserNotGroupList() {
        this.usersNotInGroupList = this.aclManagerService.getUsersNotInGroup(this.group);
    }

    private void editGroup()
            throws PortletException {
        // Get group parameters
        setGroupID(getParameter("groupID"));
        setGroupName(getParameter("groupName"));
        // Validate group
        validateGroup();
    }

    private void validateGroup()
            throws PortletException {
        // Validate group parameters
        if (this.groupName.equals("")) {
            throw new PortletException("Group name can't be blank.");
        }
    }

    private void saveGroup()
            throws PortletException {
        // Get account group
        PortletGroup group = getGroup();
        // If group is new
        if (group == null) {
            group = this.aclManagerService.createGroup(getGroupName());
        }
        // Reset group
        setGroup(group);
    }

    private void deleteGroup() {
        // Delete our group
        PortletGroup group = getGroup();
        if (group != null) {
            this.aclManagerService.deleteGroup(group);
            // Blank out group id to be safe.
            setGroupID("");
        }
    }

    public GroupEntry getGroupEntry() {
        return this.groupEntry;
    }

    public void setGroupEntry(GroupEntry groupEntry) {
        this.groupEntry = groupEntry;
        this.groupEntryID = groupEntry.getID();
        this.groupEntryUser = groupEntry.getUser();
        this.groupEntryGroup = groupEntry.getGroup();
        this.groupEntryRole = groupEntry.getRole();
    }

    public String getGroupEntryID() {
        return this.groupEntryID;
    }

    public void getGroupEntryID(String id) {
        this.groupEntryID = id;
    }

    public User getGroupEntryUser() {
        return this.groupEntryUser;
    }

    public void setGroupEntryUser(User user) {
        this.groupEntryUser = user;
    }

    public PortletGroup getGroupEntryGroup() {
        return this.groupEntryGroup;
    }

    public void setGroupEntryGroup(PortletGroup group) {
        this.groupEntryGroup = group;
    }

    public PortletRole getGroupEntryRole() {
        return this.groupEntryRole;
    }

    public void setGroupEntryRole(PortletRole role) {
        this.groupEntryRole = role;
    }

    private void loadGroupEntry()
            throws PortletException {
        String userID = getParameter("userID");
        User user = this.userManagerService.getUser(userID);
        String groupID = getParameter("groupID");
        PortletGroup group = this.aclManagerService.getGroup(groupID);
        GroupEntry groupEntry = this.aclManagerService.getGroupEntry(user, group);
        setGroupEntry(groupEntry);
    }

    private void editGroupEntry()
            throws PortletException {
        String userID = getParameter("userID");
        User user = this.userManagerService.getUser(userID);
        setGroupEntryUser(user);
        String groupID = getParameter("groupID");
        PortletGroup group = this.aclManagerService.getGroup(groupID);
        setGroupEntryGroup(group);
        String roleName = getParameter("roleName");
        PortletRole role = toPortletRole(roleName);
        setGroupEntryRole(role);
    }

    private void saveGroupEntry()
            throws PortletException {
        // Get edited parameters
        User user = getGroupEntryUser();
        PortletGroup group = getGroupEntryGroup();
        PortletRole role = getGroupEntryRole();
        // Add access right
        addGroupEntry(user, group, role);
        // Retrieve and save access right
        GroupEntry groupEntry = this.aclManagerService.getGroupEntry(user, group);
        setGroupEntry(groupEntry);
    }

    private void addGroupEntry(User user, PortletGroup group, PortletRole role)
            throws PortletException {
        // Create add access request
        GroupRequest groupRequest = this.aclManagerService.createGroupRequest(user);
        groupRequest.setAction(GroupRequest.ACTION_ADD);
        // Edit access request
        groupRequest.setGroup(group);
        groupRequest.setRole(role);
        // Create access right
        this.aclManagerService.submitGroupRequest(groupRequest);
        this.aclManagerService.approveGroupRequest(groupRequest);
    }

    private void addGroupEntries()
            throws PortletException {
    }

    private void removeGroupEntries()
            throws PortletException {
    }

    private void removeGroupEntry(GroupEntry right)
            throws PortletException {
        // Get edited parameters
        User user = right.getUser();
        PortletGroup group = right.getGroup();
        PortletRole role = right.getRole();
        // Create add access request
        GroupRequest groupRequest = this.aclManagerService.createGroupRequest(user);
        groupRequest.setAction(GroupRequest.ACTION_ADD);
        // Edit access request
        groupRequest.setGroup(group);
        groupRequest.setRole(role);
        // Create access right
        this.aclManagerService.submitGroupRequest(groupRequest);
        this.aclManagerService.approveGroupRequest(groupRequest);
    }

    public static PortletRole toPortletRole(String roleName) {
        try {
            return PortletRole.toPortletRole(roleName);
        } catch (Exception e) {
            return PortletRole.GUEST;
        }
    }
}
