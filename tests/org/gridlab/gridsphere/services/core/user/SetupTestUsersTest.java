/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;

import java.util.List;

public class SetupTestUsersTest extends SetupTestGroupsTest {

    private UserManagerService rootUserService = null;
    private AccountRequest jasonRequest = null;
    private AccountRequest michaelRequest = null;
    private AccountRequest oliverRequest = null;

    private User jason = null;
    private User michael = null;
    private User oliver = null;

    public SetupTestUsersTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(SetupTestUsersTest.class);
    }

    protected void setUp() {
        super.setUp();
        super.testSetupGroups();
        // Create a root user services using mock ServletConfig
        try {
            rootUserService = (UserManagerService) factory.createUserPortletService(UserManagerService.class, rootUser, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize services: ", e);
        }
    }

    public void testSetupUsers() {
        // jason, michael should be users
        // oliver will be denied

        AccountRequest jasonRequest = rootUserService.createAccountRequest();
        jasonRequest.setUserID("jason");
        jasonRequest.setGivenName("Jason");
        jasonRequest.setPasswordValue("");
        jasonRequest.setPasswordValidation(false);
        jasonRequest.addToGroup(portal, PortletRole.ADMIN);
        jasonRequest.addToGroup(cactus, PortletRole.USER);

        AccountRequest michaelRequest = rootUserService.createAccountRequest();
        michaelRequest.setUserID("michael");
        michaelRequest.setGivenName("Michael");
        michaelRequest.setPasswordValue("");
        michaelRequest.setPasswordValidation(false);
        michaelRequest.addToGroup(portal, PortletRole.USER);
        michaelRequest.addToGroup(triana, PortletRole.USER);

        AccountRequest oliverRequest = rootUserService.createAccountRequest();
        oliverRequest.setUserID("oliver");
        oliverRequest.setGivenName("Oliver");
        oliverRequest.setPasswordValue("");
        oliverRequest.setPasswordValidation(false);
        try {
            rootUserService.submitAccountRequest(jasonRequest);
            rootUserService.submitAccountRequest(michaelRequest);
            rootUserService.submitAccountRequest(oliverRequest);
            jason = rootUserService.approveAccountRequest(jasonRequest);
            michael = rootUserService.approveAccountRequest(michaelRequest);
            oliver = rootUserService.approveAccountRequest(oliverRequest);
        } catch (PortletServiceException e) {
            String msg = "Failed to setup users";
            log.error(msg, e);
            fail(msg);
        }

        // test accessor methods
        List users = rootUserService.getUsers();
        assertEquals(true, users.contains(jason));
        assertEquals(true, users.contains(michael));
        assertEquals(true, users.contains(oliver));
    }

    public void testAddRemoveUsers() {
        AccountRequest franzReq = rootUserService.createAccountRequest();
        franzReq.setUserName("franz");
        franzReq.setUserID("franz");
        franzReq.setGivenName("Franz");
        franzReq.setPasswordValue("");
        franzReq.setPasswordValidation(false);
        try {
            rootUserService.submitAccountRequest(franzReq);
        } catch (InvalidAccountRequestException e) {
            fail("Unable to submit account request" + e.getMessage());
        }
        List reqs = rootUserService.getAccountRequests();
        boolean isthere = reqs.contains(franzReq);
        assertEquals(true, isthere);
        User franz = rootUserService.approveAccountRequest(franzReq);
        User newfranz = rootUserService.getUserByUserName("franz");
        assertEquals(franz, newfranz);
    }

    public void teardownUsers() {
        rootUserService.deleteAccount(jason);
        rootUserService.deleteAccount(michael);
        rootUserService.deleteAccount(oliver);
    }

    protected void tearDown() {
        teardownUsers();
        super.tearDown();
    }

}
