/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.jsrimpl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.GuestUser;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.jsrimpl.PortletPreferencesImpl;

import javax.portlet.PortletPreferences;

/**
 * The <code>PortletPreferencesManager</code> provides a a singleton implementation of the <code>PortletDataManager</code>
 * used for loading and storing <code>PortletData</code>.
 */
public class PortletPreferencesManager {

    private static PersistenceManagerRdbms pm = PersistenceManagerFactory.createGridSphereRdbms();
    private static PortletPreferencesManager instance = new PortletPreferencesManager();

    /**
     * Default instantiation is disallowed
     */
    private PortletPreferencesManager() {
    }

    /**
     * Returns an instance of a <code>PortletDataManager</code>
     *
     * @return an instance of a <code>PortletDataManager</code>
     */
    public static PortletPreferencesManager getInstance() {
        return instance;
    }

    /**
     * Returns the users portlet data for the specified portlet
     *
     * @param defaultPreferences a default portlet preferences obtained from the portlet descriptor
     * @param user the <code>User</code>
     * @param portletID the concrete portlet id
     * @return the PortletPreferences for this portlet or null if none exists.
     */
    public PortletPreferences getPortletPreferences(PortletPreferences defaultPreferences, User user, String portletID) {

        if (user instanceof GuestUser) return null;

        String command =
                "select u from " + PortletPreferencesImpl.class.getName() + " u where u.UserID='" + user.getID() + "' and u.PortletID='" + portletID + "'";

        // get sportlet data if it exists
        PortletPreferencesImpl prefs = null;
        try {
            prefs = (PortletPreferencesImpl) pm.restore(command);

            // or create one
            if (prefs == null) {
                prefs = new PortletPreferencesImpl(pm);
                prefs.setPortletID(portletID);
                prefs.setUserID(user.getID());
                pm.create(prefs);
            }
        } catch (Exception e) {
            return defaultPreferences;
        }
        return prefs;
    }

    public void setPortletPreferences(PortletPreferences prefs) {

    }

}
