/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.LoginAuthModule;

import java.util.List;
import java.util.Map;

public interface LoginService extends PortletService {

    public Map getSupportedAuthModules();

    public void setActiveAuthModule(LoginAuthModule loginModule);

    /**
     * Login a user with the given login name and password.
     * Returns the associated user if login succeeds.
     * Throws an AuthenticationException if login fails.
     *
     * @param loginName the login name
     * @param loginPassword the login password.
     * @return User The associated user.
     * @throws org.gridlab.gridsphere.services.core.security.auth.AuthorizationException If login unsuccessful
     */
    public User login(String loginName, String loginPassword)
            throws AuthorizationException;


}
