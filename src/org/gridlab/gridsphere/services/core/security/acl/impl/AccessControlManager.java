/*
 *
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.security.acl.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletRoleInfo;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;

import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;
import org.gridlab.gridsphere.services.core.security.acl.*;
import org.gridlab.gridsphere.services.core.user.impl.*;
import org.gridlab.gridsphere.services.core.user.impl.GroupEntryImpl;
import org.gridlab.gridsphere.services.core.user.impl.GroupRequestImpl;
import org.gridlab.gridsphere.services.core.user.impl.UserManager;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;

import java.util.*;

public class AccessControlManager implements AccessControlManagerService {

    private static PortletLog log = SportletLog.getInstance(AccessControlManager.class);
    private static AccessControlManager instance = null;
    private static UserManager userManager = null;
    private PortletManager pms = null;
    private PortletRegistry registry = null;
    private PersistenceManagerRdbms pm = PersistenceManagerFactory.createGridSphereRdbms();

    private String jdoGroupRequest = GroupRequestImpl.class.getName();
    private String jdoGroupEntry = GroupEntryImpl.class.getName();
    private String jdoPortletGroup = SportletGroup.class.getName();

    private static boolean isInitialized = false;

    private AccessControlManager() {
        registry = PortletRegistry.getInstance();
        pms = PortletManager.getInstance();
        userManager = UserManager.getInstance();
        //initGroups();
    }

    public static void main(String[] args) throws Exception  {
        AccessControlManager gum = AccessControlManager.getInstance();
        gum.pm = PersistenceManagerFactory.createGridSphereRdbms();
        gum.initSportletGroup((SportletGroup)SportletGroup.SUPER);
        gum.initSportletGroup((SportletGroup)PortletGroupFactory.GRIDSPHERE_GROUP);
    }

    public static synchronized AccessControlManager getInstance() {
        if (instance == null) {
            instance = new AccessControlManager();
        }
        return instance;
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        if (!isInitialized) {
            initGroups();
        }
        isInitialized = true;
    }

    private void initGroups() {
        log.info("Entering initGroups()");

        initSportletGroup((SportletGroup)SportletGroup.SUPER);
        initSportletGroup((SportletGroup)PortletGroupFactory.GRIDSPHERE_GROUP);

        List webappNames = pms.getPortletWebApplicationNames();
        Iterator it = webappNames.iterator();
        while (it.hasNext()) {
            String groupName = (String)it.next();
            System.err.println(groupName);
            if (!existsGroupWithName(groupName)) {
                log.info("creating group " + groupName);
                registry.getAllConcretePortletIDs();
                createGroup(groupName);
            }
        }
        // Creating groups
        log.info("Entering initGroups()");
    }

    private void initSportletGroup(SportletGroup group) {
        String groupName = group.getName();
        if (!existsGroupWithName(groupName)) {
            try {
                log.info("Creating group...." + groupName);
                pm.create(group);
            } catch (Exception e) {
                log.error("Error creating group " + groupName, e);
            }
        } else {
            try {
                log.info("Resetting group...." + groupName);
                SportletGroup realGroup = this.getSportletGroupByName(groupName);
                group.setID(realGroup.getID());
            } catch (Exception e) {
                log.error("Error resetting group " + groupName, e);
            }
        }
    }


    public void destroy() {
        log.info("Calling destroy()");
    }

    public List getGroupRequests() {
        String criteria = "";
        return selectGroupRequests(criteria);
    }

    public List getGroupRequests(User user) {
        String criteria = "where groupRequest.sportletUser.oid='" + user.getID() + "'";
        return selectGroupRequests(criteria);
    }

    public List getGroupRequests(PortletGroup group) {
        String criteria = "where groupRequest.sportletGroup.oid='" + group.getID() + "'";
        return selectGroupRequests(criteria);
    }

    public List getGroupRequestsForGroups(List groups) {
        List sumGroupRequests = null;
        for (int ii = 0; ii < groups.size(); ++ii) {
            PortletGroup group = (PortletGroup)groups.get(ii);
            List groupRequests = getGroupRequests(group);
            sumGroupRequests.add(groupRequests);
        }
        return sumGroupRequests;
    }

    public List selectGroupRequests(String criteria) {
        String oql = "select groupRequest from "
                   + jdoGroupRequest
                   + " groupRequest "
                   + criteria;
        try {
            return pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving account request";
            log.error(msg, e);
            return new Vector();
        }
    }

    public GroupRequest getGroupRequest(String id) {
        return getGroupRequestImpl(id);
    }

    private GroupRequestImpl getGroupRequestImpl(String id) {
        String criteria = "where groupRequest.oid='" + id + "'";
        return selectGroupRequestImpl(criteria);
    }

    public GroupRequestImpl selectGroupRequestImpl(String criteria) {
        String oql = "select groupRequest from "
                   + jdoGroupRequest
                   + " groupRequest "
                   + criteria;
        try {
            return (GroupRequestImpl)pm.restore(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving account request";
            log.error(msg, e);
            return null;
        }
    }

    private boolean existsGroupRequest(GroupRequest request) {
       GroupRequestImpl requestImpl = (GroupRequestImpl)request;
       String oql = "select groupRequest.oid from "
                  + jdoGroupRequest
                  + " groupRequest where groupRequest.oid='"
                  + requestImpl.getOid() + "'";
       try {
           return (pm.restore(oql) != null);
       } catch (PersistenceManagerException e) {
           String msg = "Error retrieving account request";
           log.error(msg, e);
       }
       return false;
    }

    private void deleteGroupRequest(GroupRequest request) {
        try {
            pm.delete(request);
        } catch (PersistenceManagerException e) {
            String msg = "Error deleting access request";
            log.error(msg, e);
        }
    }

    public GroupRequest createGroupRequest() {
        GroupRequest request = new GroupRequestImpl();
        return createNewGroupRequest(request);
    }

    public GroupRequest createGroupRequest(GroupEntry groupEntry) {
        GroupRequest request = new GroupRequestImpl(groupEntry);
        return createNewGroupRequest(request);
    }

    private GroupRequest createNewGroupRequest(GroupRequest request) {
        try {
            pm.create(request);
        } catch (PersistenceManagerException e) {
            String msg = "Error saving group request";
            log.error(msg, e);
        }
        return request;
    }

    public void submitGroupRequest(GroupRequest request)
            throws InvalidGroupRequestException {
        if (request instanceof GroupRequestImpl) {
            // First validate accesss request
            //validateGroupRequest(request);
            // Then save account request if not already saved
            if (existsGroupRequest(request)) {
                try {
                    pm.update(request);
                } catch (PersistenceManagerException e) {
                    String msg = "Error saving group request";
                    log.error(msg, e);
                }
            }
            // Send message if not null
        }
    }

    public void validateGroupRequest(GroupRequest request)
            throws InvalidGroupRequestException {
        PortletGroup group = request.getGroup();
        PortletRole role = request.getRole();
        // If role is super but group isn't, throw invalid access request exception
       // if (role.equals(PortletRole.SUPER) && (! group.equals(SportletGroup.SUPER) )) {
        //    String msg = "Super role can only exist in super group.";
        //    log.info(msg);
         //   throw new InvalidGroupRequestException(msg);
        // If group is super but role isn't, throw invalid access request exception
        // } else if (group.equals(SportletGroup.SUPER) && (!role.equals(PortletRole.SUPER) )) {
         //   String msg = "Super group can only contain super role.";
          //  log.info(msg);
          //  throw new InvalidGroupRequestException(msg);
        //} else

        if (! (role.equals(PortletRole.ADMIN) ||
                      role.equals(PortletRole.USER)  ||
                      role.equals(PortletRole.GUEST) )) {
            String msg = "Portlet role [" + role + "] not recognized.";
            log.info(msg);
            throw new InvalidGroupRequestException(msg);
        }
    }

    public void approveGroupRequest(GroupRequest request) {
        if (request instanceof GroupRequestImpl) {
            GroupRequestImpl requestImpl = (GroupRequestImpl)request;
            // Get request attributes
            User user = requestImpl.getUser();
            PortletGroup group = requestImpl.getGroup();
            GroupAction action = requestImpl.getGroupAction();
            PortletRole role = request.getRole();
            // Delete group request
            deleteGroupRequest(request);
            // Perform requested action
            if (action.equals(GroupAction.ADD)) {
                // Add user to group
                addGroupEntry(user, group, role);
           } else if (action.equals(GroupAction.EDIT)) {
                // Get associated entry
                GroupEntryImpl entryImpl = requestImpl.getGroupEntry();
                // Edit user role in group
                entryImpl.setRole(role);
                // Update associated entry
                saveGroupEntry(entryImpl);
            } else {
               // Get associated entry
                GroupEntryImpl entryImpl = requestImpl.getGroupEntry();
                // Delete associated entry
                deleteGroupEntry(entryImpl);
            }
            // Send message if not null
        }
    }

    public void denyGroupRequest(GroupRequest request) {
        if (request instanceof GroupRequestImpl) {
            // Delete account request
            deleteGroupRequest(request);
            // Send message if not null
        }
    }

    public List getGroupEntries() {
        String criteria = "";
        return selectGroupEntries(criteria);
    }

    public List getGroupEntries(User user) {
        //if (hasSuperRole(user)) {
        //    return new Vector();
        //} else {
            String criteria = "where groupEntry.sportletUser.oid='" + user.getID() + "'";
            return selectGroupEntries(criteria);
        //}
    }

    public List getGroupEntries(PortletGroup group) {
        String criteria = "where groupEntry.sportletGroup.oid='" + group.getID() + "'";
        return selectGroupEntries(criteria);
    }

    private List selectGroupEntries(String criteria) {
        String oql = "select groupEntry from "
                   + jdoGroupEntry
                   + " groupEntry "
                   + criteria;
        try {
            return pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving access right";
            log.error(msg, e);
            return new Vector();
        }
    }

    public GroupEntry getGroupEntry(String id) {
        String criteria = "where groupEntry.oid='" + id + "'";
        return selectGroupEntryImpl(criteria);
    }

    public GroupEntry getGroupEntry(User user, PortletGroup group) {
        return getGroupEntryImpl(user, group);
    }

    private GroupEntryImpl getGroupEntryImpl(User user, PortletGroup group) {
        String criteria = " where groupEntry.sportletUser.oid='" + user.getID() + "'"
                       +  " and groupEntry.sportletGroup.oid='" + group.getID() + "'";
        return selectGroupEntryImpl(criteria);
    }

    private GroupEntryImpl selectGroupEntryImpl(String criteria) {
        String oql = "select groupEntry from "
                   + jdoGroupEntry
                   + " groupEntry "
                   + criteria;
        try {
            return (GroupEntryImpl)pm.restore(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving access right";
            log.error(msg, e);
            return null;
        }
    }

   private boolean existsGroupEntry(GroupEntry entry) {
       GroupEntryImpl rightImpl = (GroupEntryImpl)entry;
       String oql = "select groupEntry.oid from "
                  + jdoGroupEntry
                  + " groupEntry where groupEntry.oid='"
                  + rightImpl.getOid() + "'";
       try {
           return (pm.restore(oql) != null);
       } catch (PersistenceManagerException e) {
           String msg = "Error retrieving access right";
           log.error(msg, e);
       }
       return false;
    }

    private void saveGroupEntry(GroupEntry entry) {
        // Create or update access right
        if (existsGroupEntry(entry)) {
            try {
                pm.update(entry);
            } catch (PersistenceManagerException e) {
                String msg = "Error creating access right";
                log.error(msg, e);
            }
        } else {
            try {
                pm.create(entry);
            } catch (PersistenceManagerException e) {
                String msg = "Error creating access right";
                log.error(msg, e);
            }
        }
    }

    public void deleteGroupEntry(GroupEntry entry) {
        try {
            pm.delete(entry);
        } catch (PersistenceManagerException e) {
            String msg = "Error deleting access right";
            log.error(msg, e);
        }
    }

    public void deleteGroupEntries(User user) {
        Iterator groupEntries = getGroupEntries(user).iterator();
        while (groupEntries.hasNext()) {
            GroupEntry groupEntry = (GroupEntry)groupEntries.next();
            deleteGroupEntry(groupEntry);
        }
    }

    public List getGroups() {
        return selectGroups("");
    }

    public List selectGroups(String criteria) {
        // Build object query
        StringBuffer oqlBuffer = new StringBuffer();
        oqlBuffer.append("select grp from ");
        oqlBuffer.append(jdoPortletGroup);
        oqlBuffer.append(" grp ");
        // Note, we don't return super groups
        if (criteria.equals("")) {
            oqlBuffer.append(" where ");
        } else {
            oqlBuffer.append(criteria);
            oqlBuffer.append(" and ");
        }
        oqlBuffer.append("grp.oid !='");
        oqlBuffer.append(getSuperGroup().getID());
        oqlBuffer.append("'");
        // Generate object query
        String oql = oqlBuffer.toString();
        // Execute query
        try {
            return pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving portlet groups";
            log.error(msg, e);
            return new Vector();
        }
    }

    public PortletGroup getGroup(String id) {
        return selectSportletGroup("where grp.oid='" + id + "'");
    }

    private PortletGroup getSuperGroup() {
        return SportletGroup.SUPER;
    }

    public PortletGroup getGroupByName(String name) {
        return getSportletGroupByName(name);
    }

    public String getGroupDescription(PortletGroup group) {
        return pms.getPortletWebApplicationDescription(group.getName());
    }
    private SportletGroup getSportletGroupByName(String name) {
        return selectSportletGroup("where grp.Name='" + name + "'");
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
        try {
            return (SportletGroup)pm.restore(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving portlet group";
            log.error(msg, e);
            return null;
        }
    }

    public boolean existsGroupWithName(String groupName) {
        return (getGroupByName(groupName) != null);
    }

    public PortletGroup createGroup(String groupName) {
        SportletGroup group = getSportletGroupByName(groupName);
        if (group == null) {
            group = new SportletGroup();
            group.setName(groupName);
            group.setPublic(true);
            try {
                pm.create(group);
            } catch (PersistenceManagerException e) {
                String msg = "Error creating portlet group " + groupName;
                log.error(msg, e);
            }
        }
        return group;
    }

    public PortletGroup createGroup(String groupName, Set portletRoleList) {
        SportletGroup group = getSportletGroupByName(groupName);
        if (group == null) {
            group = new SportletGroup();
            group.setName(groupName);
            group.setPublic(true);
            Iterator it = portletRoleList.iterator();
            while (it.hasNext()) {
                SportletRoleInfo info = (SportletRoleInfo)it.next();
                System.err.println("role= " + info.getRole() + " class=" + info.getPortletClass());
            }
            group.setPortletRoleList(portletRoleList);
            try {
                pm.create(group);
            } catch (PersistenceManagerException e) {
                String msg = "Error creating portlet group " + groupName;
                log.error(msg, e);
            }
        }
        return group;
    }

    public void deleteGroup(PortletGroup group) {
        try {
            pm.delete(group);
        } catch (PersistenceManagerException e) {
            String msg = "Error deleting portlet group";
            log.error(msg, e);
        }
    }

    public List getUsers(PortletGroup group) {
        String oql = "select groupEntry.sportletUser from "
                   + jdoGroupEntry
                   + " groupEntry where groupEntry.sportletGroup.oid='"
                   + group.getID()
                   + "'";
        try {
            return pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving access right";
            log.error(msg, e);
            return new Vector();
        }
    }

    public List getUsers(PortletGroup group, PortletRole role) {
        List users = this.getUsers(group);
        Iterator it = users.iterator();
        List l = new Vector();
        while (it.hasNext()) {
            User u = (User)it.next();
            System.err.println("Checking if " + u.getFullName() + " has " + role);
            if (this.hasRoleInGroup(u, group, role)) {
                System.err.println("user has role in group" + u.getFullName() + " " + u.getEmailAddress());
                l.add(u);
            }
        }
        return l;
    }

    public void modifyGroupAccess(PortletGroup group, boolean isPublic) {
        SportletGroup g = this.getSportletGroupByName(group.getName());
        g.setPublic(isPublic);
        try {
            pm.update(g);
        } catch (PersistenceManagerException e) {
            String msg = "Error updating portlet group";
            log.error(msg, e);
        }
    }

    public List getUsersNotInGroup(PortletGroup group) {
        List usersNotInGroup = new Vector();
        Iterator allUsers = userManager.getUsers().iterator();
        while (allUsers.hasNext()) {
            User user = (User)allUsers.next();
            // If user has super role, don't include
         //   if (hasSuperRole(user)) {
           //     continue;
          //  }
            // Else, if user not in group, then include
            if (!isUserInGroup(user, group)) {
                usersNotInGroup.add(user);
            }
        }
        return usersNotInGroup;
    }

    public boolean isUserInGroup(User user, PortletGroup group) {
        return (getGroupEntry(user, group) != null);
    }

    public List getGroups(User user) {
        List groups = null;
        // If user has super role
        //if (hasSuperRole(user)) {
        //    groups = getGroups();
        //} else {
            // Otherwise, return groups for given user
            String oql = "select groupEntry.sportletGroup from "
                       + jdoGroupEntry
                       + " groupEntry where groupEntry.sportletUser.oid='"
                       + user.getID()
                       + "'";
            try {
                groups = pm.restoreList(oql);
            } catch (PersistenceManagerException e) {
                String msg = "Error retrieving access right";
                log.error(msg, e);
                return new Vector();
            }
       // }
        return groups;
    }

    public List getGroupsNotMemberOf(User user) {
        List groupsNotMemberOf = new Vector();
        if (!hasSuperRole(user)) {
            Iterator allGroups = getGroups(user).iterator();
            while (allGroups.hasNext()) {
                PortletGroup group = (PortletGroup)allGroups.next();
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

    public void addGroupEntry(User user, PortletGroup group, PortletRole role) {
        GroupEntryImpl right = getGroupEntryImpl(user, group);
        if (right != null) {
            deleteGroupEntry(right);
        }
        right = new GroupEntryImpl();
        right.setUser(user);
        right.setGroup(group);
        right.setRole(role);
        saveGroupEntry(right);
    }

    private void removeGroupEntry(User user, PortletGroup group) {
        GroupEntry entry = getGroupEntry(user, group);
        if (entry != null) {
            log.debug("Deleting group entry " + entry.getID());
            deleteGroupEntry(entry);
        }
    }

    public boolean hasRoleInGroup(User user, PortletGroup group, PortletRole role) {
        PortletRole test = getRoleInGroup(user, group);
        return test.equals(role);
    }

    public boolean hasAdminRoleInGroup(User user, PortletGroup group) {
        return hasRoleInGroup(user, group, PortletRole.ADMIN);
    }

    public boolean hasUserRoleInGroup(User user, PortletGroup group) {
        return hasRoleInGroup(user, group, PortletRole.USER);
    }

    public boolean hasGuestRoleInGroup(User user, PortletGroup group) {
        return hasRoleInGroup(user, group, PortletRole.GUEST);
    }

    public List getUsersWithSuperRole() {
        List users = getUsers(SportletGroup.CORE);
        List supers = new Vector();
        Iterator it = users.iterator();
        while (it.hasNext()) {
            User u = (User)it.next();
            if (this.hasRoleInGroup(u, SportletGroup.CORE, PortletRole.SUPER)) {
                supers.add(u);
            }
        }
        return supers;
    }

    public void grantSuperRole(User user) {
        //addGroupEntry(user, SportletGroup.SUPER, PortletRole.SUPER);
        addGroupEntry(user, SportletGroup.CORE, PortletRole.SUPER);
    }

    public boolean hasSuperRole(User user) {
        return hasRoleInGroup(user, SportletGroup.CORE, PortletRole.SUPER);
        //isUserInGroup(user, SportletGroup.SUPER);
    }
}
