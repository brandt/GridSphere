/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
     * Sets the collection of portlet service definitions
     *
     * @param services the collection of portlet service definitions
     */
    public void setServiceCollection(SportletServiceCollection services) {
        this.services = services;
    }

    /**
     * Sets the service definition
     *
     * @param definition the service definition
     */
    public void setServiceDefinition(SportletServiceDefinition definition) {
        List serviceDefs = services.getPortletServicesList();
        Iterator it = serviceDefs.iterator();
        while (it.hasNext()) {
            SportletServiceDefinition def = (SportletServiceDefinition) it.next();
            if (definition.getServiceName().equals(def.getServiceName())) {
                def.setConfigParamList(definition.getConfigParamList());
            }
        }
    }

    /**
     * Saves the portlet service descriptor
     *
     * @throws IOException                 if an I/O error occurs
     * @throws PersistenceManagerException if a Castor error occurs during the marshalling
     */
    public void save() throws IOException, PersistenceManagerException {
        pmXML.save(services);
    }

}
