package org.gridsphere.services.core.tracker;

import org.gridsphere.portlet.service.PortletService;
import org.gridsphere.services.core.tracker.impl.TrackerAction;

import java.util.List;

public interface TrackerService extends PortletService {

    public static final String TRACK_PARAM = "gs.trackme";
    public static final String REDIRECT_URL = "gs.url";

    /**
     * Persists a tracking label to the GridSphere database
     *
     * @param label a label used in identifying the action invoked
     * @param userAgent the user agent string provided by the web browser
     * @param userName the user name
     */ 
    public void trackURL(String label, String userAgent, String userName);

    /**
     * Return a list of the available labels
     *
     * @return a list of the available labels
     */
    public List getTrackingLabels();

    public boolean hasTrackingAction(String actionName);

    /**
     * Return a list of TrackerInfo objects for the provided label
     *
     * @param label the label to retrieve tacking info on
     * @return a list of TrackerInfo objects for the provided label
     */
    public List getTrackingInfoByLabel(String label);

    public List getTrackingActions();

    public TrackerAction getTrackingAction(String actionName);

    public void removeTrackingAction(String action);

    public void clearTrackingActions();

    public void addTrackingAction(TrackerAction action);

}
