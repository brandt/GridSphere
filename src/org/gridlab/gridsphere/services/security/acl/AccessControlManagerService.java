/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.security.acl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;

public interface AccessControlManagerService extends PortletService {

    /**
     * Promotes a user to the role of super
     *
     * @param user the Role object
     */
    public void addUserToSuperRole(User user) throws PortletServiceException ;

    /**
     * Creates a new group
     *
     * @param groupName the name of the new group
     */
    public void createNewGroup(String groupName) throws PortletServiceException ;

    /**
     * Rename an existing group
     *
     * @param group the PortletGroup to modify
     * @param newGroupName the name of the new group
     */
    public void renameGroup(PortletGroup group, String newGroupName) throws PortletServiceException ;

    /**
     * Removes a group
     *
     * @param group the PortletGroup
     */
    public void removeGroup(PortletGroup group) throws PortletServiceException ;


    /**
     * Add a user to a group with a specified role
     *
     * @param user the Role object
     * @param group the PortletGroup
     */
    public void approveUserInGroup(User user, PortletGroup group) throws PortletServiceException ;


    /**
     * Removes a user from a group
     *
     * @param user the user object
     * @param group the PortletGroup
     */
    public void removeUserFromGroup(User user, PortletGroup group) throws PortletServiceException ;


    /**
     * Removes a user from the grouprequest for a group
     * @param user user object
     * @param group the group
     */
    public void removeUserGroupRequest(User user, PortletGroup group) throws PortletServiceException ;
}
