/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.User;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The User interface is an abstract view on the user-specific data.
 * Apart from a set of pre-defined, fixed set of attributes,
 * the interface gives access to user data as well.
 */
public class SportletUserImpl implements SportletUser {

    // store used to maintain user attributes
    private Hashtable store = new Hashtable();


    // Data fields that make up the User object
    private String familyName;
    private String fullName;
    private String givenName;
    private String id;
    private String userID;
    private long lastLoginTime;

    /**
     * Returns the value of the attribute with the given name,
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name) {
        return store.get(name);
    }

    /**
     * Sets the value of the attribute with the given name.
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, Object value) {
        store.put(name, value);
    }

    /**
     * Returns an enumeration of names of all attributes available to this request.
     * This method returns an empty enumeration if the request has no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames() {
        return store.keys();
    }

    /**
     * Returns the family (aka last) name of the user.
     *
     * @return the family name
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Sets the family (aka last) name of the user.
     *
     * @param familyName the family name
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * Returns the full name of the user, or null if the full name is not available.
     * The full name contains given names, family names and possibly a title or suffix.
     * Therefore, the full name may be different from the concatenation of given and family name.
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name of the user, or null if the full name is not available.
     * The full name contains given names, family names and possibly a title or suffix.
     * Therefore, the full name may be different from the concatenation of given and family name.
     *
     * @param fullName the full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Returns the given (aka first) name of the user, or  if the given name is not available.
     *
     * @return the given name
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Sets the given (aka first) name of the user, or  if the given name is not available.
     *
     * @param givenName the given name
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * Returns the internal unique user id.
     *
     * @return the internal unique id
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the internal unique user id.
     *
     * @param id the internal unique id
     */
    public void setID(String id) {
        this.id = id;
    }


    /**
     * Returns the user id of the user, or null if the user id is not available.
     *
     * @return the user id
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the user id of the user, or null if the user id is not available.
     *
     * @param userID the user id
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Returns the point of time that this user was last logged in, or null if this information is not available.
     * The time is returned in number of milliseconds since January 1, 1970 GMT.
     *
     * @return the last login time
     */
    public long getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * Sets the point of time that this user was last logged in, or null if this information is not available.
     * The time is returned in number of milliseconds since January 1, 1970 GMT.
     *
     * @param lastLoginTime the last login time
     */
    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

}
