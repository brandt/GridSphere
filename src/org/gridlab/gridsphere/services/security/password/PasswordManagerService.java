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

    public Password createPassword(User user, String password)
            throws PasswordInvalidException;

    public void resetPassword(User user, String password)
            throws PasswordInvalidException, PasswordNotFoundException;

    public void updatePassword(User user, String password)
            throws PasswordInvalidException, PasswordNotFoundException;

    public void deletePassword(User user);

    public boolean hasPassword(User user);

    public Date getDatePasswordExpires(User user)
            throws PasswordNotFoundException;

    public void setDatePasswordExpires(User user, Date date)
            throws PasswordNotFoundException;

    public String getPasswordHint(User user)
            throws PasswordNotFoundException;

    public void setPasswordHint(User user, String hint)
            throws PasswordNotFoundException;

    public void validatePassword(String password)
          throws PasswordInvalidException;

    public boolean isPasswordCorrect(User user, String password);
}
