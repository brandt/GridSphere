/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl2;

import org.gridlab.gridsphere.core.persistence.BaseObject;

public class RoleACL extends BaseObject {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(RoleACL.class.getName());

    private Role role;
    private UserACL user;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserACL getUser() {
        return user;
    }

    public void setUser(UserACL user) {
        this.user = user;
    }
}

