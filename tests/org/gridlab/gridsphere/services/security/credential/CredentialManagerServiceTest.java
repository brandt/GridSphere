/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Dec 25, 2002
 * Time: 4:18:13 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.credential;

import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import org.gridlab.gridsphere.portlet.service.spi.ServiceTest;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;

import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.PermissionDeniedException;

import org.gridlab.gridsphere.services.security.acl.impl.UserACL;

import org.gridlab.gridsphere.services.security.credential.CredentialPermission;
import org.gridlab.gridsphere.services.security.credential.CredentialMapping;
import org.gridlab.gridsphere.services.security.credential.CredentialNotPermittedException;
import org.gridlab.gridsphere.services.security.credential.impl.GlobusHostMapping;
import org.gridlab.gridsphere.services.security.credential.impl.GlobusCredentialMapping;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.List;
import java.util.Vector;

public class CredentialManagerServiceTest extends ServiceTest {

    // Service variables
    private static PortletLog _log = SportletLog.getInstance(CredentialManagerServiceTest.class);
    private PersistenceManagerRdbms _pm = PersistenceManagerRdbms.getInstance();
    private UserManagerService userManager = null;
    private CredentialManagerService credentialManager = null;
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

    public static Test suite( ) {
        return new TestSuite(CredentialManagerServiceTest.class);
    }

    protected void setUp() {
        super.setUp();
        _log.info(" =====================================  setup");
        // Create both services using mock ServletConfig
        try {
            this.userManager
                    = (UserManagerService)factory.createPortletService(UserManagerService.class, null, true);
            this.credentialManager
                    = (CredentialManagerService)factory.createPortletService(CredentialManagerService.class, null, true);
        } catch (Exception e) {
            _log.error("Unable to initialize services: ", e);
        }
        createUsers();
    }

    protected void tearDown() {
        _log.info(" ===================================== tear down");
        deleteUsers();
    }

    private void createUsers() {
        this.rootUser = createRootUser();
        this.testUser = createTestUser(this.rootUser);
    }

    private User createRootUser() {
        _log.info("- create root user");
        // create a superuser - faked
        SportletUserImpl root = new SportletUserImpl();
        root.setUserID("root");
        root.setGivenName("Root");

        UserACL rootacl = new UserACL();
        rootacl.setUserID(root.getOid());
        rootacl.setRoleID(PortletRole.SUPER.getID());
        rootacl.setGroupID(PortletGroup.SUPER.getID());
        rootacl.setStatus(UserACL.STATUS_APPROVED);

        try {
            _pm.create(root);
            _pm.create(rootacl);
        } catch (PersistenceManagerException e) {
            _log.error("Exception " + e);
        }
        return root;
    }

    private User createTestUser(User rootUser) {
        _log.info("- create test user");
        User testUser = null;
        AccountRequest req1 = userManager.createAccountRequest();
        req1.setUserID("test");
        req1.setGivenName("Test");
        try {
            this.userManager.submitAccountRequest(req1);
            this.userManager.approveAccountRequest(rootUser, req1, null);
            testUser = this.userManager.getUser(this.rootUser, "test");
        } catch (PortletServiceException e) {
            _log.error("Exception " + e);
            fail("failed to generate AccountRequests");
        } catch (PermissionDeniedException e) {
            _log.error("Exception " + e);
            fail("No permissions!");
        }
        return testUser;
    }

