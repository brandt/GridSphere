/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id: PortletPreferencesManager.java 5412 2006-09-28 23:44:53Z novotny $
 */
package org.gridsphere.portlet.jsrimpl;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.User;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portletcontainer.jsrimpl.JSRApplicationPortletImpl;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;

import javax.portlet.PreferencesValidator;

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
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences prefsDesc = null;
    private PortletPreferencesImpl prefs = null;

    public PortletPreferencesManager(JSRApplicationPortletImpl appPortlet, User user, boolean isRender) {
        PersistenceManagerService pms = (PersistenceManagerService)PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pm = pms.createGridSphereRdbms();
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