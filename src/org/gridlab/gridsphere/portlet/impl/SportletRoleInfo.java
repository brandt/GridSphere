package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletRole;
/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 * The <code>PortletRoleInfo</code> saves the role associated to a concrete portlet.
 */
public class SportletRoleInfo {

    private String portletRole = new String();
    private String portletClass = new String();
    private String oid = new String();

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
