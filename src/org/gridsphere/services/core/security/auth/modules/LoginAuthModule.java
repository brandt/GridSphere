package org.gridsphere.services.core.security.auth.modules;

import org.gridsphere.services.core.user.User;
import org.gridsphere.services.core.security.auth.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: LoginAuthModule.java 4496 2006-02-08 20:27:04Z wehrens $
 */
public interface LoginAuthModule extends Comparable {

    public void setHttpServletRequest(HttpServletRequest request);

    public void setAttributes(Map attributes);

    public String getAttribute(String name);

    public Map getAttributes();

    public String getModuleName();

    public String getModuleDescription(Locale locale);

    public int getModulePriority();

    public boolean isModuleActive();

    public void setModulePriority(int priority);

    public void setModuleActive(boolean isActive);

    public String getModuleError(String key, Locale locale);

    public abstract void checkAuthentication(User user, String password) throws AuthenticationException;

}
