/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.ArrayList;
import java.util.List;

public class PortletRole {

    public static final int USER = 0;
    public static final int ADMIN = 1;
    public static final int SUPER = 2;

    private int role = USER;

    private static ArrayList roleNames = new ArrayList();

    static {
        roleNames.add("USER");
        roleNames.add("ADMIN");
        roleNames.add("SUPER");
    }

    private PortletRole(int role) {
        this.role = role;
    }

    public static PortletRole getUserRole() {
        return new PortletRole(USER);
    }

    public static PortletRole getAdminRole() {
        return new PortletRole(ADMIN);
    }

    public static PortletRole getSuperRole() {
        return new PortletRole(SUPER);
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
        if (role == USER) {
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
