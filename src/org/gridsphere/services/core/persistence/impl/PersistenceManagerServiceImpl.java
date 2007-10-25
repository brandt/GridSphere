/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridsphere.services.core.persistence.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;

import javax.servlet.ServletContext;
import java.util.*;

public class PersistenceManagerServiceImpl implements PersistenceManagerService, PortletServiceProvider {

    protected transient Log log = LogFactory.getLog(PersistenceManagerServiceImpl.class);

    protected Map<String, PersistenceManagerRdbms> databases = new HashMap<String, PersistenceManagerRdbms>();

    protected PersistenceManagerRdbms gsDB = null;

    protected ServletContext context;

    public void init(PortletServiceConfig config) {
        context = config.getServletContext();
    }

    /**
     * Create the core GridSphere persistence manager
     *
     * @return the core GS PersistenceManager
     */
    public synchronized PersistenceManagerRdbms createGridSphereRdbms() {
        if (gsDB == null) {
            gsDB = new PersistenceManagerRdbmsImpl(context);
        }
        return gsDB;
    }

    /**
     * Creates a new persistence manager.
     *
     * @param webappname the webapp identifier for this PersistenceManager
     * @return the new PersistenceManager
     */
    public synchronized PersistenceManagerRdbms createPersistenceManagerRdbms(String webappname) {
        String path = context.getRealPath("../" + webappname + "/WEB-INF/persistence/");
        return createPersistenceManagerRdbms(webappname, path, path);
    }

    public synchronized PersistenceManagerRdbms createPersistenceManagerRdbms(String webappname, String pathTohibernateProperties, String pathToHibernateMappings) {
        if (!databases.containsKey(webappname)) {
            log.info("Creating new PM for :" + webappname);
            PersistenceManagerRdbms pm = new PersistenceManagerRdbmsImpl(pathTohibernateProperties,
                    pathToHibernateMappings);
            databases.put(webappname, pm);
        }
        return (PersistenceManagerRdbms) databases.get(webappname);
    }


    /**
     * Returns all persistence managers.
     *
     * @return all persistence managers.
     */
    public Collection<PersistenceManagerRdbms> getAllPersistenceManagerRdbms() {
        return databases.values();
    }

    /**
     * Return the persistence manager for the supplied webapp name
     *
     * @param webappname the webapp identifier for this PersistenceManager
     * @return the persistence manager for the supplied webapp name
     */
    public PersistenceManagerRdbms getPersistenceManagerRdbms(String webappname) {
        return databases.get(webappname);
    }

    /**
     * Destroys a persistence manager.
     *
     * @param webappname the webapp identifier for this PersistenceManager
     */
    public void destroyPersistenceManagerRdbms(String webappname) {
        if (databases.containsKey(webappname)) {
            try {
                PersistenceManagerRdbms pm = databases.get(webappname);
                log.info("Shutdown persistence manager for " + webappname);
                pm.destroy();
            } catch (PersistenceManagerException e) {
                log.error("Unable to destroy pm manager for: " + webappname, e);
            } finally {
                databases.remove(webappname);
            }
        }
    }

    /**
     * Destroys all persistence managers.
     */
    public void destroy() {
        log.info("Shutting down PersistenceManagers ");
        Set<String> allpms = databases.keySet();
        Iterator<String> it = allpms.iterator();
        while (it.hasNext()) {
            String pmname = it.next();
            PersistenceManagerRdbms pm = databases.get(pmname);
            log.info("  shutdown persistencemanager for " + pmname);
            try {
                pm.destroy();
            } catch (PersistenceManagerException e) {
                log.debug("Could not shutdown PersistenceManager " + pmname);
            } finally {
                it.remove();
            }
        }
        gsDB.destroy();
    }

}
