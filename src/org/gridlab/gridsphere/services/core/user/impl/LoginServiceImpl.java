/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.security.auth.LoginAuthModule;

import java.util.*;
import java.lang.reflect.Constructor;

public class LoginServiceImpl implements LoginService, PortletServiceProvider {

    private GridSphereUserManager userManager = GridSphereUserManager.getInstance();
    private PortletLog log = SportletLog.getInstance(LoginServiceImpl.class);
    private Map authModules = new HashMap();
    private LoginAuthModule activeModule = null;
    PortletServiceAuthorizer authorizer = null;

    public LoginServiceImpl(PortletServiceAuthorizer authorizer) {
        this.authorizer = authorizer;
    }

    public void setActiveAuthModule(LoginAuthModule activeModule) {
        authorizer.authorizeSuperUser();
        this.activeModule = activeModule;
    }

    public Map getSupportedAuthModules() {
        authorizer.authorizeSuperUser();
        return authModules;
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
        //loginManager.init(config);
        Enumeration enum = config.getInitParameterNames();
        while (enum.hasMoreElements()) {
            String authModuleName = (String)enum.nextElement();
            String authClassName = config.getInitParameter(authModuleName);
            LoginAuthModule authModule = createNewAuthModule(authModuleName, authClassName);
            if (authModule != null) authModules.put(authModuleName, authModule);
        }
        activeModule = (LoginAuthModule)authModules.get("PASSWORD_AUTH_MODULE");
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
        if (user == null) throw new AuthorizationException("User does not exist in database");

        System.err.println("Active module is: " + activeModule.getModuleName());

        activeModule.checkAuthorization(user, loginPassword);
        return user;
    }

}
