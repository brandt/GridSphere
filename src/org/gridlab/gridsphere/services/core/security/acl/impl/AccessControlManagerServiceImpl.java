/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Jan 31, 2003
 * Time: 4:06:44 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.core.security.acl.impl;

import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.security.acl.GroupEntry;
import org.gridlab.gridsphere.services.core.security.acl.GroupRequest;
import org.gridlab.gridsphere.services.core.security.acl.InvalidGroupRequestException;
import org.gridlab.gridsphere.services.core.user.impl.GridSphereUserManager;

import java.util.List;

public class AccessControlManagerServiceImpl implements AccessControlManagerService, PortletServiceProvider {

    private GridSphereUserManager aclManager = GridSphereUserManager.getInstance();
    private PortletServiceAuthorizer authorizer = null;

    public AccessControlManagerServiceImpl(PortletServiceAuthorizer authorizer) {
        this.authorizer = authorizer;
    }

    /**
     * Initializes the portlet service.
     * The init method is invoked by the portlet container immediately after a portlet service has
     * been instantiated and before it is passed to the requestor.
     *
     * @param config the service configuration
     * @throws PortletServiceUnavailableException if an error occurs during initialization
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        aclManager.init(config);
    }

    /**
     * The destroy method is invoked by the portlet container to destroy a portlet service.
     * This method must free all resources allocated to the portlet service.
     */
    public void destroy() {}

    /*** PORTLET GROUP METHODS ***/

    public List getGroups() {
        return aclManager.getGroups();
    }

    public PortletGroup getGroup(String groupId) {
        return aclManager.getGroup(groupId);
    }

    public PortletGroup getGroupByName(String groupName) {
        return aclManager.getGroupByName(groupName);
    }

    public String getGroupDescription(String groupName) {
        return aclManager.getGroupDescription(groupName);
    }

    /*** GROUP REQUEST METHODS ***/

    public List getGroupRequests() {
        return aclManager.getGroupRequests();
    }

    public List getGroupRequests(User user) {
        return aclManager.getGroupRequests(user);
    }

    public List getGroupRequests(PortletGroup group) {
        return aclManager.getGroupRequests(group);
    }

    public List getGroupRequestsForGroups(List groups) {
        return aclManager.getGroupRequestsForGroups(groups);
    }

    public GroupRequest getGroupRequest(String id) {
        return aclManager.getGroupRequest(id);
    }

    public GroupRequest createGroupRequest() {
        return aclManager.createGroupRequest();
    }

    public GroupRequest createGroupRequest(GroupEntry entry) {
        return aclManager.createGroupRequest(entry);
    }

    public void validateGroupRequest(GroupRequest request)
            throws InvalidGroupRequestException {
        aclManager.validateGroupRequest(request);
    }

    public void submitGroupRequest(GroupRequest request)
            throws InvalidGroupRequestException {
        aclManager.submitGroupRequest(request);
    }

    public void approveGroupRequest(GroupRequest request) {
        aclManager.approveGroupRequest(request);
    }

    public void denyGroupRequest(GroupRequest request) {
        aclManager.denyGroupRequest(request);
    }

    /*** GROUP ENTRY METHODS ***/

    public List getGroupEntries() {
        return aclManager.getGroupEntries();
    }

    public List getGroupEntries(User user) {
        return aclManager.getGroupEntries(user);
    }

    public List getGroupEntries(PortletGroup group) {
        return aclManager.getGroupEntries(group);
    }

    public List getGroupEntriesForGroups(List groups) {
        return aclManager.getGroupEntriesForGroups(groups);
    }

    public GroupEntry getGroupEntry(String id) {
        return aclManager.getGroupEntry(id);
    }

    public GroupEntry getGroupEntry(User user, PortletGroup group) {
        return aclManager.getGroupEntry(user, group);
    }

    /*** ACCESS CONTROL LOGIC METHODS ***/

    public List getGroups(User user) {
        return aclManager.getGroups(user);
    }

    public List getGroupsNotMemberOf(User user) {
        return aclManager.getGroupsNotMemberOf(user);
    }

    public List getUsers(PortletGroup group) {
        return aclManager.getUsers(group);
    }

    public List getUsersNotInGroup(PortletGroup group) {
        return aclManager.getUsersNotInGroup(group);
    }

    public boolean isUserInGroup(User user, PortletGroup group) {
        return aclManager.isUserInGroup(user, group);
    }

    public PortletRole getRoleInGroup(User user, PortletGroup group) {
        return aclManager.getRoleInGroup(user, group);
    }

    public boolean hasRoleInGroup(User user, PortletGroup group, PortletRole role) {
        return aclManager.hasRoleInGroup(user, group, role);
    }

    public boolean hasAdminRoleInGroup(User user, PortletGroup group) {
        return aclManager.hasAdminRoleInGroup(user, group);
    }

    public boolean hasUserRoleInGroup(User user, PortletGroup group) {
        return aclManager.hasUserRoleInGroup(user, group);
    }

    public boolean hasGuestRoleInGroup(User user, PortletGroup group) {
        return aclManager.hasGuestRoleInGroup(user, group);
    }

    public List getUsersWithSuperRole() {
        return aclManager.getUsersWithSuperRole();
    }

    public void grantSuperRole(User user) {
        aclManager.grantSuperRole(user);
    }

    public void revokeSuperRole(User user) {
        aclManager.revokeSuperRole(user);
    }

    public boolean hasSuperRole(User user) {
        return aclManager.hasSuperRole(user);
    }
}
