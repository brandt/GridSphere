/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletRole;

import java.util.ArrayList;
import java.util.List;

public class SportletRole implements PortletRole {

    public static final int GUEST = 0;
    public static final int USER  = 1;
    public static final int ADMIN = 2;
    public static final int SUPER = 3;

    private int role = GUEST;

    private static ArrayList roleNames = new ArrayList();

    static {
        roleNames.add("GUEST");
        roleNames.add("USER");
        roleNames.add("ADMIN");
        roleNames.add("SUPER");
    }

    private SportletRole(int role) {
        this.role = role;
    }

    public static PortletRole getGuestRole() {
        return new SportletRole(GUEST);
    }

    public static PortletRole getUserRole() {
        return new SportletRole(USER);
    }

    public static PortletRole getAdminRole() {
        return new SportletRole(ADMIN);
    }

    public static PortletRole getSuperRole() {
        return new SportletRole(SUPER);
    }

    public boolean isGuestRole() {
        return (role == GUEST);
    }

    public boolean isUserRole() {
        return (role == USER);
    }

    public boolean isAdminRole() {
        return (role == ADMIN);
    }

    public boolean isSuperRole() {
        return (role == SUPER);
    }

    public int getRole() {
        return role;
    }

    public int getID() {
        return role;
    }

    public String getRoleName() {
        String tagstring;
        if (role == GUEST) {
            tagstring = "GUEST";
        } else if (role == USER) {
            tagstring = "USER";
        } else if (role == ADMIN) {
            tagstring = "ADMIN";
        } else {
            tagstring = "SUPER";
        }
        return tagstring;
    }

    public static List getAllRoleNames() {
        return roleNames;
    }
}
