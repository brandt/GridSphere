/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.gridlab.gridsphere.core.persistence.ConfigurationException;
import org.gridlab.gridsphere.core.persistence.RestoreException;
import org.gridlab.gridsphere.core.persistence.PersistenceException;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerXml;
import org.gridlab.gridsphere.portlet.PortletLog;

import javax.servlet.ServletConfig;
import java.util.Iterator;
import java.util.Vector;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PortletDeploymentDescriptor {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletDeploymentDescriptor.class);
    private PersistenceManagerXml pmx = PersistenceManagerXml.getInstance();
    private Vector PortletDef = new Vector();
    private PortletCollection pc = null;

    public PortletDeploymentDescriptor(ServletConfig config) throws IOException, PortletDeploymentDescriptorException {
        // load in layout.xml file
        String appRoot = config.getServletContext().getRealPath("") + "/";
        String portletConfigFile = config.getInitParameter("portlet.xml");
        String portletMappingFile = config.getInitParameter("portlet-mapping.xml");
        if (portletConfigFile == null) {
            portletConfigFile = "WEB-INF/conf/portlet.xml";
        }
        if (portletMappingFile == null) {
            portletMappingFile = "WEB-INF/conf/portlet-mapping.xml";
        }
        String fullPath = appRoot + portletConfigFile;
        String mappingPath = appRoot + portletMappingFile;
        try {
            FileInputStream fistream = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            log.error("Can't find file: " + fullPath);
            throw new PortletDeploymentDescriptorException("Unable to create descriptor from " + fullPath + " using " + mappingPath);
        }
        load(fullPath, mappingPath);
    }

    /**
     * Constructs a PortletDeploymentDescriptor from a portlet.xml and mapping file
     *
     * @param portletFilePath location of the portlet.xml
     * @param mappingFilePath location of the mapping file
     * @throws PortletDeploymentDescriptorException if the PortletDeploymentDescriptor cannot be created
     */
    public PortletDeploymentDescriptor(String portletFilePath, String mappingFilePath) throws IOException, PortletDeploymentDescriptorException  {
        load(portletFilePath, mappingFilePath);
    }

    /**
     * Returns a list of PortletApps
     *
     * @return a list of PortletApps
     */
    public List getPortletDef() {
        return PortletDef;
    }

    /**
     * Return the portlet application associated with the
     *
     * @param concretePortletID the concrete portlet ID
     * @return the corresponding ConcretePortletApplication or null if none exists
     */
    public ConcretePortletApplication getConcretePortletApplication(String concretePortletID) {
        Iterator it = PortletDef.iterator();
        while (it.hasNext()) {
            PortletDefinition pd = (PortletDefinition)it.next();
            List apps = pd.getConcreteApps();
            Iterator appsIt = apps.iterator();
            while (appsIt.hasNext()) {
                ConcretePortletApplication capp = (ConcretePortletApplication)appsIt.next();
                String uid = capp.getUID();
                if (concretePortletID.equals(uid)) {
                    return capp;
                }
            }
        }
        return null;
    }

    /**
     * Return the portlet application associated with the
     *
     * @param concretePortletID the concrete portlet ID
     * @return the corresponding PortletApplication or null if none exists
     */
    public PortletApplication getPortletApplication(String concretePortletID) {
        Iterator it = PortletDef.iterator();
        while (it.hasNext()) {
            PortletDefinition pd = (PortletDefinition)it.next();
            PortletApplication app = pd.getPortletApp();
            String uid = app.getUID();
            if (concretePortletID.startsWith(uid)) {
                return app;
            }
        }
        return null;
    }


    /**
     * Loads the PortletDeploymentDescriptor from the portlet.xml
     *
     * @param url the location of the portlet.xml file
     * @param mapping the location of the portlet xml mapping file
     * @throws PortletDeploymentDescriptorException if the PortletDeploymentDescriptor cannot be created
     */
    public void load(String url, String mapping) throws IOException, PortletDeploymentDescriptorException  {

        // where is the portlet.xml ?
        pmx.setConnectionURL(url);

        // set the path to the mapping file
        pmx.setMappingFile(mapping);

        // try to get it
        try {
             pc = (PortletCollection)pmx.restoreObject();
        } catch (PersistenceException e) {
            log.error("Unable to load portlet.xml: ("+pmx.getMappingFile()+", "+pmx.getConnectionURL()+") "+e.getMessage());
            throw new PortletDeploymentDescriptorException("Unable to load portlet.xml: "+e.getMessage());
        }
        this.PortletDef = (Vector)pc.getPortletDefList();
    }

    /**
     * Save the portlet deployment descriptor to portlet.xml
     * <b>not implemented yet</b>
     */
    public void save() throws IOException, PersistenceException {
        pmx.update(pc);
    }

}
