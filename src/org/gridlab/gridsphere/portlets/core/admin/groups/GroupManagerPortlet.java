/*
 * $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.groups;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.security.acl.GroupAction;
import org.gridlab.gridsphere.services.core.security.acl.GroupEntry;
import org.gridlab.gridsphere.services.core.security.acl.GroupRequest;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;

import javax.servlet.UnavailableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class GroupManagerPortlet extends ActionPortlet {

      // JSP pages used by this portlet
    public static final String DO_VIEW_GROUP_LIST = "admin/groups/groupList.jsp";
    public static final String DO_VIEW_GROUP_VIEW = "admin/groups/groupView.jsp";
    public static final String DO_VIEW_GROUP_EDIT = "admin/groups/groupEdit.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_VIEW = "admin/groups/groupEntryView.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_EDIT = "admin/groups/groupEntryEdit.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_ADD = "admin/groups/groupEntryAdd.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_ADD_CONFIRM = "admin/groups/groupEntryAddConfirm.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_REMOVE = "admin/groups/groupEntryRemove.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_REMOVE_CONFIRM = "admin/groups/groupEntryRemoveConfirm.jsp";

    // Portlet services
    private UserManagerService userManagerService = null;
    private LayoutManagerService  layoutMgr = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        this.log.debug("Entering initServices()");
        try {
            this.userManagerService = (UserManagerService)config.getContext().getService(UserManagerService.class);
            this.layoutMgr = (LayoutManagerService)config.getContext().getService(LayoutManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }

        DEFAULT_VIEW_PAGE = "doViewListGroup";
        DEFAULT_HELP_PAGE = "admin/groups/help.jsp";
        log.debug("Exiting init()");
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        getPortletLog().info("Exiting initConcrete()");
    }

    public void doViewListGroup(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewListGroup");
        PortletRequest req = evt.getPortletRequest();
        User user = req.getUser();
        List groupDescs = new Vector();
        List groupList = new Vector();
        List groups = getACLService(user).getGroups();
        Iterator it = groups.iterator();
        while (it.hasNext()) {
            PortletGroup g = (PortletGroup)it.next();
            AccessControlManagerService aclService = getACLService(user);
            String desc = aclService.getGroupDescription(g);
            if ((aclService.hasAdminRoleInGroup(user, g)) && (!g.equals(PortletGroupFactory.GRIDSPHERE_GROUP))) {
                System.err.println("user " + user.getFullName() + " is admin");
            //System.err.println("group= " + g.getName() + " ispublic=" + g.isPublic());

            //System.err.println("desc=" + desc);
                groupDescs.add(desc);
                groupList.add(g);
            } else if (aclService.hasSuperRole(user)) {
                System.err.println("user " + user.getFullName() + " is super");
                groupDescs.add(desc);
                groupList.add(g);
            }
        }
        req.setAttribute("groupList", groupList);
        req.setAttribute("groupDescs", groupDescs);
        setNextState(req, DO_VIEW_GROUP_LIST);
        this.log.debug("Exiting doViewListGroup");
    }

    public void doViewViewGroup(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewViewGroup");
        PortletRequest req = evt.getPortletRequest();

        String groupId = evt.getAction().getParameter("groupID");

        if (groupId == null) {
            HiddenFieldBean groupIDBean = evt.getHiddenFieldBean("groupID");
            groupId = groupIDBean.getValue();
        }
        PortletGroup group = loadGroup(evt, groupId);

        setGroupEntryList(evt, group);
        setNextState(req, DO_VIEW_GROUP_VIEW);
        this.log.debug("Exiting doViewViewGroup");
    }

    public void doViewViewGroupEntry(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewViewGroupEntry");
        PortletRequest req = evt.getPortletRequest();

        loadGroup(evt);
        GroupEntry groupEntry = loadGroupEntry(evt);
        viewGroupEntry(evt, groupEntry);
        setNextState(req, DO_VIEW_GROUP_ENTRY_VIEW);
        this.log.debug("Exiting doViewViewGroupEntry");
    }

    public void doViewEditGroupEntry(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewEditGroupEntry");
        PortletRequest req = evt.getPortletRequest();
        loadGroup(evt);

        HiddenFieldBean groupEntryIDBean = evt.getHiddenFieldBean("groupEntryID");
        User user = evt.getPortletRequest().getUser();
        GroupEntry entry = getACLService(user).getGroupEntry(groupEntryIDBean.getValue());
        evt.getTextBean("userName").setValue(entry.getUser().getUserName());

        setRoleListBox(evt, entry.getRole());

        setNextState(req, DO_VIEW_GROUP_ENTRY_EDIT);
        this.log.debug("Exiting doViewEditGroupEntry");
    }

    public void doViewConfirmEditGroupEntry(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewConfirmEditGroupEntry");
        PortletRequest req = evt.getPortletRequest();
        loadGroup(evt);
        GroupEntry groupEntry = loadGroupEntry(evt);

        viewGroupEntry(evt, groupEntry);
        setNextState(req, DO_VIEW_GROUP_ENTRY_VIEW);
        this.log.debug("Exiting doViewConfirmEditGroupEntry");
    }

    public void doViewCancelEditGroupEntry(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewCancelEditGroupEntry");
        doViewViewGroup(evt);
        this.log.debug("Exiting doViewCancelEditGroupEntry");
    }

    public void doViewAddGroupEntry(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewAddGroupEntry");
        PortletRequest req = evt.getPortletRequest();
        User user = req.getUser();
        PortletGroup group = loadGroup(evt);
        List usersNotInGroupList = getACLService(user).getUsersNotInGroup(group);

        viewUsersNotInGroupList(evt, usersNotInGroupList);
        setNextState(req, DO_VIEW_GROUP_ENTRY_ADD);
        this.log.debug("Exiting doViewAddGroupEntry");
    }

    public void doViewConfirmAddGroupEntry(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewConfirmAddGroupEntry");
        PortletRequest req = evt.getPortletRequest();
        PortletGroup group = loadGroup(evt);
        User user = req.getUser();
        try {
            addGroupEntries(evt, group);
            setGroupEntryList(evt, group);
            setNextTitle(req, "Portlet Group Manager [Added User Role]");
            setNextState(req, DO_VIEW_GROUP_ENTRY_ADD_CONFIRM);
        } catch (PortletException e) {
            List usersNotInGroupList = getACLService(user).getUsersNotInGroup(group);

            viewUsersNotInGroupList(evt, usersNotInGroupList);
            setNextState(req, DO_VIEW_GROUP_ENTRY_ADD);
        }
    }

    public void doViewConfirmChangeRole(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewConfirmChangeRole");
        PortletRequest req = evt.getPortletRequest();


        User root = req.getUser();

        HiddenFieldBean groupEntryIDBean = evt.getHiddenFieldBean("groupEntryID");
        GroupEntry entry = getACLService(root).getGroupEntry(groupEntryIDBean.getValue());

        ListBoxBean groupEntryRoleListBox = evt.getListBoxBean("groupEntryRoleLB");
        String selectedGroupRole = groupEntryRoleListBox.getSelectedValue();
        PortletRole role = PortletRole.toPortletRole(selectedGroupRole);

        // Create add to group request
        GroupRequest groupRequest = getACLService(root).createGroupRequest(entry);

        groupRequest.setRole(role);
        groupRequest.setGroupAction(GroupAction.EDIT);

        // Create access right
        getACLService(root).submitGroupRequest(groupRequest);
        getACLService(root).approveGroupRequest(groupRequest);

        loadGroup(evt);
        entry = getACLService(root).getGroupEntry(groupEntryIDBean.getValue());
        viewGroupEntry(evt, entry);

        setNextTitle(req, "Portlet Group Manager [Modified User's Role]");
        setNextState(req, DO_VIEW_GROUP_ENTRY_VIEW);
    }

    public void doViewCancelAddGroupEntry(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewCancelAddGroupEntry");
        doViewViewGroup(evt);
        this.log.debug("Exiting doViewCancelAddGroupEntry");
    }

    public void doViewRemoveGroupEntry(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewRemoveGroupEntry");
        PortletRequest req = evt.getPortletRequest();
        PortletGroup group = loadGroup(evt);
        setGroupEntryList(evt, group);
        setNextState(req, DO_VIEW_GROUP_ENTRY_REMOVE);
        this.log.debug("Exiting doViewRemoveGroupEntry");
    }

    public void doViewConfirmRemoveGroupEntry(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewConfirmRemoveGroupEntry");
        PortletRequest req = evt.getPortletRequest();
        loadGroup(evt);
        removeGroupEntries(evt);
        setNextState(req, DO_VIEW_GROUP_ENTRY_REMOVE_CONFIRM);
        this.log.debug("Exiting doViewConfirmRemoveGroupEntry");
    }

    public void doViewCancelRemoveGroupEntry(FormEvent evt)
            throws PortletException {
        this.log.debug("Entering doViewCancelRemoveGroupEntry");
        doViewViewGroup(evt);
        this.log.debug("Exiting doViewCancelRemoveGroupEntry");
    }


    private PortletGroup loadGroup(FormEvent evt) {
        HiddenFieldBean groupIDBean = evt.getHiddenFieldBean("groupID");
        String groupId = groupIDBean.getValue();
        User user = evt.getPortletRequest().getUser();
        if (groupId == null) {
            String groupEntryId = evt.getAction().getParameter("groupEntryID");
            GroupEntry entry = getACLService(user).getGroupEntry(groupEntryId);
            groupId = entry.getGroup().getID();
        }
        return loadGroup(evt, groupId);
    }

    private PortletGroup loadGroup(FormEvent evt, String groupID) {
        // Get group id
        PortletGroup group = null;
        TextBean groupNameBean = evt.getTextBean("groupName");
        TextBean groupLabelBean = evt.getTextBean("groupLabel");
        TextBean groupDescBean = evt.getTextBean("groupDescription");
        HiddenFieldBean groupIDBean = evt.getHiddenFieldBean("groupID");
        groupIDBean.setValue(groupID);

        String groupDescription = "";
        User user = evt.getPortletRequest().getUser();
        // Load group
        if (!groupID.equals("")) {
            group = getACLService(user).getGroup(groupID);
            //System.err.println("group= " + group.getName());
            groupDescription = getACLService(user).getGroupDescription(group);
            //System.err.println("desc=" + groupDescription);
        }

        if (groupID.equals("")) {
            // Clear group attributes
            groupNameBean.setValue("");
            groupLabelBean.setValue("");
            groupDescBean.setValue("");
        }  else {
            // Set attributes
            groupNameBean.setValue(group.getName());
            groupLabelBean.setValue(group.getLabel());
            groupDescBean.setValue(groupDescription);
        }
        log.debug("Exiting loadGroup()");
        return group;
    }

    private void setGroupEntryList(FormEvent evt, PortletGroup group) {
        PortletRequest req = evt.getPortletRequest();
        User user = req.getUser();
        List groupEntryList = getACLService(user).getGroupEntries(group);
        req.setAttribute("groupEntryList", groupEntryList);
    }

    private void viewUsersNotInGroupList(FormEvent evt, List usersNotInGroupList) {
        log.debug("Entering viewUsersNotInGroupList()");
        PortletRequest req = evt.getPortletRequest();
        ListBoxBean usersNotInGroupListBox = evt.getListBoxBean("usersNotInGroupList");
        usersNotInGroupListBox.setMultipleSelection(false);
        usersNotInGroupListBox.setSize(1);
        if (usersNotInGroupList.size() == 0) {
            ListBoxItemBean item = new ListBoxItemBean();
            item.setValue("&lt;" + this.getLocalizedText(req, "GROUP_NOUSERS") + "&gt;");
            usersNotInGroupListBox.addBean(item);
        } else {
            // Add rows to table
            Iterator userIterator = usersNotInGroupList.iterator();
            while (userIterator.hasNext()) {
                // Get next user
                User user = (User)userIterator.next();
                //String userID = user.getID();
                String userName = user.getUserName();
                String fullName = user.getFullName();
                String userLabel = userName + " (" + fullName + ")";
                ListBoxItemBean item = new ListBoxItemBean();
                item.setValue(userName);
                usersNotInGroupListBox.addBean(item);
            }
        }
        setRoleListBox(evt, PortletRole.USER);
    }

    private void setRoleListBox(FormEvent evt, PortletRole role) {
        ListBoxBean userRoles = evt.getListBoxBean("groupEntryRoleLB");
        userRoles.setMultipleSelection(false);
        userRoles.setSize(1);
        ListBoxItemBean adminItem = new ListBoxItemBean();
        adminItem.setValue("ADMIN");
        if (role.equals(PortletRole.ADMIN)) adminItem.setSelected(true);
        ListBoxItemBean userItem = new ListBoxItemBean();
        userItem.setValue("USER");
        if (role.equals(PortletRole.USER)) userItem.setSelected(true);
        userRoles.addBean(userItem);
        userRoles.addBean(adminItem);
    }

    private GroupEntry loadGroupEntry(FormEvent evt) {
        log.debug("Entering loadGroupEntry()");
        User user = evt.getPortletRequest().getUser();
        String groupEntryID = evt.getAction().getParameter("groupEntryID");
        GroupEntry groupEntry = getACLService(user).getGroupEntry(groupEntryID);
        return groupEntry;
    }

    private void viewGroupEntry(FormEvent evt, GroupEntry groupEntry) {
        log.debug("Entering viewGroupEntry()");

        HiddenFieldBean groupEntryIDBean = evt.getHiddenFieldBean("groupEntryID");
        TextBean groupEntryUserNameBean = evt.getTextBean("groupEntryUserName");
        TextBean groupEntryUserFullNameBean = evt.getTextBean("groupEntryUserFullName");
        TextBean groupEntryRoleBean = evt.getTextBean("groupEntryRole");
        if (groupEntry == null) {
            // Clear group entry attributes

            groupEntryIDBean.setValue("");
            groupEntryUserNameBean.setValue("");
            groupEntryUserFullNameBean.setValue("");
            groupEntryRoleBean = new TextBean("");
        } else {
            User groupEntryUser = groupEntry.getUser();
            // Set group entry attributes
            groupEntryIDBean.setValue(groupEntry.getID());
            groupEntryUserNameBean.setValue(groupEntryUser.getUserName());
            groupEntryUserFullNameBean.setValue(groupEntryUser.getFullName());
            groupEntryRoleBean.setValue(groupEntry.getRole().toString());
        }

        log.debug("Exiting viewGroupEntry()");
    }

    private void addGroupEntries(FormEvent evt, PortletGroup group)
            throws PortletException {
        // Group entry role to apply to all users below...
        ListBoxBean groupEntryRoleListBox = evt.getListBoxBean("groupEntryRoleLB");
        String selectedGroupRole = groupEntryRoleListBox.getSelectedValue();
        PortletRole groupEntryRole = PortletRole.toPortletRole(selectedGroupRole);

        // Users to add...
        ListBoxBean userList = evt.getListBoxBean("usersNotInGroupList");
        String groupEntryUserID = userList.getSelectedValue();

        User groupEntryUser = this.userManagerService.getUserByUserName(groupEntryUserID);
        User root = evt.getPortletRequest().getUser();
        if (groupEntryUser != null) {
            addGroupEntry(root, groupEntryUser, group, groupEntryRole);
            if (groupEntryUser.getID().equals(root.getID())) {
                layoutMgr.addApplicationTab(evt.getPortletRequest(), group.getName());
            }
            layoutMgr.addApplicationTab(groupEntryUser, group.getName());
            layoutMgr.reloadPage(evt.getPortletRequest());
        } else {
            log.debug("Unable to get user: " + groupEntryUserID);
        }

    }

    private GroupEntry addGroupEntry(User root, User user, PortletGroup group, PortletRole role)
            throws PortletException {
        // Create add to group request
        GroupRequest groupRequest = getACLService(root).createGroupRequest();
        groupRequest.setUser(user);
        groupRequest.setGroup(group);
        groupRequest.setRole(role);

        // Create access right
        getACLService(root).submitGroupRequest(groupRequest);
        getACLService(root).approveGroupRequest(groupRequest);
        // Return the new group
        return getACLService(root).getGroupEntry(user, group);
    }

    private void removeGroupEntries(FormEvent evt)
            throws PortletException {
        // Create group entry list
        List groupEntryList = new ArrayList();
        PortletRequest req = evt.getPortletRequest();
        User root = req.getUser();
        CheckBoxBean groupEntryIDCheckBox = evt.getCheckBoxBean("groupEntryIDCB");

        List groupEntryIDList = groupEntryIDCheckBox.getSelectedValues();

        if (groupEntryIDList.size() == 0) {
            throw new PortletException("No users were selected for removal. Please try again.");
        }

        for (int ii = 0; ii < groupEntryIDList.size(); ++ii) {
            // Get entry to remove
            String groupEntryID = (String)groupEntryIDList.get(ii);
            GroupEntry entry = getACLService(root).getGroupEntry(groupEntryID);
            // Remove group entry
            removeGroupEntry(root, entry);
            // remove group layout
            PortletRegistry portletRegistry = PortletRegistry.getInstance();
            List portletIds =  portletRegistry.getAllConcretePortletIDs(req.getRole(), entry.getGroup().getName());
            if (entry.getUser().getID().equals(root.getID())) {
                this.layoutMgr.removePortlets(req, portletIds);
            }
            this.layoutMgr.removePortlets(req, entry.getUser(), portletIds);
            //this.layoutMgr.removeApplicationTab(entry.getUser(), entry.getGroup().getName());
            // Put entry in list
            groupEntryList.add(entry);
        }
        req.setAttribute("groupEntryList", groupEntryList);
    }

    private void removeGroupEntry(User root, GroupEntry entry)
            throws PortletException {
        // Create remove from group request
        GroupRequest groupRequest = getACLService(root).createGroupRequest(entry);
        groupRequest.setGroupAction(GroupAction.REMOVE);
        // Create access right
        getACLService(root).submitGroupRequest(groupRequest);
        getACLService(root).approveGroupRequest(groupRequest);
    }

    public void saveGroups(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();

        List groups = getACLService(user).getGroups();
        Iterator it = groups.iterator();
        boolean isPublic = true;

        while (it.hasNext()) {

            PortletGroup g = (PortletGroup)it.next();
            String access = req.getParameter(g.getName());

            if (access.equalsIgnoreCase("PRIVATE")) {
                isPublic = false;
            } else if (access.equalsIgnoreCase("PUBLIC")) {
                isPublic = true;
            }
            log.debug("making group " + g.getName() + " is public? " + isPublic);
            getACLService(user).modifyGroupAccess(g, isPublic);
        }
    }

    public AccessControlManagerService getACLService(User user) {
        AccessControlManagerService aclManagerService = null;
        try {
            aclManagerService = (AccessControlManagerService)this.getConfig().getContext().getService(AccessControlManagerService.class, user);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        return aclManagerService;
    }
}
