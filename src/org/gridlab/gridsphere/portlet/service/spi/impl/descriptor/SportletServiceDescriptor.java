/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.Descriptor;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;

import java.io.IOException;

/**
 * The <code>SportletServiceDescriptor</code> provides the portlet service
 * descriptor class mappings that are used by Castor to unmarshall the
 * services descriptor file.
 */
public class SportletServiceDescriptor extends Descriptor {

    private SportletServiceCollection services = null;

    private String descriptorPath, mappingPath;

    /**
     * Constructor disallows non-argument instantiation
     */
    private SportletServiceDescriptor() {
    }

    public SportletServiceDescriptor(String descriptorFile, String mappingFile) throws IOException, DescriptorException {
        services = (SportletServiceCollection) load(descriptorFile, mappingFile);
    }

    /**
     * Returns the collection of portlet service definitions
     *
     * @return the collection of portlet service definitions
     */
    public SportletServiceCollection getServiceCollection() {
        return services;
    }

    /**
     * Saves the portlet service descriptor
     *
     * @throws IOException if an I/O error occurs
     * @throws DescriptorException if a Castor error occurs during the marshalling
     */
    public void save() throws IOException, DescriptorException {
        save(descriptorPath, mappingPath, services);
    }

}
