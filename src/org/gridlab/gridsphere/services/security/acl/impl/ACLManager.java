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
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;

import java.util.List;
import java.util.Vector;

public class ACLManager {


    // work in progress, all in one place

    protected transient static PortletLog log = SportletLog.getInstance(ACLManager.class);

    private PersistenceManagerRdbms pm = null;


    public ACLManager() {
        super();
        pm = PersistenceManagerRdbms.getInstance();
    }

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
    public void addUserToSuperRole(User user) {

        UserACL rootacl = new UserACL();
        rootacl.setUserID(user.getID());
        rootacl.setRoleID(PortletRole.SUPER.getID());
        rootacl.setGroupID(SportletGroup.SUPER.getID());
        rootacl.setStatus(UserACL.STATUS_APPROVED);

        try {
            pm.create(rootacl);
        } catch (PersistenceManagerException e) {
            log.error("Exception :" + e);
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
                "select g from org.gridlab.gridsphere.portlet.impl.SportletGroup g where g.ObjectID=" + group.getID();
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
                "select g from org.gridlab.gridsphere.portlet.impl.SportletGroup g where g.ObjectID=" + groupid;
        delete(command);

        command =
                "select g from org.gridlab.gridsphere.services.security.acl.impl.UserACL g where g.GroupID=" + groupid;
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

        String command2 = "select acl from org.gridlab.gridsphere.services.security.acl.impl.UserACL acl where " +
                "UserID=\"" + user.getID() + "\" and GroupID=\"" + group.getID() + "\" and Status=" + UserACL.STATUS_APPROVED;
        String command = "select acl from org.gridlab.gridsphere.services.security.acl.impl.UserACL acl where " +
                "UserID=\"" + user.getID() + "\" and GroupID=\"" + group.getID() + "\" and Status=" + UserACL.STATUS_NOT_APPROVED;

        UserACL notapproved = null;
        try {
            notapproved = (UserACL) pm.restoreObject(command);
            if (notapproved == null) {
                log.error("User " + user.getFullName() + " did not requested a role with an accountrequest change");
            } else {
                // we don't want to approve a superuserrole by an admin!
                if (notapproved.getRoleID() != PortletRole.SUPER.getID()) {
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
        String command =
                "select r from org.gridlab.gridsphere.services.security.acl.impl.UserACL r where r.UserID=" + user.getID() +
                " and r.GroupID=" + group.getID() + " and r.Status=" + UserACL.STATUS_APPROVED;
        delete(command);
    }

    /**
     * Removes a user from the grouprequest for a group
     * @param user user object
     * @param group the group
     */
    public void removeUserGroupRequest(User user, PortletGroup group) throws PortletServiceException {
        String command =
                "select r from org.gridlab.gridsphere.services.security.acl.impl.UserACL r where r.UserID=" + user.getID() +
                " and r.GroupID=" + group.getID() + " and r.Status=" + UserACL.STATUS_NOT_APPROVED;
        delete(command);

    }


        /**
     * returns true if a oql query is succsessfull
     * @param command oql query
     * @return true/false
     */
    private boolean queryACL(String command) {
        UserACL acl = null;
        try {
            acl = (UserACL)pm.restoreObject(command);
        } catch (PersistenceManagerException e) {
            log.error("PM Exception: "+e);
        }

        if (acl!=null) {
            return true;
        } else {
            return false;
        }
    }

    private List listACL(String command) {
        List result = null;
        try {
            result = pm.restoreList(command);
        } catch (PersistenceManagerException e) {
            log.error("PM Exception: "+e);
        }
        return result;
    }

    /**
     * Returns list of super users
     */
    public List getSuperUsers() throws  PortletServiceException {
    // @todo check the length of the second query string, could get too long!

        String command =
            "select u from org.gridlab.gridsphere.services.security.acl.impl.UserACL u where u.RoleID=\""+PortletRole.SUPER.getRole()+
                "\" and u.Status="+UserACL.STATUS_APPROVED;
        //log.info(command);
        List acl = listACL(command);

        command = "select u from org.gridlab.gridsphere.portlet.impl.SportletUserImpl u where ";
        for (int i=0;i<acl.size();i++) {
            if (i!=0) {
                command = command +" and ";
            }
            command = command + " u.ObjectID=\""+((UserACL)acl.get(i)).getUserID()+"\"";
        }
        //log.info(command);
        return listACL(command);
    }

    //@todo fillin issuperuser , see usermanagerserviceimpl!
    public boolean isSuperUser(User user) {
        return true;
    }

    /**
     * Checks if a user has a particular role in a group
     *
     * @param user the User object
     * @param group the PortletGroup
     * @param role the PortletRole
     * @return true if the user has the specified role in the specified group, false otherwise
     */
    public boolean hasRoleInGroup(User user, PortletGroup group, PortletRole role) throws PortletServiceException{

        String command =
            " select ua from org.gridlab.gridsphere.services.security.acl.impl.UserACL ua where "+
            "UserID=\""+user.getID()+"\" and RoleID="+role.getID()+" and GroupID=\""+group.getID()+
            "\" and Status="+UserACL.STATUS_APPROVED;

        return (queryACL(command));
    }

    /**
     * Return the list of users associated with a particular group and possessing the specified role
     *
     * @param role the PortletRole
     * @param group the PortletGroup
     * @return the list of users associated with a particular group and possessing the specified role
     */
    public List getUsersInGroup(PortletRole role, PortletGroup group) throws PortletServiceException{

        String command =
            "select u from org.gridlab.gridsphere.services.security.acl.impl.UserACL u where u.RoleID=\""+role.getID()+
            " and u.GroupID="+group.getID()+" and u.Status="+UserACL.STATUS_APPROVED;
        return listACL(command);
    }

    /**
     * Return a list of PortletGroup objects
     *
     * @return a list of PortletGroup objects
     */
    public List getAllGroups() throws PortletServiceException {

        String command =
            "select g from org.gridlab.gridsphere.portlet.impl.SportletGroup g";
        List result = null;

        try {
            result = pm.restoreList(command);
        } catch (PersistenceManagerException e) {
            log.error("Transaction error "+e);
            throw new PortletServiceException();
        }
        return result;
    }

    private PortletGroup getGroupByID(String id) throws PortletServiceException {
        String command =
            "select g from org.gridlab.gridsphere.portlet.impl.SportletGroup g where g.ObjectID=\""+id+"\"";
        PortletGroup g = null;
        try {
            g = (PortletGroup)pm.restoreObject(command);
        } catch (PersistenceManagerException e) {
            log.error("Exception " + e);
            throw new PortletServiceException("Could ot get group "+id);
        }

        return g;
    }

    /**
     * Returns a list of PortletGroup objects associated with a user
     *
     * @param user the User object
     * @return the list of PortletGroup objects
     */
    public List getGroups(User user) throws PortletServiceException{

        Vector result = new Vector();

        log.info("ID "+user.getID());

        String command =
            "select u from org.gridlab.gridsphere.services.security.acl.impl.UserACL u where u.UserID=\""+user.getID()+
                "\" and u.Status="+UserACL.STATUS_APPROVED;
        List acl = new Vector();

        try {
            acl=pm.restoreList(command);
        } catch (PersistenceManagerException e) {
            log.error("Exception " + e);
            throw new PortletServiceException("Could not get ACLs "+e);
        }

        PortletGroup g = null;
        for (int i=0; i<acl.size();i++) {
            g = getGroupByID(((UserACL)acl.get(i)).getGroupID());
            result.add(g);
        }
        return result;
    }

    /**
     * Check to see if a user is in a group
     *
     * @param user the User object
     * @param group the PortletGroup
     * @return true if the user in the PortletGroup, false otherwise
     */
    public boolean isUserInGroup(User user, PortletGroup group) throws PortletServiceException{

        String command =
            "select u from org.gridlab.gridsphere.services.security.acl.impl.UserACL u where u.UserID=\""+user.getID()+
            "\" and u.GroupID="+group.getID()+" and u.Status="+UserACL.STATUS_APPROVED;

       return queryACL(command);

    }

    //@todo fillin getalroles
    public List getAllRoles() {
        return null;
    }

}

