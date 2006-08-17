/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.security.group.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.services.core.security.group.PortletGroup;
import org.gridlab.gridsphere.services.core.security.role.PortletRole;

public class UserGroup {

    protected transient static PortletLog log = SportletLog.getInstance(UserGroup.class);

    private String oid = null;
    private SportletUserImpl user = null;
    private PortletGroup sgroup = null;
    // deprecated
    private String role = "";
    private PortletRole portletRole;

    public UserGroup() {
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
        this.sgroup = (PortletGroup) group;
    }

    /**
     * @deprecated
     * @return the portlet role
     */
    public PortletRole getRole() {
        return portletRole;
    }

    /**
     * @deprecated
     * @param role the portlet role
     */
    public void setRole(PortletRole role) {
        this.portletRole = role;
    }

    /**
     * @deprecated
     * @return the role name
     */
    public String getRoleName() {
        return this.role;
    }

    /**
     * @deprecated
     * @param roleName the role name
     */
    public void setRoleName(String roleName) {
        this.role = roleName;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = (SportletUserImpl) user;
    }

}
