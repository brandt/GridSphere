/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Dec 18, 2002
 * Time: 2:04:14 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.login;

import java.util.List;
import java.util.Map;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;

public interface LoginService extends PortletService {

    public User login(String username, String password)
            throws LoginException;

    public User login(Map parameters)
            throws LoginException;

    public void logout(User user);
}
