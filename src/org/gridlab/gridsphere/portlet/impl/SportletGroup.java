/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.e">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletGroup;

public class SportletGroup implements PortletGroup {

    public static final int BASE = 0;

    private int ID = BASE;
    private String groupName = "BASE";
    private static final SportletGroup baseGroup = new SportletGroup(BASE, "BASE");

    public SportletGroup(int id, String groupName) {
        this.ID = id;
        this.groupName = groupName;
    }

    public SportletGroup() {
        super();
    }

    public static PortletGroup getBaseGroup() {
        return baseGroup;
    }

    public boolean isBaseGroup() {
        if (ID == BASE) {
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return groupName;
    }

    public int getID() {
        return ID;
    }

}
