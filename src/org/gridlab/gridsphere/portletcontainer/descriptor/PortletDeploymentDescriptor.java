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
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;

import javax.servlet.ServletConfig;
import java.util.Iterator;
import java.util.Vector;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PortletDeploymentDescriptor {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletDeploymentDescriptor.class);
    private PersistenceManagerXml pmx = PersistenceManagerXml.getInstance();
    private Vector PortletDef = new Vector();
    private PortletCollection pc = null;

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
    public Vector getPortletDef() {
        return PortletDef;
    }

    /**
     * Returns a list of PortletApps
     *
     * @return a list of PortletApps
     */
    public void setPortletDef(Vector defs) {
        PortletDef = defs;
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
                String uid = capp.getID();
                if (concretePortletID.equals(uid)) {
                    return capp;
                }
            }
        }
        return null;
    }

    public void setConcretePortletApplication(ConcretePortletApplication concApp) {
        if (getConcretePortletApplication(concApp.getID()) == null) {
            // see if an application portlet exists
            Vector defList = pc.getPortletDefList();
            Iterator it = defList.iterator();
            while (it.hasNext()) {
                PortletDefinition pd = (PortletDefinition)it.next();
                Vector apps = pd.getConcreteApps();
                PortletApp pApp = pd.getPortletApp();
                if (concApp.getID().startsWith(pApp.getID())) {
                    apps.add(concApp);
                    defList.remove(pd);
                    pd.setConcreteApps(apps);
                    defList.add(pd);
                    pc.setPortletDefList(defList);
                }
            }
        }
    }

    /**
     * Return the portlet application associated with the
     *
     * @param concretePortletID the concrete portlet ID
     * @return the corresponding PortletApplication or null if none exists
     */
    public PortletApp getPortletAppDescriptor(String concretePortletID) {
        Iterator it = PortletDef.iterator();
        while (it.hasNext()) {
            PortletDefinition pd = (PortletDefinition)it.next();
            PortletApp app = pd.getPortletApp();
            String uid = app.getID();
            if (concretePortletID.startsWith(uid)) {
                return app;
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
    public void setPortletAppDescriptor(PortletApp portletApp) {
        if (getPortletAppDescriptor(portletApp.getID()) == null) {
            PortletDefinition pd = new PortletDefinition();
            Vector defList = pc.getPortletDefList();
            defList.add(pd);
            pc.setPortletDefList(defList);
        }
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
            log.error("Unable to load portlet.xml: ("+pmx.getMappingFile()+", "+pmx.getConnectionURL()+") ", e);
            throw new PortletDeploymentDescriptorException("Unable to load file " + e.getMessage());
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
