/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

public class Owner {

    private String GroupName = "";
    private String roleName = "";

    public Owner() {}

    /**
     * gets the group name
     *
     * @returns GroupName
     */
    public String getGroupName() {
        return GroupName;
    }

    /**
     * sets the group name
     *
     * @param GroupName the group name
     */
    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
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
     * Sets the role name
     *
     * @param roleName the role name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}

