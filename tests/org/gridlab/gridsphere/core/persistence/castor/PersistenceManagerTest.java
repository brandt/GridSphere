/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence.castor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/**
 * This is the persistence manager test to make sure we can get a PM instance
 */
public class PersistenceManagerTest extends TestCase {

    protected static PersistenceManagerRdbms pm = null;
    protected static PortletLog log = SportletLog.getInstance(PersistenceManagerTest.class);

    public PersistenceManagerTest(String name) {
        super(name);
    }

    protected void setUp() {

    }

    public void testGetPMInstance() {
        // create persistence manager
        pm = PersistenceManagerFactory.createGridSphereRdbms();
    }

    public static void main(String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(PersistenceManagerTest.class);
    }

    protected void tearDown() {

    }
}
