/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;

import java.io.IOException;

/**
 * The <code>SportletServiceDescriptor</code> provides the portlet service
 * descriptor class mappings that are used by Castor to unmarshall the
 * services descriptor file.
 */
public class SportletServiceDescriptor {

    private SportletServiceCollection services = null;
    private PersistenceManagerXml pmXML = null;

    /**
     * Constructor disallows non-argument instantiation
     */
    private SportletServiceDescriptor() {
    }

    public SportletServiceDescriptor(String descriptorFile, String mappingFile) throws IOException, PersistenceManagerException {
        pmXML = PersistenceManagerFactory.createPersistenceManagerXml(descriptorFile, mappingFile);
        services = (SportletServiceCollection) pmXML.load();
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
     * @throws PersistenceManagerException if a Castor error occurs during the marshalling
     */
    public void save() throws IOException, PersistenceManagerException {
        pmXML.save(services);
    }

}
