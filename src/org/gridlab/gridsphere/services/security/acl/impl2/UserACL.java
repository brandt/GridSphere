/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl2;

import org.gridlab.gridsphere.core.persistence.BaseObject;

public class UserACL extends BaseObject {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(UserACL.class.getName());

    private String User;
    private GroupACL groupacl;

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public GroupACL getGroupACL() {
        return groupacl;
    }

    public void setGroupACL(GroupACL group) {
        this.groupacl = group;
    }
}

