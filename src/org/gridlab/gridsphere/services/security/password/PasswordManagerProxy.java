/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 19, 2003
 * Time: 4:44:12 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.password;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.security.AuthorizationUtility;

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

    public Password getPassword(User user) {
        authorizer.authorizeSuperOrSameUser(user);
        return this.passwordManager.getPassword(user);
    }

    public PasswordBean editPassword(User user) {
        authorizer.authorizeSuperUser();
        return this.passwordManager.editPassword(user);
    }

    public void savePassword(PasswordBean password)
            throws PasswordInvalidException {
        authorizer.authorizeSuperUser();
        this.passwordManager.savePassword(password);
    }

    public void savePassword(PasswordBean password, boolean validatePassword)
            throws PasswordInvalidException {
        authorizer.authorizeSuperUser();
        this.passwordManager.savePassword(password, validatePassword);
    }

    public void savePassword(User user, String value)
            throws PasswordInvalidException {
       authorizer.authorizeSuperOrSameUser(user);
       this.passwordManager.savePassword(user, value);
    }

    public void savePassword(User user, String value, boolean validatePassword)
            throws PasswordInvalidException {
        authorizer.authorizeSuperUser();
        this.passwordManager.savePassword(user, value, validatePassword);
    }

    public void deletePassword(User user) {
        authorizer.authorizeSuperUser();
        this.passwordManager.deletePassword(user);
    }

    public boolean hasPassword(User user) {
        authorizer.authorizeSuperUser();
        return this.passwordManager.hasPassword(user);
    }

    public void validatePassword(String password)
          throws PasswordInvalidException {
        this.passwordManager.validatePassword(password);
    }

    public void validatePassword(User user, String password)
          throws PasswordInvalidException {
        authorizer.authorizeSuperOrSameUser(user);
        this.passwordManager.validatePassword(user, password);
    }

    public boolean isPasswordCorrect(User user, String password) {
        authorizer.authorizeSuperOrSameUser(user);
        return this.passwordManager.isPasswordCorrect(user, password);
    }

    public long getDefaultPasswordLifetime() {
        return this.passwordManager.getDefaultPasswordLifetime();
    }

    /***
    public Password createPassword(User user, String password)
            throws PasswordInvalidException;

    public Password createPassword(User user, String password, boolean validatePasword)
            throws PasswordInvalidException;

    public void resetPassword(User user, String password)
            throws PasswordInvalidException, PasswordNotFoundException;

    public void updatePassword(User user, String password)
            throws PasswordInvalidException, PasswordNotFoundException;

    public Date getDatePasswordExpires(User user)
            throws PasswordNotFoundException;

    public void setDatePasswordExpires(User user, Date date)
            throws PasswordNotFoundException;

    public String getPasswordHint(User user)
            throws PasswordNotFoundException;

    public void setPasswordHint(User user, String hint)
            throws PasswordNotFoundException;

    ***/
}
