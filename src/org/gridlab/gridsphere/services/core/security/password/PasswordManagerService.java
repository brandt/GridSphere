/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 11:33:22 AM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.password;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.core.user.AccountRequest;

public interface PasswordManagerService {

    public Password getPassword(User user);

    public void validatePassword(String password)
          throws InvalidPasswordException;

    public void validatePassword(User user, String password)
          throws InvalidPasswordException;

    public void savePassword(Password passwordBean)
            throws InvalidPasswordException;

    public void deletePassword(User user);

    public void activatePassword(AccountRequest request, User user)
            throws InvalidPasswordException;

    public boolean hasPassword(User user);

    public boolean isPasswordCorrect(User user, String password);

    public long getDefaultPasswordLifetime();
}
