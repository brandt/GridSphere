/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Dec 18, 2002
 * Time: 2:04:14 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.login.impl;

import java.util.*;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.security.login.LoginService;
import org.gridlab.gridsphere.services.security.login.LoginException;
import org.gridlab.gridsphere.services.security.credential.CredentialManagerService;
import org.gridlab.gridsphere.services.security.credential.CredentialRetrievalException;
import org.gridlab.gridsphere.services.security.credential.impl.GlobusCredentialManagerService;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

public class CredentialLoginServiceImpl
        implements LoginService, PortletServiceProvider {

    private static PortletLog _log = SportletLog.getInstance(CredentialLoginServiceImpl.class);
    private static SportletServiceFactory _factory = SportletServiceFactory.getInstance();

    private UserManagerService userService = null;
    private CredentialManagerService credentialService = null;

    /****** PORTLET SERVICE METHODS *******/

    public void init(PortletServiceConfig config)
            throws PortletServiceUnavailableException {
        _log.info("Entering init()");
        // Get user manager service
        try {
            userService =
                    (UserManagerService)_factory.createPortletService
                        (UserManagerService.class, config.getServletConfig(), true);
        } catch (PortletServiceNotFoundException e) {
            throw new PortletServiceUnavailableException("Service instance not found: " + e.toString());
        }
        // Get credential manager service
        try {
            credentialService =
                    (CredentialManagerService)_factory.createPortletService
                        (CredentialManagerService.class, config.getServletConfig(), true);
        } catch (PortletServiceNotFoundException e) {
            throw new PortletServiceUnavailableException("Service instance not found: " + e.toString());
        }
        _log.info("Exiting init()");
    }

    public void destroy() {
    }

    /****** LOGIN SERVICE METHODS *******/

    public User login(String username, String password)
            throws LoginException {
        Map parameters = new TreeMap();
        parameters.put("username", username);
        parameters.put("password", password);
        return login(parameters);
    }

    public User login(Map parameters)
            throws LoginException {
        User user = null;
        // 1. Login with appropriate method
        if (parameters.containsKey("credentials")) {
            user = uploadCredentialsLogin(parameters);
        } else {
            user = retrieveCredentialsLogin(parameters);
        }
        // 2. Add user to list of logged in users
        //this.userService.addLoggedInUser(user);
        return user;
    }

    private User uploadCredentialsLogin(Map parameters)
            throws LoginException {
        throw new LoginException("Method not yet implemented!");
    }

    private User retrieveCredentialsLogin(Map parameters)
            throws LoginException {
        // 1. Attempt to retrieve user from user manager service
        String username = (String)parameters.get("username");
        if (username == null || username.equals("")) {
            LoginException ex = new LoginException();
            ex.putInvalidParameter("username", "Username not provided!");
            throw ex;
        }
        // VERY LAME THAT I CANNOT SIMPLY RETRIEVE A USER OBJECT AS NEEDED!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //User user = this.userService.getUser(username);
        User user = null;
        if (user == null) {
            LoginException ex = new LoginException();
            ex.putInvalidParameter("username", "No user exists with given username!");
            throw ex;
        }
        // 2. Attempt to retrieve user's credentials with given password
        String password = (String)parameters.get("password");
        if (password == null || password.equals("")) {
            LoginException ex = new LoginException();
            ex.putInvalidParameter("password", "Password not provided!");
            throw ex;
        }
        try {
            this.credentialService.retrieveCredentials(user, password);
        } catch (CredentialRetrievalException e) {
            LoginException ex = new LoginException();
            ex.putInvalidParameter("password", "Failed to retrieve user's credentials!");
            throw ex;
        }
        return user;
    }

    public void logout(User user) {
        this.credentialService.destroyCredentials(user);
        //this.userService.removeLoggedInUser(user);
    }
}
