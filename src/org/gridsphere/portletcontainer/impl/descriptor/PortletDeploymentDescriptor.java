/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridsphere.portletcontainer.impl.descriptor;

import org.gridsphere.portletcontainer.impl.JavaXMLBindingFactory;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerXml;

import java.net.URL;

/**
 * The <code>PortletDeploymentDescriptor</code> is responsible for
 * marshalling/unmarshalling the XML portlet schema represntation and the
 * associated Java classes using Castor.
 */
public class PortletDeploymentDescriptor {

    private PersistenceManagerXml pmXML = null;
    private PortletApp portletApp = null;

    /**
     * Constructs a PortletDeploymentDescriptor from a portlet.xml and mapping file
     *
     * @param portletFilePath location of the portlet.xml
     * @throws PersistenceManagerException if the PortletDeploymentPersistenceManager cannot be created
     */
    public PortletDeploymentDescriptor(String portletFilePath) throws PersistenceManagerException {
        URL portletMappingStream = this.getClass().getResource("/org/gridsphere/portletcontainer/impl/descriptor/portlet-mapping.xml");
        pmXML = JavaXMLBindingFactory.createPersistenceManagerXml(portletFilePath, portletMappingStream);
        portletApp = (PortletApp) pmXML.load();
    }

    /**
     * Returns a list of portlet definitions
     *
     * @return a list of portlet definitions
     */
    public PortletDefinition[] getPortletDefinitionList() {
        return portletApp.getPortlet();
    }

    public PortletApp getPortletWebApplication() {
        return portletApp;
    }

    /**
     * Returns a list of portlet definitions
     *
     * @param portlets a list of portlet definitions
     */
    public void setPortletDefinitionList(PortletDefinition[] portlets) {
        this.portletApp.setPortlet(portlets);
    }


    public void save() throws PersistenceManagerException {
        pmXML.save(portletApp);
    }
}
