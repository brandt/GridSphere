/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.GuestUser;
import org.gridlab.gridsphere.portlet.PortletData;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletData;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.PortletDataManager;

public class SportletDataManager implements PortletDataManager {

    private static PortletLog log = SportletLog.getInstance(PortletDataManager.class);
    private static PersistenceManagerRdbms pm = PersistenceManagerRdbms.getInstance();
    private static PortletDataManager instance = new SportletDataManager();

    private SportletDataManager() {
    }

    public static PortletDataManager getInstance() {
        return instance;
    }

    /**
     * Returns the users portlet data for the specified portlet
     *
     * @param User the user
     * @param portletID the concrete portlet id
     * @return the PortletData for this portlet or null if none exists.
     */
    public PortletData getPortletData(User user, String portletID) {

        if (user instanceof GuestUser) return null;

        String command =
                "select u from " + SportletData.class.getName() + " u where u.UserID=\"" + user.getID() + "\" and u.PortletID=\"" + portletID + "\"";

        SportletData pd = null;
        try {
            pd = (SportletData) pm.restoreObject(command);
        } catch (PersistenceManagerException e) {

        }

        if (pd == null) {
            pd = new SportletData();
            pd.setPortletID(portletID);
            pd.setUserID(user.getID());
            try {
                pm.create(pd);
            } catch (PersistenceManagerException e) {

            }
        }
        return pd;
    }

    /**
     * Makes the users persistent portlet data persistent
     *
     * @param User the user
     * @param portletID the concrete portlet id
     * @param data the PortletData
     */
    public void setPortletData(User user, String portletID, PortletData data) {

        SportletData sd = (SportletData) data;
        //sd.setPortletID(portletID);
        //sd.setUserID(user.getID());
        try {
            pm.update(sd);
        } catch (PersistenceManagerException e) {
            log.error("Persistence Exception !" + e);
        }
    }
}
