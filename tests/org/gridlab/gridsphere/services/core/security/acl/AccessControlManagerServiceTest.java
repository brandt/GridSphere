/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.security.acl;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.services.core.user.SetupTestUsersTest;

public class AccessControlManagerServiceTest extends SetupTestUsersTest {

    private AccessControlManagerService aclService = null;

    public AccessControlManagerServiceTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(AccessControlManagerServiceTest.class);
    }

    protected void setUp() {
        super.setUp();
        super.testSetupUsers();
        // Create a root user services using mock ServletConfig
        try {
            aclService = (AccessControlManagerService) factory.createUserPortletService(AccessControlManagerService.class, rootUser, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize services: ", e);
        }
    }

    public void testIsRootSuper() {
        assertTrue(aclService.hasSuperRole(rootUser));
    }

    protected void tearDown() {
        super.tearDown();
    }

}
