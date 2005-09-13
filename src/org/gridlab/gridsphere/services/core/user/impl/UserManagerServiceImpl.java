/*
 *
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.security.password.PasswordEditor;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.Calendar;
import java.util.List;

public class UserManagerServiceImpl extends HibernateDaoSupport implements UserManagerService {

    private static PortletLog log = SportletLog.getInstance(UserManagerServiceImpl.class);

    private PasswordManagerService passwordManagerService = null;
    private AccessControlManagerService aclManager = null;

    private String jdoUser = SportletUserImpl.class.getName();
    private PortletServiceConfig config = null;

    public UserManagerServiceImpl() {

    }

    public void setAccessControlManagerService(AccessControlManagerService accessControlManagerService) {
        this.aclManager = accessControlManagerService;
    }

    public AccessControlManagerService getAccessControlManagerService() {
        return aclManager;
    }

    public void setPasswordManagerService(PasswordManagerService passwordManagerService) {
        this.passwordManagerService = passwordManagerService;
    }

    public PasswordManagerService getPasswordManagerService() {
        return passwordManagerService;
    }

    
    public void initRootUser() {
        log.info("Entering initRootUser()");
        /** 1. Retrieve root user properties **/

        if (getUsers().isEmpty()) {
            /* Set root user profile */

            /* Create root user account */
            log.info("Creating root user account.");
            SportletUser root = this.createUser();

            root.setUserID("root");
            root.setFamilyName("User");
            root.setGivenName("Root");
            root.setFullName("Root User");
            root.setOrganization("GridSphere");
            root.setEmailAddress("root@localhost.localdomain");
            this.saveUser(root);

            /* Set root user password */
            PasswordEditor editor = passwordManagerService.editPassword(root);
            editor.setValue("");
            editor.setDateExpires(null);
            passwordManagerService.savePassword(editor);


            /* Retrieve root user object */
            //rootUser = getUserByUserName(loginName);
            /* Grant super role to root user */
            log.info("Granting super role to root user.");
            aclManager.grantSuperRole(root);
        } else {
            log.info("Root user exists.");
            /*
            if (!aclManager.hasSuperRole(root)) {
                log.info("Root user does not have super role! Granting now...");

                aclManager.grantSuperRole(rootUser);
            }
            */
        }

        log.info("Exiting initRootUser()");
    }

    public void destroy() {
        log.info("Calling destroy()");
    }

    /**
     * Creates a new user
     *
     * @return a blank user
     */
    public SportletUser createUser() {

        SportletUserImpl user = new SportletUserImpl();
        saveSportletUserImpl(user);

        return user;
    }

    /**
     * Creates a new user
     *
     * @return a blank user
     */
    public SportletUser editUser(User user) {
        SportletUserImpl newuser = null;
        if (user instanceof SportletUserImpl) {
            SportletUserImpl u = (SportletUserImpl) user;
            newuser = this.getSportletUserImpl(u.getOid());
        }
        return newuser;
    }

    /**
     * Adds a user
     *
     * @param user a supplied User object
     */
    public void saveUser(User user) {
        if (user instanceof SportletUserImpl) {
            SportletUserImpl impl = (SportletUserImpl) user;
            saveSportletUserImpl(impl);
        }

    }


    public void deleteUser(User user) {
        if (user instanceof SportletUserImpl) {
            // First delete user password
            this.passwordManagerService.deletePassword(user);
            // Then delete user acl
            aclManager.deleteGroupEntries(user);
            // Then delete user object
            deleteSportletUserImpl((SportletUserImpl) user);
            // Send message if not null
        }
    }

    public List selectUsers(String criteria) {
        String oql = "select uzer from "
                + this.jdoUser
                + " uzer "
                + criteria;
        return this.getHibernateTemplate().find(oql);
    }

    public List getUsers() {
        return selectUsers("");
    }

    public User getUser(String id) {
        return getSportletUserImpl(id);
    }

    public User getLoggedInUser(String loginName) {
        SportletUserImpl user = getSportletUserImplByLoginName(loginName);
        if (user != null) {
            long now = Calendar.getInstance().getTime().getTime();
            String lastlogin = (String) user.getAttribute("lastlogin");
            if (lastlogin != null) {
                long lastl = Long.parseLong(lastlogin);
                user.setLastLoginTime(lastl);
            } else {
                user.setLastLoginTime(now);
            }
            user.setAttribute("lastlogin", Long.toString(now));
            saveSportletUserImpl(user);
        }
        return user;
    }

    public User getUserByUserName(String loginName) {
        return getSportletUserImplByLoginName(loginName);
    }


    /**
     * Retrieves a user object with the given email from this service.
     *
     * @param email the user's email address
     */
    public User getUserByEmail(String email) {
        return selectSportletUserImpl("where uzer.EmailAddress='" + email + "'");
    }


    private SportletUserImpl getSportletUserImpl(String id) {
        return selectSportletUserImpl("where uzer.oid='" + id + "'");
    }

    private SportletUserImpl getSportletUserImplByLoginName(String loginName) {
        log.debug("Attempting to retrieve user by login name " + loginName);
        return selectSportletUserImpl("where uzer.UserID='" + loginName + "'");
    }

    private SportletUserImpl selectSportletUserImpl(String criteria) {
        String oql = "select uzer from "
                + jdoUser
                + " uzer "
                + criteria;
        log.debug("Retrieving user with OQL: " + oql);

        List users = this.getHibernateTemplate().find(oql);
        if ((users != null) && (!users.isEmpty())) {
            return (SportletUserImpl)users.get(0);
        }
        return null;
    }

    private void saveSportletUserImpl(SportletUserImpl user) {
        // Create or update user
        this.getHibernateTemplate().saveOrUpdate(user);
    }

    private void deleteSportletUserImpl(SportletUserImpl user) {
        this.getHibernateTemplate().delete(user);
    }

    public boolean existsUserWithID(String userID) {
        String criteria = "where uzer.oid='" + userID + "'";
        return existsSportletUserImpl(criteria);
    }

    public boolean existsUserName(String loginName) {
        String criteria = "where uzer.UserID='" + loginName + "'";
        return existsSportletUserImpl(criteria);
    }

    private boolean existsSportletUserImpl(String criteria) {
        String oql = "select uzer from "
                + jdoUser
                + " uzer "
                + criteria;

        List users = this.getHibernateTemplate().find(oql);
        return ((users != null) && (!users.isEmpty()));

    }

}
