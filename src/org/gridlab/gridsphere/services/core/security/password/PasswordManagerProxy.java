/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 19, 2003
 * Time: 4:44:12 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.password;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.security.AuthorizationUtility;
import org.gridlab.gridsphere.services.core.user.AccountRequest;

import java.util.Date;
import java.util.List;

public class PasswordManagerProxy
    implements PortletServiceProvider, PasswordManagerService {

    private static PortletLog _log = SportletLog.getInstance(PasswordManagerProxy.class);
    private AuthorizationUtility authorizer = null;
    private PasswordManagerService passwordManager = null;

    private PasswordManagerProxy() {
    }

    public PasswordManagerProxy(PortletServiceProvider psp, User user) throws Exception {
        // Check psp implements password manager service
        if (psp instanceof PasswordManagerService) {
            this.passwordManager = (PasswordManagerService) psp;
        } else {
            throw new Exception("Unable to create PasswordManagerProxy");
        }
        // Get authorizer
        this.authorizer = new AuthorizationUtility(user, PasswordManagerService.class);
    }

    /****** PORTLET SERVICE PROVIDER METHODS *******/

    public void init(PortletServiceConfig config) {
    }

    public void destroy() {
    }

    public List getPasswords() {
        authorizer.authorizeSuperUser();
        return this.passwordManager.getPasswords();
    }

    public List getAccountRequestPasswords() {
        authorizer.authorizeSuperUser();
        return this.passwordManager.getPasswords();
    }

    public List getUserPasswords() {
        authorizer.authorizeSuperUser();
        return this.passwordManager.getUserPasswords();
    }

    public Password getPassword(User user) {
        authorizer.authorizeSuperOrSameUser(user);
        return this.passwordManager.getPassword(user);
    }

    public PasswordEditor editPassword(User user) {
        authorizer.authorizeSuperUser();
        return this.passwordManager.editPassword(user);
    }

    public void validatePassword(String password)
          throws InvalidPasswordException {
        this.passwordManager.validatePassword(password);
    }

    public void validatePassword(User user, String password)
          throws InvalidPasswordException {
        authorizer.authorizeSuperOrSameUser(user);
        this.passwordManager.validatePassword(user, password);
    }

    public void savePassword(PasswordEditor password)
            throws InvalidPasswordException {
        // Super or same user can call
        User user = password.getUser();
        authorizer.authorizeSuperOrSameUser(user);
        // But only super can request no validation
        if (!password.getValidation()) {
            authorizer.authorizeSuperUser();
        }
        this.passwordManager.savePassword(password);
    }

    public void deletePassword(User user) {
        authorizer.authorizeSuperUser();
        this.passwordManager.deletePassword(user);
    }

    public boolean hasPassword(User user) {
        authorizer.authorizeSuperUser();
        return this.passwordManager.hasPassword(user);
    }

    public void activatePassword(AccountRequest request, User user)
          throws InvalidPasswordException {
        authorizer.authorizeSuperUser();
        this.passwordManager.activatePassword(request, user);
    }

    public boolean isPasswordCorrect(User user, String password) {
        authorizer.authorizeSuperOrSameUser(user);
        return this.passwordManager.isPasswordCorrect(user, password);
    }

    public long getDefaultPasswordLifetime() {
        return this.passwordManager.getDefaultPasswordLifetime();
    }
}
