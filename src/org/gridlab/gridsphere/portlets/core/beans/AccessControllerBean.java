/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.beans;

import org.gridlab.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.security.password.Password;
import org.gridlab.gridsphere.services.core.security.password.PasswordBean;
import org.gridlab.gridsphere.services.core.security.acl.*;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.portlet.*;

import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.PortletBean;

import javax.servlet.UnavailableException;
import java.util.*;
import java.io.PrintWriter;

public class AccessControllerBean extends PortletBean {

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
    public static final String ACTION_GROUP_LIST = "doListGroup";
    public static final String ACTION_GROUP_VIEW = "doViewGroup";
    public static final String ACTION_GROUP_EDIT = "doEditGroup";
    public static final String ACTION_GROUP_EDIT_CONFIRM = "doConfirmEditGroup";
    public static final String ACTION_GROUP_EDIT_CANCEL = "doCancelEditGroup";
    public static final String ACTION_GROUP_DELETE = "doDeleteGroup";
    public static final String ACTION_GROUP_DELETE_CONFIRM = "doConfirmDeleteGroup";
    public static final String ACTION_GROUP_DELETE_CANCEL = "doCancelDeleteGroup";

    public static final String ACTION_GROUP_ENTRY_VIEW = "doViewGroupEntry";
    public static final String ACTION_GROUP_ENTRY_EDIT = "doEditGroupEntry";
    public static final String ACTION_GROUP_ENTRY_EDIT_CONFIRM = "doConfirmEditGroupEntry";
    public static final String ACTION_GROUP_ENTRY_EDIT_CANCEL = "doCancelEditGroupEntry";
    public static final String ACTION_GROUP_ENTRY_ADD = "doAddGroupEntry";
    public static final String ACTION_GROUP_ENTRY_ADD_CONFIRM = "doConfirmAddGroupEntry";
    public static final String ACTION_GROUP_ENTRY_ADD_CANCEL = "doCancelAddGroupEntry";
    public static final String ACTION_GROUP_ENTRY_REMOVE = "doRemoveGroupEntry";
    public static final String ACTION_GROUP_ENTRY_REMOVE_CONFIRM = "doConfirmRemoveGroupEntry";
    public static final String ACTION_GROUP_ENTRY_REMOVE_CANCEL = "doCancelRemoveGroupEntry";

    // Portlet services
    private UserManagerService userManagerService = null;
    private AccessControlManagerService aclManagerService = null;

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
    private PortletGroup CORE_GROUP = null;

    public AccessControllerBean() {
        super();
        initView();
    }

    public AccessControllerBean(PortletConfig config, PortletRequest request, PortletResponse response)
            throws PortletException {
        super(config, request, response);
        initServices();
        initView();
    }

    private void initServices()
            throws PortletException {
        this.log.debug("Entering initServices()");
        this.userManagerService = (UserManagerService)getPortletService(UserManagerService.class);
        this.aclManagerService = (AccessControlManagerService)getPortletService(AccessControlManagerService.class);
        this.log.debug("Exiting initServices()");
        this.CORE_GROUP = aclManagerService.getGroup("core");
    }

    private void initView() {
        setTitle("Access Control Manager");
    }

    public void doDefaultViewAction()
            throws PortletException {
        doListGroup();
    }

    public void doListGroup()
            throws PortletException {
        this.log.debug("Entering doListGroups");
        loadGroupList();
        setPage(PAGE_GROUP_LIST);
        this.log.debug("Exiting doListGroups");
    }

    public void doViewGroup()
            throws PortletException {
        this.log.debug("Entering doViewGroup");
        loadGroup();
        setPage(PAGE_GROUP_VIEW);
        this.log.debug("Exiting doViewGroup");
    }

    public void doEditGroup()
            throws PortletException {
        this.log.debug("Entering doEditGroup");
        loadGroup();
        setPage(PAGE_GROUP_EDIT);
        this.log.debug("Exiting doEditGroup");
    }

