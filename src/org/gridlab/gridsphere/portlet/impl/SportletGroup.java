/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.impl.AccountRequestImpl;

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

    public SportletGroup(String ID, String groupName) {
        super();
        this.Name = groupName;
        setOid(ID);
    }

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

    public boolean equals(String object) {
        if (object == null) {
            return false;
        }
        String thisGroup = getName();
        String thatGroup = (String)object;
        return thisGroup.equals(thatGroup);
    }

    public boolean equals(PortletGroup object) {
        if (object == null) {
            return false;
        }
        String thisGroup = toString();
        String thatGroup = ((PortletGroup)object).toString();
        return thisGroup.equals(thatGroup);
    }


    public String toString() {
        return Name;
    }
}
