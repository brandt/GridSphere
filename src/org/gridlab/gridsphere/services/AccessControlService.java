/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;

import java.util.Iterator;

/**
 * The AccessControlService provides access control information for users
 * concerning roles they have within the groups they belong to. Because
 * the AccessControlService doesn't modify any access control settings,
 * it is not implemented as a secure service.
 */
public interface AccessControlService extends PortletService {

    public boolean hasRoleInGroup(User user, String groupName, int roleName);

    public Iterator getRolesInGroup(User user, String groupName);

    public Iterator getAllGroupNames();

    public Iterator getAllUserNames();

    public Iterator getAllRoles();

    public Iterator getGroupNames(User user);

    public boolean isUserInGroup(User user, String groupName);

    public Iterator getUserNamesInGroup(String groupName);

}
