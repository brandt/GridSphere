/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portletcontainer.impl;

import org.gridsphere.portlet.service.spi.impl.descriptor.PortletServiceCollection;
import org.gridsphere.portlet.service.spi.impl.descriptor.PortletServiceDefinition;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerXml;

import java.net.URL;
import java.util.List;

/**
 * The <code>SportletServiceDescriptor</code> provides the portlet service
 * descriptor class mappings that are used by Castor to unmarshall the
 * services descriptor file.
 */
public class PortletServiceDescriptor {

    private PortletServiceCollection services = null;
    private PersistenceManagerXml pmXML = null;


    private URL servicesMappingStream = getClass().getResource("/org/gridsphere/portlet/service/spi/impl/portlet-services-mapping.xml");

    /**
     * Constructor disallows non-argument instantiation
     */
    private PortletServiceDescriptor() {}

    public PortletServiceDescriptor(String descriptorFile) throws PersistenceManagerException {
        PersistenceManagerXml pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorFile, servicesMappingStream);
        services = (PortletServiceCollection) pmXML.load();
    }

    /**
     * Returns the collection of portlet service definitions
     *
     * @return the collection of portlet service definitions
     */
    public PortletServiceCollection getServiceCollection() {
        return services;
    }

    /**
     * Sets the collection of portlet service definitions
     *
     * @param services the collection of portlet service definitions
     */
    public void setServiceCollection(PortletServiceCollection services) {
        this.services = services;
    }

    /**
     * Sets the service definition
     *
     * @param definition the service definition
     */
    public void setServiceDefinition(PortletServiceDefinition definition) {
        List<PortletServiceDefinition> serviceDefs = services.getPortletServicesList();
        for (PortletServiceDefinition def : serviceDefs) {
            if (definition.getServiceName().equals(def.getServiceName())) {
                def.setConfigParamList(definition.getConfigParamList());
            }
        }
    }

    /**
     * Saves the portlet service descriptor
     *
     * @throws PersistenceManagerException if a Castor error occurs during the marshalling
     */
    public void save() throws PersistenceManagerException {
        pmXML.save(services);
    }

}
