/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Dec 25, 2002
 * Time: 4:18:13 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.credential;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.ServiceTest;
import org.gridlab.gridsphere.services.core.security.password.AuthenticationException;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.security.credential.*;

import java.util.List;
import java.util.Vector;

public class CredentialManagerServiceTest extends ServiceTest {

    // Service variables
    private static PortletLog _log = SportletLog.getInstance(CredentialManagerServiceTest.class);
    private PersistenceManagerRdbms _pm = PersistenceManagerRdbms.getInstance();
    private LoginService loginService = null;
    private UserManagerService userManagerService = null;
    private CredentialManagerService credentialManagerService = null;
    // User variables
    private User rootUser;
    private User testUser;
    // Credential permission variables
    private String globusSubjects = "/O=Grid/O=Globus/*";
    private String globusDescription = "Permission to use Globus credentials";
    private String ncsaSubjects = "/O=Grid/O=NCSA/*";
    private String ncsaDescription = "Permission to use NCSA credentials";
    private String allSubjects = "/*";
    private String allDescription = "Permission to use any credentials";
    // Credential mapping variables
    private String guestSubject = "/O=Grid/O=Globus/OU=Guest User";
    private String guestTag = "guest";
    private String guestDescription = "My Globus credentials";
    private String guestHost1 = "portal.gridsphere.org";
    private String guestHost2 = "services.gridsphere.org";

    public CredentialManagerServiceTest(String name) {
        super(name);
    }

    public static void main(String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(CredentialManagerServiceTest.class);
    }

    protected void setUp() {
        super.setUp();
        _log.info(" =====================================  setup");
        // Create both services using mock ServletConfig
        try {
            this.loginService
                    = (LoginService) factory.createPortletService(LoginService.class, null, true);
            this.userManagerService
                    = (UserManagerService) factory.createPortletService(UserManagerService.class, null, true);
            this.credentialManagerService
                    = (CredentialManagerService) factory.createPortletService(CredentialManagerService.class, null, true);
        } catch (Exception e) {
            _log.error("Unable to initialize services: ", e);
        }
        createUsers();
    }

    protected void tearDown() {
        _log.info(" ===================================== tear down");
        deleteUsers();
    }

    private User getSuperUser() {
        User superUser = null;
        //return userManager.getUser("root");
        try {
            superUser = loginService.login("root", "");
        } catch (AuthenticationException e) {
            String msg = "Unable to login as root!";
            log.error(msg, e);
            fail(msg);
        }
        return superUser;
    }

    private void createUsers() {
        this.rootUser = getSuperUser();
        this.testUser = createTestUser(this.rootUser);
    }

    private User createTestUser(User rootUser) {
        _log.info("- create test user");
        User testUser = null;
        AccountRequest req1 = userManagerService.createAccountRequest();
        req1.setUserID("test");
        req1.setGivenName("Test");
        req1.setPasswordValidation(false);
        try {
            this.userManagerService.submitAccountRequest(req1);
            this.userManagerService.approveAccountRequest(req1);
            testUser = this.userManagerService.getUserByUserName("test");
        } catch (PortletServiceException e) {
            _log.error("Exception " + e);
            fail("failed to generate AccountRequests");
        }
        return testUser;
    }

    private void deleteUsers() {
        this.userManagerService.deleteAccount(testUser);
    }

    public void testEverything() {
        permissionsTest();
        mappingsTest();
        //retrievalTest();
        //storageTest();
    }

