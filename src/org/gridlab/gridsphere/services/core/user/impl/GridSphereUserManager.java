/*
 *
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.core.mail.MailMessage;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.hibernate.PersistenceManagerRdbmsImpl;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.registry.PortletManagerService;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.acl.*;
import org.gridlab.gridsphere.services.core.security.password.InvalidPasswordException;
import org.gridlab.gridsphere.services.core.security.password.PasswordEditor;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.security.password.impl.DbmsPasswordManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.InvalidAccountRequestException;
import org.gridlab.gridsphere.services.core.user.UserManagerService;

import java.util.*;
import java.io.IOException;

public class GridSphereUserManager implements UserManagerService, AccessControlManagerService {

    private static PortletLog log = SportletLog.getInstance(GridSphereUserManager.class);
    private static GridSphereUserManager instance = new GridSphereUserManager();
    private PortletManagerService pms = null;
    private PersistenceManagerRdbms pm = PersistenceManagerFactory.createGridSphereRdbms();
    //private PersistenceManagerRdbms pm = null;
    private PasswordManagerService passwordManagerService = DbmsPasswordManagerService.getInstance();
    //private PasswordManagerService passwordManagerService = null;

    private static boolean isInitialized = false;

    private String jdoUser = SportletUserImpl.class.getName();
    private String jdoAccountRequest = AccountRequestImpl.class.getName();
    private String jdoGroupRequest = GroupRequestImpl.class.getName();
    private String jdoGroupEntry = GroupEntryImpl.class.getName();
    private String jdoPortletGroup = SportletGroup.class.getName();

    private GridSphereUserManager() {}

    public static void main(String[] args) throws Exception  {
        GridSphereUserManager gum = GridSphereUserManager.getInstance();
        String dir = "webapps/gridsphere/WEB-INF/persistence/";
        gum.pm = new PersistenceManagerRdbmsImpl(dir);
        //gum.pm.createDatabaseFromScratch(dir);
        gum.initSportletGroup((SportletGroup)SportletGroup.SUPER);
        gum.initSportletGroup((SportletGroup)PortletGroupFactory.GRIDSPHERE_GROUP);
    }

    public static GridSphereUserManager getInstance() {
        return instance;
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.info("Entering init()");
        if (!isInitialized) {
            PortletServiceFactory factory = SportletServiceFactory.getInstance();
            try {
                pms = (PortletManagerService)factory.createUserPortletService(PortletManagerService.class, GuestUser.getInstance(), config.getServletConfig(), true);
            } catch (Exception e) {
                throw new PortletServiceUnavailableException("Unable to get instance of PMS!");
            }
            initGroups(config);
            initRootUser(config);

            log.info("Entering init()");
            isInitialized = true;
        }
    }

    private void initGroups(PortletServiceConfig config)
            throws PortletServiceUnavailableException {
        log.info("Entering initGroups()");

        initSportletGroup((SportletGroup)SportletGroup.SUPER);
        initSportletGroup((SportletGroup)PortletGroupFactory.GRIDSPHERE_GROUP);

        List webappNames = pms.getPortletWebApplicationNames();

        Iterator it = webappNames.iterator();
        while (it.hasNext()) {
            String groupName = (String)it.next();
            if (!existsGroupWithName(groupName)) {
                String groupDescription = pms.getPortletWebApplicationDescription(groupName);
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
                log.error("Creating group...." + groupName);
                pm.create(group);
                log.error("Created group...." + groupName);
            } catch (Exception e) {
                log.error("Error creating group " + groupName, e);
            }
        } else {
            try {
                log.error("Resetting group...." + groupName);
                SportletGroup realGroup = this.getSportletGroupByName(groupName);
                group.setID(realGroup.getID());
            } catch (Exception e) {
                log.error("Error resetting group " + groupName, e);
            }
        }
    }

    private void initRootUser(PortletServiceConfig config)
            throws PortletServiceUnavailableException {
        log.info("Entering initRootUser()");
        /** 1. Retrieve root user properties **/
        // Login name
        String loginName = config.getInitParameter("loginName", "root").trim();
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
            grantSuperRole(rootUser);
        } else {
            log.info("Root user exists.");
            if (!hasSuperRole(rootUser)) {
                log.info("Root user does not have super role! Granting now...");
                /* Grant super role to root user */
                grantSuperRole(rootUser);
            }
        }
        log.info("Exiting initRootUser()");
    }

    public void destroy() {
        log.info("Calling destroy()");
    }

    private User getAuthUser(String loginName)
            throws AuthorizationException {
        log.debug("Attempting to retrieve user " + loginName);
        if (loginName == null) {
            AuthorizationException ex = new AuthorizationException();
            ex.putInvalidParameter("username", "No username provided.");
            throw ex;
        }
        User user = getUserByUserName(loginName);
        if (user == null) {
            log.debug("Unable to retrieve user " + loginName);
            AuthorizationException ex = new AuthorizationException();
            ex.putInvalidParameter("username", "Invalid username provided.");
            throw ex;
        }
        log.debug("Successfully retrieved user " + loginName);
        return user;
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
        return new AccountRequestImpl();
    }

    public AccountRequest createAccountRequest(User user) {
        AccountRequestImpl request = new AccountRequestImpl();
        request.setID(user.getID());
        request.setUserName(user.getUserName());
        request.setFamilyName(user.getFamilyName());
        request.setGivenName(user.getGivenName());
        request.setFullName(user.getFullName());
        request.setOrganization(user.getOrganization());
        request.setEmailAddress(user.getEmailAddress());
        return request;
    }

    public void submitAccountRequest(AccountRequest request)
            throws InvalidAccountRequestException {
        submitAccountRequest(request, null);
    }

    public void submitAccountRequest(AccountRequest request, MailMessage mailMessage)
            throws InvalidAccountRequestException {
         if (request instanceof AccountRequestImpl) {
             // Save account request if not already saved
             if (!existsAccountRequest(request)) {
                 // First validate account request
                 validateAccountRequest(request);
                 /* Store account request */
                 try {
                     log.debug("Creating account request record for " + request.getUserName());
                     pm.create(request);
                 } catch (PersistenceManagerException e) {
                     String msg = "Error saving account request";
                     log.error(msg, e);
                 }
                 /* Store passsword for requested account */
                 saveAccountRequestPassword(request);
             }
             // Send message if not null
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
                   + " accountRequest where accountRequest.oid="
                   + requestImpl.getOid();
        try {
            return (pm.restore(oql) != null);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving account request";
            log.error(msg, e);
        }
        return false;
    }

    public User approveAccountRequest(AccountRequest request) {
        return approveAccountRequest(request, null);
    }

    public User approveAccountRequest(AccountRequest request, MailMessage mailMessage) {
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
        if (this.passwordManagerService.hasPassword(request)) {
            log.info("Activating password for " + user.getUserName());
            // Activate user password
            try {
                this.passwordManagerService.activatePassword(request, user);
            } catch (InvalidPasswordException e) {
                log.error("Invalid password during account request approval!!!", e);
            }
        }
    }

    private void activateAccountRequestGroupEntries(AccountRequest request, User user) {
        // If new user then set initial acl
        if (request.isNewUser()) {
            // Grant user role in base group
            addGroupEntry(user, SportletGroup.CORE,  PortletRole.USER);
        }
    }

    public void denyAccountRequest(AccountRequest request) {
        denyAccountRequest(request, null);
    }

    public void denyAccountRequest(AccountRequest request, MailMessage mailMessage) {
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
        deleteAccount(user, null);
    }

    public void deleteAccount(User user, MailMessage mailMessage) {
        if (user instanceof SportletUserImpl) {
            // First delete user password
            this.passwordManagerService.deletePassword(user);
            // Then delete user acl
            deleteGroupEntries(user);
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
        if (user!=null) {
            long now = Calendar.getInstance().getTime().getTime();
            user.setLastLoginTime(now);
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
            return (SportletUserImpl)pm.restore(oql);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving user with criteria " + criteria;
            log.error(msg, e);
            return null;
        }
    }

    private SportletUserImpl editSportletUserImpl(AccountRequest request) {
        /* TODO: Account request id should not be same as user id */
        String userID = request.getID();
        SportletUserImpl user = getSportletUserImpl(userID);
        if (user == null) {
            user = new SportletUserImpl();
            user.setID(userID);
        }
        user.setUserName(request.getUserName());
        user.setFamilyName(request.getFamilyName());
        user.setGivenName(request.getGivenName());
        user.setFullName(request.getFullName());
        user.setOrganization(request.getOrganization());
        user.setEmailAddress(request.getEmailAddress());
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
        String oql = "select user.oid from "
                   + jdoUser
                   + " user "
                   + criteria;
        try {
            return (pm.restore(oql) != null);
        } catch (PersistenceManagerException e) {
            String msg = "Error retrieving account request";
            log.error(msg, e);
        }
        return false;
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
        GroupRequestImpl request = new GroupRequestImpl();
        return request;
    }

    public GroupRequest createGroupRequest(GroupEntry entry) {
        if (entry instanceof GroupEntryImpl) {
            GroupEntryImpl entryImpl = (GroupEntryImpl)entry;
            GroupRequestImpl request = new GroupRequestImpl(entryImpl);
            return request;
        }
        return null;
    }

    public void submitGroupRequest(GroupRequest request)
            throws InvalidGroupRequestException {
        submitGroupRequest(request, null);
    }

    public void submitGroupRequest(GroupRequest request, MailMessage mailMessage)
            throws InvalidGroupRequestException {
        if (request instanceof GroupRequestImpl) {
            // First validate accesss request
            validateGroupRequest(request);
            // Then save account request if not already saved
            if (!existsGroupRequest(request)) {
                try {
                    pm.create(request);
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
        if (role.equals(PortletRole.SUPER) && (! group.equals(SportletGroup.SUPER) )) {
            String msg = "Super role can only exist in super group.";
            log.info(msg);
            throw new InvalidGroupRequestException(msg);
        // If group is super but role isn't, throw invalid access request exception
        } else if (group.equals(PortletRole.SUPER) && (! role.equals(PortletRole.SUPER) )) {
            String msg = "Super group can only contain super role.";
            log.info(msg);
            throw new InvalidGroupRequestException(msg);
        } else if (! (role.equals(PortletRole.ADMIN) ||
                      role.equals(PortletRole.USER)  ||
                      role.equals(PortletRole.GUEST) )) {
            String msg = "Portlet role [" + role + "] not recognized.";
            log.info(msg);
            throw new InvalidGroupRequestException(msg);
        }
    }

    public void approveGroupRequest(GroupRequest request) {
        approveGroupRequest(request, null);
    }

    public void approveGroupRequest(GroupRequest request, MailMessage mailMessage) {
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
        denyGroupRequest(request, null);
    }

    public void denyGroupRequest(GroupRequest request, MailMessage mailMessage) {
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
        if (hasSuperRole(user)) {
            return new Vector();
        } else {
            String criteria = "where groupEntry.sportletUser.oid='" + user.getID() + "'";
            return selectGroupEntries(criteria);
        }
    }

    public List getGroupEntries(PortletGroup group) {
        String criteria = "where groupEntry.sportletGroup.oid='" + group.getID() + "'";
        return selectGroupEntries(criteria);
    }

    public List getGroupEntriesForGroups(List groups) {
        List sumGroupEntries = null;
        for (int ii = 0; ii < groups.size(); ++ii) {
            PortletGroup group = (PortletGroup)groups.get(ii);
            List groupEntries = getGroupEntries(group);
            sumGroupEntries.add(groupEntries);
        }
        return sumGroupEntries;
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

    private void deleteGroupEntry(GroupEntry entry) {
        try {
            pm.delete(entry);
        } catch (PersistenceManagerException e) {
            String msg = "Error deleting access right";
            log.error(msg, e);
        }
    }

    private void deleteGroupEntries(User user) {
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

    public String getGroupDescription(String groupName) {
        return pms.getPortletWebApplicationDescription(groupName);
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

    public List getUsersNotInGroup(PortletGroup group) {
        List usersNotInGroup = new Vector();
        Iterator allUsers = getUsers().iterator();
        while (allUsers.hasNext()) {
            User user = (User)allUsers.next();
            // If user has super role, don't include
            if (hasSuperRole(user)) {
                continue;
            }
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
        if (hasSuperRole(user)) {
            groups = getGroups();
        } else {
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
        }
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
        if (hasSuperRole(user)) {
            return PortletRole.SUPER;
        } else {
            GroupEntry entry = getGroupEntry(user, group);
            if (entry == null) {
                return PortletRole.GUEST;
            }
            return entry.getRole();
        }
    }

    private void addGroupEntry(User user, PortletGroup group, PortletRole role) {
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
        return getUsers(SportletGroup.SUPER);
    }

    public void grantSuperRole(User user) {
        addGroupEntry(user, SportletGroup.SUPER, PortletRole.SUPER);
    }

    public void revokeSuperRole(User user) {
        removeGroupEntry(user, SportletGroup.SUPER);
    }

    public boolean hasSuperRole(User user) {
        return isUserInGroup(user, SportletGroup.SUPER);
    }
}
