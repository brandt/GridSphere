package org.gridlab.gridsphere.services.core.security.role.impl.descriptor;

import org.gridlab.gridsphere.portlet.PortletRole;

import java.util.HashSet;
import java.util.Set;

public class PortletRoleDescription {

    private String roleName = "";
    private String roleDescription = "";

    public PortletRoleDescription() {
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}