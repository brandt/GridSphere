/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.exolab.castor.jdo.Database;
import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.List;

/**
 * The User interface is an abstract view on the user-specific data.
 * Apart from a set of pre-defined, fixed set of attributes,
 * the interface gives access to user profile data.
 */

/**
 * @table sportletuserimpl
 */
public class SportletUserImpl extends BaseObject implements SportletUser {

    // store used to maintain user attributes
    private transient Hashtable Store = new Hashtable();

    // Data fields that make up the Role object
    /**
     * @sql-size 50
     * @sql-name familyname
     */
    private String FamilyName = new String();
    /**
     * @sql-size 100
     * @sql-name fullname
     */
    private String FullName = new String();
    /**
     * @sql-size 30
     * @sql-name givenname
     */
    private String GivenName = new String();
    /**
     * @sql-size 128
     * @sql-name emailaddress
     */
    private String EmailAddress = new String();
    /**
     * @sql-size 32
     * @sql-name userid
     */
    private String UserID = new String();
    /**
     * @sql-size 256
     * @sql-name organization
     */
    private String Organization = new String();

    /**
     * @sql-name lastlogintime
     */
    private long LastLoginTime = 0;

    /**
     * @field-type SportletUserImplAttribute
     * @many-key sportletuser
     */
    public Vector Attributes = new Vector();

    /**
     * Sets the value of the attribute with the given name,
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value) {
        Store.put(name, value);
    }

    /**
     * Returns the value of the attribute with the given name,s
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name) {
        return Store.get(name);
    }

    /**
     * Returns an enumeration of names of all attributes available to this request.
     * This method returns an empty enumeration if the request has no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames() {
        return Store.elements();
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
     * Sets the family (aka last) name of the user.
     *
     * @param familyName the family name
     */
    public void setFamilyName(String familyName) {
        this.FamilyName = familyName;
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
     * Sets the full name of the user, or null if the full name is not available.
     * The full name contains given names, family names and possibly a title or suffix.
     * Therefore, the full name may be different from the concatenation of given and family name.
     *
     * @param fullName the full name
     */
    public void setFullName(String fullName) {
        this.FullName = fullName;
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
     * Sets the given (aka first) name of the user, or  if the given name is not available.
     *
     * @param givenName the given name
     */
    public void setGivenName(String givenName) {
        this.GivenName = givenName;
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
     * Sets the given e-mail of the user.
     *
     * @param emailAddress the email address
     */
    public void setEmailAddress(String emailAddress) {
        this.EmailAddress = emailAddress;
    }

    /**
     * Returns the internal unique user id.
     *
     * @return the internal unique id
     */
    public String getID() {
        return getOid();
    }

    /**
     * Sets the internal unique user id.
     *
     * @param id the internal unique id
     */
    public void setID(String id) {
        setOid(id);
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
     * Sets the user id of the user, or null if the user id is not available.
     *
     * @param userID the user id
     */
    public void setUserID(String userID) {
        this.UserID = userID;
    }

    /**
     * Gets the organization the user belongs to
     *
     * @return organization the organization
     */
    public String getOrganization() {
        return Organization;
    }

    /**
     * Sets the organization the user belongs to
     *
     * @param organization
     */
    public void setOrganization(String organization) {
        this.Organization=organization;
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
     * Sets the point of time that this user was last logged in, or null if this information is not available.
     * The time is returned in number of milliseconds since January 1, 1970 GMT.
     *
     * @param lastLoginTime the last login time
     */
    public void setLastLoginTime(long lastLoginTime) {
        this.LastLoginTime = lastLoginTime;
    }

    /*
    public List getACL() {
        return ACL;
    }

    public void setACL(List acl) {
        ACL = acl;
    }
    */
    private void convert2vector() {
        Enumeration allkeys = Store.keys();
        SportletUserImplAttribute ha = null;
        while (allkeys.hasMoreElements()) {
            String key = (String) allkeys.nextElement();
            ha = new SportletUserImplAttribute(key, (String) Store.get(key));
            ha.setUser(this);
            Attributes.add(ha);
        }
    }

    private void convert2hash() {
        for (int i = 0; i < Attributes.size(); i++) {
            SportletUserImplAttribute ha = (SportletUserImplAttribute) Attributes.get(i);
            Store.put((String) ha.getKey(), (String) ha.getValue());
        }
    }

    public Vector getAttributes() {
        return Attributes;
    }

    public void setAttributes(Vector attributes) {
        Attributes = attributes;
    }

    public void jdoBeforeCreate(Database database) throws Exception {

        //super.jdoBeforeCreate(database);
        convert2vector();
    }

    public Class jdoLoad(short i) throws Exception {
        convert2hash();
        return super.jdoLoad(i);
    }

    public void jdoUpdate() {
        convert2vector();
    }



    /**
     * Returns a string representaation of the User
     *
     * @return User information represented as a String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("FamilyName: " + FamilyName);
        sb.append("FullName: " + FullName);
        sb.append("GivenName: " + GivenName);
        sb.append("EmailAddress: " + EmailAddress);
        sb.append("Id: " + getOid());
        sb.append("UserID: " + UserID);
        sb.append("LastLoginTime: " + LastLoginTime);
        return sb.toString();
    }
}
