/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.e">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletGroup;

public class SportletGroup implements PortletGroup {

    public static final int BASE = 0;

    private int group = BASE;
    private String groupName = "BASE";

    public SportletGroup(int group, String groupName) {
        this.group = group;
        this.groupName = groupName;
    }

    public SportletGroup() {
        super();
    }

    public static PortletGroup getBaseGroup() {
        return new SportletGroup(BASE, "BASE");
    }

    public boolean isBaseGroup() {
        if (group == BASE) {
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return groupName;
    }

    public int getGroup() {
        return group;
    }

    public int getID() {
        return group;
    }

}
