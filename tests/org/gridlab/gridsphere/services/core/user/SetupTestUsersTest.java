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
import org.gridlab.gridsphere.GridSphereScenarios;
import org.apache.cactus.ServletTestCase;

import java.util.List;

public class SetupTestUsersTest extends ServletTestCase {

    GridSphereScenarios scenario = null;

    public SetupTestUsersTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(SetupTestUsersTest.class);
    }

    protected void setUp() throws PortletServiceException {
        scenario = new GridSphereScenarios(this);
        scenario.setupGridSphereServlet();
        scenario.loginRoot();
        scenario.initRootServices();
    }

    public void testCreateUsers() throws PortletServiceException {
        scenario.createTestUsers();
    }

    protected void tearDown() {

    }

}
