/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbmsImpl;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.security.acl.impl.UserACL;
import org.gridlab.gridsphere.services.core.security.password.PasswordEditor;
import org.gridlab.gridsphere.services.core.user.AccountRequest;

import java.util.List;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Date;

/**
 * @table accountrequest
 *
 */
public class AccountRequestImpl extends BaseObject implements AccountRequest {

    protected transient static PortletLog log = SportletLog.getInstance(AccountRequestImpl.class);

    /**
     * @sql-size 32
     * @sql-name userid
     */
    private String UserID = "";
    /**
     * @sql-size 30
     * @sql-name givenname
     */
    private String GivenName = "";
    /**
     * @sql-size 50
     * @sql-name familyname
     */
    private String FamilyName = "";
    /**
     * @sql-size 256
     * @sql-name fullname
     */
    private String FullName = "";
    /**
     * @sql-size 128
     * @sql-name emailaddress
     */
    private String EmailAddress = "";
    /**
     * @sql-size 256
     * @sql-name organization
     */
    private String Organization = "";

    private transient List Userdns = new Vector();
    private transient List MyproxyUserNames = new Vector();

    /**
     * @field-type AccountRequestImplUserdns
     * @many-key reference
     */
    private Vector UserdnsSV = new Vector();

    /**
     * @field-type AccountRequestImplMyproxyUserNames
     * @many-key reference
     */
    private  Vector MyproxyUserNamesSV = new Vector();

    // Password bean
    private transient PasswordEditor passwordBean = null;
    // New user flag
    private transient boolean isNewUser = false;

    public AccountRequestImpl() {
        super();
        this.isNewUser = true;
        this.passwordBean = new PasswordEditor(this);
    }

