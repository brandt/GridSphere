/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
package org.gridsphere.services.core.setup.modules.impl.descriptor;

import org.gridsphere.services.core.persistence.PersistenceManagerXml;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.portletcontainer.impl.JavaXMLBindingFactory;

import java.net.URL;
import java.util.List;
import java.util.Iterator;

/**
 * The <code>PortletsSetupModulesDescriptor</code> 
 */
public class PortletsSetupModulesDescriptor {

    private PortletsSetupModuleCollection portletsSetupModules = null;
    private PersistenceManagerXml pmXML = null;

    /**
     * Constructor disallows non-argument instantiation
     */
    private PortletsSetupModulesDescriptor() {
    }

    public PortletsSetupModulesDescriptor(String descriptorFile, String contextName, URL mappingFile) throws PersistenceManagerException {
        pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorFile, mappingFile);
        portletsSetupModules = (PortletsSetupModuleCollection) pmXML.load();
        Iterator portletsModulesSetupListIterator = portletsSetupModules.getPortletsSetupModulesList().iterator();
        while (portletsModulesSetupListIterator.hasNext()) {
            PortletsSetupModuleDefinition portletsSetupModuleDefinition =  (PortletsSetupModuleDefinition) portletsModulesSetupListIterator.next();
            portletsSetupModuleDefinition.setContextName(contextName);
        }
    }

    /**
     * Returns the collection of portlets setup module definitions
     *
     * @return the collection of portlets setup module definitions
     */
    public PortletsSetupModuleCollection getCollection() {
        return portletsSetupModules;
    }

    /**
     * Sets the collection of portlets setup module definitions
     *
     * @param portletsSetupModules the collection of portlets setup module definitions
     */
    public void setCollection(PortletsSetupModuleCollection portletsSetupModules) {
        this.portletsSetupModules = portletsSetupModules;
    }

    /**
     * Sets the portlets setup module definition
     *
     * @param definition the portlets setup module definition
     */
    public void setModuleDefinition(PortletsSetupModuleDefinition definition) {
        List serviceDefs = portletsSetupModules.getPortletsSetupModulesList();
        Iterator it = serviceDefs.iterator();
        while (it.hasNext()) {
            PortletsSetupModuleDefinition def = (PortletsSetupModuleDefinition) it.next();
            if (definition.getModuleName().equals(def.getModuleName())) {
                def.setConfigParamList(definition.getConfigParamList());
            }
        }
    }

    /**
     * Saves the portlets setup module descriptor
     *
     * @throws PersistenceManagerException if a Castor error occurs during the marshalling
     */
    public void save() throws PersistenceManagerException {
        pmXML.save(portletsSetupModules);
    }
}
