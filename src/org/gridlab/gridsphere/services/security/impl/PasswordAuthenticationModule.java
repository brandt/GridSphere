/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 1:13:02 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.services.security.AuthenticationException;
import org.gridlab.gridsphere.services.security.AuthenticationModule;
import org.gridlab.gridsphere.services.security.password.PasswordManagerService;

import java.util.Map;

public class PasswordAuthenticationModule implements AuthenticationModule {

    private static PortletLog _log = SportletLog.getInstance(PasswordAuthenticationModule.class);
    private PasswordManagerService passwordManager = null;
    private String name = PasswordAuthenticationModule.class.getName();

    public PasswordAuthenticationModule() {
        init();
    }

    public void init() {
        // Get instance of service factory
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        // Get instance of password manager service
        try {
            this.passwordManager =
                (PasswordManagerService)
                    factory.createPortletService(PasswordManagerService.class, null, true);
        } catch (Exception e) {
            _log.error("Unable to get instance of password manager service!", e);
        }
    }

    public String getName() {
        return name;
    }

    public void authenticate(User user, String password)
            throws AuthenticationException {
        // Check that password is not null
        if (password == null) {
            AuthenticationException ex = new AuthenticationException();
            ex.putInvalidParameter("password", "Password is not provided.");
            throw ex;
        }
        // Check that password is not blank
        password = password.trim();
        if (password.length() == 0) {
            AuthenticationException ex = new AuthenticationException();
            ex.putInvalidParameter("password", "Password is blank.");
            throw ex;
        }
        // Check that password maps to the given user
        if (!this.passwordManager.isPasswordCorrect(user, password)) {
            AuthenticationException ex = new AuthenticationException();
            ex.putInvalidParameter("password", "Incorrect password provided.");
            throw ex;
        }
    }

    public void authenticate(User user, Map parameters)
            throws AuthenticationException {
        // Retrieve password parameter
        String password = (String)parameters.get("password");
        // Then authenticate with default method
        authenticate(user, password);
    }
}
