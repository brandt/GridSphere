/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.PropertyConfigurator;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;

/**
 * This is the base fixture for service testing. Provides a service factory and the
 * properties file.
 */
public class ServiceTest extends TestCase {

    protected static SportletServiceFactory factory = null;
    protected static PortletLog log = SportletLog.getInstance(ServiceTest.class);
    protected PersistenceManagerRdbms persistenceManager = PersistenceManagerRdbms.getInstance();

    public ServiceTest(String name) {
        super(name);
    }

    protected void setUp() {
        PropertyConfigurator.configure("conf/log4j.properties");
        // create factory
        factory = SportletServiceFactory.getInstance();
        if (factory == null) fail("Unable to instantiate SportletServiceFactory!");
    }

    public static void main(String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(ServiceTest.class);
    }

    protected void tearDown() {
        SportletServiceFactory.getInstance().shutdownServices();
    }
}
