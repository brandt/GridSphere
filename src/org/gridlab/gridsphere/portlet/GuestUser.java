/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The Role interface is an abstract view on the user-specific data.
 * Apart from a set of pre-defined, fixed set of attributes,
 * the interface gives access to user data as well.
 */
public class GuestUser implements User {

    // store used to maintain user attributes
    private Hashtable store = new Hashtable();

    private static GuestUser instance = new GuestUser();
    // Data fields that make up the Role object
    private String FamilyName = null;
    private String FullName = null;
    private String GivenName = null;
    private String EmailAddress = null;
    private String Id = null;
    private String UserID = null;
    private String Organization = null;
    private long LastLoginTime;

    private GuestUser() {
        FamilyName = "Role";
        FullName = "Guest Role";
        GivenName = "Guest";
        EmailAddress = "no email";
        Id = "500";
        UserID = "guest";
        Organization = "guest";
        LastLoginTime = -1;
    }

    public static GuestUser getInstance() {
        return instance;
    }

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
        return FamilyName;
    }

    /**
     * Returns the full name of the user, or null if the full name is not available.
     * The full name contains given names, family names and possibly a title or suffix.
     * Therefore, the full name may be different from the concatenation of given and family name.
     *
     * @return the full name
     */
    public String getFullName() {
        return FullName;
    }

    /**
     * Returns the given (aka first) name of the user, or  if the given name is not available.
     *
     * @return the given name
     */
    public String getGivenName() {
        return GivenName;
    }

    /**
     * Returns the given e-mail of the user or null if none is available.
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return EmailAddress;
    }

    /**
     * Returns the internal unique user id.
     *
     * @return the internal unique id
     */
    public String getID() {
        return Id;
    }

    /**
     * Returns the user id of the user, or null if the user id is not available.
     *
     * @return the user id
     */
    public String getUserID() {
        return UserID;
    }

    /**
     * This is an alias for the getUserID method, which for all intensive
     * purposes represents the name required for this user to login.
     *
     * @return String the user id
     */
    public String getLoginName() {
        return UserID;
    }

    public String getOrganization() {
        return Organization;
    }

    /**
     * Returns the point of time that this user was last logged in, or null if this information is not available.
     * The time is returned in number of milliseconds since January 1, 1970 GMT.
     *
     * @return the last login time
     */
    public long getLastLoginTime() {
        return LastLoginTime;
    }

    /**
     * Returns a string representaation of the User
     *
     * @return User information represented as a String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("FamilyName: " + FamilyName + "\n");
        sb.append("FullName: " + FullName + "\n");
        sb.append("GivenName: " + GivenName + "\n");
        sb.append("EmailAddress: " + EmailAddress + "\n");
        sb.append("Id: " + Id + "\n");
        sb.append("UserID: " + UserID + "\n");
        sb.append("LastLoginTime: " + LastLoginTime + "\n");
        return sb.toString();
    }
}
