/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Dec 18, 2002
 * Time: 2:04:14 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.user;

import java.util.List;
import java.util.Map;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.security.AuthenticationException;
import org.gridlab.gridsphere.services.security.AuthenticationModule;

public interface LoginService extends PortletService {

    public List getAuthenticationModules();

    public AuthenticationModule getAuthenticationModule(String name);

    public User login(String username, String password)
            throws AuthenticationException;

    public User login(Map parameters)
            throws AuthenticationException;
}
