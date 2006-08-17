/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.portlet.PortletData;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletData;
import org.gridlab.gridsphere.portletcontainer.PortletDataManager;
import org.gridlab.gridsphere.services.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridlab.gridsphere.services.core.persistence.PersistenceManagerRdbms;

/**
 * The <code>SportletDataManager</code> provides a a singleton implementation of the <code>PortletDataManager</code>
 * used for loading and storing <code>PortletData</code>.
 */
public class SportletDataManager implements PortletDataManager {

    private static PersistenceManagerRdbms pm = null;
    private static PortletDataManager instance = null;

    /**
     * Default instantiation is disallowed
     */
    private SportletDataManager() {
        PersistenceManagerService pmservice = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pm = pmservice.createGridSphereRdbms();
    }

    /**
     * Returns an instance of a <code>PortletDataManager</code>
     *
     * @return an instance of a <code>PortletDataManager</code>
     */
    public static synchronized PortletDataManager getInstance() {
        if (instance == null) {
            instance = new SportletDataManager();
        }
        return instance;
    }

    /**
     * Returns the users portlet data for the specified portlet
     *
     * @param user      the <code>User</code>
     * @param portletID the concrete portlet id
     * @return the PortletData for this portlet or null if none exists.
     */
    public PortletData getPortletData(User user, String portletID) throws PersistenceManagerException {

        if (user == null) return null;

        String command =
                "select u from " + SportletData.class.getName() + " u where u.UserID='" + user.getID() + "' and u.PortletID='" + portletID + "'";

        // get sportlet data if it exists
        SportletData data = (SportletData) pm.restore(command);
        // or create one
        if (data == null) {
            data = new SportletData();
            data.setPortletID(portletID);
            data.setUserID(user.getID());
            data.setPersistenceManager(pm);
            pm.create(data);
        } else {
            data.setPersistenceManager(pm);
        }
        return data;
    }

}