    private void deleteUsers() {
        try {
            this.userManager.removeUser(rootUser, "test");
            this.userManager.removeUser(rootUser, "root");
        } catch (PermissionDeniedException e) {
            _log.error("Exception " + e);
            fail("No permissions!");
        }
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
        CredentialPermission permission = null;
        boolean answer = false;

        /*** Test get permissions (should be empty) ***/
        _log.info("Testing get permissions. Should be 0 entries.");
        permissions = this.credentialManager.getCredentialPermissions();
        assertEquals(0, permissions.size());

        /*** Test create globus permission ***/
        _log.info("Testing create globus permission.");
        permission = this.credentialManager.createCredentialPermission(this.globusSubjects,
                                                                       this.globusDescription);
        /*** Test get permissions (should be 1 entry) ***/
        _log.info("Testing get permissions. Should be 1 entry");
        permissions = this.credentialManager.getCredentialPermissions();
        assertEquals(1, permissions.size());

        /*** Test retrieve globus permission ***/
        _log.info("Testing retrieve globus permission.");
        permission = this.credentialManager.getCredentialPermission(globusSubjects);
        assertEquals(this.globusSubjects, permission.getPermittedSubjects());
        assertEquals(this.globusDescription, permission.getDescription());

        /*** Test update and retrieve globus permission ***/
        _log.info("Testing update globus permission.");
        // Modify the subject and description
        this.globusSubjects = "/O=Grid/O=Globus*";
        this.globusDescription = "Permission to use all Globus credentials";
        permission.setPermittedSubjects(this.globusSubjects);
        permission.setDescription(this.globusDescription);
        // Update the permission
        this.credentialManager.updateCredentialPermission(permission);
        // Then retrieve it again
        permission = this.credentialManager.getCredentialPermission(this.globusSubjects);
        // And check everything is all right
        assertEquals(this.globusSubjects, permission.getPermittedSubjects());
        assertEquals(this.globusDescription, permission.getDescription());

        /*** Test create ncsa permission ***/
        _log.info("Testing create ncsa permission.");
        permission = this.credentialManager.createCredentialPermission(this.ncsaSubjects,
                                                                       this.ncsaDescription);

        /*** Test list permissions (should be 2 entries) ***/
        _log.info("Testing get permissions. Should be 2 entries.");
        permissions = this.credentialManager.getCredentialPermissions();
        assertEquals(2, permissions.size());

        /*** Test globus credential is permitted ***/
        _log.info("Testing globus credential is permitted.");
        answer = this.credentialManager.isCredentialPermitted(globusDummy);
        assertEquals(true, answer);

        /*** Test ncsa credential is permitted ***/
        _log.info("Testing ncsa credential is permitted.");
        answer = this.credentialManager.isCredentialPermitted(ncsaDummy);
        assertEquals(true, answer);

        /*** Test sdsc credential is not permitted ***/
        _log.info("Testing sdsc credential is not permitted.");
        answer = this.credentialManager.isCredentialPermitted(sdscDummy);
        assertEquals(false, answer);

        /*** Test delete permission ***/
        this.credentialManager.deleteCredentialPermission(this.globusSubjects);

        /*** Test list permissions (should be ncsa entry only) ***/
        _log.info("Testing get permissions. Should be 1 entry left.");
        permissions = this.credentialManager.getCredentialPermissions();
        assertEquals(1, permissions.size());
        _log.info("Testing get permissions. This should be ncsa entry.");
        permission = (CredentialPermission)permissions.get(0);
        assertEquals(this.ncsaSubjects, permission.getPermittedSubjects());

        /*** Test globus credential is not permitted ***/
        _log.info("Testing globus is no longer permitted.");
        answer = this.credentialManager.isCredentialPermitted(globusDummy);
        assertEquals(false, answer);

        /*** Test ncsa credential is still permitted ***/
        _log.info("Testing ncsa is still permitted.");
        ncsaDummy = "/O=Grid/O=NCSA/OU=Dummy User";
        answer = this.credentialManager.isCredentialPermitted(ncsaDummy);
        assertEquals(true, answer);

        /*** Test remove ncsa permission ***/
        _log.info("Testing remove ncsa permission.");
        this.credentialManager.deleteCredentialPermission(this.ncsaSubjects);
        permissions = this.credentialManager.getCredentialPermissions();
        assertEquals(0, permissions.size());

        /*** Test "*" permission (Should permit all credentials) ***/
        _log.info("Testing \"all\" permission. Should permit all credentials.");
        permission = this.credentialManager.createCredentialPermission(this.allSubjects,
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
        this.credentialManager.deleteCredentialPermission(this.allSubjects);
        permissions = this.credentialManager.getCredentialPermissions();
        assertEquals(0, permissions.size());
    }

    public void mappingsTest() {
        /*** Setup some variables first ***/
        List mappings = null;
        CredentialMapping mapping = null;
        List permissions = null;
        CredentialPermission permission = null;
        List hosts = null;

        /*** Test get all mappings. Should be 0 entries. ***/
        _log.info("Testing get all mappings. Should be 0 entries.");
        mappings = this.credentialManager.getCredentialMappings();
        assertEquals(0, mappings.size());

        /*** Test create guest mapping without globus permission. ***/
        _log.info("Testing create guest mapping without globus permission.");
        try {
            mapping = this.credentialManager.createCredentialMapping(this.guestSubject,
                                                                     this.testUser);
        } catch (CredentialNotPermittedException e) {
            _log.error("This is good. At this point, no globus credentials should be permitted.");
        }

        /*** Test create guest mapping with globus permission. ***/
        _log.info("Creating globus permissions.");
        permission = this.credentialManager.createCredentialPermission(this.globusSubjects);
        _log.info("Testing create guest mapping with globus permission.");
        try {
            mapping = this.credentialManager.createCredentialMapping(this.guestSubject,
                                                                     this.testUser);
        } catch (CredentialNotPermittedException e) {
            _log.error("This is bad. At this point, globus credentials should be permitted.", e);
        }
        assertEquals(this.guestSubject, mapping.getSubject());
        assertEquals(this.testUser.getID(), mapping.getUser().getID());
        assertEquals(null, mapping.getTag());
        hosts = mapping.getHosts();
        assertEquals(0, hosts.size());

        /*** Test get mapping for guest subject. ***/
        _log.info("Testing get mapping for guest subject.");
        mapping = this.credentialManager.getCredentialMapping(this.guestSubject);
        assertEquals(this.guestSubject, mapping.getSubject());
        assertEquals(this.testUser.getID(), mapping.getUser().getID());
        assertEquals(null, mapping.getTag());
        hosts = mapping.getHosts();
        assertEquals(0, hosts.size());

        /*** Test get all credential mappings. Should be 1 entry. ***/
        _log.info("Testing get all mappings. Should be 1 entry.");
        mappings = this.credentialManager.getCredentialMappings();
        assertEquals(1, mappings.size());

        /*** Test get mappings for root user. Should be 0 entries. ***/
        _log.info("Testing get mappings for root user. Should be 0 entries.");
        mappings = this.credentialManager.getCredentialMappings(this.rootUser);
        assertEquals(0, mappings.size());

        /*** Test get mappings for test user. Should be 1 entry. ***/
        _log.info("Testing get mappings for test user. Should be 1 entry.");
        mappings = this.credentialManager.getCredentialMappings(this.testUser);
        assertEquals(1, mappings.size());

        /*** Test get mapping for guest subject. ***/
        _log.info("Testing get mapping for guest subject.");
        mapping = this.credentialManager.getCredentialMapping(this.guestSubject);
        assertEquals(this.guestSubject, mapping.getSubject());
        assertEquals(this.testUser.getID(), mapping.getUser().getID());
        assertEquals(null, mapping.getTag());
        hosts = mapping.getHosts();
        assertEquals(0, hosts.size());

        /*** Test update mapping tag for guest subject. ***/
        _log.info("Testing update mapping tag for guest subject.");
        mapping.setTag(this.guestTag);
        try {
            this.credentialManager.updateCredentialMapping(mapping);
        } catch (CredentialNotPermittedException e) {
            _log.error("This is bad. At this point, globus credentials should be permitted.", e);
        }
        mapping = this.credentialManager.getCredentialMapping(this.guestSubject);
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
        mapping.addHosts(hosts);
        try {
            this.credentialManager.updateCredentialMapping(mapping);
        } catch (CredentialNotPermittedException e) {
            _log.error("This is bad. At this point, globus credentials should be permitted.", e);
        }
        _log.info("Asserting number of hosts for guest subject...");
        mapping = this.credentialManager.getCredentialMapping(this.guestSubject);
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
        this.credentialManager.deleteCredentialMapping(this.guestSubject);

        /*** Test get all mappings. Should be 0 entries. ***/
        _log.info("Testing get all mappings. Should be 0 entries.");
        mappings = this.credentialManager.getCredentialMappings();
        assertEquals(0, mappings.size());
    }

    public void retrievalTest() {
    }

    public void storageTest() {
    }
}
