/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.Descriptor;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;

import java.io.IOException;

/**
 * The <code>PortletLayoutDescriptor</code> is responsible for marshalling and
 * unmarshalling a container of portlet components into/from an XML descriptor
 * using Castor XML data binding capabilities.
 */
public class PortletLayoutDescriptor extends Descriptor {

    /**
     * Constructs an instance of PortletLayoutDescriptor
     */
    private PortletLayoutDescriptor() {
    }

    /**
     * Loads the portlet container associated with this descriptor
     *
     * @param layoutDescriptorPath location of the layout.xml
     * @param layoutMappingPath location of the mapping file
     *
     * @return the portlet container
     *
     * @throws IOException if an I/O error occurs
     * @throws DescriptorException if a descriptor error occurs
     */
    public static PortletContainer loadPortletContainer(String layoutDescriptorPath, String layoutMappingPath) throws IOException, DescriptorException {
        return (PortletContainer) load(layoutDescriptorPath, layoutMappingPath);
    }

    /**
     * Loads the portlet tab associated with this descriptor
     *
     * @param descriptorPath location of the layout.xml
     * @param mappingPath location of the mapping file
     *
     * @return the portlet tab
     *
     * @throws IOException if an I/O error occurs
     * @throws DescriptorException if a descriptor error occurs
     */
    public static PortletTab loadPortletTab(String descriptorPath, String mappingPath) throws IOException, DescriptorException {
        return (PortletTab) load(descriptorPath, mappingPath);
    }

    /**
     * Loads the portlet container associated with this descriptor
     *
     * @param pc the portlet container to load
     * @param descriptorPath location of the layout.xml
     * @param mappingPath location of the mapping file
     *
     * @throws IOException if an I/O error occurs
     * @throws DescriptorException if a descriptor error occurs
     */
    public static void savePortletContainer(PortletContainer pc, String descriptorPath, String mappingPath) throws IOException, DescriptorException {
        save(descriptorPath, mappingPath, pc);
    }

    /**
     * Saves the portlet tab associated with this descriptor
     *
     * @param tab the portlet tab to save
     * @param descriptorPath location of the layout.xml
     * @param mappingPath location of the mapping file
     *
     * @throws IOException if an I/O error occurs
     * @throws DescriptorException if a descriptor error occurs
     */
    public static void savePortletTab(PortletTab tab, String descriptorPath, String mappingPath) throws IOException, DescriptorException {
        save(descriptorPath, mappingPath, tab);
    }
}
