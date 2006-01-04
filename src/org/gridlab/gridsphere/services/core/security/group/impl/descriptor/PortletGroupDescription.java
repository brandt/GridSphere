package org.gridlab.gridsphere.services.core.security.group.impl.descriptor;

import java.util.HashSet;
import java.util.Set;

public class PortletGroupDescription {

    private String groupName = "";
    private String groupDescription = "";
    private String groupVisibility = "PUBLIC";
    private String isCore = "false";

    private Set portletRoleList = new HashSet();

    public PortletGroupDescription() {
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCore() {
        return isCore;
    }

    public void setCore(String core) {
        isCore = core;
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