/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

public class User {

    private String UserRole = "";

    /**
     * gets the group name
     *
     * @returns GroupName
     */
    public String getUserRole() {
        return UserRole;
    }

    /**
     * sets the user role
     *
     * @param UserRole the user role
     */
    public void setUserRole(String UserRole) {
        this.UserRole = UserRole;
    }

}

