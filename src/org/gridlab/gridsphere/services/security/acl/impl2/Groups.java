/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl2;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.portlet.PortletGroup;

public class Groups extends BaseObject implements PortletGroup {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(Groups.class.getName());

    private String Name = "";
    private int ID = 0;

    public Groups() {
        super();
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isBaseGroup() {
        return false;
    }

}


