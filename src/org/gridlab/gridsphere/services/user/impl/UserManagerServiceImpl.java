/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user.impl;

import org.gridlab.gridsphere.core.mail.MailMessage;
import org.gridlab.gridsphere.core.mail.MailUtils;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;
import org.gridlab.gridsphere.services.security.acl.impl.UserACL;
import org.gridlab.gridsphere.services.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.PermissionDeniedException;
import org.gridlab.gridsphere.services.user.UserManagerService;

import javax.mail.MessagingException;
import java.util.List;

/**
 * The UserManagerService manages users and account requests. Thru the UserManagerService
 * new portal accounts can be requested and granted or denied. Role objects can be retrieved
 * and removed.
 */
public class UserManagerServiceImpl implements PortletServiceProvider, UserManagerService {

    private static PortletLog log = SportletLog.getInstance(UserManagerServiceImpl.class);
    private static PortletServiceFactory factory = SportletServiceFactory.getInstance();
    private static AccessControlService aclService = null;
    private static AccessControlManagerService aclManager = null;
    private static PasswordManagerService passwordManager = null;

    private String jdoUserACL = new String();   // object name for UserACL
    private String jdoARImpl = new String();    // ... for AccountRequest
    private String jdoSUImpl = new String();    // ... for SportletUserImpl
    private String jdoPDImpl = new String();    // ... for SportletData

    private PersistenceManagerRdbms pm = PersistenceManagerRdbms.getInstance();

    public UserManagerServiceImpl() {
        super();
        jdoSUImpl = SportletUserImpl.class.getName();
        jdoARImpl = AccountRequestImpl.class.getName();
        jdoUserACL = UserACL.class.getName();
        jdoPDImpl = SportletData.class.getName();
    }

