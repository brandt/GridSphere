/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

public class Role {

    private String roleName = "";

    public Role() {}

    /**
     * Returns the group name
     *
     * @returns GroupName
     */
    public String getUserRole() {
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

