/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import junit.framework.Test;
import junit.framework.TestSuite;

public class UserManagerServiceTest extends SetupTestUsersTest {

    public UserManagerServiceTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(UserManagerServiceTest.class);
    }

    protected void setUp() {
        super.setUp();
        super.testSetupUsers();
    }

    protected void tearDown() {
        super.tearDown();
    }

}