    public void doConfirmEditGroup()
            throws PortletException {
        this.log.debug("Entering doConfirmEditGroup");
        try {
            loadGroup();
            editGroup();
            saveGroup();
            setPage(PAGE_GROUP_VIEW);
        } catch (PortletException e) {
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
            setPage(PAGE_GROUP_EDIT);
        }
        this.log.debug("Exiting doConfirmEditGroup");
    }

    public void doCancelEditGroup()
            throws PortletException {
        this.log.debug("Entering doCancelEditGroup");
        doListGroup();
        this.log.debug("Exiting doCancelEditGroup");
    }


    public void doDeleteGroup()
            throws PortletException {
        this.log.debug("Entering doDeleteGroup");
        loadGroup();
        setPage(PAGE_GROUP_DELETE);
        this.log.debug("Exiting doDeleteGroup");
    }

    public void doConfirmDeleteGroup()
            throws PortletException {
        this.log.debug("Entering doConfirmDeleteGroup");
        loadGroup();
        deleteGroup();
        setPage(PAGE_GROUP_DELETE_CONFIRM);
        this.log.debug("Exiting doConfirmDeleteGroup");
    }

    public void doCancelDeleteGroup()
            throws PortletException {
        this.log.debug("Entering doCancelDeleteGroup");
        doListGroup();
        this.log.debug("Exiting doCancelDeleteGroup");
    }

