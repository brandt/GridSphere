/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl.descriptor;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortletConfig;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.impl.ConcreteSportlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  The <code>PortletDeploymentDescriptor</code> is responsible for
 * marshalling/unmarshalling the XML portlet schema represntation and the
 * associated Java classes using Castor.
 */
public class PortletDeploymentDescriptor implements Cloneable {

    private PersistenceManagerXml pmXML = null;
    private SportletCollection sportletCollection = null;

    /**
     * Constructs a PortletDeploymentDescriptor from a portlet.xml and mapping file
     *
     * @param portletFilePath location of the portlet.xml
     * @param mappingFilePath location of the mapping file
     * @throws PersistenceManagerException if the PortletDeploymentPersistenceManager cannot be created
     */
    public PortletDeploymentDescriptor(String portletFilePath, String mappingFilePath) throws IOException, PersistenceManagerException {
        pmXML = PersistenceManagerFactory.createPersistenceManagerXml(portletFilePath, mappingFilePath);
        sportletCollection = (SportletCollection) pmXML.load();
    }

    /**
     * Returns a list of portlet definitions
     *
     * @return a list of portlet definitions
     */
    public List getPortletDefinitionList() {
        return sportletCollection.getPortletDefinitionList();
    }

    /**
     * Returns a list of portlet definitions
     *
     * @param defs a list of portlet definitions
     */
    public void setPortletDefinitionList(ArrayList defs) {
        sportletCollection.setPortletDefinitionList(defs);
    }

    /**
     * Sets the concrete portlet information
     *
     * @param concPortlet the <code>ConcretePortlet</code>
     */
    public void setConcreteSportlet(ConcreteSportlet concPortlet) {
        String concID = concPortlet.getConcretePortletID();
        // see if an application portlet exists
        List defList = sportletCollection.getPortletDefinitionList();
        Iterator it = defList.iterator();
        while (it.hasNext()) {
            SportletDefinition portletDef = (SportletDefinition) it.next();
            ApplicationSportletConfig portletAppConfig = portletDef.getApplicationSportletConfig();
            if (concID.startsWith(portletAppConfig.getApplicationPortletID())) {
                List concSportletsList = portletDef.getConcreteSportletList();
                Iterator concIt = concSportletsList.iterator();
                while (concIt.hasNext()) {
                    ConcreteSportletDefinition concSportlet = (ConcreteSportletDefinition) concIt.next();
                    if (concID.equals(concSportlet.getConcretePortletID())) {
                        concSportlet.setContextAttributes(concPortlet.getContextAttributes());
                        concSportlet.setConcreteSportletConfig((ConcreteSportletConfig)concPortlet.getConcretePortletConfig());
                    }
                }
            }
        }
    }

    public void save() throws IOException, PersistenceManagerException {
        pmXML.save(sportletCollection);
    }
}
