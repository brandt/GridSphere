/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.portlet.impl.SportletRequestImpl;
import org.gridlab.gridsphere.portletcontainer.GridSphereServletTest;

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
        super.testInitGridSphere();
    }

    public void testCreatePortletRequest() {
        SportletRequestImpl req = new SportletRequestImpl(request);
        assertNotNull(req);
    }


}

