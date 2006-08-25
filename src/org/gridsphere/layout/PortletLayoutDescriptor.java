/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id: PortletLayoutDescriptor.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.portletcontainer.impl.JavaXMLBindingFactory;
import org.gridsphere.services.core.persistence.PersistenceManagerXml;
import org.gridsphere.services.core.persistence.PersistenceManagerException;

import java.io.IOException;
import java.net.URL;

/**
 * The <code>PortletLayoutDescriptor</code> is responsible for marshalling and
 * unmarshalling a container of portlet components into/from an XML descriptor
 * using Castor XML data binding capabilities.
 */
public class PortletLayoutDescriptor {

    /**
     * Constructs an instance of PortletLayoutDescriptor
     */
    private PortletLayoutDescriptor() {
    }

    /**
     * Loads the portlet container associated with this descriptor
     *
     * @param layoutDescriptorPath location of the layout.xml
     * @param layoutMappingPath    location of the mapping file
     * @return the portlet container
     * @throws IOException                 if an I/O error occurs
     * @throws PersistenceManagerException if a descriptor error occurs
     */
    public static PortletPage loadPortletPage(String layoutDescriptorPath, URL layoutMappingPath) throws IOException, PersistenceManagerException {
        PersistenceManagerXml pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(layoutDescriptorPath, layoutMappingPath);
        PortletPage page = (PortletPage) pmXML.load();
        page.setLayoutDescriptor(layoutDescriptorPath);
        return page;
    }

    /**
     * Loads the portlet tab associated with this descriptor
     *
     * @param descriptorPath location of the layout.xml
     * @param mappingPath    location of the mapping file
     * @return the portlet tab
     * @throws IOException                 if an I/O error occurs
     * @throws PersistenceManagerException if a descriptor error occurs
     */
    public static PortletTabbedPane loadPortletTabs(String descriptorPath, URL mappingPath) throws IOException, PersistenceManagerException {
        PersistenceManagerXml pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorPath, mappingPath);
        return (PortletTabbedPane) pmXML.load();
    }

    /**
     * Saves the portlet page associated with this descriptor
     *
     * @param pc             the portlet container to load
     * @param descriptorPath location of the layout.xml
     * @param mappingPath    location of the mapping file
     * @throws IOException                 if an I/O error occurs
     * @throws PersistenceManagerException if a descriptor error occurs
     */
    public static void savePortletPage(PortletPage pc, String descriptorPath, String mappingPath) throws IOException, PersistenceManagerException {
        PersistenceManagerXml pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorPath, mappingPath);
        pmXML.save(pc);
    }

    /**
     * Saves the portlet page associated with this descriptor
     *
     * @param pc             the portlet container to load
     * @param descriptorPath location of the layout.xml
     * @param mappingPath    location of the mapping file
     * @throws IOException                 if an I/O error occurs
     * @throws PersistenceManagerException if a descriptor error occurs
     */
    public static void savePortletPage(PortletPage pc, String descriptorPath, URL mappingPath) throws IOException, PersistenceManagerException {
        PersistenceManagerXml pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorPath, mappingPath);
        pmXML.save(pc);
    }

    /**
     * Validates the portlet page associated with this descriptor
     *
     * @param descriptorPath location of the layout.xml
     * @param mappingPath    location of the mapping file
     */
    public static void validatePortletPage(String descriptorPath, String mappingPath) {
        JavaXMLBindingFactory.createPersistenceManagerXml(descriptorPath, mappingPath);
    }

    /**
     * Saves the portlet tab associated with this descriptor
     *
     * @param pane           the list of portlet tabs to save
     * @param descriptorPath location of the layout.xml
     * @param mappingPath    location of the mapping file
     * @throws IOException                 if an I/O error occurs
     */
    public static void savePortletTabbedPane(PortletTabbedPane pane, String descriptorPath, String mappingPath) throws IOException, PersistenceManagerException {
        PersistenceManagerXml pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorPath, mappingPath);
        pmXML.save(pane);
    }

    /**
     * Saves the portlet tab associated with this descriptor
     *
     * @param pane           the list of portlet tabs to save
     * @param descriptorPath location of the layout.xml
     * @param mappingPath    location of the mapping file
     * @throws IOException                 if an I/O error occurs
     * @throws PersistenceManagerException if a descriptor error occurs
     */
    public static void savePortletTabbedPane(PortletTabbedPane pane, String descriptorPath, URL mappingPath) throws IOException, PersistenceManagerException {
        PersistenceManagerXml pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorPath, mappingPath);
        pmXML.save(pane);
    }

}
