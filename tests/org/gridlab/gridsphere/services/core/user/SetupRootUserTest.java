/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.spi.impl.GridSphereServiceTest;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;

public class SetupRootUserTest extends GridSphereServiceTest {

    private static LoginService loginService = null;

    protected User rootUser = null;

    public SetupRootUserTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(SetupRootUserTest.class);
    }

    protected void setUp() {
        super.setUp();
        super.testServiceFactoryCreate();
    }

    public void testCreateRootUser() {
        log.info(" =====================================  setup");
        loginRoot();
    }

    public void loginRoot() {
        try {
            loginService = (LoginService) factory.createPortletService(LoginService.class, config, true);
        } catch (Exception e) {
            String msg = "Unable to get login service instance";
            log.error(msg, e);
            fail(msg);
        }
        try {
            rootUser = loginService.login("root", "");
        } catch (AuthorizationException e) {
            String msg = "Unable to login as root";
            log.error(msg, e);
            fail(msg);
        }
    }

    protected void tearDown() {
        super.tearDown();
    }

}
