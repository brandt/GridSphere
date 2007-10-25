/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.portlet.jsr.mvc.descriptor;

import org.gridsphere.portletcontainer.impl.JavaXMLBindingFactory;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerXml;

import java.net.URL;

/**
 * The <code>AuthModulesDescriptor</code> 
 */
public class ActionPortletsDescriptor {

    private ActionPortletCollection actionPortlets = null;
    private PersistenceManagerXml pmXML = null;

    /**
     * Constructor disallows non-argument instantiation
     */
    private ActionPortletsDescriptor() {
    }

    public ActionPortletsDescriptor(String descriptorFile) throws PersistenceManagerException {
        URL mappingFile = getClass().getResource("/org/gridsphere/provider/portlet/jsr/mvc/descriptor/portlet-services-mapping.xml");
        pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(descriptorFile, mappingFile);
        actionPortlets = (ActionPortletCollection) pmXML.load();
    }


    public ActionPortletCollection getActionPortletCollection() {
        return actionPortlets;
    }


    public void setActionPortletCollection(ActionPortletCollection actionPortlets) {
        this.actionPortlets = actionPortlets;
    }

    /**
     * Saves the auth module descriptor
     *
     * @throws PersistenceManagerException if a Castor error occurs during the marshalling
     */
    public void save() throws PersistenceManagerException {
        pmXML.save(actionPortlets);
    }

}
