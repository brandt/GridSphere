/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.User;

/**
 * The <code>SportletUser</code> extends the immutable <code>User</code>
 * and provides all the setter- methods to set user attributes.
 */
public interface SportletUser extends User {

    /**
     * Sets the value of the attribute with the given name,
     *
     * @param name  the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value);

    /**
     * Sets the family (aka last) name of the user.
     *
     * @param familyName the family name
     */
    public void setFamilyName(String familyName);

    /**
     * Sets the full name of the user, or null if the full name is not available.
     * The full name contains given names, family names and possibly a title or suffix.
     * Therefore, the full name may be different from the concatenation of given and family name.
     *
     * @param fullName the full name
     */
    public void setFullName(String fullName);

    /**
     * Sets the given (aka first) name of the user, or  if the given name is not available.
     *
     * @param givenName the given name
     */
    public void setGivenName(String givenName);

    /**
     * Sets the given e-mail of the user.
     *
     * @param emailAddress the email address
     */
    public void setEmailAddress(String emailAddress);

    /**
     * Sets the organization the user belongs to
     *
     * @param organization the organization name
     */
    public void setOrganization(String organization);

    /**
     * Sets the internal unique user id.
     *
     * @param id the internal unique id
     */
    public void setID(String id);

    /**
     * Sets the user id of the user, or null if the user id is not available.
     *
     * @param userID the user id
     */
    public void setUserID(String userID);

    /**
     * This is an alias for the setUserID method, which for all intensive
     * purposes represents the name required for this user to login.
     *
     * @param userName the user name
     */
    public void setUserName(String userName);

    /**
     * Sets the point of time that this user was last logged in, or null if this
     * information is not available. The time is returned in number of milliseconds
     * since January 1, 1970 GMT.
     *
     * @param lastLoginTime the last login time
     */
    public void setLastLoginTime(long lastLoginTime);

}
