/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;

import java.io.IOException;

/**
 * The <code>PortletDeploymentDescriptor</code> is responsible for
 * marshalling/unmarshalling the XML portlet schema represntation and the
 * associated Java classes using Castor.
 */
public class PortletDeploymentDescriptor2 {

    private PersistenceManagerXml pmXML = null;
    private PortletApp portletApp = null;

    /**
     * Constructs a PortletDeploymentDescriptor from a portlet.xml and mapping file
     *
     * @param portletFilePath location of the portlet.xml
     * @param mappingFilePath location of the mapping file
     * @throws PersistenceManagerException if the PortletDeploymentPersistenceManager cannot be created
     */
    public PortletDeploymentDescriptor2(String portletFilePath, String mappingFilePath) throws IOException, PersistenceManagerException {
        pmXML = PersistenceManagerFactory.createPersistenceManagerXml(portletFilePath, mappingFilePath);
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


    public void save() throws IOException, PersistenceManagerException {
        pmXML.save(portletApp);
    }
}
