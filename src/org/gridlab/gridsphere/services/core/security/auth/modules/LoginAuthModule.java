package org.gridlab.gridsphere.services.core.security.auth.modules;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.AuthenticationException;

import java.util.Map;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface LoginAuthModule extends Comparable {

    public void setAttributes(Map attributes);

    public String getAttribute(String name);

    public Map getAttributes();

    public String getModuleName();

    public String getModuleDescription();

    public int getModulePriority();

    public boolean isModuleActive();
    
    public void checkAuthentication(User user, String password) throws AuthenticationException;

}
