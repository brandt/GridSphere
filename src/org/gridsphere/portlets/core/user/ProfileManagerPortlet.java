/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ProfileManagerPortlet.java 5062 2006-08-17 21:31:25Z novotny $
 */
package org.gridsphere.portlets.core.user;

import org.gridsphere.portlet.*;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.provider.event.FormEvent;
import org.gridsphere.provider.portlet.ActionPortlet;
import org.gridsphere.provider.portletui.beans.*;
import org.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridsphere.services.core.cache.CacheService;
import org.gridsphere.services.core.locale.LocaleService;
import org.gridsphere.services.core.messaging.TextMessagingService;
import org.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridsphere.services.core.security.password.PasswordEditor;
import org.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridsphere.services.core.security.role.RoleManagerService;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.user.UserManagerService;
import org.gridsphere.services.core.utils.DateUtil;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.gridsphere.portlets.core.login.LoginPortlet;
import org.gridsphere.tmf.services.TextMessageService;
import org.gridsphere.tmf.services.TextMessageServiceConfig;

import javax.servlet.UnavailableException;
import java.text.DateFormat;
import java.util.*;
import java.io.File;

public class ProfileManagerPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String VIEW_USER_JSP = "profile/viewuser.jsp";
    public static final String CONFIGURE_JSP = "profile/configure.jsp";
    public static final String HELP_JSP = "profile/help.jsp";

    // Portlet services
    private UserManagerService userManagerService = null;
    private PasswordManagerService passwordManagerService = null;
    private RoleManagerService roleManagerService = null;
    private LocaleService localeService = null;

    private TextMessagingService tms = null;
    private PortalConfigService portalConfigService = null;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        try {
            this.userManagerService = (UserManagerService) config.getContext().getService(UserManagerService.class);
            this.roleManagerService = (RoleManagerService) config.getContext().getService(RoleManagerService.class);
            this.passwordManagerService = (PasswordManagerService) config.getContext().getService(PasswordManagerService.class);
            this.localeService = (LocaleService) config.getContext().getService(LocaleService.class);
            this.tms = (TextMessagingService) config.getContext().getService(TextMessagingService.class);
            this.portalConfigService = (PortalConfigService) getPortletConfig().getContext().getService(PortalConfigService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }

    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        DEFAULT_VIEW_PAGE = "doViewUser";
        DEFAULT_HELP_PAGE = HELP_JSP;
        DEFAULT_CONFIGURE_PAGE = "doConfigureSettings";
    }

    public void doViewUser(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        setUserTable(event, false);
        DefaultTableModel messaging = getMessagingFrame(event, false);
        FrameBean messagingFrame = event.getFrameBean("messagingFrame");

        messagingFrame.setTableModel(messaging);
        messagingFrame.setValign("top");

        if (portalConfigService.getProperty(LoginPortlet.SAVE_PASSWORDS).equals(Boolean.TRUE.toString())) {
            req.setAttribute("savePass", "true");
        }

        ListBoxBean themeLB = event.getListBoxBean("themeLB");
        String[] themes = null;

        String theme = (String)req.getPortletSession().getAttribute(SportletProperties.LAYOUT_THEME);
        String renderkit = (String)req.getPortletSession().getAttribute(SportletProperties.LAYOUT_RENDERKIT);
        themeLB.clear();

        String themesPath = getPortletConfig().getContext().getRealPath("/themes");
        /// retrieve the current renderkit
        themesPath += "/" + renderkit;

        File f = new File(themesPath);
        if (f.isDirectory()) {
            themes = f.list();
        }

        for (int i = 0; i < themes.length; i++) {
            ListBoxItemBean lb = new ListBoxItemBean();
            lb.setValue(themes[i].trim());
            if (themes[i].trim().equalsIgnoreCase(theme)) lb.setSelected(true);
            themeLB.addBean(lb);
        }

        setNextState(req, VIEW_USER_JSP);
    }

    public void saveTheme(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        ListBoxBean themeLB = event.getListBoxBean("themeLB");
        String theme = themeLB.getSelectedValue();

        User user = req.getUser();
        if (user != null) {
            user.setAttribute(User.THEME, theme);
            userManagerService.saveUser(user);
            req.getPortletSession().setAttribute(SportletProperties.LAYOUT_THEME, theme);
        }
    }

    public void doConfigureSettings(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();
        String locales = getPortletSettings().getAttribute("supported-locales");
        TextFieldBean localesTF = event.getTextFieldBean("localesTF");
        localesTF.setValue(locales);
        setNextState(req, CONFIGURE_JSP);
    }

    public void setUserTable(FormEvent event, boolean disable) {
        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();

        //String logintime = DateFormat.getDateTimeInstance().format(new Date(user.getLastLoginTime()));
        req.setAttribute("logintime", DateUtil.getLocalizedDate(user,
                req.getLocale(),
                user.getLastLoginTime(), DateFormat.FULL, DateFormat.FULL));
        req.setAttribute("username", user.getUserName());

        if (req.getRoles().contains(PortletRole.ADMIN.getName())) {
            TextFieldBean userName = event.getTextFieldBean("userNameTF");
            userName.setValue(user.getUserName());
        }   else {
            TextBean userName = event.getTextBean("userName");
            userName.setValue(user.getUserName());
        }
        //userName.setDisabled(disable);

        TextFieldBean fullName = event.getTextFieldBean("fullName");
        fullName.setValue(user.getFullName());
        fullName.setDisabled(disable);

        TextFieldBean organization = event.getTextFieldBean("organization");
        organization.setValue(user.getOrganization());
        organization.setDisabled(disable);

        TextBean userRolesTB = event.getTextBean("userRoles");
        List userRoles = roleManagerService.getRolesForUser(user);
        Iterator it = userRoles.iterator();
        String userRole = "";
        while (it.hasNext()) {
            userRole += ((PortletRole)it.next()).getName() + ", ";
        }
        if (userRole.length() > 2) {
            userRolesTB.setValue(userRole.substring(0, userRole.length()-2));
        } else {
            userRolesTB.setValue(this.getLocalizedText(req, "ROLES_HASNOROLES"));
        }

        Locale locale = req.getLocale();

        req.setAttribute("locale", locale);

        ListBoxBean localeSelector = event.getListBoxBean("userlocale");
        localeSelector.clear();
        localeSelector.setSize(1);
        localeSelector.setDisabled(disable);

        Locale[] locales = localeService.getSupportedLocales();

        for (int i = 0; i < locales.length; i++) {
            Locale displayLocale = locales[i];
            ListBoxItemBean localeBean = makeLocaleBean(displayLocale.getDisplayLanguage(displayLocale), displayLocale.getLanguage(), locale);
            localeSelector.addBean(localeBean);
        }
        req.setAttribute(CacheService.NO_CACHE, CacheService.NO_CACHE);

        ListBoxBean timezoneList = event.getListBoxBean("timezones");
        Map zones = DateUtil.getLocalizedTimeZoneNames();
        Set keys = zones.keySet();
        Iterator it2 = keys.iterator();
        String userTimeZone = (String) user.getAttribute(User.TIMEZONE);
        if (userTimeZone == null) {
            userTimeZone = TimeZone.getDefault().getID();
        }

        while (it2.hasNext()) {
            String zone = (String) it2.next();
            ListBoxItemBean item = new ListBoxItemBean();
            item.setValue((String) zones.get(zone));
            item.setName(zone);
            if (userTimeZone.equals(zone)) {
                item.setSelected(true);
            }
            timezoneList.addBean(item);
        }
        timezoneList.setSize(6);
        timezoneList.sortByValue();
        timezoneList.setDisabled(disable);
        timezoneList.setMultipleSelection(false);
    }


    private DefaultTableModel getMessagingFrame(FormEvent event, boolean readonly) {
        DefaultTableModel model = new DefaultTableModel();

        PortletRequest req = event.getPortletRequest();

        Set services = tms.getServices();

        if (services.size() == 0) {
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

            for (Iterator iterator = services.iterator(); iterator.hasNext();) {
                TextMessageService textmessageService =  (TextMessageService)iterator.next();
                // tablerow
                TableRowBean trService = new TableRowBean();


                // NAME
                TableCellBean tcServiceName = new TableCellBean();
                // make text
                TextBean servicename = new TextBean();

                TextMessageServiceConfig tmsc = textmessageService.getServiceConfig();
                servicename.setValue(tmsc.getProperty(TextMessagingService.SERVICE_NAME)+":");
                tcServiceName.addBean(servicename);

                trService.addBean(tcServiceName);

                // INPUT
                TableCellBean tcServiceInput = new TableCellBean();

                // make inputfield
                TextFieldBean servicename_input = event.getTextFieldBean("TFSERVICENAME" +
                        textmessageService.getServiceConfig().getProperty(TextMessagingService.SERVICE_ID));

                if (!textmessageService.getServiceConfig().getProperty(TextMessagingService.SERVICE_ID).equals("mail")) {
                    servicename_input.setValue(tms.getServiceUserID(textmessageService.getServiceConfig().getProperty(TextMessagingService.SERVICE_ID),
                        req.getUser().getUserID()));
                } else {
                    servicename_input.setValue(req.getUser().getEmailAddress());
                }

                servicename_input.setDisabled(readonly);

                tcServiceInput.addBean(servicename_input);
                trService.addBean(tcServiceInput);


                model.addTableRowBean(trService);
            }
        }
        return model;

    }

    public void doSavePass(FormEvent event) {

        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();

        String origPasswd = event.getPasswordBean("origPassword").getValue();
        try {
            passwordManagerService.validateSuppliedPassword(user, origPasswd);
        } catch (InvalidPasswordException e) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_INVALID"));
            return;
        }

        String passwordValue = event.getPasswordBean("password").getValue();
        String confirmPasswordValue = event.getPasswordBean("confirmPassword").getValue();
        if (passwordValue == null) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_NOTSET"));
        } else
        // Otherwise, password must match confirmation
        if (!passwordValue.equals(confirmPasswordValue)) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_MISMATCH"));
            // If they do match, then validate password with our service
        } else if (passwordValue.length() == 0) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_BLANK"));
        } else if (passwordValue.length() < 5) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_TOOSHORT"));
        } else {
            // save password
            PasswordEditor editPasswd = passwordManagerService.editPassword(user);
            editPasswd.setValue(passwordValue);
            editPasswd.setDateLastModified(Calendar.getInstance().getTime());
            passwordManagerService.savePassword(editPasswd);
            createSuccessMessage(event, this.getLocalizedText(req, "USER_PASSWORD_SUCCESS"));
        }
    }


    public void doSaveUser(FormEvent event) {

        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();

        // validate user entries to create an account request
        User acctReq = validateUser(event);
        if (acctReq != null) {
            log.debug("approve account request for user: " + user.getID());
            userManagerService.saveUser(acctReq);
            String uid = (String) req.getPortletSession().getAttribute(SportletProperties.PORTLET_USER);
            user = userManagerService.getUser(uid);
            req.setAttribute(SportletProperties.PORTLET_USER, user);
            createSuccessMessage(event, this.getLocalizedText(req, "USER_UPDATE_SUCCESS"));
        }

        Set services = tms.getServices();
        for (Iterator iterator = services.iterator(); iterator.hasNext();) {
            TextMessageService textmessageService = (TextMessageService) iterator.next();
            TextFieldBean tfb = event.getTextFieldBean("TFSERVICENAME" + textmessageService.getServiceConfig().getProperty(TextMessagingService.SERVICE_ID));
            tms.setServiceUserID(textmessageService.getServiceConfig().getProperty(TextMessagingService.SERVICE_ID), req.getUser().getUserID(), tfb.getValue());
        }

    }


    private User validateUser(FormEvent event) {
        log.debug("Entering validateUser()");
        PortletRequest req = event.getPortletRequest();
        User user = req.getUser();
        StringBuffer message = new StringBuffer();
        boolean isInvalid = false;

        // get timezone
        String timeZone = event.getListBoxBean("timezones").getSelectedValue();

        // get timezone
        String locale = event.getListBoxBean("userlocale").getSelectedValue();

        // Validate user name
        String userName = "";
        if (req.getRoles().contains(PortletRole.ADMIN.getName())) {
            userName = event.getTextFieldBean("userNameTF").getValue();
            if (userName.equals("")) {
                message.append(this.getLocalizedText(req, "USER_NAME_BLANK") + "<br />");
                isInvalid = true;
            }
        }

        // Validate full name
        String fullName = event.getTextFieldBean("fullName").getValue();
        if (fullName.equals("")) {
            message.append(this.getLocalizedText(req, "USER_FULLNAME_BLANK") + "<br />");
            isInvalid = true;
        }
        // Validate given name
        String organization = event.getTextFieldBean("organization").getValue();

        // Validate e-mail
        String eMail = event.getTextFieldBean("TFSERVICENAMEmail").getValue();
        if (eMail.equals("")) {
            message.append(this.getLocalizedText(req, "USER_NEED_EMAIL") + "<br />");
            isInvalid = true;
        } else if ((eMail.indexOf("@") < 0)) {
            message.append(this.getLocalizedText(req, "USER_NEED_EMAIL") + "<br />");
            isInvalid = true;
        } else if ((eMail.indexOf(".") < 0)) {
            message.append(this.getLocalizedText(req, "USER_NEED_EMAIL") + "<br />");
            isInvalid = true;
        }

        // Throw exception if error was found
        if (isInvalid) {
            createErrorMessage(event, message.toString());
            return null;
        }

        log.debug("creating account request for user: " + user.getID());
        user.setEmailAddress(eMail);
        if (!userName.equals("")) user.setUserName(userName);
        user.setFullName(fullName);
        if (locale != null) {
            Locale loc = new Locale(locale, "", "");
            user.setAttribute(User.LOCALE, locale);
            req.getPortletSession(true).setAttribute(User.LOCALE, loc);
        }

        if (timeZone != null) user.setAttribute(User.TIMEZONE, timeZone);
        if (organization != null) user.setOrganization(organization);

        log.debug("Exiting validateUser()");
        return user;
    }

    private ListBoxItemBean makeLocaleBean(String language, String name, Locale locale) {
        ListBoxItemBean bean = new ListBoxItemBean();
        String display;
        display = language.substring(0, 1).toUpperCase() + language.substring(1);

        bean.setValue(display);
        bean.setName(name);

        if (locale.getLanguage().equals(name)) {
            bean.setSelected(true);
        }
        return bean;
    }


    private void createErrorMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        msgBox.appendText(msg);
    }

    private void createSuccessMessage(FormEvent event, String msg) {
        MessageBoxBean msgBox = event.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_SUCCESS);
        msgBox.appendText(msg);
    }
}
