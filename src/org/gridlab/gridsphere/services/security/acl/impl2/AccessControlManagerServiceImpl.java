/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl2;

import org.gridlab.gridsphere.services.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.core.persistence.*;
import org.gridlab.gridsphere.core.persistence.castor.*;

import java.util.Random;


public class AccessControlManagerServiceImpl implements PortletServiceProvider, AccessControlManagerService {

    protected transient static PortletLog log = SportletLog.getInstance(AccessControlManagerServiceImpl.class);

    private PersistenceManagerRdbms pm = null;

    public AccessControlManagerServiceImpl() {
        super();
        pm = new PersistenceManagerRdbms();
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
        } catch (PersistenceException e) {
            log.equals("Delete error "+e);
            throw new PortletServiceException("Delete Exception "+e);
        }
    }

    /**
     * Promotes a user to the role of super
     *
     * @param user the User object
     */
    public void addUserToSuperRole(User user) {


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
        } catch (PersistenceException e) {
            log.error("Transaction Exception "+e);
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
                "select g from org.gridlab.gridsphere.portlet.impl.SportletGroup g where g.Oid="+group.getID();
        try {
            SportletGroup pg = (SportletGroup)pm.restoreObject(command);
            pg.setName(newGroupName);
            pm.update(pg);
        } catch (PersistenceException e) {
            throw new  PortletServiceException("Persistence Error:"+e.toString());
        }
    }

    /**
     * Removes a group
     *
     * @param group the PortletGroup
     */
    public void removeGroup(PortletGroup group) throws PortletServiceException {
        String command =
            "select g from org.gridlab.gridsphere.portlet.impl.SportletGroup g where g.Oid="+group.getID();
        delete(command);

    }

    /**
     * Add a role to a user in a group or changes the status of that user to
     * that specific role
     *
     * @param user the User object
     * @param group the PortletGroup
     */
    public void addRoleInGroup(User user, PortletGroup group) throws PortletServiceException {
        // all users need to make an accountrequest to get in groups, without it ... no

        String command = "select acl from org.gridlab.gridsphere.services.security.acl.impl2.UserACL acl where "+
                "UserID=\""+user.getID()+"\" and GroupID=\""+group.getID()+"\"";

        UserACL useracl = null;
        try {
            useracl = (UserACL)pm.restoreObject(command);
            if (useracl==null) {
                log.error("User "+user.getFullName()+" did not requested a role with an accountrequest change");
            } else {
                useracl.setStatus(useracl.STATUS_APPROVED);
                pm.update(useracl);
                log.info("Approved ACL for user "+user.getFullName()+" in group "+group.getName());
            }

        } catch (PersistenceException e) {
            log.error("TransactionException "+e);
            throw new PortletServiceException(e.toString());
        }
    }

    /**
     * Add a user to a group with the user role
     *
     * @param user the User object
     * @param group the PortletGroup
     */
    public void addUserToGroup(User user, PortletGroup group) throws PortletServiceException {
        addRoleInGroup(user, group);
    }


    /**
     * Removes a user from a group
     *
     * @param user the User object
     * @param group the PortletGroup
     */
    public void removeUserFromGroup(User user, PortletGroup group) throws PortletServiceException {
        String command =
            "select r from org.gridlab.gridsphere.services.security.acl.impl2.UserACL r where r.UserID="+user.getID()+
            " and r r.GroupID="+group.getID();
        delete(command);
    }

}

