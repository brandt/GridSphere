package org.gridsphere.services.core.persistence;

import org.gridsphere.portlet.service.PortletService;

import java.util.Collection;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface PersistenceManagerService extends PortletService {

    /**
     * Create the core GridSphere persistence manager
     *
     * @return the core GS PersistenceManager
     */
    public PersistenceManagerRdbms createGridSphereRdbms();

    /**
     * Creates a new persistence manager.
     *
     * @param webappname the webapp identifier for this PersistenceManager
     * @return the new PersistenceManager
     */
    public PersistenceManagerRdbms createPersistenceManagerRdbms(String webappname);

    /**
     * Destroys a persistence manager.
     *
     * @param webappname the webapp identifier for this PersistenceManager
     */
    public void destroyPersistenceManagerRdbms(String webappname);

    /**
     * Returns all persistence managers.
     *
     * @return all persistence managers.
     */
    public Collection<PersistenceManagerRdbms> getAllPersistenceManagerRdbms();

}
