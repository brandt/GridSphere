/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: LoginPortlet.java 5087 2006-08-18 22:52:23Z novotny $
 */
package org.gridsphere.portlets.core.login;

import org.gridsphere.portlet.*;
import org.gridsphere.provider.event.FormEvent;
import org.gridsphere.provider.portlet.ActionPortlet;
import org.gridsphere.provider.portletui.beans.*;
import org.gridsphere.services.core.messaging.TextMessagingService;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.gridsphere.services.core.request.GenericRequest;
import org.gridsphere.services.core.request.RequestService;
import org.gridsphere.services.core.security.password.PasswordEditor;
import org.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridsphere.services.core.security.role.RoleManagerService;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.user.UserManagerService;
import org.gridsphere.services.core.captcha.impl.CaptchaServiceSingleton;
import org.gridsphere.tmf.TextMessagingException;
import org.gridsphere.tmf.message.MailMessage;

import javax.servlet.UnavailableException;
import java.security.cert.X509Certificate;
import java.util.*;

import com.octo.captcha.service.CaptchaServiceException;

public class LoginPortlet extends ActionPortlet {

    private static String FORGOT_PASSWORD_LABEL = "forgotpassword";
    private static String ACTIVATE_ACCOUNT_LABEL = "activateaccount";
    private static String LOGIN_NUMTRIES = "ACCOUNT_NUMTRIES";
    private static String ADMIN_ACCOUNT_APPROVAL = "ADMIN_ACCOUNT_APPROVAL";
    private static String LOGIN_NAME = "LOGIN_NAME";
    public static String SAVE_PASSWORDS = "SAVE_PASSWORDS";
    public static String REMEMBER_USER = "REMEMBER_USER";
    public static String SUPPORT_X509_AUTH = "SUPPORT_X509_AUTH";
    public static String SEND_USER_FORGET_PASSWORD = "SEND_USER_FORGET_PASSWD";

    private static long REQUEST_LIFETIME = 1000 * 60 * 24 * 3; // 3 days

    public static final String LOGIN_ERROR_FLAG = "LOGIN_FAILED";
    public static final Integer LOGIN_ERROR_UNKNOWN = new Integer(-1);

    public static final String DO_VIEW_USER_EDIT_LOGIN = "login/createaccount.jsp"; //edit user
    public static final String DO_FORGOT_PASSWORD = "login/forgotpassword.jsp";
    public static final String DO_NEW_PASSWORD = "login/newpassword.jsp";

    private UserManagerService userManagerService = null;
    private RoleManagerService roleService = null;
    private PasswordManagerService passwordManagerService = null;
    private PortalConfigService portalConfigService = null;
    private RequestService requestService = null;
    private TextMessagingService tms = null;

