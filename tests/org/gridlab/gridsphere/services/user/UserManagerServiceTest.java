/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
//import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.ServiceTest;
import org.gridlab.gridsphere.services.security.AuthenticationException;
import org.gridlab.gridsphere.services.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;

import java.util.List;
import java.util.Vector;

public class UserManagerServiceTest extends ServiceTest {

    private static PortletLog log = SportletLog.getInstance(UserManagerServiceTest.class);
    private static AccessControlService aclService = null;
    private static AccessControlManagerService aclManagerService = null;
    private static UserManagerService userManager = null;
    private PersistenceManagerRdbms pm = null;
    private PortletGroup portal, triana, cactus;
    private User superuser;
    private int numUsers = 0;
    private int numGroups = 0;

    public UserManagerServiceTest(String name) {
        super(name);
    }

    public static void main(String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(UserManagerServiceTest.class);
    }

    protected void setUp() {
        super.setUp();
        log.info(" =====================================  setup");
        // Create both services using mock ServletConfig
        try {
            aclService = (AccessControlService) factory.createPortletService(AccessControlService.class, null, true);
            aclManagerService = (AccessControlManagerService) factory.createPortletService(AccessControlManagerService.class, null, true);
            userManager = (UserManagerService) factory.createPortletService(UserManagerService.class, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize services: ", e);
        }
        pm = PersistenceManagerRdbms.getInstance();
        System.out.println("Clean up the tables...");
        /* SHOULD NEVER EVER EVER DO THIS!!! (MPR 11/01/03)
        try {
            pm.deleteList("select u from org.gridlab.gridsphere.services.user.impl.AccountRequestImpl u");
            pm.deleteList("select u from org.gridlab.gridsphere.portlet.impl.SportletUserImpl u");
            pm.deleteList("select u from org.gridlab.gridsphere.portlet.impl.SportletGroup u");
            pm.deleteList("select u from org.gridlab.gridsphere.services.security.acl.impl.UserACL u");
            pm.deleteList("select u from org.gridlab.gridsphere.portlet.impl.SportletData u");
        } catch (PersistenceManagerException e) {
            log.error("Exception " + e);
        }
        */
        //setupSuperUser();
        setupGroups();
        setupUsers();
    }

    protected void tearDown() {

        log.info(" ===================================== tear down");
        /* SHOULD NEVER EVER EVER DO THIS!!! (MPR 11/01/03)
        try {
            pm.deleteList("select u from org.gridlab.gridsphere.services.user.impl.AccountRequestImpl u");
            pm.deleteList("select u from org.gridlab.gridsphere.portlet.impl.SportletUserImpl u");
            pm.deleteList("select u from org.gridlab.gridsphere.portlet.impl.SportletGroup u");
            pm.deleteList("select u from org.gridlab.gridsphere.services.security.acl.impl.UserACL u");
            pm.deleteList("select u from org.gridlab.gridsphere.portlet.impl.SportletData u");
        } catch (PersistenceManagerException e) {
            log.error("Exception " + e);
        }
        */
        teardownUsers();
        teardownGroups();
    }

    public void setupGroups() {
        log.info("- setup groups");
        try {
            aclManagerService.createNewGroup("portal");
            aclManagerService.createNewGroup("triana");
            aclManagerService.createNewGroup("cactus");
        } catch (PortletServiceException e) {
            log.error("Exception " + e);
            fail("Could not create groups ");
        }
        List groups = null;
        try {
            groups = aclService.getAllGroups();
        } catch (PortletServiceException e) {
            fail("shouldnot fail here");
            log.error("Exception " + e);
        }

        for (int i = 0; i < groups.size(); i++) {
            if (((PortletGroup) groups.get(i)).getName().equals("cactus")) {
                cactus = (PortletGroup) groups.get(i);
            }
            if (((PortletGroup) groups.get(i)).getName().equals("triana")) {
                triana = (PortletGroup) groups.get(i);
            }
            if (((PortletGroup) groups.get(i)).getName().equals("portal")) {
                portal = (PortletGroup) groups.get(i);
            }
        }
    }

    public void teardownGroups() {
        log.info("- setup groups");
        try {
            aclManagerService.removeGroup("portal");
            aclManagerService.removeGroup("triana");
            aclManagerService.removeGroup("cactus");
        } catch (PortletServiceException e) {
            log.error("Exception " + e);
            fail("Could not create groups ");
        }
    }

    public void setupUsers() {
        log.info("- setup users");
        List users = userManager.getAllUsers();
        int numUsers = users.size();

        // Save reference super super
        superuser = getSuperUser();
        // jason, michael should be users
        // oliver was denied

        AccountRequest req1 = userManager.createAccountRequest();
        req1.setUserID("jason");
        req1.setGivenName("Jason");
        req1.addToGroup(portal, PortletRole.ADMIN);
        req1.addToGroup(cactus, PortletRole.USER);

        AccountRequest req2 = userManager.createAccountRequest();
        req2.setUserID("michael");
        req2.setGivenName("Michael");
        req2.addToGroup(portal, PortletRole.USER);
        req2.addToGroup(triana, PortletRole.USER);

        AccountRequest req3 = userManager.createAccountRequest();
        req3.setUserID("oliver");
        req3.setGivenName("Oliver");
        Vector userdns2 = new Vector();
        userdns2.add("dns1");
        userdns2.add("dns2");
        req3.setMyproxyUserDN(userdns2);

        try {
            userManager.submitAccountRequest(req1);
            userManager.submitAccountRequest(req2);
            userManager.submitAccountRequest(req3);
            userManager.approveAccountRequest(superuser, req1, null);
            userManager.approveAccountRequest(superuser, req2, null);
        } catch (PortletServiceException e) {
            log.error("Exception " + e);
            fail("failed to generate AccountRequests");
        } catch (PermissionDeniedException e) {
            log.error("Exception " + e);
            fail("No permissions!");
        }
    }

    public void teardownUsers() {
        log.info("- setup groups");
        try {
            userManager.removeUser(getSuperUser(), "jason");
            userManager.removeUser(getSuperUser(), "michael");
            userManager.removeUser(getSuperUser(), "oliver");
            userManager.removeUser(getSuperUser(), "ian");
        } catch (PermissionDeniedException e) {
            log.error("Exception " + e);
            fail("Could not delete users ");
        }
    }

    public User getSuperUser() {
        User superUser = null;
        //return userManager.getUser("root");
        try {
            superUser = userManager.login("root", "");
        } catch (AuthenticationException e) {
            String msg = "Unable to login as root!";
            log.error(msg, e);
            fail(msg);
        }
        return superUser;
    }

    // === tests

    public void testSportletData() {
        log.info("+ testSportletData");
        User jason = userManager.getUser(getSuperUser(), "jason");
        PortletData pd = (PortletData) userManager.getPortletData(jason, "1");
        pd.setAttribute("test", "result");
        try {
            pd.store();
        } catch (PersistenceManagerException e) {

        }

        User jason2 = userManager.getUser(getSuperUser(), "jason");
        PortletData pd2 = userManager.getPortletData(jason2, "1");
        String result = pd2.getAttribute("test");
        assertEquals("result", result);
    }

    /* Good old Sportlet User Attributes -- we need to work on this again -JN
    public void testSportletUserAttributes() {
        log.info("+ testSportletuserAttributes");
        User jason = userManager.getUser(getSuperUser(), "jason");
        SportletUser su = (SportletUser) jason;
        su.setAttribute("test", "result");
        try {
            userManager.saveUser(getSuperUser(), su);
        } catch (PermissionDeniedException e) {
            log.error("Unable to save changes to user", e);
        }

        User jason2 = userManager.getUser(getSuperUser(), "jason");
        String Attribute = (String) jason2.getAttribute("test");
        System.out.println("Attr:" + Attribute);

        assertEquals("result", Attribute);
    }
    */

    public void testRemoveUser() {
        log.info("+ testRemoveUser");
        // Should fail to remove michael
        User jason = userManager.getUser(getSuperUser(), "jason");
        try {
            userManager.removeUser(jason, "michael");
            fail("Should fail here!");
        } catch (PermissionDeniedException e) {
        }

        jason = userManager.getUser(getSuperUser(), "jason");
        if (jason == null) {
            fail("Jason should not be delete");
        }

        // Should succeed to remove michael
        try {
            userManager.removeUser(getSuperUser(), "michael");
        } catch (PermissionDeniedException e) {
            fail("should NOT fail here");
        }

        assertEquals(false, userManager.userExists("michael"));
    }

    public void testDenyGroupRequest() {
        log.info("+ testdenygrouprequest");
        User jason = userManager.getUser(getSuperUser(), "jason");
        try {
            userManager.denyGroupRequest(superuser, jason, portal, null);
        } catch (PermissionDeniedException e) {
            log.error("Exception " + e);
            fail("Should NOT fail");
        }

        boolean isInGroup = false;
        try {
            isInGroup = aclService.isUserInGroup(jason, portal);
        } catch (PortletServiceException e) {
            log.error("Exception " + e);
            fail("should not fail here");
        }

        assertTrue(!isInGroup);
    }

    public void testApproveGroup() {
        log.info("+ testapprovegroup");
        User jason = userManager.getUser(getSuperUser(), "jason");
        try {
            userManager.approveGroupRequest(superuser, jason, portal, null);
        } catch (PermissionDeniedException e) {
            log.error("Exception " + e);
            fail("Should NOT fail");
        }

        User michael = userManager.getUser(getSuperUser(), "michael");

        try {
            userManager.approveGroupRequest(jason, michael, triana, null);
            fail("This should fail!");
        } catch (PermissionDeniedException e) {
        }

        try {
            userManager.approveGroupRequest(jason, michael, portal, null);
        } catch (PermissionDeniedException e) {
            log.error("Exception " + e);
            fail("This should not fail");
        }

        List result = null;

        try {
            result = aclService.getGroups(michael);
        } catch (PortletServiceException e) {
            log.error("Exception " + e);
            fail("should not fail");
        }

        assertEquals(1, result.size());
    }

    public void testRemoveGroup() {
        log.info("+ testremovegroup");
        try {
            aclManagerService.removeGroup(portal);
        } catch (PortletServiceException e) {
            log.error("Exception " + e);
            fail("should not fail");
        }
        PortletGroup group = aclManagerService.getGroup("portal");
        assertEquals(null, group);
    }

    //@todo test which tries to create a new user with existing login
    //@todo all that useracl stuff

    public void testModifyExistingUser() {
        log.info("+ testmodifyExistingUser");
        User jason = userManager.getUser(getSuperUser(), "jason");
        String jasonid = jason.getID();
        AccountRequest req = userManager.changeAccountRequest(jason);
        req.setFamilyName("Novotny");
        try {
            userManager.submitAccountRequest(req);
        } catch (PortletServiceException e) {
            log.error("Exception " + e);
            fail("Should not fail at submit accountrequest");
        }

        User root = getSuperUser();
        try {
            userManager.approveAccountRequest(root, req, null);
        } catch (PermissionDeniedException e) {
            log.error("Exception " + e);
            fail("Should not fail at approving accountrequest");
        }

        User newjason = userManager.getUser(root, "jason");
        assertEquals("Novotny", newjason.getFamilyName());
        assertEquals(jasonid, newjason.getID());

    }

    public void testGroups() {
        log.info("+ testGroups");
        List result = null;
        try {
            result = aclService.getAllGroups();
        } catch (PortletServiceException e) {
            log.error("Exception " + e);
            fail("could not get Groups");
        }
        assertEquals(3, result.size());

        String groupid = ((PortletGroup) result.get(0)).getID();
        String groupname = ((PortletGroup) result.get(0)).getName();

        try {
            aclManagerService.renameGroup((PortletGroup) result.get(0), "globus");
        } catch (PortletServiceException e) {
            log.error("Exception " + e);
            fail("grouprenaming failed");
        }
        // get them again..yeah codeshareing
        try {
            result = aclService.getAllGroups();
        } catch (PortletServiceException e) {
            log.error("Exception " + e);
            fail("could not get Groups");
        }
        assertEquals(3, result.size());

        for (int i = 0; i < result.size(); i++) {
            if (((PortletGroup) result.get(i)).getID().equals(groupid)) {
                assertTrue(!((PortletGroup) result.get(i)).getName().equals(groupname));
            }
        }
    }

    public void testApproveAccountRequest() {
        log.info("+ testAccountRequests");

        // Get initial list of requests
        List requests = userManager.getAccountRequests();
        int numRequests = requests.size();

        // Create new request
        AccountRequest ianRequest = userManager.createAccountRequest();
        ianRequest.setUserID("ian");
        ianRequest.setGivenName("Ian");
        Vector userdns = new Vector();
        userdns.add("dns1");
        userdns.add("dns2");
        ianRequest.setMyproxyUserDN(userdns);
        try {
            userManager.submitAccountRequest(ianRequest);
        } catch (PortletServiceException e) {
            String msg = "Failed to submit account request";
            log.error(msg, e);
            fail(msg);
        }
        // Get request id
        String ianId = ianRequest.getID();

        // Check number of account requests is +1
        requests = userManager.getAccountRequests();
        assertEquals(requests.size(), numRequests + 1);

        // Check that ian exists as an account request
        ianRequest = userManager.getAccountRequest(ianId);
        if (ianRequest == null) {
            fail("Failed to retrieve account request");
        }

        // Approve the request with jason
        User jason = userManager.getUser(getSuperUser(), "jason");
        try {
            userManager.approveAccountRequest(jason, ianRequest, null);
            String msg = "Should have failed to approve request";
            fail(msg);
        } catch (PermissionDeniedException e) {
        }

        // Approve the request with root
        try {
            userManager.approveAccountRequest(getSuperUser(), ianRequest, null);
        } catch (PermissionDeniedException e) {
            String msg = "Failed to approve request with super user";
            log.error(msg, e);
            fail(msg);
        }

        // Check number of account requests is back to previous
        requests = userManager.getAccountRequests();
        assertEquals(requests.size(), numRequests);

        // Check that account exists
        assertEquals(true, userManager.userExists("ian"));
    }

    public void testDenyAccountRequest() {
        log.info("+ testAccountRequests");

        // Get initial list of requests
        List requests = userManager.getAccountRequests();
        int numRequests = requests.size();

        // Create account request
        AccountRequest ianRequest = userManager.createAccountRequest();
        ianRequest.setUserID("ian");
        ianRequest.setGivenName("Ian");
        Vector userdns = new Vector();
        userdns.add("dns1");
        userdns.add("dns2");
        ianRequest.setMyproxyUserDN(userdns);
        try {
            userManager.submitAccountRequest(ianRequest);
        } catch (PortletServiceException e) {
            String msg = "Failed to submit account request";
            log.error(msg, e);
            fail(msg);
        }

        // Get request id
        String ianId = ianRequest.getID();

        // Check number of account requests is +1
        requests = userManager.getAccountRequests();
        assertEquals(requests.size(), numRequests + 1);

        // Check that ian exists as an account request
        ianRequest = userManager.getAccountRequest(ianId);
        if (ianRequest == null) {
            fail("Failed to retrieve account request");
        }

        // Deny the request with jason
        User jason = userManager.getUser(getSuperUser(), "jason");
        try {
            userManager.denyAccountRequest(jason, ianRequest, null);
            String msg = "Should have failed to deny request with jason";
            fail(msg);
        } catch (PermissionDeniedException e) {
        }

        // Deny the request with root
        try {
            userManager.denyAccountRequest(getSuperUser(), ianRequest, null);
        } catch (PermissionDeniedException e) {
            String msg = "Failed to deny request with super user";
            log.error(msg, e);
            fail(msg);
        }

        // Check number of account requests is back to previous
        requests = userManager.getAccountRequests();
        assertEquals(requests.size(), numRequests);

        // Check that ian does not exist
        assertEquals(false, userManager.userExists("ian"));
    }
}
