package org.gridsphere.services.core.persistence;

import org.gridsphere.portlet.service.PortletService;

/**
 * @author <a href="mailto:jnovotny@ucsd.edu">Jason Novotny</a>
 * @version $Id$
 */
public interface PersistenceManagerService extends PortletService {

    public PersistenceManagerRdbms createGridSphereRdbms();

    public PersistenceManagerRdbms createPersistenceManagerRdbms(String webappname);

    public void destroyPersistenceManagerRdbms(String webappname);

}
