/*
 *
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.security.acl.impl.AccessControlManager;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.security.password.PasswordEditor;
import org.gridlab.gridsphere.services.core.user.UserManagerService;

public class UserManagerServiceImpl implements PortletServiceProvider, UserManagerService {

    private static PortletLog log = SportletLog.getInstance(UserManagerServiceImpl.class);
    private static UserManagerServiceImpl instance = new UserManagerServiceImpl();

    private PersistenceManagerRdbms pm = PersistenceManagerFactory.createGridSphereRdbms();
    private PasswordManagerService passwordManagerService = null;
    private AccessControlManager aclManager = null;

    private static boolean isInitialized = false;

    private String jdoUser = SportletUserImpl.class.getName();

    public UserManagerServiceImpl() {
    }

    public static UserManagerServiceImpl getInstance() {
        return instance;
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.info("Entering init()");
        if (!isInitialized) {
            aclManager = AccessControlManager.getInstance();
            PortletServiceFactory factory = SportletServiceFactory.getInstance();
            try {
                passwordManagerService = (PasswordManagerService) factory.createPortletService(PasswordManagerService.class, config.getServletContext(), true);
            } catch (PortletServiceUnavailableException e) {
                throw new PortletServiceUnavailableException("Cannot initialize usermanager service", e);
            } catch (PortletServiceNotFoundException e) {
                throw new PortletServiceUnavailableException("Cannot initialize usermanager service", e);
            }
            initRootUser(config);
            log.info("Entering init()");
            isInitialized = true;
        }
    }

    private void initRootUser(PortletServiceConfig config)
            throws PortletServiceUnavailableException {
        log.info("Entering initRootUser()");
        /** 1. Retrieve root user properties **/
        // Login name
        String loginName = config.getInitParameter("userid", "root").trim();
        log.info("Root user login name = " + loginName);
        /** 2. Create root user account if doesn't exist **/
        User rootUser = getUserByUserName(loginName);
        if (rootUser == null) {
            /* Retrieve root user properties */
            log.info("Retrieving root user properties");
            String familyName = config.getInitParameter("familyName", "User").trim();
            log.info("Root user family name = " + familyName);
            String givenName = config.getInitParameter("givenName", "Root").trim();
            log.info("Root user given name = " + givenName);
            String fullName = config.getInitParameter("fullName", "Root User").trim();
            log.info("Root user full name = " + givenName);
            String organization = config.getInitParameter("organization", "GridSphere").trim();
            log.info("Root user organization = " + organization);
            String emailAddress = config.getInitParameter("emailAddress", "root@localhost.localdomain").trim();
            log.info("Root user email address = " + emailAddress);
            String password = config.getInitParameter("password", "").trim();
            if (password.equals("")) {
                log.warn("Root user password is blank. Please create a password as soon as possible!");
            }
            /* Set root user profile */

            /* Create root user account */
            log.info("Creating root user account.");
            SportletUser root = this.createUser();

            root.setUserID(loginName);
            root.setFamilyName(familyName);
            root.setGivenName(givenName);
            root.setFullName(fullName);
            root.setOrganization(organization);
            root.setEmailAddress(emailAddress);
            this.saveUser(root);

            /* Set root user password */
            PasswordEditor editor = passwordManagerService.editPassword(root);
            editor.setValue(password);
            editor.setDateExpires(null);
            passwordManagerService.savePassword(editor);


            /* Retrieve root user object */
            rootUser = getUserByUserName(loginName);
            /* Grant super role to root user */
            log.info("Granting super role to root user.");
            aclManager.grantSuperRole(rootUser);
        } else {
            log.info("Root user exists.");
            if (!aclManager.hasSuperRole(rootUser)) {
                log.info("Root user does not have super role! Granting now...");
                /* Grant super role to root user */
                aclManager.grantSuperRole(rootUser);
            }
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
            SportletUserImpl u = (SportletUserImpl)user;
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
            SportletUserImpl impl = (SportletUserImpl)user;
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
            deleteSportletUserImpl((SportletUserImpl)user);
            // Send message if not null
        }
    }

    public List selectUsers(String criteria) {
        String oql = "select uzer from "
                   + this.jdoUser
                   + " uzer "
                   + criteria;
        try {
            return pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving users with criteria " + criteria;
            log.error(msg, e);
            return new Vector();
        }
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
            String lastlogin = (String)user.getAttribute("lastlogin");
            if (lastlogin != null) {
                long lastl = Long.parseLong(lastlogin);
                user.setLastLoginTime(lastl);
            }  else {
                user.setLastLoginTime(now);
            }
            user.setAttribute("lastlogin", new Long(now).toString());
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
        log.debug("Retrieving user with OQL: "+oql);
        try {
            SportletUserImpl sui = (SportletUserImpl)pm.restore(oql);
            //log.debug("Retrieved user with OQL: "+oql);
            return sui;
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving user with criteria " + criteria;
            log.error(msg, e);
            return null;
        }
    }

    private void saveSportletUserImpl(SportletUserImpl user) {
        // Create or update user
        if (user.getOid() != null) {
            log.debug("Updating user record for " + user.getUserName());
            try {
                pm.update(user);
            } catch (PersistenceManagerException e) {
                String msg = "Error updating user";
                log.error(msg, e);
            }
        } else {
            log.debug("Creating user record for " + user.getUserName());
            try {
                pm.create(user);
            } catch (PersistenceManagerException e) {
                String msg = "Error creating user";
                log.error(msg, e);
            }
        }
    }

    private void deleteSportletUserImpl(SportletUserImpl user) {
        try {
            pm.delete(user);
        } catch (PersistenceManagerException e) {
            String msg = "Error deleting user";
            log.error(msg, e);
        }
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
        try {
            SportletUserImpl sui = (SportletUserImpl) pm.restore(oql);
            if (sui == null) {
                log.debug("User does not exist!");
            }
            return (sui != null);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving account request";
            log.error(msg, e);
        }
        return false;
    }

}
