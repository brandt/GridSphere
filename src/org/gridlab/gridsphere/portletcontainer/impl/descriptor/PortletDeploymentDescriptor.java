/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl.descriptor;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.Descriptor;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortletConfig;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  The <code>PortletDeploymentDescriptor</code> is responsible for
 * marshalling/unmarshalling the XML portlet schema represntation and the
 * associated Java classes using Castor.
 */
public class PortletDeploymentDescriptor extends Descriptor {

    private SportletCollection sportletCollection = null;
    private String mappingFilePath = null;
    private String portletFilePath = null;

    /**
     * Constructs a PortletDeploymentDescriptor from a portlet.xml and mapping file
     *
     * @param portletFilePath location of the portlet.xml
     * @param mappingFilePath location of the mapping file
     * @throws DescriptorException if the PortletDeploymentDescriptor cannot be created
     */
    public PortletDeploymentDescriptor(String portletFilePath, String mappingFilePath) throws IOException, DescriptorException {
        this.mappingFilePath = mappingFilePath;
        this.portletFilePath = portletFilePath;
        sportletCollection = (SportletCollection) load(portletFilePath, mappingFilePath);
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
     * Returns a list of portelt definitions
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
    public void setConcretePortlet(ConcretePortlet concPortlet) {
        String concID = concPortlet.getConcretePortletID();
        // see if an application portlet exists
        List defList = sportletCollection.getPortletDefinitionList();
        Iterator it = defList.iterator();
        while (it.hasNext()) {
            SportletDefinition portletDef = (SportletDefinition) it.next();
            ApplicationPortletConfig portletAppConfig = portletDef.getApplicationSportletConfig();
            if (concID.startsWith(portletAppConfig.getApplicationPortletID())) {
                List concSportletsList = portletDef.getConcreteSportletList();
                Iterator concIt = concSportletsList.iterator();
                while (concIt.hasNext()) {
                    ConcreteSportletDefinition concSportlet = (ConcreteSportletDefinition) concIt.next();
                    if (concID.equals(concSportlet.getConcretePortletID())) {
                        concSportlet.setContextAttributes(concPortlet.getContextAttributes());
                        concSportlet.setConcreteSportletConfig(concSportlet.getConcreteSportletConfig());
                    }
                }
            }
        }
    }

    public void save() throws IOException, DescriptorException {
        save(portletFilePath, mappingFilePath, sportletCollection);
    }
}
