/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl2;

import java.util.Vector;

public class RoleACLManager {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(RoleACLManager.class.getName());

    /**
     * adds a user
     */
    public void addRole(UserACL useracl, Role role) {}

    /**
     * removes the role of a user
     */
    public void removeRole(UserACL useracl, org.gridlab.gridsphere.services.security.acl.impl2.Role role) {

    }

    public Vector getRolesForUserInGroup(UserACL useracl) {

        Vector v = new Vector();

        // here goes the query;
        // oql = select r from RoleACL where r.user = User

        return v;

    }

    public void deleteAllRolesForUserInGroup(UserACL user) {

       // query
       // delete all from roleacl where useracl = user
    };


}

