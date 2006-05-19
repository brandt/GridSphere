/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.security.password;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;

public interface PasswordManagerService extends PortletService {

    public Password getPassword(User user);

    public String getHashedPassword(String pass);

    public void validateSuppliedPassword(User user, String suppliedPassword)
            throws InvalidPasswordException;

    public void savePassword(Password password);

    public void saveHashedPassword(Password password);
    
    public void deletePassword(User user);

    public PasswordEditor editPassword(User user);

}
