/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.services.security.acl.impl2;

import org.gridlab.gridsphere.services.security.acl.AccessControlService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletRole;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.core.persistence.*;
import org.gridlab.gridsphere.core.persistence.castor.*;
import org.gridlab.gridsphere.portletcontainer.descriptor.Role;

import java.util.List;
import java.util.Vector;

public class AccessControlServiceImpl  implements AccessControlService, PortletServiceProvider {

    protected transient static PortletLog log = SportletLog.getInstance(AccessControlServiceImpl.class);

    PersistenceManagerRdbms pm = null;

    public AccessControlServiceImpl() throws PersistenceException {
        super();

        pm = new PersistenceManagerRdbms();

        log.info("AccessControlServiceImpl constructor done ");
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
    }

    public void destroy() {
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
        } catch (PersistenceException e) {
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
        } catch (PersistenceException e) {
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
            "select u from org.gridlab.gridsphere.services.security.acl.impl2.UserACL u where u.RoleID=\""+SportletRole.getSuperRole().getRole()+
                "\" and u.Status="+UserACL.STATUS_APPROVED;
        //log.info(command);
        List acl = listACL(command);

        command = "select u from org.gridlab.gridsphere.portlet.impl.SportletUserImpl u where ";
        for (int i=0;i<acl.size();i++) {
            if (i!=0) {
                command = command +" and ";
            }
            command = command + " u.Oid=\""+((UserACL)acl.get(i)).getUserID()+"\"";
        }
        //log.info(command);
        return listACL(command);
    }

    public boolean isSuperUser(User user) {
        return true; // needs to be saved somewhere
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
            " select ua from org.gridlab.gridsphere.services.security.acl.impl2.UserACL ua where "+
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
            "select u from org.gridlab.gridsphere.services.security.acl.impl2.UserACL u where u.RoleID=\""+role.getID()+
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
        } catch (PersistenceException e) {
            log.error("Transaction error "+e);
            throw new PortletServiceException();
        }
        return result;
    }

    private PortletGroup getGroupByID(String id) throws PortletServiceException {
        String command =
            "select g from org.gridlab.gridsphere.portlet.impl.SportletGroup g where g.Oid=\""+id+"\"";
        PortletGroup g = null;
        try {
            g = (PortletGroup)pm.restoreObject(command);
        } catch (PersistenceException e) {
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
            "select u from org.gridlab.gridsphere.services.security.acl.impl2.UserACL u where u.UserID=\""+user.getID()+
                "\" and u.Status="+UserACL.STATUS_APPROVED;
        List acl = new Vector();

        try {
            acl=pm.restoreList(command);
        } catch (PersistenceException e) {
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
            "select u from org.gridlab.gridsphere.services.security.acl.impl2.UserACL u where u.UserID=\""+user.getID()+
            "\" and u.GroupID="+group.getID()+" and u.Status="+UserACL.STATUS_APPROVED;

       return queryACL(command);

    }

    public List getAllRoles() {
        return null;
    }
}

