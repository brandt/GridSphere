/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 29, 2003
 * Time: 3:53:06 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.acl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;

public interface GroupEntry {

    public String getID();

    public User getUser();

    public PortletGroup getGroup();

    public PortletRole getRole();
}
