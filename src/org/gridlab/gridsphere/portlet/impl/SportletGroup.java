/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.impl.AccountRequestImpl;

import java.util.Vector;
import java.util.Random;

/**
 * @table sportletgroup
 */
public class SportletGroup extends BaseObject implements PortletGroup {

    public static final String SUPER_GROUP = "super";
    public static final String BASE_GROUP = "base";

    /**
     * @sql-size 128
     * @sql-name base
     */
    private String Name = new String();

    // @todo why did we need the baseGroup thing??
    // @todo elimnate the sportletgroup(id, name) thing

    public SportletGroup(String groupName) {
        super();
        this.Name = groupName;
    }

    public SportletGroup() {
        super();
    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return getOid();
    }

    public void setID(String id) {
        setOid(id);
    }


    public void setName(String name) {
        Name = name;
    }

    public boolean isBaseGroup() {
        if (Name.equals(BASE_GROUP)) return true;
        return false;
    }

    public boolean isSuperGroup() {
        if (Name.equals(SUPER_GROUP)) return true;
        return false;
    }

    public boolean equals(Object object) {
        if (object != null && (object.getClass().equals(this.getClass()))) {
            PortletGroup portletGroup = (PortletGroup)object;
            return (Name == portletGroup.getName());
        }
        return false;
    }


    public int hashCode() {
        return Name.hashCode();
    }

    public String toString() {
        return Name;
    }
}
