/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user.impl;

import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.portlet.PortletGroup;

import java.util.List;
import java.util.Vector;

public class AccountRequestImpl implements AccountRequest {

    private int id;
    private String userID = "";
    private String givenName = "";
    private String familyName = "";
    private String fullName = "";
    private String emailAddress = "";
    private String organization = "";
    private List desiredGroups = new Vector();
    private List approvedGroups = new Vector();
    private List userdns = new Vector();
    private List myproxyUserNames = new Vector();

    /**
     * Returns the internal unique user id.
     *
     * @return the internal unique id
     */
    public int getID() {
        return id;
    }

    /**
     * Sets the internal unique user id.
     *
     * @param id the internal unique id
     */
    public void setID(int id) {
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
     * Sets the family (aka last) name of the user.
     *
     * @param familyName the family name
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * Returns the family (aka first) name of the user, or null if the family name is not available.
     *
     * @return the family name
     */
    public String getFamilyName() {
        return familyName;
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
     * Returns the given e-mail of the user or null if none is available.
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the given e-mail of the user.
     *
     * @param the email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Sets the users organizational affiliation
     *
     * @param organization the users organizational affiliation
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * Returns the users organizational affiliation
     *
     * @return the users organizational affiliation
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Sets the list of myproxy user names that can be used for this user
     *
     * @param userdns the array of strings containing user DN information
     */
    public void setMyproxyUserNames(List myproxyUserNames) {
        myproxyUserNames = new Vector(myproxyUserNames);
    }

    /**
     * Returns the list of myproxy user names that can be used for this user
     *
     * @return userdns the array of strings containing user DN information
     */
    public List getMyproxyUserNames() {
        return myproxyUserNames;
    }

    /**
     * Sets the list of myproxy user names that can be used for this user
     *
     * @param userdns the array of strings containing user DN information
     */
    public void setMyproxyUserDN(List userdns) {
        this.userdns = userdns;
    }

    /**
     * Returns the list of myproxy user names that can be used for this user
     *
     * @param userdns the array of strings containing user DN information
     */
    public List getMyProxyUserDN() {
        return userdns;
    }

    /**
     * Adds a group the user wishes to join
     *
     * @param group the group a user wishes to join
     */
    public void setDesiredGroups(List group) {
        desiredGroups = new Vector(group);
    }

    /**
     * Returns the list of groups the user desires to join, or null if none
     *
     * @return groups the List of group names the user desires to join, or empty if none
     */
    public List getDesiredGroups() {
        return desiredGroups;
    }

    /**
     * Returns the list of groups the user is authorized to join, or null if none
     *
     * @return groups the List of group names the user is authorized to join, or empty if none
     */
    public List getApprovedGroups() {
        return approvedGroups;
    }

    /**
     * Not part of the AccountRequest interface. Allows the UserAdminService to approve a desiredGroup entry
     *
     * @param group the group name to approve
     */
    public void addApprovedGroup(PortletGroup group) {
        // make sure it's a desired group as well
        if (desiredGroups.contains(group)) {
            approvedGroups.add(group);
        }
    }

    /**
     * Prints the constents of the account request to a String
     *
     * @return the account request information
     */
    public String toString() {
        int i;
        StringBuffer sb = new StringBuffer();
        sb.append("Given Name: " + givenName);
        sb.append("Family Name: " + familyName);
        sb.append("Full Name: " + fullName);
        sb.append("Email Address: " + emailAddress);
        sb.append("Organization: " + organization);
        sb.append("Requested Groups: ");
        for (i = 0; i < desiredGroups.size(); i++) {
            PortletGroup group = (PortletGroup)desiredGroups.get(i);
            sb.append(group.getName());
        }
        sb.append("Role DNs: ");
        for (i = 0; i < userdns.size(); i++) {
            sb.append(userdns.get(i));
        }
        sb.append("Myproxy Role Names: ");
        for (i = 0; i < myproxyUserNames.size(); i++) {
            sb.append(myproxyUserNames.get(i));
        }
        return sb.toString();
    }
}
