/*
 * @version: $Id: PasswordManagerService.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.security.password;

import org.gridsphere.services.core.user.User;
import org.gridsphere.portlet.service.PortletService;

public interface PasswordManagerService extends PortletService {

    public Password getPassword(User user);

    public String getHashedPassword(String pass);

    public void validateSuppliedPassword(User user, String suppliedPassword)
            throws InvalidPasswordException;

    public void savePassword(Password passwordBean);

    public void saveHashedPassword(Password editor);
    
    public void deletePassword(User user);

    public PasswordEditor editPassword(User user);

    public boolean hasPassword(User user);

}