    public void permissionsTest() {
        /*** Setup some variables ***/
        String globusDummy = "/O=Grid/O=Globus/OU=Dummy User";
        String ncsaDummy = "/O=Grid/O=NCSA/OU=Dummy User";
        String sdscDummy = "/O=Grid/O=SDSC/OU=Dummy User";
        List permissions = null;
        int numPermissions = 0;
        CredentialPermission permission = null;
        boolean answer = false;

        /*** Test get permissions (should be empty) ***/
        _log.info("Testing get permissions. Should be 0 entries.");
        permissions = this.credentialManagerService.getCredentialPermissions();
        numPermissions = permissions.size();

        /*** Test create globus permission ***/
        permission = this.credentialManagerService.createCredentialPermission(this.globusSubjects,
                this.globusDescription);
        /*** Test get permissions (should be 1 entry) ***/
        _log.info("Testing get permissions. Should be 1 entry");
        permissions = this.credentialManagerService.getCredentialPermissions();
        assertEquals(numPermissions + 1, permissions.size());


        /*** Test create globus permission ***/
        _log.info("Testing create globus permission.");
        permission = this.credentialManagerService.createCredentialPermission(this.globusSubjects,
                this.globusDescription);
        /*** Test get permissions (should be 1 entry) ***/
        _log.info("Testing get permissions. Should be 1 entry");
        permissions = this.credentialManagerService.getCredentialPermissions();
        assertEquals(numPermissions + 1, permissions.size());

        /*** Test retrieve globus permission ***/
        _log.info("Testing retrieve globus permission.");
        permission = this.credentialManagerService.getCredentialPermission(globusSubjects);
        assertEquals(this.globusSubjects, permission.getPermittedSubjects());
        assertEquals(this.globusDescription, permission.getDescription());

        /*** Test create ncsa permission ***/
        _log.info("Testing create ncsa permission.");
        permission = this.credentialManagerService.createCredentialPermission(this.ncsaSubjects,
                this.ncsaDescription);

        /*** Test list permissions (should be 2 entries) ***/
        _log.info("Testing get permissions. Should be 2 entries.");
        permissions = this.credentialManagerService.getCredentialPermissions();
        assertEquals(numPermissions + 2, permissions.size());

        /*** Test globus credential is permitted ***/
        _log.info("Testing globus credential is permitted.");
        answer = this.credentialManagerService.isCredentialPermitted(globusDummy);
        assertEquals(true, answer);

        /*** Test ncsa credential is permitted ***/
        _log.info("Testing ncsa credential is permitted.");
        answer = this.credentialManagerService.isCredentialPermitted(ncsaDummy);
        assertEquals(true, answer);

        /*** Test sdsc credential is not permitted ***/
        _log.info("Testing sdsc credential is not permitted.");
        answer = this.credentialManagerService.isCredentialPermitted(sdscDummy);
        assertEquals(false, answer);

        /*** Test delete permission ***/
        _log.info("Testing delete globus permission. Should be 1 entry left.");
        // Delete permission
        this.credentialManagerService.deleteCredentialPermission(this.globusSubjects);
        // Test number of permissions
        permissions = this.credentialManagerService.getCredentialPermissions();
        assertEquals(numPermissions + 1, permissions.size());
        // Test that permission is ncsa
        _log.info("Testing get permissions. This should be ncsa entry.");
        permission = (CredentialPermission) permissions.get(0);
        assertEquals(this.ncsaSubjects, permission.getPermittedSubjects());

        /*** Test globus credential is not permitted ***/
        _log.info("Testing globus is no longer permitted.");
        answer = this.credentialManagerService.isCredentialPermitted(globusDummy);
        assertEquals(false, answer);

        /*** Test ncsa credential is still permitted ***/
        _log.info("Testing ncsa is still permitted.");
        ncsaDummy = "/O=Grid/O=NCSA/OU=Dummy User";
        answer = this.credentialManagerService.isCredentialPermitted(ncsaDummy);
        assertEquals(true, answer);

        /*** Test remove ncsa permission ***/
        _log.info("Testing remove ncsa permission.");
        // Delete permission
        this.credentialManagerService.deleteCredentialPermission(this.ncsaSubjects);
        // Test number of permissions left...
        permissions = this.credentialManagerService.getCredentialPermissions();
        assertEquals(numPermissions, permissions.size());

        /*** Test "*" permission (Should permit all credentials) ***/
        _log.info("Testing \"all\" permission. Should permit all credentials.");
        permission = this.credentialManagerService.createCredentialPermission(this.allSubjects,
                this.allDescription);
        _log.info("Testing globus permission...");
        answer = permission.isCredentialPermitted(globusDummy);
        assertEquals(true, answer);
        _log.info("Testing ncsa permission...");
        answer = permission.isCredentialPermitted(ncsaDummy);
        assertEquals(true, answer);
        _log.info("Testing sdsc permission...");
        answer = permission.isCredentialPermitted(sdscDummy);
        assertEquals(true, answer);

        /*** Test remove last permission ***/
        _log.info("Testing remove last permission. Should be 0 entries now.");
        this.credentialManagerService.deleteCredentialPermission(this.allSubjects);
        // Test number of permissions left...
        permissions = this.credentialManagerService.getCredentialPermissions();
        assertEquals(numPermissions, permissions.size());
    }

