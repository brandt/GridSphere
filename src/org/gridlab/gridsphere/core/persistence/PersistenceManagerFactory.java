/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence;

import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerXmlImpl;
import org.gridlab.gridsphere.core.persistence.hibernate.PersistenceManagerRdbmsImpl;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Constructor;
import java.net.URL;

public class PersistenceManagerFactory {

    protected transient static PortletLog log = SportletLog.getInstance(PersistenceManagerFactory.class);

    public static final String GRIDSPHERE_DATABASE_NAME = "gridsphere";

    protected static final Map databases = new HashMap();

    private PersistenceManagerFactory() {
        log.debug("Entering PersistenceManagerFactory");
    }

    public static synchronized PersistenceManagerRdbms createGridSphereRdbms() {
        log.info("Trying to get Gridsphere PM!");
        String databaseName = GRIDSPHERE_DATABASE_NAME;
        if (!databases.containsKey(databaseName)) {
            PersistenceManagerRdbms pm = new PersistenceManagerRdbmsImpl();
            databases.put(databaseName, pm);
        }
        return (PersistenceManagerRdbms) databases.get(databaseName);
    }

    /**
     * Creates a new persistencemanager.
     *
     * @param webappname
     */
    public static synchronized PersistenceManagerRdbms createPersistenceManagerRdbms(String webappname) {
        ServletContext ctx = GridSphereConfig.getServletContext();
        String path = ctx.getRealPath("../" + webappname + "/WEB-INF/persistence/");
        return createPersistenceManagerRdbms(webappname, path, path);
    }

    public static synchronized PersistenceManagerRdbms createPersistenceManagerRdbms(String webappname, String pathTohibernateProperties, String pathToHibernateMappings) {
        if (!databases.containsKey(webappname)) {
            log.info("Creating new PM for :" + webappname);
            PersistenceManagerRdbms pm = new PersistenceManagerRdbmsImpl(pathTohibernateProperties, pathToHibernateMappings);
            databases.put(webappname, pm);
        }
        return (PersistenceManagerRdbms) databases.get(webappname);
    }

    public static synchronized void destroyPersistenceManagerRdbms(String webappname) {
        if (databases.containsKey(webappname)) {
            try {
                PersistenceManagerRdbms pm = (PersistenceManagerRdbms)databases.get(webappname);
                pm.destroy();
            } catch (PersistenceManagerException e) {
                log.error("Unable to destroy pm manager for: " + webappname, e);
            }
            databases.remove(webappname);       
        }
    }

    /**
     * Returns an instance of a PersistenceManagerXML from a descriptor and mapping URL
     *
     * @param descriptorURL the descriptor location
     * @param mappingURL    the mapping location
     * @return an instance of PersistenceManagerXmlImpl
     */
    public static PersistenceManagerXml createPersistenceManagerXml(String descriptorURL, String mappingURL) {
        return new PersistenceManagerXmlImpl(descriptorURL, mappingURL);
    }

    /**
     * Returns an instance of a PersistenceManagerXML from a descriptor and mapping URL
     *
     * @param descriptorURL the descriptor location
     * @param mappingURL    the mapping location
     * @return an instance of PersistenceManagerXmlImpl
     */
    public static PersistenceManagerXml createPersistenceManagerXml(String descriptorURL, URL mappingURL) {
        return new PersistenceManagerXmlImpl(descriptorURL, mappingURL);
    }

    /**
     * Returns an instance of a PersistenceManagerXML from a descriptor and mapping URL
     *
     * @param descriptorURL the descriptor location
     * @param mappingURL    the mapping location
     * @return an instance of PersistenceManagerXmlImpl
     */
    public static PersistenceManagerXml createPersistenceManagerXml(String descriptorURL, String mappingURL, ClassLoader classLoader) {
        PersistenceManagerXml pmXml = null;
        try {
            Class pmfClass = Class.forName(org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerXmlImpl.class.getName(), true, classLoader);
            Class[] pmfParamTypes = { String.class, String.class };
            Constructor constructor = pmfClass.getConstructor(pmfParamTypes);
            Object[] pmfParams = { descriptorURL, mappingURL };
            pmXml = (PersistenceManagerXml)constructor.newInstance(pmfParams);
        } catch (Exception e) {
            log.error("FATAL PMXML: Unable to deserialize ActionComponentTypes.xml", e);
        }
        return pmXml;
    }

    public static void shutdown() {
        log.info("Shutting down PersistenceManagers ");
        Set allpms = databases.keySet();
        Iterator it = allpms.iterator();
        while (it.hasNext()) {
            String pmname = (String) it.next();
            PersistenceManagerRdbms pm = (PersistenceManagerRdbms) databases.get(pmname);
            log.info("  shutdown persistencemanager for " + pmname);
            try {
                pm.destroy();
            } catch (PersistenceManagerException e) {
                log.debug("Could not shutdown PersistenceManager " + pmname);
            }
        }
    }

}
