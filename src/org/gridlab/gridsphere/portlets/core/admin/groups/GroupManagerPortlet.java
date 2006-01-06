/*
* $Id$
*/
package org.gridlab.gridsphere.portlets.core.admin.groups;

import org.gridlab.gridsphere.layout.PortletTabRegistry;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletRoleInfo;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portlet.jsr.PortletServlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.services.core.portal.PortalConfigService;
import org.gridlab.gridsphere.services.core.portal.PortalConfigSettings;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;
import org.gridlab.gridsphere.services.core.security.group.GroupManagerService;
import org.gridlab.gridsphere.services.core.security.group.impl.UserGroup;
import org.gridlab.gridsphere.services.core.security.role.RoleManagerService;
import org.gridlab.gridsphere.services.core.user.UserManagerService;

import javax.servlet.UnavailableException;
import java.util.*;

public class GroupManagerPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String DO_VIEW_GROUP_LIST = "admin/groups/groupList.jsp";
    public static final String DO_VIEW_GROUP_VIEW = "admin/groups/groupView.jsp";
    public static final String DO_VIEW_GROUP_EDIT = "admin/groups/groupEditDefaults.jsp";
    public static final String DO_VIEW_GROUP_ENTRY_EDIT = "admin/groups/groupEntryEdit.jsp";
    public static final String DO_VIEW_GROUP_CREATE = "admin/groups/groupCreate.jsp";
    public static final String DO_VIEW_GROUP_LAYOUT = "admin/groups/groupLayout.jsp";


    // Portlet services
    private UserManagerService userManagerService = null;
    private GroupManagerService groupManagerService = null;
    private RoleManagerService roleManagerService = null;
    private LayoutManagerService layoutMgr = null;
    private PortalConfigService portalConfigService = null;

    private PortletManager portletMgr = null;
    private PortletRegistry portletRegistry = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        log.debug("Entering initServices()");
        try {
            this.userManagerService = (UserManagerService) config.getContext().getService(UserManagerService.class);
            this.groupManagerService = (GroupManagerService) this.getConfig().getContext().getService(GroupManagerService.class);
            this.roleManagerService = (RoleManagerService) this.getConfig().getContext().getService(RoleManagerService.class);
            this.layoutMgr = (LayoutManagerService) config.getContext().getService(LayoutManagerService.class);
            this.portalConfigService = (PortalConfigService) getPortletConfig().getContext().getService(PortalConfigService.class);
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
        List groupList = new ArrayList();
        List groups = groupManagerService.getGroups();
        PortletGroup coreGroup = groupManagerService.getCoreGroup();
        Iterator it = groups.iterator();
        while (it.hasNext()) {
            PortletGroup g = (PortletGroup) it.next();
            groupList.add(g);
        }
        List webappNames = portletMgr.getWebApplicationNames();
        if (webappNames.size() > 1) req.setAttribute("create", "yes");

        req.setAttribute("coreGroup", coreGroup);
        req.setAttribute("groupList", groupList);
        setNextState(req, DO_VIEW_GROUP_LIST);
        log.debug("Exiting doViewListGroup");
    }

    public void doCreateNewGroup(FormEvent event) {

        PortletRequest req = event.getPortletRequest();
        PortletGroup coreGroup = groupManagerService.getCoreGroup();
        // see if there is a group already
        PortletGroup group = null;
        Set portletRoleList = null;
        DefaultPortletAction action = event.getAction();
        if (action != null) {
            String groupName = action.getParameter("groupName");
            if (groupName != null) {
                group = groupManagerService.getGroup(groupName);
                TextFieldBean groupNameTF = event.getTextFieldBean("groupNameTF");
                groupNameTF.setValue(group.getName());
                TextFieldBean groupDescTF = event.getTextFieldBean("groupDescTF");
                groupDescTF.setValue(group.getDescription());

                req.setAttribute("groupType", group.getType());

                portletRoleList = group.getPortletRoleList();
                HiddenFieldBean gid = event.getHiddenFieldBean("groupName");
                gid.setValue(groupName);
            }
        }


        PanelBean panel = event.getPanelBean("panel");
        FrameBean frame = new FrameBean();
        DefaultTableModel model = new DefaultTableModel();

        List webappNames = portletMgr.getWebApplicationNames();

        Iterator it = webappNames.iterator();

        //String gsname = coreGroup.getName();
        //List roles = req.getRoles();
        while (it.hasNext()) {

            String g = (String) it.next();

            if (g.equals(coreGroup.getName())) {
                if (group == null) continue;
                if (!group.equals(coreGroup)) continue;
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
                ApplicationPortlet app = (ApplicationPortlet) appIt.next();
                List concPortlets = app.getConcretePortlets();
                Iterator cit = concPortlets.iterator();
                while (cit.hasNext()) {
                    boolean found = false;
                    ConcretePortlet conc = (ConcretePortlet) cit.next();
                    String concID = conc.getConcretePortletID();

                    // we don't want to list PortletServlet loader!
                    if (concID.startsWith(PortletServlet.class.getName())) continue;

                    CheckBoxBean cb = new CheckBoxBean();
                    cb.setBeanId(concID + "CB");
                    cb.setValue(concID);

                    cb.setSelected(false);

                    // set 4th column to required role listbox
                    ListBoxBean lb = new ListBoxBean();
                    lb.setBeanId(concID + "LB");

                    if (portletRoleList != null) {
                        Iterator pit = portletRoleList.iterator();
                        while (pit.hasNext() && (!found)) {

                            SportletRoleInfo info = (SportletRoleInfo) pit.next();
                            if (info.getPortletClass().equals(concID)) {
                                cb.setSelected(true);
                                PortletRole reqRole = info.getSportletRole();
                                List availroles = roleManagerService.getRoles();
                                for (int i = 0; i < availroles.size(); i++) {
                                    PortletRole thisrole = (PortletRole) availroles.get(i);
                                    ListBoxItemBean roleItem = new ListBoxItemBean();
                                    roleItem.setName(thisrole.getName());
                                    roleItem.setValue(thisrole.getName());
                                    if (thisrole.getName().equalsIgnoreCase(reqRole.getName()))
                                        roleItem.setSelected(true);
                                    lb.addBean(roleItem);
                                }
                                found = true;
                            }

                        }

                    }

                    // don't allow core portlets to be changed

                    TableRowBean newtr = new TableRowBean();
                    TableCellBean newtc = new TableCellBean();
                    newtc.addBean(cb);
                    newtr.addBean(newtc);

                    TableCellBean newtc2 = new TableCellBean();
                    TextBean tb = new TextBean();

                    // set 2nd column to portlet display name from concrete portlet
                    Locale loc = req.getLocale();

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

                    // if there was no existing group than add the list box here
                    if (!found) {
                        List availroles = roleManagerService.getRoles();
                        for (int i = 0; i < availroles.size(); i++) {
                            PortletRole thisrole = (PortletRole) availroles.get(i);
                            ListBoxItemBean roleItem = new ListBoxItemBean();
                            roleItem.setName(thisrole.getName());
                            roleItem.setValue(thisrole.getName());
                            lb.addBean(roleItem);
                        }
                    }
                    newtc = new TableCellBean();
                    newtc.addBean(lb);
                    newtr.addBean(newtc);
                    model.addTableRowBean(newtr);
                }
            }
        }


        frame.setTableModel(model);
        panel.addBean(frame);

        setNextState(req, DO_VIEW_GROUP_CREATE);
    }

    public void doMakeGroup(FormEvent evt) throws PortletException {
        PortletRequest req = evt.getPortletRequest();

        List webappNames = portletMgr.getWebApplicationNames();
        Iterator it = webappNames.iterator();
        Set portletRoles = new HashSet();

        PortletGroup coreGroup = groupManagerService.getCoreGroup();
        HiddenFieldBean groupNameHF = evt.getHiddenFieldBean("groupName");
        while (it.hasNext()) {
            String g = (String) it.next();

            if (g.equals(coreGroup.getName()) && (!groupNameHF.getValue().equals(coreGroup.getID()))) {
                continue;
            }

            List appColl = portletRegistry.getApplicationPortlets(g);

            Iterator appIt = appColl.iterator();
            while (appIt.hasNext()) {
                ApplicationPortlet app = (ApplicationPortlet) appIt.next();
                List concPortlets = app.getConcretePortlets();
                Iterator cit = concPortlets.iterator();
                while (cit.hasNext()) {
                    ConcretePortlet conc = (ConcretePortlet) cit.next();
                    String concID = conc.getConcretePortletID();
                    ListBoxBean lb = evt.getListBoxBean(concID + "LB");
                    CheckBoxBean cb = evt.getCheckBoxBean(concID + "CB");
                    if (cb.isSelected()) {
                        String reqRole = lb.getSelectedName();
                        SportletRoleInfo portletRoleInfo = new SportletRoleInfo();
                        portletRoleInfo.setPortletClass(concID);
                        portletRoleInfo.setSportletRole(roleManagerService.getRole(reqRole));
                        portletRoles.add(portletRoleInfo);
                    }
                }
            }
        }

        TextFieldBean groupTF = evt.getTextFieldBean("groupNameTF");
        TextFieldBean groupDescTF = evt.getTextFieldBean("groupDescTF");

        RadioButtonBean groupVisibility = evt.getRadioButtonBean("groupVisibility");

        PortletGroup newgroup = new PortletGroup();
        if (!groupNameHF.getValue().equals("")) {
            newgroup.setOid(groupNameHF.getValue());

            PortletGroup oldgroup = groupManagerService.getGroup(groupNameHF.getValue());
            newgroup.setCore(oldgroup.isCore());
            // if group name has been modified update group tab
            if (!oldgroup.getName().equals(groupTF.getValue())) {

                String tabfile = PortletTabRegistry.getTabDescriptorPath(oldgroup.getName());
                PortletTabRegistry.removeGroupTab(oldgroup.getName());
                try {
                    PortletTabRegistry.addGroupTab(groupTF.getValue(), tabfile);
                } catch (Exception e) {
                    log.error("unable to save group tab: " + groupTF.getValue(), e);
                }
            }
        }

        try {
            if (groupTF.getValue().equals("")) {
                this.createErrorMessage(evt, this.getLocalizedText(evt.getPortletRequest(), "GROUP_INVALID_NAME"));
                setNextState(evt.getPortletRequest(), "doCreateNewGroup");
                return;
            }
            if (groupDescTF.getValue().equals("")) {
                this.createErrorMessage(evt, this.getLocalizedText(evt.getPortletRequest(), "GROUP_INVALID_DESC"));
                setNextState(evt.getPortletRequest(), "doCreateNewGroup");
                return;
            }
            if (portletRoles.isEmpty()) {
                this.createErrorMessage(evt, this.getLocalizedText(evt.getPortletRequest(), "GROUP_INVALID_PORTLETS"));
                setNextState(evt.getPortletRequest(), "doCreateNewGroup");
                return;
            }

            newgroup.setName(groupTF.getValue());
            newgroup.setDescription(groupDescTF.getValue());
            newgroup.setPortletRoleList(portletRoles);

            PortletGroup.Type groupType = PortletGroup.Type.getType(groupVisibility.getSelectedValue());
            newgroup.setType(groupType);
            groupManagerService.saveGroup(newgroup);

            req.setAttribute("groupName", newgroup.getID());
            createSuccessMessage(evt, this.getLocalizedText(evt.getPortletRequest(), "GROUP_NEWGROUP_SUCCESS"));
            if (newgroup.getType().equals(PortletGroup.Type.PRIVATE)) {
                createSuccessMessage(evt, this.getLocalizedText(evt.getPortletRequest(), "GROUP_VISIBILITY_MOREDESC"));
            }
            PortletTabRegistry.newEmptyGroupTab(groupTF.getValue());
        } catch (Exception e) {
            log.error("Unable to save new group layout: ", e);
        }
        setNextState(req, DO_VIEW_GROUP_LAYOUT);
    }

    public void doMakeTemplateLayout(FormEvent evt) {

        String groupName = evt.getAction().getParameter("groupName");
        String fileName = evt.getTextFieldBean("layoutFileTF").getValue();
        if (fileName.equals("")) {
            this.createErrorMessage(evt, this.getLocalizedText(evt.getPortletRequest(), "GROUP_INVALID_LAYOUTFILE"));
            evt.getPortletRequest().setAttribute("groupName", groupName);
            setNextState(evt.getPortletRequest(), DO_VIEW_GROUP_LAYOUT);
            return;
        }

        PortletGroup group = groupManagerService.getGroup(groupName);
        if (group != null) {
            Set portletRoles = group.getPortletRoleList();
            try {
                // now create new group layout
                PortletTabRegistry.newTemplateGroupTab(fileName, group.getName(), portletRoles);
                User user = evt.getPortletRequest().getUser();
                if (groupManagerService.isUserInGroup(user, group)) layoutMgr.refreshPage(evt.getPortletRequest());
            } catch (Exception e) {
                log.error("Unable to save new group layout: ", e);
            }
        } else {
            log.debug("No group exists for group id: " + groupName);
        }
    }

    public void doViewViewGroup(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewViewGroup");
        PortletRequest req = evt.getPortletRequest();

        HiddenFieldBean groupNameBean = evt.getHiddenFieldBean("groupName");
        String groupName = groupNameBean.getValue();
        if (groupName == null) {
            groupName = evt.getAction().getParameter("groupName");
        }

        PortletGroup group = loadGroup(evt, groupName);

        doViewRemoveGroupEntry(evt);
        doViewAddGroupEntry(evt);

        //List groupEntries = groupManagerService.getUserGroups(group);

        List usersInGroup = groupManagerService.getUsersInGroup(group);

        req.setAttribute("usersInGroup", usersInGroup);
        setNextState(req, DO_VIEW_GROUP_VIEW);
        log.debug("Exiting doViewViewGroup");
    }

    public void doViewEditGroupEntry(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewEditGroupEntry");
        PortletRequest req = evt.getPortletRequest();
        loadGroup(evt);

        HiddenFieldBean userNameHF = evt.getHiddenFieldBean("userName");
        String userName = evt.getAction().getParameter("userName");

        userNameHF.setValue(userName);

        evt.getTextBean("userName").setValue(userName);

        setNextState(req, DO_VIEW_GROUP_ENTRY_EDIT);
        log.debug("Exiting doViewEditGroupEntry");
    }


    public void doViewAddGroupEntry(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewAddGroupEntry");

        PortletRequest req = evt.getPortletRequest();


        PortletGroup group = loadGroup(evt);

        addGroupEntries(evt, group);
        List usersNotInGroup = new Vector();
        Iterator allUsers = userManagerService.getUsers().iterator();
        while (allUsers.hasNext()) {
            User user = (User) allUsers.next();
            if (!groupManagerService.isUserInGroup(user, group)) {
                usersNotInGroup.add(user);
            }
        }
        viewUsersNotInGroupList(evt, usersNotInGroup);

        setNextState(req, DO_VIEW_GROUP_VIEW);
        log.debug("Exiting doViewAddGroupEntry");
    }

    public void doViewConfirmChangeRole(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewConfirmChangeRole");

        PortletRequest req = evt.getPortletRequest();

        HiddenFieldBean userNameHF = evt.getHiddenFieldBean("userName");
        HiddenFieldBean groupNameHF = evt.getHiddenFieldBean("groupName");

        // Create access right
        User user = userManagerService.getUser(userNameHF.getValue());
        PortletGroup group = groupManagerService.getGroup(groupNameHF.getValue());
        groupManagerService.addUserToGroup(user, group);

        setNextState(req, "doViewViewGroup");
    }

    public void doViewRemoveGroupEntry(FormEvent evt)
            throws PortletException {
        log.debug("Entering doViewRemoveGroupEntry");

        PortletRequest req = evt.getPortletRequest();
        PortletGroup group = loadGroup(evt);

        removeGroupEntry(evt);

        List usersInGroup = groupManagerService.getUsersInGroup(group);
        req.setAttribute("usersInGroup", usersInGroup);
    }

    public void doEditDefaultGroups(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        Set existingDefaults = portalConfigService.getPortalConfigSettings().getDefaultGroups();
        List groups = groupManagerService.getGroups();
        PortletGroup coreGroup = groupManagerService.getCoreGroup();
        req.setAttribute("groups", groups);


        TableBean defaultTable = evt.getFrameBean("defaultTable");
        defaultTable.setZebra(true);
        defaultTable.setWidth("50%");
        DefaultTableModel tm = new DefaultTableModel();

        TableRowBean tr = new TableRowBean();
        TableCellBean tc = new TableCellBean();
        tr.setHeader(true);

        TextBean text = new TextBean();
        text.setKey("GROUP_DEFAULT");
        tc.addBean(text);

        TableCellBean tc2 = new TableCellBean();
        TextBean text2 = new TextBean();
        text2.setKey("GROUP_NAME");
        tc2.addBean(text2);

        tr.addBean(tc);
        tr.addBean(tc2);
        tm.addTableRowBean(tr);
        Iterator it = groups.iterator();
        while (it.hasNext()) {
            PortletGroup g = (PortletGroup) it.next();
            tr = new TableRowBean();

            CheckBoxBean cb = new CheckBoxBean();
            cb.setBeanId("groupCB");
            cb.setValue(g.getName());
            if (g.equals(coreGroup)) {
                cb.setDisabled(true);
            }
            if (existingDefaults.contains(g)) {
                cb.setSelected(true);
            }

            tc = new TableCellBean();
            tc.addBean(cb);
            tr.addBean(tc);

            tc = new TableCellBean();
            text = new TextBean();

            text.setValue(g.getName());
            tc.addBean(text);

            tr.addBean(tc);

            tm.addTableRowBean(tr);
        }
        defaultTable.setTableModel(tm);

        setNextState(req, DO_VIEW_GROUP_EDIT);
    }

    public void doSaveDefaultGroups(FormEvent evt) throws PortletException {

        //System.err.println("in doSaveDefaultGroups");
        CheckBoxBean cb = evt.getCheckBoxBean("groupCB");
        PortletRequest req = evt.getPortletRequest();

        List groups = cb.getSelectedValues();
        Set defaultGroups;
        PortalConfigSettings configSettings = portalConfigService.getPortalConfigSettings();
        defaultGroups = configSettings.getDefaultGroups();
        defaultGroups.clear();
        defaultGroups.add(groupManagerService.getCoreGroup());
        Iterator it = groups.iterator();

        while (it.hasNext()) {
            String name = (String) it.next();
            PortletGroup g = groupManagerService.getGroup(name);
            if (g != null) defaultGroups.add(g);
        }


        configSettings.setDefaultGroups(defaultGroups);
        portalConfigService.savePortalConfigSettings(configSettings);
        createSuccessMessage(evt, this.getLocalizedText(req, "GROUP_SAVE_DEFGROUPS_SUCCESS"));
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    private PortletGroup loadGroup(FormEvent evt) {
        HiddenFieldBean groupNameBean = evt.getHiddenFieldBean("groupName");
        String groupName = groupNameBean.getValue();
        if (groupName == null) {
            groupName = evt.getAction().getParameter("groupName");
        }
        return loadGroup(evt, groupName);
    }

    private PortletGroup loadGroup(FormEvent evt, String groupName) {
        // Get group id
        PortletGroup group = null;
        TextBean groupNameBean = evt.getTextBean("groupNameText");
        TextBean groupLabelBean = evt.getTextBean("groupLabel");
        TextBean groupDescBean = evt.getTextBean("groupDescription");
        HiddenFieldBean groupNameHF = evt.getHiddenFieldBean("groupName");
        groupNameHF.setValue(groupName);

        String groupDescription = "";

        // Load group
        if (!groupName.equals("")) {
            group = groupManagerService.getGroup(groupName);
            //System.err.println("group= " + group.getName());
            groupDescription = group.getDescription();
            //groupDescription = getACLService(user).getGroupDescription(group);
            //System.err.println("desc=" + groupDescription);
        }

        if (groupName.equals("")) {
            // Clear group attributes
            groupNameBean.setValue("");
            groupLabelBean.setValue("");
            groupDescBean.setValue("");
        } else {
            // Set attributes
            groupNameBean.setValue(group.getName());
            groupLabelBean.setValue(group.getLabel());
            groupDescBean.setValue(groupDescription);
        }
        log.debug("Exiting loadGroup()");
        return group;
    }

    private void viewUsersNotInGroupList(FormEvent evt, List usersNotInGroupList) {
        log.debug("Entering viewUsersNotInGroupList()");
        PortletRequest req = evt.getPortletRequest();
        ListBoxBean usersNotInGroupListBox = evt.getListBoxBean("usersNotInGroupList");
        usersNotInGroupListBox.clear();
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
                User user = (User) userIterator.next();
                String fullName = user.getFullName();

                ListBoxItemBean item = new ListBoxItemBean();
                item.setName(user.getUserName());
                item.setValue(fullName);
                usersNotInGroupListBox.addBean(item);
            }
        }
    }

    private void addGroupEntries(FormEvent evt, PortletGroup group) {
        // Users to add...
        ListBoxBean userList = evt.getListBoxBean("usersNotInGroupList");
        String userVal = userList.getSelectedValue();
        if (userVal != null) {
            User groupEntryUser = this.userManagerService.getUserByUserName(userVal);
            if (groupEntryUser != null) {
                User user = evt.getPortletRequest().getUser();
                addGroupEntry(groupEntryUser, group);
                if (groupEntryUser.getID().equals(user.getID())) {
                    layoutMgr.addGroupTab(evt.getPortletRequest(), group.getName());
                }
                createSuccessMessage(evt, this.getLocalizedText(evt.getPortletRequest(), "GROUP_ADD_USER_SUCCESS") + " " + group.getName());
                //layoutMgr.addGroupTab(groupEntryUser, group.getName());
                //layoutMgr.reloadPage(evt.getPortletRequest());
            } else {
                log.debug("Unable to get user: " + userVal);
            }
        }
    }

    private UserGroup addGroupEntry(User user, PortletGroup group) {
        groupManagerService.addUserToGroup(user, group);

        // Return the new group
        return groupManagerService.getUserGroup(user, group);
    }

    private void removeGroupEntry(FormEvent event) {
        // Create remove from group request
        CheckBoxBean cb = event.getCheckBoxBean("userCB");
        String groupName = event.getHiddenFieldBean("groupName").getValue();
        PortletGroup group = groupManagerService.getGroup(groupName);
        List l = cb.getSelectedValues();
        Iterator it = l.iterator();
        if (!l.isEmpty()) {
            while (it.hasNext()) {
                String userCB = (String) it.next();
                //System.err.println("geid= " + geid);
                User user = userManagerService.getUserByUserName(userCB);

                groupManagerService.deleteUserInGroup(user, group);

                if (event.getPortletRequest().getUser().getID().equals(user.getID())) {
                    layoutMgr.removeGroupTab(event.getPortletRequest(), groupName);
                }

                createSuccessMessage(event, this.getLocalizedText(event.getPortletRequest(), "GROUP_REMOVE_USER_SUCCESS"));
            }
        }
    }


    public void deleteGroup(FormEvent event) throws PortletException {

        String groupName = event.getAction().getParameter("groupName");

        PortletGroup group = groupManagerService.getGroup(groupName);
        PortletGroup coreGroup = groupManagerService.getCoreGroup();
        List users = groupManagerService.getUsersInGroup(group);
        if (!users.isEmpty()) {
            createErrorMessage(event, this.getLocalizedText(event.getPortletRequest(), "GROUP_REMOVE_USERS_MSG"));

        } else {

            // delete group from acl service
            if (!group.equals(coreGroup)) {
                groupManagerService.deleteGroup(group);

                // delete group from layout registry
                PortletTabRegistry.removeGroupTab(group.getName());
                createSuccessMessage(event, this.getLocalizedText(event.getPortletRequest(), "GROUP_REMOVE_GROUP_SUCCESS") + " " + group.getName());

            }
        }
    }

    private void createErrorMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        msgBox.setValue(msg);
    }

    private void createSuccessMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_SUCCESS);
        msgBox.setValue(msg);
    }


}
