package org.gridlab.gridsphere.services.core.tracker.impl;

import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.tracker.TrackerService;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import java.util.Calendar;

public class TrackerServiceImpl implements TrackerService, PortletServiceProvider {

    private PersistenceManagerRdbms pm = null;

    private PortletLog log = SportletLog.getInstance(TrackerServiceImpl.class);

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        pm = PersistenceManagerFactory.createGridSphereRdbms();
    }

    public void destroy() {

    }

    /**
     * Persists a tracking label to the GridSphere database
     *
     * @param label a label used in identifying the action invoked
     * @param userAgent the user agent string provided by the web browser
     * @param userOid the user oid
     */
    public void trackURL(String label, String userAgent, String userOid) {
        TrackerInfo info = new TrackerInfo();
        info.setLabel(label);
        info.setDate(Calendar.getInstance().getTime().getTime());
        info.setUserAgent(userAgent);
        info.setUserOid(userOid);
        try {
            pm.saveOrUpdate(info);
        } catch (PersistenceManagerException e) {
            log.error("Unable to save tracker info", e);
        }
    }


}
