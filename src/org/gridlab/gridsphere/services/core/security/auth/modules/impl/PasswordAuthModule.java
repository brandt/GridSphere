/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.security.auth.modules.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridlab.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;

import java.util.HashMap;
import java.util.Map;

public class PasswordAuthModule extends BaseAuthModule {

    private static final int PASSWORD_MODULE_PRIORITY = 100;
    private String moduleName;
    private PasswordManagerService passwordManager = null;

    private PortletLog log = SportletLog.getInstance(PasswordAuthModule.class);
    private Map env = new HashMap();

    public PasswordAuthModule(String moduleName) {
        this.moduleName = moduleName;

        // Get instance of service factory
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        // Get instance of password manager service
        try {
            this.passwordManager = (PasswordManagerService) factory.createPortletService(PasswordManagerService.class, null, true);
        } catch (Exception e) {
            log.error("Unable to get instance of password manager service!", e);
        }
    }

    public void setEnvironmentVariable(String name, String value) {
        env.put(name, value);
    }

    public String getEnvironmentVariable(String name) {
        return (String) env.get(name);
    }

    public String getModuleName() {
        return moduleName;
    }

    public int getModulePriority() {
        return PASSWORD_MODULE_PRIORITY;
    }

    public int compareTo(Object obj) {
        if ((obj != null) && (obj instanceof LoginAuthModule)) {
            LoginAuthModule l = (LoginAuthModule) obj;
            if (this.PASSWORD_MODULE_PRIORITY < l.getModulePriority()) {
                return -1;
            } else if (this.PASSWORD_MODULE_PRIORITY > l.getModulePriority()) {
                return 1;
            } else {
                return 0;
            }
        }
        throw new ClassCastException("Unable to compare supplied object to this module");
    }

    public void checkAuthorization(User user, String password) throws AuthorizationException {

        log.debug("Entering authenticate");
        // Check that password is not null
        if (password == null) {
            String msg = "Password is not provided.";
            log.debug(msg);
            AuthorizationException ex = new AuthorizationException(msg);
            ex.putInvalidParameter("password", msg);
            throw ex;
        }
        // Check that password maps to the given user
        try {
            this.passwordManager.validateSuppliedPassword(user, password);
        } catch (InvalidPasswordException e) {
            String msg = "Incorrect password provided.";
            log.debug(msg);
            AuthorizationException ex = new AuthorizationException(msg);
            ex.putInvalidParameter("password", msg);
            throw ex;
        }
    }

}
