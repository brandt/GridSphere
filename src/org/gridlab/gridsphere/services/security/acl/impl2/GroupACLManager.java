/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl2;


public class GroupACLManager {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(GroupACLManager.class.getName());

    /**
     * creats a new Group
     */
    public void createGroup(String Name) {} ;

    /**
     * deletes a groups (crosschecks!!)
     */
    public void deleteGroup(String Name) {} ;

    /**
     * returns a groupacl object
     */
    public GroupACL getGroupByName(String Name) { return new GroupACL();} ;


}

