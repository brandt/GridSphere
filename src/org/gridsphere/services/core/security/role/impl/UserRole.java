/*
 * @version: $Id: UserRole.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.security.role.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.User;
import org.gridsphere.portlet.impl.SportletUserImpl;
import org.gridsphere.services.core.security.role.PortletRole;

public class UserRole {

    protected transient static Log log = LogFactory.getLog(UserRole.class);

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
