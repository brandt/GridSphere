/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

import org.gridlab.gridsphere.services.security.acl.ACLServiceTest;
import org.gridlab.gridsphere.services.user.UserManagerServiceTest;
import org.gridlab.gridsphere.portletcontainer.descriptor.DescriptorTest;

/**
 * Simple class to build a TestSuite out of the individual test classes.
 */
public class AllJUnitTests extends TestCase {

    public AllJUnitTests(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(ACLServiceTest.class));
        suite.addTest(new TestSuite(UserManagerServiceTest.class));
        suite.addTest(new TestSuite(DescriptorTest.class));
        return suite;
   }
}

