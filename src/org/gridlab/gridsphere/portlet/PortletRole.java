/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.ArrayList;
import java.util.List;

public class PortletRole {

    private int role;

    public static final int SUPER_ROLE = 0;
    public static final int GUEST_ROLE = 1;
    public static final int USER_ROLE  = 2;
    public static final int ADMIN_ROLE = 3;

    public static final PortletRole GUEST = new PortletRole(GUEST_ROLE);
    public static final PortletRole USER = new PortletRole(USER_ROLE);
    public static final PortletRole ADMIN = new PortletRole(ADMIN_ROLE);
    public static final PortletRole SUPER = new PortletRole(SUPER_ROLE);

    private PortletRole(int role) {
        this.role = role;
    }

    public static PortletRole toPortletRole(String portletRole) throws Exception {
        if (portletRole.equalsIgnoreCase("GUEST")) {
            return GUEST;
        } else if (portletRole.equalsIgnoreCase("USER")) {
            return USER;
        } else if (portletRole.equalsIgnoreCase("ADMIN")) {
            return ADMIN;
        } else if (portletRole.equalsIgnoreCase("SUPER")) {
            return SUPER;
        } else {
            throw new Exception("Unable to create PortletRole corresponding to: " + portletRole);
        }
    }

    public int getRole() {
        return role;
    }

    public int getID() {
        return role;
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

    public boolean equals(Object object) {
        if (object != null && (object.getClass().equals(this.getClass()))) {
            PortletRole portletRole = (PortletRole)object;
            return (role == portletRole.getRole());
        }
        return false;
    }

    public int hashCode() {
        return role;
    }
}
