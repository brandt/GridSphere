package org.gridlab.gridsphere.services.core.security.auth.modules.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;

import java.util.Map;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public abstract class BaseAuthModule implements LoginAuthModule {

    protected Map attributes = null;

    public String getAttribute(String name) {
        return (String) attributes.get(name);
    }

    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    public abstract String getModuleName();

    public abstract int getModulePriority();

    public abstract void checkAuthorization(User user, String password) throws AuthorizationException;

}