    public AccountRequestImpl(User user) {
        setOid(user.getID());
        setEmailAddress(user.getEmailAddress());
        setFamilyName(user.getFamilyName());
        setFullName(user.getFullName());
        setGivenName(user.getGivenName());
        setOrganization(user.getOrganization());
        setUserID(user.getUserID());
        this.passwordBean = new PasswordEditor(this);
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
     * This is alias for the getUserID method, which for all intensive
     * purposes represents the name required for this user to login.
     *
     * @return String the user id
     */
    public String getUserName() {
        return this.UserID;
    }

    /**
     * This is an alias for the setUserID method, which for all intensive
     * purposes represents the name required for this user to login.
     *
     * @param name the user id
     */
    public void setUserName(String name) {
        this.UserID = name;
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
     * Sets the family (aka last) name of the user.
     *
     * @param familyName the family name
     */
    public void setFamilyName(String familyName) {
        this.FamilyName = familyName;
    }

    /**
     * Returns the family (aka first) name of the user, or null if the family name is not available.
     *
     * @return the family name
     */
    public String getFamilyName() {
        return FamilyName;
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
     * Sets the users organizational affiliation
     *
     * @param organization the users organizational affiliation
     */
    public void setOrganization(String organization) {
        this.Organization = organization;
    }

    /**
     * Returns the users organizational affiliation
     *
     * @return the users organizational affiliation
     */
    public String getOrganization() {
        return Organization;
    }

    /**
     * Sets the value of the attribute with the given name,
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value) {

    }

    /**
     * Returns the value of the attribute with the given name,
     * or null if no attribute with the given name exists.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    public Object getAttribute(String name) {
        return null;
    }

    /**
     * Returns an enumeration of names of all attributes available to this request.
     * This method returns an empty enumeration if the request has no attributes available to it.
     *
     * @return an enumeration of attribute names
     */
    public Enumeration getAttributeNames() {
        return null;
    }

    /**
     * Returns the point of time that this user was last logged in, or null if this information is not available.
     * The time is returned in number of milliseconds since January 1, 1970 GMT.
     *
     * @return the last login time
     */
    public long getLastLoginTime() {
        return -1;
    }

    /**
     * Sets the point of time that this user was last logged in, or null if this information is not available.
     * The time is returned in number of milliseconds since January 1, 1970 GMT.
     *
     * @param lastLoginTime the last login time
     */
    public void setLastLoginTime(long lastLoginTime) {

    }

    // -----

    /**
     * Sets the list of myproxy user names that can be used for this user
     *
     * @param myproxyUserNames userdns the array of strings containing user DN information
     */
    public void setMyproxyUserNames(List myproxyUserNames) {
        MyproxyUserNames = myproxyUserNames;
        MyproxyUserNamesSV = this.convertToStringVector(this, MyproxyUserNames, AccountRequestImplMyproxyUserNames.class);

    }

    /**
     * Returns the list of myproxy user names that can be used for this user
     *
     * @return userdns the array of strings containing user DN information
     */
    public List getMyproxyUserNames() {
        return MyproxyUserNames;
    }

    public List getMyproxyUserNamesSV() {
        return MyproxyUserNamesSV;
    }

    public void setMyproxyUserNamesSV(Vector myproxyUserNamesSV) {
        MyproxyUserNamesSV = myproxyUserNamesSV;
        MyproxyUserNames = this.convertToVector(MyproxyUserNamesSV);
    }


    /**
     * Sets the list of myproxy user names that can be used for this user
     *
     * @param userdns the array of strings containing user DN information
     */
    public void setMyproxyUserDN(List userdns) {
        Userdns = userdns;
        UserdnsSV = this.convertToStringVector(this, Userdns, AccountRequestImplUserdns.class);
    }

    /**
     * Returns the list of myproxy user names that can be used for this user
     */
    public List getMyProxyUserDN() {
        return Userdns;
    }

    public List getUserdnsSV() {
        return UserdnsSV;
    }

    public void setUserdnsSV(Vector userdnsSV) {
        UserdnsSV = userdnsSV;
        Userdns = this.convertToVector(UserdnsSV);
    }

    //@todo should be done using the aclmanager service!
    /**
     * Adds a user with status 'candidate' to a group
     *
     * @param group
     */
    public void addToGroup(PortletGroup group, PortletRole role) {
        UserACL acl;
        acl = new UserACL(this.getID(), role.getRole(), group.getID());
        PersistenceManagerRdbms pm = PersistenceManagerFactory.createGridSphereRdbms();
        try {
            pm.create(acl);
        } catch (PersistenceManagerException e) {
            e.printStackTrace();
        }
    }

    public PasswordEditor getPassword() {
        return this.passwordBean;
    }

    public void setPassword(PasswordEditor passwordBean) {
        this.passwordBean = passwordBean;
    }

    public String getPasswordValue() {
        return this.passwordBean.getValue();
    }

    public void setPasswordValue(String value) {
        this.passwordBean.setValue(value);
    }

    public String getPasswordHint() {
        return this.passwordBean.getHint();
    }

    public void setPasswordHint(String hint) {
        this.passwordBean.setHint(hint);
    }

    public Date getPasswordDateExpires() {
        return this.passwordBean.getDateExpires();
    }

    public void setPasswordDateExpires(Date dateExpires) {
        this.passwordBean.setDateExpires(dateExpires);
    }

    public long getPasswordLifetime() {
        return this.passwordBean.getLifetime();
    }

    public void setPasswordLifetime(long lifetime) {
        this.passwordBean.setLifetime(lifetime);
    }

    public boolean getPasswordValidation() {
        return this.passwordBean.getValidation();
    }

    public void setPasswordValidation(boolean flag) {
        this.passwordBean.setValidation(flag);
    }

    public boolean getPasswordHasChanged() {
        return this.passwordBean.isDirty();
    }

    public boolean isNewUser() {
        return this.isNewUser;
    }

    /**
     * Prints the constents of the account request to a String
     *
     * @return the account request information
     */
    public String toString() {
        int i;
        StringBuffer sb = new StringBuffer();
        sb.append("Given Name: " + GivenName);
        sb.append("Family Name: " + FamilyName);
        sb.append("Full Name: " + FullName);
        sb.append("Email Address: " + EmailAddress);
        sb.append("Organization: " + Organization);
        sb.append("Requested Groups: ");
        sb.append("Role DNs: ");
        for (i = 0; i < Userdns.size(); i++) {
            sb.append(Userdns.get(i));
        }
        sb.append("Myproxy Role Names: ");
        for (i = 0; i < MyproxyUserNames.size(); i++) {
            sb.append(MyproxyUserNames.get(i));
        }
        return sb.toString();
    }
}
