/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.groups;

import org.gridlab.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.security.password.Password;
import org.gridlab.gridsphere.services.core.security.password.PasswordEditor;
import org.gridlab.gridsphere.services.core.security.acl.*;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.portlet.*;

import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.PortletBean;
import org.gridlab.gridsphere.provider.ActionEventHandler;
import org.gridlab.gridsphere.provider.ui.beans.*;

import javax.servlet.UnavailableException;
import java.util.*;
import java.io.PrintWriter;

public class GroupManagerBean extends ActionEventHandler {

    // JSP pages used by this portlet
    public static final String DO_VIEW_GROUP_LIST = "/jsp/admin/groups/groupList.jsp";
    public static final String DO_VIEW_GROUP_VIEW = "/jsp/admin/groups/groupView.jsp";
    public static final String DO_VIEW_GROUP_EDIT = "/jsp/admin/groups/groupEdit.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_VIEW = "/jsp/admin/groups/groupEntryView.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_EDIT = "/jsp/admin/groups/groupEntryEdit.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_ADD = "/jsp/admin/groups/groupEntryAdd.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_ADD_CONFIRM = "/jsp/admin/groups/groupEntryAddConfirm.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_REMOVE = "/jsp/admin/groups/groupEntryRemove.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_REMOVE_CONFIRM = "/jsp/admin/groups/groupEntryRemoveConfirm.jsp";

    // Portlet services
    private UserManagerService userManagerService = null;
    private AccessControlManagerService aclManagerService = null;

    // Group and group entry properties
    private List groupList = new Vector();
    private PortletGroup group = null;
    private List groupEntryList = new Vector();
    private List usersNotInGroupList = new Vector();
    private GroupEntry groupEntry = null;

    // Group view
    private TableBean groupListBean = null;
    private HiddenFieldBean groupIDBean = null;
    private TextBean groupNameBean = null;
    private TextBean groupLabelBean = null;
    private TableBean groupEntryListBean = null;
    private CheckBoxListBean groupEntryCheckBoxList = null;

    // Group entry view
    private HiddenFieldBean groupEntryIDBean = null;
    private TextBean groupEntryUserNameBean = null;
    private TextBean groupEntryUserFullNameBean = null;
    private TextBean groupEntryRoleBean = null;

    // Group entry edit
    private ListBoxBean groupEntryRoleEditBean = null;

    public GroupManagerBean() {
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
        this.log.debug("Exiting initServices()");
    }

    public void doView()
            throws PortletException {
        doViewListGroup();
    }

    public void doViewListGroup()
            throws PortletException {
        this.log.debug("Entering doViewListGroup");
        loadGroupList();
        viewGroupList();
        setTitle("Portlet Group Manager [List Groups]");
        setPage(DO_VIEW_GROUP_LIST);
        this.log.debug("Exiting doViewListGroup");
    }

    public void doViewViewGroup()
            throws PortletException {
        this.log.debug("Entering doViewViewGroup");
        loadGroup();
        viewGroup();
        loadGroupEntryList();
        viewGroupEntryList("There are no users in this group");
        setTitle("Portlet Group Manager [View Group]");
        setPage(DO_VIEW_GROUP_VIEW);
        this.log.debug("Exiting doViewViewGroup");
    }

