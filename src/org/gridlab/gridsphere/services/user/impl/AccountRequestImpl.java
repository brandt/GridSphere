/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user.impl;

import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.castor.StringVector;

import java.util.List;
import java.util.Vector;
import java.util.Random;

public class AccountRequestImpl extends BaseObject implements AccountRequest {

    protected transient static PortletLog log = SportletLog.getInstance(AccountRequestImpl.class);

    private String UserID = "";
    private String GivenName = "";
    private String FamilyName = "";
    private String FullName = "";
    private String EmailAddress = "";
    private String Organization = "";
    private List DesiredGroups = new Vector();
    private List Userdns = new Vector();
    private List MyproxyUserNames = new Vector();

    private Vector DesiredGroupsSV = new Vector();      // half-ready for castor
    private Vector UserdnsSV = new Vector();            // ready
    private Vector MyproxyUserNamesSV = new Vector();   // ready


    public AccountRequestImpl() {
        super();
        log.info("created accountrequestimpl with OID: "+getOid());
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
     * @param the email address
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

    // -----

    /**
     * Sets the list of myproxy user names that can be used for this user
     *
     * @param userdns the array of strings containing user DN information
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

    // -------

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
     *
     * @param userdns the array of strings containing user DN information
     */
    public List getMyProxyUserDN() {
        return Userdns;
    }

    public List getUserdnsSV() {
        return UserdnsSV;
    }

    public void setUserdnsSV(Vector userdnsSV) {
        UserdnsSV  =  userdnsSV;
        Userdns =  this.convertToVector(UserdnsSV);
    }

    // --------

    /**
     * Adds a group the user wishes to join
     *
     * @param group the group a user wishes to join
     */
    public void setDesiredGroups(List group) {
        //DesiredGroups = group;
        for (int i=0;i<group.size();i++) {
            addDesiredGroups((PortletGroup)group.get(i));
        }

    }

    /**
     * Returns the list of groups the user desires to join, or null if none
     *
     * @return groups the List of group names the user desires to join, or empty if none
     */
    public List getDesiredGroups() {
       // log.info("called getDesiredGroups ");
        return DesiredGroups;
    }

    public void addDesiredGroups(PortletGroup pg) {
//        log.info("called addDesiredGroups "+pg.getName());
        if (!DesiredGroups.contains(pg)) {
            DesiredGroups.add(pg);
            SportletGroup sg = (SportletGroup)pg;
            sg.addAccountRequest(this);
            log.info("called addDesiredGroups for "+this.getFullName()+" and group "+sg.getName());
        }
    }

    // ----

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
        for (i = 0; i < DesiredGroups.size(); i++) {
            PortletGroup group = (PortletGroup)DesiredGroups.get(i);
            sb.append(group.getName());
        }
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
