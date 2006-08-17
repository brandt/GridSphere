package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.services.core.security.role.PortletRole;

import java.io.Serializable;

/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 * The <code>PortletRoleInfo</code> saves the role associated to a concrete portlet.
 */

public class SportletRoleInfo implements Serializable {

    private static final long serialVersionUID = 2887296080186064446L;

    // deprecated
    private String role = "";

    private PortletRole portletRole = null;
    private String portletClass = "";
    private String oid = null;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * @deprecated
     * @return role represented as a <code>String</code>
     */
    public String getRole() {
        return role;
    }

    /**
     * @deprecated
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    public PortletRole getSportletRole() {
        return portletRole;
    }

    public void setSportletRole(PortletRole portletRole) {
        this.portletRole = portletRole;
    }

    /**
     * Returns the concrete PortletId.
     *
     * @return concrete PortletId
     */
    public String getPortletClass() {
        return portletClass;
    }

    /**
     * Sets the concrete PortletClass.
     *
     * @param portletClass id od the portletclass
     */
    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

}
