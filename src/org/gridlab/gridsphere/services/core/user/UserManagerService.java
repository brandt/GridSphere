/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import org.gridlab.gridsphere.portlet.User;

import java.util.List;

public interface UserManagerService  extends LoginUserModule {

    /**
      * Administrators can retrieve all pending account request
      *
      * @return a list of pending account requests
      */
    public List getAccountRequests();

    /**
     * Returns the account request for the given user id
     *
     * @param oid the user id of account request
     * @return account request for given user id
     */
    public AccountRequest getAccountRequest(String oid);

    /**
      * Create a new account request. An internal ID is assigned the request
      *
      * @return a new AccountRequest
      */
    public AccountRequest createAccountRequest();

    /**
      * Create a change account request. An internal ID is assigned the request
      *
      * @return a new AccountRequest
      */
    public AccountRequest createAccountRequest(User user);

    /**
     * Validate account request.
     *
     * @param request the <code>AccountRequest</code> to submit
     */
    public void validateAccountRequest(AccountRequest request)
            throws InvalidAccountRequestException;

    /**
     * Submit the account request to the queue for administrative approval
     *
     * @param request the <code>AccountRequest</code> to submit
     */
    public void submitAccountRequest(AccountRequest request)
            throws InvalidAccountRequestException;

    /**
      * Approve a new or modified account request.
      * If mailMessage is non-null, a mail message will be sent out to the account requestor
      *
      * @param request the <code>AccountRequest</code> to approve
      */
    public User approveAccountRequest(AccountRequest request);

    /**
      * Deny a new or modified account request.
      *
      * @param request the <code>AccountRequest</code> to approve
      */
    public void denyAccountRequest(AccountRequest request);

    /**
      * Delete an account
      *
      * @param user the user
      */
    public void deleteAccount(User user);

    /**
      * Administrators can retrieve all pending account request
      *
      * @return a list of pending account requests
      */
    public List getUsers();

    /**
      * Retrieves a user object with the given id from this service.
      *
      * @param id the user name or login id of the user in question
      */
    public User getUser(String id);

    /**
      * Retrieves a user object with the given username from this service.
      *
      * @param loginName the user name or login id of the user in question
      */
    public User getUserByUserName(String loginName);

    /**
      * Checks to see if account exists for a user
      *
      * @param loginName the user login ID
      * @return true if the user exists, false otherwise
      */
    public boolean existsUserName(String loginName);

    public boolean canUserCreateNewAccount();

    public void setUserCreateNewAccount(boolean canUserCreateAccount);

}