    /**
     * Initializes the portlet service.
     * The init method is invoked by the portlet container immediately after a portlet service has
     * been instantiated and before it is passed to the requestor.
     *
     * @param config the service configuration
     * @throws PortletServiceUnavailableException if an error occurs during initialization
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        initServices(config);
        initRootUser(config);
    }

    private void initServices(PortletServiceConfig config)
        throws PortletServiceUnavailableException {
        try {
            aclService = (AccessControlService) factory.createPortletService(AccessControlService.class, config.getServletConfig(), true);
            aclManager = (AccessControlManagerService) factory.createPortletService(AccessControlManagerService.class, config.getServletConfig(), true);
            passwordManager = (PasswordManagerService) factory.createPortletService(PasswordManagerService.class, config.getServletConfig(), true);
            log.info("in init()");
        } catch (PortletServiceNotFoundException e) {
            throw new PortletServiceUnavailableException("Unable to find portlet services: AccessControlService and AccessControlManagerService");
        }
    }

    private void initRootUser(PortletServiceConfig config)
            throws PortletServiceUnavailableException {
        /** 1. Retrieve root user properties **/
        // Login name
        String userID = config.getInitParameter("userID", "root").trim();
        log.info("Root user login name = " + userID);
        /** 2. Create root user account if doesn't exist **/
        User user = this.getUser(userID);
        if (user == null) {
            log.info("Retrieving root user properties");
            AccountRequest rootRequest = createAccountRequest();
            rootRequest.setUserID(userID);
            // Family name
            String familyName = config.getInitParameter("familyName", "User").trim();
            log.info("Root user family name = " + familyName);
            rootRequest.setFamilyName(familyName);
            // Given name
            String givenName = config.getInitParameter("givenName", "Root").trim();
            log.info("Root user given name = " + givenName);
            rootRequest.setGivenName(givenName);
            // Organization
            String organization = config.getInitParameter("organization", "GridSphere").trim();
            log.info("Root user organization = " + organization);
            rootRequest.setOrganization(organization);
            /* Create root user account */
            log.info("Creating root user account");
            createAccount(rootRequest);
            /* Make sure we have a root user */
            user = getUser(userID);
            if (user == null) {
                String msg = "Unable to create root user!";
                //throw new PortletServiceUnavailableException(msg);
            }
            /* Create root password */
            log.info("Creating root password.");
            String password = config.getInitParameter("password", "").trim();
            if (password.equals("")) {
                log.info("Root user has no password! Please create one as soon as possible.");
            }
            try {
                // Create password, but don't try to validate it...
                this.passwordManager.createPassword(user, password, false);
            } catch (Exception e) {
                String msg = "Unable to create password for root user";
                log.error(msg, e);
                //throw new PortletServiceUnavailableException(msg);
            }
            if (user == null) {
                String msg = "User is null!";
                //throw new PortletServiceUnavailableException(msg);
            }

            log.info("Granting root user super user privileges.");
            try {
                aclManager.addUserToSuperRole(user);
            } catch (PortletServiceException e) {
                String msg = "Unable to add root user to super role";
                log.error(msg, e);
                //throw new PortletServiceUnavailableException(msg);
            }
        } else {
            log.info("Root user exists...");
        }
    }

    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy() {
        log.info("in destroy()");
    }

    private void createAccount(AccountRequest request) {
        try {
            submitAccountRequest(request);
            approveAccountRequest((AccountRequestImpl)request);
        } catch (PortletServiceException e) {
            log.error("Unable to save account", e);
        }
    }

    /**
     * Create a new account request. A unique ID is assigned to this request, that can't be modified by the client.
     * When an account request is submitted, the ID is checked
     */
    public AccountRequest createAccountRequest() {
        AccountRequestImpl newacct = new AccountRequestImpl();
        return newacct;
    }

    /**
     * Modify an existing user account. Changes must be approved
     * @param user
     * @return
     */
    public AccountRequest changeAccountRequest(User user) {
        // get user from DB
        // create an AccountRequestImpl from User
        // when modifications are made to account request they go thru submission process again

        AccountRequest ar = new AccountRequestImpl(user);

        return ar;
    }

    /**
     * Submit the account request to the queue for administrative approval
     * @param request
     * @throws PortletServiceException
     */
    public void submitAccountRequest(AccountRequest request) throws PortletServiceException {

        try {
            pm.create(request);
        } catch (PersistenceManagerException e) {
            log.info("conf error " + e);
        }

        // mail the super user
        String newAcctSubject = "GridSphere: New Role Request";
        String newAcctBody = "New user request from: " + request.toString();
        String portalRoot = "gridsphere-admin@gridlab.org";

        String newGroupSubject = "GridSphere: New Group Addition Request";
        String newGroupBody = "New user request to join group from: " + request.toString();

        MailMessage superMsg = new MailMessage(newAcctSubject, newAcctBody, request.getEmailAddress(), "");
        MailMessage adminMsg = new MailMessage(newGroupSubject, newGroupBody, request.getEmailAddress(), "");

        // do that later in the approveRequest thing
/*
        try {
            // notify super users of new account
            Iterator rootIt = aclService.getSuperUsers().iterator();
            while (rootIt.hasNext()) {
                String sender = (String) rootIt.next();
                superMsg.setSender(sender);
                MailUtils.sendMail(superMsg, "localhost");
            }

        } catch (MessagingException e) {
            log.error("Failed to mail message to: " + request.getEmailAddress(), e);
        }
*/
    }

    /**
     * Administrators can retrieve all pending account request
     */
    public List getAccountRequests() {

        String command =
                "select ar from "+jdoARImpl+" ar";
        List requests = null;
        try {
            requests = pm.restoreList(command);
        } catch (PersistenceManagerException e) {
            log.info("Config error " + e);
        }
        return requests;
    }

    /**
     * Returns the account request for the given user id
     *
     * @param user id of account request
     * @return account request for given user id
     */
    public AccountRequest getAccountRequest(String id) {
        return getAccountRequestImpl(id);
    }

    public boolean existsAccountRequest(String id) {
        return (getAccountRequest(id) != null);
    }

    private AccountRequestImpl getAccountRequestImpl(String id) {
        AccountRequestImpl requestImpl = null;
        String command =
                "select ar from "+jdoARImpl+" ar where ar.ObjectID=" + id;
        try {
            requestImpl = (AccountRequestImpl) pm.restoreObject(command);
        } catch (PersistenceManagerException e) {
            log.error("PM Exception :" + e);
        }
        return requestImpl;
    }

    /**
     * Approve a new or modified account request
     *
     * @param approver user who approves this request (should better be a superuser)
     * @param request accountrquest to be approved
     * @param mailMessage message to be send ouut
     * @throws PermissionDeniedException if the approver is not allowed to approve that request
     */
    public void approveAccountRequest(User approver, AccountRequest request, MailMessage mailMessage)
            throws PermissionDeniedException {
        if ( ! existsAccountRequest(request.getID()) ) {
            throw new PermissionDeniedException("Account request has not been submitted");
        }
        //@todo check if a user with that userid already exists!
        if (isSuperUser(approver)) {
            approveAccountRequest((AccountRequestImpl)request, mailMessage);
        } else {
            log.info("User '" + approver.getGivenName() + "' tried to approve User '" + request.getGivenName() + "' (denied) ");
            throw new PermissionDeniedException("Permission denied ");
        }
    }

    /**
     */
    private void approveAccountRequest(AccountRequest request, MailMessage mailMessage) {
        // Approve the request
        approveAccountRequest(request);
        // Mail the user
        try {
            if (mailMessage != null)
                MailUtils.sendMail(mailMessage, "localhost");
        } catch (MessagingException e) {
            log.error("Unable to send mail: ", e);
        }
    }

    /**
     */
    private void approveAccountRequest(AccountRequest request) {
        User user = null;
        String username= request.getUserID();
        // now need to check wheter new account or an existing should be modified
        if (existsUser(username)) {
            // update user and delete request
            user = modifyExistingUser((AccountRequestImpl)request, (SportletUser) getUser(username));
            try {
                pm.update(user);
                pm.delete(request);
            } catch (PersistenceManagerException e) {
                log.error("PM Exception :" + e);
            }
        } else {
            // create user and delete request
            user = makeNewUser((AccountRequestImpl)request);
            try {
                pm.create(user);
                pm.delete(request);
            } catch (PersistenceManagerException e) {
                log.error("PM Exception :" + e);
            }
        }
    }

    /**
     * Creates a new SportletUser from an AccountRequestImpl
     */
    private SportletUser makeNewUser(AccountRequestImpl requestImpl) {
        SportletUserImpl newuser = new SportletUserImpl();
        modifyExistingUser(requestImpl, newuser);
        return newuser;
    }

    /**
     * Changes a SportletUser to the values of an accountrequest
     * @param requestImpl
     * @param user
     * @return the changes portletuser
     */
    private SportletUser modifyExistingUser(AccountRequestImpl requestImpl, SportletUser user) {
        user.setEmailAddress(requestImpl.getEmailAddress());
        user.setFamilyName(requestImpl.getFamilyName());
        user.setFullName(requestImpl.getFullName());
        user.setGivenName(requestImpl.getGivenName());
        user.setID("" + requestImpl.getID());
        user.setUserID(requestImpl.getUserID());
        return user;
    }

    /**
     * Deny a new or modified account request
     *
     * @param denier user who denies the request
     * @param request accountrequest to be denied
     * @param mailMessage message to send out
     * @throws PermissionDeniedException if the denier is no allowed to deny
     */
    public void denyAccountRequest(User denier, AccountRequest request, MailMessage mailMessage)
            throws PermissionDeniedException {
        if (isSuperUser(denier)) {

            // @todo should somehow be in a transaction

            String userid = request.getID();
            String command =
                    "select acl from "+jdoUserACL+" acl where " +
                    "UserID=\"" + request.getID() + "\"";

            try {
                pm.delete(request);
                // only delete the requested groups when the user did not exist before!
                if (!existsUser(userid)) {
                    pm.deleteList(command);
                }
            } catch (PersistenceManagerException e) {
                log.error("PM Exception :" + e);
            }
            // Mail the user
            try {
                if (mailMessage != null)
                    MailUtils.sendMail(mailMessage, "localhost");
            } catch (MessagingException e) {
                log.error("Unable to send mail: ", e);
            }
        } else {
            throw new PermissionDeniedException("Permission denied to deny Accountrequest for user " + request.getGivenName());
        }
    }

    /**
     * Approve a new or modified account group request
     * @param approver
     * @param user
     * @param group
     * @param mailMessage
     * @throws PermissionDeniedException
     */
    public void approveGroupRequest(User approver, User user, PortletGroup group, MailMessage mailMessage)
            throws PermissionDeniedException {
        if (isAdminUser(approver, group) || isSuperUser(approver)) {
            try {
                aclManager.approveUserInGroup(user, group);
            } catch (PortletServiceException e) {
                log.error("PortletService Exeption " + e);
            }
            // Mail the user
            try {
                if (mailMessage != null)
                    MailUtils.sendMail(mailMessage, "localhost");
            } catch (MessagingException e) {
                log.error("Unable to send mail: ", e);
            }
        } else {
            throw new PermissionDeniedException("User " + approver.getGivenName() + " is not allowed to approve the " +
                    group.getName() + " group");
        }
    }

    /**
     * Deny a new or modified account group request
     * @param denier
     * @param user
     * @param group
     * @param mailMessage
     * @throws PermissionDeniedException
     */
    public void denyGroupRequest(User denier, User user, PortletGroup group, MailMessage mailMessage)
            throws PermissionDeniedException {

        if (isAdminUser(denier, group) || (isSuperUser(denier))) {
            try {
                aclManager.removeUserGroupRequest(user, group);
                // Mail the user
                if (mailMessage != null) {
                    MailUtils.sendMail(mailMessage, "localhost");
                }
            } catch (PortletServiceException e) {
                log.error("Exception " + e);
            } catch (MessagingException e) {
                log.error("Unable to send mail: ", e);
            }
        } else {
            throw new PermissionDeniedException("Permission Denied!");
        }
    }

    /**
     * Returns the users portlet data for the specified portlet
     *
     * @param User the user
     * @param portletID the concrete portlet id
     * @return the PortletData for this portlet or null if none exists.
     */
    public PortletData getPortletData(User user, String portletID) {

        if (user instanceof GuestUser) return null;

        String command =
            "select u from "+jdoPDImpl+" u where u.UserID=\""+user.getID()+"\" and u.PortletID=\""+portletID+"\"";

        SportletData pd = null;
        try {
            pd = (SportletData)pm.restoreObject(command);
        } catch (PersistenceManagerException e) {

        }

        if (pd==null) {
            pd = new SportletData();
            pd.setPortletID(portletID);
            pd.setUserID(user.getID());
            try {
                pm.create(pd);
            } catch (PersistenceManagerException e) {

            }
        }
        return pd;
    }

    /**
     * Makes the users persistent portlet data persistent
     *
     * @param User the user
     * @param portletID the concrete portlet id
     * @param data the PortletData
     */
    public void setPortletData(User user, String portletID, PortletData data) {

        SportletData sd = (SportletData)data;
        //sd.setPortletID(portletID);
        //sd.setUserID(user.getID());
        try {
            pm.update(sd);
        } catch (PersistenceManagerException e) {
            log.error("Persistence Exception !"+e);
        }
    }

    /**
     * Retrieves a user object with the given username from this service.
     * Requires a user with the "super user" privileges, since this
     * by-passes the normal login mechanism of retrieving a user object.
     *
     * @param User The super user requesting the user object
     * @param String The user name or login id of the user in question
     * @throws PermissionDeniedException If approver is not a super user
     */
    public User getUser(User approver, String userName)
            throws PermissionDeniedException {
        if (isSuperUser(approver)) {
            return getUser(userName);
        } else {
            throw new PermissionDeniedException("User "
                                               + approver.getGivenName()
                                               + " wanted to retrieve "
                                               + userName + " (denied)");
        }
    }

    /**
     * Remove a user permanently.
     * @param approver
     * @param userN
     * @throws PermissionDeniedException
     */
    public void saveUser(User approver, User user) throws PermissionDeniedException {
        if (isSuperUser(approver)) {
            saveUser(user);
        } else {
            throw new PermissionDeniedException("User "
                                               + approver.getGivenName()
                                               + " wanted to save "
                                               + user.getUserID() + " (denied)");
        }
    }

    /**
     * Remove a user permanently.
     * @param approver
     * @param userName
     * @throws PermissionDeniedException
     */
    public void removeUser(User approver, String userName) throws PermissionDeniedException {
        if (isSuperUser(approver)) {
            removeUser(userName);
        } else {
            throw new PermissionDeniedException("User "
                                               + approver.getGivenName()
                                               + " wanted to retrieve "
                                               + userName + " (denied)");
        }
    }

    /**
     * Return a list of all portal users
     * @return  list of user objects
     */
    public List getAllUsers() {
        String command =
                "select user from "+jdoSUImpl+" user";
        List result = null;
        try {
            result = pm.restoreList(command);
        } catch (PersistenceManagerException e) {
            log.error("Exception " + e);
        }
        return result;
    }

    /**
     * Gets a user by his loginname
     * @param name loginname
     * @return requested user object
     */
    public User getUser(String name) {
        String command =
                "select u from "+jdoSUImpl+" u where UserID=\"" + name + "\"";
        return selectUser(command);

    }

    /**
     * Used internally by other methods in this class
     */
    private SportletUserImpl getSportletUser(String name) {
        String command =
                "select u from "+jdoSUImpl+" u where UserID=\"" + name + "\"";
        return selectUser(command);

    }

    /**
     * Gets a user by the unique ID
     * @param ID unique ID
     * @return requested user
     */
    public User getUserByID(String ID) {
        String command =
                "select u from "+jdoSUImpl+" u where ObjectID=\"" + ID + "\"";
        return selectUser(command);
    }

    /**
     * Used internally by other methods in this classs.
     * Returns a sportlet user with given oql.
     *
     * @param command the oql query
     * @return the requested user
     */
    private SportletUserImpl selectUser(String oql) {
        SportletUserImpl user = null;
        try {
            user = (SportletUserImpl) pm.restoreObject(oql);
        } catch (PersistenceManagerException e) {
            log.error("PM Exception :" + e.toString());
        }
        return user;
    }

    /**
     * save the userobjects to the database
     * @param user the userobject
     * @todo check/pass up the exception
     */
    public void saveUser(User user) {
        try {
            if (existsUserID(user.getID())) {
                pm.update(user);
            } else {
                pm.create(user);
            }
        } catch (PersistenceManagerException e) {
            log.error("PM Exception :" + e.toString());
        }
    }

    /**
     * save the userobjects to the database
     * @param user the userobject
     * @todo check/pass up the exception
     */
    public void removeUser(String userName) {
        if (existsUser(userName)) {
            User user = getUser(userName);
            try {
                List groups = aclService.getGroups(user);
                for (int i = 0; i < groups.size(); i++) {
                    PortletGroup group = (PortletGroup)groups.get(i);
                    if (group == null) {
                        log.error("Why in the hell is this group object null?!!!!!!");
                    }
                    aclManager.removeUserFromGroup(user, group);
                }
                pm.delete(user);
            } catch (PortletServiceException e) {
                log.error("Could not delete the ACL of the user " + e);
            } catch (PersistenceManagerException e) {
                log.error("Could not delete User " + e);
            }
        } else {
            log.debug("User " + userName + " does not exist in database.");
        }
    }

    /**
     * Checks to see if account exists for a user
     * @param userName
     * @return true/false if user exists/not exists
     */
    public boolean existsUser(String userName) {
        String command =
                "select user from "+jdoSUImpl+" user where UserID=\"" + userName + "\"";
        SportletUser user = null;
        try {
            user = (SportletUser) pm.restoreObject(command);
        } catch (PersistenceManagerException e) {
            log.error("Exception " + e);
        }
        return !(user == null);
    }

    /**
     * Checks if a user with a given unique ID already exists
     *
     * @param ID the unique ID
     * @return true/false if the user exists
     */
    public boolean existsUserID(String ID) {
        String command =
                "select user from "+jdoSUImpl+" user where ObjectID=\"" + ID + "\"";
        SportletUser user = null;
        try {
            user = (SportletUser) pm.restoreObject(command);
        } catch (PersistenceManagerException e) {
            log.error("Exception " + e);
        }
        return !(user == null);
    }

    /**
     * checks if the user is superuser
     *
     * @param user userobject to be examined
     * @return true is the user is usperuser, false otherwise
     */
    public boolean isSuperUser(User user) {
        try {
            return aclService.hasRoleInGroup(user, PortletGroup.SUPER, PortletRole.SUPER);
        } catch (PortletServiceException e) {
            log.error("Exception :" + e);
            return false;
        }
    }

    /**
     * Checks if the user is an adminuser in a given group
     * @param user the user
     * @param group in that group
     * @return true/false if he is an admin
     */
    public boolean isAdminUser(User user, PortletGroup group) {
        try {
            return aclService.hasRoleInGroup(user, group, PortletRole.ADMIN);
        } catch (PortletServiceException e) {
            log.error("Exception :" + e);
            return false;
        }
    }
}
