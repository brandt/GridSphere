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
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.ServiceTest;
import org.gridlab.gridsphere.services.security.AuthenticationException;
import org.gridlab.gridsphere.services.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.portletcontainer.PortletDataManager;
import org.gridlab.gridsphere.portletcontainer.impl.SportletDataManager;

import java.util.List;
import java.util.Vector;

public class UserManagerServiceTest extends ServiceTest {

    private static PortletLog log = SportletLog.getInstance(UserManagerServiceTest.class);
    private static LoginService loginService = null;
    private static UserManagerService userManagerService = null;
    private static AccessControlManagerService aclManagerService = null;
    private PersistenceManagerRdbms pm = null;
    private PortletGroup portalGroup = null;
    private PortletGroup trianaGroup = null;
    private PortletGroup cactusGroup = null;
    private User rootUser = null;
    private AccountRequest jasonRequest = null;
    private User jasonUser = null;
    private AccountRequest michaelRequest = null;
    private User michaelUser = null;
    private AccountRequest oliverRequest = null;
    private User oliverUser = null;
    private AccountRequest ianRequest = null;
    private User ianUser = null;
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
            loginService = (LoginService) factory.createPortletService(LoginService.class, null, true);
            userManagerService = (UserManagerService) factory.createPortletService(UserManagerService.class, null, true);
            aclManagerService = (AccessControlManagerService) factory.createPortletService(AccessControlManagerService.class, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize services: ", e);
        }
        pm = PersistenceManagerRdbms.getInstance();
        System.out.println("Clean up the tables...");
        loginRoot();
        setupGroups();
        setupUsers();
    }

    public void loginRoot() {
        try {
            rootUser = loginService.login("root", "");
        } catch (AuthenticationException e) {
            String msg = "Unable to login as root";
            log.error(msg, e);
            fail(msg);
        }
    }

    public void setupGroups() {
        log.info("- setup groups");
        portalGroup = aclManagerService.createGroup("portal");
        trianaGroup = aclManagerService.createGroup("triana");
        cactusGroup = aclManagerService.createGroup("cactus");
    }

    public void setupUsers() {
        log.info("- setup users");
        List users = userManagerService.getUsers();
        int numUsers = users.size();

        // jason, michael should be users
        // oliver will be denied

        AccountRequest jasonRequest = userManagerService.createAccountRequest();
        jasonRequest.setUserID("jason");
        jasonRequest.setGivenName("Jason");
        jasonRequest.setPasswordValue("");
        jasonRequest.setPasswordValidation(false);
        jasonRequest.addToGroup(portalGroup, PortletRole.ADMIN);
        jasonRequest.addToGroup(cactusGroup, PortletRole.USER);

        AccountRequest michaelRequest = userManagerService.createAccountRequest();
        michaelRequest.setUserID("michael");
        michaelRequest.setGivenName("Michael");
        michaelRequest.setPasswordValue("");
        michaelRequest.setPasswordValidation(false);
        michaelRequest.addToGroup(portalGroup, PortletRole.USER);
        michaelRequest.addToGroup(trianaGroup, PortletRole.USER);

        AccountRequest oliverRequest = userManagerService.createAccountRequest();
        oliverRequest.setUserID("oliver");
        oliverRequest.setGivenName("Oliver");
        oliverRequest.setPasswordValue("");
        oliverRequest.setPasswordValidation(false);

        try {
            userManagerService.submitAccountRequest(jasonRequest);
            userManagerService.submitAccountRequest(michaelRequest);
            userManagerService.submitAccountRequest(oliverRequest);
            jasonUser = userManagerService.approveAccountRequest(jasonRequest);
            michaelUser = userManagerService.approveAccountRequest(michaelRequest);
        } catch (PortletServiceException e) {
            String msg = "Failed to setup users";
            log.error(msg, e);
            fail(msg);
        }
    }

    protected void tearDown() {
        log.info(" ===================================== tear down");
        teardownUsers();
        teardownGroups();
    }

    public void teardownGroups() {
        log.info("- setup groups");
        aclManagerService.deleteGroup(portalGroup);
        aclManagerService.deleteGroup(trianaGroup);
        aclManagerService.deleteGroup(cactusGroup);
    }

    public void teardownUsers() {
        log.info("- setup groups");
        try {
            userManagerService.deleteAccount(jasonUser);
            userManagerService.deleteAccount(michaelUser);
            userManagerService.deleteAccount(oliverUser);
            userManagerService.deleteAccount(ianUser);
        } catch (PermissionDeniedException e) {
            log.error("Exception " + e);
            fail("Could not delete users ");
        }
    }

    public User getSuperUser() {
        return rootUser;
    }

    // === tests

    public void testDenyAccessRequest() {
        userManagerService.denyAccountRequest(oliverRequest);
    }
}
