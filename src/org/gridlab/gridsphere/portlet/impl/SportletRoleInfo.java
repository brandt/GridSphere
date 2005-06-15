package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletRole;

import java.io.Serializable;

/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 * The <code>PortletRoleInfo</code> saves the role associated to a concrete portlet.
 */

public class SportletRoleInfo implements Serializable {

    private static final long serialVersionUID = 2887296080186064446L;
    
    private String portletRole = "";
    private String portletClass = "";
    private String oid = null;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public PortletRole getPortletRole() {
        return PortletRole.toPortletRole(portletRole);
    }

    public void setPortletRole(PortletRole portletRole) {
        this.portletRole = portletRole.toString();
    }

    public String getRole() {
        return portletRole;
    }

    public void setRole(String role) {
        portletRole = role;
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
