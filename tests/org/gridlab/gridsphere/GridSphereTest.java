/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import org.apache.log4j.PropertyConfigurator;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.ServiceDescriptorTest;
import org.gridlab.gridsphere.portletcontainer.GridSphereServletTest;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDescriptorTest;
import org.gridlab.gridsphere.services.core.user.SetupRootUserTest;
import org.gridlab.gridsphere.services.core.user.SetupTestUsersTest;
import org.gridlab.gridsphere.services.core.user.SetupUsersRolesTest;

import java.net.URL;

/**
 * Simple class to build a TestSuite out of the individual test classes.
 */
public class GridSphereTest extends ServletTestCase {

    public GridSphereTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.main(new String[] {GridSphereTest.class.getName()});
    }

    public static Test suite() {
        URL propsUrl = GridSphereTest.class.getResource("/gridsphere/log4j.properties");
        PropertyConfigurator.configure(propsUrl);

        TestSuite suite = new TestSuite();

        suite.addTest(new GridSphereServletTest("testInitGridSphere"));
//        suite.addTest(new PortletDescriptorTest("testPortletDescriptor"));
        suite.addTest(new ServiceDescriptorTest("testServiceDescriptor"));
        suite.addTest(new SetupRootUserTest("testLoginRootUser"));
        suite.addTest(new SetupTestUsersTest("testCreateTestUsers"));
        suite.addTest(new SetupUsersRolesTest("testHasSuperRole"));
        suite.addTest(new SetupUsersRolesTest("testAssignRoles"));
        return suite;
    }

}

