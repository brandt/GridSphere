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

    public static final String SUPER_GROUP = "0";
    public static final String BASE_GROUP = "1";

    public static final PortletGroup BASE = new SportletGroup(BASE_GROUP);
    public static final PortletGroup SUPER = new SportletGroup(SUPER_GROUP);

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

}
