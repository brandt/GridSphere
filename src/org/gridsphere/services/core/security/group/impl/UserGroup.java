/*
 * @version: $Id: UserGroup.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.security.group.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.User;
import org.gridsphere.portlet.jsrimpl.SportletUserImpl;
import org.gridsphere.services.core.security.group.PortletGroup;
import org.gridsphere.services.core.security.role.PortletRole;

public class UserGroup {

    protected transient static Log log = LogFactory.getLog(UserGroup.class);

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
