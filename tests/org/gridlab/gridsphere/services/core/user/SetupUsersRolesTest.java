/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.PortletGroupFactory;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.services.core.security.acl.GroupEntry;
import org.gridlab.gridsphere.services.core.security.acl.GroupRequest;

public class SetupUsersRolesTest extends SetupTestUsersTest {

    public SetupUsersRolesTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(SetupUsersRolesTest.class);
    }

    protected void setUp() {
        super.setUp();
        createUsers();
    }

    public void testHasSuperRole() {
        boolean isRootReallyRoot = rootACLService.hasSuperRole(rootUser);
        assertTrue(isRootReallyRoot);

        PortletRole superRole = rootACLService.getRoleInGroup(rootUser, PortletGroupFactory.GRIDSPHERE_GROUP);
        assertEquals(superRole, PortletRole.SUPER);
    }


    public void testGroups() {

    }

    public void testUserGroup() {

    }

    public void testAssignRoles() {

        GroupRequest req = rootACLService.createGroupEntry();
        req.setUser(jason);
        req.setRole(PortletRole.ADMIN);
        req.setGroup(PortletGroupFactory.GRIDSPHERE_GROUP);


        rootACLService.saveGroupEntry(req);

        // should be an admin
        assertNotNull(jason);

        boolean isAdmin = rootACLService.hasRoleInGroup(jason, PortletGroupFactory.GRIDSPHERE_GROUP, PortletRole.ADMIN);
        assertTrue(isAdmin);

        GroupEntry jasonEntry = rootACLService.getGroupEntry(jason, PortletGroupFactory.GRIDSPHERE_GROUP);
        PortletRole role = jasonEntry.getRole();
        assertEquals(role, PortletRole.ADMIN);

        // should be an admin
        isAdmin = rootACLService.hasRoleInGroup(jason, PortletGroupFactory.GRIDSPHERE_GROUP, PortletRole.ADMIN);
        assertTrue(isAdmin);

    }

    protected void tearDown() {
        super.tearDown();
        deleteUsers();
    }

}
