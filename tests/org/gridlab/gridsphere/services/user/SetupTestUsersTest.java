/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user;

import org.gridlab.gridsphere.portlet.service.spi.ServiceTest;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.services.security.AuthenticationException;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.List;

public class SetupTestUsersTest extends SetupTestGroupsTest {

    private static UserManagerService userService = null;
    private AccountRequest jasonRequest = null;
    private AccountRequest michaelRequest = null;
    private AccountRequest oliverRequest = null;
    private AccountRequest ianRequest = null;

    private User jason = null;
    private User michael = null;
    private User oliver = null;
    private User ian = null;

    public SetupTestUsersTest(String name) {
        super(name);
    }

    public static void main(String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(SetupTestUsersTest.class);
    }

    protected void setUp() {
        super.setUp();
        log.info(" =====================================  setup");
        // Create a root user services using mock ServletConfig
        try {
            userService = (UserManagerService) factory.createPortletUserService(UserManagerService.class, rootUser, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize services: ", e);
        }
        setupUsers();
    }

    public void setupUsers() {
        log.info("- setup users");
        List users = userService.getUsers();
        int numUsers = users.size();

        // jason, michael should be users
        // oliver will be denied

        AccountRequest jasonRequest = userService.createAccountRequest();
        jasonRequest.setUserID("jason");
        jasonRequest.setGivenName("Jason");
        jasonRequest.setPasswordValue("");
        jasonRequest.setPasswordValidation(false);
        jasonRequest.addToGroup(portal, PortletRole.ADMIN);
        jasonRequest.addToGroup(cactus, PortletRole.USER);

        AccountRequest michaelRequest = userService.createAccountRequest();
        michaelRequest.setUserID("michael");
        michaelRequest.setGivenName("Michael");
        michaelRequest.setPasswordValue("");
        michaelRequest.setPasswordValidation(false);
        michaelRequest.addToGroup(portal, PortletRole.USER);
        michaelRequest.addToGroup(triana, PortletRole.USER);

        AccountRequest oliverRequest = userService.createAccountRequest();
        oliverRequest.setUserID("oliver");
        oliverRequest.setGivenName("Oliver");
        oliverRequest.setPasswordValue("");
        oliverRequest.setPasswordValidation(false);

        try {
            userService.submitAccountRequest(jasonRequest);
            userService.submitAccountRequest(michaelRequest);
            userService.submitAccountRequest(oliverRequest);
            jason = userService.approveAccountRequest(jasonRequest);
            michael = userService.approveAccountRequest(michaelRequest);
        } catch (PortletServiceException e) {
            String msg = "Failed to setup users";
            log.error(msg, e);
            fail(msg);
        }
    }

    public void teardownUsers() {
        log.info("- setup groups");
        userService.deleteAccount(jason);
        userService.deleteAccount(michael);
        userService.deleteAccount(oliver);
        userService.deleteAccount(ian);
    }

    protected void tearDown() {
        teardownUsers();
        super.tearDown();
    }

}
