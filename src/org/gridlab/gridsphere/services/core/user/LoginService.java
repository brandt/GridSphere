/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;

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

    public void loadAuthModules(String authModsPath, ClassLoader classloader);

    public void addActiveAuthModule(User user, LoginAuthModule authModule);

    public void removeActiveAuthModule(User user, String moduleClassName);

    public List getActiveAuthModules(User user);

    public boolean hasActiveAuthModule(User user, String moduleClassName);

    public List getAuthModules();

    public LoginAuthModule getAuthModule(String moduleClassName);
    
    public LoginUserModule getActiveLoginModule();

    /**
     * Login a user. Implementation
     * obtains username and password from supplied PortletRequest
     *
     * @param req the portlet request
     *
     * @return User The associated user.
     * @throws org.gridlab.gridsphere.services.core.security.auth.AuthorizationException
     *          If login unsuccessful
     */
    public User login(PortletRequest req)
            throws AuthenticationException, AuthorizationException;

    public void saveAuthModule(LoginAuthModule authModule);

}
