package org.gridsphere.portlets.core.admin.config;

import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portletui.beans.*;
import org.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.auth.LoginService;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.messaging.TextMessagingService;
import org.gridsphere.tmf.services.TMService;
import org.gridsphere.tmf.services.TextMessageServiceConfig;
import org.gridsphere.tmf.TextMessagingException;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    public static final String DO_VIEW = "admin/config/view.jsp"; //configure login

    private PortalConfigService portalConfigService = null;
    private LoginService loginService = null;
    private TextMessagingService tms = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        portalConfigService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
        loginService = (LoginService) PortletServiceFactory.createPortletService(LoginService.class, true);
        tms = (TextMessagingService) PortletServiceFactory.createPortletService(TextMessagingService.class, true);
        DEFAULT_VIEW_PAGE = "showConfigure";
    }

    public void showConfigure(RenderFormEvent event) {
        PortletRequest req = event.getRenderRequest();
        CheckBoxBean acctCB = event.getCheckBoxBean("acctCB");
        boolean canUserCreateAccount = Boolean.valueOf(portalConfigService.getProperty("CAN_USER_CREATE_ACCOUNT")).booleanValue();

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

        doViewMessaging(event);

        setNextState(req, DO_VIEW);
    }

    public void setLoginSettings(ActionFormEvent event) throws PortletException {
        PortletRequest req = event.getActionRequest();

        if (!req.isUserInRole(PortletRole.ADMIN.getName())) return;

        CheckBoxBean acctCB = event.getCheckBoxBean("acctCB");

        String useracct = acctCB.getSelectedValue();

        boolean canUserCreateAccount = (useracct != null);

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
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void configAccountSettings(ActionFormEvent event) throws PortletException {
        PortletRequest req = event.getActionRequest();

        if (!req.isUserInRole(PortletRole.ADMIN.getName())) return;

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
        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    public void configErrorSettings(ActionFormEvent event) throws PortletException {
        PortletRequest req = event.getActionRequest();
        if (!req.isUserInRole(PortletRole.ADMIN.getName())) return;

        RadioButtonBean errorRB = event.getRadioButtonBean("errorRB");

        Boolean sendMail = Boolean.FALSE;
        if (errorRB.getSelectedValue().equals("MAIL")) sendMail = Boolean.TRUE;
        portalConfigService.setProperty(LOGIN_ERROR_HANDLING, sendMail.toString());
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

        setNextState(req, DEFAULT_VIEW_PAGE);
    }

    private DefaultTableModel getMessagingService(RenderFormEvent event) {

        DefaultTableModel configTable = new DefaultTableModel();

        Set services = tms.getServices();

        // get the mail service
        TMService service = null;
        Iterator it = services.iterator();
        while (it.hasNext()) {
            service  = (TMService)it.next();
        }
        if (service == null) return configTable;

        // description

        TextMessageServiceConfig config = service.getServiceConfig();

        // configuration
        List params = config.getConfigPropertyKeys();

        for (int j=0;j<params.size();j++) {

            TableRowBean configRow = new TableRowBean();
            TableCellBean configDescription = new TableCellBean();
            TableCellBean configValue = new TableCellBean();

            TextBean paramName = event.getTextBean(service.getClass().getName()+"paramKey"+params.get(j));
            paramName.setValue((String)params.get(j));
            configDescription.addBean(paramName);

            TextFieldBean paramValue = event.getTextFieldBean(service.getClass().getName()+"paramValue"+params.get(j));
            paramValue.setValue(config.getProperty((String)params.get(j)));
            configValue.addBean(paramValue);

            configRow.addBean(configDescription); configDescription.setAlign("top");
            configRow.addBean(configValue);

            configTable.addTableRowBean(configRow);
        }

        FrameBean frameConfig = new FrameBean();
        frameConfig.setTableModel(configTable);


        return configTable;

    }

    public void doViewMessaging(RenderFormEvent event)  {
        FrameBean serviceFrame = event.getFrameBean("serviceframe");
        serviceFrame.setTableModel(getMessagingService(event));
        Set services = tms.getServices();
        event.getRenderRequest().setAttribute("services", ""+services.size());
    }

    public void doSaveValues(ActionFormEvent event) {
        Set services = tms.getServices();
        for (Iterator iterator = services.iterator(); iterator.hasNext();) {
            // get the service
            TMService service = (TMService) iterator.next();
                TextMessageServiceConfig config = service.getServiceConfig();

                List params = config.getConfigPropertyKeys();

                boolean isDirty = false;
                for (int j=0;j<params.size();j++) {
                    String propKey = (String)params.get(j);
                    String propValue = config.getProperty(propKey);

                    TextFieldBean paramValue = event.getTextFieldBean(service.getClass().getName()+"paramValue"+propKey);
                    String propTextBeanValue = paramValue.getValue();

                    // if a parameter changed, restart the service....
                    if (!propTextBeanValue.equals(propValue)) {
                        config.setProperty((String)params.get(j), paramValue.getValue());
                        isDirty = true;
                    }
                }
                if (isDirty) {
                    try {
                        config.saveConfig();
                        service.shutdown();
                        service.startup();
                        String msg = this.getLocalizedText(event.getActionRequest(), "MESSAGING_SERVICE_SERVICERESTARTED");
                        createSuccessMessage(event, msg + ": "+config.getProperty(TextMessagingService.SERVICE_NAME));
                    } catch (IOException e) {
                        createErrorMessage(event, this.getLocalizedText(event.getActionRequest(), "MESSAGING_SERVICE_SAVEFAILURE"));
                    } catch (TextMessagingException e) {
                        String msg = this.getLocalizedText(event.getActionRequest(), "MESSAGING_SERVICE_RESTARTFAILURE");
                        createErrorMessage(event, msg+" : "+config.getProperty(TextMessagingService.SERVICE_NAME));
                    }
                }
            }
        }


    private void createErrorMessage(ActionFormEvent evt, String text) {
        MessageBoxBean msg = evt.getMessageBoxBean("msg");
        msg.setValue(text);
        msg.setMessageType(MessageStyle.MSG_ERROR);
    }

    private void createSuccessMessage(ActionFormEvent evt, String text) {
        MessageBoxBean msg = evt.getMessageBoxBean("msg");
        msg.setValue(text);
        msg.setMessageType(MessageStyle.MSG_SUCCESS);
    }

}
