/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user.test;

import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.test.ServiceTest;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletGroupImpl;

import org.gridlab.gridsphere.services.security.acl.AccessControlService;
import org.gridlab.gridsphere.services.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.PermissionDeniedException;
import org.gridlab.gridsphere.services.user.impl.AccountRequestImpl;

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

public class UserManagerServiceTest extends ServiceTest {

    private static PortletLog log = SportletLog.getInstance(UserManagerServiceTest.class);
    private static AccessControlService aclService = null;
    private static AccessControlManagerService aclManagerService = null;
    private static UserManagerService userManager = null;

    public static final String[] GROUPS = {"cactus", "portals", "triana"};
    public static final String[] USERS = {"hans", "franz", "josef"};

    private SportletUser hans, franz, josef;
    private PortletGroup cactus, portals, triana;
    private List groups;


    public UserManagerServiceTest(String name) {
        super(name);
    }

    public static void main (String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }

    protected void setUp() {
        super.setUp();
        int i;

        // Create both services using mock ServletConfig
        try {
            aclService = (AccessControlService)factory.createPortletService(AccessControlService.class, props, null, true);
            aclManagerService = (AccessControlManagerService)factory.createPortletService(AccessControlManagerService.class, props, null, true);
            userManager = (UserManagerService)factory.createPortletService(UserManagerService.class, props, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize services: ", e);
        }

        // First we need to create some groups
        log.info("creating fake groups");
        groups = new Vector(GROUPS.length);
        for (i = 0; i < GROUPS.length; i++) {
            groups.set(i, GROUPS[i]);
            aclManagerService.createNewGroup(GROUPS[i]);
        }

        List portletGroups = aclService.getAllGroups();
        cactus = (PortletGroup)portletGroups.get(0);
        portals = (PortletGroup)portletGroups.get(1);
        triana = (PortletGroup)portletGroups.get(2);

        // Make sure we have proper ordering
        assertEquals(cactus.getName(), GROUPS[0]);
        assertEquals(portals.getName(), GROUPS[1]);
        assertEquals(triana.getName(), GROUPS[2]);

        // Lets make user 0 the super user  and admin of group 0
        hans = new SportletUserImpl();
        hans.setGivenName(USERS[0]);
        hans.setUserID(USERS[0]);
        aclManagerService.addUserToSuperRole(hans);
        aclManagerService.addUserToGroup(hans, cactus, PortletRole.getAdminRole());

        // Lets make user 1 admin of group 1 and 2
        franz = new SportletUserImpl();
        franz.setGivenName(USERS[1]);
        franz.setUserID(USERS[1]);
        aclManagerService.addUserToGroup(franz, portals, PortletRole.getAdminRole());
        aclManagerService.addUserToGroup(franz, triana, PortletRole.getAdminRole());

        // Lets make user 2 admin of group 2
        josef = new SportletUserImpl();
        josef.setGivenName(USERS[2]);
        josef.setUserID(USERS[2]);
        aclManagerService.addUserToGroup(josef, triana, PortletRole.getAdminRole());

    }

    public static Test suite ( ) {
        return new TestSuite(UserManagerServiceTest.class);
    }


    public void testUserService() {
        AccountRequest req1, req2;
        // create mock accounts -- since createAccountRequest just returns new AccountRequest()
        // there's little need to test this method


        // Create James -- who wants to be in portals and triana
        List req1groups = new Vector();
        req1groups.add(portals);
        req1groups.add(triana);

        req1 = userManager.createAccountRequest();
        req1.setFullName("James A Somebody");
        req1.setUserID("somebody");
        req1.setDesiredGroups(req1groups);

        // Create John -- who wants to be in cactus
        List req2groups = new Vector();
        req2groups.add(cactus);

        req2 = userManager.createAccountRequest();
        req2.setFullName("John D Nobody");
        req2.setUserID("nobody");
        req2.setDesiredGroups(req2groups);

        // submit requests as well
        userManager.submitAccountRequest(req1);
        userManager.submitAccountRequest(req2);

        List accounts = userManager.getAccountRequests();
        assertEquals(accounts.size(), 2);

        AccountRequest james = (AccountRequest)accounts.get(0);
        AccountRequest john = (AccountRequest)accounts.get(1);

        assertEquals(james.getFullName(), req1.getFullName());
        assertEquals(john.getFullName(), req2.getFullName());

        // Now lets see who can and can't approve deny requests
        // james wants to be in groups 1 and 2, the admins are franz and josef
        // hans is super user and can approve account but cannot approve group

        // make sure franz cannot give james an account
        try {
            userManager.approveAccountRequest(franz, james, null);
            fail("Should raise a PermissionDeniedException");
        } catch (PermissionDeniedException e) {}

        // Now hans CAN approve a new account
        try {
            userManager.approveAccountRequest(hans, james, null);
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }

        // Lets make sure josef cannot approve/deny a group request for triana
        try {
            userManager.approveGroupRequest(josef, james, triana, null);
            fail("Should raise a PermissionDeniedException");
        } catch (PermissionDeniedException e) {}

        // Now let franz approve portals group
        try {
            userManager.approveGroupRequest(franz, james, portals, null);
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }

        // Now let josef deny triana group
        try {
            userManager.approveGroupRequest(josef, james, triana, null);
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }

        // Now john is out of luck-- lets say hans is feeling schizophrenic
        // hans approves the group request, yet denies the account

        try {
            userManager.approveGroupRequest(hans, john, cactus, null);
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }

        try {
            userManager.denyAccountRequest(hans, john, null);
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }

        // So now we should only have one user, james
        assertTrue(userManager.existsUser(james.getUserID()));
        assertTrue(!userManager.existsUser(john.getUserID()));

        List allUsers = userManager.getAllUsers();
        assertNotNull(allUsers);
        assertEquals(allUsers.size(), 1);

        User jamesUser = (User)allUsers.get(0);
        assertEquals(jamesUser.getUserID(), james.getUserID());

        // See if james can edit his account -- needs super approval
        String newname = "James Cool Guy";
        AccountRequest newreq = userManager.changeAccountRequest(jamesUser);
        newreq.setFullName(newname);
        userManager.submitAccountRequest(newreq);
        try {
            userManager.approveAccountRequest(hans, newreq, null);
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }

        // test login
        User user = userManager.loginUser(jamesUser.getUserID());
        List activeUsers = userManager.getActiveUsers();
        assertEquals(activeUsers.size(), 1);
        User auser = (User)activeUsers.get(0);
        assertEquals(auser.getUserID(), jamesUser.getUserID());

        // test logoff
        userManager.logoffUser(auser);
        activeUsers = userManager.getActiveUsers();
        assertEquals(activeUsers.size(), 0);

        // test remove user
        try {
            userManager.removeUser(franz, jamesUser.getUserID());
            fail("Should raise a PermissionDeniedException");
        } catch (PermissionDeniedException e) {}

        try {
            userManager.removeUser(hans, jamesUser.getUserID());
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }

        allUsers = userManager.getAllUsers();
        assertNotNull(allUsers);
        assertEquals(allUsers.size(), 0);
    }
}
