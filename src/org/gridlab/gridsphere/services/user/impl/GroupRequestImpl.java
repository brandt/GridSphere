/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 31, 2003
 * Time: 3:27:11 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.user.impl;

import org.gridlab.gridsphere.services.security.acl.GroupRequest;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.core.persistence.BaseObject;

/**
 * @table grouprequest
 */
public class GroupRequestImpl
        extends BaseObject
        implements GroupRequest {

    /**
     * @sql-name user
     * @get-method getSportletUser
     * @set-method setSportletUser
     * @required
     */
    private SportletUserImpl user = null;
    /**
     * @sql-name group
     * @get-method getSportletGroup
     * @set-method setSportletGroup
     * @required
     */
    private SportletGroup group = null;
    /**
     * @sql-name role
     * @sql-size 256
     * @required
     * @get-method getRoleName
     * @set-method setRoleName
     */
    private String role = null;
    /**
     * @sql-name action
     * @sql-size 6
     * @required
     */
    private String action = GroupRequest.ACTION_ADD;

    public String getID() {
        return getOid();
    }

    public PortletGroup getGroup() {
        return this.group;
    }

    public void setGroup(PortletGroup group) {
        this.group = (SportletGroup)group;
    }

    public PortletRole getRole() {
        try {
            return PortletRole.toPortletRole(this.role);
        } catch (Exception e) {
            log.error("Error converting role to string", e);
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = (SportletUserImpl)user;
    }

    /**
     * Castor method for getting user object.
     */
    public SportletUserImpl getSportletUser() {
        return this.user;
    }

    /**
     * Castor method for setting user object.
     */
    public void setSportletUser(SportletUserImpl user) {
        this.user = user;
    }

    /**
     * Castor method for getting group object.
     */
    public SportletGroup getSportletGroup() {
        return this.group;
    }

    /**
     * Castor method for setting group object.
     */
    public void setSportletGroup(SportletGroup group) {
        this.group = group;
    }
}
