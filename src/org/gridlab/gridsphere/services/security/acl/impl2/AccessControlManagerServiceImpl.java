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
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.core.persistence.*;
import org.gridlab.gridsphere.core.persistence.castor.Transaction;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManager;
import org.gridlab.gridsphere.core.persistence.castor.Result;
import org.gridlab.gridsphere.core.persistence.castor.Query;

public class AccessControlManagerServiceImpl implements PortletServiceProvider, AccessControlManagerService {
    static org.apache.log4j.Category cat = org.apache.log4j.Category.getInstance(AccessControlManagerServiceImpl.class.getName());

    PersistenceInterface pm = null;

    public AccessControlManagerServiceImpl() throws PortletServiceException  {
        super();
        try {
            pm = new PersistenceManager("/Users/wehrens/gridsphere/webapps/WEB-INF/conf/database.xml","portal");
            cat.info("AccessControlManagerServiceImpl done");
        } catch (ConfigurationException e) {
            throw new PortletServiceException(e.toString());
        }

    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
    }

    public void destroy() {
    }

    /**
     *  creates a new unique ID
     */
    private int getNewID() {
        Long l = new Long(System.currentTimeMillis()) ;
        return l.intValue();
    }

    private void delete(String command) throws PortletServiceException {

        try {
            pm.begin();
            Query query = pm.getQuery();
            Result res = query.execute(command);
            while (res.hasMore()) {
                pm.delete(res.next());
            }
            pm.commit();
        } catch (TransactionException e) {
            pm.close();
            throw new PortletServiceException(e.toString());
        } catch (QueryException e) {
            pm.close();
            throw new PortletServiceException(e.toString());
        } catch (DeleteException e) {
            pm.close();
            throw new PortletServiceException(e.toString());
        }
        pm.close();
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
     * @param groupName the name of the new group
     */
    public void createNewGroup(String Name) throws PortletServiceException {


        Groups ga = new Groups();
        ga.setName(Name);
        ga.setID(this.getNewID());

        cat.debug("Group "+ga.getName()+" ID " +ga.getID());

        try {
            pm.begin();
            pm.create(ga);
            pm.commit();
        } catch (TransactionException e) {
            pm.close();
            cat.error("Transaction Exception "+e);
            throw new PortletServiceException(e.toString());
        } catch (CreateException e) {
            pm.close();
            cat.error("Create Exception "+e);
            throw new PortletServiceException(e.toString());
        }

        pm.close();
    }

    /**
     * Rename an existing group
     *
     * @param group the PortletGroup to modify
     * @param newGroupName the name of the new group
     */
    public void renameGroup(PortletGroup group, String newGroupName) throws PortletServiceException {

        String command =
                "select g from org.gridlab.gridsphere.services.security.acl.impl2.Groups g where g.ID="+group.getID();
        Transaction tx = null;

        try {
            pm.begin();
            Query query = pm.getQuery();
            Result res = query.execute(command);
            Groups ga = (Groups)res.next();
            ga.setName(newGroupName);
            pm.commit();
        } catch (TransactionException e) {
            pm.close();
            cat.error("Transaction Exception "+e);
            throw new PortletServiceException(e.toString());
        } catch (QueryException e) {
            pm.close();
            cat.error("Problem querying "+e);
            throw new PortletServiceException(e.toString());
        }
        pm.close();

    }

    /**
     * Removes a group
     *
     * @param group the PortletGroup
     */
    public void removeGroup(PortletGroup group) throws PortletServiceException {

        String command =
            "select g from org.gridlab.gridsphere.services.security.acl.impl2.Groups g where g.ID="+group.getID();

        delete(command);

    }

    /**
     * Add a role to a user in a group
     *
     * @param user the User object
     * @param group the PortletGroup
     * @param role the PortletRole
     */
    public void addRoleInGroup(User user, PortletGroup group, PortletRole role) throws PortletServiceException {

        UserACL ur = new UserACL(user.getID(), role.getID(), group.getID());

        try {
            pm.begin();
            pm.create(ur);
            pm.commit();
        } catch (TransactionException e) {
            pm.close();
            cat.error("TransactionException "+e);
            throw new PortletServiceException(e.toString());
        } catch (CreateException e) {
            pm.close();
            cat.error("CreationException "+e);
            throw new PortletServiceException(e.toString());
        }
        cat.info("created user "+user.getUserID()+" in group "+group.getName());
        cat.info("userid "+ur.getUserID()+" groupid "+ur.getGroupID());
        pm.close();

    }

    /**
     * Add a user to a group with a specified role
     *
     * @param user the User object
     * @param group the PortletGroup
     * @param role the PortletRole
     */
    public void addUserToGroup(User user, PortletGroup group, PortletRole role) throws PortletServiceException {
        addRoleInGroup(user, group, role); // can have a standardrole here
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

    /**
     * Remove a specified user role from a group
     *
     * @param user the User object
     * @param group the PortletGroup
     * @param role the PortletRole
     */
    public void removeUserRoleFromGroup(User user, PortletGroup group, PortletRole role) throws PortletServiceException {

        String command =
            "select r from org.gridlab.gridsphere.services.security.acl.impl2.UserACL r where r.UserID="+user.getID()+
            " and r r.GroupID="+group.getID()+" and r.RoleID="+role.getID();

        delete(command);
    }
}

