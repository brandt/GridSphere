/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;

import java.util.List;

public class SetupTestUsersTest extends SetupRootUserTest {

    protected UserManagerService rootUserService = null;
    //protected AccessControlManagerService rootACLService = null;

    protected User jason = null;
    protected PortletGroup coreGroup = null;

    public SetupTestUsersTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(SetupTestUsersTest.class);
    }

    protected void setUp() {
        super.setUp();
        try {
            super.testLoginRootUser();
            rootUserService = (UserManagerService) factory.createPortletService(UserManagerService.class, context, true);
            //rootACLService = (AccessControlManagerService) factory.createPortletService(AccessControlManagerService.class, context, true);
        } catch (PortletServiceException e) {
            fail("Unable to initialize user services");
        }

    }

    protected void createUsers() {
        /*
        coreGroup = rootACLService.getCoreGroup();
        assertNotNull(coreGroup);

        SportletUser jasonRequest = rootUserService.createUser();

        jasonRequest.setUserID("jason");
        jasonRequest.setGivenName("Jason");

        SportletUser michaelRequest = rootUserService.createUser();
        michaelRequest.setUserID("michael");
        michaelRequest.setGivenName("Michael");

        SportletUser oliverRequest = rootUserService.createUser();
        oliverRequest.setUserID("oliver");
        oliverRequest.setGivenName("Oliver");

        rootUserService.saveUser(jasonRequest);
        rootUserService.saveUser(michaelRequest);
        rootUserService.saveUser(oliverRequest);

        jason = rootUserService.getUserByUserName("jason");
        */
    }

    public void testCreateTestUsers() {
        // jason, michael should be users
        // oliver will be denied

        createUsers();

        // test accessor methods

        // should be root + 3 new users
        List users = rootUserService.getUsers();
        assertEquals(users.size(), 4);

        jason = rootUserService.getUserByUserName("jason");
        assertEquals(jason.getGivenName(), "Jason");


        deleteUsers();
        users = rootUserService.getUsers();
        assertEquals(1, users.size());
        // test accessor methods
        //users = rootUserService.getUsers();
        //assertFalse(users.contains(jason));
        //assertFalse(users.contains(michael));
        //assertFalse(users.contains(oliver));
    }

    protected void deleteUsers() {
        // test delete users
        rootUserService.deleteUser(jason);
        //User jason2 = rootUserService.getUserByUserName("jason");
        //assertNull(jason2);

        User oliver = rootUserService.getUserByUserName("oliver");
        User michael = rootUserService.getUserByUserName("michael");
        rootUserService.deleteUser(michael);
        rootUserService.deleteUser(oliver);

    }

    protected void tearDown() {

    }

}