    public void doViewGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewGroupEntry");
        loadGroup();
        loadGroupEntry();
        setPage(PAGE_GROUP_ENTRY_VIEW);
        this.log.debug("Exiting doViewGroupEntry");
    }

    public void doEditGroupEntry()
            throws PortletException {
        this.log.debug("Entering doEditGroupEntry");
        loadGroup();
        loadGroupEntry();
        setPage(PAGE_GROUP_ENTRY_EDIT);
        this.log.debug("Exiting doEditGroupEntry");
    }

    public void doConfirmEditGroupEntry()
            throws PortletException {
        this.log.debug("Entering doConfirmEditGroupEntry");
        loadGroup();
        loadGroupEntry();
        try {
            editGroupEntry();
            saveGroupEntry();
            setPage(PAGE_GROUP_ENTRY_VIEW);
        } catch (PortletException e) {
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
            setPage(PAGE_GROUP_ENTRY_EDIT);
        }
        this.log.debug("Exiting doConfirmEditGroupEntry");
    }

    public void doCancelEditGroupEntry()
            throws PortletException {
        this.log.debug("Entering doCancelEditGroupEntry");
        doViewGroup();
        this.log.debug("Exiting doCancelEditGroupEntry");
    }

    public void doAddGroupEntry()
            throws PortletException {
        this.log.debug("Entering doAddGroupEntry");
        loadGroup();
        loadUserNotGroupList();
        setPage(PAGE_GROUP_ENTRY_ADD);
        this.log.debug("Exiting doAddGroupEntry");
    }

    public void doConfirmAddGroupEntry()
            throws PortletException {
        this.log.debug("Entering doConfirmAddGroupEntry");
        loadGroup();
        addGroupEntries();
        setPage(PAGE_GROUP_ENTRY_ADD_CONFIRM);
        this.log.debug("Exiting doConfirmAddGroupEntry");
    }

    public void doCancelAddGroupEntry()
            throws PortletException {
        this.log.debug("Entering doCancelAddGroupEntry");
        doViewGroup();
        this.log.debug("Exiting doCancelAddGroupEntry");
    }

    public void doRemoveGroupEntry()
            throws PortletException {
        this.log.debug("Entering doRemoveGroupEntry");
        loadGroup();
        readGroupEntryList();
        setPage(PAGE_GROUP_ENTRY_REMOVE);
        this.log.debug("Exiting doRemoveGroupEntry");
    }

    public void doConfirmRemoveGroupEntry()
            throws PortletException {
        this.log.debug("Entering doConfirmRemoveGroupEntry");
        loadGroup();
        removeGroupEntries();
        setPage(PAGE_GROUP_ENTRY_REMOVE_CONFIRM);
        this.log.debug("Exiting doConfirmRemoveGroupEntry");
    }

    public void doCancelRemoveGroupEntry()
            throws PortletException {
        this.log.debug("Entering doCancelRemoveGroupEntry");
        doViewGroup();
        this.log.debug("Exiting doCancelRemoveGroupEntry");
    }

    public List getAllRolesInBaseGroup() {
        return getAllRolesInGroup(CORE_GROUP);
    }

    public List getAllRolesInGroup(PortletGroup group) {
        List allRoles = new Vector();
        allRoles.add(PortletRole.GUEST);
        allRoles.add(PortletRole.USER);
        allRoles.add(PortletRole.ADMIN);
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

    private void readGroupEntryList() {
        // Create group entry list
        List entryList = new Vector();
        // Get ids of entries to remove
        List groupEntryIDList = getParameterCheckBoxList("groupEntryID");
        for (int ii = 0; ii < groupEntryIDList.size(); ++ii) {
            String groupEntryID = (String)groupEntryIDList.get(ii);
            // Get entry to remove
            GroupEntry entry = this.aclManagerService.getGroupEntry(groupEntryID);
            // Put entry in list
            entryList.add(entry);
        }
        // Set group entry list
        setGroupEntryList(entryList);
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

    private void addGroupEntries()
            throws PortletException {
        // Instantiate group entry list
        List entryList = new Vector();
        // Get portlet group
        PortletGroup group = getGroup();
        // Get ids of users to add
        List entryUserIDList = getParameterCheckBoxList("groupEntryUserID");
        // Get role names to user mapping
        Map entryRoleNameMap = getParameterValuesAsMap("groupEntryRoleName");
        // Iterate through user id list
        for (int ii = 0; ii < entryUserIDList.size(); ++ii) {
            String entryUserID = (String)entryUserIDList.get(ii);
            // Get user to add
            User user = this.userManagerService.getUser(entryUserID);
            // Get role mapped to user ...
            PortletRole role = null;
            try {
                String roleName = (String)entryRoleNameMap.get(entryUserID);
                role = PortletRole.toPortletRole(roleName);
            } catch (Exception e) {
                this.log.error("Error retrieving role name", e);
                role = PortletRole.GUEST;
            }
            // Add group entry
            GroupEntry entry = addGroupEntry(user, group, role);
            // Put entry in list
            entryList.add(entry);
        }
        // Set group entry list
        setGroupEntryList(entryList);
    }

    private GroupEntry addGroupEntry(User user, PortletGroup group, PortletRole role)
            throws PortletException {
        // Create add to group request
        GroupRequest groupRequest = this.aclManagerService.createGroupRequest();
        groupRequest.setUser(user);
        groupRequest.setGroup(group);
        groupRequest.setRole(role);
        // Create access right
        this.aclManagerService.submitGroupRequest(groupRequest);
        this.aclManagerService.approveGroupRequest(groupRequest);
        // Return the new group
        return this.aclManagerService.getGroupEntry(user, group);
    }

    private void removeGroupEntries()
            throws PortletException {
        // Create group entry list
        List entryList = new Vector();
        // Get ids of entries to remove
        List groupEntryIDList = getParameterCheckBoxList("groupEntryID");
        for (int ii = 0; ii < groupEntryIDList.size(); ++ii) {
            String groupEntryID = (String)groupEntryIDList.get(ii);
            // Get entry to remove
            GroupEntry entry = this.aclManagerService.getGroupEntry(groupEntryID);
            // Remove group entry
            removeGroupEntry(entry);
            // Put entry in list
            entryList.add(entry);
        }
        // Set group entry list
        setGroupEntryList(entryList);
    }

    private void removeGroupEntry(GroupEntry entry)
            throws PortletException {
        // Create remove from group request
        GroupRequest groupRequest = this.aclManagerService.createGroupRequest(entry);
        groupRequest.setGroupAction(GroupAction.REMOVE);
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
