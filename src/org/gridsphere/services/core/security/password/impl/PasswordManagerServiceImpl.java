/*
 * @version: $Id$
 */
package org.gridsphere.services.core.security.password.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridsphere.services.core.security.password.Password;
import org.gridsphere.services.core.security.password.PasswordEditor;
import org.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridsphere.services.core.user.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class PasswordManagerServiceImpl implements PortletServiceProvider, PasswordManagerService {

    private Log log = LogFactory.getLog(PasswordManagerServiceImpl.class);
    private PersistenceManagerRdbms pm = null;
    private String userPasswordImpl = PasswordImpl.class.getName();

    public PasswordManagerServiceImpl() {
    }

    public void init(PortletServiceConfig config) {
        PersistenceManagerService pmservice = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pm = pmservice.createGridSphereRdbms();
    }

    public void destroy() {
    }

    public Password getPassword(User user) {
        return getPasswordImpl(user);
    }

    /**
     * This method returns the <code>PasswordImpl</code> associated with
     * a user and is called internally by other methods in this class.
     */
    private PasswordImpl getPasswordImpl(User user) {
        PasswordImpl password = null;
        String query = "select pw from "
                + this.userPasswordImpl
                + " pw where pw.sportletUser.oid='" + user.getID() + "'";
        try {
            password = (PasswordImpl) pm.restore(query);
        } catch (PersistenceManagerException e) {
            log.error("Unable to retrieve password for user", e);
        }
        return password;
    }

    public void validateSuppliedPassword(User user, String value)
            throws InvalidPasswordException {
        PasswordImpl password = getPasswordImpl(user);
        if (password == null) {
            log.debug("No password found for user");
            throw new InvalidPasswordException("No password found for user!");
        }
        //log.debug("Stored value is " + password.getValue());
        //log.debug("Provided value is " + value);

        // SHA-512 hash of password value
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            sha.update(value.getBytes());
            value = toHex(sha.digest());

            //log.debug("Hash of value is " + value);
            if (!password.getValue().equals(value)) {
                throw new InvalidPasswordException("Supplied password does not match user password!");
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("No such algorithm: SHA-512", e);
        }
    }

    public void savePassword(Password editor) {
        try {
            if (editor instanceof PasswordImpl) {
                PasswordImpl pass = (PasswordImpl) editor;
                try {
                    MessageDigest sha = MessageDigest.getInstance("SHA-512");
                    sha.update(pass.getValue().getBytes());
                    String value = toHex(sha.digest());
                    pass.setValue(value);
                } catch (NoSuchAlgorithmException e) {
                    throw new PersistenceManagerException("Can't get SHA-512 algorithm! " + e.getMessage());
                }
                pm.saveOrUpdate(pass);
            }
        } catch (PersistenceManagerException e) {
            log.error("Unable to create or update password for user", e);
        }
    }

    public String getHashedPassword(String pass) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            sha.update(pass.getBytes());
            return toHex(sha.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithm SHA-512!", e);
        }
        return null;
    }

    public void saveHashedPassword(Password editor) {
        try {
            if (editor instanceof PasswordImpl) {
                PasswordImpl pass = (PasswordImpl) editor;
                pm.saveOrUpdate(pass);
            }
        } catch (PersistenceManagerException e) {
            log.error("Unable to create or update password for user", e);
        }
    }

    public void deletePassword(User user) {
        Password password = getPassword(user);
        if (password != null) {
            deletePassword(password);
        }
    }

    private void deletePassword(Password password) {
        try {
            pm.delete(password);
        } catch (PersistenceManagerException e) {
            log.error("Unable to delete password", e);
        }
    }

    public boolean hasPassword(User user) {
        Password password = getPassword(user);
        return (password != null);
    }

    public PasswordEditor editPassword(User user) {
        PasswordImpl password = this.getPasswordImpl(user);
        if (password == null) {
            password = new PasswordImpl();
            password.setUser(user);
            password.setDateCreated(Calendar.getInstance().getTime());
            this.savePassword(password);
        }
        return password;
    }

    /**
     * Return an 8 byte representation of the 32 byte SHA-512 digest
     *
     * @param digest the message digest
     * @return String 8 byte hexadecimal
     */
    private static String toHex(byte[] digest) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            buf.append(Integer.toHexString((int) digest[i] & 0x00FF));
        }
        return buf.toString();
    }

}
