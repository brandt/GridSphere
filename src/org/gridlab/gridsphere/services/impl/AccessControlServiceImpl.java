/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRoles;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.AccessControlService;
import org.gridlab.gridsphere.services.impl.acl.AccessControlDataManager;
import org.gridlab.gridsphere.services.impl.acl.AccessControlData;
import org.gridlab.gridsphere.services.impl.acl.GroupACL;
import org.gridlab.gridsphere.services.impl.acl.UserACL;

import java.util.*;

/**
 * The AccessControlService provides access control information for users
 * concerning roles they have within the groups they belong to. Because
 * the AccessControlService doesn't modify any access control settings,
 * it is not implemented as a secure service.
 */
public class AccessControlServiceImpl implements PortletServiceProvider, AccessControlService {


    private static PortletLog log = SportletLog.getInstance(AccessControlServiceImpl.class);
    protected static AccessControlData ACLData = AccessControlDataManager.getACL();

    public void init(PortletServiceConfig config) {
        log.info("in init()");
    }

    public void destroy() {
        log.info("in destroy()");
    }

    public boolean hasRoleInGroup(User user, String groupName, int roleName) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(groupName)) {
                Iterator usersIt = group.getUserACLs().iterator();
                while (usersIt.hasNext()) {
                    UserACL userACL = (UserACL)usersIt.next();
                    if (userACL.getUserID().equals(user.getID())) {
                        Vector roles = userACL.getRoles();
                        if (roles.contains(new Integer(roleName)))
                            return true;
                        break;
                    }
                }
                break;
            }
        }
        return false;
    }

    public Iterator getRolesInGroup(User user, String groupName) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(groupName)) {
                Iterator usersIt = group.getUserACLs().iterator();
                while (usersIt.hasNext()) {
                    UserACL userACL = (UserACL)usersIt.next();
                    if (userACL.getUserID().equals(user.getID())) {
                        return userACL.getRoles().iterator();
                    }
                }
                break;
            }
        }
        return new ArrayList().iterator();
    }


    public Iterator getAllGroupNames() {
        ArrayList groupNames = new ArrayList();
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            groupNames.add(group.getGroupName());
        }
        return groupNames.iterator();
    }

    public Iterator getAllUserNames() {
        ArrayList userNames = new ArrayList();
        Vector groups = ACLData.getGroups();
        Iterator groupIt = groups.iterator();
        while (groupIt.hasNext()) {
            GroupACL group = (GroupACL)groupIt.next();
            Vector users = group.getUserACLs();
            Iterator usersIt = users.iterator();
            while (usersIt.hasNext()) {
                UserACL user = (UserACL)usersIt.next();
                userNames.add(user.getUserName());
            }
        }
        return userNames.iterator();
    }

    public Iterator getAllRoles() {
        return PortletRoles.getRoleNames().iterator();
    }

    public Iterator getGroupNames(User user) {
        ArrayList groupNames = new ArrayList();
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            Iterator usersIt = group.getUserACLs().iterator();
            while (usersIt.hasNext()) {
                UserACL userACL = (UserACL)usersIt.next();
                if (userACL.getUserID().equals(user.getID())) {
                    groupNames.add(group.getGroupName());
                }
            }
        }
        return groupNames.iterator();
    }

    public boolean isUserInGroup(User user, String groupName) {
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(groupName)) {
                Iterator usersIt = group.getUserACLs().iterator();
                while (usersIt.hasNext()) {
                    UserACL userACL = (UserACL)usersIt.next();
                    if (userACL.getUserID().equals(user.getID())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Iterator getUserNamesInGroup(String groupName) {
        ArrayList list = new ArrayList();
        Iterator groupsIt = ACLData.getGroups().iterator();
        while (groupsIt.hasNext()) {
            GroupACL group = (GroupACL)groupsIt.next();
            if (group.getGroupName().equals(groupName)) {
                Iterator usersIt = group.getUserACLs().iterator();
                while (usersIt.hasNext()) {
                    UserACL userACL = (UserACL)usersIt.next();
                    list.add(userACL.getUserName());
                }
                break;
            }
        }
        return list.iterator();
    }

}
