/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.security.acl.impl;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.services.core.security.acl.GroupEntry;
import org.gridlab.gridsphere.services.core.security.acl.GroupRequest;

public class GroupRequestImpl implements GroupRequest {

    protected transient static PortletLog log = SportletLog.getInstance(GroupRequestImpl.class);

    private String oid = null;
    private SportletUserImpl user = null;
    private SportletGroup sgroup = null;
    private String role = "";

    public GroupRequestImpl() {
    }

    public GroupRequestImpl(GroupEntry groupEntry) {
        this.user = (SportletUserImpl)groupEntry.getUser();
        this.sgroup = (SportletGroup)groupEntry.getGroup();
        this.role = groupEntry.getRole().toString();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getID() {
        return getOid();
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
        return this.sgroup;
    }

    /**
     * Castor method for setting group object.
     */
    public void setSportletGroup(SportletGroup group) {
        this.sgroup = group;
    }

}
