/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.jsrimpl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.jsrimpl.JSRApplicationPortletImpl;

import javax.portlet.PreferencesValidator;
import javax.portlet.PortletPreferences;

/**
 * The <code>PortletPreferencesManager</code> provides a a singleton implementation of the <code>PortletDataManager</code>
 * used for loading and storing <code>PortletData</code>.
 */
public class PortletPreferencesManager {

    private static PortletLog log = SportletLog.getInstance(PortletPreferencesManager.class);
    private static PersistenceManagerRdbms pm = null;
    private PreferencesValidator validator = null;
    private boolean isRender = false;
    private String userId = null;
    private String portletId = null;
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences prefsDesc = null;
    private PortletPreferencesImpl prefs = null;

    public PortletPreferencesManager(JSRApplicationPortletImpl appPortlet, User user, boolean isRender) {
        pm = PersistenceManagerFactory.createGridSphereRdbms();
        validator = appPortlet.getPreferencesValidator();
        portletId = appPortlet.getApplicationPortletID();
        this.prefsDesc = appPortlet.getPreferencesDescriptor();
        this.isRender = isRender;
        if (user == null) {
            userId = PortletPreferencesImpl.NO_USER;
        } else {
            userId = user.getID();
        }
    }

    /**
     * Returns the users portlet data for the specified portlet
     *
     * @return the PortletPreferences for this portlet or null if none exists.
     */
    public javax.portlet.PortletPreferences getPortletPreferences() {
        if (prefs == null) {
            prefs = createPortletPreferences();
        }
        return prefs;
    }

    private PortletPreferencesImpl createPortletPreferences() {
        try {
            String command =
                    "select u from " + PortletPreferencesImpl.class.getName() + " u where u.userId='" + userId + "' and u.portletId='" + portletId + "'";
            prefs = (PortletPreferencesImpl) pm.restore(command);
            if (prefs == null) {
                // we have no prefs in the db so create one from the xml...
                log.debug("No prefs exist-- storing prefs for user: " + userId + " portlet: " + portletId);
                prefs = new PortletPreferencesImpl();
                prefs.setPortletId(portletId);
                prefs.setUserId(userId);
            } else {
                log.debug("Retrieved prefs for user: " + userId + " portlet: " + portletId);
            }
            prefs.setPersistenceManager(pm);
            if (prefsDesc != null) prefs.setPreferencesDesc(prefsDesc);
            if (validator != null) prefs.setValidator(validator);
            prefs.setRender(isRender);
        } catch (Exception e) {
            log.error("Error attempting to restore persistent preferences: ", e);
        }
        return prefs;
    }

}
