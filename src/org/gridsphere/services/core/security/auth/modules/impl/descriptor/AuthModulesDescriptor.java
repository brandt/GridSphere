/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.services.core.security.auth.modules.impl.descriptor;

import org.gridsphere.portletcontainer.impl.JavaXMLBindingFactory;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerXml;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * The <code>AuthModulesDescriptor</code> 
 */
public class AuthModulesDescriptor {

    private AuthModuleCollection authModules = null;
    private PersistenceManagerXml pmXML = null;

    /**
     * Constructor disallows non-argument instantiation
     */
    private AuthModulesDescriptor() {
    }

    public AuthModulesDescriptor(String descriptorFile, URL mappingFile) throws PersistenceManagerException {
        pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorFile, mappingFile);
        authModules = (AuthModuleCollection) pmXML.load();
    }

    /**
     * Returns the collection of auth module definitions
     *
     * @return the collection of auth module definitions
     */
    public AuthModuleCollection getCollection() {
        return authModules;
    }

    /**
     * Sets the collection of auth module definitions
     *
     * @param authModules the collection of auth module definitions
     */
    public void setCollection(AuthModuleCollection authModules) {
        this.authModules = authModules;
    }

    /**
     * Sets the auth module definition
     *
     * @param definition the auth module definition
     */
    public void setModuleDefinition(AuthModuleDefinition definition) {
        List serviceDefs = authModules.getAuthModulesList();
        Iterator it = serviceDefs.iterator();
        while (it.hasNext()) {
            AuthModuleDefinition def = (AuthModuleDefinition) it.next();
            if (definition.getModuleName().equals(def.getModuleName())) {
                def.setConfigParamList(definition.getConfigParamList());
            }
        }
    }

    /**
     * Saves the auth module descriptor
     *
     * @throws PersistenceManagerException if a Castor error occurs during the marshalling
     */
    public void save() throws PersistenceManagerException {
        pmXML.save(authModules);
    }

}
