/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.security.acl.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;


import java.util.List;

/**
 * The AccessControlService provides access control information for users
 * concerning roles they have within the groups they belong to. Because
 * the AccessControlService doesn't modify any access control settings,
 * it is not implemented as a secure service.
 */
public class AccessControlServiceImpl implements PortletServiceProvider, AccessControlService {

    /**
     * Initializes the portlet service.
     * The init method is invoked by the portlet container immediately after a portlet service has
     * been instantiated and before it is passed to the requestor.
     *
     * @param config the service configuration
     * @throws PortletServiceUnavailableException if an error occurs during initialization
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
    }

    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy() {
    }

    /**
     * Returns list of super users
     */
    public List getSuperUsers() {
        return null;
    }

    public boolean isSuperUser(User user) {
        return true;
    }

    /**
     * Checks if a user has a particular role in a group
     *
     * @param user the Role object
     * @param group the PortletGroup
     * @param role the PortletRole
     * @return true if the user has the specified role in the specified group, false otherwise
     */
    public boolean hasRoleInGroup(User user, PortletGroup group, PortletRole role) {
        return false;
    }

    /**
     * Return the list of users associated with a particular group and possessing the specified role
     *
     * @param role the PortletRole
     * @param group the PortletGroup
     * @return the list of users associated with a particular group and possessing the specified role
     */
    public List getUsersInGroup(PortletRole role, PortletGroup group) {
        return null;
    }

    /**
     * Return a list of PortletRole objects for a user in a group
     *
     * @param user the Role object
     * @param group the PortletGroup
     * @return a list of PortletRole objects
     */
    public List getRolesInGroup(User user, PortletGroup group) {
        return null;
    }

    /**
     * Return a list of PortletGroup objects
     *
     * @return a list of PortletGroup objects
     */
    public List getAllGroups() {
        return null;
    }

    /**
     * Return a list of all PortletRole objects
     *
     * @return a list of all PortletRole objects
     */
    public List getAllRoles() {
        return null;
    }

    /**
     * Returns a list of PortletGroup objects associated with a user
     *
     * @param user the Role object
     * @return the list of PortletGroup objects
     */
    public List getGroups(User user) {
        return null;
    }

    /**
     * Check to see if a user is in a group
     *
     * @param user the Role object
     * @param group the PortletGroup
     * @return true if the user in the PortletGroup, false otherwise
     */
    public boolean isUserInGroup(User user, PortletGroup group) {
        return false;
    }


}
