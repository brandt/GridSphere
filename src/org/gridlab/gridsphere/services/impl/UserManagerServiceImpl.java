/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.impl.AccountRequest;
import org.gridlab.gridsphere.services.UserManagerService;

import java.util.List;
import java.util.Vector;

/**
 * The UserManagerService manages users and account requests. Thru the UserManagerService
 * new portal accounts can be requested and granted or denied. User objects can be retrieved
 * and removed.
 */
public class UserManagerServiceImpl implements PortletServiceProvider, UserManagerService {

    private static PortletLog log = SportletLog.getInstance(UserManagerServiceImpl.class);

    private static List accountRequests = new Vector();

    public void init(PortletServiceConfig config) {
        log.info("in init()");
    }

    public void destroy() {
        log.info("in destroy()");
    }

    /**
     * Create a new account request
     */
    public AccountRequest createAccountRequest() {
        return new AccountRequest();
    }

    /**
     * Submit the account request to the queue for administrative approval
     */
    public void submitAccountRequest(AccountRequest request) {
        accountRequests.add(request);
    }

    /**
     * Administrators can retrieve all pending account request
     */
    public List getAccountRequests() {
        return accountRequests;
    }

    /**
     * Approve a new or modified account request
     */
    public void approveAccountRequest(AccountRequest request) {
        if (!accountRequests.contains(request)) return;

        // Transform AccountRequest into User object
        accountRequests.remove(request);

    }

    /**
     * Deny a new or modified account request
     */
    public void denyAccountRequest(AccountRequest request) {

    }

    /**
     * Modify an existing user account. Changes must be approved
     */
    public AccountRequest changeAccountRequest(User user) {
        return null;
    }

    /**
     * Retrieve a user
     */
    public User retrieveUser(String userName) {
        return null;
    }

    /**
     * Save user to DB
     */
    public void saveUser(String userName) {

    }

    /**
     * Remove a user permanently!
     */
    public void removeUser(String userName) {

    }

    /**
     * Checks to see if account exists for a user
     */
    public boolean existsUser(String userName) {
        return false;
    }

    /**
     * Return a list of all portal users
     */
    public List getAllUsers() {
        return null;
    }

    /**
     * Return a list of users currently logged in
     */
    public List getActiveUsers() {
        return null;
    }

}
