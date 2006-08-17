package org.gridlab.gridsphere.services.core.tracker.impl;

import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.tracker.TrackerService;
import org.gridlab.gridsphere.services.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridlab.gridsphere.services.core.persistence.PersistenceManagerException;

import java.util.Calendar;
import java.util.List;

public class TrackerServiceImpl implements TrackerService, PortletServiceProvider {

    private PersistenceManagerRdbms pm = null;

    private PortletLog log = SportletLog.getInstance(TrackerServiceImpl.class);

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        PersistenceManagerService pmservice = (PersistenceManagerService) PortletServiceFactory.createPortletService(PersistenceManagerService.class, true);
        pm = pmservice.createGridSphereRdbms();
    }

    public void destroy() {

    }

    /**
     * Persists a tracking label to the GridSphere database
     *
     * @param label a label used in identifying the action invoked
     * @param userAgent the user agent string provided by the web browser
     * @param userName the user name
     */
    public void trackURL(String label, String userAgent, String userName) {
        TrackerInfo info = new TrackerInfo();
        info.setLabel(label);
        info.setDate(Calendar.getInstance().getTime().getTime());
        info.setUserAgent(userAgent);
        info.setUserName(userName);
        try {
            pm.saveOrUpdate(info);
        } catch (PersistenceManagerException e) {
            log.error("Unable to save tracker info", e);
        }
    }

    public List getTrackingActions() {
        List actions = null;
        try {
            actions = pm.restoreList("from " + TrackerAction.class.getName() + " as trackeraction");
        } catch (PersistenceManagerException e) {
            log.error("Unable to retrieve tracker actions");
        }
        return actions;
    }

    public TrackerAction getTrackingAction(String actionName) {
        TrackerAction action = null;
        try {
            action = (TrackerAction)pm.restore("from " + TrackerAction.class.getName() + " as trackeraction where trackeraction.Action=\'" + actionName + "'");
        } catch (PersistenceManagerException e) {
            log.error("Unable to retrieve tracker actions");
        }
        return action;
    }

    public void addTrackingAction(TrackerAction action) {
        try {
            pm.saveOrUpdate(action);
        } catch (PersistenceManagerException e) {
            log.error("Unable to save new tracker action: " + action);
        }
    }

    public void removeTrackingAction(String action) {
        try {
            TrackerAction trackerAction = getTrackingAction(action);
            if (trackerAction != null) pm.delete(trackerAction);
        } catch (PersistenceManagerException e) {
            log.error("Unable to delete tracker action: " + action);
        }
    }

    public void clearTrackingActions() {
        try {
            pm.deleteList("from " + TrackerAction.class.getName() + " as trackeraction ");
        } catch (PersistenceManagerException e) {
            log.error("Unable to clear tracker actions");
        }
    }

    /**
     * Return a list of the available labels
     *
     * @return a list of the available labels
     */
    public List getTrackingLabels() {
        List result = null;
        try {
            result = pm.restoreList("select tracker.Label from " +  TrackerInfo.class.getName() + " as tracker");
        } catch (PersistenceManagerException e) {
         log.error("Could not retrieve labels :"+e);
        }
        return result;
    }

    /**
     * Return a list of TrackerInfo objects for the provided label
     *
     * @return a list of TrackerInfo objects for the provided label
     */
    public List getTrackingInfoByLabel(String label) {
        return queryDB("where tracker.Label='" + label + "'");
    }

    private List queryDB(String condition) {
        List result = null;
        try {
            // try to get the address
            result = pm.restoreList("from " + TrackerInfo.class.getName() + " as tracker " + condition);
        } catch (PersistenceManagerException e) {
            log.error("Could not retrieve info :"+e);
        }

        return result;
    }

    public boolean hasTrackingAction(String actionName) {
        TrackerAction ta = getTrackingAction(actionName);
        return ((ta != null) && (ta.isEnabled()));
    }
}
