/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.AccessDeniedException;

import java.util.List;

public interface AccessControlManagerService extends PortletService {

    public void addUserToSuperRole(User user);

    public void createNewGroup(String groupName);

    public void renameGroup(String oldGroupName, String newGroupName);

    public void removeGroup(String groupName);

    public void addRoleInGroup(User user, String groupName, int roleName);

    public void addUserToGroup(User user, String groupName, int roleName);

    public void removeUserFromGroup(User user, String groupName);

    public void removeUserRoleFromGroup(User user, String groupName, int roleName);

}
