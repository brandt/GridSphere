/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 31, 2003
 * Time: 4:06:44 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridsphere.services.core.security.group;

import org.gridsphere.services.core.user.User;
import org.gridsphere.portlet.service.PortletService;

import java.util.List;

public interface GroupManagerService extends PortletService {

    public List getGroups();

    public void deleteGroup(PortletGroup group);

    public PortletGroup getGroup(String groupName);

    public void saveGroup(PortletGroup portletGroup);

    public void addUserToGroup(User user, PortletGroup group);

    public void deleteUserInGroup(User user, PortletGroup group);

    public List getGroups(User user);

    public List getUsersInGroup(PortletGroup group);

    public boolean isUserInGroup(User user, PortletGroup group);

}
