/*
 *
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.security.role.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.security.role.RoleManagerService;

import java.util.*;

public class RoleManagerServiceImpl implements PortletServiceProvider, RoleManagerService {

    private static PortletLog log = SportletLog.getInstance(RoleManagerServiceImpl.class);

    private static PersistenceManagerRdbms pm = null;

    private String jdoUserRoles = UserRole.class.getName();

    public RoleManagerServiceImpl() {

    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        pm = PersistenceManagerFactory.createGridSphereRdbms();
    }

    public void destroy() {
        log.info("Calling destroy()");
    }

    public boolean isUserInRole(User user, PortletRole role) {
        return (getUserRole(user, role) != null);
    }

    public List getRolesForUser(User user) {
        if (user == null) throw new IllegalArgumentException("user can't be null");
        List roles = null;
        String oql = "select userRole.role from "
                + jdoUserRoles
                + " userRole where userRole.user.oid='" + user.getID() + "'";
        try {
            roles = (List)pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving user role";
            log.error(msg, e);
        }
        if (roles == null) {
            roles = new ArrayList();
        }
        return roles;
    }

    public List getUsersInRole(PortletRole role) {
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        List users = null;
        String oql = "select userRole.user from "
                + jdoUserRoles
                + " userRole where userRole.role.Name='" + role.getName() + "'";
        try {
            users = (List)pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving user role";
            log.error(msg, e);
        }
        return users;
    }

    public void addUserToRole(User user, PortletRole role) {
        if (user == null) throw new IllegalArgumentException("user cannot be null!");
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        UserRole userRole = new UserRole();
        if (role.getOid() == null) role = getRoleByName(role.getName());
        userRole.setRole(role);
        userRole.setUser(user);
        try {
            pm.saveOrUpdate(userRole);
        } catch (PersistenceManagerException e) {
            String msg = "Error saving user role";
            log.error(msg, e);
        }
    }

    public void deleteUserInRole(User user, PortletRole role) {
        UserRole userRole = getUserRole(user, role);
        try {
            if (userRole != null) pm.delete(userRole);
        } catch (PersistenceManagerException e) {
            String msg = "Error saving user role";
            log.error(msg, e);
        }
    }

    private UserRole getUserRole(User user, PortletRole role) {
        if (user == null) throw new IllegalArgumentException("user cannot be null!");
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        UserRole userRole = null;
        String oql = "select userRole from "
                + jdoUserRoles
                + " userRole where userRole.user.oid='" + user.getID() + "'"
                + " and userRole.role.Name='" + role.getName() + "'";
        try {
            userRole = (UserRole)pm.restore(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error saving user role";
            log.error(msg, e);
        }
        return userRole;
    }

    public List getRoles() {
        List roles = null;
        try {
            roles = pm.restoreList("select prole from " + PortletRole.class.getName() + " prole");
        } catch (PersistenceManagerException e) {
            log.error("Error deleting role", e);
        }
        return roles;
    }

    public void deleteRole(PortletRole role) {
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        try {
            pm.delete(role);
        } catch (PersistenceManagerException e) {
            log.error("Error deleting role", e);
        }
    }

    public PortletRole getRole(String roleId) {
        if (roleId == null) throw new IllegalArgumentException("role id cannot be null!");
        PortletRole role = null;
        try {
            role = (PortletRole)pm.restore("select prole from " + PortletRole.class.getName() + " prole where prole.oid='" + roleId + "'");
        } catch (PersistenceManagerException e) {
            log.error("Error retrieving role " + roleId, e);
        }
        return role;
    }

    public PortletRole getRoleByName(String roleName) {
        if (roleName == null) throw new IllegalArgumentException("role name cannot be null!");
        PortletRole role = null;
        try {
            role = (PortletRole)pm.restore("select prole from " + PortletRole.class.getName() + " prole where prole.Name='" + roleName + "'");
        } catch (PersistenceManagerException e) {
            log.error("Error retrieving role " + roleName, e);
        }
        return role;
    }

    public void saveRole(PortletRole role) {
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        try {
            pm.saveOrUpdate(role);
        } catch (PersistenceManagerException e) {
            String msg = "Error saving portlet role: ";
            log.error(msg, e);
        }
    }

}
