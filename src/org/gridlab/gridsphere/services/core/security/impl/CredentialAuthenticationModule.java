/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 9, 2003
 * Time: 1:07:04 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.services.core.security.AuthenticationException;
import org.gridlab.gridsphere.services.core.security.AuthenticationModule;
import org.gridlab.gridsphere.services.security.credential.Credential;
import org.gridlab.gridsphere.services.security.credential.CredentialManagerService;
import org.gridlab.gridsphere.services.security.credential.CredentialRetrievalException;

import java.util.Map;

public class CredentialAuthenticationModule implements AuthenticationModule {

    private static PortletLog _log = SportletLog.getInstance(PasswordAuthenticationModule.class);
    private CredentialManagerService credentialManager = null;
    private String name = CredentialAuthenticationModule.class.getName();

    public CredentialAuthenticationModule() {
        init();
    }

    public void init() {
        // Get instance of service factory
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        // Get instance of password manager service
        try {
            this.credentialManager =
                (CredentialManagerService)
                    factory.createPortletService(CredentialManagerService.class, null, true);
        } catch (Exception e) {
            _log.error("Unable to get instance of credential manager service!", e);
        }
    }

    public String getName() {
        return name;
    }

    public void authenticate(User user, String password)
            throws AuthenticationException {
        // Check that password is not null
        if (password == null) {
            AuthenticationException ae = new AuthenticationException();
            ae.putInvalidParameter("password", "Password is not provided.");
            throw ae;
        }
        // Check that password is not blank
        password = password.trim();
        if (password.length() == 0) {
            AuthenticationException ae = new AuthenticationException();
            ae.putInvalidParameter("password", "Password is blank.");
            throw ae;
        }
        // Attempt to retrieve credentials from default retrieval service
        try {
            this.credentialManager.retrieveCredentials(user, password);
        } catch (CredentialRetrievalException ce) {
            AuthenticationException ae = new AuthenticationException();
            ae.putInvalidParameter("password", "Password does not retrieve credentials.");
            throw ae;
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
