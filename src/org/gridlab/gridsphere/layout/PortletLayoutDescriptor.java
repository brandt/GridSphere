/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.Descriptor;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PortletLayoutDescriptor extends Descriptor {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletLayoutDescriptor.class);
    private PortletContainer pc = null;
    private List containerElements = new ArrayList();

    public PortletLayoutDescriptor() throws IOException, DescriptorException {
        GridSphereConfig gsConfig = GridSphereConfig.getInstance();
        String layoutDescriptorPath = gsConfig.getProperty(GridSphereConfigProperties.LAYOUT_XML);
        String layoutMappingPath = gsConfig.getProperty(GridSphereConfigProperties.LAYOUT_MAPPING_XML);
        pc = (PortletContainer)load(layoutDescriptorPath, layoutMappingPath);
    }

    /**
     * Constructs a PortletLayoutDescriptor from an xml descriptor and a mapping file
     *
     * @param layoutDescriptorPath location of the layout.xml
     * @param layoutMappingPath location of the mapping file
     * @throws DescriptorException if the PortletLayoutDescriptor cannot be created
     */
    public PortletLayoutDescriptor(String layoutDescriptorPath, String layoutMappingPath) throws IOException, DescriptorException  {
        pc = (PortletContainer)load(layoutDescriptorPath, layoutMappingPath);
    }

    /**
     * Returns the portlet container associated with this descriptor
     *
     * @return the portlet container
     */
    public PortletContainer getPortletContainer() {
        return pc;
    }

    public List getPortletContainerList() {
        return containerElements;
    }

    /**
     *
     */
    public void save(String descriptorPath, String mappingPath) throws IOException, DescriptorException {
       save(descriptorPath, mappingPath, pc);
    }

}
