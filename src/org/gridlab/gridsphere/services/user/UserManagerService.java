/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletData;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.core.mail.MailMessage;
import org.gridlab.gridsphere.services.security.AuthenticationException;

import java.util.List;
import java.util.Map;

/**
 * The UserManagerService manages users and account requests. Thru the UserManagerService
 * new portal accounts can be requested and granted or denied. Role objects can be retrieved
 * and removed.
 */
public interface UserManagerService extends PortletService {

    /**
     * Create a new account request. An internal ID is assigned the request
     *
     * @return a new AccountRequest
     */
    public AccountRequest createAccountRequest();

    /**
     * Submit the account request to the queue for administrative approval
     *
     * @param request the <code>AccountRequest</code> to submit
     */
    public void submitAccountRequest(AccountRequest request) throws PortletServiceException ;

    /**
     * Administrators can retrieve all pending account request
     *
     * @return a list of pending account requests
     */
    public List getAccountRequests();

    /**
     * Returns the account request for the given user id
     *
     * @param user id of account request
     * @return account request for given user id
     */
    public AccountRequest getAccountRequest(String userID);

    /**
     * Approve a new or modified account request.
     * If mailMessage is non-null, a mail message will be sent out to the account requestor
     *
     * @param request the <code>AccountRequest</code> to approve
     * @param mailMessage the <code>MailMessage</code> to use for notification
     */
    public void approveAccountRequest(User approver, AccountRequest request, MailMessage mailMessage)
            throws PermissionDeniedException;

    /**
     * Deny a new or modified account request
     * If mailMessage is non-null, a mail message will be sent out to the account requestor
     *
     * @param request the <code>AccountRequest</code> to approve
     * @param mailMessage the <code>MailMessage</code> to use for notification
     */
    public void denyAccountRequest(User denier, AccountRequest request, MailMessage mailMessage)
            throws PermissionDeniedException;

    /**
     * Approve a new or modified account group request
     * If mailMessage is non-null, a mail message will be sent out to the account requestor
     *
     * @param request the <code>User</code> to approve
     * @param group the group to approve admittance into
     * @param mailMessage the <code>MailMessage</code> to use for notification
     */
    public void approveGroupRequest(User approver, User request, PortletGroup group, MailMessage mailMessage)
            throws PermissionDeniedException;

    /**
     * Deny a new or modified account group request
     * If mailMessage is non-null, a mail message will be sent out to the account requestor
     *
     * @param request the <code>User</code> to approve
     * @param group the group to deny admittance into
     * @param mailMessage the <code>MailMessage</code> to use for notification
     */
    public void denyGroupRequest(User denier, User request, PortletGroup group, MailMessage mailMessage)
            throws PermissionDeniedException;

    /**
     * Modify an existing user account. Changes must be approved
     * (or should only some changes need to be approved??)
     *
     * @param user the Role wishing to modify their account
     * @return a new AccountRequest
     */
    public AccountRequest changeAccountRequest(User user);

    /**
     * Returns the users portlet data for the specified portlet
     *
     * @param User the user
     * @param portletID the concrete portlet id
     * @return the PortletData for this portlet or null if none exists.
     */
    public PortletData getPortletData(User user, String portletID);

    /**
     * Makes the users persistent portlet data persistent
     *
     * @param User the user
     * @param portletID the concrete portlet id
     * @param data the PortletData
     */
    public void setPortletData(User user, String portletID, PortletData data);

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
            throws PermissionDeniedException;

    /**
     * Saves a user object with the given username from this service.
     * Requires a user with the "super user" privileges.
     *
     * @param User The super user requesting the user object
     * @param String The user name or login id of the user in question
     * @throws PermissionDeniedException If approver is not a super user
     */
    public void saveUser(User approver, User user)
            throws PermissionDeniedException;

    /**
     * Removes a user object with the given username from this service.
     * Requires a user with the "super user" privileges.
     *
     * @param User The super user requesting the user object
     * @param String The user name or login id of the user in question
     * @throws PermissionDeniedException If approver is not a super user
     */
    public void removeUser(User approver, String userName)
            throws PermissionDeniedException;

    /**
     * Gets a user by the unique ID
     * @param ID unique ID
     * @return requested user
     */
    public User getUserByID(String ID);

    /**
     * Return a list of all portal users
     *
     * @return a list containing all Role objects
     */
    public List getAllUsers();

    /**
     * Checks to see if account exists for a user
     *
     * @param userID the user login ID
     * @return true if the user exists, false otherwise
     */
    public boolean userExists(String userName);

    /**
     * checks if the user is super user
     *
     * @param user userobject to be examined
     * @return true is the user is usperuser, false otherwise
     */
    public boolean isSuperUser(User user);

    /**
     * Checks if the user is an admin user in a given group
     * @param user the user
     * @param group in that group
     * @return true/false if he is an admin
     */
    public boolean isAdminUser(User user, PortletGroup group);

    /**
     * Login a user with the given login name and password.
     * Returns the associated user if login succeeds.
     * Throws an AuthenticationException if login fails.
     *
     * @param String The login name or user id.
     * @param String The login password.
     * @return User The associated user.
     * @throws AuthenticationException If login unsuccessful
     */
    public User login(String loginName, String loginPassword)
            throws AuthenticationException;

    /**
     * Login a user with the given login parameters.
     * Returns the associated user if login succeeds.
     * Throws an AuthenticationException if login fails.
     *
     * @param Map The login parameters.
     * @return User The associated user.
     * @throws AuthenticationException If login unsuccessful
     */
    public User login(Map parameters)
            throws AuthenticationException;

}
