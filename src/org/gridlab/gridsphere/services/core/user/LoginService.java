/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;

import java.util.List;

/**
 * The <code>LoginService</code> is the primary interface that defines the login method used to obtain a
 * <code>User</code> from a username and password. The <code>LoginService</code> is configured
 * dynamically at run-time with login authorization modules. By default the PASSWORD_AUTH_MODULE is
 * selected which uses the GridSphere database to store passwords. Other authorization modules
 * can use external directory servers such as LDAP, etc
 */
public interface LoginService extends PortletService {

    public static final String CAN_USER_CREATE_ACCOUNT = "LoginService.CAN_USER_CREATE_ACCOUNT";

    public List getSupportedAuthModules();

    public void addActiveAuthModule(User user, LoginAuthModule authModule);

    public void removeActiveAuthModule(User user, String moduleClassName);

    public List getActiveAuthModules(User user);

    public boolean hasActiveAuthModule(User user, String moduleClassName);

    public LoginUserModule getActiveLoginModule();

    /**
     * Login a user with the given login name and password.
     * Returns the associated user if login succeeds.
     * Throws an AuthenticationException if login fails.
     *
     * @param loginName     the login name
     * @param loginPassword the login password.
     * @return User The associated user.
     * @throws org.gridlab.gridsphere.services.core.security.auth.AuthorizationException
     *          If login unsuccessful
     */
    public User login(String loginName, String loginPassword)
            throws AuthorizationException;


}
