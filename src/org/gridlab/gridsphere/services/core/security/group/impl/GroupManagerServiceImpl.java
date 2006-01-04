/*
 *
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.security.group.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.security.group.GroupManagerService;

import java.util.*;

public class GroupManagerServiceImpl implements PortletServiceProvider, GroupManagerService {

    private static PortletLog log = SportletLog.getInstance(GroupManagerServiceImpl.class);

    private static PersistenceManagerRdbms pm = null;

    private String jdoGroupRequest = UserGroup.class.getName();
    private String jdoPortletGroup = SportletGroup.class.getName();

    public GroupManagerServiceImpl() {

    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        pm = PersistenceManagerFactory.createGridSphereRdbms();
    }

    public void destroy() {
        log.info("Calling destroy()");
    }

    public UserGroup createGroupEntry() {
        UserGroup request = new UserGroup();
        this.saveGroupEntry(request);
        return request;
    }

    public UserGroup editGroupEntry(UserGroup groupEntry) {
        return (UserGroup) this.getGroupEntry(groupEntry.getID());
    }

    public List getGroupEntries() {
        String criteria = "";
        return selectGroupEntries(criteria);
    }

    public List getGroupEntries(User user) {
        String criteria = "where groupRequest.user.oid='" + user.getID() + "'";
        return selectGroupEntries(criteria);
    }

    public List getGroupEntries(PortletGroup group) {
        String criteria = "where groupRequest.group.oid='" + group.getID() + "'";
        return selectGroupEntries(criteria);
    }

    private List selectGroupEntries(String criteria) {
        String oql = "select groupRequest from "
                + jdoGroupRequest
                + " groupRequest "
                + criteria;
        try {
            return pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving access right";
            log.error(msg, e);
            return new ArrayList();
        }
    }

    public UserGroup getGroupEntry(String id) {
        String criteria = "where groupRequest.oid='" + id + "'";
        return selectGroupRequestImpl(criteria);
    }

    public UserGroup getGroupEntry(User user, PortletGroup group) {
        return getGroupRequestImpl(user, group);
    }

    private UserGroup getGroupRequestImpl(User user, PortletGroup group) {
        String criteria = " where groupRequest.user.oid='" + user.getID() + "'"
                + " and groupRequest.group.oid='" + group.getID() + "'";
        return selectGroupRequestImpl(criteria);
    }

    private UserGroup selectGroupRequestImpl(String criteria) {
        String oql = "select groupRequest from "
                + jdoGroupRequest
                + " groupRequest "
                + criteria;
        try {
            return (UserGroup) pm.restore(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving access right";
            log.error(msg, e);
            return null;
        }
    }

    public void saveGroupEntry(UserGroup entry) {
        // Create or update access right
        try {
            pm.saveOrUpdate(entry);
        } catch (PersistenceManagerException e) {
            String msg = "Error creating access right";
            log.error(msg, e);
        }
    }

    public void deleteGroupEntry(UserGroup entry) {
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
            UserGroup groupEntry = (UserGroup) groupEntries.next();
            deleteGroupEntry(groupEntry);
        }
    }

    public List getGroups() {
        // Execute query
        try {
            return pm.restoreList("select grp from " + jdoPortletGroup + " grp ");
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving portlet groups";
            log.error(msg, e);
            return new Vector();
        }
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
        try {
            return (SportletGroup) pm.restore(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving portlet group";
            log.error(msg, e);
            return null;
        }
    }

    public boolean existsGroupWithName(String groupName) {
        return (getGroupByName(groupName) != null);
    }

    public PortletGroup createGroup(SportletGroup portletGroup) {
        try {
            pm.saveOrUpdate(portletGroup);
        } catch (PersistenceManagerException e) {
            String msg = "Error creating portlet group " + portletGroup.getName();
            log.error(msg, e);
        }
        return portletGroup;
    }


    public void deleteGroup(PortletGroup group) {
        try {
            pm.delete(group);
        } catch (PersistenceManagerException e) {
            String msg = "Error deleting portlet group";
            log.error(msg, e);
        }
    }

    public List getUsersInGroup(PortletGroup group) {
        String oql = "select groupRequest.user from "
                + jdoGroupRequest
                + " groupRequest where groupRequest.group.oid='"
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

    public boolean isUserInGroup(User user, PortletGroup group) {
        return (getGroupEntry(user, group) != null);
    }

    public List getGroups(User user) {
        List groups;
        // If user has super role
        //if (hasSuperRole(user)) {
        //    groups = getGroups();
        //} else {
        // Otherwise, return groups for given user
        String oql = "select groupRequest.group from "
                + jdoGroupRequest
                + " groupRequest where groupRequest.user.oid='"
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
        Iterator allGroups = getGroups(user).iterator();
        while (allGroups.hasNext()) {
            PortletGroup group = (PortletGroup) allGroups.next();
            if (!isUserInGroup(user, group)) {
                groupsNotMemberOf.add(user);
            }
        }
        return groupsNotMemberOf;
    }

    public void addGroupEntry(User user, PortletGroup group, PortletRole role) {
        UserGroup right = getGroupRequestImpl(user, group);
        if (right != null) {
            deleteGroupEntry(right);
        }
        right = new UserGroup();
        right.setUser(user);
        right.setGroup(group);
        saveGroupEntry(right);
    }


    public void addUserToGroup(User user, PortletGroup group) {
        UserGroup right = getGroupRequestImpl(user, group);
        if (right != null) {
            deleteGroupEntry(right);
        }
        right = new UserGroup();
        right.setUser(user);
        right.setGroup(group);
        saveGroupEntry(right);
    }

}
