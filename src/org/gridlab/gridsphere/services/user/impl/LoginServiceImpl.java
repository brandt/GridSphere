/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 3:10:25 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.user.impl;

import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.services.user.LoginService;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.security.AuthenticationModule;
import org.gridlab.gridsphere.services.security.AuthenticationException;
import org.gridlab.gridsphere.services.security.impl.PasswordAuthenticationModule;

import java.util.Vector;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

public class LoginServiceImpl
    implements PortletServiceProvider, LoginService {

    private static PortletLog _log = SportletLog.getInstance(LoginServiceImpl.class);
    private PersistenceManagerRdbms pm = PersistenceManagerRdbms.getInstance();
    private UserManagerService userManager = null;
    private List authenticationModules = new Vector();

    /****** PORTLET SERVICE METHODS *******/

    public void init(PortletServiceConfig config) {
        _log.info("Entering init()");
        initServices();
        initAuthenticationModules();
        _log.info("Exiting init()");
    }

    private void initServices() {
        // Get instance of service factory
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        // Get instance of password manager service
        try {
            this.userManager =
                (UserManagerService)
                    factory.createPortletService(UserManagerService.class, null, true);
        } catch (Exception e) {
            _log.error("Unable to get instance of password manager service!", e);
        }
    }

    private void initAuthenticationModules() {
        authenticationModules.add(new PasswordAuthenticationModule());
    }

    public void destroy() {
    }

    /****** LOGIN SERVICE METHODS *******/

    public List getAuthenticationModules() {
        return this.authenticationModules;
    }

    public AuthenticationModule getAuthenticationModule(String name) {
        Iterator modules = this.authenticationModules.iterator();
        while (modules.hasNext()) {
            AuthenticationModule module = (AuthenticationModule)modules.next();
            if (module.getName().equals(name)) {
                return module;
            }
        }
        return null;
    }

    public User login(String username, String password)
            throws AuthenticationException {
        User user = getUser(username);
        AuthenticationException ex = null;
        Iterator modules = this.authenticationModules.iterator();
        while (modules.hasNext()) {
            AuthenticationModule module = (AuthenticationModule)modules.next();
            try {
               module.authenticate(user, password);
            } catch (AuthenticationException e) {
                if (ex == null) {
                    ex = e;
                }
            }
        }
        if (ex != null) {
            throw ex;
        }
        return user;
    }

    public User login(Map parameters)
            throws AuthenticationException {
        User user = getUser(parameters);
        AuthenticationException ex = null;
        Iterator modules = this.authenticationModules.iterator();
        while (modules.hasNext()) {
            AuthenticationModule module = (AuthenticationModule)modules.next();
            try {
               module.authenticate(user, parameters);
            } catch (AuthenticationException e) {
                if (ex == null) {
                    ex = e;
                }
            }
        }
        if (ex != null) {
            throw ex;
        }
        return user;
    }

    private User getUser(Map parameters)
            throws AuthenticationException {
        String username = (String)parameters.get("username");
        return getUser(username);
    }

    private User getUser(String username)
            throws AuthenticationException {
        _log.debug("Attempting to retrieve user " + username);
        User user = null;
        if (username == null) {
            AuthenticationException ex = new AuthenticationException();
            ex.putInvalidParameter("username", "No username provided.");
            throw ex;
        }
        //user = this.userManager.getUser(username);
        if (user == null) {
            _log.debug("Unable to retrieve user " + username);
            AuthenticationException ex = new AuthenticationException();
            ex.putInvalidParameter("username", "Invalid username provided.");
            throw ex;
        }
        _log.debug("Successfully retrieved user " + username);
        return user;
    }
}
