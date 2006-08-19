/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SportletServiceDescriptor.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer.impl;

import org.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceCollection;
import org.gridsphere.portlet.service.spi.impl.descriptor.SportletServiceDefinition;
import org.gridsphere.services.core.persistence.PersistenceManagerXml;
import org.gridsphere.services.core.persistence.PersistenceManagerException;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.net.URL;

/**
 * The <code>SportletServiceDescriptor</code> provides the portlet service
 * descriptor class mappings that are used by Castor to unmarshall the
 * services descriptor file.
 */
public class PortletServiceDescriptor {

    private SportletServiceCollection services = null;
    private PersistenceManagerXml pmXML = null;


    private URL servicesMappingStream = getClass().getResource("/org/gridsphere/portlet/service/spi/impl/portlet-services-mapping.xml");

    /**
     * Constructor disallows non-argument instantiation
     */
    private PortletServiceDescriptor() {}

    public PortletServiceDescriptor(String descriptorFile) throws IOException, PersistenceManagerException {
        PersistenceManagerXml pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorFile, servicesMappingStream);
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
