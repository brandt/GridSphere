/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.gridlab.gridsphere.portlet.PortletRole;

public class AllowedAccess {

    private String role = PortletRole.USER.toString();
    private String visibility = null;
    private int scope = PUBLIC;
    public static final int PUBLIC = 3;
    public static final int PRIVATE = 3;

    public AllowedAccess() {}


    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getVisibility() {
        return visibility;
    }

    public int getScope() {
        if (visibility != null) {
            if (visibility.equalsIgnoreCase("private")) {
                scope = PRIVATE;
            }
        }
        return scope;
    }

    public void setScope(int scope) {
        if (scope == PUBLIC) {
            this.scope = scope;
            this.visibility = "PUBLIC";
        }
        if (scope == PRIVATE) {
            this.scope = scope;
            this.visibility = "PRIVATE";
        }
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
