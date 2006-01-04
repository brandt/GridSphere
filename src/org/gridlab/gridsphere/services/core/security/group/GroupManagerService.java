/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 31, 2003
 * Time: 4:06:44 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.group;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.services.core.security.group.impl.UserGroup;

import java.util.List;

public interface GroupManagerService extends PortletService {

    public PortletGroup getCoreGroup();

    public List getGroups();

    public void deleteGroup(PortletGroup group);

    public PortletGroup getGroup(String groupId);

    public PortletGroup getGroupByName(String groupName);

    public PortletGroup createGroup(SportletGroup portletGroup);

    public UserGroup createGroupEntry();

    public UserGroup editGroupEntry(UserGroup entry);

    public List getGroupEntries();

    public List getGroupEntries(User user);

    public List getGroupEntries(PortletGroup group);

    public UserGroup getGroupEntry(String id);

    public UserGroup getGroupEntry(User user, PortletGroup group);

    public void addGroupEntry(User user, PortletGroup group, PortletRole role);

    public void addUserToGroup(User user, PortletGroup group);

    public void deleteGroupEntry(UserGroup entry);

    public void deleteGroupEntries(User user);

    public void saveGroupEntry(UserGroup groupEntry);

    public List getGroups(User user);

    public List getGroupsNotMemberOf(User user);

    public List getUsersInGroup(PortletGroup group);

    public boolean isUserInGroup(User user, PortletGroup group);

}
