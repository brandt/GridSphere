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

import java.util.List;

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
     * Login a user
     *
     * @param userName the user to login
     * @param passWord the user's password
     * @return the retieved Role object
     */
    public User loginUser(String userName, String passWord);

    /**
     * Logoff a user
     *
     * @param user the user to logoff
     */
    public void logoutUser(User user);

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
     * Get user from DB
     */
    public User getUser(String userName);

    /**
     * Save user to DB
     */
    public void saveUser(String userName);


    /**
     * Save a user to DB
     */
    public void saveUser(User user);


    /**
     * Remove a user permanently! Requires super user
     */
    public void removeUser(User approver, String userName)
            throws PermissionDeniedException;

    /**
     * Checks to see if account exists for a user
     *
     * @param userID the user login ID
     * @return true if the user exists, false otherwise
     */
    public boolean existsUser(String userID);

    /**
     * Return a list of all portal users
     *
     * @return a list containing all Role objects
     */
    public List getAllUsers();

    /**
     * Return a list of users currently logged in
     *
     * @return a list of Users logged in
     */
    public List getActiveUsers();

}
