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
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;

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
    private List supportedLocales = null;
    private PortletRegistry portletRegistry = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        this.log.debug("Entering initServices()");
        try {
            this.userManagerService = (UserManagerService)config.getContext().getService(UserManagerService.class);
            this.aclManagerService = (AccessControlManagerService)config.getContext().getService(AccessControlManagerService.class);
            this.portletRegistry = PortletRegistry.getInstance();
            this.layoutMgr = (LayoutManagerService)config.getContext().getService(LayoutManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        this.log.debug("Exiting initServices()");

    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        String locales = settings.getAttribute("supported-locales");
        StringTokenizer st = new StringTokenizer(locales, ",");
        supportedLocales = new Vector();
        while (st.hasMoreElements()) {
            String s = (String)st.nextElement();
            supportedLocales.add(s.trim());
        }

        DEFAULT_VIEW_PAGE = "doViewUser";
        DEFAULT_EDIT_PAGE = "doEditUser";
        DEFAULT_HELP_PAGE = HELP_JSP;
        DEFAULT_CONFIGURE_PAGE = "doConfigureSettings";
    }

    public void doViewUser(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        DefaultTableModel model = setUserTable(event, true);
        FrameBean groupsFrame = event.getFrameBean("groupsFrame");
        groupsFrame.setTableModel(model);
        setNextState(req, VIEW_USER_JSP);
    }

    public void doEditUser(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        DefaultTableModel model = setUserTable(event, false);
        FrameBean groupsFrame = event.getFrameBean("groupsFrame");
        groupsFrame.setTableModel(model);
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

        TextFieldBean localeTF = event.getTextFieldBean("mylocale");

        // fill in locale
        String selectedValue = "";
        ListBoxBean localeLB = event.getListBoxBean("userLocale");

        selectedValue = localeLB.getSelectedValue();
        if (selectedValue != null) localeLB.clear();

        String locale = (String)user.getAttribute(User.LOCALE);
        if (locale == null) {
            locale = "en";
        }
        Locale userLocale = new Locale(locale, "", "");

        localeTF.setValue(userLocale.getDisplayLanguage());
        localeTF.setDisabled(disable);

        String dispVal = "";
        for (int i = 0; i < supportedLocales.size(); i++) {
            ListBoxItemBean item = new ListBoxItemBean();
            String localeVal = (String)supportedLocales.get(i);
            Locale loc = new Locale(localeVal, "", "");
            dispVal = loc.getDisplayLanguage(userLocale);

            //System.err.println("selectedValue: " + selectedValue + " localeVal=" + localeVal);
            if (selectedValue != null) {
                if (localeVal.equals(selectedValue)) item.setSelected(true);
            } else {
                if (localeVal.equals(locale)) item.setSelected(true);
            }
            item.setName(localeVal);
            item.setValue(dispVal);
            item.setDisabled(disable);
            localeLB.addBean(item);
        }

        // fill in groups model

        TableRowBean tr = new TableRowBean();
        tr.setHeader(true);
        TableCellBean tcGroups = new TableCellBean();
        TextBean tbGroups = new TextBean();

        String text = this.getLocalizedText(req, "PROFILE_GROUPS");
        tbGroups.setValue(text);
        //tbGroups.setValue("Groups:");
        //tbGroups.setKey("PROFILE_GROUPS");
        TextBean tbGroupsDesc = new TextBean();
        String desc =  this.getLocalizedText(req, "PROFILE_GROUP_DESC");
        tbGroupsDesc.setValue(desc);
        tcGroups.addBean(tbGroups);
        TableCellBean tcGroupsDesc = new TableCellBean();
        tcGroupsDesc.addBean(tbGroupsDesc);
        tr.addBean(tcGroups);
        tr.addBean(tcGroupsDesc);
        DefaultTableModel model = new DefaultTableModel();
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
                priv.setValue("&nbsp;&nbsp;This group is private. Please email the ");
                List supers = aclManagerService.getUsersWithSuperRole();
                User root = (User)supers.get(0);
                String mailhref = "<a href=\"mailto:" + root.getEmailAddress() + "\">portal administrator</a>";
                System.err.println(mailhref);
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

        // fill in locale
        ListBoxBean localeLB = event.getListBoxBean("userLocale");
        String language = localeLB.getSelectedValue();

        AccountRequest acctReq = userManagerService.createAccountRequest(user);
        acctReq.setAttribute(User.LOCALE, language);

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


    }



}