    public void init(PortletConfig config) throws UnavailableException {

        super.init(config);

        userManagerService = (UserManagerService) getPortletConfig().getContext().getService(UserManagerService.class);
        roleService = (RoleManagerService) getPortletConfig().getContext().getService(RoleManagerService.class);
        passwordManagerService = (PasswordManagerService) getPortletConfig().getContext().getService(PasswordManagerService.class);
        requestService = (RequestService) getPortletConfig().getContext().getService(RequestService.class);
        tms = (TextMessagingService) getPortletConfig().getContext().getService(TextMessagingService.class);
        portalConfigService = (PortalConfigService) getPortletConfig().getContext().getService(PortalConfigService.class);
        DEFAULT_VIEW_PAGE = "doViewUser";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doViewUser(FormEvent event) throws PortletException {
        log.debug("in LoginPortlet: doViewUser");
        PortletRequest request = event.getPortletRequest();
        User user = request.getUser();
        PasswordBean pass = event.getPasswordBean("password");
        pass.setValue("");
        request.setAttribute("user", user);

        // Check certificates
        String x509supported = portalConfigService.getProperty("SUPPORT_X509_AUTH");
        if ((x509supported != null) && (x509supported.equalsIgnoreCase("true"))) {
            X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
            if (certs != null && certs.length > 0) {
                request.setAttribute("certificate", certs[0].getSubjectDN().toString());
            }
        }

        String remUser = portalConfigService.getProperty(REMEMBER_USER);
        if ((remUser != null) && (remUser.equalsIgnoreCase("TRUE"))) {
            request.setAttribute("remUser", "true");
        }

        Boolean useSecureLogin = Boolean.valueOf(portalConfigService.getProperty("use.https.login"));

        request.setAttribute("useSecureLogin", useSecureLogin.toString());
        boolean canUserCreateAccount = Boolean.valueOf(portalConfigService.getProperty("CAN_USER_CREATE_ACCOUNT")).booleanValue();
        if (canUserCreateAccount) request.setAttribute("canUserCreateAcct", "true");
        boolean dispUser = Boolean.valueOf(portalConfigService.getProperty(SEND_USER_FORGET_PASSWORD)).booleanValue();
        if (dispUser) request.setAttribute("dispPass", "true");
        setNextState(request, "login/login.jsp");

    }

    public void gs_login(FormEvent event) throws PortletException {
        log.debug("in LoginPortlet: gs_login");
        PortletRequest req = event.getPortletRequest();

        String errorMsg = (String) req.getAttribute(LoginPortlet.LOGIN_ERROR_FLAG);

        if (errorMsg != null) {
            Integer numTries = (Integer) req.getSession(true).getAttribute(LoginPortlet.LOGIN_NUMTRIES);
            String loginname = (String) req.getSession(true).getAttribute(LoginPortlet.LOGIN_NAME);
            int i = 1;
            if (numTries != null) {
                i = numTries.intValue();
                i++;
            }
            numTries = new Integer(i);
            req.getSession(true).setAttribute(LoginPortlet.LOGIN_NUMTRIES, numTries);
            req.getSession(true).setAttribute(LoginPortlet.LOGIN_NAME, req.getParameter("username"));
            System.err.println("num tries = " + i);
            // tried one to many times using same name

            int defaultNumTries = Integer.valueOf(portalConfigService.getProperty(LOGIN_NUMTRIES)).intValue();

            if (req.getParameter("username") != null && req.getParameter("username").equals(loginname)) {
                if ((i >= defaultNumTries) && (defaultNumTries != -1)) {
                    disableAccount(event);
                    errorMsg = this.getLocalizedText(req, "LOGIN_TOOMANY_ATTEMPTS");
                    req.getSession(true).removeAttribute(LoginPortlet.LOGIN_NUMTRIES);
                    req.getSession(true).removeAttribute(LoginPortlet.LOGIN_NAME);
                }
            }
            createErrorMessage(event, errorMsg);
            req.removeAttribute(LoginPortlet.LOGIN_ERROR_FLAG);
        }

        setNextState(req, "doViewUser");
    }

    public void disableAccount(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        String loginName = req.getParameter("username");
        User user = userManagerService.getUserByUserName(loginName);
        if (user != null) {
            user.setAttribute(User.DISABLED, "true");
            userManagerService.saveUser(user);

            MailMessage mailToUser = tms.getMailMessage();
            StringBuffer body = new StringBuffer();
            body.append(getLocalizedText(req, "LOGIN_DISABLED_MSG1") + " " + getLocalizedText(req, "LOGIN_DISABLED_MSG2") + "\n\n");
            mailToUser.setBody(body.toString());
            mailToUser.setSubject(getLocalizedText(req, "LOGIN_DISABLED_SUBJECT"));
            mailToUser.setTo(user.getEmailAddress());
            mailToUser.setServiceid("mail");

            MailMessage mailToAdmin = tms.getMailMessage();
            StringBuffer body2 = new StringBuffer();
            body2.append(getLocalizedText(req, "LOGIN_DISABLED_ADMIN_MSG") + " " + user.getUserName());
            mailToAdmin.setBody(body2.toString());
            mailToAdmin.setSubject(getLocalizedText(req, "LOGIN_DISABLED_SUBJECT") + " " + user.getUserName());
            mailToAdmin.setTo(tms.getServiceUserID("mail", "root"));
            mailToUser.setServiceid("mail");


            try {
                tms.send(mailToUser);
                tms.send(mailToAdmin);
            } catch (TextMessagingException e) {
                log.error("Unable to send mail message!", e);
                createErrorMessage(event, this.getLocalizedText(req, "LOGIN_FAILURE_MAIL"));
                return;
            }
        }
    }

    public void doNewUser(FormEvent evt)
            throws PortletException {

        boolean canUserCreateAccount = Boolean.valueOf(portalConfigService.getProperty("CAN_USER_CREATE_ACCOUNT")).booleanValue();
        if (!canUserCreateAccount) return;

        PortletRequest req = evt.getPortletRequest();

        MessageBoxBean msg = evt.getMessageBoxBean("msg");

        String savePasswds = portalConfigService.getProperty("SAVE_PASSWORDS");
        if (savePasswds.equals(Boolean.TRUE.toString())) {
            req.setAttribute("savePass", "true");
        }
        String adminApproval = portalConfigService.getProperty("ADMIN_ACCOUNT_APPROVAL");
        if (adminApproval.equals(Boolean.TRUE.toString())) {
            msg.setKey("LOGIN_ACCOUNT_CREATE_APPROVAL");
        } else {
            msg.setKey("LOGIN_CREATE_ACCT");
        }

        setNextState(req, DO_VIEW_USER_EDIT_LOGIN);
        log.debug("in doViewNewUser");
    }

    public void doConfirmEditUser(FormEvent evt)
            throws PortletException {
        PortletRequest req = evt.getPortletRequest();

        String savePasswds = portalConfigService.getProperty("SAVE_PASSWORDS");
        if (savePasswds.equals(Boolean.TRUE.toString())) {
            req.setAttribute("savePass", "true");
        }

        boolean canUserCreateAccount = Boolean.valueOf(portalConfigService.getProperty("CAN_USER_CREATE_ACCOUNT")).booleanValue();
        if (!canUserCreateAccount) return;

        try {
            //check if the user is new or not
            validateUser(evt);
            //new and valid user and will save it
            notifyNewUser(evt);

            setNextState(req, "doViewUser");
        } catch (PortletException e) {
            //invalid user, an exception was thrown
            //back to edit
            setNextState(req, DO_VIEW_USER_EDIT_LOGIN);
        }
    }

    private void validateUser(FormEvent event)
            throws PortletException {
        log.debug("Entering validateUser()");
        PortletRequest req = event.getPortletRequest();
        StringBuffer message = new StringBuffer();
        boolean isInvalid = false;
        // Validate user name
        String userName = event.getTextFieldBean("userName").getValue();
        if (userName.equals("")) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_NAME_BLANK") + "<br />");
            isInvalid = true;
        }

        if (this.userManagerService.existsUserName(userName)) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_EXISTS") + "<br />");
            isInvalid = true;
        }

