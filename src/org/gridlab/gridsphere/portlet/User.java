/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.Enumeration;

/**
 * The User interface is an abstract view on the user-specific data.
 * Apart from a set of pre-defined, fixed set of attributes,
 * the interface gives access to user profile data.
 */
public interface User {

    /**
     * Returns the value of the attribute with the given name,
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name);

    /**
     * Returns an enumeration of names of all attributes available to this request.
     * This method returns an empty enumeration if the request has no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames();

    /**
     * Returns the family (aka last) name of the user.
     *
     * @return the family name
     */
    public String getFamilyName();

    /**
     * Returns the full name of the user, or null if the full name is not available.
     * The full name contains given names, family names and possibly a title or suffix.
     * Therefore, the full name may be different from the concatenation of given and family name.
     *
     * @return the full name
     */
    public String getFullName();

    /**
     * Returns the given (aka first) name of the user, or null if the given name is not available.
     *
     * @return the given name
     */
    public String getGivenName();

    /**
     * Returns the organization the user belongs to
     *
     * @return the organization name
     *
     */
    public String getOrganization();

    /**
     * Returns the given e-mail of the user or null if none is available.
     *
     * @return the email address
     */
    public String getEmailAddress();

    /**
     * Returns the internal unique user id.
     *
     * @return the internal unique user id
     */
    public String getID();

    /**
     * Returns the user id of the user, or null if the user id is not available.
     * The userid is the user's login name
     *
     * @return the user id
     */
    public String getUserID();

    /**
     * This is an alias for the getUserID method, which for all intensive
     * purposes represents the name required for this user to login.
     *
     * @return String the user id
     */
    public String getLoginName();

    /**
     * Returns the point of time that this user was last logged in, or null if this information is not available.
     * The time is returned in number of milliseconds since January 1, 1970 GMT.
     *
     * @return the last login time
     */
    public long getLastLoginTime();

    /**
     * Returns a string representaation of the User
     *
     * @return User information represented as a String
     */
    public String toString();
}
