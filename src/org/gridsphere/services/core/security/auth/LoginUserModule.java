/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: LoginUserModule.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.security.auth;

import org.gridsphere.portlet.User;
import org.gridsphere.portlet.service.PortletService;

public interface LoginUserModule extends PortletService {

    public User getLoggedInUser(String loginName);

}
