/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id: User.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.user;

import java.util.Enumeration;

/**
 * The <code>User</code> interface is an abstract view on the user-specific data.
 * Apart from a set of pre-defined, fixed set of attributes,
 * the interface gives access to user profile data.
 */
public interface User {

    /**
     * Users locale preference stored as an attribute with this key
     */
    public static final String LOCALE = "gridsphere.user.locale";

    public static final String TIMEZONE = "gridsphere.user.timezone";

    public static final String THEME = "gridsphere.user.theme";

    public static final String DISABLED = "gridsphere.user.disabled";

    public static final String CREATEDATE = "gridsphere.user.createdate";

    public static final String LASTLOGINDATE = "gridsphere.user.lastlogindate";

    /**
     * Returns the value of the attribute with the given name,
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name);

    /**
     * Sets the value of the attribute with the given name,
     *
     * @param name  the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value);

    /**
     * Returns an enumeration of names of all attributes available to this
     * request. This method returns an empty enumeration if the request has
     * no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames();

    /**
     * Returns the last name of the user.
     *
     * @return the last name
     */
    public String getLastName();

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName);

    /**
     * Returns the family (aka last) name of the user.
     *
     * @return the family name
     */
    public String getFirstName();

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName);


    /**
     * Returns the full name of the user, or null if the full name is not
     * available. The full name contains given names, family names and possibly
     * a title or suffix. Therefore, the full name may be different from the
     * concatenation of given and family name.
     *
     * @return the full name
     */
    public String getFullName();

    /**
     * Sets the full name of the user, or null if the full name is not available.
     * The full name contains given names, family names and possibly a title or suffix.
     * Therefore, the full name may be different from the concatenation of given and family name.
     *
     * @param fullName the full name
     */
    public void setFullName(String fullName);

    /**
     * Returns the organization affiliation association of the user
     *
     * @return the organization name
     */
    public String getOrganization();

    /**
     * Sets the organization the user belongs to
     *
     * @param organization the organization name
     */
    public void setOrganization(String organization);


    /**
     * Returns the given e-mail of the user or <code>null</code> if none
     * is available.
     *
     * @return the email address
     */
    public String getEmailAddress();

    /**
     * Sets the given e-mail of the user.
     *
     * @param emailAddress the email address
     */
    public void setEmailAddress(String emailAddress);


    /**
     * Returns the internal unique user id.
     *
     * @return the internal unique user id
     */
    public String getID();

    /**
     * Sets the internal unique user id.
     *
     * @param id the internal unique id
     */
    public void setID(String id);


    /**
     * Returns the user id of the user, or <code>null</code> if the user id
     * is not available. The userid is the user's login name
     *
     * @return the user id
     * @see #getUserName
     */
    public String getUserID();

    /**
     * Sets the user id of the user, or null if the user id is not available.
     *
     * @param userID the user id
     */
    public void setUserID(String userID);

    /**
     * This is an alias for the getUserID method, which for all intensive
     * purposes represents the name required for this user to login.
     *
     * @return String the user id
     * @see #getUserID
     */
    public String getUserName();


    /**
     * This is an alias for the setUserID method, which for all intensive
     * purposes represents the name required for this user to login.
     *
     * @param userName the user name
     */
    public void setUserName(String userName);

    /**
     * Returns the point of time that this user was last logged in, or
     * <code>null</code> if this information is not available.
     * The time is returned in number of milliseconds since January 1, 1970 GMT.
     *
     * @return the last login time
     */
    public long getLastLoginTime();

    /**
     * Sets the point of time that this user was last logged in, or null if this
     * information is not available. The time is returned in number of milliseconds
     * since January 1, 1970 GMT.
     *
     * @param lastLoginTime the last login time
     */
    public void setLastLoginTime(long lastLoginTime);


    /**
     * Returns a <code>String</code> representation of the User
     *
     * @return User information represented as a <code>String</code>
     */
    public String toString();
}