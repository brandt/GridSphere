/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.GridSphereScenarios;
import org.apache.cactus.ServletTestCase;

public class SetupRootUserTest extends ServletTestCase {

    private GridSphereScenarios scenario = null;
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
       scenario = new GridSphereScenarios(this);
       scenario.setupGridSphereServlet();
    }

    public void testLoginRootUser() throws PortletServiceException {
        scenario.loginRoot();
    }

    protected void tearDown() {

    }

}
