/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 31, 2003
 * Time: 3:27:11 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.acl;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;

public class GroupRequestBean
        implements GroupRequest {

    private GroupEntry entry = null;
    private String id = new String();
    private User user = null;
    private PortletGroup group = null;
    private String role = null;
    private GroupAction action = GroupAction.ADD;
    private String description = null;

    public GroupRequestBean() {
    }

    public GroupRequestBean(GroupEntry entry) {
        this.entry = entry;
        this.id = entry.getID();
        this.user = entry.getUser();
        this.group = entry.getGroup();
        this.role = entry.getRole().toString();
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

    public String getGroupDescription() {
        return this.description;
    }

    public void setGroupDescription(String description) {
        this.description = description;
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

    public GroupAction getGroupAction() {
        return this.action;
    }

    public void setGroupAction(GroupAction action) {
        this.action = action;
    }

    public void setGroupAction(String action) {
        this.action = GroupAction.toGroupAction(action);
    }
}
