/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.AccessDeniedException;
import org.gridlab.gridsphere.portlet.PortletRoles;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.AccessControlService;

import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;

public abstract class BaseSecureServiceImpl {

    private static PortletLog log = SportletLog.getInstance(BaseSecureServiceImpl.class);
    protected AccessControlData ACLData = AccessControlData.getInstance();
    protected User creator = null;
    protected List groups = null;
    protected int role = PortletRoles.USER;

    public BaseSecureServiceImpl(User creator, int role) {
        // Get groups of user
        this.creator = creator;
        this.role = role;
        Iterator it = ACLData.keySet().iterator();
        String groupName = null;
        groups = new ArrayList();
        // Iterate thru all groups
        while (it.hasNext()) {
            groupName = (String)it.next();
            Map group = (Map)ACLData.get(groupName);
            List roles = (List)group.get(creator.getID());
            if (roles != null) {
                if (roles.contains((Object)role)) {
                    groups.add(groupName);
                }
            }
        }
    }

    protected void checkForGroup(String groupName) throws AccessDeniedException {
        if (!groups.contains(groupName)) {
            throw new AccessDeniedException("Permission denied");
        }
    }

}
