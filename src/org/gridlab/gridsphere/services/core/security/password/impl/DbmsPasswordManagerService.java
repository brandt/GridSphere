/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 11:51:26 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.password.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.services.core.security.password.*;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.impl.AccountRequestImpl;
import org.gridlab.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.core.security.password.Password;
import org.gridlab.gridsphere.services.core.security.password.PasswordBean;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import java.util.List;
import java.util.Date;
import java.util.Vector;

public class DbmsPasswordManagerService
    implements PortletServiceProvider, PasswordManagerService {

    private static PortletLog _log = SportletLog.getInstance(DbmsPasswordManagerService.class);
    private PersistenceManagerRdbms pm = PersistenceManagerRdbms.getInstance();
    private String userPasswordImpl = DbmsUserPassword.class.getName();
    private String requestPasswordImpl = DbmsRequestPassword.class.getName();
    private long defaultPasswordLifetime = -1;

    /****** PORTLET SERVICE METHODS *******/

    public void init(PortletServiceConfig config) {
        _log.info("Entering init()");
        initServices();
        _log.info("Exiting init()");
    }

    private void initServices() {
        // Get instance of service factory
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        // Instantiate helper services
    }

    public void destroy() {
    }

    /****** PASSWORD SERVICE METHODS *******/

    public List getPasswords() {
        List userPasswords = getUserPasswords();
        List requestPasswords = getAccountRequestPasswords();
        Vector allPasswords = new Vector();
        allPasswords.add(userPasswords);
        allPasswords.add(requestPasswords);
        return allPasswords;
    }

    public List getAccountRequestPasswords() {
        List passwords = null;
        String query = "select pw from "
                     + this.requestPasswordImpl
                     + " pw";
        try {
            passwords = this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Unable to retrieve passwords", e);
            passwords = new Vector();
        }
        return passwords;
    }

    public List getUserPasswords() {
        List passwords = null;
        String query = "select pw from "
                     + this.userPasswordImpl
                     + " pw";
        try {
            passwords = this.pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            _log.error("Unable to retrieve passwords", e);
            passwords = new Vector();
        }
        return passwords;
    }

    public Password getPassword(User user) {
        return getDbmsPassword(user);
    }

    /**
     * This method returns the <code>DbmsPassword</code> associated with
     * a user and is called internally by other methods in this class.
     */
    private DbmsPassword getDbmsPassword(User user) {
        if (user instanceof AccountRequest) {
            return getDbmsRequestPassword((AccountRequest)user);
        } else {
            return getDbmsUserPassword(user);
        }
    }

    /**
     * This method returns the <code>DbmsPassword</code> associated with
     * a user and is called internally by other methods in this class.
     */
    private DbmsPassword getDbmsUserPassword(User user) {
        DbmsPassword password = null;
        String query = "select pw from "
                     + this.userPasswordImpl
                     + " pw where pw.user=" + user.getID();
        try {
            password = (DbmsPassword)this.pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Unable to retrieve password for user", e);
        }
        return password;
    }

    /**
     * This method returns the <code>DbmsPassword</code> associated with
     * a user and is called internally by other methods in this class.
     */
    private DbmsRequestPassword getDbmsRequestPassword(AccountRequest request) {
        DbmsRequestPassword password = null;
        String query = "select pw from "
                     + this.requestPasswordImpl
                     + " pw where pw.user=" + request.getID();
        try {
            password = (DbmsRequestPassword)this.pm.restoreObject(query);
        } catch (PersistenceManagerException e) {
            _log.error("Unable to retrieve password for user", e);
        }
        return password;
    }

    public PasswordBean editPassword(User user) {
        PasswordBean editor = null;
        Password password = getDbmsUserPassword(user);
        if (password == null) {
            long lifetime = getDefaultPasswordLifetime();
            editor = new PasswordBean(user);
            editor.setLifetime(lifetime);
        } else {
            editor = new PasswordBean(password);
        }
        return editor;
    }

    public void savePassword(PasswordBean editor)
            throws InvalidPasswordException {
        // Get password attributes
        User user = editor.getUser();
        String hint = editor.getHint();
        String value = editor.getValue();
        Date dateExpires = editor.getDateExpires();
        // Create or update password
        Date now = new Date();
        // Check if user has password already
        DbmsPassword password = getDbmsPassword(user);
        if (password == null) {
            // Validate the value if requested
            if (editor.getValidation()) {
                validatePassword(value);
            }
            // Instantiate new password
            password = createDbmsPassword(user);
            password.setValue(value);
            password.setDateExpires(dateExpires);
            password.setDateCreated(now);
            password.setDateLastModified(now);
            // Create record of password
            try {
                this.pm.create(password);
            } catch (PersistenceManagerException e) {
                _log.error("Unable to create password", e);
            }
        } else {
            // Validate the value if requested
            if (editor.getValidation()) {
                String oldValue = password.getValue();
                validatePassword(oldValue, value);
            }
            // Update password
            password.setValue(value);
            password.setDateExpires(dateExpires);
            password.setDateLastModified(now);
            try {
                this.pm.update(password);
            } catch (PersistenceManagerException e) {
                _log.error("Unable to update password", e);
            }
        }
    }

    private DbmsPassword createDbmsPassword(User user) {
        DbmsPassword password = null;
        if (user instanceof AccountRequestImpl) {
            password = new DbmsRequestPassword();
        } else {
            password = new DbmsUserPassword();
        }
        password.setUser(user);
        return password;
    }

    public void deletePassword(User user) {
        Password password = getPassword(user);
        if (password != null) {
            deletePassword(password);
        }
    }

    private void deletePassword(Password password) {
        try {
            this.pm.delete(password);
        } catch (PersistenceManagerException e) {
            _log.error("Unable to delete password", e);
        }
    }

    public boolean hasPassword(User user) {
        Password password = getPassword(user);
        return (password != null);
    }

    public void activatePassword(AccountRequest request, User user)
            throws InvalidPasswordException {
        DbmsRequestPassword requestPassword = getDbmsRequestPassword(request);
        if (requestPassword == null) {
            String msg = "No password associated with given account request!";
            _log.debug(msg);
            throw new InvalidPasswordException(msg);
        }
        // Save user password
        PasswordBean passwordBean = new PasswordBean();
        passwordBean.setUser(user);
        passwordBean.setHint(passwordBean.getHint());
        passwordBean.setValue(requestPassword.getValue());
        passwordBean.setDateExpires(requestPassword.getDateExpires());
        passwordBean.setValidation(false);
        savePassword(passwordBean);
        // Delete request password
        deletePassword(requestPassword);
    }

    public void validatePassword(User user, String newValue)
            throws InvalidPasswordException {
        DbmsPassword password = getDbmsUserPassword(user);
        if (password == null) {
            validatePassword(newValue);
        } else {
            String oldValue = password.getValue();
            validatePassword(oldValue, newValue);
        }
    }

    private void validatePassword(String oldValue, String newValue)
            throws InvalidPasswordException {
        if (oldValue.equals(newValue)) {
            String msg = "New password must be different from the old value.";
            throw new InvalidPasswordException(msg);
        }
        validatePassword(newValue);
    }

    public void validatePassword(String password)
          throws InvalidPasswordException {
        if (password == null) {
            String msg = "Password is not set.";
            throw new InvalidPasswordException(msg);
        }
        password = password.trim();
        if (password.length() == 0) {
            String msg = "Password is blank.";
            throw new InvalidPasswordException(msg);
        }
        if (password.length() < 5) {
            String msg = "Password must be longer than 5 characters.";
            throw new InvalidPasswordException(msg);
        }
    }

    public boolean isPasswordCorrect(User user, String value) {
        DbmsPassword password = getDbmsUserPassword(user);
        if (password == null) {
            _log.debug("No password found for user");
            return false;
        }
        _log.debug("Stored value is " + password.getValue());
        _log.debug("Provided value is " + value);
        return password.equals(value);
    }

    public long getDefaultPasswordLifetime() {
        return this.defaultPasswordLifetime;
    }
}
