/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.exolab.castor.jdo.Database;
import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.services.user.UserManagerService;

import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionBindingEvent;
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
public class SportletUserImpl extends BaseObject implements SportletUser, HttpSessionBindingListener {

    private transient PersistenceManagerRdbms pm = PersistenceManagerRdbms.getInstance();

    // store used to maintain user attributes
    private transient Hashtable store = new Hashtable();

    /**
     * @sql-size 32
     * @sql-name userid
     */
    private String UserID = new String();

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
     * This is alias for the getUserID method, which for all intensive
     * purposes represents the name required for this user to login.
     *
     * @return String the user id
     */
    public String getLoginName() {
        return this.UserID;
    }

    /**
     * This is an alias for the setUserID method, which for all intensive
     * purposes represents the name required for this user to login.
     *
     * @param String the user id
     */
    public void setLoginName(String name) {
        this.UserID = name;
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

    /**
     * Returns the value of the attribute with the given name,s
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name) {
        return store.get(name);
    }

    /**
     * Sets the value of the attribute with the given name,
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value) {
        store.put(name, value);
    }

    /**
     * Returns an enumeration of names of all attributes available to this request.
     * This method returns an empty enumeration if the request has no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames() {
        return store.elements();
    }

    /**
     * Returns an enumeration of names of all attributes available to this request.
     * This method returns an empty enumeration if the request has no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeValues() {
        return store.elements();
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
        Enumeration allkeys = store.keys();
        SportletUserImplAttribute ha = null;
        while (allkeys.hasMoreElements()) {
            String key = (String) allkeys.nextElement();
            ha = new SportletUserImplAttribute(key, (String) store.get(key));
            ha.setUser(this);
            Attributes.add(ha);
        }
    }

    private void convert2hash() {
        for (int i = 0; i < Attributes.size(); i++) {
            SportletUserImplAttribute ha = (SportletUserImplAttribute) Attributes.get(i);
            store.put((String) ha.getKey(), (String) ha.getValue());
        }
    }

    public List getAttributes() {
        return Attributes;
    }

    public void setAttributes(Vector attributes) {
        attributes = attributes;
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

    public void valueBound(HttpSessionBindingEvent event) {
        System.err.println("valueBound of SportletUserImpl invoked");
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        System.err.println("valueUnbound of SportletUserImpl invoked");
        try {
            pm.update(this);
        } catch(PersistenceManagerException e) {
            // what can we do??
        }
        pm = null;
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
        sb.append("Id: " + getOid() + "\n");
        sb.append("UserID: " + UserID + "\n");
        sb.append("LastLoginTime: " + LastLoginTime + "\n");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean b = true;
        if ((obj != null) && (obj.getClass().equals(this.getClass()))) {
            b = (((SportletUserImpl)obj).EmailAddress == this.EmailAddress);
            b &= (((SportletUserImpl)obj).FamilyName == this.FamilyName);
            b &= (((SportletUserImpl)obj).GivenName == this.GivenName);
            b &= (((SportletUserImpl)obj).FullName == this.FullName);
            b &= (((SportletUserImpl)obj).FullName == this.FullName);
            b &= (((SportletUserImpl)obj).UserID == this.UserID);
            b &= (((SportletUserImpl)obj).LastLoginTime == this.LastLoginTime);
            b &= (((SportletUserImpl)obj).Organization == this.Organization);
        }
        return b;
    }

}