    public void mappingsTest() {
        /*** Setup some variables first ***/
        List mappings = null;
        CredentialMapping mapping = null;
        CredentialMappingRequest mappingRequest = null;
        int numMappings = 0;
        int numRootMappings = 0;
        int numTestMappings = 0;
        List permissions = null;
        CredentialPermission permission = null;
        List hosts = null;

        // Get initial number of all mappings
        mappings = this.credentialManagerService.getCredentialMappings();
        numMappings = mappings.size();
        // Get initial number of root user mappings
        mappings = this.credentialManagerService.getCredentialMappings(this.rootUser);
        numRootMappings = mappings.size();
        // Get initial number of test user mappings
        mappings = this.credentialManagerService.getCredentialMappings(this.testUser);
        numTestMappings = mappings.size();

        /*** Test create guest mapping without globus permission. ***/
        _log.info("Testing create guest mapping without globus permission.");
        try {
            mapping = this.credentialManagerService.createCredentialMapping(this.guestSubject,
                    this.testUser);
            fail("This is bad. At this point, globus credentials should not be permitted.");
        } catch (CredentialNotPermittedException e) {
            _log.error("This is good. At this point, no globus credentials should be permitted.");
        }
        // Testing number of mappings
        _log.info("Testing get all mappings. Should be 0 entries.");
        hosts = this.credentialManagerService.getCredentialMappings();
        assertEquals(numMappings, mappings.size());

        /*** Test create guest mapping with globus permission. ***/
        _log.info("Creating globus permissions.");
        permission = this.credentialManagerService.createCredentialPermission(this.globusSubjects);
        _log.info("Testing create guest mapping with globus permission.");
        //try {
            _log.info("Testing create mapping request.");
            mappingRequest = this.credentialManagerService.createCredentialMappingRequest();
            mappingRequest.setUser(this.testUser);
            mappingRequest.setSubject(this.guestSubject);
            this.credentialManagerService.submitCredentialMappingRequest(mappingRequest);
            mapping = this.credentialManagerService.approveCredentialMappingRequest(mappingRequest);
            //mapping = this.credentialManagerService.createCredentialMapping(this.guestSubject,
            //        this.testUser);
        //} catch (CredentialNotPermittedException e) {
        //    String msg = "This is bad. At this point, globus credentials should be permitted.";
        //    _log.error(msg, e);
        //    fail(msg);
        //}
        // Testing number of mappings
        _log.info("Testing get all mappings. Should be 1 entry.");
        mappings = this.credentialManagerService.getCredentialMappings();
        assertEquals(numMappings + 1, mappings.size());

        /*** Test get mapping for guest subject. ***/
        _log.info("Testing get mapping for guest subject.");
        mapping = this.credentialManagerService.getCredentialMapping(this.guestSubject);
        assertEquals(this.guestSubject, mapping.getSubject());
        assertEquals(this.testUser.getID(), mapping.getUser().getID());
        assertEquals(null, mapping.getTag());
        hosts = mapping.getHosts();
        assertEquals(0, hosts.size());

        /*** Test get mappings for root user. Should be 0 entries. ***/
        _log.info("Testing get mappings for root user. Should be 0 entries.");
        mappings = this.credentialManagerService.getCredentialMappings(this.rootUser);
        assertEquals(numRootMappings, mappings.size());

        /*** Test get mappings for test user. Should be 1 entry. ***/
        _log.info("Testing get mappings for test user. Should be 1 entry.");
        mappings = this.credentialManagerService.getCredentialMappings(this.testUser);
        assertEquals(numTestMappings + 1, mappings.size());

        /*** Test update mapping tag for guest subject. ***/
        _log.info("Testing update mapping tag for guest subject.");
        try {
            this.credentialManagerService.setCredentialTag(guestSubject, guestTag);
        } catch (CredentialMappingNotFoundException e) {
            _log.error("This is bad. At this point, globus credentials should be permitted.", e);
        }
        mapping = this.credentialManagerService.getCredentialMapping(this.guestSubject);
        assertEquals(this.guestSubject, mapping.getSubject());
        assertEquals(this.testUser.getID(), mapping.getUser().getID());
        assertEquals(this.guestTag, mapping.getTag());
        hosts = mapping.getHosts();
        assertEquals(0, hosts.size());

        /*** Test update mapping hosts for guest subject. ***/
        _log.info("Testing update mapping hosts for guest subject.");
        hosts = new Vector();
        hosts.add(this.guestHost1);
        hosts.add(this.guestHost2);
        try {
            this.credentialManagerService.addCredentialHosts(guestSubject, hosts);
        } catch (CredentialMappingNotFoundException e) {
            _log.error("This is bad. At this point, globus credentials should be permitted.", e);
        }
        _log.info("Asserting number of hosts for guest subject...");
        mapping = this.credentialManagerService.getCredentialMapping(this.guestSubject);
        hosts = mapping.getHosts();
        assertEquals(2, hosts.size());

        _log.info("Asserting host names for guest subject...");
        assertEquals(true, mapping.hasHost(this.guestHost1));
        assertEquals(true, mapping.hasHost(this.guestHost2));

        _log.info("Asserting other mapping information is unchanged...");
        assertEquals(this.guestSubject, mapping.getSubject());
        assertEquals(this.testUser.getID(), mapping.getUser().getID());
        assertEquals(this.guestTag, mapping.getTag());

        /*** Testing delete mappping. ***/
        _log.info("Testing delete mapping for guest subject.");
        this.credentialManagerService.deleteCredentialMapping(this.guestSubject);

        /*** Test get all mappings. Should be 0 entries. ***/
        _log.info("Testing get all mappings. Should be 0 entries.");
        mappings = this.credentialManagerService.getCredentialMappings();
        assertEquals(numMappings, mappings.size());
    }

    public void retrievalTest() {
    }

    public void storageTest() {
    }
}
