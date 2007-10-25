/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridsphere.portlets.core.user;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.FormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portletui.beans.*;
import org.gridsphere.services.core.locale.LocaleService;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridsphere.services.core.security.password.PasswordEditor;
import org.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.security.role.RoleManagerService;
import org.gridsphere.services.core.user.User;
import org.gridsphere.services.core.user.UserManagerService;
import org.gridsphere.services.core.utils.DateUtil;

import javax.portlet.*;
import java.io.File;
import java.text.DateFormat;
import java.util.*;

public class ProfileManagerPortlet extends ActionPortlet {

    public static String USER_PROFILE_PUBLIC = "user.publicprofile";

    // JSP pages used by this portlet
    public static final String VIEW_USER_JSP = "profile/viewuser.jsp";
    public static final String CONFIGURE_JSP = "profile/configure.jsp";
    public static final String HELP_JSP = "profile/help.jsp";
    public static final String EDIT_PASSWD_JSP = "profile/editpassword.jsp";

    // Portlet services
    private UserManagerService userManagerService = null;
    private PasswordManagerService passwordManagerService = null;
    private RoleManagerService roleManagerService = null;
    private LocaleService localeService = null;
    private PortalConfigService portalConfigService = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        this.userManagerService = (UserManagerService) createPortletService(UserManagerService.class);
        this.roleManagerService = (RoleManagerService) createPortletService(RoleManagerService.class);
        this.passwordManagerService = (PasswordManagerService) createPortletService(PasswordManagerService.class);
        this.localeService = (LocaleService) createPortletService(LocaleService.class);
        this.portalConfigService = (PortalConfigService) createPortletService(PortalConfigService.class);
        DEFAULT_VIEW_PAGE = "doViewUser";
        DEFAULT_HELP_PAGE = HELP_JSP;
        DEFAULT_CONFIGURE_PAGE = "doConfigureSettings";
    }


    public void doViewUser(RenderFormEvent event) {
        PortletRequest req = event.getRenderRequest();
        setUserTable(event, req);

        if (portalConfigService.getProperty(PortalConfigService.SAVE_PASSWORDS).equals(Boolean.TRUE.toString())) {
            req.setAttribute("savePass", "true");
        }

        User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);

        String email = user.getEmailAddress();
        TextFieldBean emailTF = event.getTextFieldBean("emailTF");
        emailTF.setValue(email);

        CheckBoxBean privacyCB = event.getCheckBoxBean("privacyCB");
        if ((user.getAttribute(USER_PROFILE_PUBLIC) != null) && (user.getAttribute(USER_PROFILE_PUBLIC).equals("true"))) {
            privacyCB.setSelected(true);
        } else {
            privacyCB.setSelected(false);
        }

        ListBoxBean themeLB = event.getListBoxBean("themeLB");
        String[] themes = new String[]{};

        String theme = (String) user.getAttribute(User.THEME);
        String renderkit = (String) req.getPortletSession().getAttribute(SportletProperties.LAYOUT_RENDERKIT, PortletSession.APPLICATION_SCOPE);
        themeLB.clear();

        String themesPath = getPortletContext().getRealPath("themes");
        /// retrieve the current renderkit
        themesPath += File.separator + renderkit;

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

    public void doEditPassword(ActionFormEvent event) {
        ActionRequest req = event.getActionRequest();
        setNextState(req, EDIT_PASSWD_JSP);
    }

    public void saveTheme(ActionFormEvent event) {
        PortletRequest req = event.getActionRequest();
        ListBoxBean themeLB = event.getListBoxBean("themeLB");
        String theme = themeLB.getSelectedValue();

        String loginName = req.getRemoteUser();

        User user = userManagerService.getUserByUserName(loginName);
        if (user != null) {
            user.setAttribute(User.THEME, theme);
            userManagerService.saveUser(user);
            req.getPortletSession().setAttribute(SportletProperties.LAYOUT_THEME, theme, PortletSession.APPLICATION_SCOPE);
        }
    }

    public void setUserTable(FormEvent event, PortletRequest req) {

        User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);
        //String uid = (String) req.getPortletSession().getAttribute(SportletProperties.PORTLET_USER, PortletSession.APPLICATION_SCOPE);
        //User user = userManagerService.getUser(uid);

        //String logintime = DateFormat.getDateTimeInstance().format(new Date(user.getLastLoginTime()));
        req.setAttribute("logintime", DateUtil.getLocalizedDate(user,
                req.getLocale(),
                user.getLastLoginTime(), DateFormat.FULL, DateFormat.FULL));
        req.setAttribute("username", user.getUserName());

        if (req.isUserInRole(PortletRole.ADMIN.getName())) {
            TextFieldBean userName = event.getTextFieldBean("userNameTF");
            userName.setValue(user.getUserName());
        } else {
            TextBean userName = event.getTextBean("userName");
            userName.setValue(user.getUserName());
        }
        //userName.setDisabled(disable);

        TextFieldBean firstName = event.getTextFieldBean("firstName");
        firstName.setValue(user.getFirstName());


        TextFieldBean lastName = event.getTextFieldBean("lastName");
        lastName.setValue(user.getLastName());

        TextFieldBean organization = event.getTextFieldBean("organization");
        organization.setValue(user.getOrganization());

        TextBean userRolesTB = event.getTextBean("userRoles");
        List userRoles = roleManagerService.getRolesForUser(user);
        Iterator it = userRoles.iterator();
        String userRole = "";
        while (it.hasNext()) {
            userRole += ((PortletRole) it.next()).getName() + ", ";
        }
        if (userRole.length() > 2) {
            userRolesTB.setValue(userRole.substring(0, userRole.length() - 2));
        } else {
            userRolesTB.setValue(this.getLocalizedText(req, "ROLES_HASNOROLES"));
        }

        Locale locale = req.getLocale();

        req.setAttribute("locale", locale);

        ListBoxBean localeSelector = event.getListBoxBean("userlocale");
        localeSelector.clear();
        localeSelector.setSize(1);

        Locale[] locales = localeService.getSupportedLocales();

        for (int i = 0; i < locales.length; i++) {
            Locale displayLocale = locales[i];
            ListBoxItemBean localeBean = makeLocaleBean(displayLocale.getDisplayLanguage(displayLocale), displayLocale.getLanguage(), locale);
            localeSelector.addBean(localeBean);
        }

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
        timezoneList.setSize(1);
        timezoneList.sortByValue();
        timezoneList.setMultipleSelection(false);
    }

    public void doSavePass(ActionFormEvent event) {

        PortletRequest req = event.getActionRequest();
        User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);

        String origPasswd = event.getPasswordBean("origPassword").getValue();
        String passwordValue = event.getPasswordBean("password").getValue();
        String confirmPasswordValue = event.getPasswordBean("confirmPassword").getValue();

        if (origPasswd.equals("") && passwordValue.equals("") && confirmPasswordValue.equals("")) return;

        try {
            passwordManagerService.validateSuppliedPassword(user, origPasswd);
        } catch (InvalidPasswordException e) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_INVALID"));
            return;
        }

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


    public void doSaveUser(ActionFormEvent event) {

        PortletRequest req = event.getActionRequest();
        User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);

        // validate user entries to create an account request
        User acctReq = validateUser(event);
        if (acctReq != null) {
            log.debug("approve account request for user: " + user.getID());
            userManagerService.saveUser(acctReq);
            String uid = (String) req.getPortletSession().getAttribute(SportletProperties.PORTLET_USER, PortletSession.APPLICATION_SCOPE);
            user = userManagerService.getUser(uid);
            req.setAttribute(SportletProperties.PORTLET_USER, user);
            createSuccessMessage(event, this.getLocalizedText(req, "USER_UPDATE_SUCCESS"));
        }
    }


    private User validateUser(ActionFormEvent event) {
        log.debug("Entering validateUser()");
        PortletRequest req = event.getActionRequest();
        User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);

        StringBuffer message = new StringBuffer();
        boolean isInvalid = false;

        // get timezone
        String timeZone = event.getListBoxBean("timezones").getSelectedValue();

        // get timezone
        String locale = event.getListBoxBean("userlocale").getSelectedValue();

        // Validate user name
        String userName = "";
        if (req.isUserInRole(PortletRole.ADMIN.getName())) {
            userName = event.getTextFieldBean("userNameTF").getValue();
            if (userName.equals("")) {
                message.append(this.getLocalizedText(req, "USER_NAME_BLANK")).append("<br />");
                isInvalid = true;
            }
        }

        // Validate first name
        String firstName = event.getTextFieldBean("firstName").getValue();
        if (firstName.equals("")) {
            message.append(this.getLocalizedText(req, "USER_GIVENNAME_BLANK")).append("<br />");
            isInvalid = true;
        }
        // Validate last name
        String lastName = event.getTextFieldBean("lastName").getValue();
        if (lastName.equals("")) {
            message.append(this.getLocalizedText(req, "USER_FAMILYNAME_BLANK")).append("<br />");
            isInvalid = true;
        }

        String organization = event.getTextFieldBean("organization").getValue();

        // Validate e-mail
        String eMail = event.getTextFieldBean("emailTF").getValue();
        if (eMail.equals("")) {
            message.append(this.getLocalizedText(req, "USER_NEED_EMAIL")).append("<br />");
            isInvalid = true;
        } else if ((eMail.indexOf("@") < 0)) {
            message.append(this.getLocalizedText(req, "USER_NEED_EMAIL")).append("<br />");
            isInvalid = true;
        } else if ((eMail.indexOf(".") < 0)) {
            message.append(this.getLocalizedText(req, "USER_NEED_EMAIL")).append("<br />");
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

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setFullName(lastName + ", " + firstName);
        if (locale != null) {
            Locale loc = new Locale(locale, "", "");
            user.setAttribute(User.LOCALE, locale);
            req.getPortletSession(true).setAttribute(User.LOCALE, loc, PortletSession.APPLICATION_SCOPE);
        }

        if (timeZone != null) user.setAttribute(User.TIMEZONE, timeZone);
        if (organization != null) user.setOrganization(organization);

        log.debug("Exiting validateUser()");
        return user;
    }

/*    public void savePrivacy(ActionFormEvent event) {
        CheckBoxBean privacyCB = event.getCheckBoxBean("privacyCB");
        ActionRequest req = event.getActionRequest();
        User user = (User) req.getAttribute(SportletProperties.PORTLET_USER);
        if (privacyCB.isSelected()) {
            user.setAttribute(USER_PROFILE_PUBLIC, "true");
        } else {
            user.setAttribute(USER_PROFILE_PUBLIC, "false");
        }
        userManagerService.saveUser(user);
    }  */

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

    public void doCancel(ActionFormEvent event) {
        ActionRequest req = event.getActionRequest();
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void doSaveAll(ActionFormEvent event) {
        doSaveUser(event);
        //doSavePass(event);
        saveTheme(event);
//        savePrivacy(event);
    }


}
