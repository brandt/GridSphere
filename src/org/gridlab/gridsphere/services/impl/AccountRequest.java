/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl;

import org.gridlab.gridsphere.portlet.User;

public class AccountRequest {

    private String firstName = "";
    private String givenName = "";
    private String familyName = "";
    private String fullName = "";
    private String emailAddress = "";
    private String organization = "";
    private String[] groups = null;
    private String[] userdns = null;
    private String[] myproxyUserNames = null;

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
    public void setMyproxyUserNames(String[] myproxyUserNames) {
        myproxyUserNames = myproxyUserNames;
    }

    /**
     * Sets the list of myproxy user names that can be used for this user
     *
     * @param userdns the array of strings containing user DN information
     */
    public void getMyproxyUserNames() {

    }

    /**
     * Sets the list of myproxy user names that can be used for this user
     *
     * @param userdns the array of strings containing user DN information
     */
    public void setMyproxyUserDN(String[] userdns) {
        this.userdns = userdns;
    }

    /**
     * Returns the list of myproxy user names that can be used for this user
     *
     * @param userdns the array of strings containing user DN information
     */
    public String[] getMyProxyUserDN() {
        return userdns;
    }

    /**
     * Sets the list of groups the user wishes to join
     *
     * @param groups the array of strings containg the groups the user wishes to join
     */
    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    /**
     * Returns the list of groups the user wishes to join
     *
     * @param groups the array of strings containg the groups the user wishes to join
     */
    public String[] getGroups() {
        return groups;
    }

}
