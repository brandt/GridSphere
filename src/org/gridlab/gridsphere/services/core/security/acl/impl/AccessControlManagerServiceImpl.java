/*
 *
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.security.acl.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletRoleInfo;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.security.acl.GroupEntry;
import org.gridlab.gridsphere.services.core.security.acl.GroupRequest;
import org.gridlab.gridsphere.services.core.user.impl.UserManagerServiceImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.*;

public class AccessControlManagerServiceImpl extends HibernateDaoSupport implements AccessControlManagerService {

    private static PortletLog log = SportletLog.getInstance(AccessControlManagerServiceImpl.class);

    private String jdoGroupRequest = GroupRequestImpl.class.getName();
    private String jdoPortletGroup = SportletGroup.class.getName();

    public AccessControlManagerServiceImpl() {

    }

    public GroupRequest createGroupEntry() {
        GroupRequest request = new GroupRequestImpl();
        this.saveGroupEntry(request);
        return request;
    }

    public GroupRequest editGroupEntry(GroupEntry groupEntry) {
        return (GroupRequest) this.getGroupEntry(groupEntry.getID());
    }

    public List getGroupEntries() {
        String criteria = "";
        return selectGroupEntries(criteria);
    }

    public List getGroupEntries(User user) {
        String criteria = "where groupRequest.sportletUser.oid='" + user.getID() + "'";
        return selectGroupEntries(criteria);
    }

    public List getGroupEntries(PortletGroup group) {
        String criteria = "where groupRequest.sportletGroup.oid='" + group.getID() + "'";
        return selectGroupEntries(criteria);
    }

    private List selectGroupEntries(String criteria) {
        String oql = "select groupRequest from "
                + jdoGroupRequest
                + " groupRequest "
                + criteria;

        return this.getHibernateTemplate().find(oql);

    }

    public GroupEntry getGroupEntry(String id) {
        String criteria = "where groupRequest.oid='" + id + "'";
        return selectGroupRequestImpl(criteria);
    }

    public GroupEntry getGroupEntry(User user, PortletGroup group) {
        return getGroupRequestImpl(user, group);
    }

    private GroupRequestImpl getGroupRequestImpl(User user, PortletGroup group) {
        String criteria = " where groupRequest.sportletUser.oid='" + user.getID() + "'"
                + " and groupRequest.sportletGroup.oid='" + group.getID() + "'";
        return selectGroupRequestImpl(criteria);
    }

    private GroupRequestImpl selectGroupRequestImpl(String criteria) {
        String oql = "select groupRequest from "
                + jdoGroupRequest
                + " groupRequest "
                + criteria;
       List groups = this.getHibernateTemplate().find(oql);
       if ((groups != null) && (!groups.isEmpty())) {
            return (GroupRequestImpl)groups.get(0);
        }
        return null;
    }

    public void saveGroupEntry(GroupEntry entry) {
        // Create or update access right
           this.getHibernateTemplate().saveOrUpdate(entry);
    }

    public void deleteGroupEntry(GroupEntry entry) {
       this.getHibernateTemplate().delete(entry);
    }

    public void deleteGroupEntries(User user) {
        Iterator groupEntries = getGroupEntries(user).iterator();
        while (groupEntries.hasNext()) {
            GroupEntry groupEntry = (GroupEntry) groupEntries.next();
            deleteGroupEntry(groupEntry);
        }
    }

    public List getGroups() {
        // Execute query
        return this.getHibernateTemplate().find("select grp from " + jdoPortletGroup + " grp ");
    }

    public PortletGroup getGroupByName(String name) {
        return selectSportletGroup("where grp.Name='" + name + "'");
    }

    public PortletGroup getGroup(String id) {
        return selectSportletGroup("where grp.oid='" + id + "'");
    }

    public PortletGroup getCoreGroup() {
        return selectSportletGroup("where grp.Core=" + true);
    }

    private SportletGroup selectSportletGroup(String criteria) {
        // Build object query
        StringBuffer oqlBuffer = new StringBuffer();
        oqlBuffer.append("select grp from ");
        oqlBuffer.append(jdoPortletGroup);
        oqlBuffer.append(" grp ");
        oqlBuffer.append(criteria);
        // Generate object query
        String oql = oqlBuffer.toString();
        log.debug(oql);
        List groups = this.getHibernateTemplate().find(oql);
        if ((groups != null) && (!groups.isEmpty())) {
            return (SportletGroup)groups.get(0);
        }
        return null;
    }

    public boolean existsGroupWithName(String groupName) {
        return (getGroupByName(groupName) != null);
    }

    public PortletGroup createGroup(SportletGroup portletGroup) {
        this.getHibernateTemplate().saveOrUpdate(portletGroup);
        return portletGroup;
    }


    public void deleteGroup(PortletGroup group) {
        this.getHibernateTemplate().delete(group);
    }

    public List getUsers(PortletGroup group) {
        String oql = "select groupRequest.sportletUser from "
                + jdoGroupRequest
                + " groupRequest where groupRequest.sportletGroup.oid='"
                + group.getID()
                + "'";
        return this.getHibernateTemplate().find(oql);
    }

    public List getUsers(PortletGroup group, PortletRole role) {
        List users = this.getUsers(group);
        Iterator it = users.iterator();
        List l = new Vector();
        while (it.hasNext()) {
            User u = (User) it.next();
            //System.err.println("Checking if " + u.getFullName() + " has " + role);
            if (this.hasRoleInGroup(u, group, role)) {
                //System.err.println("user has role in group" + u.getFullName() + " " + u.getEmailAddress());
                l.add(u);
            }
        }
        return l;
    }

    public boolean isUserInGroup(User user, PortletGroup group) {
        return (getGroupEntry(user, group) != null);
    }

    public List getGroups(User user) {
        // If user has super role
        //if (hasSuperRole(user)) {
        //    groups = getGroups();
        //} else {
        // Otherwise, return groups for given user
        String oql = "select groupRequest.sportletGroup from "
                + jdoGroupRequest
                + " groupRequest where groupRequest.sportletUser.oid='"
                + user.getID()
                + "'";
        return this.getHibernateTemplate().find(oql);
    }

    public List getGroupsNotMemberOf(User user) {
        List groupsNotMemberOf = new Vector();
        if (!hasSuperRole(user)) {
            Iterator allGroups = getGroups(user).iterator();
            while (allGroups.hasNext()) {
                PortletGroup group = (PortletGroup) allGroups.next();
                if (!isUserInGroup(user, group)) {
                    groupsNotMemberOf.add(user);
                }
            }
        }
        return groupsNotMemberOf;
    }

    public PortletRole getRoleInGroup(User user, PortletGroup group) {
        //if (hasSuperRole(user)) {
        //    return PortletRole.SUPER;
        //} else {
        GroupEntry entry = getGroupEntry(user, group);
        if (entry == null) {
            return PortletRole.GUEST;
        }
        return entry.getRole();
        //}
    }

    public boolean hasRequiredRole(PortletRequest req, String portletId, boolean checkAdmin) {
        Map userGroups = (Map)req.getAttribute(SportletProperties.PORTLETGROUPS);

        boolean found = false;

        //Iterator it = aclService.getGroups().iterator();

        Iterator it = userGroups.keySet().iterator();
        while (it.hasNext()) {
            PortletGroup group = (PortletGroup)it.next();
            Set roleList = group.getPortletRoleList();
            Iterator roleIt = roleList.iterator();
            //System.err.println("group= " + group.getName());
            while (roleIt.hasNext()) {
                SportletRoleInfo roleInfo = (SportletRoleInfo)roleIt.next();
                //System.err.println("class= " + roleInfo.getPortletID());
                String pid = roleInfo.getPortletClass();

                if (pid.equals(portletId))  {
                    // check if user has this group
                    found = true;
                    //if (userGroups.containsKey(group)) {
                        //System.err.println("group= " + group.getName());
                        PortletRole usersRole = (PortletRole)userGroups.get(group);
                        //System.err.println("usersRole= " + usersRole);
                        PortletRole reqRole = getRoleByName(roleInfo.getRole());
                        //System.err.println("reqRole= " + reqRole);
                        if (usersRole.compare(usersRole, reqRole) >= 0) {
                            if (checkAdmin) {
                                if (usersRole.compare(usersRole, getAdminRole()) >= 0) {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        }
                    //}
                }
            }
        }
        return (!found);
    }

    public boolean hasRequiredRole(User user, String portletID, boolean checkAdmin) {
        // first check to see if portletID is in groups

        List userGroups = this.getGroups(user);
        boolean found = false;
        Iterator it = this.getGroups().iterator();
        while (it.hasNext()) {
            PortletGroup group = (PortletGroup)it.next();
            Set roleList = group.getPortletRoleList();
            Iterator roleIt = roleList.iterator();
            //System.err.println("group= " + group.getName());
            while (roleIt.hasNext()) {
                SportletRoleInfo roleInfo = (SportletRoleInfo)roleIt.next();
                //System.err.println("class= " + roleInfo.getPortletID());
                if (roleInfo.getPortletClass().equals(portletID)) {
                    // check if user has this group
                    found = true;
                    if (userGroups.contains(group)) {
                        //System.err.println("group= " + group.getName());
                        PortletRole usersRole = this.getRoleInGroup(user, group);
                        //System.err.println("usersRole= " + usersRole);
                        PortletRole reqRole = getRoleByName(roleInfo.getRole());
                        //System.err.println("reqRole= " + reqRole);
                        if (usersRole.compare(usersRole, reqRole) >= 0) {
                            if (checkAdmin) {
                                if (usersRole.compare(usersRole, getAdminRole()) >= 0) {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return (!found);
    }

    public void addGroupEntry(User user, PortletGroup group, PortletRole role) {
        GroupRequestImpl right = getGroupRequestImpl(user, group);
        if (right != null) {
            deleteGroupEntry(right);
        }
        right = new GroupRequestImpl();
        right.setUser(user);
        right.setGroup(group);
        right.setRole(role);
        saveGroupEntry(right);
    }

    public boolean hasRoleInGroup(User user, PortletGroup group, PortletRole role) {
        PortletRole test = getRoleInGroup(user, group);
        return test.equals(role);
    }

    public boolean hasAdminRoleInGroup(User user, PortletGroup group) {
        return hasRoleInGroup(user, group, getAdminRole());
    }

    public boolean hasUserRoleInGroup(User user, PortletGroup group) {
        return hasRoleInGroup(user, group, getUserRole());
    }

    public boolean hasGuestRoleInGroup(User user, PortletGroup group) {
        return hasRoleInGroup(user, group, getGuestRole());
    }

    public List getUsersWithSuperRole() {
        List users = getUsers(getCoreGroup());
        List supers = new Vector();
        Iterator it = users.iterator();
        while (it.hasNext()) {
            User u = (User) it.next();
            if (this.hasRoleInGroup(u, getCoreGroup(), getSuperRole())) {
                supers.add(u);
            }
        }
        return supers;
    }

    public void grantSuperRole(User user) {
        addGroupEntry(user, getCoreGroup(), getSuperRole());
    }

    public PortletRole getSuperRole() {
        return getRoleByName(PortletRole.SUPER.getName());
    }

    public PortletRole getAdminRole() {
        return getRoleByName(PortletRole.ADMIN.getName());
    }

    public PortletRole getUserRole() {
        return getRoleByName(PortletRole.USER.getName());
    }

    public PortletRole getGuestRole() {
        return getRoleByName(PortletRole.GUEST.getName());
    }

    public boolean hasSuperRole(User user) {
        return hasRoleInGroup(user, getCoreGroup(), getSuperRole());
    }

    /**
     * PORTLET ROLE METHODS
     */
    public List getRoles() {
            return this.getHibernateTemplate().find("select prole from " + PortletRole.class.getName() + " prole");
    }

    public void deleteRole(PortletRole role) {
        this.getHibernateTemplate().delete(role);
    }

    public PortletRole getRole(String roleId) {
        List roles = this.getHibernateTemplate().find("select prole from " + PortletRole.class.getName() + " prole where prole.oid='" + roleId + "'");
        if ((roles != null) && (!roles.isEmpty())) {
            return (PortletRole)roles.get(0);
        }
        return null;
    }

    public PortletRole getRoleByName(String roleName) {
        List roles = this.getHibernateTemplate().find("select prole from " + PortletRole.class.getName() + " prole where prole.Name='" + roleName + "'");
        if ((roles != null) && (!roles.isEmpty())) {
            return (PortletRole)roles.get(0);
        }
        return null;
    }

    public void saveRole(PortletRole role) {
        this.getHibernateTemplate().saveOrUpdate(role);
    }

    public void createRole(String roleName, int priority) {
        PortletRole role = new PortletRole(roleName, priority);
        this.getHibernateTemplate().saveOrUpdate(role);
    }

}
