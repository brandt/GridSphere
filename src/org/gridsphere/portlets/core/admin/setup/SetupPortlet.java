/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: SetupPortlet.java 5079 2006-08-18 19:03:26Z novotny $
 */
package org.gridsphere.portlets.core.admin.setup;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.User;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.services.core.security.password.PasswordEditor;
import org.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridsphere.services.core.security.role.RoleManagerService;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.user.UserManagerService;
import org.gridsphere.layout.PortletPageFactory;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;


public class SetupPortlet extends ActionPortlet {

    // Portlet services
    private UserManagerService userManagerService = null;
    private PasswordManagerService passwordManagerService = null;
    private RoleManagerService roleManagerService = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        this.userManagerService = (UserManagerService) createPortletService(UserManagerService.class);
        this.roleManagerService = (RoleManagerService) createPortletService(RoleManagerService.class);
        this.passwordManagerService = (PasswordManagerService) createPortletService(PasswordManagerService.class);
        DEFAULT_HELP_PAGE = "admin/setup/help.jsp";
        DEFAULT_VIEW_PAGE = "admin/setup/doView.jsp";
    }

    private void validateUser(ActionFormEvent event, boolean newuser)
            throws PortletException {
        log.debug("Entering validateUser()");
        PortletRequest req = event.getActionRequest();

        boolean isInvalid = false;
        // Validate user name
        String userName = event.getTextFieldBean("userName").getValue();
        if (userName.equals("")) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_NAME_BLANK") + "<br />");
            isInvalid = true;
        } else if (newuser) {
            if (this.userManagerService.existsUserName(userName)) {
                createErrorMessage(event, this.getLocalizedText(req, "USER_EXISTS") + "<br />");
                isInvalid = true;
            }
        }

        // Validate first and last name
        String firstName = event.getTextFieldBean("firstName").getValue();

        if (firstName.equals("")) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_GIVENNAME_BLANK") + "<br />");
            isInvalid = true;
        }

        String lastName = event.getTextFieldBean("lastName").getValue();

        if (lastName.equals("")) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_FAMILYNAME_BLANK") + "<br />");
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

        // Throw exception if error was found
        if (isInvalid) {
            throw new PortletException();
        }
        log.debug("Exiting validateUser()");
    }

    public void doSavePortalAdmin(ActionFormEvent event) {
        try {
            validateUser(event, true);
        } catch (PortletException e) {
            return;
        }
        String password = event.getPasswordBean("password").getValue();
        boolean isbad = this.isInvalidPassword(event, true);
        if (isbad) {
            return;
        }

        User  accountRequest = this.userManagerService.createUser();
        accountRequest.setUserName(event.getTextFieldBean("userName").getValue());
        accountRequest.setFirstName(event.getTextFieldBean("firstName").getValue());
        accountRequest.setLastName(event.getTextFieldBean("lastName").getValue());
        accountRequest.setEmailAddress(event.getTextFieldBean("emailAddress").getValue());
        accountRequest.setOrganization(event.getTextFieldBean("organization").getValue());
        PasswordEditor editor = passwordManagerService.editPassword(accountRequest);
        editor.setValue(password);
        passwordManagerService.savePassword(editor);
        userManagerService.saveUser(accountRequest);

        roleManagerService.addUserToRole(accountRequest, PortletRole.ADMIN);
        roleManagerService.addUserToRole(accountRequest, PortletRole.USER);

        event.getActionRequest().setAttribute(SportletProperties.LAYOUT_PAGE, PortletPageFactory.USER_PAGE);
    }

    private boolean isInvalidPassword(ActionFormEvent event, boolean newuser) {
        // Validate password
        PortletRequest req = event.getActionRequest();
        String passwordValue = event.getPasswordBean("password").getValue();
        String confirmPasswordValue = event.getPasswordBean("confirmPassword").getValue();

        // If user already exists and password unchanged, no problem
        if (passwordValue.length() == 0 &&
                confirmPasswordValue.length() == 0) {
            if (newuser) {
                createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_BLANK") + "<br />");
                return true;
            }
            return false;
        }
        // Otherwise, password must match confirmation
        if (!passwordValue.equals(confirmPasswordValue)) {
            createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_MISMATCH") + "<br />");
            return true;
            // If they do match, then validate password with our service
        } else {
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

}
