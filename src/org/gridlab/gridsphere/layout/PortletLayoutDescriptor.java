/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import java.io.IOException;

/**
 * The <code>PortletLayoutDescriptor</code> is responsible for marshalling and
 * unmarshalling a container of portlet components into/from an XML descriptor
 * using Castor XML data binding capabilities.
 */
public class PortletLayoutDescriptor {

    private static PersistenceManagerXml pmXML = null;

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
     * @throws PersistenceManagerException if a descriptor error occurs
     */
    public static PortletPage loadPortletContainer(String layoutDescriptorPath, String layoutMappingPath) throws IOException, PersistenceManagerException {
        pmXML = PersistenceManagerFactory.createPersistenceManagerXml(layoutDescriptorPath, layoutMappingPath);
        return (PortletPage) pmXML.load();
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
     * @throws PersistenceManagerException if a descriptor error occurs
     */
    public static PortletTab loadPortletTab(String descriptorPath, String mappingPath) throws IOException, PersistenceManagerException {
        pmXML = PersistenceManagerFactory.createPersistenceManagerXml(descriptorPath, mappingPath);
        return (PortletTab) pmXML.load();
    }

    /**
     * Loads the portlet container associated with this descriptor
     *
     * @param pc the portlet container to load
     * @param descriptorPath location of the layout.xml
     * @param mappingPath location of the mapping file
     *
     * @throws IOException if an I/O error occurs
     * @throws PersistenceManagerException if a descriptor error occurs
     */
    public static void savePortletContainer(PortletPage pc, String descriptorPath, String mappingPath) throws IOException, PersistenceManagerException {
        pmXML.setDescriptorPath(descriptorPath);
        pmXML.setMappingPath(mappingPath);
        pmXML.save(pc);
    }

    /**
     * Saves the portlet tab associated with this descriptor
     *
     * @param tab the portlet tab to save
     * @param descriptorPath location of the layout.xml
     * @param mappingPath location of the mapping file
     *
     * @throws IOException if an I/O error occurs
     * @throws PersistenceManagerException if a descriptor error occurs
     */
    public static void savePortletTab(PortletTab tab, String descriptorPath, String mappingPath) throws IOException, PersistenceManagerException {
        pmXML.setDescriptorPath(descriptorPath);
        pmXML.setMappingPath(mappingPath);
        pmXML.save(tab);
    }
}
