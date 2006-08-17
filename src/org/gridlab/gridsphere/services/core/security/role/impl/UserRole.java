/*
 * @version: $Id$
 */
package org.gridlab.gridsphere.services.core.security.role.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.services.core.security.role.PortletRole;

public class UserRole {

    protected transient static PortletLog log = SportletLog.getInstance(UserRole.class);

    private String oid = null;
    private SportletUserImpl user = null;
    private PortletRole portletRole;

    public UserRole() {
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

    public PortletRole getRole() {
        return portletRole;
    }

    public void setRole(PortletRole role) {
        this.portletRole = role;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = (SportletUserImpl) user;
    }

}
