/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;

import java.util.List;

public class SetupTestGroupsTest extends SetupRootUserTest {

    protected static AccessControlManagerService rootAclService = null;

    protected PortletGroup portal = null;
    protected PortletGroup triana = null;
    protected PortletGroup cactus = null;

    public SetupTestGroupsTest(String name) {
        super(name);
    }

    public static void main(String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(SetupTestGroupsTest.class);
    }

    protected void setUp() {
        super.setUp();
        super.loginRoot();
        log.info(" =====================================  setup");
        // Create a root user services using mock ServletConfig
        try {
            rootAclService = (AccessControlManagerService) factory.createUserPortletService(AccessControlManagerService.class, rootUser, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize services: ", e);
        }
    }

    public void testSetupGroups() {
        log.info("- setup groups");
        portal = rootAclService.createGroup("portal");
        triana = rootAclService.createGroup("triana");
        cactus = rootAclService.createGroup("cactus");

        PortletGroup c = rootAclService.getGroupByName("cactus");
        assertEquals(cactus, c);
    }

    public void testAddDeleteGroup() {
        log.info("- testAddRemove");
        PortletGroup hiya = rootAclService.createGroup("hiya");
        PortletGroup c = rootAclService.getGroupByName("hiya");
        assertEquals(hiya, c);
        rootAclService.deleteGroup(hiya);
        c = rootAclService.getGroupByName("hiya");
        assertEquals(null, c);
    }

    public void teardownGroups() {
        log.info("- teardown groups");
        rootAclService.deleteGroup(portal);
        rootAclService.deleteGroup(triana);
        rootAclService.deleteGroup(cactus);
    }

    protected void tearDown() {
        //teardownGroups();
        super.tearDown();
    }

}
