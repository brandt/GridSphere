/**
 * @version $Id: JaasAuthModule.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.security.auth.modules.impl;

import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.CredentialExpiredException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.User;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;

public class JaasAuthModule extends BaseAuthModule implements LoginAuthModule {

    private String contextName = "Gridsphere";
    
    private PortletLog log = SportletLog.getInstance(JaasAuthModule.class);

    public JaasAuthModule(AuthModuleDefinition moduleDef) {
        super(moduleDef);
    }

    public void checkAuthentication(User user, String password) throws AuthenticationException {

		String username = user.getUserName();

        if (log.isDebugEnabled()) log.debug("beginning authentication for '" + username + "'");

		LoginContext loginContext;

		// Create the LoginContext
		try{
			loginContext = new LoginContext(contextName, new JaasCallbackHandler(username, password));
			if (log.isDebugEnabled()) log.debug("got loginContext");
		} catch (SecurityException e) {
		    if (log.isDebugEnabled()) log.debug("SecurityException: " + e);
			throw new AuthenticationException("key4", e);
		} catch (LoginException e) {
		    if (log.isDebugEnabled()) log.debug("LoginException: " + e);
      		throw new AuthenticationException("key4", e);
		}

		// Attempt login
		try{
			loginContext.login();
			if (log.isDebugEnabled()) log.debug("login successful");
		} catch (FailedLoginException e) {
		    if (log.isDebugEnabled()) log.debug("login failed: " + e.getMessage());
			throw new AuthenticationException("key4", e);
		} catch (AccountExpiredException e) {
		    if (log.isDebugEnabled()) log.debug("account expired");
			throw new AuthenticationException("key1");
		} catch (CredentialExpiredException e) {
		    if (log.isDebugEnabled()) log.debug("credentials expired");
			throw new AuthenticationException("key2", e);
		} catch (Exception e) {
		    if (log.isDebugEnabled()) log.debug("unexpected failure: " + e.getMessage());
			throw new AuthenticationException("key3", e);
		}

    }

    public String getContextName() {
        return contextName;
    }
    public void setContextName(String contextName) {
        if (log.isDebugEnabled()) log.debug("setting contextName = '" + contextName + "'");
        this.contextName = contextName;
    }
    
}
