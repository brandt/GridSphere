/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 8, 2003
 * Time: 11:24:08 AM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security;

import org.gridlab.gridsphere.portlet.User;

import java.util.Map;

public interface AuthenticationModule {

    public String getName();

    public void authenticate(User user, String password)
            throws AuthenticationException;

    public void authenticate(User user, Map parameters)
            throws AuthenticationException;
}
