/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl.acl;

import java.util.Hashtable;
import java.util.Vector;

public class GroupACL {

    private String groupName;
    private Vector userACLs;

    public GroupACL() {
        userACLs = new Vector();
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public UserACL createUserACL() {
        return new UserACL();
    }

    public void addUserACL(UserACL userACL) {
        userACLs.add(userACL);
    }

    public void removeUserACL(UserACL userACL) {
        userACLs.remove(userACL);
    }

    public Vector getUserACLs() {
        return userACLs;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userACLs.size(); i++) {
            sb.append("Group : " + i + " group name: " + groupName);
            sb.append(userACLs.get(i).toString());
        }
        return sb.toString();
    }

}
