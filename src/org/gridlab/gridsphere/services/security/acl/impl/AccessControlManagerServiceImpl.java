/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.impl.SportletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletRole;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.security.acl.AccessControlManagerService;


public class AccessControlManagerServiceImpl implements PortletServiceProvider, AccessControlManagerService {

    protected transient static PortletLog log = SportletLog.getInstance(AccessControlManagerServiceImpl.class);

    private PersistenceManagerRdbms pm = null;
    private String jdoUserACL = new String();
    private String jdoSportletGroup = new String();

    public AccessControlManagerServiceImpl() {
        super();
        pm = PersistenceManagerRdbms.getInstance();
        jdoUserACL = UserACL.class.getName();
        jdoSportletGroup = SportletGroup.class.getName();
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
    }

    public void destroy() {
    }

    /**
     * executes any delete command
     * @param command
     * @throws PortletServiceException
     */
    private void delete(String command) throws PortletServiceException {
        try {
            pm.deleteList(command);
        } catch (PersistenceManagerException e) {
            log.equals("Delete error " + e);
            throw new PortletServiceException("Delete Exception " + e);
        }
    }

    /**
     * Promotes a user to the role of super
     *
     * @param user the User object
     */
    public void addUserToSuperRole(User user) throws PortletServiceException {

        UserACL rootacl = new UserACL();
        rootacl.setUserID(user.getID());
        rootacl.setRoleID(PortletRole.SUPER.getID());
        rootacl.setGroupID(PortletGroup.SUPER.getID());
        rootacl.setStatus(UserACL.STATUS_APPROVED);

        try {
            pm.create(rootacl);
        } catch (PersistenceManagerException e) {
            log.error("Exception :" + e);
            throw new PortletServiceException("Unable to add user to super role: " + e);
        }
    }

    /**
     * Creates a new group
     *
     * @param Name the name of the new group
     * @throws PortletServiceException
     */
    public void createNewGroup(String Name) throws PortletServiceException {
        SportletGroup ga = new SportletGroup(Name);
        try {
            pm.create(ga);
        } catch (PersistenceManagerException e) {
            log.error("Transaction Exception " + e);
            throw new PortletServiceException(e.toString());
        }
    }

    /**
     * Rename an existing group
     *
     * @param group the PortletGroup to modify
     * @param newGroupName the name of the new group
     * @throws PortletServiceException
     */
    public void renameGroup(PortletGroup group, String newGroupName) throws PortletServiceException {
        String command =
                "select g from "+jdoSportletGroup+" g where g.ObjectID=" + group.getID();
        try {
            SportletGroup pg = (SportletGroup) pm.restoreObject(command);
            pg.setName(newGroupName);
            pm.update(pg);
        } catch (PersistenceManagerException e) {
            throw new PortletServiceException("Persistence Error:" + e.toString());
        }
    }

    /**
     * Removes a group
     *
     * @param group the PortletGroup
     */
    public void removeGroup(PortletGroup group) throws PortletServiceException {
        String groupid = group.getID();
        String command =
                "select g from "+jdoSportletGroup+" g where g.ObjectID=" + groupid;
        delete(command);

        command =
                "select g from "+jdoUserACL+" g where g.GroupID=" + groupid;
        delete(command);
    }

    /**
     * Add a role to a user in a group or changes the status of that user to
     * that specific role
     *
     * @param user the User object
     * @param group the PortletGroup
     */
    public void approveUserInGroup(User user, PortletGroup group) throws PortletServiceException {
        // all users need to make an accountrequest to get in groups, without it ... no

        String command2 = "select acl from "+jdoUserACL+" acl where " +
                "UserID=\"" + user.getID() + "\" and GroupID=\"" + group.getID() + "\" and Status=" + UserACL.STATUS_APPROVED;
        String command = "select acl from "+jdoUserACL+" acl where " +
                "UserID=\"" + user.getID() + "\" and GroupID=\"" + group.getID() + "\" and Status=" + UserACL.STATUS_NOT_APPROVED;

        UserACL notapproved = null;
        try {
            notapproved = (UserACL) pm.restoreObject(command);
            if (notapproved == null) {
                log.error("User " + user.getFullName() + " did not requested a role with an accountrequest change");
            } else {
                // we don't want to approve a superuserrole by an admin!
                if (notapproved.getRoleID() != SportletRole.SUPER.getID()) {
                    // delete the status the user has until now
                    UserACL approved = (UserACL) pm.restoreObject(command2);
                    if (approved != null) {
                        pm.delete(approved);
                    }
                    notapproved.setStatus(notapproved.STATUS_APPROVED);
                    pm.update(notapproved);
                    log.info("Approved ACL for user " + user.getGivenName() + " in group " + group.getName());
                } else {
                    log.info("User can not approve superuserrole!");
                }
            }

        } catch (PersistenceManagerException e) {
            log.error("TransactionException " + e);
            throw new PortletServiceException(e.toString());
        }
    }

    /**
     * Removes a user from a group
     *
     * @param user the User object
     * @param group the PortletGroup
     */
    public void removeUserFromGroup(User user, PortletGroup group) throws PortletServiceException {
        String uid = user.getID();
        String gid = group.getID();
        if (log.isDebugEnabled()) {
            log.debug("Attempting to remove user " + uid + " from group " + gid);
        }
        String command =
                "select r from "+jdoUserACL+" r where r.UserID=" + uid +
                " and r.GroupID=" + gid + " and r.Status=" + UserACL.STATUS_APPROVED;
        delete(command);
    }

    /**
     * Removes a user from the grouprequest for a group
     * @param user user object
     * @param group the group
     */
    public void removeUserGroupRequest(User user, PortletGroup group) throws PortletServiceException {
        String command =
                "select r from "+jdoUserACL+" r where r.UserID=" + user.getID() +
                " and r.GroupID=" + group.getID() + " and r.Status=" + UserACL.STATUS_NOT_APPROVED;
        delete(command);

    }
}

