/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.core.mail.MailMessage;

import java.util.List;

/**
 * The UserManagerService manages users and account requests. Thru the UserManagerService
 * new portal accounts can be requested and granted or denied. User objects can be retrieved
 * and removed.
 */
public interface UserManagerService extends PortletService {

    /**
     * Create a new account request
     */
    public AccountRequest createAccountRequest();

    /**
     * Submit the account request to the queue for administrative approval
     */
    public void submitAccountRequest(AccountRequest request);

    /**
     * Administrators can retrieve all pending account request
     */
    public List getAccountRequests();

    /**
     * Approve a new or modified account request
     */
    public void approveAccountRequest(AccountRequest request, MailMessage mailMessage);

    /**
     * Deny a new or modified account request
     */
    public void denyAccountRequest(AccountRequest request, MailMessage mailMessage);

    /**
     * Approve a new or modified account group request
     */
    public void approveGroupRequest(AccountRequest request, String groupName, MailMessage mailMessage);

    /**
     * Deny a new or modified account group request
     */
    public void denyGroupRequest(AccountRequest request, String groupName, MailMessage mailMessage);

    /**
     * Modify an existing user account. Changes must be approved
     */
    public AccountRequest changeAccountRequest(User user);

    /**
     * Login a user
     */
    public User loginUser(String userName);

    /**
     * Logoff a user
     */
    public void logoffUser(User user);

    /**
     * Save user to DB
     */
    public void saveUser(String userName);

    /**
     * Remove a user permanently!
     */
    public void removeUser(String userName);

    /**
     * Checks to see if account exists for a user
     */
    public boolean existsUser(String userName);

    /**
     * Return a list of all portal users
     */
    public List getAllUsers();

    /**
     * Return a list of users currently logged in
     */
    public List getActiveUsers();

}
