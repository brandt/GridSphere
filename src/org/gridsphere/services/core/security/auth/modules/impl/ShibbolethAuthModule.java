package org.gridsphere.services.core.security.auth.modules.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridsphere.services.core.user.User;

public class ShibbolethAuthModule extends BaseAuthModule implements LoginAuthModule {
	
	private String contextName = "Gridsphere";
	private PasswordManagerService passwordManager = null;
	
	private Log log = LogFactory.getLog(ShibbolethAuthModule.class);
		
	public ShibbolethAuthModule(AuthModuleDefinition moduleDef) {
		super(moduleDef);
		//		 Get instance of password manager service
        try {
            this.passwordManager = (PasswordManagerService) PortletServiceFactory.createPortletService(PasswordManagerService.class, true);
        } catch (Exception e) {
            log.error("Unable to get instance of password manager service!", e);
        }
		log.debug("\n\n>>>>>   In ShibbolethAuthModule \n\n");
	}

	/**
	 * Kommt vom Interface LoginAuthModule
	 * Wird ueberschrieben damit ein Login mit einem beliebigen password nicht moeglich ist
	 * 
	 */
	public void checkAuthentication(User user, String password) throws AuthenticationException {
			log.debug("\n\n>>>>>>>>>>        In checkAuthentication\n\n");
//			 Check that password is not null
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
	
	public String getContextName() {
		return contextName;
	}
	
	public void setContextName(String contextName) {
		if (log.isDebugEnabled())
			log.debug("setting contextName = '" + contextName + "'");
		this.contextName = contextName;
	}

}
