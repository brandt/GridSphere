/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 31, 2003
 * Time: 4:06:44 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.role;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;

import java.util.List;

public interface RoleManagerService extends PortletService {

    public List getRoles();

    public void deleteRole(PortletRole role);

    public PortletRole getRole(String roleName);

    public void saveRole(PortletRole role);

    public boolean isUserInRole(User user, PortletRole role);

    public List getRolesForUser(User user);

    public List getUsersInRole(PortletRole role);

    public void addUserToRole(User user, PortletRole role);

    public void deleteUserInRole(User user, PortletRole role);

}
