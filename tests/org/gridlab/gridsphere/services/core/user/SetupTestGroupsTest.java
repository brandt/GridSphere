/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;

public class SetupTestGroupsTest extends SetupRootUserTest {

    //protected static AccessControlManagerService rootAclService = null;

    protected PortletGroup portal = null;
    protected PortletGroup triana = null;
    protected PortletGroup cactus = null;

    public SetupTestGroupsTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(SetupTestGroupsTest.class);
    }

    protected void setUp() {
        super.setUp();

    }



    public void testSuperRolesHasSuperRole() {
        //assertTrue(rootAclService.hasSuperRole(rootUser));
    }

    public void testSuperRolesInSuperGroup() {
        //List groups = rootAclService.getGroups(rootUser);
        //assertEquals(1, groups.size());
        //PortletGroup g = rootAclService.getSuperGroup()
    }

    public void testRolesInGroup() {
        /***
        PortletRole role = rootAclService.getRoleInGroup(rootUser, null);
        assertEquals(PortletRole.SUPER, role);
         ***/
    }

    public void testHasSuperRole() {
        //assertTrue(rootAclService.hasSuperRole(rootUser));
    }


    protected void tearDown() {
        //teardownGroups();
        super.tearDown();
    }

}
