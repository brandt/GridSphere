/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 30, 2003
 * Time: 12:55:09 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.acl;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;

public interface GroupRequest extends GroupEntry {

    public void setUser(User user);

    public void setGroup(PortletGroup group);

    public void setRole(PortletRole role);

    public GroupAction getGroupAction();

    public void setGroupAction(GroupAction action);
}
