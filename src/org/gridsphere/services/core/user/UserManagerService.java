/*
 * @version: $Id: UserManagerService.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.user;

import org.gridsphere.portlet.User;
import org.gridsphere.services.core.persistence.QueryFilter;
import org.gridsphere.services.core.security.auth.LoginUserModule;

import java.util.List;

public interface UserManagerService extends LoginUserModule {

    /**
     * Creates a new user
     *
     * @return a blank user
     */
    public User createUser();

    /**
     * Adds a user
     *
     * @param user a supplied User object
     */
    public void saveUser(User user);

    /**
     * Delete a user
     *
     * @param user the user
     */
    public void deleteUser(User user);

    /**
     * Return the list of users in an unsorted list
     *
     * @return a list of users
     */
    public List getUsers(QueryFilter queryFilter);

    public int getNumUsers();

    /**
     * Return the list of users in an unsorted list
     *
     * @return a list of users
     */
    public List getUsers();

    public List getUsersByUserName(QueryFilter queryFilter);

    public List getUsersByOrganization(QueryFilter queryFilter);

    public List getUsersByFullName(QueryFilter queryFilter);

    public List getUsersByFullName(String likeEmail, String likeOrg, QueryFilter queryFilter);

    public List getUsersByEmail(QueryFilter queryFilter);

    /**
     * Retrieves a user object with the given user name from this service.
     *
     * @param userName the user name or login id of the user in question
     */
    public User getUser(String userName);

    /**
     * Retrieves a user object with the given username from this service.
     *
     * @param loginName the user name or login id of the user in question
     */
    public User getUserByUserName(String loginName);

    /**
     * Retrieves users based on attribute criteria
     *
     * @param attrName the attribute name
     * @param attrValue the attribute value
     */
    public List getUsersByAttribute(String attrName, String attrValue, QueryFilter queryFilter);

    /**
     * Retrieves a user object with the given email from this service.
     *
     * @param email the user's email address
     */
    public User getUserByEmail(String email);


    /**
     * Checks to see if account exists for a user
     *
     * @param loginName the user login ID
     * @return true if the user exists, false otherwise
     */
    public boolean existsUserName(String loginName);

}
