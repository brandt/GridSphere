/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user.impl;

import org.gridlab.gridsphere.core.mail.MailMessage;
import org.gridlab.gridsphere.portlet.PortletData;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.PermissionDeniedException;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.security.AuthenticationException;

import java.util.List;
import java.util.Map;

/**
 * The UserManagerService manages users and account requests. Thru the UserManagerService
 * new portal accounts can be requested and granted or denied. Role objects can be retrieved
 * and removed.
 */
public class SecureUserManagerService implements PortletServiceProvider, UserManagerService {

    private UserManagerServiceImpl serviceImpl = UserManagerServiceImpl.getInstance();
    private User user;

    private SecureUserManagerService() {
    }

    public SecureUserManagerService(User user) {
        this.user = user;
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
        serviceImpl.init(config);
    }

    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy() {
        serviceImpl.destroy();
        serviceImpl = null;
    }

    /**
     * Create a new account request. A unique ID is assigned to this request, that can't be modified by the client.
     * When an account request is submitted, the ID is checked
     */
    public AccountRequest createAccountRequest() {
        return serviceImpl.createAccountRequest();
    }

    /**
     * Submit the account request to the queue for administrative approval
     * @param request
     * @throws PortletServiceException
     */
    public void submitAccountRequest(AccountRequest request) throws PortletServiceException {
        serviceImpl.submitAccountRequest(request);
    }

    /**
     * Administrators can retrieve all pending account request
     */
    public List getAccountRequests() {
        return serviceImpl.getAccountRequests();
    }

    /**
     * Returns the account request for the given user id
     *
     * @param user id of account request
     * @return account request for given user id
     */
    public AccountRequest getAccountRequest(String userID) {
        return serviceImpl.getAccountRequest(userID);
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

        serviceImpl.approveAccountRequest(approver, request, mailMessage);
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
        serviceImpl.denyAccountRequest(denier, request, mailMessage);
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
        serviceImpl.approveGroupRequest(approver, user, group, mailMessage);
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

        serviceImpl.denyGroupRequest(denier, user, group, mailMessage);
    }

    /**
     * Modify an existing user account. Changes must be approved
     * @param user
     * @return
     */
    public AccountRequest changeAccountRequest(User user) {
        return serviceImpl.changeAccountRequest(user);
    }

    /**
     * Returns the users portlet data for the specified portlet
     *
     * @param User the user
     * @param portletID the concrete portlet id
     * @return the PortletData for this portlet or null if none exists.
     */
    public PortletData getPortletData(User user, String portletID) {
        return serviceImpl.getPortletData(user, portletID);
    }

    /**
     * Makes the users persistent portlet data persistent
     *
     * @param User the user
     * @param portletID the concrete portlet id
     * @param data the PortletData
     */
    public void setPortletData(User user, String portletID, PortletData data) {
        serviceImpl.setPortletData(user, portletID, data);
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
        return serviceImpl.getUser(approver, userName);
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
    public void saveUser(User approver, User user)
            throws PermissionDeniedException {
        serviceImpl.saveUser(approver, user);
    }

    /**
     * Remove a user permanently.
     * @param approver
     * @param userName
     * @throws PermissionDeniedException
     */
    public void removeUser(User approver, String userName) throws PermissionDeniedException {
        serviceImpl.removeUser(approver, userName);
    }

    /**
     * Return a list of all portal users
     * @return  list of user objects
     */
    public List getAllUsers() {
        return serviceImpl.getAllUsers();
    }

    /**
     * Gets a user by the unique ID
     * @param ID unique ID
     * @return requested user
     */
    public User getUserByID(String ID) {
        return serviceImpl.getUserByID(ID);
    }

    /**
     * save the userobjects to the database
     * @param user the userobject
     * @todo check/pass up the exception
     */
    public void removeUser(String userName) {
        serviceImpl.removeUser(userName);
    }

    /**
     * Checks to see if account exists for a user
     * @param userName
     * @return true/false if user exists/not exists
     */
    public boolean userExists(String userName) {
        return serviceImpl.userExists(userName);
    }

    /**
     * Checks if a user with a given unique ID already exists
     *
     * @param ID the unique ID
     * @return true/false if the user exists
     */
    public boolean existsUserID(String ID) {
        return serviceImpl.existsUserID(ID);
    }

    /**
     * checks if the user is superuser
     *
     * @param user userobject to be examined
     * @return true is the user is usperuser, false otherwise
     */
    public boolean isSuperUser(User user) {
        return serviceImpl.isSuperUser(user);
    }

    /**
     * Checks if the user is an adminuser in a given group
     * @param user the user
     * @param group in that group
     * @return true/false if he is an admin
     */
    public boolean isAdminUser(User user, PortletGroup group) {
        return serviceImpl.isAdminUser(user, group);
    }

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
    public User login(String loginName, String loginPassword) throws AuthenticationException {
        return serviceImpl.login(loginName, loginPassword);
    }

    /**
     * Login a user with the given login parameters.
     * Returns the associated user if login succeeds.
     * Throws an AuthenticationException if login fails.
     *
     * @param Map The login parameters.
     * @return User The associated user.
     * @throws AuthenticationException If login unsuccessful
     */
    public User login(Map parameters) throws AuthenticationException {
        return serviceImpl.login(parameters);
    }

}
