/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.service.spi.ServiceTest;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.ServiceDescriptorTest;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDescriptorTest;
import org.gridlab.gridsphere.services.security.credential.CredentialManagerServiceTest;
import org.gridlab.gridsphere.services.user.SetupRootUserTest;
import org.gridlab.gridsphere.services.user.SetupTestGroupsTest;
import org.gridlab.gridsphere.services.user.SetupTestUsersTest;
import org.gridlab.gridsphere.services.user.UserManagerServiceTest;

/**
 * Simple class to build a TestSuite out of the individual test classes.
 */
public class AllJUnitTests extends TestCase {

    public AllJUnitTests(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(PortletDescriptorTest.class));
        suite.addTest(new TestSuite(ServiceDescriptorTest.class));

        //suite.addTest(new TestSuite(ServiceTest.class));
       // suite.addTest(new TestSuite(SetupRootUserTest.class));

        //suite.addTest(new TestSuite(SetupTestGroupsTest.class));
        //suite.addTest(new TestSuite(SetupTestUsersTest.class));
        //suite.addTest(new TestSuite(UserManagerServiceTest.class));
        //suite.addTest(new TestSuite(CredentialManagerServiceTest.class));

        return suite;
    }

}

