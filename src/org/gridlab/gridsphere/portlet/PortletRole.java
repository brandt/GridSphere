/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;


/**
 * The <code>PortletRole</code> describes the supported portlet roles used
 * by the portal. In general, <code>Group</code>s contain <code>User</codes
 * with a single <code>PortleRole</code>
 * <p>
 * Over time, we plan on allowing users to have more than one role
 *
 * @see org.gridlab.gridsphere.portlet.PortletGroup
 */
public class PortletRole {

    private int role;

    public static final int GUEST_ROLE = 1;
    public static final int USER_ROLE = 2;
    public static final int ADMIN_ROLE = 3;
    public static final int SUPER_ROLE = 4;

    public static final PortletRole GUEST = new PortletRole(GUEST_ROLE);
    public static final PortletRole USER = new PortletRole(USER_ROLE);
    public static final PortletRole ADMIN = new PortletRole(ADMIN_ROLE);
    public static final PortletRole SUPER = new PortletRole(SUPER_ROLE);

    /**
     * Constructs an instance of PortletRole
     */
    private PortletRole(int role) {
        this.role = role;
    }

    /**
     * Return the appropriate <code>PortletRole</code> obtained by parsing
     * the <code>String</code> portlet role name
     *
     * @param portletRole a portlet role name
     * @throws IllegalArgumentException if the <code>String</code> does
     * not match any of the pre-defined roles
     */
    public static PortletRole toPortletRole(String portletRole)
            throws IllegalArgumentException {
        if (portletRole.equalsIgnoreCase("GUEST")) {
            return GUEST;
        } else if (portletRole.equalsIgnoreCase("USER")) {
            return USER;
        } else if (portletRole.equalsIgnoreCase("ADMIN")) {
            return ADMIN;
        } else if (portletRole.equalsIgnoreCase("SUPER")) {
            return SUPER;
        } else {
            throw new IllegalArgumentException("Unable to create PortletRole corresponding to: " + portletRole);
        }
    }

    public int getRole() {
        return role;
    }

    public int getID() {
        return role;
    }

    public boolean isGuest() {
        if (role == GUEST_ROLE) return true;
        return false;
    }

    public boolean isUser() {
        if (role == USER_ROLE) return true;
        return false;
    }

    public boolean isAdmin() {
        if (role == ADMIN_ROLE) return true;
        return false;
    }

    public boolean isSuper() {
        if (role == SUPER_ROLE) return true;
        return false;
    }

    public String toString() {
        String tagstring;
        if (role == GUEST_ROLE) {
            tagstring = "GUEST";
        } else if (role == USER_ROLE) {
            tagstring = "USER";
        } else if (role == ADMIN_ROLE) {
            tagstring = "ADMIN";
        } else {
            tagstring = "SUPER";
        }
        return tagstring;
    }

    public boolean equals(Object object) {
        if (object != null && (object.getClass().equals(this.getClass()))) {
            PortletRole portletRole = (PortletRole) object;
            return (role == portletRole.getID());
        }
        return false;
    }

    public int hashCode() {
        return role;
    }

}
