/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.AccessDeniedException;
import org.gridlab.gridsphere.portlet.PortletRoles;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.AccessControlManagerService;
import org.gridlab.gridsphere.services.impl.acl.GroupACL;
import org.gridlab.gridsphere.services.impl.acl.UserACL;
import org.gridlab.gridsphere.services.impl.acl.AccessControlData;
import org.gridlab.gridsphere.services.impl.acl.AccessControlDataManager;

import java.util.Iterator;


public class AccessControlManagerServiceImpl implements PortletServiceProvider, AccessControlManagerService {

    private static PortletLog log = SportletLog.getInstance(AccessControlManagerServiceImpl.class);
    protected static AccessControlData ACLData = null;

    public void init(PortletServiceConfig config) {
        log.info("in init() of AccessControlManagerServiceImpl");
        ACLData = AccessControlDataManager.getACL();
    }

    public void destroy() {
        log.info("in destroy() of AccessControlManagerServiceImpl");
        AccessControlDataManager.save();
    }

    public void addUserToSuperRole(User user) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            Iterator usersIt = group.getUserACLs().iterator();
            while (usersIt.hasNext()) {
                UserACL userACL = (UserACL)usersIt.next();
                if (userACL.getUserID().equals(user.getID())) {
                    userACL.addRole(PortletRoles.SUPER);
                    break;
                }
            }
        }
    }

    public void createNewGroup(String groupName) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        boolean groupExists = false;
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(groupName)) {
                groupExists = true;
            }
        }
        if (!groupExists) {
            GroupACL newGroup = ACLData.createGroup();
            newGroup.setGroupName(groupName);
            ACLData.addGroup(newGroup);
        }
    }

    public void renameGroup(String oldGroupName, String newGroupName) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(oldGroupName)) {
                group.setGroupName(newGroupName);
                break;
            }
        }
    }

    public void removeGroup(String groupName) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(groupName)) {
                ACLData.removeGroup(group);
                break;
            }
        }
    }

    public void addRoleInGroup(User user, String groupName, int roleName) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(groupName)) {
                Iterator usersIt = group.getUserACLs().iterator();
                while (usersIt.hasNext()) {
                    UserACL userACL = (UserACL)usersIt.next();
                    if (userACL.getUserID().equals(user.getID())) {
                        userACL.addRole(roleName);
                        break;
                    }
                }
                break;
            }
        }
    }

    public void addUserToGroup(User user, String groupName, int roleName) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(groupName)) {
                UserACL userACL = group.createUserACL();
                userACL.setUserID(user.getID());
                userACL.setUserName(user.getFullName());
                userACL.addRole(roleName);
                group.addUserACL(userACL);
                break;
            }
        }
    }

    public void removeUserFromGroup(User user, String groupName) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(groupName)) {
                Iterator usersIt = group.getUserACLs().iterator();
                while (usersIt.hasNext()) {
                    UserACL userACL = (UserACL)usersIt.next();
                    if (userACL.getUserID().equals(user.getID())) {
                        group.removeUserACL(userACL);
                        break;
                    }
                }
                break;
            }
        }
    }

    public void removeUserRoleFromGroup(User user, String groupName, int roleName) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(groupName)) {
                Iterator usersIt = group.getUserACLs().iterator();
                while (usersIt.hasNext()) {
                    UserACL userACL = (UserACL)usersIt.next();
                    if (userACL.getUserID().equals(user.getID())) {
                        userACL.removeRole(roleName);
                        break;
                    }
                }
                break;
            }
        }
    }

}
