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
        initServices();
    }

    protected void initServices()
            throws PortletException {
        _log.debug("Entering initServices()");
        this.userManagerService = (UserManagerService)getPortletService(UserManagerService.class);
        this.aclManagerService = (AccessControlManagerService)getPortletService(AccessControlManagerService.class);
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
        // Set next page attribute
        setNextPage(PAGE_GROUP_LIST);
        _log.debug("Exiting doListGroups");
    }

    public void doViewGroup()
            throws PortletException {
        _log.debug("Entering doViewGroup");
        // Load group so we can edit attributes
        loadGroup();
        // Set next page attribute
        setNextPage(PAGE_GROUP_VIEW);
        _log.debug("Exiting doViewGroup");
    }

    public void doEditGroup()
            throws PortletException {
        _log.debug("Entering doEditGroup");
        // Load group so we can edit attributes
        loadGroup();
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
        // Set next page attribute
        setNextPage(PAGE_GROUP_DELETE_CONFIRM);
        _log.debug("Exiting doConfirmDeleteGroup");
    }

    public void doViewGroupEntry()
            throws PortletException {
        // Load group
        loadGroup();
        // Load access right
        loadGroupEntry();
        // Set next page attribute
        setNextPage(PAGE_GROUP_ENTRY_VIEW);
    }

    public void doEditGroupEntry()
            throws PortletException {
        // Load group so we can edit attributes
        loadGroup();
        // Load access right
        loadGroupEntry();
        // Set next page attribute
        setNextPage(PAGE_GROUP_ENTRY_EDIT);
    }

    public void doConfirmEditGroupEntry()
            throws PortletException {
        // Load group
        loadGroup();
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
        // Load group
        loadGroup();
        // Load users not in group
        loadUserNotGroupList();
        // Set next page attribute
        setNextPage(PAGE_GROUP_ENTRY_ADD);
    }

    public void doConfirmAddGroupEntry()
            throws PortletException {
        // Load group
        loadGroup();
        // Add group entries
        addGroupEntries();
        // View group
        doViewGroup();
    }

    public void doRemoveGroupEntry()
            throws PortletException {
        // Load group
        loadGroup();
        // Load access rights
        loadGroupEntryList();
        // Set next page attribute
        setNextPage(PAGE_GROUP_ENTRY_REMOVE);
    }

    public void doConfirmRemoveGroupEntry()
            throws PortletException {
        // Load group
        loadGroup();
        // Add group entries
        removeGroupEntries();
        // View group
        doViewGroupEntry();
    }

    public PortletURI getActionURI(String name) {
        return getPortletActionURI(name);
    }

    public PortletURI getGroupListURI() {
        return getPortletActionURI(ACTION_GROUP_LIST);
    }

    public PortletURI getGroupViewURI() {
        return getPortletActionURI(ACTION_GROUP_VIEW);
    }

    public PortletURI getGroupEditURI() {
        return getPortletActionURI(ACTION_GROUP_EDIT);
    }

    public PortletURI getGroupEditConfirmURI() {
        return getPortletActionURI(ACTION_GROUP_EDIT_CONFIRM);
    }

    public PortletURI getGroupEditCancelURI() {
        return getPortletActionURI(ACTION_GROUP_EDIT_CANCEL);
    }

    public PortletURI getGroupDeleteURI() {
        return getPortletActionURI(ACTION_GROUP_DELETE);
    }

    public PortletURI getGroupDeleteConfirmURI() {
        return getPortletActionURI(ACTION_GROUP_DELETE_CONFIRM);
    }

    public PortletURI getGroupDeleteCancelURI() {
        return getPortletActionURI(ACTION_GROUP_DELETE_CANCEL);
    }

    public String getActionPerformed() {
        return this.actionPerformed;
    }

    public void setActionPerformed(String action) {
        this.actionPerformed = action;
    }

    public String getNextTitle() {
        return "Access Control Manager";
        /***
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
        // Get ids of users to add
        String groupEntryUserIDs[] = getParameterValues("groupEntryUserID");
        // Get portlet group
        PortletGroup group = getGroup();
        // Add first user in list
        User user = this.userManagerService.getUser(groupEntryUserIDs[0]);
        System.err.println("Adding user " + groupEntryUserIDs[0]);
        addGroupEntry(user, group, PortletRole.USER);
        // If list greater than 1 then iterate through list
        if (groupEntryUserIDs.length > 1) {
            // But user could have selected add all option
            // So we have to be careful to not try to add
            // the user with the same id as in add all value
            String firstGroupEntryUserID = groupEntryUserIDs[0];
            for (int ii = 1; ii < groupEntryUserIDs.length; ++ii) {
                // Skip if we already added this user
                if (groupEntryUserIDs[ii].equals(firstGroupEntryUserID)) {
                    continue;
                }
                // Add this user to the group
                user = this.userManagerService.getUser(groupEntryUserIDs[ii]);
                System.err.println("Adding user " + groupEntryUserIDs[ii]);
                addGroupEntry(user, group, PortletRole.USER);
            }
        }
    }

    private void removeGroupEntries()
            throws PortletException {
        // Get ids of entries to remove
        String groupEntryIDs[] = getParameterValues("groupEntryID");
        // Add first entry in list
        GroupEntry entry = this.aclManagerService.getGroupEntry(groupEntryIDs[0]);
        _log.debug("Removing entry " + groupEntryIDs[0]);
        removeGroupEntry(entry);
        // If list greater than 1 then iterate through list
        if (groupEntryIDs.length > 1) {
            // But user could have selected remove all option
            // So we have to be careful to not try to add
            // the user with the same id as in remove all value
            String firstGroupEntryID = groupEntryIDs[0];
            for (int ii = 1; ii < groupEntryIDs.length; ++ii) {
                // Skip if we already added this user
                if (groupEntryIDs[ii].equals(firstGroupEntryID)) {
                    continue;
                }
                // Remove this entry from the group
                entry = this.aclManagerService.getGroupEntry(groupEntryIDs[0]);
                _log.debug("Removing entry " + groupEntryIDs[ii]);
                removeGroupEntry(entry);
            }
        }
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
