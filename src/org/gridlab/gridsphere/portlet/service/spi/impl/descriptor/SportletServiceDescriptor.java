/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import java.io.IOException;

public class SportletServiceDescriptor extends Descriptor {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(SportletServiceDescriptor.class);

    private SportletServiceCollection services = null;

    private String descriptorPath, mappingPath;

    public SportletServiceDescriptor(String descriptorFile, String mappingFile) throws IOException, DescriptorException {
        services = (SportletServiceCollection)load(descriptorFile, mappingFile);
    }

    public SportletServiceDescriptor() throws IOException, DescriptorException {
        descriptorPath = GridSphereConfig.getProperty(GridSphereConfigProperties.PORTLET_SERVICES_XML);
        mappingPath = GridSphereConfig.getProperty(GridSphereConfigProperties.PORTLET_SERVICES_MAPPING_XML);
        services = (SportletServiceCollection)load(descriptorPath, mappingPath);
    }

    public SportletServiceCollection getServiceCollection() {
        return services;
    }

    public void save() throws IOException, DescriptorException  {
        save(descriptorPath, mappingPath, services);
    }

}
