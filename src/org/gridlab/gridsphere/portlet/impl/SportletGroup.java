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

public class SportletGroup extends BaseObject implements PortletGroup {

    public static final String SUPER = "0";
    public static final String BASE = "1";

    private String Name = "BASE";
    private static final SportletGroup baseGroup = new SportletGroup(BASE, "BASE");
    private static final SportletGroup superGroup = new SportletGroup(SUPER, "SUPER");

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

    public static PortletGroup getBaseGroup() {
        return baseGroup;
    }

    public static PortletGroup getSuperGroup() {
        return superGroup;
    }

    public boolean isBaseGroup() {
        if (getOid() == BASE) {
            return true;
        } else {
            return false;
        }
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
