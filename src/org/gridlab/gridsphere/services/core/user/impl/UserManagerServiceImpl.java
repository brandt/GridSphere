/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.core.mail.MailMessage;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.InvalidAccountRequestException;
import org.gridlab.gridsphere.services.core.user.UserManagerService;

import java.util.List;

public class UserManagerServiceImpl implements UserManagerService, PortletServiceProvider {

    private GridSphereUserManager userManager = GridSphereUserManager.getInstance();
    private PortletServiceAuthorizer authorizer = null;

    public UserManagerServiceImpl(PortletServiceAuthorizer authorizer) {
        this.authorizer = authorizer;
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
        userManager.init(config);
    }

    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy() {}

    /**
      * Administrators can retrieve all pending account request
      *
      * @return a list of pending account requests
      */
    public List getAccountRequests() {
        return userManager.getAccountRequests();
    }

    /**
     * Returns the account request for the given user id
     *
     * @param oid of account request
     * @return account request for given user id
     */
    public AccountRequest getAccountRequest(String oid) {
        return userManager.getAccountRequest(oid);
    }

    /**
      * Create a new account request. An internal ID is assigned the request
      *
      * @return a new AccountRequest
      */
    public AccountRequest createAccountRequest() {
        return userManager.createAccountRequest();
    }

    /**
      * Create a change account request. An internal ID is assigned the request
      *
     * @param user the user
      * @return a new AccountRequest
      */
    public AccountRequest createAccountRequest(User user) {
        return userManager.createAccountRequest(user);
    }

    /**
     * Validate account request.
     *
     * @param request the <code>AccountRequest</code> to submit
     */
    public void validateAccountRequest(AccountRequest request)
            throws InvalidAccountRequestException {
        userManager.validateAccountRequest(request);
    }

    /**
     * Submit the account request to the queue for administrative approval
     *
     * @param request the <code>AccountRequest</code> to submit
     */
    public void submitAccountRequest(AccountRequest request)
            throws InvalidAccountRequestException {
        userManager.submitAccountRequest(request);
    }

     /**
      * Submit the account request to the queue for administrative approval
      *
      * @param request the <code>AccountRequest</code> to submit
      */
    public void submitAccountRequest(AccountRequest request, MailMessage mailMessage)
            throws InvalidAccountRequestException {
         userManager.submitAccountRequest(request, mailMessage);
     }

    /**
      * Approve a new or modified account request.
      * If mailMessage is non-null, a mail message will be sent out to the account requestor
      *
      * @param request the <code>AccountRequest</code> to approve
      */
    public User approveAccountRequest(AccountRequest request) {
        return userManager.approveAccountRequest(request);
    }

    /**
      * Approve a new or modified account request.
      * If mailMessage is non-null, a mail message will be sent out to the account requestor
      *
      * @param request the <code>AccountRequest</code> to approve
      * @param mailMessage the <code>MailMessage</code> to use for notification
      */
    public User approveAccountRequest(AccountRequest request, MailMessage mailMessage) {
        return userManager.approveAccountRequest(request, mailMessage);
    }

    /**
      * Approve a new or modified account request.
      * If mailMessage is non-null, a mail message will be sent out to the account requestor
      *
      * @param request the <code>AccountRequest</code> to approve
      */
    public void denyAccountRequest(AccountRequest request) {
        userManager.denyAccountRequest(request);
    }

    /**
      * Approve a new or modified account request.
      * If mailMessage is non-null, a mail message will be sent out to the account requestor
      *
      * @param request the <code>AccountRequest</code> to approve
      * @param mailMessage the <code>MailMessage</code> to use for notification
      */
    public void denyAccountRequest(AccountRequest request, MailMessage mailMessage) {
        userManager.denyAccountRequest(request, mailMessage);
    }

    /**
      * Approve a new or modified account request.
      * If mailMessage is non-null, a mail message will be sent out to the account requestor
      *
      * @param user the <code>Account</code> to approve
      */
    public void deleteAccount(User user) {
        userManager.deleteAccount(user);
    }

    /**
      * Administrators can retrieve all pending account request
      *
      * @return a list of pending account requests
      */
    public List getUsers() {
        return userManager.getUsers();
    }

    /**
      * Retrieves a user object with the given id
      *
      * @param id the login id of the user in question
      */
    public User getUser(String id) {
        return userManager.getUser(id);
    }

    /**
      * Retrieves a user object with the given username from this service.
    *
      * @param loginName the user name or login id of the user in question
      */
    public User getUserByUserName(String loginName) {
        return userManager.getUserByUserName(loginName);
    }

    public User getLoggedInUser(String loginName) {
        return userManager.getLoggedInUser(loginName);
    }

    /**
      * Checks to see if account exists for a user
      *
      * @param loginName the user login ID
      * @return true if the user exists, false otherwise
      */
    public boolean existsUserName(String loginName) {
        return userManager.existsUserName(loginName);
    }

}
