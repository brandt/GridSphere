package org.gridlab.gridsphere.services.core.security.auth;

import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.portlet.User;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface LoginAuthModule extends Comparable {

    public String getModuleName();

    public int getModulePriority();

    public void checkAuthorization(User user, String password) throws AuthorizationException;

}
