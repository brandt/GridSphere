/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.spi.ServiceTest;
import org.gridlab.gridsphere.services.core.security.AuthenticationException;
import org.gridlab.gridsphere.services.core.user.LoginService;

public class SetupRootUserTest extends ServiceTest {

    private static LoginService loginService = null;

    protected User rootUser = null;

    public SetupRootUserTest(String name) {
        super(name);
    }

    public static void main(String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(SetupRootUserTest.class);
    }

    protected void setUp() {
        super.setUp();
        super.testCreateServiceFactory();
        testCreateRootUser();
    }

    public void testCreateRootUser() {
        log.info(" =====================================  setup");
        // Create a services using mock ServletConfig
        try {
            loginService = (LoginService) factory.createPortletService(LoginService.class, null, true);
        } catch (Exception e) {
            fail("Unable to initialize services: " + e.getMessage());
        }
        loginRoot();
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

    protected void tearDown() {
        super.tearDown();
    }

}
