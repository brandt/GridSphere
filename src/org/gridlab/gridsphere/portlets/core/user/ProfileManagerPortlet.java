/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.user;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.security.acl.GroupRequest;
import org.gridlab.gridsphere.services.core.security.acl.InvalidGroupRequestException;
import org.gridlab.gridsphere.services.core.security.acl.GroupEntry;
import org.gridlab.gridsphere.services.core.security.acl.GroupAction;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.InvalidAccountRequestException;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.services.core.messaging.TextMessagingService;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;
import org.gridlab.gridsphere.tmf.config.TmfService;
import org.gridlab.gridsphere.tmf.config.TmfUser;

import javax.servlet.UnavailableException;
import java.util.*;
import java.text.DateFormat;
import java.io.IOException;

public class ProfileManagerPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String VIEW_USER_JSP = "profile/viewuser.jsp";
    public static final String EDIT_USER_JSP = "profile/edituser.jsp";
    public static final String CONFIGURE_JSP = "profile/configure.jsp";
    public static final String HELP_JSP = "profile/help.jsp";

    // Portlet services
    private UserManagerService userManagerService = null;
    private AccessControlManagerService aclManagerService = null;
    private LayoutManagerService layoutMgr = null;
    private PortletRegistry portletRegistry = null;
    private TextMessagingService tms = null;


    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        this.log.debug("Entering initServices()");
        try {
            this.userManagerService = (UserManagerService)config.getContext().getService(UserManagerService.class);
            this.aclManagerService = (AccessControlManagerService)config.getContext().getService(AccessControlManagerService.class);
            this.portletRegistry = PortletRegistry.getInstance();
            this.layoutMgr = (LayoutManagerService)config.getContext().getService(LayoutManagerService.class);
            this.tms = (TextMessagingService) config.getContext().getService(TextMessagingService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        this.log.debug("Exiting initServices()");

    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        DEFAULT_VIEW_PAGE = "doViewUser";
        DEFAULT_EDIT_PAGE = "doEditUser";
        DEFAULT_HELP_PAGE = HELP_JSP;
        DEFAULT_CONFIGURE_PAGE = "doConfigureSettings";
    }

    public void doViewUser(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        DefaultTableModel model = setUserTable(event, true);
        DefaultTableModel messaging = getMessagingFrame(event, true);
        FrameBean messagingFrame = event.getFrameBean("messagingFrame");
        FrameBean groupsFrame = event.getFrameBean("groupsFrame");
        groupsFrame.setTableModel(model);
        messagingFrame.setTableModel(messaging);
        setNextState(req, VIEW_USER_JSP);
    }

    public void doEditUser(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        DefaultTableModel model = setUserTable(event, false);
        DefaultTableModel messaging = getMessagingFrame(event, false);
        FrameBean messagingFrame = event.getFrameBean("messagingFrame");
        FrameBean groupsFrame = event.getFrameBean("groupsFrame");
        groupsFrame.setTableModel(model);
        messagingFrame.setTableModel(messaging);
        setNextState(req, EDIT_USER_JSP);
    }

    public void doConfigureSettings(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();
        String locales = getPortletSettings().getAttribute("supported-locales");
        TextFieldBean localesTF = event.getTextFieldBean("localesTF");
        localesTF.setValue(locales);
        setNextState(req, CONFIGURE_JSP);
    }

    public void doSaveLocales(FormEvent event) {
        TextFieldBean tf = event.getTextFieldBean("localesTF");
        String locales = tf.getValue();
        if (locales != null) {
            FrameBean msg = event.getFrameBean("msgFrame");
            getPortletSettings().setAttribute("supported-locales", locales);
            try {
                getPortletSettings().store();
            } catch (IOException e) {
                msg.setKey("PROFILE_SAVE_ERROR");
                msg.setStyle("error");
            }
            msg.setKey("PROFILE_SAVE_SUCCESS");
            msg.setStyle("success");
        }
    }

    public DefaultTableModel setUserTable(FormEvent event, boolean disable) {
        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();

        String logintime = DateFormat.getDateTimeInstance().format(new Date(user.getLastLoginTime()));
        req.setAttribute("logintime", logintime);
        req.setAttribute("username", user.getUserName());

        TextFieldBean userName =  event.getTextFieldBean("userName");
        userName.setValue(user.getUserName());
        userName.setDisabled(disable);

        TextFieldBean fullName =  event.getTextFieldBean("fullName");
        fullName.setValue(user.getFullName());
        fullName.setDisabled(disable);

        TextFieldBean organization =  event.getTextFieldBean("organization");
        organization.setValue(user.getOrganization());
        organization.setDisabled(disable);

        TextFieldBean email =  event.getTextFieldBean("email");
        email.setValue(user.getEmailAddress());
        email.setDisabled(disable);

        DefaultTableModel model = new DefaultTableModel();

        // fill in groups model

        TableRowBean tr = new TableRowBean();
        tr.setHeader(true);
        TableCellBean tcGroups = new TableCellBean();
        TextBean tbGroups = new TextBean();

        String text = this.getLocalizedText(req, "PROFILE_GROUPS");
        tbGroups.setValue(text);

        TextBean tbGroupsDesc = new TextBean();
        String desc =  this.getLocalizedText(req, "PROFILE_GROUP_DESC");
        tbGroupsDesc.setValue(desc);
        tcGroups.addBean(tbGroups);
        TableCellBean tcGroupsDesc = new TableCellBean();
        tcGroupsDesc.addBean(tbGroupsDesc);
        tr.addBean(tcGroups);
        tr.addBean(tcGroupsDesc);
        model.addTableRowBean(tr);

        List groups = aclManagerService.getGroups();
        Iterator it = groups.iterator();
        TableRowBean groupsTR = null;
        TableCellBean groupsTC = null;
        TableCellBean groupsDescTC = null;
        while (it.hasNext()) {
            groupsTR = new TableRowBean();
            groupsTC = new TableCellBean();
            groupsDescTC = new TableCellBean();
            PortletGroup g = (PortletGroup)it.next();
            String groupDesc = aclManagerService.getGroupDescription(g);

            CheckBoxBean cb = new CheckBoxBean();
            cb.setBeanId("groupCheckBox");
            if (aclManagerService.isUserInGroup(user, g)) cb.setSelected(true);
            cb.setValue(g.getName());
            cb.setDisabled(disable);
            // make sure user cannot deselect core gridsphere group
            if (g.equals(PortletGroupFactory.GRIDSPHERE_GROUP)) cb.setDisabled(true);

            //System.err.println("g= " + g.getName() + " gridsphere group= " + PortletGroupFactory.GRIDSPHERE_GROUP.getName());

            TextBean groupTB = new TextBean();
            groupTB.setValue(g.getName());
            if (!g.isPublic() && (!cb.isSelected())) {
                cb.setDisabled(true);
            }
            groupsTC.addBean(cb);
            groupsTC.addBean(groupTB);
            TextBean groupDescTB = new TextBean();
            groupDescTB.setValue(groupDesc);
            groupsDescTC.addBean(groupDescTB);
            if (!g.isPublic()) {
                TextBean priv = event.getTextBean("privateTB");
                priv.setValue("&nbsp;&nbsp;" + this.getLocalizedText(req, "GROUP_NOTIFY"));
                List admins = aclManagerService.getUsers(g, PortletRole.ADMIN);
                String emailAddress = "";
                if (admins.isEmpty()) {
                    List supers = aclManagerService.getUsersWithSuperRole();
                    User root = (User)supers.get(0);
                    emailAddress = root.getEmailAddress();
                } else {
                    User admin = (User)admins.get(0);
                    emailAddress = admin.getEmailAddress();
                }
                String mailhref = "&nbsp;<a href=\"mailto:" + emailAddress + "\">" + this.getLocalizedText(req, "GROUP_ADMIN") + "</a>";
                TextBean mailTB = new TextBean();
                mailTB.setValue(mailhref);
                groupsDescTC.addBean(priv);
                groupsDescTC.addBean(mailTB);
            }
            groupsTR.addBean(groupsTC);
            groupsTR.addBean(groupsDescTC);
            model.addTableRowBean(groupsTR);
        }


        return model;
    }


    private DefaultTableModel getMessagingFrame(FormEvent event, boolean readonly) {
        DefaultTableModel model = new DefaultTableModel();
        PortletRequest req = event.getPortletRequest();

        TableRowBean trMessaging = new TableRowBean();

        TableCellBean tcMessagingDesc = new TableCellBean();
        TableCellBean tcMessagingUserid = new TableCellBean();


        TextBean tbMessagingDesc = event.getTextBean("tbMessagingDesc");
        TextBean tbMessagingUserid = event.getTextBean("tbMessagingUserid");
        String text = this.getLocalizedText(req, "PROFILE_MESSAGING_SERVICE");
        tbMessagingDesc.setValue(text);
        tcMessagingDesc.addBean(tbMessagingDesc);
        tbMessagingUserid = event.getTextBean("tbMessagingUserid");
        text = this.getLocalizedText(req, "PROFILE_MESSAGING_USERID");
        tbMessagingUserid.setValue(text);
        tcMessagingUserid.addBean(tbMessagingUserid);

        trMessaging.addBean(tcMessagingDesc);
        trMessaging.addBean(tcMessagingUserid);
        // add the header to the model
        trMessaging.setHeader(true);

        model.addTableRowBean(trMessaging);

        List services = tms.getServices();


        if (services.size()==0) {
            TableRowBean noServiceRow = new TableRowBean();
            TableCellBean noServiceCell1 = new TableCellBean();
            TableCellBean noServiceCell2 = new TableCellBean();
            String localeText = this.getLocalizedText(req, "PROFILE_MESSAGING_NO_SERVICE_CONFIGURED");
            TextBean noServiceText = new TextBean();
            noServiceText.setValue(localeText);
            noServiceCell1.addBean(noServiceText);
            TextBean noServiceText2 = new TextBean();
            noServiceText2.setValue("&nbsp;");
            noServiceCell2.addBean(noServiceText2);
            noServiceRow.addBean(noServiceCell1);
            noServiceRow.addBean(noServiceCell2);
            model.addTableRowBean(noServiceRow);

        } else {

            for (int i=0;i<services.size();i++) {
                TmfService tmfservice = (TmfService) services.get(i);

                // tablerow
                TableRowBean trService = new TableRowBean();

                // NAME
                TableCellBean tcServiceName = new TableCellBean();
                // make text
                TextBean servicename = new TextBean();

                String localeText = this.getLocalizedText(req, tmfservice.getDescription());
                servicename.setValue(localeText);
                tcServiceName.addBean(servicename);
                trService.addBean(tcServiceName);

                // INPUT
                TableCellBean tcServiceInput = new TableCellBean();
                // make inputfield
                TextFieldBean servicename_input = new TextFieldBean();
                TmfUser user = tms.getUser(req.getUser().getUserID());
                if (user!=null) {
                    servicename_input.setValue(user.getUserNameForMessagetype(tmfservice.getMessageType()));
                }
                servicename_input.setDisabled(readonly);
                tcServiceInput.addBean(servicename_input);
                trService.addBean(tcServiceInput);
                model.addTableRowBean(trService);
            }
        }
        return model;
    }

    public void doSaveUser(FormEvent event) {

        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();
        TextFieldBean userName =  event.getTextFieldBean("userName");
        String username = userName.getValue();

        TextFieldBean fullName =  event.getTextFieldBean("fullName");
        String fullname = fullName.getValue();

        TextFieldBean orgTF =  event.getTextFieldBean("organization");
        String organization = orgTF.getValue();

        TextFieldBean emailTF =  event.getTextFieldBean("email");
        String email = emailTF.getValue();

        AccountRequest acctReq = userManagerService.createAccountRequest(user);
        if (email != null) acctReq.setEmailAddress(email);
        if (username != null) acctReq.setUserName(username);
        if (fullname != null) acctReq.setFullName(fullname);
        if (organization != null) acctReq.setOrganization(organization);
        acctReq.setPasswordValidation(false);
        try {
            userManagerService.submitAccountRequest(acctReq);
        } catch (InvalidAccountRequestException e) {
            log.error("in ProfileManagerPortlet invalid account request", e);
        }
        user = userManagerService.approveAccountRequest(acctReq);

        CheckBoxBean groupsCB = event.getCheckBoxBean("groupCheckBox");
        List groups = groupsCB.getSelectedValues();


        List groupEntries = aclManagerService.getGroupEntries(user);
        Iterator geIt = groupEntries.iterator();
        List usergroups = new ArrayList();
        while (geIt.hasNext()) {
            GroupEntry ge = (GroupEntry)geIt.next();
            if (!ge.getGroup().equals(PortletGroupFactory.GRIDSPHERE_GROUP)) {
                aclManagerService.deleteGroupEntry(ge);
                usergroups.add(ge.getGroup().getName());
            }
        }

        // approve all selected group requests
        Iterator it = groups.iterator();
        while (it.hasNext()) {
            String groupStr = (String)it.next();
            PortletGroup g = this.aclManagerService.getGroupByName(groupStr);
            GroupEntry ge = this.aclManagerService.getGroupEntry(user, g);
            if (!usergroups.contains(g.getName())) {

            GroupRequest groupRequest = this.aclManagerService.createGroupRequest(ge);
            groupRequest.setUser(user);

            groupRequest.setGroup(g);
            groupRequest.setRole(PortletRole.USER);
            groupRequest.setGroupAction(GroupAction.ADD);

            // Create access right
            try {
                this.aclManagerService.submitGroupRequest(groupRequest);
            } catch (InvalidGroupRequestException e) {
                log.error("in ProfileManagerPortlet invalid group request", e);
            }
            this.aclManagerService.approveGroupRequest(groupRequest);
                usergroups.remove(g.getName());
            }
            this.layoutMgr.addApplicationTab(req, g.getName());
            this.layoutMgr.reloadPage(req);
        }

        // subtract groups

        it = usergroups.iterator();
        while (it.hasNext()) {
            String groupStr = (String)it.next();
            PortletGroup g = this.aclManagerService.getGroupByName(groupStr);
            GroupEntry entry = this.aclManagerService.getGroupEntry(user, g);
            GroupRequest groupRequest = this.aclManagerService.createGroupRequest(entry);
            groupRequest.setGroupAction(GroupAction.REMOVE);

            // Create access right
            try {
                this.aclManagerService.submitGroupRequest(groupRequest);
            } catch (InvalidGroupRequestException e) {
                log.error("in ProfileManagerPortlet invalid group request", e);
            }
            this.aclManagerService.approveGroupRequest(groupRequest);
            List portletIds =  portletRegistry.getAllConcretePortletIDs(req.getRole(), g.getName());
            this.layoutMgr.removePortlets(req, portletIds);
            this.layoutMgr.reloadPage(req);
        }

        // now do the messaging stuff

        TmfUser tmfuser = tms.getUser(req.getUser().getUserID());
        // if the user does not exist yet
        if (tmfuser==null) {
            tmfuser = new TmfUser();
            tmfuser.setName(event.getPortletRequest().getUser().getFullName());
            tmfuser.setUserid(req.getUser().getUserID());
            //tmfuser.setPreferred(h_service.getValue());
        }

        List services = tms.getServices();
        for (int i=0;i<services.size();i++) {
            TmfService tmfservice = (TmfService) services.get(i);

            TextFieldBean tfb = event.getTextFieldBean("TFSERVICENAME"+tmfservice.getMessageType());
            tmfuser.setMessageType(tmfservice.getMessageType(), tfb.getValue());
        }

        tms.saveUser(tmfuser);

    }



}
