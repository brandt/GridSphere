/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id: PortletRole.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.security.role;

import java.io.Serializable;

/**
 * The <code>PortletRole</code> describes the supported portlet roles used
 * by the portal. In general, <code>Group</code>s contain <code>User</codes
 * with a single <code>PortleRole</code>
 * <p/>
 * Over time, we plan on allowing users to have more than one role
 *
 * @see org.gridsphere.portlet.PortletGroup
 */
public class PortletRole implements Serializable, Cloneable {

    private String oid = null;

    private String roleName = null;
    private String description = "";

    private static final String USER_ROLE_STRING = "USER";
    private static final String ADMIN_ROLE_STRING = "ADMIN";

    public static final PortletRole USER = new PortletRole(USER_ROLE_STRING);
    public static final PortletRole ADMIN = new PortletRole(ADMIN_ROLE_STRING);

    public PortletRole() {}

    /**
     * Constructs an instance of PortletRole
     */
    public PortletRole(String roleName) {
        this.roleName = roleName;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * Returns the role name
     *
     * @return the role name
     */
    public String getName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the role name
     *
     * @param roleName
     */
    public void setName(String roleName) {
        this.roleName = roleName;
    }

    public String toString() {
        return roleName;
    }

    public Object clone() throws CloneNotSupportedException {
        PortletRole r = (PortletRole) super.clone();
        r.roleName = this.roleName;
        return r;
    }

    public boolean equals(Object object) {
        if (object != null && (object.getClass().equals(this.getClass()))) {
            PortletRole portletRole = (PortletRole) object;
            return (roleName.equals(portletRole.getName()));
        }
        return false;
    }

    public int hashCode() {
        return roleName.hashCode();
    }

}
