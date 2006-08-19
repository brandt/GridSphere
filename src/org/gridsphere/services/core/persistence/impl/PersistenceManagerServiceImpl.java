/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: PersistenceManagerFactory.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.persistence.impl;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerException;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PersistenceManagerServiceImpl implements PersistenceManagerService, PortletServiceProvider {

    protected transient PortletLog log = SportletLog.getInstance(PersistenceManagerServiceImpl.class);

    public static final String GRIDSPHERE_DATABASE_NAME = "gridsphere";

    protected static final Map databases = new HashMap();

    protected ServletContext context;

    public void init(PortletServiceConfig config) {
        context = config.getServletContext();
    }

    public PersistenceManagerRdbms createGridSphereRdbms() {
        String databaseName = GRIDSPHERE_DATABASE_NAME;
        if (!databases.containsKey(databaseName)) {
            PersistenceManagerRdbms pm = new PersistenceManagerRdbmsImpl(context);
            databases.put(databaseName, pm);
        }
        return (PersistenceManagerRdbms) databases.get(databaseName);
    }

    /**
     * Creates a new persistence manager.
     *
     * @param webappname
     */
    public PersistenceManagerRdbms createPersistenceManagerRdbms(String webappname) {
        if (!databases.containsKey(webappname)) {
            log.info("Creating new PM for :" + webappname);
            String path = context.getRealPath("../" + webappname + "/WEB-INF/persistence/");
            PersistenceManagerRdbms pm = new PersistenceManagerRdbmsImpl(path);
            databases.put(webappname, pm);
        }
        return (PersistenceManagerRdbms) databases.get(webappname);
    }

    public void destroyPersistenceManagerRdbms(String webappname) {
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

    public void destroy() {
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
