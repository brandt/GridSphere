/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerXml;
import org.gridlab.gridsphere.core.persistence.PersistenceException;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import java.io.IOException;

public abstract class Descriptor {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(Descriptor.class);

    private String layoutPath, layoutMappingPath;

    public Descriptor() {}

    /**
     * Unmarshalls an object given a descriptor xml file and a corresponding mapping xml file
     *
     * @param descriptorPath the location of the descriptor xml file
     * @param mappingPath the location of the mapping xml file
     * @return Object the unmarshalled object
     * @throws DescriptorException if the Descriptor cannot be created
     */
    protected Object load(String descriptorPath, String mappingPath) throws IOException, DescriptorException  {

        Object object = null;
        PersistenceManagerXml pmx = PersistenceManagerXml.getInstance();

        // the descriptor xml file
        pmx.setConnectionURL(descriptorPath);

        // the mapping xml file
        pmx.setMappingFile(mappingPath);

        // try to get it
        try {
             object = pmx.restoreObject();
        } catch (PersistenceException e) {
            log.error("PersistenceException ("+pmx.getMappingFile()+", "+pmx.getConnectionURL()+") ", e);
            throw new DescriptorException("Unable to load descriptor: "+e.getMessage());
        }
        return object;
    }

    /**
     * Save the layout deployment descriptor to layout.xml
     * <b>not implemented yet</b>
     */
    protected void save(String descriptor, String mapping, Object object) throws IOException, DescriptorException {

        GridSphereConfig gsConfig = GridSphereConfig.getInstance();
        String descriptorPath = gsConfig.getProperty(descriptor);
        String mappingPath = gsConfig.getProperty(mapping);

        PersistenceManagerXml pmx = PersistenceManagerXml.getInstance();

        // where is the portlet.xml ?
        pmx.setConnectionURL(descriptorPath);

        // set the path to the mapping file
        pmx.setMappingFile(mappingPath);


        // try to get it
        try {
             pmx.update(object);
        } catch (PersistenceException e) {
            log.error("PersistenceException: ("+pmx.getMappingFile()+", "+pmx.getConnectionURL()+") ", e);
            throw new DescriptorException("Unable to save: "+e.getMessage());
        }
    }
}
