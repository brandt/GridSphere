/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Dec 25, 2002
 * Time: 4:18:13 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.services.security.credential;

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
import org.gridlab.gridsphere.core.security.impl.GlobusCredentialPermission;
import org.gridlab.gridsphere.core.security.CredentialPermission;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.List;
import java.util.Vector;

public class CredentialManagerServiceTest extends ServiceTest {

    private static PortletLog _log = SportletLog.getInstance(CredentialManagerServiceTest.class);
    private PersistenceManagerRdbms _pm = PersistenceManagerRdbms.getInstance();
    private UserManagerService userManager = null;
    private CredentialManagerService credentialManager = null;
    private User rootUser;
    private User testUser;

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
        //createUsers();
    }

    protected void tearDown() {
        _log.info(" ===================================== tear down");
        //deleteUsers();
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

        AccountRequest req1 = userManager.createAccountRequest();
        req1.setUserID("test");
        req1.setGivenName("Test");

        try {
            this.userManager.submitAccountRequest(req1);
            this.userManager.approveAccountRequest(rootUser, req1, null);
        } catch (PortletServiceException e) {
            _log.error("Exception " + e);
            fail("failed to generate AccountRequests");
        } catch (PermissionDeniedException e) {
            _log.error("Exception " + e);
            fail("No permissions!");
        }
        return this.userManager.getUser("test");
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

    public void testCredentialPermission() {

        /*** Test get permissions (should be empty) ***/
        _log.info("Testing get permissions.");
        List permissions = this.credentialManager.getCredentialPermissions();
        assertEquals(0, permissions.size());

        /*** Test create and retrieve globus permission ***/
        _log.info("Testing create and retrieve globus permission.");
        CredentialPermission permission = new GlobusCredentialPermission();
        String globusSubjects = "/O=Grid/O=Globus*";
        permission.setPermittedSubjects(globusSubjects);
        permission.setDescription("Permission to use Globus credentials");
        this.credentialManager.createCredentialPermission(permission);
        permission = this.credentialManager.getCredentialPermission(globusSubjects);
        assertEquals(globusSubjects, permission.getPermittedSubjects());

        /*** Test update and retrieve globus permission ***/
        _log.info("Testing update and retrieve globus permission.");
        globusSubjects = "/O=Grid/O=Globus/*";
        permission.setPermittedSubjects(globusSubjects);
        this.credentialManager.updateCredentialPermission(permission);
        permission = this.credentialManager.getCredentialPermission(globusSubjects);
        assertEquals(globusSubjects, permission.getPermittedSubjects());

        /*** Test create ncsa permission ***/
        _log.info("Testing create ncsa permission.");
        permission = new GlobusCredentialPermission();
        String ncsaSubjects = "/O=Grid/O=NCSA/*";
        permission.setPermittedSubjects(ncsaSubjects);
        permission.setDescription("Permission to use NCSA-Alliance credentials");
        this.credentialManager.createCredentialPermission(permission);

        /*** Test list permissions (should be 2 entries) ***/
        _log.info("Testing get permissions. Should be 2 entries.");
        permissions = this.credentialManager.getCredentialPermissions();
        assertEquals(2, permissions.size());

        /*** Test globus credential is permitted ***/
        _log.info("Testing globus credential is permitted.");
        String globusDummy = "/O=Grid/O=Globus/OU=Dummy User";
        boolean answer = this.credentialManager.isCredentialPermitted(globusDummy);
        assertEquals(true, answer);

        /*** Test delete permission ***/
        this.credentialManager.deleteCredentialPermission(globusSubjects);

        /*** Test list permissions (should be ncsa entry only) ***/
        _log.info("Testing get permissions. Should be 1 entry left.");
        permissions = this.credentialManager.getCredentialPermissions();
        assertEquals(1, permissions.size());
        _log.info("Testing get permissions. This should be ncsa entry.");
        permission = (CredentialPermission)permissions.get(0);
        assertEquals(ncsaSubjects, permission.getPermittedSubjects());

        /*** Test globus credential is not permitted ***/
        _log.info("Testing globus is no longer supported.");
        answer = this.credentialManager.isCredentialPermitted(globusDummy);
        assertEquals(false, answer);

        /*** Test remove last permission ***/
        _log.info("Testing remove last permission.");
        this.credentialManager.deleteCredentialPermission(ncsaSubjects);
        permissions = this.credentialManager.getCredentialPermissions();
        assertEquals(0, permissions.size());
    }
}
