/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 11:33:22 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.password;

import org.gridlab.gridsphere.portlet.User;

import java.util.List;
import java.util.Date;

public interface PasswordManagerService {

    public List getPasswords();

    public Password getPassword(User user);

    public PasswordBean editPassword(User user);

    public void savePassword(PasswordBean editor)
            throws PasswordInvalidException;

    public void savePassword(PasswordBean editor, boolean validatePassword)
            throws PasswordInvalidException;

    public void savePassword(User user, String value)
            throws PasswordInvalidException;

    public void savePassword(User user, String value, boolean validatePassword)
            throws PasswordInvalidException;

    public void deletePassword(User user);

    public boolean hasPassword(User user);

    public void validatePassword(String password)
          throws PasswordInvalidException;

    public void validatePassword(User user, String password)
          throws PasswordInvalidException;

    public boolean isPasswordCorrect(User user, String password);

    public long getDefaultPasswordLifetime();

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
