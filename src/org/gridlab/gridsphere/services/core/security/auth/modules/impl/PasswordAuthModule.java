/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.security.auth.modules.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridlab.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;

import java.util.HashMap;
import java.util.Map;

public class PasswordAuthModule extends BaseAuthModule implements LoginAuthModule {

    private PasswordManagerService passwordManager = null;

    private PortletLog log = SportletLog.getInstance(PasswordAuthModule.class);

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
