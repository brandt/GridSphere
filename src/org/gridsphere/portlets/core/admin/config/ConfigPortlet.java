package org.gridsphere.portlets.core.admin.config;

import org.gridsphere.portlet.PortletConfig;
import org.gridsphere.portlet.PortletException;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletSettings;
import org.gridsphere.provider.event.FormEvent;
import org.gridsphere.provider.portlet.ActionPortlet;
import org.gridsphere.provider.portletui.beans.*;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.user.LoginService;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ConfigPortlet extends ActionPortlet {


    private static String LOGIN_NUMTRIES = "ACCOUNT_NUMTRIES";
    private static String ADMIN_ACCOUNT_APPROVAL = "ADMIN_ACCOUNT_APPROVAL";
    private static String LOGIN_ERROR_HANDLING = "LOGIN_ERROR_HANDLING";
    public static String SAVE_PASSWORDS = "SAVE_PASSWORDS";
    public static String REMEMBER_USER = "REMEMBER_USER";
    public static String SUPPORT_X509_AUTH = "SUPPORT_X509_AUTH";
    public static String SEND_USER_FORGET_PASSWORD = "SEND_USER_FORGET_PASSWD";

    public static final String LOGIN_ERROR_FLAG = "LOGIN_FAILED";
    public static final Integer LOGIN_ERROR_UNKNOWN = new Integer(-1);

    public static final String DO_CONFIGURE = "admin/config/config.jsp"; //configure login

    private boolean canUserCreateAccount = false;
    private PortalConfigService portalConfigService = null;
    private LoginService loginService = null;

    public void init(PortletConfig config) throws UnavailableException {

        super.init(config);

        portalConfigService = (PortalConfigService) getPortletConfig().getContext().getService(PortalConfigService.class);
        canUserCreateAccount = Boolean.valueOf(portalConfigService.getProperty("CAN_USER_CREATE_ACCOUNT")).booleanValue();

        String numTries = portalConfigService.getProperty(LOGIN_NUMTRIES);
        System.err.println("numtries= "+ numTries);

        loginService = (LoginService) getPortletConfig().getContext().getService(LoginService.class);


        DEFAULT_VIEW_PAGE = "showConfigure";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }


    public void showConfigure(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        CheckBoxBean acctCB = event.getCheckBoxBean("acctCB");
        acctCB.setSelected(canUserCreateAccount);

        Boolean sendMail = Boolean.valueOf(portalConfigService.getProperty("ENABLE_ERROR_HANDLING"));
        req.setAttribute("sendMail", sendMail);

        CheckBoxBean notifyCB = event.getCheckBoxBean("notifyCB");
        notifyCB.setSelected(Boolean.valueOf(portalConfigService.getProperty(SEND_USER_FORGET_PASSWORD)).booleanValue());

        CheckBoxBean savepassCB = event.getCheckBoxBean("savepassCB");
        savepassCB.setSelected(Boolean.valueOf(portalConfigService.getProperty(SAVE_PASSWORDS)).booleanValue());

        CheckBoxBean supportX509CB = event.getCheckBoxBean("supportx509CB");
        supportX509CB.setSelected(Boolean.valueOf(portalConfigService.getProperty(SUPPORT_X509_AUTH)).booleanValue());

        CheckBoxBean accountApproval = event.getCheckBoxBean("acctApproval");
        accountApproval.setSelected(Boolean.valueOf(portalConfigService.getProperty(ADMIN_ACCOUNT_APPROVAL)).booleanValue());

        CheckBoxBean remUserCB = event.getCheckBoxBean("remUserCB");
        remUserCB.setSelected(Boolean.valueOf(portalConfigService.getProperty(REMEMBER_USER)).booleanValue());

        String numTries = portalConfigService.getProperty(LOGIN_NUMTRIES);
        TextFieldBean numTriesTF = event.getTextFieldBean("numTriesTF");
        if (numTries == null) {
            numTries = "-1";
        }
        numTriesTF.setValue(numTries);

        List authModules = loginService.getAuthModules();
        req.setAttribute("authModules", authModules);
        setNextState(req, DO_CONFIGURE);
    }

    public void setLoginSettings(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();
        if (!req.getRoles().contains(PortletRole.ADMIN.getName())) return;

        CheckBoxBean acctCB = event.getCheckBoxBean("acctCB");

        String useracct = acctCB.getSelectedValue();

        canUserCreateAccount = (useracct != null);

        portalConfigService.setProperty("CAN_USER_CREATE_ACCOUNT", String.valueOf(canUserCreateAccount));

        CheckBoxBean notifyCB = event.getCheckBoxBean("notifyCB");
        String notify = notifyCB.getSelectedValue();
        boolean sendForget = (notify != null);

        CheckBoxBean savepassCB = event.getCheckBoxBean("savepassCB");
        String savepass = savepassCB.getSelectedValue();
        boolean savePasswords;
        if (savepass != null) {
            savePasswords = true;
        } else {
            // if save passwords is false than can't send notification to update password so both must be false
            savePasswords = false;
            sendForget = false;
        }

        CheckBoxBean supportsx509CB = event.getCheckBoxBean("supportx509CB");
        String supportx509val = supportsx509CB.getSelectedValue();
        boolean supportx509 = (supportx509val != null);
        portalConfigService.setProperty(SUPPORT_X509_AUTH, Boolean.toString(supportx509));

        CheckBoxBean accountApproval = event.getCheckBoxBean("acctApproval");
        String accountApprovalval = accountApproval.getSelectedValue();
        boolean accountApprove = (accountApprovalval != null);
        portalConfigService.setProperty(ADMIN_ACCOUNT_APPROVAL, Boolean.toString(accountApprove));

        CheckBoxBean remUserCB = event.getCheckBoxBean("remUserCB");
        boolean remUser = (remUserCB.getSelectedValue() != null);
        portalConfigService.setProperty(REMEMBER_USER, Boolean.toString(remUser));

        portalConfigService.setProperty(SAVE_PASSWORDS, Boolean.toString(savePasswords));
        portalConfigService.setProperty(SEND_USER_FORGET_PASSWORD, Boolean.toString(sendForget));
        try {
            portalConfigService.storeProperties();
        } catch (IOException e) {
            log.error("unable to save gridsphere.properties", e);
        }
        showConfigure(event);
    }

    public void configAccountSettings(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();
        if (!req.getRoles().contains(PortletRole.ADMIN.getName())) return;
        TextFieldBean numTriesTF = event.getTextFieldBean("numTriesTF");
        String numTries = numTriesTF.getValue();
        int numtries = -1;
        try {
            numtries = Integer.valueOf(numTries).intValue();
            portalConfigService.setProperty(LOGIN_NUMTRIES, String.valueOf(numtries));
            portalConfigService.storeProperties();
        } catch (NumberFormatException e) {
            // do nothing
        } catch (IOException e) {
            log.error("Unable to save gridsphere.properties", e);
        }
        showConfigure(event);
    }

    public void configErrorSettings(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();
        if (!req.getRoles().contains(PortletRole.ADMIN.getName())) return;

        RadioButtonBean errorRB = event.getRadioButtonBean("errorRB");

        Boolean sendMail = Boolean.FALSE;
        if (errorRB.getSelectedValue().equals("MAIL")) sendMail = Boolean.TRUE;
        portalConfigService.setProperty(LOGIN_ERROR_HANDLING, sendMail.toString());
        try {
            portalConfigService.storeProperties();
        } catch (IOException e) {
            log.error("Unable to save gridsphere.properties", e);
        }
        showConfigure(event);
    }

    public void doSaveAuthModules(FormEvent event) {

        PortletRequest req = event.getPortletRequest();
        CheckBoxBean cb = event.getCheckBoxBean("authModCB");

        List activeAuthMods = cb.getSelectedValues();
        for (int i = 0; i < activeAuthMods.size(); i++) {
            System.err.println("active auth mod: " + (String) activeAuthMods.get(i));
        }

        List authModules = loginService.getAuthModules();
        Iterator it = authModules.iterator();
        while (it.hasNext()) {
            LoginAuthModule authMod = (LoginAuthModule) it.next();
            // see if a checked active auth module appears
            if (activeAuthMods.contains(authMod.getModuleName())) {
                authMod.setModuleActive(true);
            } else {
                authMod.setModuleActive(false);
            }
            String priority = req.getParameter(authMod.getModuleName());
            authMod.setModulePriority(Integer.valueOf(priority).intValue());
            loginService.saveAuthModule(authMod);
        }

        showConfigure(event);
    }

    private void createErrorMessage(FormEvent evt, String text) {
        MessageBoxBean msg = evt.getMessageBoxBean("msg");
        msg.setValue(text);
        msg.setMessageType(MessageStyle.MSG_ERROR);
    }

    private void createSuccessMessage(FormEvent evt, String text) {
        MessageBoxBean msg = evt.getMessageBoxBean("msg");
        msg.setValue(text);
        msg.setMessageType(MessageStyle.MSG_SUCCESS);
    }

}
