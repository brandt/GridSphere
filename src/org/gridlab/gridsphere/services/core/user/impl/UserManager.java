/*
 *
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;
import org.gridlab.gridsphere.services.core.security.acl.*;
import org.gridlab.gridsphere.services.core.security.acl.impl.AccessControlManager;
import org.gridlab.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.security.password.PasswordEditor;
import org.gridlab.gridsphere.services.core.security.password.impl.DbmsPasswordManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.InvalidAccountRequestException;
import org.gridlab.gridsphere.services.core.user.UserManagerService;

import java.util.*;

public class UserManager implements UserManagerService {

    private static PortletLog log = SportletLog.getInstance(UserManager.class);
    private static UserManager instance = new UserManager();

    private PersistenceManagerRdbms pm = PersistenceManagerFactory.createGridSphereRdbms();
    private PasswordManagerService passwordManagerService = DbmsPasswordManagerService.getInstance();

    private AccessControlManager aclManager = null;

    private static boolean isInitialized = false;

    private String jdoUser = SportletUserImpl.class.getName();
    private String jdoAccountRequest = AccountRequestImpl.class.getName();

    private UserManager() {}

    public static UserManager getInstance() {
        return instance;
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.info("Entering init()");
        if (!isInitialized) {
            aclManager = AccessControlManager.getInstance();
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
        String loginName = config.getInitParameter("gridsphere.user.userid", "root").trim();
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
            String fullName = config.getInitParameter("fullName", "").trim();
            log.info("Root user full name = " + givenName);
            String organization = config.getInitParameter("organization", "GridSphere").trim();
            log.info("Root user organization = " + organization);
            String emailAddress = config.getInitParameter("emailAddress", "root@localhost").trim();
            log.info("Root user email address = " + emailAddress);
            String password = config.getInitParameter("password", "").trim();
            if (password.equals("")) {
                log.warn("Root user password is blank. Please create a password as soon as possible!");
            }
            /* Set root user profile */
            AccountRequest rootRequest = createAccountRequest();
            rootRequest.setUserID(loginName);
            rootRequest.setFamilyName(familyName);
            rootRequest.setGivenName(givenName);
            rootRequest.setFullName(fullName);
            rootRequest.setOrganization(organization);
            rootRequest.setEmailAddress(emailAddress);
            /* Set root user password */
            rootRequest.setPasswordValue(password);
            rootRequest.setPasswordDateExpires(null);
            rootRequest.setPasswordValidation(false);
            /* Create root user account */
            log.info("Creating root user account.");
            try {
                submitAccountRequest(rootRequest);
                approveAccountRequest(rootRequest);
            } catch (InvalidAccountRequestException e) {
                log.error("Unable to create account for root user", e);
                throw new PortletServiceUnavailableException(e.getMessage());
            }
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

    public List selectAccountRequests(String criteria) {
        String oql = "select accountRequest from "
                   + this.jdoAccountRequest
                   + " accountRequest "
                   + criteria;
        try {
            return pm.restoreList(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving account request";
            log.error(msg, e);
            return new Vector();
        }
    }

    public List getAccountRequests() {
        return selectAccountRequests("");
    }

    public AccountRequest getAccountRequest(String oid) {
        String oql = "select accountRequest from "
                   + jdoAccountRequest
                   + " accountRequest where accountRequest.oid=" + oid;
        try {
            return (AccountRequest)pm.restore(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving account request";
            log.error(msg, e);
            return null;
        }
    }

    public AccountRequest createAccountRequest() {
        AccountRequest request = new AccountRequestImpl();
        return createNewAccountRequest(request);
    }

    public AccountRequest createAccountRequest(User user) {
        AccountRequestImpl request = new AccountRequestImpl(user);
        return createNewAccountRequest(request);
    }

    private AccountRequest createNewAccountRequest(AccountRequest request) {
        try {
            pm.create(request);
        } catch (PersistenceManagerException e) {
            String msg = "Error saving account request";
            log.error(msg, e);
        }
        log.debug("Creating account request record: " + request.getID());
        return request;
    }

    public void submitAccountRequest(AccountRequest request)
            throws InvalidAccountRequestException {
         if (request instanceof AccountRequestImpl) {
             // Save account request if not already saved
             if (existsAccountRequest(request)) {
                 // First validate account request
                 validateAccountRequest(request);
                 /* Update account request */
                 try {
                     log.debug("Updating account request record: " + request.getID());
                     pm.update(request);
                 } catch (PersistenceManagerException e) {
                     String msg = "Error saving account request";
                     log.error(msg, e);
                 }
                 /* Store passsword for requested account */
                 saveAccountRequestPassword(request);
             }
         }
    }

    public void validateAccountRequest(AccountRequest request)
            throws InvalidAccountRequestException {
        // Then validate password
        validatePassword(request);
    }

    private void validatePassword(AccountRequest request) throws InvalidAccountRequestException {
        // Then validate password if requested
        User user = getUser(request.getID());
        if (user == null) {
            if (request.getPasswordValidation()) {
                log.info("Validating password for account request");
                try {
                    this.passwordManagerService.validatePassword(request.getPasswordValue());
                } catch (InvalidPasswordException e) {
                    throw new InvalidAccountRequestException("Unable to validate password: " + e.getExplanation());
                }
            } else {
                log.info("Not validating password for account request");
            }
        } else {
            if (request.getPasswordValidation() && request.getPasswordHasChanged()) {
                log.info("Validating password for account request");
                try {
                    this.passwordManagerService.validatePassword(user, request.getPasswordValue());
                } catch (InvalidPasswordException e) {
                    throw new InvalidAccountRequestException("Unable to validate password: " + e.getExplanation());
                }
            } else {
                log.info("Not validating password for account request");
            }
        }
    }

    private void saveAccountRequestPassword(AccountRequest request) throws InvalidAccountRequestException {
        // Get password editor from account request
        PasswordEditor passwordBean = request.getPassword();
        // Check if password wasn't edited
        if (!passwordBean.isDirty()) {
            // Get user id from account request
            String userID = request.getID();
            // If user exists for account request
            if (existsUserWithID(userID)) {
                // No need to change password
                log.debug("No changes to user password were made");
                return;
            }
        }
        log.debug("Saving password record for account request " + request.getUserName());
        // Otherwise attempt to save password edits
        System.err.println("before save passwd request id: " + request.getID());
        try {
            this.passwordManagerService.savePassword(passwordBean);
        } catch (InvalidPasswordException e) {
            throw new InvalidAccountRequestException("Unable to validate password: " + e.getExplanation());
        }

    }

    private boolean existsAccountRequest(AccountRequest request) {
        AccountRequestImpl requestImpl = (AccountRequestImpl)request;
        String oql = "select accountRequest.oid from "
                   + this.jdoAccountRequest
                   + " accountRequest where accountRequest.oid='"
                   + requestImpl.getOid()+"'";
        try {
            return (pm.restore(oql) != null);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving account request";
            log.error(msg, e);
        }
        return false;
    }

    public User approveAccountRequest(AccountRequest request) {
        if (request instanceof AccountRequestImpl) {
            // Edit user from account request
            SportletUserImpl user = editSportletUserImpl(request);
            // Save user from account request
            saveSportletUserImpl(user);
            // Activate user password
            activateAccountRequestPassword(request, user);
            // Activate user access rights
            activateAccountRequestGroupEntries(request, user);
            // Delete account request
            deleteAccountRequest(request);
            // Send message if not null
            return user;
        }
        return null;
    }

    private void activateAccountRequestPassword(AccountRequest request, User user) {
        // If a new password was submitted with account request
        //if (request.getPasswordValidation()) {
        if (this.passwordManagerService.hasPassword(request)) {
            log.info("Activating password for " + user.getUserName());
            // Activate user password
            try {
                this.passwordManagerService.activatePassword(request, user);
            } catch (InvalidPasswordException e) {
                log.error("Invalid password during account request approval!!!", e);
            }
        }
        //}
    }

    private void activateAccountRequestGroupEntries(AccountRequest request, User user) {
        // If new user then set initial acl
        if (request.isNewUser()) {
            // Grant user role in base group
            aclManager.addGroupEntry(user, SportletGroup.CORE,  PortletRole.USER);
        }
    }

    public void denyAccountRequest(AccountRequest request) {
        if (request instanceof AccountRequestImpl) {
            // Delete account request
            deleteAccountRequest(request);
            // Send message if not null
        }
    }

    private void deleteAccountRequest(AccountRequest request) {
        try {
            pm.delete(request);
        } catch(PersistenceManagerException e) {
            String msg = "Unable to delete account request";
            log.error(msg, e);
        }
    }

    public void deleteAccount(User user) {
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
        String oql = "select user from "
                   + this.jdoUser
                   + " user "
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

    private SportletUserImpl getSportletUserImpl(String id) {
        return selectSportletUserImpl("where user.oid='" + id + "'");
    }

    private SportletUserImpl getSportletUserImplByLoginName(String loginName) {
        log.debug("Attempting to retrieve user by login name " + loginName);
        return selectSportletUserImpl("where user.UserID='" + loginName + "'");
    }

    private SportletUserImpl selectSportletUserImpl(String criteria) {
        String oql = "select user from "
                   + jdoUser
                   + " user "
                   + criteria;
        try {
            SportletUserImpl sui = (SportletUserImpl)pm.restore(oql);
            log.debug("Retrieved user with OQL: "+oql);
            return sui;
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving user with criteria " + criteria;
            log.error(msg, e);
            return null;
        }
    }

    private SportletUserImpl editSportletUserImpl(AccountRequest request) {
        /* TODO: Account request id should not be same as user id */
        String userID = request.getID();
        System.err.println("in  editSportletUser userID: " + userID);
        String userName = request.getUserName();
        SportletUserImpl user = getSportletUserImplByLoginName(userName);
        if (user == null) {
            System.err.println("user is null!!");
            user = new SportletUserImpl();
            user.setID(userID);
        }
        user.setUserName(request.getUserName());
        user.setFamilyName(request.getFamilyName());
        user.setGivenName(request.getGivenName());
        user.setFullName(request.getFullName());
        user.setOrganization(request.getOrganization());
        user.setEmailAddress(request.getEmailAddress());
        Enumeration enum = request.getAttributeNames();
        while (enum.hasMoreElements()) {
            String attrName = (String)enum.nextElement();
            String attrVal = (String)request.getAttribute(attrName);
            user.setAttribute(attrName, attrVal);
        }
        return user;
    }

    private void saveSportletUserImpl(SportletUserImpl user) {
        // Reset full name if necessary
        resetFullName(user);
        // Create or update user
        if (existsSportletUserImpl(user)) {
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

    private void resetFullName(SportletUserImpl user) {
        String fullName = user.getFullName();
        if (fullName.equals("")) {
            StringBuffer buffer = new StringBuffer("");
            String givenName = user.getGivenName();
            if (givenName.length() > 0) {
                buffer.append(givenName);
                buffer.append(" ");
            }
            String familyName = user.getFamilyName();
            buffer.append(familyName);
            user.setFullName(buffer.toString());
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
        String criteria = "where user.oid='" + userID + "'";
        return existsSportletUserImpl(criteria);
    }

    public boolean existsUserName(String loginName) {
        String criteria = "where user.UserID='" + loginName + "'";
        return existsSportletUserImpl(criteria);
    }

    private boolean existsSportletUserImpl(SportletUserImpl user) {
        String criteria = "where user.oid='" + user.getOid() + "'";
        return existsSportletUserImpl(criteria);
    }

    private boolean existsSportletUserImpl(String criteria) {
        String oql = "select user from "
                   + jdoUser
                   + " user "
                   + criteria;
        try {
            SportletUserImpl sui = (SportletUserImpl) pm.restore(oql);
            if (sui==null) {
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
