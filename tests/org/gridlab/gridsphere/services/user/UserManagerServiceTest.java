/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user;

import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.ServiceTest;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.impl.*;

import org.gridlab.gridsphere.services.security.acl.AccessControlService;
import org.gridlab.gridsphere.services.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.PermissionDeniedException;
import org.gridlab.gridsphere.services.user.impl.AccountRequestImpl;
import org.gridlab.gridsphere.portletcontainer.descriptor.Role;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.AssertionFailedError;

import java.util.*;
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

    private SportletUser hans, franz, josef, root, nobody;
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
            aclService = (AccessControlService)factory.createPortletService(AccessControlService.class, null, true);
            aclManagerService = (AccessControlManagerService)factory.createPortletService(AccessControlManagerService.class, null, true);
            userManager = (UserManagerService)factory.createPortletService(UserManagerService.class, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize services: ", e);
        }

        // First we need to create some groups
        log.info("creating fake groups");
        groups = new Vector(GROUPS.length);
        try {
            for (i = 0; i < GROUPS.length; i++) {
                aclManagerService.createNewGroup(GROUPS[i]);
            }
        } catch (PortletServiceException e) {
            log.error("Unable to create new group: ", e);
            fail("Unable to create new group");
        }

        List portletGroups = null;
        try {
            portletGroups = aclService.getAllGroups();
        } catch (PortletServiceException e) {
            log.error("Unable to get all groups: ", e);
            fail("Unable to get all groups");
        }

        Iterator it = portletGroups.iterator();

        // get the groups, make sure we get it right, not rely on 1st, or 2nd dataset
        while (it.hasNext()) {
            PortletGroup sg = ((PortletGroup)it.next());
            if (sg.getName().equals("cactus")) {
                cactus = sg;
            }

            if (sg.getName().equals("portals")) {
                portals = sg;
            }

            if (sg.getName().equals("triana")) {
                triana = sg;
            }
        }

        // if one was not retrieved from the db it will fail
        assertEquals(cactus.getName(), "cactus");
        assertEquals(portals.getName(), "portals");
        assertEquals(triana.getName(), "triana");


        log.info("----------------------------");


        // we cannoy add users here since we don't have right now a root user

        root = (SportletUserImpl)userManager.loginUser("root");
        nobody = (SportletUserImpl)userManager.loginUser("nobody2");

        // make nobody admin of portals group

        log.info("RoleID: "+portals.getID());
/*
        try {
            aclManagerService.addRoleInGroup(nobody, portals);
        } catch (PortletServiceException e) {
            log.error("Error :"+e);
        }
  */
    }

    public static Test suite ( ) {
        return new TestSuite(UserManagerServiceTest.class);
    }




    public void testUserService() {
        AccountRequest req1, req2;
        // create mock accounts -- since createAccountRequest just returns new AccountRequest()
        // there's little need to test this method

        log.info("Jason");

        // Create James -- who wants to be in portals and triana
        req1 = userManager.createAccountRequest();
        req1.setFullName("Jason");
        req1.setUserID("jason");

        req1.addToGroup(portals, PortletRole.USER);
        req1.addToGroup(triana, PortletRole.USER);
        req1.addToGroup(cactus, PortletRole.USER);

        log.info("Michael");
        req2 = userManager.createAccountRequest();
        req2.setFullName("John D Nobody");
        req2.setUserID("nobody");
        req2.addToGroup(cactus, PortletRole.USER);

        log.info("Making user done");

        log.info("-- request creating done ");


        // submit requests as well
        try {
            userManager.submitAccountRequest(req1);
            userManager.submitAccountRequest(req2);
        } catch (PortletServiceException e) {
            log.error("Unable to submit account request: ", e);
            fail("Unable to submit account request");
        }


        log.info("Requests sumbitted");

        List accounts = userManager.getAccountRequests();
        assertEquals(accounts.size(), 2);

        AccountRequest james = (AccountRequest)accounts.get(0);
        AccountRequest john = (AccountRequest)accounts.get(1);

        assertEquals(james.getFullName(), req1.getFullName());
        assertEquals(john.getFullName(), req2.getFullName());


        log.info("two requests and names are ok");

        // Now lets see who can and can't approve deny requests
        // james wants to be in groups 1 and 2, the admins are franz and josef
        // hans is super user and can approve account but cannot approve group

        // make sure nobody cannot give james an account
        try {
            userManager.approveAccountRequest(nobody, james, null);
            fail("Should raise a PermissionDeniedException");
        } catch (PermissionDeniedException e) {}

        // Now root CAN approve a new account
        try {
            userManager.approveAccountRequest(root, james, null);
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }

        // root denies account for john

        try {
            userManager.denyAccountRequest(root, john, null);
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }

        // play around with groups
        log.info("start playing around with group approvals");

        SportletUserImpl userjames = (SportletUserImpl)userManager.loginUser(james.getUserID());

        log.info("User James:"+userjames.getFullName());

        // Lets make sure 'nobody' cannot approve/deny a group request for triana
        try {
            userManager.approveGroupRequest(nobody, userjames, triana, null);
            fail("Should raise a PermissionDeniedException");
        } catch (PermissionDeniedException e) {

        }

        // Now let 'nobody' approve james to portals group, because he is portal admin
        try {
            userManager.approveGroupRequest(nobody, userjames, portals, null);
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }


        // So now we should only have one user, james
        assertTrue(userManager.existsUser(james.getUserID()));
        assertTrue(!userManager.existsUser(john.getUserID()));

        List allUsers = userManager.getAllUsers();
        assertNotNull(allUsers);
        assertEquals(allUsers.size(), 3);

//        User jamesUser = (User)allUsers.get(0);
//        assertEquals(jamesUser.getUserID(), james.getUserID());

        // See if james can edit his account -- needs super approval
        String newname = "James Cool Guy";
        AccountRequest newreq = userManager.changeAccountRequest(userjames);
        newreq.setFullName(newname);

        try {
            userManager.submitAccountRequest(newreq);
            userManager.approveAccountRequest(root, newreq, null);
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        } catch (PortletServiceException e) {
            log.error("Unable to submit account request: ", e);
            fail("Unable to submit account request");
        }

        // test login
        User user = userManager.loginUser(userjames.getUserID());
        List activeUsers = userManager.getActiveUsers();
        assertEquals(activeUsers.size(), 1);
        User auser = (User)activeUsers.get(0);
        assertEquals(auser.getUserID(), userjames.getUserID());

        // test logoff
        userManager.logoffUser(auser);
        activeUsers = userManager.getActiveUsers();
        assertEquals(activeUsers.size(), 0);

        // test remove user
        try {
            userManager.removeUser(franz, userjames.getUserID());
            fail("Should raise a PermissionDeniedException");
        } catch (PermissionDeniedException e) {}

        try {
            userManager.removeUser(hans, userjames.getUserID());
        } catch (PermissionDeniedException e) {
            fail("Should NOT raise a PermissionDeniedException");
        }

        allUsers = userManager.getAllUsers();
        assertNotNull(allUsers);
        assertEquals(allUsers.size(), 0);
    }
}
