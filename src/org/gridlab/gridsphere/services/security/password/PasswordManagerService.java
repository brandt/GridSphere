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
import org.gridlab.gridsphere.services.user.AccountRequest;

import java.util.List;
import java.util.Date;

public interface PasswordManagerService {

    public List getPasswords();

    public List getAccountRequestPasswords();

    public List getUserPasswords();

    public Password getPassword(User user);

    public PasswordBean editPassword(User user);

    public void validatePassword(String password)
          throws InvalidPasswordException;

    public void validatePassword(User user, String password)
          throws InvalidPasswordException;

    public void savePassword(PasswordBean passwordBean)
            throws InvalidPasswordException;

    public void deletePassword(User user);

    public void activatePassword(AccountRequest request, User user)
            throws InvalidPasswordException;

    public boolean hasPassword(User user);

    public boolean isPasswordCorrect(User user, String password);

    public long getDefaultPasswordLifetime();
}
