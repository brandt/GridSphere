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

public interface PasswordManagerService {

    public Password getPassword(User user);

    public void validateSuppliedPassword(User user, String suppliedPassword)
          throws InvalidPasswordException;

    public void savePassword(Password passwordBean);

    public void deletePassword(User user);

    public PasswordEditor editPassword(User user);

    public boolean hasPassword(User user);

}
