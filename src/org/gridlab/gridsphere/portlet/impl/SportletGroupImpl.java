/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletGroup;

import java.util.ArrayList;
import java.util.List;

public class SportletGroupImpl implements PortletGroup {

    public static final int BASE = 0;

    private int group = BASE;
    private String groupName = "BASE";

    private SportletGroupImpl(int group) {
        this.group = group;
    }

    public SportletGroupImpl(int group, String groupName) {
        this.group = group;
        this.groupName = groupName;
    }

    public static SportletGroupImpl getBaseGroup() {
        return new SportletGroupImpl(BASE);
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
