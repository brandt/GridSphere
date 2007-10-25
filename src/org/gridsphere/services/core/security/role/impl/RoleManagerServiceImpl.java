/*
 *
 * @version: $Id: RoleManagerServiceImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.security.role.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.persistence.QueryFilter;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.security.role.RoleManagerService;
import org.gridsphere.services.core.user.User;
import org.gridsphere.services.core.user.impl.UserImpl;

import java.util.ArrayList;
import java.util.List;

public class RoleManagerServiceImpl implements PortletServiceProvider, RoleManagerService {

    private Log log = LogFactory.getLog(RoleManagerServiceImpl.class);

    private PersistenceManagerRdbms pm = null;

    private String jdoUserRoles = UserRole.class.getName();
    private String jdoUser = UserImpl.class.getName();

    public RoleManagerServiceImpl() {
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        PersistenceManagerService pmservice = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pm = pmservice.createGridSphereRdbms();

        // create user role if none exists
        PortletRole userRole = getRole("USER");
        if (userRole == null) {
            userRole = new PortletRole();
            userRole.setName("USER");
            userRole.setIsDefault(1);
            userRole.setDescription("portal user");
            saveRole(userRole);
        }

        // create admin role if none exists
        PortletRole adminRole = getRole("ADMIN");
        if (adminRole == null) {
            adminRole = new PortletRole();
            adminRole.setName("ADMIN");
            adminRole.setDescription("portal administrator");
            adminRole.setIsDefault(0);
            saveRole(adminRole);
        }
    }

    public void destroy() {
        log.info("Calling destroy()");
    }

    public boolean isUserInRole(User user, PortletRole role) {
        return (getUserRole(user, role) != null);
    }

    public int getNumUsersInRole(PortletRole role) {
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        String oql = "select count(*) from "
                + this.jdoUserRoles;
        return pm.count(oql);

    }

    public List<PortletRole> getRolesForUser(User user) {
        if (user == null) throw new IllegalArgumentException("user can't be null");
        List<PortletRole> roles = null;
        String oql = "select userRole.role from "
                + jdoUserRoles
                + " userRole where userRole.user.oid='" + user.getID() + "'";
        roles = pm.restoreList(oql);
        return (roles != null) ? roles : new ArrayList<PortletRole>();
    }

    public List<User> getUsersInRole(PortletRole role) {
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        List<User> users = null;
        String oql = "select userRole.user from "
                + jdoUserRoles
                + " userRole where userRole.role.Name='" + role.getName() + "'";
        users = pm.restoreList(oql);
        System.err.println(oql);
        return (users != null) ? users : new ArrayList<User>();
    }


    public List<User> getUsersInRole(PortletRole role, QueryFilter filter) {
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        if (filter == null) throw new IllegalArgumentException("query filter cannot be null!");
        List<User> users = null;
        String oql = "select userRole.user from "
                + jdoUserRoles
                + " userRole where userRole.role.Name='" + role.getName() + "'";

        users = (List<User>) pm.restoreList(oql, filter);
        return (users != null) ? users : new ArrayList<User>();
    }

    public List<User> getUsersNotInRole(PortletRole role, QueryFilter filter) {
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        if (filter == null) throw new IllegalArgumentException("query filter cannot be null!");
        List<User> users = null;

        String oql = "select uzer from "
                + this.jdoUser
                + " uzer left join fetch userRole.user where userRole.role.Name!='" + role.getName() + "'";
        users = (List<User>) pm.restoreList(oql, filter);
        return (users != null) ? users : new ArrayList<User>();
    }

    public void addUserToRole(User user, PortletRole role) {
        if (user == null) throw new IllegalArgumentException("user cannot be null!");
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        UserRole userRole = new UserRole();
        if (role.getOid() == null) role = getRole(role.getName());
        if (!isUserInRole(user, role)) {
            userRole.setRole(role);
            userRole.setUser(user);
            pm.saveOrUpdate(userRole);
        }
    }

    public void deleteUserInRole(User user, PortletRole role) {
        UserRole userRole = getUserRole(user, role);
        if (userRole != null) pm.delete(userRole);
    }

    private UserRole getUserRole(User user, PortletRole role) {
        if (user == null) throw new IllegalArgumentException("user cannot be null!");
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        UserRole userRole = null;
        String oql = "select userRole from "
                + jdoUserRoles
                + " userRole where userRole.user.oid='" + user.getID() + "'"
                + " and userRole.role.Name='" + role.getName() + "'";
        userRole = (UserRole) pm.restore(oql);
        return userRole;
    }

    public List<PortletRole> getRoles() {
        List<PortletRole> roles = null;
        roles = pm.restoreList("select prole from " + PortletRole.class.getName() + " prole");
        return (roles != null) ? roles : new ArrayList<PortletRole>();
    }

    public void deleteRole(PortletRole role) {
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        pm.delete(role);
    }

    public PortletRole getRole(String roleName) {
        if (roleName == null) throw new IllegalArgumentException("role name cannot be null!");
        return (PortletRole) pm.restore("select prole from " + PortletRole.class.getName() + " prole where prole.Name='" + roleName + "'");
    }

    public void saveRole(PortletRole role) {
        if (role == null) throw new IllegalArgumentException("role cannot be null!");
        pm.saveOrUpdate(role);
    }

    public List<PortletRole> getDefaultRoles() {
        List<PortletRole> roles = null;
        roles = pm.restoreList("select prole from " + PortletRole.class.getName() + " prole where prole.IsDefault=1");
        return (roles != null) ? roles : new ArrayList<PortletRole>();
    }

    public void addDefaultRole(PortletRole role) {
        PortletRole myrole = getRole(role.getName());
        if (myrole != null) {
            myrole.setIsDefault(1);
            saveRole(myrole);
        }
    }

    public void removeDefaultRole(PortletRole role) {
        PortletRole myrole = getRole(role.getName());
        if (myrole != null) {
            myrole.setIsDefault(0);
            saveRole(myrole);
        }
    }
}