    public void doViewViewGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewViewGroupEntry");
        loadGroup();
        viewGroup();
        loadGroupEntry();
        viewGroupEntry();
        setTitle("Portlet Group Manager [View Group Entry]");
        setPage(DO_VIEW_GROUP_ENTRY_VIEW);
        this.log.debug("Exiting doViewViewGroupEntry");
    }

    public void doViewEditGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewEditGroupEntry");
        loadGroup();
        viewGroup();
        loadGroupEntry();
        editGroupEntry();
        setTitle("Portlet Group Manager [Edit Group Entry]");
        setPage(DO_VIEW_GROUP_ENTRY_EDIT);
        this.log.debug("Exiting doViewEditGroupEntry");
    }

    public void doViewConfirmEditGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewConfirmEditGroupEntry");
        loadGroup();
        loadGroupEntry();
        updateGroupEntry();
        try {
            validateGroupEntry();
            saveGroupEntry();
            viewGroupEntry();
            setTitle("Portlet Group Manager [View Group Entry]");
            setPage(DO_VIEW_GROUP_ENTRY_VIEW);
        } catch (PortletException e) {
            storeGroupEntryEdit();
            setIsFormInvalid(true);
            setFormInvalidMessage(e.getMessage());
            setTitle("Portlet Group Manager [Edut Group Entry]");
            setPage(DO_VIEW_GROUP_ENTRY_EDIT);
        }
        this.log.debug("Exiting doViewConfirmEditGroupEntry");
    }

    public void doViewCancelEditGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewCancelEditGroupEntry");
        doViewViewGroup();
        this.log.debug("Exiting doViewCancelEditGroupEntry");
    }

    public void doViewAddGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewAddGroupEntry");
        loadGroup();
        viewGroup();
        loadUsersNotInGroupList();
        viewUsersNotInGroupList();
        setTitle("Portlet Group Manager [Add User]");
        setPage(DO_VIEW_GROUP_ENTRY_ADD);
        this.log.debug("Exiting doViewAddGroupEntry");
    }

    public void doViewConfirmAddGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewConfirmAddGroupEntry");
        loadGroup();
        viewGroup();
        try {
            addGroupEntries();
            viewGroupEntryList("There were no users added to this group");
            setTitle("Portlet Group Manager [Added Users]");
            setPage(DO_VIEW_GROUP_ENTRY_ADD_CONFIRM);
        } catch (PortletException e) {
            loadUsersNotInGroupList();
            viewUsersNotInGroupList();
            setFormInvalidMessage(e.getMessage());
            setTitle("Portlet Group Manager [Add User]");
            setPage(DO_VIEW_GROUP_ENTRY_ADD);
        }
    }

    public void doViewCancelAddGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewCancelAddGroupEntry");
        doViewViewGroup();
        this.log.debug("Exiting doViewCancelAddGroupEntry");
    }

    public void doViewRemoveGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewRemoveGroupEntry");
        loadGroup();
        viewGroup();
        loadGroupEntryList();
        viewGroupEntryList("There are no users in this group.", true);
        setTitle("Portlet Group Manager [Remove Users]");
        setPage(DO_VIEW_GROUP_ENTRY_REMOVE);
        this.log.debug("Exiting doViewRemoveGroupEntry");
    }

    public void doViewConfirmRemoveGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewConfirmRemoveGroupEntry");
        loadGroup();
        viewGroup();
        removeGroupEntries();
        viewGroupEntryList("No users removed from this group, please make sure you selected users to remove.");
        setTitle("Portlet Group Manager [Users Removed]");
        setPage(DO_VIEW_GROUP_ENTRY_REMOVE_CONFIRM);
        this.log.debug("Exiting doViewConfirmRemoveGroupEntry");
    }

    public void doViewCancelRemoveGroupEntry()
            throws PortletException {
        this.log.debug("Entering doViewCancelRemoveGroupEntry");
        doViewViewGroup();
        this.log.debug("Exiting doViewCancelRemoveGroupEntry");
    }

    private void loadGroupList() {
        this.groupList = this.aclManagerService.getGroups();
    }

    private void viewGroupList() {
        log.debug("Entering viewGroupList()");
        if (this.groupList.size() == 0) {
            String message = "No portlet groups in database";
            this.groupListBean = createTableBeanWithMessage(message);
        } else {
            // Create headers
            List headers = new Vector();
            headers.add("Group Name");
            headers.add("Group Label");
            // Create table with headers
            this.groupListBean = createTableBeanWithHeaders(headers);
            // Add rows to table
            Iterator userIterator = this.groupList.iterator();
            while (userIterator.hasNext()) {
                // Get next group
                PortletGroup group = (PortletGroup)userIterator.next();
                // Create new table row
                TableRowBean rowBean = new TableRowBean();
                // Group name with group id action link
                ActionLinkBean groupNameLinkBean
                        = new ActionLinkBean(createPortletURI(), "doViewViewGroup", group.getName());
                groupNameLinkBean.addParamBean("groupID", group.getID());
                TableCellBean cellBean = createTableCellBean(groupNameLinkBean);
                rowBean.add(cellBean);
                // Group label
                TextBean groupLabelBean = new TextBean(group.getLabel());
                cellBean = createTableCellBean(groupLabelBean);
                rowBean.add(cellBean);
                // Add row to table
                this.groupListBean.add(rowBean);
            }
        }
        // All done
        this.groupListBean.store("groupList", request);
        log.debug("Exiting viewGroupList()");
    }

    private void loadGroup() {
        // Get group id
        String groupID = getActionPerformedParameter("groupID");
        // Load group
        loadGroup(groupID);
    }

    private void loadGroup(String groupID) {
        this.group = this.aclManagerService.getGroup(groupID);
    }

    private void viewGroup() {
        log.debug("Entering viewGroup()");

        if (this.group == null) {
            // Clear group attributes
            this.groupIDBean = new HiddenFieldBean("groupID", "");
            this.groupNameBean = new TextBean("");
            this.groupLabelBean = new TextBean("");
        } else {
            // Set attributes
            this.groupIDBean = new HiddenFieldBean("groupID", this.group.getID());
            this.groupNameBean = new TextBean(this.group.getName());
            this.groupLabelBean = new TextBean(this.group.getLabel());
        }

        // Store beans
        this.groupIDBean.store("groupID", request);
        this.groupNameBean.store("groupName", request);
        this.groupLabelBean.store("groupLabel", request);

        log.debug("Exiting viewGroup()");
    }

    private void loadGroupEntryList() {
        this.groupEntryList = this.aclManagerService.getGroupEntries(this.group);
    }

    private void readGroupEntryList() {
        // Create group entry list
        this.groupEntryList = new Vector();

        this.groupEntryCheckBoxList = getCheckBoxListBean("groupEntryID");
        List groupEntryIDList = groupEntryCheckBoxList.getSelectedValues();

        for (int ii = 0; ii < groupEntryIDList.size(); ++ii) {
          String groupEntryID = (String)groupEntryIDList.get(ii);
          // Get entry to remove
          GroupEntry entry = this.aclManagerService.getGroupEntry(groupEntryID);
          // Put entry in list
          this.groupEntryList.add(entry);
        }
    }

    private void viewGroupEntryList(String emptyListMessage) {
        viewGroupEntryList(emptyListMessage, false);
    }

    private void viewGroupEntryList(String emptyListMessage,
                                    boolean checkBoxSelected) {
        log.debug("Entering viewGroupEntryList()");
        if (this.groupEntryList.size() == 0) {
            this.groupEntryListBean = createTableBeanWithMessage(emptyListMessage);
        } else {
            // Create headers
            List headers = new Vector();
            if (checkBoxSelected) {
                // Create check box list
                this.groupEntryCheckBoxList = new CheckBoxListBean("groupEntryID");
                // Add selection header
                headers.add("Selection");
            }
            headers.add("User Name");
            headers.add("Full Name");
            headers.add("Role In Group");
            // Create table with headers
            this.groupEntryListBean = createTableBeanWithHeaders(headers);
            // Add rows to table
            Iterator userIterator = this.groupEntryList.iterator();
            while (userIterator.hasNext()) {
                // Get next ebtry
                GroupEntry groupEntry = (GroupEntry)userIterator.next();
                User user = groupEntry.getUser();
                PortletRole role = groupEntry.getRole();
                // Create new table row
                TableRowBean rowBean = new TableRowBean();
                TableCellBean cellBean = null;
                if (checkBoxSelected) {
                    // Group entry check box
                    CheckBoxBean groupEntryCheckBoxBean = null;
                    // Can't remove super users from a group....
                    if (role.equals(PortletRole.SUPER)) {
                        groupEntryCheckBoxBean = new CheckBoxBean("groupEntryID", groupEntry.getID(), false, true);
                    } else {
                        groupEntryCheckBoxBean = new CheckBoxBean("groupEntryID", groupEntry.getID(), false, false);
                    }
                    // Add group entry check box to group check box list
                    groupEntryCheckBoxList.add(groupEntryCheckBoxBean);
                    //groupEntryCheckBoxBean.store("groupEntryID", request);
                    // Then create a table cell for it
                    cellBean = createTableCellBean(groupEntryCheckBoxBean);
                    rowBean.add(cellBean);
                }
                // User name with group entry id action link
                ActionLinkBean userNameLinkBean
                        = new ActionLinkBean(createPortletURI(), "doViewViewGroupEntry", user.getUserName());
                userNameLinkBean.addParamBean("groupEntryID", groupEntry.getID());
                cellBean = createTableCellBean(userNameLinkBean);
                rowBean.add(cellBean);
                // Full name
                TextBean fullNameBean = new TextBean(user.getFullName());
                cellBean = createTableCellBean(fullNameBean);
                rowBean.add(cellBean);
                // Role in group
                TextBean roleInGroupBean = new TextBean(groupEntry.getRole().toString());
                cellBean = createTableCellBean(roleInGroupBean);
                rowBean.add(cellBean);
                // Add row to table
                groupEntryListBean.add(rowBean);
            }
            if (checkBoxSelected) {
                groupEntryCheckBoxList.store("groupEntryID", request);
            }
        }
        this.groupEntryListBean.store("groupEntryList", request);
        log.debug("Exiting viewGroupEntryList()");
    }

    private void loadUsersNotInGroupList() {
        this.usersNotInGroupList = this.aclManagerService.getUsersNotInGroup(this.group);
    }

    private void viewUsersNotInGroupList() {
        log.debug("Entering viewUsersNotInGroupList()");
        ListBoxBean usersNotInGroupListBox = new ListBoxBean("usersNotInGroupList");
        usersNotInGroupListBox.setMultiple(false);
        usersNotInGroupListBox.setSize(1);
        if (this.usersNotInGroupList.size() == 0) {
            usersNotInGroupListBox.add("&lt;No more users to add to this group&gt;", "", false);
        } else {
            // Add rows to table
            Iterator userIterator = this.usersNotInGroupList.iterator();
            while (userIterator.hasNext()) {
                // Get next user
                User user = (User)userIterator.next();
                String userID = user.getID();
                String userName = user.getUserName();
                String fullName = user.getFullName();
                String userLabel = userName + " (" + fullName + ")";
                usersNotInGroupListBox.add(userLabel, userID, false);
            }
        }
        usersNotInGroupListBox.store("usersNotInGroupList", request);
        ListBoxBean entryRoleListBox = createUserRoleEditBean("groupEntryRole", PortletRole.USER);
        entryRoleListBox.store("groupEntryRole", request);
        log.debug("Exiting viewUsersNotInGroupList()");
    }

    private ListBoxBean createUserRoleEditBean(String name, PortletRole selectedRole) {
        ListBoxBean userRoles = new ListBoxBean(name);
        userRoles.setMultiple(false);
        userRoles.setSize(1);
        // Administrative role
        if (selectedRole.isAdmin()) {
            this.log.debug("Setting admin role");
            userRoles.add("Administrative Role", "ADMIN", true);
        } else {
            userRoles.add("Administrative Role", "ADMIN", false);
        }
        // User role
        if (selectedRole.isUser()) {
            this.log.debug("Setting user role");
            userRoles.add("User Role", "USER", true);
        } else {
            userRoles.add("User Role", "USER", false);
        }
        return userRoles;
    }

    private void loadGroupEntry()
            throws PortletException {
        log.debug("Entering loadGroupEntry()");
        String groupEntryID = getActionPerformedParameter("groupEntryID");
        this.groupEntry = this.aclManagerService.getGroupEntry(groupEntryID);
        log.debug("Entering loadGroupEntry()");
    }

    private void viewGroupEntry()
            throws PortletException {
        log.debug("Entering viewGroupEntry()");

        if (this.groupEntry == null) {
            // Clear group entry attributes
            this.groupEntryIDBean = new HiddenFieldBean("groupEntryID", "");
            this.groupEntryUserNameBean = new TextBean("");
            this.groupEntryUserFullNameBean = new TextBean("");
            this.groupEntryRoleBean = new TextBean("");
        } else {
            User groupEntryUser = this.groupEntry.getUser();
            // Set group entry attributes
            this.groupEntryIDBean = new HiddenFieldBean("groupEntryID", groupEntry.getID());
            this.groupEntryUserNameBean = new TextBean(groupEntryUser.getUserName());
            this.groupEntryUserFullNameBean = new TextBean(groupEntryUser.getFullName());
            this.groupEntryRoleBean = new TextBean(groupEntry.getRole().toString());
        }

        this.groupEntryIDBean.store("groupEntryID", request);
        this.groupEntryUserNameBean.store("groupEntryUserName", request);
        this.groupEntryUserFullNameBean.store("groupEntryUserFullName", request);
        this.groupEntryRoleBean.store("groupEntryRole", request);

        log.debug("Exiting viewGroupEntry()");
    }

    private void editGroupEntry()
            throws PortletException {
    }

    private void storeGroupEntryEdit()
            throws PortletException {
    }

    private void updateGroupEntry()
            throws PortletException {
    }

    private void validateGroupEntry()
            throws PortletException {
    }

    private void saveGroupEntry()
            throws PortletException {
        /***
        // Get edited parameters
        User user = getGroupEntryUser();
        PortletGroup group = getGroupEntryGroup();
        PortletRole role = getGroupEntryRole();
        // Add access right
        addGroupEntry(user, group, role);
        // Retrieve and save access right
        this.groupEntry = this.aclManagerService.getGroupEntry(user, group);
        ***/
    }

    private void addGroupEntries()
            throws PortletException {
        // Group entry role to apply to all users below...
        ListBoxBean groupEntryRoleListBox = getListBoxBean("groupEntryRole");
        List groupEntryRoleList = groupEntryRoleListBox.getSelectedValues();
        if (groupEntryRoleList.size() == 0) {
            throw new PortletException("No role was selected. Please try again... ");
        }
        // Get role from selection
        String groupEntryRoleID = (String)groupEntryRoleList.get(0);
        PortletRole groupEntryRole = PortletRole.toPortletRole(groupEntryRoleID);
        // Users to add...
        ListBoxBean userCheckBoxList = getListBoxBean("usersNotInGroupList");
        List groupEntryUserIDList = userCheckBoxList.getSelectedValues();
        if (groupEntryUserIDList.size() == 0) {
            throw new PortletException("No user was selected. Please try again... ");
        }

        String selectedValue = (String)userCheckBoxList.getSelectedValues().get(0);
        if (selectedValue.equals("")) {
            // First entry will be blank if all users are in the group already.
            return;
        }
        for (int ii = 0; ii < groupEntryUserIDList.size(); ++ii) {
            // Get user from user id
            String groupEntryUserID = (String)groupEntryUserIDList.get(ii);
            log.debug("Group entry user id = " + groupEntryUserID);
            User groupEntryUser = this.userManagerService.getUser(groupEntryUserID);
            // Add group entry
            GroupEntry groupEntry = addGroupEntry(groupEntryUser, group, groupEntryRole);
            // Put entry in list
            this.groupEntryList.add(groupEntry);
        }
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
        this.groupEntryList = new Vector();

        CheckBoxListBean groupEntryIDListBean = getCheckBoxListBean("groupEntryID");
        List groupEntryIDList = groupEntryIDListBean.getSelectedValues();

        if (groupEntryIDList.size() == 0) {
            throw new PortletException("No users were selected for removal. Please try again.");
        }

        for (int ii = 0; ii < groupEntryIDList.size(); ++ii) {
            // Get entry to remove
            String groupEntryID = (String)groupEntryIDList.get(ii);
            GroupEntry entry = this.aclManagerService.getGroupEntry(groupEntryID);
            // Remove group entry
            removeGroupEntry(entry);
            // Put entry in list
            this.groupEntryList.add(entry);
        }
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
