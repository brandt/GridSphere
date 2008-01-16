/*
 *
 * @version: $Id$
 */
package org.gridsphere.services.core.security.group.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.security.group.GroupManagerService;
import org.gridsphere.services.core.security.group.PortletGroup;
import org.gridsphere.services.core.user.User;

import java.util.List;
import java.util.Vector;

public class GroupManagerServiceImpl implements PortletServiceProvider, GroupManagerService {

    private Log log = LogFactory.getLog(GroupManagerServiceImpl.class);

    private PersistenceManagerRdbms pm = null;

    private String jdoGroupRequest = UserGroup.class.getName();
    private String jdoPortletGroup = PortletGroup.class.getName();

    public GroupManagerServiceImpl() {

    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        PersistenceManagerService pmservice = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pm = pmservice.createGridSphereRdbms();
    }

    public void destroy() {
        log.info("Calling destroy()");
    }

    public UserGroup getUserGroup(User user, PortletGroup group) {
        if (user == null) throw new IllegalArgumentException("user cannot be null!");
        if (group == null) throw new IllegalArgumentException("group cannot be null!");
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

    public void saveUserGroup(UserGroup entry) {
        // Create or update access right
        try {
            pm.saveOrUpdate(entry);
        } catch (PersistenceManagerException e) {
            String msg = "Error creating access right";
            log.error(msg, e);
        }
    }

    public void deleteUserGroup(UserGroup userGroup) {
        try {
            pm.delete(userGroup);
        } catch (PersistenceManagerException e) {
            String msg = "Error deleting access right";
            log.error(msg, e);
        }
    }

    public void deleteUserInGroup(User user, PortletGroup group) {
        UserGroup ug = getUserGroup(user, group);
        if (ug != null) deleteUserGroup(ug);

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

    public PortletGroup getGroup(String name) {
        if (name == null) throw new IllegalArgumentException("group name cannot be null!");
        return selectPortletGroup("where grp.Name='" + name + "'");
    }

    private PortletGroup selectPortletGroup(String criteria) {
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
            return (PortletGroup) pm.restore(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving portlet group";
            log.error(msg, e);
            return null;
        }
    }

    public boolean existsGroupWithName(String groupName) {
        return (getGroup(groupName) != null);
    }

    public void saveGroup(PortletGroup portletGroup) {
        if (portletGroup == null) throw new IllegalArgumentException("group cannot be null!");
        try {
            pm.saveOrUpdate(portletGroup);
        } catch (PersistenceManagerException e) {
            String msg = "Error saving/updating portlet group " + portletGroup.getName();
            log.error(msg, e);
        }
    }

    public void deleteGroup(PortletGroup group) {
        if (group == null) throw new IllegalArgumentException("group cannot be null!");
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
        return (getUserGroup(user, group) != null);
    }

    public List getGroups(User user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null!");
        String oql = "select groupRequest.group from "
                + jdoGroupRequest
                + " groupRequest where groupRequest.user.oid='"
                + user.getID()
                + "'";
        try {
            return pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving access right";
            log.error(msg, e);
            return new Vector();
        }
    }

    public void addUserToGroup(User user, PortletGroup group) {
        UserGroup ug = getUserGroup(user, group);
        if (ug == null) {
            ug = new UserGroup();
            ug.setUser(user);
            ug.setGroup(group);
            saveUserGroup(ug);
        }
    }

}
