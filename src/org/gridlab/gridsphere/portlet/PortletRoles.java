/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.ArrayList;
import java.util.List;

public class PortletRoles {

    public static final int USER = 0;
    public static final int ADMIN = 1;
    public static final int SUPER = 2;

    private static ArrayList roleNames = new ArrayList();

    static {
        roleNames.add("USER");
        roleNames.add("ADMIN");
        roleNames.add("SUPER");
    }

    public static String getRole(int role) {
        return toString(role);
    }

    public static String toString(int role) {
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

    public static List getRoleNames() {
        return roleNames;
    }
}
