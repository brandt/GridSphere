
package org.gridlab.gridsphere.services.core.security.acl.impl.descriptor;

import java.util.Set;
import java.util.HashSet;

public class PortletGroupDescription {

    private String groupName = "";
    private String groupDescription = "";
    private String groupVisibility = "PUBLIC";

    private Set portletRoleList = new HashSet();

    public PortletGroupDescription() {}

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupVisibility(String groupVisibility) {
        this.groupVisibility = groupVisibility;
    }

    public String getGroupVisibility() {
        return groupVisibility;
    }

    public Set getPortletRoleInfo() {
        return portletRoleList;
    }

    public void setPortletRoleInfo(HashSet portletRoleList) {
        this.portletRoleList = portletRoleList;
    }

}