/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id: PortletPreferencesManager.java 5412 2006-09-28 23:44:53Z novotny $
 */
package org.gridsphere.portletcontainer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portlet.impl.PortletPreferencesImpl;
import org.gridsphere.portletcontainer.PortletPreferencesManager;
import org.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridsphere.services.core.persistence.PersistenceManagerService;

import javax.portlet.PreferencesValidator;

/**
 * The <code>PortletPreferencesManager</code> provides a a singleton implementation of the <code>PortletDataManager</code>
 * used for loading and storing <code>PortletData</code>.
 */
public class PortletPreferencesManagerImpl implements PortletPreferencesManager {

    private Log log = LogFactory.getLog(PortletPreferencesManagerImpl.class);

    private PersistenceManagerRdbms pm = null;

    private org.gridsphere.portletcontainer.impl.descriptor.PortletPreferences prefsDesc = null;
    private PreferencesValidator validator = null;
    private String userId = null;
    private String portletId = null;
    private boolean isRender = true;

    public PortletPreferencesManagerImpl(org.gridsphere.portletcontainer.impl.descriptor.PortletPreferences prefsDesc, PreferencesValidator validator) {
        PersistenceManagerService pms = (PersistenceManagerService)PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pm = pms.createGridSphereRdbms();
        this.prefsDesc = prefsDesc;
        this.validator = validator;
    }

    public void setPortletId(String portletId) {
        this.portletId = portletId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRender(boolean isRender) {
        this.isRender= isRender;
    }


    /**
     * Returns the users portlet data for the specified portlet
     *
     * @return the PortletPreferences for this portlet or null if none exists.
     */
    public javax.portlet.PortletPreferences getPortletPreferences() {

        PortletPreferencesImpl prefs = null;
        if (userId == null) {
            userId = PortletPreferencesImpl.NO_USER;
        }

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