/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl2;


public class UserACLManager {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(UserACLManager.class.getName());

    /**
     * adds a User to a group
     */
    public void addUser(String User, GroupACL groupacl) {
        UserACL user = new UserACL();
        user.setGroupACL(groupacl);
        user.setUser(User);
        // now save to DB
        // pm.save(user);
    } ;

    /**
     * deletes a user from a group (also delete role!)
     */
    public void deleteUserFromGroup(String User, GroupACL group) {} ;

    /**
     * checks if a user is in a group
     */
    public boolean isUserInGroup(String User, GroupACL group) { return false;} ;

    /**
     * checks if user is in a group
     */
    public boolean isUserInGroup(String User, String GroupName) {
        GroupACLManager gam = new GroupACLManager();
        GroupACL group = gam.getGroupByName(GroupName);
        return isUserInGroup(User, group);
    }

    /**
     * gets the useracl for a username and a groupacl
     */
    public UserACL getUserByName (String Name, GroupACL group) {

        UserACL user = new UserACL();

        return user;
    }

    public UserACL getUserByName (String Name, String group) {
        GroupACLManager gam = new GroupACLManager();
        GroupACL groupacl = gam.getGroupByName(group);

        return getUserByName(Name, groupacl);
    }

}