        // Validate full name

        String familyName = event.getTextFieldBean("fullName").getValue();
        if (familyName.equals("")) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_FULLNAME_BLANK") + "<br />");
            isInvalid = true;
        }

        // Validate e-mail
        String eMail = event.getTextFieldBean("emailAddress").getValue();
        if (eMail.equals("")) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_NEED_EMAIL") + "<br />");
            isInvalid = true;
        } else if ((eMail.indexOf("@") < 0)) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_NEED_EMAIL") + "<br />");
            isInvalid = true;
        } else if ((eMail.indexOf(".") < 0)) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_NEED_EMAIL") + "<br />");
            isInvalid = true;
        }

        //Validate password

        String savePasswds = portalConfigService.getProperty(SAVE_PASSWORDS);
        if (savePasswds.equals(Boolean.TRUE.toString())) {
            if (!isInvalid) {
                isInvalid = isInvalidPassword(event);
            }
        }


        //remenber that we need an id to validate!
        String captchaId = req.getSession().getId();
        //retrieve the response
        String response = event.getTextFieldBean("captchaTF").getValue();
        // Call the Service method
        try {
            isInvalid = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId,
                    response);
        } catch (CaptchaServiceException e) {
            //should not happen, may be thrown if the id is not valid
        }
        if (!isInvalid) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_CAPTCHA_MISMATCH"));
        }


        // Throw exception if error was found
        if (isInvalid) {
            throw new PortletException(message.toString());
        }
        log.debug("Exiting validateUser()");
    }

    private boolean isInvalidPassword(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        // Validate password
        String passwordValue = event.getPasswordBean("password").getValue();
        String confirmPasswordValue = event.getPasswordBean("confirmPassword").getValue();

        if (passwordValue == null) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_NOTSET"));
            return true;
        }

        // Otherwise, password must match confirmation
        if (!passwordValue.equals(confirmPasswordValue)) {
            createErrorMessage(event, (this.getLocalizedText(req, "USER_PASSWORD_MISMATCH")) + "<br />");
            return true;
            // If they do match, then validate password with our service
        } else {

            passwordValue = passwordValue.trim();
            if (passwordValue.length() == 0) {
                createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_BLANK"));
                return true;
            }
            if (passwordValue.length() < 5) {
                createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_TOOSHORT"));
                return true;
            }
        }
        return false;
    }

    private User saveUser(GenericRequest request) {
        log.debug("Entering saveUser()");
        // Account request

        // Create edit account request

        User newuser = this.userManagerService.createUser();

        // Edit account attributes
        newuser.setUserName(request.getAttribute("userName"));
        newuser.setFullName(request.getAttribute("fullName"));
        newuser.setEmailAddress(request.getAttribute("emailAddress"));
        newuser.setOrganization(request.getAttribute("organization"));

        // Submit changes
        this.userManagerService.saveUser(newuser);

        String savePasswds = portalConfigService.getProperty("SAVE_PASSWORDS");
        if (savePasswds.equals(Boolean.TRUE.toString())) {
            PasswordEditor editor = passwordManagerService.editPassword(newuser);
            String password = request.getAttribute("password");

            editor.setValue(password);
            passwordManagerService.saveHashedPassword(editor);
        }

        // Save user role
        saveUserRole(newuser);
        log.debug("Exiting saveUser()");
        return newuser;
    }

    private void saveUserRole(User user) {
        log.debug("Entering saveUserRole()");
        roleService.addUserToRole(user, PortletRole.USER);
    }




    public void displayForgotPassword(FormEvent event) {
        boolean sendMail = Boolean.valueOf(portalConfigService.getProperty(SEND_USER_FORGET_PASSWORD)).booleanValue();
        if (sendMail) {
            PortletRequest req = event.getPortletRequest();
            setNextState(req, DO_FORGOT_PASSWORD);
        }
    }

    public void notifyUser(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        PortletResponse res = evt.getPortletResponse();

        User user;
        TextFieldBean emailTF = evt.getTextFieldBean("emailTF");

        if (emailTF.getValue().equals("")) {
            createErrorMessage(evt, this.getLocalizedText(req, "LOGIN_NO_EMAIL"));
            return;
        } else {
            user = userManagerService.getUserByEmail(emailTF.getValue());
        }
        if (user == null) {
            createErrorMessage(evt, this.getLocalizedText(req, "LOGIN_NOEXIST"));
            return;
        }

        // create a request
        GenericRequest request = requestService.createRequest(FORGOT_PASSWORD_LABEL);
        long now = Calendar.getInstance().getTime().getTime();

        request.setLifetime(new Date(now + REQUEST_LIFETIME));
        request.setUserID(user.getID());
        requestService.saveRequest(request);

        org.gridsphere.tmf.message.MailMessage mailToUser = tms.getMailMessage();
        mailToUser.setTo(emailTF.getValue());
        mailToUser.setSubject(getLocalizedText(req, "MAIL_SUBJECT_HEADER"));
        StringBuffer body = new StringBuffer();

        body.append(getLocalizedText(req, "LOGIN_FORGOT_MAIL") + "\n\n");

        PortletURI uri = res.createURI();

        uri.addAction("newpassword");
        uri.addParameter("reqid", request.getOid());

        body.append(uri.toString());
        mailToUser.setBody(body.toString());
        mailToUser.setServiceid("mail");

        try {
            tms.send(mailToUser);
        } catch (TextMessagingException e) {
            log.error("Unable to send mail message!", e);
            createErrorMessage(evt, this.getLocalizedText(req, "LOGIN_FAILURE_MAIL"));
            return;
        }
        createSuccessMessage(evt, this.getLocalizedText(req, "LOGIN_SUCCESS_MAIL"));

    }

    public void notifyNewUser(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        PortletResponse res = evt.getPortletResponse();


        TextFieldBean emailTF = evt.getTextFieldBean("emailAddress");

        if (emailTF.getValue().equals("")) {
            createErrorMessage(evt, this.getLocalizedText(req, "LOGIN_NO_EMAIL"));
            return;
        }

        // create a request
        GenericRequest request = requestService.createRequest(ACTIVATE_ACCOUNT_LABEL);
        long now = Calendar.getInstance().getTime().getTime();

        request.setLifetime(new Date(now + REQUEST_LIFETIME));

        // request.setUserID(user.getID());

        request.setAttribute("userName", evt.getTextFieldBean("userName").getValue());
        request.setAttribute("fullName", evt.getTextFieldBean("fullName").getValue());
        request.setAttribute("emailAddress", evt.getTextFieldBean("emailAddress").getValue());
        request.setAttribute("organization", evt.getTextFieldBean("organization").getValue());

        // put hashed pass in request
        String savePasswds = portalConfigService.getProperty("SAVE_PASSWORDS");
        if (savePasswds.equals(Boolean.TRUE.toString())) {
            String pass = evt.getPasswordBean("password").getValue();
            pass = passwordManagerService.getHashedPassword(pass);
            request.setAttribute("password", pass);
        }

        requestService.saveRequest(request);

        MailMessage mailToUser = tms.getMailMessage();
        StringBuffer body = new StringBuffer();

        PortletURI activateAccountUri = res.createURI();
        activateAccountUri.addAction("approveAccount");
        activateAccountUri.addParameter("reqid", request.getOid());

        PortletURI denyAccountUri = res.createURI();
        denyAccountUri.addAction("denyAccount");
        denyAccountUri.addParameter("reqid", request.getOid());

        // check if this account request should be approved by an administrator
        boolean accountApproval = Boolean.valueOf(portalConfigService.getProperty(ADMIN_ACCOUNT_APPROVAL)).booleanValue();
        if (accountApproval) {
            List usersToBeNotified = roleService.getUsersInRole(PortletRole.ADMIN);
            Set admins = new HashSet();
            for (int i = 0; i < usersToBeNotified.size(); i++) {
                User u = (User) usersToBeNotified.get(i);
                admins.add(u.getEmailAddress());
            }
            mailToUser.setTo(admins);
            body.append(getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ADMIN_MAIL")).append("\n\n");
            mailToUser.setSubject(getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ADMIN_MAILSUBJECT"));
            body.append(getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ALLOW")).append("\n\n");
            body.append(activateAccountUri.toString()).append("\n\n");
            body.append(getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_DENY")).append("\n\n");
            body.append(denyAccountUri.toString()).append("\n\n");
        } else {
            mailToUser.setTo(emailTF.getValue());
            mailToUser.setSubject(getLocalizedText(req, "MAIL_SUBJECT_HEADER"));
            body.append(getLocalizedText(req, "LOGIN_ACTIVATE_MAIL")).append("\n\n");
            body.append(activateAccountUri.toString()).append("\n\n");
        }

        body.append(getLocalizedText(req, "USERNAME"));
        body.append(evt.getTextFieldBean("userName").getValue()).append("\n");
        body.append(getLocalizedText(req, "FULLNAME"));
        body.append(evt.getTextFieldBean("fullName").getValue()).append("\n");
        body.append(getLocalizedText(req, "EMAILADDRESS"));
        body.append(evt.getTextFieldBean("emailAddress").getValue()).append("\n\n");

        mailToUser.setBody(body.toString());
        mailToUser.setServiceid("mail");

        try {
            tms.send(mailToUser);
        } catch (TextMessagingException e) {
            log.error("Unable to send mail message!", e);
            createErrorMessage(evt, this.getLocalizedText(req, "LOGIN_FAILURE_MAIL"));
            return;
        }

        createSuccessMessage(evt, this.getLocalizedText(req, "LOGIN_ACCT_MAIL"));

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

    public void newpassword(FormEvent evt) {
        PortletRequest req = evt.getPortletRequest();
        String id = req.getParameter("reqid");
        GenericRequest request = requestService.getRequest(id, FORGOT_PASSWORD_LABEL);
        if (request != null) {
            HiddenFieldBean reqid = evt.getHiddenFieldBean("reqid");
            reqid.setValue(id);
            setNextState(req, DO_NEW_PASSWORD);
        } else {
            setNextState(req, DEFAULT_VIEW_PAGE);
        }
    }

    private void doEmailAction(FormEvent event, String msg, boolean createAccount) {
        PortletRequest req = event.getPortletRequest();
        String id = req.getParameter("reqid");
        User user = null;
        GenericRequest request = requestService.getRequest(id, ACTIVATE_ACCOUNT_LABEL);
        if (request != null) {
            requestService.deleteRequest(request);
            if (createAccount) {
                user = saveUser(request);
                createSuccessMessage(event, msg + " " + user.getUserName());

                // send the user an email

                PortletResponse res = event.getPortletResponse();
                PortletURI portalURI = res.createURI();

                MailMessage mailToUser = tms.getMailMessage();
                mailToUser.setTo(user.getEmailAddress());
                mailToUser.setSubject(getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ACCOUNT_CREATED"));
                StringBuffer body = new StringBuffer();
                body.append(getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ACCOUNT_CREATED")).append("\n\n");
                body.append(portalURI.toString());
                mailToUser.setBody(body.toString());
                mailToUser.setServiceid("mail");

                try {
                    tms.send(mailToUser);
                } catch (TextMessagingException e) {
                    log.error("Error: " + e.getMessage());
                    createErrorMessage(event, this.getLocalizedText(req, "LOGIN_FAILURE_MAIL"));
                }
            } else {
                createSuccessMessage(event, msg);
            }
        }
        setNextState(req, "doViewUser");

    }

    public void activate(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        String msg = this.getLocalizedText(req, "USER_NEW_ACCOUNT") +
                "<br>" + this.getLocalizedText(req, "USER_PLEASE_LOGIN");
        doEmailAction(event, msg, true);
    }

    public void approveAccount(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        String msg = this.getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ACCOUNT_CREATED");
        doEmailAction(event, msg, true);
    }

    public void denyAccount(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        String msg = this.getLocalizedText(req, "LOGIN_ACCOUNT_APPROVAL_ACCOUNT_DENY");
        doEmailAction(event, msg, false);
    }


    public void doSavePass(FormEvent event) {

        PortletRequest req = event.getPortletRequest();

        HiddenFieldBean reqid = event.getHiddenFieldBean("reqid");
        String id = reqid.getValue();
        GenericRequest request = requestService.getRequest(id, FORGOT_PASSWORD_LABEL);
        if (request != null) {
            String uid = request.getUserID();
            User user = userManagerService.getUser(uid);

            passwordManagerService.editPassword(user);

            String passwordValue = event.getPasswordBean("password").getValue();
            String confirmPasswordValue = event.getPasswordBean("confirmPassword").getValue();

            if (passwordValue == null) {
                createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_NOTSET"));
                setNextState(req, DO_NEW_PASSWORD);
                return;
            }

            // Otherwise, password must match confirmation
            if (!passwordValue.equals(confirmPasswordValue)) {
                createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_MISMATCH"));
                setNextState(req, DO_NEW_PASSWORD);
                // If they do match, then validate password with our service
            } else {
                if (passwordValue.length() == 0) {
                    createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_BLANK"));
                    setNextState(req, DO_NEW_PASSWORD);
                } else if (passwordValue.length() < 5) {
                    System.err.println("length < 5 password= " + passwordValue);
                    createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_TOOSHORT"));
                    setNextState(req, DO_NEW_PASSWORD);
                } else {
                    // save password
                    //System.err.println("saving password= " + passwordValue);
                    PasswordEditor editPasswd = passwordManagerService.editPassword(user);
                    editPasswd.setValue(passwordValue);
                    editPasswd.setDateLastModified(Calendar.getInstance().getTime());
                    passwordManagerService.savePassword(editPasswd);
                    createSuccessMessage(event, this.getLocalizedText(req, "USER_PASSWORD_SUCCESS"));
                    requestService.deleteRequest(request);
                }
            }
        }
    }
}
