/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.ConfigParam;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.Description;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.servlets.GridSphereServletTest;
import org.gridlab.gridsphere.servlets.GridSphereServletTest;

import java.io.IOException;
import java.util.List;

/**
 * This is the base fixture for service testing. Provides a service factory and the
 * properties file.
 */
public class ServiceDescriptorTest extends GridSphereServletTest {


    public ServiceDescriptorTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(ServiceDescriptorTest.class);
    }

    public void setUp() {
        super.testInitGridSphere();
    }

    public void testServiceDescriptor() {
        SportletServiceDescriptor descriptor = null;

        // load files from JAR
        String serviceFile = config.getServletContext().getRealPath("/WEB-INF/test/PortletServices-test.xml");
        String servicesMappingPath = config.getServletContext().getRealPath("/WEB-INF/mapping/portlet-services-mapping.xml");
        try {
            descriptor = new SportletServiceDescriptor(serviceFile, servicesMappingPath);
        } catch (Exception e) {
            fail("IO error unmarshalling " + serviceFile + " : " + e.getMessage());
        }
        SportletServiceCollection services = descriptor.getServiceCollection();

        // assertEquals(expected, actual)

        List list = services.getPortletServicesList();
        assertEquals(2, list.size());

        SportletServiceDefinition serviceDef = (SportletServiceDefinition) list.get(0);
        assertEquals("Test Portlet Service", serviceDef.getServiceName());
        assertEquals("This is a Test Portlet Service", ((Description)serviceDef.getServiceDescription().get(0)).getText());
        assertEquals("org.gridlab.gridsphere.services.test.PortletTestService", serviceDef.getServiceInterface());
        assertEquals("org.gridlab.gridsphere.services.test.PortletTestServiceImpl", serviceDef.getServiceImplementation());
        assertEquals(false, serviceDef.getUserRequired());
        List configList = serviceDef.getConfigParamList();

        assertEquals(1, configList.size());
        ConfigParam configParam = (ConfigParam) configList.get(0);
        assertEquals("some parameter", configParam.getParamName());
        assertEquals("some value", configParam.getParamValue());

        serviceDef = (SportletServiceDefinition) list.get(1);
        assertEquals("User Test Portlet Service", serviceDef.getServiceName());
        assertEquals("This is a User Test Portlet Service", ((Description)(serviceDef.getServiceDescription().get(0))).getText());
        assertEquals("org.gridlab.gridsphere.services.test.PortletTestService", serviceDef.getServiceInterface());
        assertEquals("org.gridlab.gridsphere.services.test.PortletTestServiceImpl", serviceDef.getServiceImplementation());
        assertEquals(true, serviceDef.getUserRequired());
    }


}
