/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.core.security.acl.impl;

import org.gridlab.gridsphere.portlet.PortletRole;

public class UserACL {


    private String oid = null;
    private int RoleID = PortletRole.GUEST.getID();
    private String UserID = new String();
    private String GroupID = new String();
    private int Status = 0;         // 0 not approved; 1 approved

    public static final int STATUS_NOT_APPROVED = 0;
    public static final int STATUS_APPROVED = 1;

    public UserACL() {
        super();
    };

    public UserACL(String userid, int roleid, String groupid) {
        this.UserID = userid;
        this.RoleID = roleid;
        this.GroupID = groupid;
        this.Status = STATUS_NOT_APPROVED;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}

