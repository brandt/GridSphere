/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import org.apache.log4j.PropertyConfigurator;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerTest;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.ServiceDescriptorTest;
import org.gridlab.gridsphere.portlet.service.spi.impl.GridSphereServiceTest;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletRequestImpl;
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
public class PortletRequestTest extends GridSphereServletTest {

    public PortletRequestTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.main(new String[] {PortletRequestTest.class.getName()});
    }

    public static Test suite() {
        return new TestSuite(PortletRequestTest.class);
    }

    protected void setUp() {
        super.setUp();
        super.testCreateServlet();
    }

    public void testCreatePortletRequest() {
        SportletRequestImpl req = new SportletRequestImpl(request);
        assertNotNull(req);
    }


}

