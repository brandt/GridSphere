/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user.impl;

import org.gridlab.gridsphere.core.mail.MailMessage;
import org.gridlab.gridsphere.core.mail.MailUtils;
import org.gridlab.gridsphere.core.persistence.ConfigurationException;
import org.gridlab.gridsphere.core.persistence.CreateException;
import org.gridlab.gridsphere.core.persistence.PersistenceException;
import org.gridlab.gridsphere.core.persistence.RestoreException;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
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
    private static AccessControlManagerService aclManagerService = null;

    private PersistenceManagerRdbms pm = null;

    public UserManagerServiceImpl() {
        super();
        pm = new PersistenceManagerRdbms();

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
        try {
            aclService = (AccessControlService) factory.createPortletService(AccessControlService.class, config.getServletConfig(), true);
            aclManagerService = (AccessControlManagerService) factory.createPortletService(AccessControlManagerService.class, config.getServletConfig(), true);
            log.info("in init()");
        } catch (PortletServiceNotFoundException e) {
            throw new PortletServiceUnavailableException("Unable to find portlet services: AccessControlService and AccessControlManagerService");
        }
    }

    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy() {
        log.info("in destroy()");

        // save structures to DB
        save();
    }

    protected void load() {

    }

    protected void save() {

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
     * Submit the account request to the queue for administrative approval
     * @param request
     * @throws PortletServiceException
     */
    public void submitAccountRequest(AccountRequest request) throws PortletServiceException {

        try {
            pm.create(request);
        } catch (ConfigurationException e) {
            log.info("conf error " + e);
        } catch (CreateException e) {
            log.info("create error " + e);
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
                "select ar from org.gridlab.gridsphere.services.user.impl.AccountRequestImpl ar";
        List requests = null;
        try {
            requests = pm.restoreList(command);
        } catch (ConfigurationException e) {
            log.info("Config error " + e);
        } catch (RestoreException e) {
            log.info("Restore error " + e);
        }
        return requests;
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

        //@todo check if a user with that userid already exists!
        if (isSuperuser(approver)) {
            String userid = request.getID();
            AccountRequestImpl requestImpl = null;
            String command =
                    "select ar from org.gridlab.gridsphere.services.user.impl.AccountRequestImpl ar where ar.ObjectID=" + userid;
            try {
                requestImpl = (AccountRequestImpl) pm.restoreObject(command);
            } catch (PersistenceException e) {
                log.error("PM Exception :" + e);
            }

            SportletUserImpl user = null;

            // now need to check wheter new account or an existing should be modified
            if (existsUserID(userid)) {
                user = (SportletUserImpl) modifyExistingUser(requestImpl, (SportletUser) getUserByID(userid));
            } else {
                // make a new user
                user = (SportletUserImpl) makeNewUser(requestImpl);
            }
            // now delete the request and create/update the user
            try {
                if (existsUserID(userid)) {
                    pm.update(user);
                } else {
                    pm.create(user);
                }
                pm.delete(requestImpl);
            } catch (PersistenceException e) {
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
            log.info("User '" + approver.getGivenName() + "' tried to approve User '" + request.getGivenName() + "' (denied) ");
            throw new PermissionDeniedException("Permission denied ");
        }
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
        if (isSuperuser(denier)) {
            // @TODO share code with approveAccountrequest

            String userid = request.getID();
            AccountRequestImpl requestImpl = null;
            String command =
                    "select ar from org.gridlab.gridsphere.services.user.impl.AccountRequestImpl ar where ar.ObjectID=" + userid;
            String command2 =
                    "select acl from org.gridlab.gridsphere.services.security.acl.impl2.UserACL acl where " +
                    "UserID=\"" + request.getID() + "\"";

            try {
                requestImpl = (AccountRequestImpl) pm.restoreObject(command);
                pm.delete(requestImpl);
                // only delete the requested groups when the user did not exist before!
                if (!existsUser(userid)) {
                    pm.deleteList(command2);
                }
            } catch (PersistenceException e) {
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
            throw new PermissionDeniedException("Permission denied to deny Accounrequest for user " + request.getGivenName());
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
        if (isAdminuser(approver, group) || isSuperuser(approver)) {
            try {
                aclManagerService.approveUserInGroup(user, group);
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

        if (isAdminuser(denier, group) || (isSuperuser(denier))) {
            try {
                aclManagerService.removeUserGroupRequest(user, group);
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
     * Gets a user by a oql query
     *
     * @param command the oql query
     * @return the requested user
     */
    private User getUser(String command) {
        SportletUserImpl user = null;
        try {
            user = (SportletUserImpl) pm.restoreObject(command);
        } catch (PersistenceException e) {
            log.error("PM Exception :" + e.toString());
        }
        return user;
    }

    /**
     * Gets a user by the unique ID
     * @param ID unique ID
     * @return requested user
     */
    private User getUserByID(String ID) {
        String command =
                "select u from org.gridlab.gridsphere.portlet.impl.SportletUserImpl u where ObjectID=\"" + ID + "\"";
        return getUser(command);
    }

    /**
     * Gets a user by his loginname
     * @param name loginname
     * @return requested user object
     */
    private User getUserByName(String name) {
        String command =
                "select u from org.gridlab.gridsphere.portlet.impl.SportletUserImpl u where UserID=\"" + name + "\"";
        return getUser(command);

    }

    /**
     * loginUser retrieves a new user
     *
     * @param userName
     * @return the correspodning userobject
     * @see User
     */
    public User loginUser(String userName) {
        // same as getUser, we do add the user somewhere to the active users
        String command =
                "select u from org.gridlab.gridsphere.portlet.impl.SportletUserImpl u where UserID=\"" + userName + "\"";
        return getUser(command);
    }

    //@todo fill in logoffuser
    /**
     * logoffUser release user information and serializes to DB
     */
    public void logoffUser(User user) {

    }

    //@todo fill in saveuser
    /**
     * Save user to DB
     */
    public void saveUser(String userName) {

    }

    /**
     * Remove a user permanently.
     * @param approver
     * @param userName
     * @throws PermissionDeniedException
     */
    public void removeUser(User approver, String userName) throws PermissionDeniedException {

        if (isSuperuser(approver)) {
            if (existsUser(userName)) {
                User user = loginUser(userName);
                try {
                    List result = aclService.getGroups(user);
                    if (result.size() > 0) {
                        for (int i = 0; i < result.size(); i++) {
                            aclManagerService.removeUserFromGroup(user, ((PortletGroup) result.get(i)));
                        }
                    }
                    pm.delete(user);
                } catch (PortletServiceException e) {
                    log.error("Could not delete the ACL of the user " + e);
                } catch (PersistenceException e) {
                    log.error("Could not delete User " + e);
                }
            }
        } else {
            throw new PermissionDeniedException("User " + approver.getGivenName() + " wanted to delete " + userName + " (denied)");
        }
    }

    /**
     * Checks to see if account exists for a user
     * @param userName
     * @return true/false if user exists/not exists
     */
    public boolean existsUser(String userName) {
        String command =
                "select user from org.gridlab.gridsphere.portlet.impl.SportletUserImpl user where UserID=\"" + userName + "\"";
        SportletUser user = null;
        try {
            user = (SportletUser) pm.restoreObject(command);
        } catch (PersistenceException e) {
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
                "select user from org.gridlab.gridsphere.portlet.impl.SportletUserImpl user where ObjectID=\"" + ID + "\"";
        SportletUser user = null;
        try {
            user = (SportletUser) pm.restoreObject(command);
        } catch (PersistenceException e) {
            log.error("Exception " + e);
        }
        return !(user == null);
    }

    /**
     * Return a list of all portal users
     * @return  list of user objects
     */
    public List getAllUsers() {

        String command =
                "select user from org.gridlab.gridsphere.portlet.impl.SportletUserImpl user";
        List result = null;
        try {
            result = pm.restoreList(command);
        } catch (PersistenceException e) {
            log.error("Exception " + e);
        }
        return result;
    }

    //@todo fill in getActiveUser
    /**
     * Return a list of users currently logged in
     */
    public List getActiveUsers() {
        return null;
    }


    /**
     * Creates a new SportletUser from an AccountRequestImpl
     */
    protected SportletUser makeNewUser(AccountRequestImpl requestImpl) {
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
    protected SportletUser modifyExistingUser(AccountRequestImpl requestImpl, SportletUser user) {
        user.setEmailAddress(requestImpl.getEmailAddress());
        user.setFamilyName(requestImpl.getFamilyName());
        user.setFullName(requestImpl.getFullName());
        user.setGivenName(requestImpl.getGivenName());
        user.setID("" + requestImpl.getID());
        user.setUserID(requestImpl.getUserID());
        return user;
    }

    /**
     * checks if the user is superuser
     *
     * @param user userobject to be examined
     * @return true is the user is usperuser, false otherwise
     */
    public boolean isSuperuser(User user) {

        try {
            return aclService.hasRoleInGroup(user, SportletGroup.getSuperGroup(), SportletRole.getSuperRole());
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
    public boolean isAdminuser(User user, PortletGroup group) {
        try {
            return aclService.hasRoleInGroup(user, group, SportletRole.getAdminRole());
        } catch (PortletServiceException e) {
            log.error("Exception :" + e);
            return false;
        }
    }

}
