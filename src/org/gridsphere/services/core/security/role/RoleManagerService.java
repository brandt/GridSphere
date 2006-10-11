/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 31, 2003
 * Time: 4:06:44 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridsphere.services.core.security.role;

import org.gridsphere.services.core.user.User;
import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.services.core.persistence.QueryFilter;

import java.util.List;

public interface RoleManagerService extends PortletService {

    public List getRoles();

    public void deleteRole(PortletRole role);

    public PortletRole getRole(String roleName);

    public void saveRole(PortletRole role);

    public boolean isUserInRole(User user, PortletRole role);

    public List getRolesForUser(User user);

    public int getNumUsersInRole(PortletRole role);

    public List getUsersInRole(PortletRole role);

    public List getUsersInRole(PortletRole role, QueryFilter filter);

    public void addUserToRole(User user, PortletRole role);

    public void deleteUserInRole(User user, PortletRole role);

}
