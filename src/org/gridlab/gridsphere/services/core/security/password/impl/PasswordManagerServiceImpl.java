/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 11:51:26 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.password.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.core.security.password.Password;
import org.gridlab.gridsphere.services.core.security.password.PasswordEditor;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;

import java.util.Date;
import java.util.Calendar;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManagerServiceImpl
    implements PortletServiceProvider, PasswordManagerService {

    private static PasswordManagerService instance = new PasswordManagerServiceImpl();
    private static PortletLog _log = SportletLog.getInstance(PasswordManagerService.class);
    private PersistenceManagerRdbms pm = PersistenceManagerFactory.createGridSphereRdbms();
    private String userPasswordImpl = PasswordImpl.class.getName();

    private long defaultPasswordLifetime = -1;

    /****** PORTLET SERVICE METHODS *******/


    public static PasswordManagerService getInstance() {
        return instance;
    }

    public void init(PortletServiceConfig config) {

    }

    public void destroy() {
    }

    /****** PASSWORD SERVICE METHODS *******/




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
            password = (PasswordImpl)this.pm.restore(query);
        } catch (PersistenceManagerException e) {
            _log.error("Unable to retrieve password for user", e);
        }
        return password;
    }

   public void validateSuppliedPassword(User user, String value)
          throws InvalidPasswordException {
        PasswordImpl password = getPasswordImpl(user);
        if (password == null) {
            _log.debug("No password found for user");
            throw new InvalidPasswordException("No password found for user!");
        }
       //_log.debug("Stored value is " + password.getValue());
       //_log.debug("Provided value is " + value);

       // MD5 hash of password value
       try {
           MessageDigest md5 = MessageDigest.getInstance("MD5");
           md5.update(value.getBytes());
           value = toHex(md5.digest());

           //_log.debug("Hash of value is " + value);
           if (!password.getValue().equals(value)) {
               throw new InvalidPasswordException("Supplied password does not match user password!");
           }
       } catch (NoSuchAlgorithmException e) {
           //
       }
   }

    public void savePassword(Password editor) {
        try {
            if (editor instanceof PasswordImpl) {
                PasswordImpl pass = (PasswordImpl)editor;
                try {
                    MessageDigest md5 = MessageDigest.getInstance("MD5");
                    md5.update(pass.getValue().getBytes());
                    String value = toHex(md5.digest());
                    pass.setValue(value);
                } catch (NoSuchAlgorithmException e) {
                    throw new PersistenceManagerException("Can't get MD5 algorithm! " + e.getMessage());
                }
                if (pass.getOid() != null) {
                    pm.update(pass);
                } else {
                    pm.create(pass);
                }

            }
        } catch (PersistenceManagerException e) {
            _log.error("Unable to create or update password for user", e);
        }
    }
    /*
    public void savePassword(Password editor) {
        // Get password attributes
        User user = editor.getUser();
        String value = editor.getValue();
        Date dateExpires = editor.getDateExpires();
        // Create or update password
        Date now = new Date();
        // Check if user has password already
        PasswordImpl password = getPasswordImpl(user);
        //System.err.println("original passwd=" + value);
        if (password == null) {
            // Validate the value if requested
            if (editor.getValidation()) {

            }
            // Instantiate new password
            password = createPasswordImpl(user);

            // MD5 hash of password value
            if (user instanceof AccountRequest) {
                try {
                    MessageDigest md5 = MessageDigest.getInstance("MD5");
                    md5.update(value.getBytes());
                    value = toHex(md5.digest());
                } catch (NoSuchAlgorithmException e) {
                    throw new InvalidPasswordException("Can't get MD5 algorithm! " + e.getMessage());
                }
                //System.err.println("it's an account request password we save");
                //System.err.println("hashed passwd=" + value);
            }

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

            // MD5 hash of password value
            /*
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(value.getBytes());
                value = toHex(md5.digest());
            } catch (NoSuchAlgorithmException e) {
                throw new InvalidPasswordException("Can't get MD5 algorithm! " + e.getMessage());
            }
            System.err.println("hashed passwd=" + value);
            */

            // Update password
    /*
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
        */

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


         /*
    public void validatePassword(User user, String newValue)
            throws InvalidPasswordException {
        PasswordImpl password = getDbmsUserPassword(user);
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
    */
    /**
     * Return an 8 byte representation of the 32 byte MD5 digest
     *
     * @param digest the message digest
     * @return String 8 byte hexadecimal
     */
    private static String toHex(byte[] digest) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            buf.append(Integer.toHexString((int)digest[i] & 0x00FF));
        }
        return buf.toString();
    }

}
