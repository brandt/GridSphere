/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.services.core.security.AuthenticationException;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;

public interface LoginService extends PortletService {

    /**
     * Login a user with the given login name and password.
     * Returns the associated user if login succeeds.
     * Throws an AuthenticationException if login fails.
     *
     * @param loginName the login name
     * @param loginPassword the login password.
     * @return User The associated user.
     * @throws AuthenticationException If login unsuccessful
     */
    public User login(String loginName, String loginPassword)
            throws AuthenticationException;

    /*
    public void sessionCreated(HttpSession session);

    public void sessionDestoyed(HttpSession session);

    public void addSessionListener(HttpSessionListener sessionListener);
    */

}
