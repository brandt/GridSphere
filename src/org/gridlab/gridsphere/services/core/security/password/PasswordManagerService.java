/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.security.password;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;

public interface PasswordManagerService extends PortletService {

    public Password getPassword(User user);

    public void validateSuppliedPassword(User user, String suppliedPassword)
          throws InvalidPasswordException;

    public void savePassword(Password passwordBean);

    public void deletePassword(User user);

    public PasswordEditor editPassword(User user);

    public boolean hasPassword(User user);

}
