/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.LoginAuthModule;

import java.util.Map;
import java.util.List;

/**
 * The <code>LoginService</code> is the primary interface that defines the login method used to obtain a
 * <code>User</code> from a username and password. The <code>LoginService</code> is configured
 * dynamically at run-time with login authorization modules. By default the PASSWORD_AUTH_MODULE is
 * selected which uses the GridSphere database to store passwords. Other authorization modules
 * can use external directory servers such as LDAP, etc
 */
public interface LoginService extends PortletService {

    public List getSupportedAuthModules();

    public void setActiveAuthModules(List loginModules);

    public List getActiveAuthModules();

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
