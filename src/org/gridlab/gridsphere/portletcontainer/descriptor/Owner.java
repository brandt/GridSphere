/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;

public class Owner {

    private String groupName = "";
    private String roleName = "";
    private PortletGroup group = null;
    private PortletRole role = null;

    public Owner() {}

    /**
     * gets the group name
     *
     * @returns GroupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * gets the portlet group
     *
     * @returns Group
     */
    public PortletGroup getGroup() {
        return group;
    }

    /**
     * Sets the portlet group
     *
     * @param group the PortletGroup Group
     */
    public void setGroup(PortletGroup group) {
        this.group = group;
    }

    /**
     * sets the group name
     *
     * @param GroupName the group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Returns the role name
     *
     * @returns the role name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * gets the portlet group
     *
     * @returns Group
     */
    public PortletRole getRole() {
        return role;
    }

    /**
     * gets the portlet group
     *
     * @returns Group
     */
    public void setRole(PortletRole role) {
        this.role = role;
    }

    /**
     * Sets the role name
     *
     * @param roleName the role name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}

