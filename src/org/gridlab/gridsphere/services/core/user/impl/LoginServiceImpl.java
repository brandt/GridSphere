/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletGroupFactory;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.user.UserSessionManager;
import org.gridlab.gridsphere.services.core.security.auth.LoginAuthModule;

import java.util.*;
import java.lang.reflect.Constructor;

/**
 * The <code>LoginService</code> is the primary interface that defines the login method used to obtain a
 * <code>User</code> from a username and password. The <code>LoginService</code> is configured
 * dynamically at run-time with login authorization modules. By default the PASSWORD_AUTH_MODULE is
 * selected which uses the GridSphere database to store passwords. Other authorization modules
 * can use external directory servers such as LDAP, etc
 */
public class LoginServiceImpl implements LoginService, PortletServiceProvider {

    private GridSphereUserManager userManager = GridSphereUserManager.getInstance();
    private UserSessionManager userSessionManager = UserSessionManager.getInstance();
    private PortletLog log = SportletLog.getInstance(LoginServiceImpl.class);
    private static boolean inited = false;
    private Map authModules = new HashMap();
    private List activeModules = new ArrayList();
    PortletServiceAuthorizer authorizer = null;

    public LoginServiceImpl(PortletServiceAuthorizer authorizer) {
        this.authorizer = authorizer;
    }

    public List getActiveAuthModules() {
        authorizer.authorizeSuperUser();
        return activeModules;
    }

    public void setActiveAuthModules(List activeModules) {
        authorizer.authorizeSuperUser();
        this.activeModules = activeModules;
    }

    public List getSupportedAuthModules() {
        authorizer.authorizeSuperUser();
        List mods = new ArrayList(authModules.size());
        Iterator it = authModules.values().iterator();
        while (it.hasNext()) {
            mods.add(it.next());
        }
        return mods;
    }

    public List getActiveUserIds() {
        authorizer.authorizeAdminUser(PortletGroupFactory.GRIDSPHERE_GROUP);
        return userSessionManager.getUserIds();
    }

    /**
     * Initializes the portlet service.
     * The init method is invoked by the portlet container immediately after a portlet service has
     * been instantiated and before it is passed to the requestor.
     *
     * @param config the service configuration
     * @throws PortletServiceUnavailableException if an error occurs during initialization
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("in login service init");
        if (!inited) {
            Enumeration enum = config.getInitParameterNames();
            while (enum.hasMoreElements()) {
                String authModuleName = (String)enum.nextElement();
                String authClassName = config.getInitParameter(authModuleName);
                LoginAuthModule authModule = createNewAuthModule(authModuleName, authClassName);
                if (authModule != null) authModules.put(authModuleName, authModule);
            }
            LoginAuthModule activeModule = (LoginAuthModule)authModules.get("PASSWORD_AUTH_MODULE");
            activeModules.add(activeModule);
        }
    }

    private LoginAuthModule createNewAuthModule(String authModuleName, String authClassName) {
        LoginAuthModule authModule = null;
        try {
            Class c = Class.forName(authClassName);
            Class[] parameterTypes = new Class[] {String.class};
            Object[] obj = new Object[] { authModuleName };
            Constructor con = c.getConstructor(parameterTypes);
            authModule = (LoginAuthModule)con.newInstance(obj);
        } catch (ClassNotFoundException cne) {
            log.error("LoginServiceImpl: Unable to locate class: " + authClassName);
        } catch(Exception e) {
            log.error("LoginServiceImpl: Unable to create new LoginAuthModule " + authClassName + "(" + authModuleName + ")");
        }
        log.debug("LoginServiceImpl: created module: " + authModuleName);
        return authModule;
    }

    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy() {}

    /**
     * Login a user with the given login name and password.
     * Returns the associated user if login succeeds.
     * Throws an AuthenticationException if login fails.
     *
     * @param loginName the login name
     * @param loginPassword The login password.
     * @return User The associated user.
     * @throws org.gridlab.gridsphere.services.core.security.auth.AuthorizationException If login unsuccessful
     */
    public User login(String loginName, String loginPassword)
            throws AuthorizationException {
        if ((loginName == null) || (loginPassword == null)) {
            throw new AuthorizationException("Username or password is blank");
        }

        User user = userManager.getUserByUserName(loginName);
        if (user == null) throw new AuthorizationException("User " + loginName + " does not exist");

        Collections.sort(activeModules);

        Iterator it = activeModules.iterator();
        log.debug("Active modules are: ");
        while (it.hasNext()) {
            LoginAuthModule mod = (LoginAuthModule)it.next();
            log.debug( mod.getModuleName() );
            mod.checkAuthorization(user, loginPassword);
        }
        return user;
    }

}
