/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerXml;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.DescriptorException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.spi.impl.descriptor.Descriptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class PortletDeploymentDescriptor extends Descriptor {

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
    public PortletDeploymentDescriptor(String portletFilePath, String mappingFilePath) throws IOException, DescriptorException  {
        pc = (PortletCollection)load(portletFilePath, mappingFilePath);
    }

    /**
     * Returns a list of PortletApps
     *
     * @return a list of PortletApps
     */
    public Vector getPortletDef() {
        return pc.getPortletDefList();
    }

    /**
     * Returns a list of PortletApps
     *
     * @return a list of PortletApps
     */
    public void setPortletDef(Vector defs) {
        pc.setPortletDefList(defs);
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
     *
     */
    public void save(String portletFilePath, String mappingFilePath) throws IOException, DescriptorException {
        save(portletFilePath, mappingFilePath, pc);
    }

}
