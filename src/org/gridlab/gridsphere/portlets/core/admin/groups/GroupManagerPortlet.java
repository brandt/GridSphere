/*
 * $Id$
 */
package org.gridlab.gridsphere.portlets.core.admin.groups;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletRoleInfo;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.security.acl.GroupAction;
import org.gridlab.gridsphere.services.core.security.acl.GroupEntry;
import org.gridlab.gridsphere.services.core.security.acl.GroupRequest;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.layout.PortletTabRegistry;

import javax.servlet.UnavailableException;
import java.util.*;
import java.io.IOException;


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
    public static final String DO_VIEW_GROUP_CREATE = "admin/groups/groupCreate.jsp";

    // Portlet services
    private UserManagerService userManagerService = null;
    private LayoutManagerService  layoutMgr = null;

    private PortletManager portletMgr = null;
    private PortletRegistry portletRegistry = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        log.debug("Entering initServices()");
        try {
            this.userManagerService = (UserManagerService)config.getContext().getService(UserManagerService.class);
            this.layoutMgr = (LayoutManagerService)config.getContext().getService(LayoutManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        portletRegistry = PortletRegistry.getInstance();
        portletMgr = PortletManager.getInstance();

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
        log.debug("Entering doViewListGroup");
        PortletRequest req = evt.getPortletRequest();
        User user = req.getUser();
        List groupDescs = new Vector();
        List groupList = new Vector();
        List groups = getACLService(user).getGroups();
        Iterator it = groups.iterator();
        while (it.hasNext()) {
            PortletGroup g = (PortletGroup)it.next();
            AccessControlManagerService aclService = getACLService(user);
            String desc = g.getDescription();
            if ((aclService.hasAdminRoleInGroup(user, g)) && (!g.equals(PortletGroupFactory.GRIDSPHERE_GROUP))) {
                System.err.println("user " + user.getFullName() + " is admin");
            //System.err.println("group= " + g.getName() + " ispublic=" + g.isPublic());

            //System.err.println("desc=" + desc);
                groupDescs.add(desc);
                groupList.add(g);
            } else if (aclService.hasSuperRole(user)) {

                groupDescs.add(desc);
                groupList.add(g);
            }
        }
        List webappNames = portletMgr.getWebApplicationNames();
        if (webappNames.size() > 1) req.setAttribute("create", "yes");

        req.setAttribute("groupList", groupList);
        req.setAttribute("groupDescs", groupDescs);
        setNextState(req, DO_VIEW_GROUP_LIST);
        log.debug("Exiting doViewListGroup");
    }

    public void doCreateNewGroup(FormEvent event) {

        PortletRequest req = event.getPortletRequest();

        // see if there is a group already
        PortletGroup group = null;
        String groupId = event.getAction().getParameter("groupID");
        if (groupId != null) {
            User user = req.getUser();
            group = getACLService(user).getGroup(groupId);
            TextFieldBean groupNameTF = event.getTextFieldBean("groupNameTF");
            groupNameTF.setValue(group.getName());
            TextFieldBean groupDescTF = event.getTextFieldBean("groupDescTF");
            groupDescTF.setValue(group.getDescription());
            if (!group.isPublic()) req.setAttribute("isPrivate", "true");

        }


        PanelBean panel = event.getPanelBean("panel");
        FrameBean frame = new FrameBean();
        DefaultTableModel model = new DefaultTableModel();
        if (group == null) {
            List webappNames = portletMgr.getWebApplicationNames();

            Iterator it = webappNames.iterator();


            PortletRole role = req.getRole();
            while (it.hasNext()) {


                String g = (String)it.next();
                System.err.println("listing group = " + g);
                if (g.equals(PortletGroupFactory.GRIDSPHERE_GROUP.toString())) {
                    continue;
                }
                TableRowBean tr = new TableRowBean();
                tr.setHeader(true);
                TableCellBean tc3 = new TableCellBean();
                TextBean text3 = new TextBean();
                text3.setValue(this.getLocalizedText(req, "SUBSCRIPTION_SUBSCRIBE"));
                tc3.addBean(text3);
                tr.addBean(tc3);
                TableCellBean tc = new TableCellBean();
                TextBean text = new TextBean();
                text.setValue(portletMgr.getPortletWebApplicationDescription(g));
                tc.addBean(text);
                tr.addBean(tc);
                tc = new TableCellBean();
                text = new TextBean();
                text.setValue(this.getLocalizedText(req, "SUBSCRIPTION_DESC"));
                tc.addBean(text);
                tr.addBean(tc);
                tc = new TableCellBean();
                text = new TextBean();
                text.setValue(this.getLocalizedText(req, "SUBSCRIPTION_REQROLE"));
                tc.addBean(text);
                tr.addBean(tc);
                model.addTableRowBean(tr);

                //if (group != null) break;

                List appColl = portletRegistry.getApplicationPortlets(g);
                if (appColl.isEmpty()) appColl = portletRegistry.getApplicationPortlets(g);
                Iterator appIt = appColl.iterator();
                while (appIt.hasNext()) {
                    ApplicationPortlet app = (ApplicationPortlet)appIt.next();
                    List concPortlets = app.getConcretePortlets();
                    Iterator cit = concPortlets.iterator();
                    while (cit.hasNext()) {
                        ConcretePortlet conc = (ConcretePortlet)cit.next();
                        String concID = conc.getConcretePortletID();

                        PortletRole reqrole = conc.getConcretePortletConfig().getRequiredRole();
                        log.debug("subscribed to portlet: " + concID + " " + reqrole);
                        if (role.compare(role, reqrole) >= 0) {
                            // build an interface
                            CheckBoxBean cb = new CheckBoxBean();
                            cb.setBeanId(concID + "CB");
                            cb.setValue(concID);
                            cb.setSelected(false);

                            // don't allow core portlets to be changed

                            TableRowBean newtr = new TableRowBean();
                            TableCellBean newtc = new TableCellBean();
                            newtc.addBean(cb);
                            newtr.addBean(newtc);

                            TableCellBean newtc2 = new TableCellBean();
                            TextBean tb = new TextBean();

                            // set 2nd column to portlet display name from concrete portlet
                            Locale loc = req.getLocale();
                            /*
                            int li = concID.lastIndexOf(".");
                            concID = concID.substring(0, li);
                            li = concID.lastIndexOf(".");
                            concID = concID.substring(li+1);
                            tb.setValue(concID);
                            */
                            String dispName = conc.getDisplayName(loc);
                            tb.setValue(dispName);
                            newtc2.addBean(tb);
                            newtr.addBean(newtc2);
                            newtc = new TableCellBean();
                            TextBean tb2 = new TextBean();

                            // set 3rd column to portlet description from concrete portlet

                            //tb2.setValue(conc.getPortletSettings().getDescription(loc, null));
                            tb2.setValue(conc.getDescription(loc));
                            newtc.addBean(tb2);
                            newtr.addBean(newtc);
                            //model.addTableRowBean(newtr);

                            // set 4th column to required role listbox
                            ListBoxBean lb = new ListBoxBean();
                            lb.setBeanId(concID + "LB");

                            //tb2.setValue(conc.getPortletSettings().getDescription(loc, null));
                            ListBoxItemBean item = new ListBoxItemBean();
                            item.setValue(PortletRole.USER.getName());
                            lb.addBean(item);
                            item = new ListBoxItemBean();
                            item.setValue(PortletRole.ADMIN.getName());
                            lb.addBean(item);
                            item = new ListBoxItemBean();
                            item.setValue(PortletRole.SUPER.getName());
                            lb.addBean(item);
                            newtc = new TableCellBean();
                            newtc.addBean(lb);
                            newtr.addBean(newtc);
                            model.addTableRowBean(newtr);
                        }
                    }
                }
            }

        } else {
            Set portletRoleList = group.getPortletRoleList();
            Iterator it = portletRoleList.iterator();

            TableRowBean tr = new TableRowBean();
            tr.setHeader(true);
            TableCellBean tc3 = new TableCellBean();
            TextBean text3 = new TextBean();
            text3.setValue(this.getLocalizedText(req, "SUBSCRIPTION_SUBSCRIBE"));
            tc3.addBean(text3);
            tr.addBean(tc3);
            TableCellBean tc = new TableCellBean();
            TextBean text = new TextBean();
            text.setValue(group.getDescription());
            tc.addBean(text);
            tr.addBean(tc);
            tc = new TableCellBean();
            text = new TextBean();
            text.setValue(this.getLocalizedText(req, "SUBSCRIPTION_DESC"));
            tc.addBean(text);
            tr.addBean(tc);
            tc = new TableCellBean();
            text = new TextBean();
            text.setValue(this.getLocalizedText(req, "SUBSCRIPTION_REQROLE"));
            tc.addBean(text);
            tr.addBean(tc);
            model.addTableRowBean(tr);

            while (it.hasNext()) {
                SportletRoleInfo info = (SportletRoleInfo)it.next();
                System.err.println("class= " + info.getPortletClass() + "role " + info.getPortletRole());
                CheckBoxBean cb = new CheckBoxBean();
                cb.setBeanId(info.getPortletClass() + "CB");
                cb.setValue(info.getPortletClass());
                cb.setSelected(true);

                TableRowBean newtr = new TableRowBean();
                TableCellBean newtc = new TableCellBean();
                newtc.addBean(cb);
                newtr.addBean(newtc);

                TableCellBean newtc2 = new TableCellBean();
                TextBean tb = new TextBean();

                // set 2nd column to portlet display name from concrete portlet
                Locale loc = req.getLocale();
                /*
                int li = concID.lastIndexOf(".");
                concID = concID.substring(0, li);
                li = concID.lastIndexOf(".");
                concID = concID.substring(li+1);
                tb.setValue(concID);
                */
                String appID = PortletRegistry.getApplicationPortletID(info.getPortletClass());
                ApplicationPortlet appPortlet = portletRegistry.getApplicationPortlet(appID);
                ConcretePortlet concPortlet = appPortlet.getConcretePortlet(info.getPortletClass());
                String dispName = concPortlet.getDisplayName(loc);
                tb.setValue(dispName);
                newtc2.addBean(tb);
                newtr.addBean(newtc2);
                newtc = new TableCellBean();
                TextBean tb2 = new TextBean();

                // set 3rd column to portlet description from concrete portlet

                //tb2.setValue(conc.getPortletSettings().getDescription(loc, null));
                tb2.setValue(concPortlet.getDescription(loc));
                newtc.addBean(tb2);
                newtr.addBean(newtc);
                //model.addTableRowBean(newtr);

                // set 4th column to required role listbox
                ListBoxBean lb = new ListBoxBean();
                lb.setBeanId(info.getPortletClass() + "LB");

                PortletRole reqRole = info.getPortletRole();

                //tb2.setValue(conc.getPortletSettings().getDescription(loc, null));
                ListBoxItemBean item = new ListBoxItemBean();


                item.setValue(PortletRole.USER.getName());
                if (reqRole.isUser()) item.setSelected(true);
                lb.addBean(item);
                item = new ListBoxItemBean();
                item.setValue(PortletRole.ADMIN.getName());
                if (reqRole.isAdmin()) item.setSelected(true);
                lb.addBean(item);
                item = new ListBoxItemBean();
                item.setValue(PortletRole.SUPER.getName());
                if (reqRole.isSuper()) item.setSelected(true);
                lb.addBean(item);
                newtc = new TableCellBean();
                newtc.addBean(lb);
                newtr.addBean(newtc);
                model.addTableRowBean(newtr);

            }

        }


        frame.setTableModel(model);
        panel.addBean(frame);

        setNextState(req, DO_VIEW_GROUP_CREATE);
    }

    public void doMakeGroup(FormEvent evt) throws PortletException {
        this.checkSuperRole(evt);
        List webappNames = portletMgr.getWebApplicationNames();
        Iterator it = webappNames.iterator();
        Set portletRoles = new HashSet();
        while (it.hasNext()) {
            String g = (String)it.next();
            if (g.equals(PortletGroupFactory.GRIDSPHERE_GROUP.toString())) {
                continue;
            }
            List appColl = portletRegistry.getApplicationPortlets(g);
            if (appColl.isEmpty()) appColl = portletRegistry.getApplicationPortlets(g);
            Iterator appIt = appColl.iterator();
            while (appIt.hasNext()) {
                ApplicationPortlet app = (ApplicationPortlet)appIt.next();
                List concPortlets = app.getConcretePortlets();
                Iterator cit = concPortlets.iterator();
                while (cit.hasNext()) {
                    ConcretePortlet conc = (ConcretePortlet)cit.next();
                    String concID = conc.getConcretePortletID();
                    ListBoxBean lb = evt.getListBoxBean(concID + "LB");
                    CheckBoxBean cb = evt.getCheckBoxBean(concID + "CB");
                    if (cb.isSelected()) {
                        String reqRole = lb.getSelectedValue();
                        SportletRoleInfo portletRoleInfo = new SportletRoleInfo();
                        System.err.println("concID= " + concID);
                        portletRoleInfo.setPortletClass(concID);
                        portletRoleInfo.setPortletRole(PortletRole.toPortletRole(reqRole));
                        portletRoles.add(portletRoleInfo);
                    }
                }
            }
        }

        it = portletRoles.iterator();
        while (it.hasNext()) {
            SportletRoleInfo info = (SportletRoleInfo)it.next();
            System.err.println("role= " + info.getRole() + " class=" + info.getPortletClass());
        }

        User user = evt.getPortletRequest().getUser();
        TextFieldBean groupTF = evt.getTextFieldBean("groupNameTF");
        TextFieldBean groupDescTF = evt.getTextFieldBean("groupDescTF");

        try {
            if ((groupTF.getValue() != "") && !portletRoles.isEmpty()) {
                this.getACLService(user).createGroup(groupTF.getValue(), groupDescTF.getValue(), portletRoles);
                // now create new group layout
                PortletTabRegistry.newGroupTab(groupTF.getValue(), portletRoles);
            } else {
                log.error("Unable to create new group. Either group name or portlets is empty");
            }
        } catch (Exception e) {
            log.error("Unable to save new group layout: ", e);
        }


    }


    public void doViewViewGroup(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewViewGroup");
        PortletRequest req = evt.getPortletRequest();

        String groupId = evt.getAction().getParameter("groupID");

        if (groupId == null) {
            HiddenFieldBean groupIDBean = evt.getHiddenFieldBean("groupID");
            groupId = groupIDBean.getValue();
        }
        PortletGroup group = loadGroup(evt, groupId);

        setGroupEntryList(evt, group);
        setNextState(req, DO_VIEW_GROUP_VIEW);
        log.debug("Exiting doViewViewGroup");
    }

    public void doViewViewGroupEntry(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewViewGroupEntry");
        PortletRequest req = evt.getPortletRequest();

        loadGroup(evt);
        GroupEntry groupEntry = loadGroupEntry(evt);
        viewGroupEntry(evt, groupEntry);
        setNextState(req, DO_VIEW_GROUP_ENTRY_VIEW);
        log.debug("Exiting doViewViewGroupEntry");
    }

    public void doViewEditGroupEntry(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewEditGroupEntry");
        checkAdminRole(evt);
        PortletRequest req = evt.getPortletRequest();
        loadGroup(evt);

        HiddenFieldBean groupEntryIDBean = evt.getHiddenFieldBean("groupEntryID");
        User user = evt.getPortletRequest().getUser();
        GroupEntry entry = getACLService(user).getGroupEntry(groupEntryIDBean.getValue());
        evt.getTextBean("userName").setValue(entry.getUser().getUserName());

        setRoleListBox(evt, entry.getRole());

        setNextState(req, DO_VIEW_GROUP_ENTRY_EDIT);
        log.debug("Exiting doViewEditGroupEntry");
    }

    public void doViewConfirmEditGroupEntry(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewConfirmEditGroupEntry");
        checkAdminRole(evt);
        PortletRequest req = evt.getPortletRequest();
        loadGroup(evt);
        GroupEntry groupEntry = loadGroupEntry(evt);

        viewGroupEntry(evt, groupEntry);
        setNextState(req, DO_VIEW_GROUP_ENTRY_VIEW);
        log.debug("Exiting doViewConfirmEditGroupEntry");
    }

    public void doViewCancelEditGroupEntry(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewCancelEditGroupEntry");
        checkAdminRole(evt);
        doViewViewGroup(evt);
        log.debug("Exiting doViewCancelEditGroupEntry");
    }

    public void doViewAddGroupEntry(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewAddGroupEntry");
        checkAdminRole(evt);
        PortletRequest req = evt.getPortletRequest();
        User user = req.getUser();
        PortletGroup group = loadGroup(evt);
        List usersNotInGroupList = getACLService(user).getUsersNotInGroup(group);

        viewUsersNotInGroupList(evt, usersNotInGroupList);
        setNextState(req, DO_VIEW_GROUP_ENTRY_ADD);
        log.debug("Exiting doViewAddGroupEntry");
    }

    public void doViewConfirmAddGroupEntry(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewConfirmAddGroupEntry");
        checkAdminRole(evt);
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
        log.debug("Entering doViewConfirmChangeRole");
        checkAdminRole(evt);
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
        log.debug("Entering doViewCancelAddGroupEntry");
        checkAdminRole(evt);
        doViewViewGroup(evt);
        log.debug("Exiting doViewCancelAddGroupEntry");
    }

    public void doViewRemoveGroupEntry(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewRemoveGroupEntry");
        checkAdminRole(evt);
        PortletRequest req = evt.getPortletRequest();
        PortletGroup group = loadGroup(evt);
        setGroupEntryList(evt, group);
        setNextState(req, DO_VIEW_GROUP_ENTRY_REMOVE);
        log.debug("Exiting doViewRemoveGroupEntry");
    }

    public void doViewConfirmRemoveGroupEntry(FormEvent evt)
            throws PortletException {
        checkAdminRole(evt);
        log.debug("Entering doViewConfirmRemoveGroupEntry");
        PortletRequest req = evt.getPortletRequest();
        loadGroup(evt);
        removeGroupEntries(evt);
        setNextState(req, DO_VIEW_GROUP_ENTRY_REMOVE_CONFIRM);
        log.debug("Exiting doViewConfirmRemoveGroupEntry");
    }

    public void doViewCancelRemoveGroupEntry(FormEvent evt)
            throws PortletException {
        checkAdminRole(evt);
        log.debug("Entering doViewCancelRemoveGroupEntry");
        doViewViewGroup(evt);
        log.debug("Exiting doViewCancelRemoveGroupEntry");
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
            groupDescription = group.getDescription();
            //groupDescription = getACLService(user).getGroupDescription(group);
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
                layoutMgr.addGroupTab(evt.getPortletRequest(), group.getName());
            }
            //layoutMgr.addGroupTab(groupEntryUser, group.getName());
            //layoutMgr.reloadPage(evt.getPortletRequest());
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

            layoutMgr.refreshPage(req);

            /*
            PortletRegistry portletRegistry = PortletRegistry.getInstance();
            List portletIds =  portletRegistry.getAllConcretePortletIDs(req.getRole(), entry.getGroup().getName());
            if (entry.getUser().getID().equals(root.getID())) {
                this.layoutMgr.removePortlets(req, portletIds);
            }
            this.layoutMgr.removePortlets(req, entry.getUser(), portletIds);
            */

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

    public void deleteGroup(FormEvent event) throws PortletException {
        checkAdminRole(event);
        PortletRequest req = event.getPortletRequest();

        String groupId = event.getAction().getParameter("groupID");

        User user = req.getUser();

        PortletGroup group = getACLService(user).getGroup(groupId);

        List users = getACLService(user).getUsers(group);
        if (users != null) {
            MessageBoxBean msg = event.getMessageBoxBean("msg");
            msg.setMessageType("error");
            msg.setValue("You must remove all users in the group before deleting it");

        } else {

        // delete group from acl service
        getACLService(user).deleteGroup(group);

        // delete group from layout registry
        PortletTabRegistry.removeGroupTab(group.getName());
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
