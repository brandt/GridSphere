/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.user.impl;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.services.core.security.acl.GroupEntry;

/**
 *  @table groupentry
 */
public class GroupEntryImpl
        extends BaseObject
        implements GroupEntry {

    /**
     * @sql-name user
     * @get-method getSportletUser
     * @set-method setSportletUser
     * @required
     */
    private SportletUserImpl user = null;

    /**
     * @sql-name sgroup
     * @get-method getSportletGroup
     * @set-method setSportletGroup
     * @required
     */
    private SportletGroup sgroup = null;

    /**
     * @sql-name role
     * @sql-size 256
     * @required
     * @get-method getRoleName
     * @set-method setRoleName
     */
    private String role = null;

    public String getID() {
        return getOid();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = (SportletUserImpl)user;
    }

    public PortletGroup getGroup() {
        return this.sgroup;
    }

    public void setGroup(PortletGroup group) {
        this.sgroup = (SportletGroup)group;
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
        return this.sgroup;
    }

    /**
     * Castor method for setting group object.
     */
    public void setSportletGroup(SportletGroup group) {
        this.sgroup = group;
    }
}
