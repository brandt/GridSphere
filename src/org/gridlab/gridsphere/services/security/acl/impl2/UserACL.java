/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl2;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRole;

public class UserACL extends BaseObject  {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(UserACL.class.getName());


    private String UserID;
    private int RoleID;
    private int GroupID;

    public UserACL() {
        super();
    };

    public UserACL(String userid, int roleid, int groupid) {
        this.UserID = userid;
        this.RoleID = roleid;
        this.GroupID = groupid;
        cat.info("new useracl "+userid+" role: "+roleid+" group "+groupid);
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int roleID) {
        RoleID = roleID;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }
}

