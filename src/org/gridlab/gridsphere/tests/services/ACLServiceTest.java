/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tests.services;

import org.gridlab.gridsphere.services.AccessControlService;
import org.gridlab.gridsphere.services.AccessControlManagerService;
import org.gridlab.gridsphere.services.ServletParsingService;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletGroupImpl;
import org.gridlab.gridsphere.tests.services.ServiceTest;

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

    private List susers = null;
    private List groups = null;

    public static final int NUMGROUPS = 10;
    public static final int NUMPEOPLE = 10;

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
        // Create both services using mock ServletConfig
        try {
            aclService = (AccessControlService)factory.createPortletService(AccessControlService.class, props, null, true);
            aclManagerService = (AccessControlManagerService)factory.createPortletService(AccessControlManagerService.class, props, null, true);
        } catch (Exception e) {
            log.error("Unable to initailize access services: ", e);
            System.exit(-1);
        }

        // Create fake groups
        log.info("creating fake groups");
        groups = new Vector(NUMGROUPS);
        for (i = 0; i < NUMGROUPS; i++) {
            SportletGroupImpl group = new SportletGroupImpl(i, "group" + i);
            groups.add("group" + i);
        }

        // Create fake users--  ACL only assumes ID from Users
        susers = new Vector(NUMPEOPLE);
        for (i = 0; i < NUMPEOPLE; i++) {
            SportletUserImpl suser = new SportletUserImpl();
            suser.setFullName("Person: " + i);
            suser.setID("" + i);
            susers.add(suser);
        }
    }

    public static Test suite ( ) {
        return new TestSuite(ACLServiceTest.class);
    }

    public void testGroups() {
        int i;
        String newgroup;
        List allgroups = null;
        List allgroupnames = null;
        List newgroupnames = null;

        // Create a bunch of groups
        for (i = 0; i < NUMGROUPS; i++) {
            newgroup = "group" + i;
            newgroupnames.add(newgroup);
            aclManagerService.createNewGroup(newgroup);
        }

        // Retrieve groups
        allgroups = aclService.getAllGroups();

        // They should be equal
        for (i = 0; i < NUMGROUPS; i++) {
            PortletGroup pg = (PortletGroup)allgroups.get(i);
            allgroupnames.add(pg.getName());
        }
        assertEquals(allgroupnames, newgroupnames);

        // Rename groups
        //newgroups = new Vector(NUMGROUPS);

        for (i = 0; i < NUMGROUPS; i++) {
            //newgroups.add(i, "newgroup" + i);
            //aclManagerService.renameGroup((String)groups.get(i), (String)newgroups.get(i));
        }

        // Retrieve groups
        allgroupnames = aclService.getAllGroups();

        // They should be equal
        //assertEquals(newgroups, allgroupnames);

        // Remove the third group
        //aclManagerService.removeGroup((PortletGroup)newgroups.get(3));
        //newgroups.remove(3);
        //allgroupnames = aclService.getAllGroupNames();

        // They should be equal
        //assertEquals(newgroups, allgroupnames);

    }

    public void testUsers() {
        int i, j;

        // In first group add all 10 users and make them USER
        for (i = 0; i < NUMPEOPLE; i++) {
            //aclManagerService.addUserToGroup((User)susers.get(i), (String)groups.get(0), PortletRoles.getUserRole());
        }

        // Test that users are in group
        for (i = 0; i < NUMPEOPLE; i++) {
            //assertTrue(aclService.isUserInGroup((User)susers.get(i), (String)groups.get(0)));
        }

        // Get users

        //List users = aclService.getUsersInGroup(PortletRoles.getUserRole(), String groupName);

        //List aclService.getGroupNames(User user);

        //aclManagerService.removeUserFromGroup(User user, String groupName);

        //aclManagerService.removeUserRoleFromGroup(User user, String groupName, int roleName);

        // Make sure we have

        //List aclService.getAllUserNames();
    }

    public void testRoles() {

    //boolean aclService.hasRoleInGroup(User user, String groupName, int roleName);

    //List aclService.getRolesInGroup(User user, String groupName);

    //List aclService.getAllRoles();

    //List aclService.getSuperRoles();

    //aclManagerService.addUserToSuperRole(User user);

    //aclManagerService.addRoleInGroup(User user, String groupName, int roleName);

    }


    public void testOtherCrap() {
        int i, j;
        Iterator it = null;

        // Create a bunch of people in groups
        SportletUser[] user = new SportletUserImpl[NUMPEOPLE*NUMGROUPS];
        for (j = 0; j < NUMGROUPS; j++) {
            for (i = 0; i < NUMPEOPLE; i++) {
                user[10*j+i] = new SportletUserImpl();
                user[10*j+i].setFullName("person " + (10*j+i));
                user[10*j+i].setID("uid: " + (10*j+i));
            }
        }

        for (j = 0; j < NUMGROUPS; j++) {
            for (i = 0; i < NUMPEOPLE; i++) {
      //          aclManagerService.addUserToGroup(user[10*j+i], "group " + j, PortletRoles.USER);
            }
        }

        // Print out all users in each group
        for (j = 0; j < NUMGROUPS; j++) {
            //it = aclService.getUsersInGroup(PortletRoles.getUserRole(), "group " + j);
            System.err.println("users in group " + j);
            while (it.hasNext()) {
                System.err.println(it.next());
            }
        }

        // Give more roles

        for (j = 0; j < NUMGROUPS; j++) {
            for (i = 0; i < NUMPEOPLE; i=i+2) {
        //        aclManagerService.addRoleInGroup(user[10*j+i], "group " + j, PortletRoles);
            }
        }

        // Check roles
        int num = 0;
        for (j = 0; j < NUMGROUPS; j++) {
            for (i = 0; i < NUMPEOPLE; i++) {
     //           if (aclService.hasRoleInGroup(user[10*j+i], "group " + j, PortletRoles.ADMIN)) num++;
            }
        }
        System.err.println("number = " + num);

        //it = aclService.getAllGroupNames();
        while (it.hasNext()) {
            System.err.println(it.next());
        }

        //it = aclService.getAllUserNames();
        while (it.hasNext()) {
            System.err.println(it.next());
        }

    }


}
