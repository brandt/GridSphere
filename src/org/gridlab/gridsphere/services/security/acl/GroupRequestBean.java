/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 31, 2003
 * Time: 3:27:11 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.acl;

import org.gridlab.gridsphere.services.security.acl.GroupRequest;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.core.persistence.BaseObject;

public class GroupRequestBean
        implements GroupRequest {

    private String id = new String();
    private User user = null;
    private PortletGroup group = null;
    private String role = null;
    private String action = GroupRequest.ACTION_ADD;

    public GroupRequestBean() {
    }

    public GroupRequestBean(GroupEntry accessRight) {
        this.id = accessRight.getID();
        this.user = accessRight.getUser();
        this.group = accessRight.getGroup();
        this.role = accessRight.getRole().toString();
    }

    public String getID() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PortletGroup getGroup() {
        return this.group;
    }

    public void setGroup(PortletGroup group) {
        this.group = group;
    }

    public PortletRole getRole() {
        try {
            return PortletRole.toPortletRole(this.role);
        } catch (Exception e) {
            return PortletRole.GUEST;
        }
    }

    public void setRole(PortletRole role) {
        this.role = role.toString();
    }

    public String getRoleName() {
        return this.role;
    }

    public void setRoleName(String roleName) {
        this.role = roleName;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        if (action.equals(GroupRequest.ACTION_ADD) ||
            action.equals(GroupRequest.ACTION_REMOVE)) {
            this.action = action;
        }
    }
}
