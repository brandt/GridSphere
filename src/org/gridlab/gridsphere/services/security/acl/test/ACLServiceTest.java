/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.security.acl.test;

import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.test.ServiceTest;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.impl.*;

import org.gridlab.gridsphere.services.security.acl.AccessControlService;
import org.gridlab.gridsphere.services.security.acl.AccessControlManagerService;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.AssertionFailedError;

import java.util.Properties;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;

public class ACLServiceTest extends ServiceTest {

    private static PortletLog log = SportletLog.getInstance(ACLServiceTest.class);
    private static AccessControlService aclService = null;
    private static AccessControlManagerService aclManagerService = null;

    public static final String[] GROUPS = {"cactus", "portals", "triana"};
    public static final String[] USERS = {"hans", "franz", "josef"};

    private SportletUser hans, franz, josef;
    private PortletGroup cactus, portals, triana;

    public ACLServiceTest(String name) {
        super(name);
    }

    public static void main (String[] args) throws Exception{
        junit.textui.TestRunner.run(suite());
    }

    protected void setUp() {
        super.setUp();

        int i;
        log.info("setting up services");
        // create services
        try {
            aclService = (AccessControlService)factory.createPortletService(AccessControlService.class, props, null, true);
            aclManagerService = (AccessControlManagerService)factory.createPortletService(AccessControlManagerService.class, props, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize access services: ", e);
        }

        // Create fake users--  ACL only assumes ID from Users
        hans = new SportletUserImpl();
        hans.setGivenName(USERS[0]);
        hans.setUserID(USERS[0]);

        franz = new SportletUserImpl();
        franz.setGivenName(USERS[1]);
        franz.setUserID(USERS[1]);

        josef = new SportletUserImpl();
        josef.setGivenName(USERS[2]);
        josef.setUserID(USERS[2]);

    }

    public static Test suite ( ) {
        return new TestSuite(ACLServiceTest.class);
    }

    public void testCreateNewGroups() {
        int i;
        makeNewGroups();
    }

    public void testRenameGroups() {

        makeNewGroups();

        int i;
        String newgroup;
        List allgroups = null;
        List allgroupnames = new Vector();
        List newgroupnames = null;

        String newname = "flobbery";
        // rename cactus group to "flobbery"
        aclManagerService.renameGroup(cactus, newname);

        allgroups = aclService.getAllGroups();
        Iterator it = allgroups.iterator();
        while (it.hasNext()) {
            PortletGroup g = (PortletGroup)it.next();
            allgroupnames.add(g.getName());
        }
        // make sure no cactus group name is there
        assertTrue(!allgroupnames.contains(cactus.getName()));

        // make sure newname group name is there
        assertTrue(allgroupnames.contains(newname));
    }

    // Tests removal of a group
    public void testRemoveGroup() {

        makeNewGroups();
        addUsersToGroups();

        List allgroups = aclService.getAllGroups();
        PortletGroup lastgroup = (PortletGroup)allgroups.get(allgroups.size() - 1);
        String lastgroupname = lastgroup.getName();

        // remove last group
        aclManagerService.removeGroup(lastgroup);
        allgroups = aclService.getAllGroups();

        // should not contain last group
        assertTrue(!allgroups.contains(lastgroup));
    }

    public void testAddUsers() {

        makeNewGroups();
        addUsersToGroups();

        // test getUsersInGroup
        List users = aclService.getUsersInGroup(SportletRole.getUserRole(), cactus);
        assertEquals(users.size(), 1);

        User user = (User)users.get(0);
        assertEquals(user.getUserID(), hans.getUserID());

        List groups = aclService.getGroups(franz);
        assertEquals(groups.size(), 1);
        PortletGroup g = (PortletGroup)groups.get(0);
        assertEquals(g.getName(), triana.getName());

        // remove hans from cactus -- also move franz which should do nothing
        aclManagerService.removeUserFromGroup(hans, cactus);
        aclManagerService.removeUserFromGroup(franz, cactus);

        groups = aclService.getGroups(hans);
        assertTrue(!groups.contains(cactus));
        assertTrue(!aclService.isUserInGroup(hans, cactus));
    }

    public void testRoles() {

        makeNewGroups();
        addUsersToGroups();

        List roles;
        aclManagerService.addRoleInGroup(hans, cactus, SportletRole.getAdminRole());
        assertTrue(aclService.hasRoleInGroup(hans, cactus, SportletRole.getAdminRole()));

        roles = aclService.getRolesInGroup(hans, cactus);
        // should have user and admin role
        assertEquals(roles.size(), 2);

        aclManagerService.removeUserRoleFromGroup(hans, cactus, SportletRole.getUserRole());
        roles = aclService.getRolesInGroup(hans, cactus);
        // should have user role
        assertEquals(roles.size(), 1);
        PortletRole role = (PortletRole)roles.get(0);
        assertEquals(role.getRole(), SportletRole.USER);

        // remove josef from user role hence he has no roles
        aclManagerService.removeUserRoleFromGroup(josef, portals, SportletRole.getUserRole());
        List group = aclService.getGroups(josef);
        assertTrue(!aclService.isUserInGroup(josef, portals));
        assertTrue(!group.contains(portals));

        // should do nothing
        aclManagerService.removeUserRoleFromGroup(josef, portals, SportletRole.getAdminRole());

    }


    public void testSuperRole() {

        makeNewGroups();
        addUsersToGroups();

        aclManagerService.addUserToSuperRole(hans);
        aclManagerService.addUserToSuperRole(franz);
        List supers = aclService.getSuperUsers();
        assertEquals(supers.size(), 2);
        User user = (User)supers.get(0);
        assertEquals(user.getUserID(), hans.getUserID());
    }

    // Creates the cactus, triana and portals groups
    protected void makeNewGroups() {
         // First we need to create some groups
        int i;
        List groups = new Vector(GROUPS.length);
        for (i = 0; i < GROUPS.length; i++) {
            groups.set(i, GROUPS[i]);
            aclManagerService.createNewGroup(GROUPS[i]);
        }
        // retrieve all groups
        List portletGroups = aclService.getAllGroups();

        // should only have three groups
        assertEquals(portletGroups.size(), 3);

        cactus = (PortletGroup)portletGroups.get(0);
        portals = (PortletGroup)portletGroups.get(1);
        triana = (PortletGroup)portletGroups.get(2);

        // Make sure we have proper ordering
        assertEquals(cactus.getName(), GROUPS[0]);
        assertEquals(portals.getName(), GROUPS[1]);
        assertEquals(triana.getName(), GROUPS[2]);
    }

    /**
     * Add hans to cactus, franz to triana and josef to portals
     */
    protected void addUsersToGroups() {

        aclManagerService.addUserToGroup(hans, cactus, SportletRole.getUserRole());
        aclManagerService.addUserToGroup(franz, triana, SportletRole.getUserRole());
        aclManagerService.addUserToGroup(josef, portals, SportletRole.getUserRole());

        assertTrue(aclService.isUserInGroup(hans, cactus));
        assertTrue(aclService.isUserInGroup(franz, triana));
        assertTrue(aclService.isUserInGroup(josef, portals));
        assertTrue(!aclService.isUserInGroup(josef, cactus));
    }

}
