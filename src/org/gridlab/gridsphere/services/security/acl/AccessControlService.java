/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.security.acl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.service.PortletService;

import java.util.List;

/**
 * The AccessControlService provides access control information for users
 * concerning roles they have within the groups they belong to. Because
 * the AccessControlService doesn't modify any access control settings,
 * it is not implemented as a secure service.
 */
public interface AccessControlService extends PortletService {

    /**
     * Returns list of super users
     */
    public List getSuperUsers();

    /**
     * Checks if a user has a particular role in a group
     *
     * @param user the User object
     * @param group the PortletGroup
     * @param role the PortletRole
     * @return true if the user has the specified role in the specified group, false otherwise
     */
    public boolean hasRoleInGroup(User user, PortletGroup group, PortletRole role);

    /**
     * Return the list of users associated with a particular group and possessing the specified role
     *
     * @param role the PortletRole
     * @param group the PortletGroup
     * @return the list of users associated with a particular group and possessing the specified role
     */
    public List getUsersInGroup(PortletRole role, PortletGroup group);

    /**
     * Return a list of PortletRole objects for a user in a group
     *
     * @param user the User object
     * @param group the PortletGroup
     * @return a list of PortletRole objects
     */
    public List getRolesInGroup(User user, PortletGroup group);

    /**
     * Return a list of PortletGroup objects
     *
     * @return a list of PortletGroup objects
     */
    public List getAllGroups();

    /**
     * Returns a list of PortletGroup objects associated with a user
     *
     * @param user the User object
     * @return the list of PortletGroup objects
     */
    public List getGroups(User user);

    /**
     * Check to see if a user is in a group
     *
     * @param user the User object
     * @param group the PortletGroup
     * @return true if the user in the PortletGroup, false otherwise
     */
    public boolean isUserInGroup(User user, PortletGroup group);


}
