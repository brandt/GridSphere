/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.ConfigParam;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;

import java.io.IOException;
import java.util.List;

/**
 * This is the base fixture for service testing. Provides a service factory and the
 * properties file.
 */
public class ServiceDescriptorTest extends TestCase {


    public ServiceDescriptorTest(String name) {
        super(name);
    }

    public static void main(String[] args) throws Exception {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        return new TestSuite(ServiceDescriptorTest.class);
    }

    public void testDescriptor() {
        SportletServiceDescriptor descriptor = null;
        String serviceFile = "webapps/gridsphere/WEB-INF/conf/test/PortletServices-test.xml";
        String mappingFile = "webapps/gridsphere/WEB-INF/conf/mapping/PortletServices-mapping.xml";
        try {
            descriptor = new SportletServiceDescriptor(serviceFile, mappingFile);
        } catch (IOException e) {
            fail("IO error unmarshalling " + serviceFile + " using " + mappingFile + " : " + e.getMessage());
        } catch (DescriptorException e) {
            fail("Unable to unmarshall " + serviceFile + " using " + mappingFile + " : " + e.getMessage());
        }
        SportletServiceCollection services = descriptor.getServiceCollection();

        // assertEquals(expected, actual)

        List list = services.getPortletServicesList();
        assertEquals(1, list.size());

        SportletServiceDefinition serviceDef = (SportletServiceDefinition) list.get(0);
        assertEquals("Test Portlet Service", serviceDef.getName());
        assertEquals("This is a Test Portlet Service", serviceDef.getDescription());
        assertEquals("org.gridlab.gridsphere.services.test.PortletTestService", serviceDef.getInterface());
        assertEquals("org.gridlab.gridsphere.services.test.PortletTestServiceImpl", serviceDef.getImplementation());
        List configList = serviceDef.getConfigParamList();

        assertEquals(1, configList.size());
        ConfigParam configParam = (ConfigParam) configList.get(0);
        assertEquals("some parameter", configParam.getParamName());
        assertEquals("some value", configParam.getParamValue());
    }


}
