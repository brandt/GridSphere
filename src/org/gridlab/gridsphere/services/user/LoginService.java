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

    /**
     * Login a user with the given login name and password.
     * Returns the associated user if login succeeds.
     * Throws an AuthenticationException if login fails.
     *
     * @param String The login name or user id.
     * @param String The login password.
     * @return User The associated user.
     * @throws AuthenticationException If login unsuccessful
     */
    public User login(String loginName, String loginPassword)
            throws AuthenticationException;

    /**
     * Login a user with the given login parameters.
     * Returns the associated user if login succeeds.
     * Throws an AuthenticationException if login fails.
     *
     * @param Map The login parameters.
     * @return User The associated user.
     * @throws AuthenticationException If login unsuccessful
     */
    public User login(Map parameters)
            throws AuthenticationException;
}
