/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.security.AuthenticationException;
import org.gridlab.gridsphere.services.core.user.LoginService;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;

public class LoginServiceImpl implements LoginService, PortletServiceProvider {

    private GridSphereUserManager loginManager = GridSphereUserManager.getInstance();

    public LoginServiceImpl() {}

    /**
     * Initializes the portlet service.
     * The init method is invoked by the portlet container immediately after a portlet service has
     * been instantiated and before it is passed to the requestor.
     *
     * @param config the service configuration
     * @throws PortletServiceUnavailableException if an error occurs during initialization
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        loginManager.init(config);
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
     * @throws AuthenticationException If login unsuccessful
     */
    public User login(String loginName, String loginPassword)
            throws AuthenticationException {
        return loginManager.login(loginName, loginPassword);
    }

    /*
    public void sessionCreated(HttpSession session) {

    }

    public void sessionDestoyed(HttpSession session) {

    }

    public void addSessionListener(String sessionID, HttpSessionListener sessionListener) {

    }
    */
}
