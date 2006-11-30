/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: LoginService.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.security.auth;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;

import java.util.List;

/**
 * The <code>AuthModuleService</code> provides the portal with the available authentication
 * modules. By default the PasswordAuthModule is selected which uses the GridSphere database
 * to store passwords. Other authorization modules can use external directory servers such as LDAP, etc
 */
public interface AuthModuleService extends PortletService {

    public void loadAuthModules(String authModsPath, ClassLoader classloader);

    public List<LoginAuthModule> getActiveAuthModules();

    public List<LoginAuthModule> getAuthModules();

    public void saveAuthModule(LoginAuthModule authModule);

}
