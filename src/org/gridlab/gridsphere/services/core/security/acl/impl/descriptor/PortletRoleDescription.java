package org.gridlab.gridsphere.services.core.security.acl.impl.descriptor;

import org.gridlab.gridsphere.portlet.PortletRole;

import java.util.HashSet;
import java.util.Set;

public class PortletRoleDescription {

    private String roleName = "";
    private String rolePriority = PortletRole.GUEST.toString();

    public PortletRoleDescription() {
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRolePriority(String rolePriority) {
        this.rolePriority = rolePriority;
    }

    public String getRolePriority() {
        return rolePriority;
    }

}