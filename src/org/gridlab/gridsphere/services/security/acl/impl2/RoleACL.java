/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl2;

import org.gridlab.gridsphere.core.persistence.BaseObject;

public class RoleACL extends BaseObject {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(RoleACL.class.getName());

    private Role Role;
    private UserACL UserACL;

    public Role getRole() {
        return Role;
    }

    public void setRole(Role role) {
        this.Role = role;
    }

    public UserACL getUserACL() {
        return UserACL;
    }

    public void setUserACL(UserACL user) {
        this.UserACL = user;
    }
}

