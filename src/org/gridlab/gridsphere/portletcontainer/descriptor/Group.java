/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

public class Group {

    private String GroupName = "";

    /**
     * gets the group name
     *
     * @returns GroupName
     */
    public String getGroupName() {
        return GroupName;
    }

    /**
     * sets the group name
     *
     * @param GroupName the group name
     */
    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

}

