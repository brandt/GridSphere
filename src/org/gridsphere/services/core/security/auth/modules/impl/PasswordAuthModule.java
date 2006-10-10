/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PasswordAuthModule.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.security.auth.modules.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.User;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridsphere.services.core.security.password.PasswordManagerService;

public class PasswordAuthModule extends BaseAuthModule implements LoginAuthModule {

    private PasswordManagerService passwordManager = null;

    private Log log = LogFactory.getLog(PasswordAuthModule.class);

    public PasswordAuthModule(AuthModuleDefinition moduleDef) {

        super(moduleDef);

        // Get instance of password manager service
        try {
            this.passwordManager = (PasswordManagerService)PortletServiceFactory.createPortletService(PasswordManagerService.class, true);
        } catch (Exception e) {
            log.error("Unable to get instance of password manager service!", e);
        }
    }

    public void checkAuthentication(User user, String password) throws AuthenticationException {
        log.debug("Entering authenticate");
        // Check that password is not null
        if (password == null) {
            log.debug("Password is not provided.");
            throw new AuthenticationException("key1");
        }
        // Check that password maps to the given user
        try {
            this.passwordManager.validateSuppliedPassword(user, password);
        } catch (InvalidPasswordException e) {
            log.debug("Incorrect password provided.");
            throw new AuthenticationException("key2");
        }
    }


}
