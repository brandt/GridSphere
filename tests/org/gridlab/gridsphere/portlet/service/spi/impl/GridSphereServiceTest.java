/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import org.apache.log4j.PropertyConfigurator;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerTest;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.ServiceDescriptorTest;
import org.gridlab.gridsphere.portletcontainer.GridSphereServletTest;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDescriptorTest;
import org.gridlab.gridsphere.services.security.credential.CredentialManagerServiceTest;
import org.gridlab.gridsphere.services.user.SetupRootUserTest;
import org.gridlab.gridsphere.services.user.SetupTestGroupsTest;
import org.gridlab.gridsphere.services.user.SetupTestUsersTest;
import org.gridlab.gridsphere.services.user.UserManagerServiceTest;
import org.gridlab.gridsphere.GridSphereTest;

import java.net.URL;

/**
 * Simple class to build a TestSuite out of the individual test classes.
 */
public class GridSphereServiceTest extends GridSphereServletTest {

    public GridSphereServiceTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.main(new String[] {GridSphereServiceTest.class.getName()});
    }

    public static Test suite() {
        return new TestSuite(GridSphereServiceTest.class);
    }

    protected void setUp() {
        super.setUp();
        super.testCreateServlet();
    }

    public void testServiceFactoryCreate() {
        factory = SportletServiceFactory.getInstance();
        assertNotNull(factory);
    }
}

