/*
 * @version: $Id: UserManagerService.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.user;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.services.core.persistence.QueryFilter;

import java.util.List;

public interface UserManagerService extends PortletService {

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
     * @param queryFilter queryFilter
     * @return a list of users
     */
    public List<User> getUsers(QueryFilter queryFilter);

    /**
     * Returns the number of users.
     *
     * @return number of users.
     */
    public int getNumUsers();

    /**
     * Return the list of users in an unsorted list
     *
     * @return a list of users
     */
    public List<User> getUsers();

    /**
     * Returns a list of users by name.
     *
     * @param queryFilter QueryFilter
     * @return list of users
     */
    public List<User> getUsersByUserName(QueryFilter queryFilter);

    /**
     * Returns a list of users by organization.
     *
     * @param queryFilter QueryFilter
     * @return list of users
     */
    public List<User> getUsersByOrganization(QueryFilter queryFilter);

    /**
     * Returns a list of users sorted by number of login occurences
     *
     * @param queryFilter QueryFilter
     * @return list of users
     */
    public List<User> getUsersByNumLogins(QueryFilter queryFilter);

    /**
     * Returns a list of users by full name.
     *
     * @param queryFilter QueryFilter
     * @return list of users
     */
    public List<User> getUsersByFullName(QueryFilter queryFilter);


    /**
     * Returns a list of users by Email.
     *
     * @param queryFilter QueryFilter
     * @return list of users
     */
    public List<User> getUsersByEmail(QueryFilter queryFilter);

    /**
     * Returns a list of users by Email and Organization
     *
     * @param likeEmail   email
     * @param likeOrg     organization
     * @param queryFilter queryFilter
     * @return List of users
     */
    public List<User> getUsersByFullName(String likeEmail, String likeOrg, QueryFilter queryFilter);

    /**
     * Retrieves a user object with the oid of the user.
     *
     * @param oid the objectid of the user in question
     * @return userobject in question
     */
    public User getUser(String oid);

    /**
     * Retrieves a user object with the given username from this service.
     *
     * @param loginName the user name or login id of the user in question
     * @return userobject in question
     */
    public User getUserByUserName(String loginName);

    /**
     * Retrieves users based on attribute criteria
     *
     * @param attrName    the attribute name
     * @param attrValue   the attribute value
     * @param queryFilter queryFilter
     * @return List of users matching the query
     */
    public List<User> getUsersByAttribute(String attrName, String attrValue, QueryFilter queryFilter);

    /**
     * Retrieves a user object with the given email from this service.
     *
     * @param email the user's email address
     * @return User in question
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
