/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbmsImpl;
import org.gridlab.gridsphere.portlet.GuestUser;
import org.gridlab.gridsphere.portlet.PortletData;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletData;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.PortletDataManager;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;

/**
 * The <code>SportletDataManager</code> provides a a singleton implementation of the <code>PortletDataManager</code>
 * used for loading and storing <code>PortletData</code>.
 */
public class SportletDataManager implements PortletDataManager {

    private static transient PortletLog log = SportletLog.getInstance(PortletDataManager.class);
    private PortletRegistry registry = PortletRegistry.getInstance();
    //private static PersistenceManagerRdbms pm = null;
    //PersistenceManagerFactory.createGridSphereRdbms();
    private static PortletDataManager instance = new SportletDataManager();

    /**
     * Default instantiation is disallowed
     */
    private SportletDataManager() {
    }

    /**
     * Returns an instance of a <code>PortletDataManager</code>
     *
     * @return an instance of a <code>PortletDataManager</code>
     */
    public static PortletDataManager getInstance() {
        return instance;
    }

    /**
     * Returns the users portlet data for the specified portlet
     *
     * @param user the <code>User</code>
     * @param portletID the concrete portlet id
     * @return the PortletData for this portlet or null if none exists.
     */
    public PortletData getPortletData(User user, String portletID) throws PersistenceManagerException {

        String appID = PortletRegistry.getApplicationPortletID(portletID);
        System.err.println("appID: " + appID);
        PersistenceManagerRdbms pm = null;
        if (appID != null) {
            ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
            String webApp = appPortlet.getWebApplicationName();
            System.err.println("webapp: " + webApp);
            pm = PersistenceManagerFactory.createProjectPersistenceManagerRdbms(webApp);
        }
        if (user instanceof GuestUser) return null;

        String command =
                "select u from " + SportletData.class.getName() + " u where u.UserID=\"" + user.getID() + "\" and u.PortletID=\"" + portletID + "\"";

        // get sportlet data if it exists
        SportletData pd = (SportletData) pm.restore(command);

        // or create one
        if (pd == null) {
            pd = new SportletData(pm);
            pd.setPortletID(portletID);
            pd.setUserID(user.getID());
            pm.create(pd);
        }
        return pd;
    }

    /**
     * Makes the users persistent portlet data persistent
     *
     * @param user the <code>User</code>
     * @param portletID the concrete portlet id
     * @param data the PortletData
     */
    public void setPortletData(User user, String portletID, PortletData data) throws PersistenceManagerException {
        String appID = PortletRegistry.getApplicationPortletID(portletID);
        String webApp = registry.getApplicationPortlet(appID).getWebApplicationName();
        PersistenceManagerRdbms pm = PersistenceManagerFactory.createProjectPersistenceManagerRdbms(webApp);
        SportletData sd = (SportletData) data;
        //sd.setPortletID(portletID);
        //sd.setUserID(user.getID());

            pm.update(sd);

    }
}
