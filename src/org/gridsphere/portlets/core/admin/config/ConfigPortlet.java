package org.gridsphere.portlets.core.admin.config;

import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portletui.beans.CheckBoxBean;
import org.gridsphere.provider.portletui.beans.RadioButtonBean;
import org.gridsphere.provider.portletui.beans.TextAreaBean;
import org.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.gridsphere.services.core.security.auth.AuthModuleService;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.role.PortletRole;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ConfigPortlet extends ActionPortlet {

    public static final String DO_VIEW = "admin/config/view.jsp"; //configure login

    private PortalConfigService portalConfigService = null;
    private AuthModuleService authModuleService = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        portalConfigService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
        authModuleService = (AuthModuleService) PortletServiceFactory.createPortletService(AuthModuleService.class, true);
        DEFAULT_VIEW_PAGE = "showConfigure";
    }

    public void showConfigure(RenderFormEvent event) {
        PortletRequest req = event.getRenderRequest();
        CheckBoxBean acctCB = event.getCheckBoxBean("acctCB");
        boolean canUserCreateAccount = Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.CAN_USER_CREATE_ACCOUNT)).booleanValue();

        acctCB.setSelected(canUserCreateAccount);

        Boolean sendMail = Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.ENABLE_ERROR_HANDLING));
        req.setAttribute("sendMail", sendMail);

        CheckBoxBean notifyCB = event.getCheckBoxBean("notifyCB");
        notifyCB.setSelected(Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.SEND_USER_FORGET_PASSWORD)).booleanValue());

        CheckBoxBean savepassCB = event.getCheckBoxBean("savepassCB");
        savepassCB.setSelected(Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.SAVE_PASSWORDS)).booleanValue());

        CheckBoxBean supportX509CB = event.getCheckBoxBean("supportx509CB");
        supportX509CB.setSelected(Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.SUPPORT_X509_AUTH)).booleanValue());

        CheckBoxBean accountApproval = event.getCheckBoxBean("acctApproval");
        accountApproval.setSelected(Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.ADMIN_ACCOUNT_APPROVAL)).booleanValue());

        CheckBoxBean remUserCB = event.getCheckBoxBean("remUserCB");
        remUserCB.setSelected(Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.REMEMBER_USER)).booleanValue());

        String numTries = portalConfigService.getProperty(PortalConfigService.LOGIN_NUMTRIES);
        TextFieldBean numTriesTF = event.getTextFieldBean("numTriesTF");
        if (numTries == null) {
            numTries = "-1";
        }
        numTriesTF.setValue(numTries);


        Boolean isUsernameLogin = Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.USE_USERNAME_FOR_LOGIN));
        req.setAttribute("isUsernameLogin", isUsernameLogin);

        List authModules = authModuleService.getAuthModules();
        req.setAttribute("authModules", authModules);

        // configure mail settings
        TextFieldBean serverTF = event.getTextFieldBean("mailServerTF");
        serverTF.setValue(portalConfigService.getProperty(PortalConfigService.MAIL_SERVER));
        TextFieldBean portTF = event.getTextFieldBean("mailPortTF");
        portTF.setValue(portalConfigService.getProperty(PortalConfigService.MAIL_PORT));
        TextFieldBean fromTF = event.getTextFieldBean("mailFromTF");
        fromTF.setValue(portalConfigService.getProperty(PortalConfigService.MAIL_FROM));
        TextFieldBean adminTF = event.getTextFieldBean("adminTF");
        adminTF.setValue(portalConfigService.getProperty(PortalConfigService.PORTAL_ADMIN_EMAIL));

        // Portal emails to user  subject for forgotten password
        String subjectHeader = portalConfigService.getProperty("LOGIN_FORGOT_SUBJECT");
        if (subjectHeader == null) subjectHeader = getLocalizedText(req, "MAIL_SUBJECT_HEADER");
        TextFieldBean forgotHeaderTF = event.getTextFieldBean("forgotHeaderTF");
        forgotHeaderTF.setValue(subjectHeader);

        String forgotMail = portalConfigService.getProperty("LOGIN_FORGOT_BODY");
        if (forgotMail == null) forgotMail = getLocalizedText(req, "LOGIN_FORGOT_MAIL");
        TextAreaBean forgotBodyTA = event.getTextAreaBean("forgotBodyTA");
        forgotBodyTA.setValue(forgotMail);

        // Portal emails to user  subject for account activation
        String activateHeader = portalConfigService.getProperty("LOGIN_ACTIVATE_SUBJECT");
        if (activateHeader == null) activateHeader = getLocalizedText(req, "LOGIN_ACTIVATE_SUBJECT");
        TextFieldBean activateHeaderTF = event.getTextFieldBean("activateHeaderTF");
        activateHeaderTF.setValue(activateHeader);

        String activateMail = portalConfigService.getProperty("LOGIN_ACTIVATE_BODY");
        if (activateMail == null) activateMail = getLocalizedText(req, "LOGIN_ACTIVATE_MAIL");
        TextAreaBean activateBodyTA = event.getTextAreaBean("activateBodyTA");
        activateBodyTA.setValue(activateMail);

        // Email to user when their account has been approved
        String approvedSubject = portalConfigService.getProperty("LOGIN_APPROVED_SUBJECT");
        if (approvedSubject == null) approvedSubject = getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ACCOUNT_CREATED");
        TextFieldBean approvedHeaderTF = event.getTextFieldBean("approvedHeaderTF");
        approvedHeaderTF.setValue(approvedSubject);

        String approvedBody = portalConfigService.getProperty("LOGIN_APPROVED_BODY");
        if (approvedBody == null) approvedBody = getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ACCOUNT_CREATED");
        TextAreaBean approvedBodyTA = event.getTextAreaBean("approvedBodyTA");
        approvedBodyTA.setValue(approvedBody);

        // Email to user when their account has been denied
        String subject = portalConfigService.getProperty("LOGIN_DENIED_SUBJECT");
        if (subject == null) subject = getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ACCOUNT_DENY");
        TextFieldBean deniedHeaderTF = event.getTextFieldBean("deniedHeaderTF");
        deniedHeaderTF.setValue(subject);

        String  body = portalConfigService.getProperty("LOGIN_DENIED_BODY");
        if (body == null) body = getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ACCOUNT_DENY");
        TextAreaBean deniedBodyTA = event.getTextAreaBean("deniedBodyTA");
        deniedBodyTA.setValue(body);

        setNextState(req, DO_VIEW);
    }

    public void setLoginSettings(ActionFormEvent event) throws PortletException {
        PortletRequest req = event.getActionRequest();

        if (!req.isUserInRole(PortletRole.ADMIN.getName())) return;

        CheckBoxBean acctCB = event.getCheckBoxBean("acctCB");

        String useracct = acctCB.getSelectedValue();

        boolean canUserCreateAccount = (useracct != null);

        portalConfigService.setProperty(PortalConfigService.CAN_USER_CREATE_ACCOUNT, String.valueOf(canUserCreateAccount));

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
        portalConfigService.setProperty(PortalConfigService.SUPPORT_X509_AUTH, Boolean.toString(supportx509));

        CheckBoxBean accountApproval = event.getCheckBoxBean("acctApproval");
        String accountApprovalval = accountApproval.getSelectedValue();
        boolean accountApprove = (accountApprovalval != null);
        portalConfigService.setProperty(PortalConfigService.ADMIN_ACCOUNT_APPROVAL, Boolean.toString(accountApprove));

        CheckBoxBean remUserCB = event.getCheckBoxBean("remUserCB");
        boolean remUser = (remUserCB.getSelectedValue() != null);
        portalConfigService.setProperty(PortalConfigService.REMEMBER_USER, Boolean.toString(remUser));

        TextFieldBean numTriesTF = event.getTextFieldBean("numTriesTF");
        String numTries = numTriesTF.getValue();
        int numtries = -1;


        RadioButtonBean isUsernameLogin = event.getRadioButtonBean("loginRB");
        portalConfigService.setProperty(PortalConfigService.USE_USERNAME_FOR_LOGIN, isUsernameLogin.getValue());
        try {
            numtries = Integer.valueOf(numTries).intValue();
            portalConfigService.setProperty(PortalConfigService.LOGIN_NUMTRIES, String.valueOf(numtries));

            portalConfigService.setProperty(PortalConfigService.SAVE_PASSWORDS, Boolean.toString(savePasswords));
            portalConfigService.setProperty(PortalConfigService.SEND_USER_FORGET_PASSWORD, Boolean.toString(sendForget));
            portalConfigService.storeProperties();
        } catch (IOException e) {
            log.error("unable to save gridsphere.properties", e);
        } catch (NumberFormatException e) {
            // do nothing
        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void configErrorSettings(ActionFormEvent event) throws PortletException {
        PortletRequest req = event.getActionRequest();
        if (!req.isUserInRole(PortletRole.ADMIN.getName())) return;

        RadioButtonBean errorRB = event.getRadioButtonBean("errorRB");

        Boolean sendMail = Boolean.FALSE;
        if (errorRB.getSelectedValue().equals("MAIL")) sendMail = Boolean.TRUE;
        portalConfigService.setProperty(PortalConfigService.ENABLE_ERROR_HANDLING, sendMail.toString());
        try {
            portalConfigService.storeProperties();
        } catch (IOException e) {
            log.error("Unable to save gridsphere.properties", e);
        }
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void doSaveAuthModules(ActionFormEvent event) {

        PortletRequest req = event.getActionRequest();
        CheckBoxBean cb = event.getCheckBoxBean("authModCB");

        List activeAuthMods = cb.getSelectedValues();
        for (int i = 0; i < activeAuthMods.size(); i++) {
            System.err.println("active auth mod: " + (String) activeAuthMods.get(i));
        }

        List authModules = authModuleService.getAuthModules();
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
            authModuleService.saveAuthModule(authMod);
        }

        setNextState(req, DEFAULT_VIEW_PAGE);
    }


    public void doSaveMailConfig(ActionFormEvent event) {
        TextFieldBean serverTF = event.getTextFieldBean("mailServerTF");
        String mailServer = serverTF.getValue();
        portalConfigService.setProperty(PortalConfigService.MAIL_SERVER, mailServer);
        TextFieldBean portTF = event.getTextFieldBean("mailPortTF");
        String mailPort = portTF.getValue();
        portalConfigService.setProperty(PortalConfigService.MAIL_PORT, mailPort);
        TextFieldBean fromTF = event.getTextFieldBean("mailFromTF");
        String mailFrom = fromTF.getValue();
        portalConfigService.setProperty(PortalConfigService.MAIL_FROM, mailFrom);
        TextFieldBean adminTF = event.getTextFieldBean("adminTF");
        String admin = adminTF.getValue();
        portalConfigService.setProperty(PortalConfigService.PORTAL_ADMIN_EMAIL, admin);
        try {
            portalConfigService.storeProperties();
        } catch (IOException e) {
            log.error("Unable to save gridsphere.properties!", e);
        }
    }


    public void doSaveMailMessage(ActionFormEvent event) {
        String type = event.getAction().getParameter("type");
        if (type != null) {
            System.err.println("/n/n/n" + type + "/n/n/n");
            TextFieldBean subjectTF = event.getTextFieldBean(type + "HeaderTF");
            if (!subjectTF.getValue().equals("")) portalConfigService.setProperty("LOGIN_" + type.toUpperCase() + "_SUBJECT", subjectTF.getValue());
            System.err.println("tf=" + subjectTF.getValue());
            TextAreaBean bodyTA = event.getTextAreaBean(type + "BodyTA");
            if (!bodyTA.getValue().equals("")) portalConfigService.setProperty("LOGIN_" + type.toUpperCase() + "_BODY", bodyTA.getValue());
            System.err.println("ta=" + bodyTA.getValue());
            try {
                portalConfigService.storeProperties();
            } catch (IOException e) {
                log.error("Unable to save gridsphere.properties!", e);
            }
        }
    }

}
