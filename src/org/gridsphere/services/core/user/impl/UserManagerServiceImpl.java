/*
 *
 * @version: $Id: UserManagerServiceImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.user.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.persistence.QueryFilter;
import org.gridsphere.services.core.user.User;
import org.gridsphere.services.core.user.UserManagerService;

import java.util.ArrayList;
import java.util.List;

public class UserManagerServiceImpl implements PortletServiceProvider, UserManagerService {

    private static Log log = LogFactory.getLog(UserManagerServiceImpl.class);

    private static PersistenceManagerRdbms pm = null;

    private String jdoUser = UserImpl.class.getName();

    public UserManagerServiceImpl() {
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        PersistenceManagerService pmservice = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pm = pmservice.createGridSphereRdbms();
    }

    public void destroy() {
        log.info("Calling destroy()");
    }

    /**
     * Creates a new user
     *
     * @return a blank user
     */
    public User createUser() {
        UserImpl user = new UserImpl();
        saveSportletUserImpl(user);
        return user;
    }

    /**
     * Adds a user
     *
     * @param user a supplied User object
     */
    public void saveUser(User user) {
        if (user instanceof UserImpl) {
            UserImpl impl = (UserImpl) user;
            saveSportletUserImpl(impl);
        }
    }

    public void deleteUser(User user) {
        if (user instanceof UserImpl) {
            // delete user object
            deleteSportletUserImpl((UserImpl) user);
            // Send message if not null
        }
    }

    public int getNumUsers() {
            String oql = "select count(*) from "
                    + this.jdoUser;
            return pm.count(oql);
    }

    public List<User> selectUsers(String criteria, QueryFilter queryFilter) {
        String oql = "select uzer from "
                + this.jdoUser
                + " uzer "
                + criteria;

        List<User> userList = (List<User>) pm.restoreList(oql, queryFilter);
        if (userList == null) userList = new ArrayList<User>();
        return userList;
    }


    public List<User> getUsersByUserName(QueryFilter queryFilter) {
        return selectUsers("order by uzer.UserID", queryFilter);
    }

    public List<User> getUsersByOrganization(QueryFilter queryFilter) {
        return selectUsers("order by uzer.Organization", queryFilter);
    }

    public List<User> getUsersByFullName(QueryFilter queryFilter) {
        return selectUsers("order by upper(uzer.FullName)", queryFilter);
    }

    public List<User> getUsersByNumLogins(QueryFilter queryFilter) {
        return selectUsers("order by uzer.NumLogins", queryFilter);
    }

    public List<User> getUsersByFullName(String likeEmail, String likeOrg, QueryFilter queryFilter) {
        String query = "";
        String equery = "";
        String oquery = "";
        if (!likeEmail.equals("") || !likeOrg.equals("")) {
            query += "where ";
            if (!likeEmail.equals("")) {
                equery = "upper(uzer.EmailAddress) like '%" + likeEmail.toUpperCase() + "%' ";
            }
            if (!likeOrg.equals("")) {
                oquery = "upper(uzer.Organization) like '%" + likeOrg.toUpperCase() + "%' ";
            }
            if (!equery.equals("") && !oquery.equals("")) {
                query += equery + " and " + oquery;
            } else {
                query += equery + oquery;
            }
        }
        return selectUsers(query + " order by upper(uzer.FullName)", queryFilter);
    }

    public List<User> getUsersByEmail(QueryFilter queryFilter) {
        return selectUsers("order by uzer.EmailAddress", queryFilter);
    }

    public List<User> getUsers(QueryFilter queryFilter) {
        return selectUsers("", queryFilter);
    }

    public List<User> getUsers() {
        return selectUsers("", null);
    }

    public User getUser(String id) {
        return getSportletUserImpl(id);
    }

    public User getUserByUserName(String loginName) {
        return getSportletUserImplByLoginName(loginName);
    }


    /**
     * Retrieves a user object with the given email from this service.
     *
     * @param email the user's email address
     */
    public User getUserByEmail(String email) {
        return selectSportletUserImpl("where uzer.EmailAddress='" + email + "'");
    }

    /**
     * Retrieves users based on attribute criteria
     *
     * @param attrName  the attribute name
     * @param attrValue the attribute value
     */
    public List<User> getUsersByAttribute(String attrName, String attrValue, QueryFilter queryFilter) {
        String criteria = "where uzer.attributes['user." + attrName + "'] = '" + attrValue + "'";
        return selectUsers(criteria, queryFilter);
    }

    private UserImpl getSportletUserImpl(String id) {
        return selectSportletUserImpl("where uzer.oid='" + id + "'");
    }

    private UserImpl getSportletUserImplByLoginName(String loginName) {
        return selectSportletUserImpl("where uzer.UserID='" + loginName + "'");
    }

    private UserImpl selectSportletUserImpl(String criteria) {
        String oql = "select uzer from "
                + jdoUser
                + " uzer "
                + criteria;

        return (UserImpl) pm.restore(oql);
    }

    private void saveSportletUserImpl(UserImpl user) {
        // Create or update user
        pm.saveOrUpdate(user);
    }

    private void deleteSportletUserImpl(UserImpl user) {
        pm.delete(user);
    }

    public boolean existsUserWithID(String userID) {
        String criteria = "where uzer.oid='" + userID + "'";
        return existsSportletUserImpl(criteria);
    }

    public boolean existsUserName(String loginName) {
        String criteria = "where uzer.UserID='" + loginName + "'";
        return existsSportletUserImpl(criteria);
    }

    private boolean existsSportletUserImpl(String criteria) {
        String oql = "select uzer from "
                + jdoUser
                + " uzer "
                + criteria;
        UserImpl sui = (UserImpl) pm.restore(oql);
        return (sui != null);
    }

}
