/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.services.security.acl.AccessControlManagerService;

public class SetupTestGroupsTest extends SetupRootUserTest {

    private static AccessControlManagerService aclService = null;

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
        log.info(" =====================================  setup");
        // Create a root user services using mock ServletConfig
        try {
            aclService = (AccessControlManagerService) factory.createPortletUserService(AccessControlManagerService.class, rootUser, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize services: ", e);
        }
        setupGroups();
    }

    public void setupGroups() {
        log.info("- setup groups");
        portal = aclService.createGroup("portal");
        triana = aclService.createGroup("triana");
        cactus = aclService.createGroup("cactus");
    }

    public void teardownGroups() {
        log.info("- setup groups");
        aclService.deleteGroup(portal);
        aclService.deleteGroup(triana);
        aclService.deleteGroup(cactus);
    }

    protected void tearDown() {
        teardownGroups();
        super.tearDown();
    }

}
