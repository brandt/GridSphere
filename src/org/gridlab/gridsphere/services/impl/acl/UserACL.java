/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl.acl;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Iterator;

/**
 * A UserACL defines a user in the access control list. A user has a userID, which is the ID from the Portlet
 * User object, a user name which is generally the givenName of the Portlet User and a role. The role is defined
 * by the PortletRoles class.
 */
public class UserACL {

    private String userName;
    private String userID;
    private Vector roleACLs;

    public UserACL() {
        roleACLs = new Vector();
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void addRole(int role) {
        roleACLs.add(new Integer(role));
    }

    public void removeRole(int role) {
        Integer irole = new Integer(role);
        if (roleACLs.contains(irole)) {
            roleACLs.remove(irole);
        }
    }

    public Vector getRoles() {
        return roleACLs;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("User : " + userName + " userid: " + userID);
        Iterator it = roleACLs.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
        }
        return sb.toString();
    }

}
