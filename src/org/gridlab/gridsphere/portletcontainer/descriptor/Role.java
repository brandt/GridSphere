/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;


public class Role {

    private String roleName = "";

    public Role() {
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

