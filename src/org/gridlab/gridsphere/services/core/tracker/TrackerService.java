package org.gridlab.gridsphere.services.core.tracker;

import org.gridlab.gridsphere.portlet.service.PortletService;

public interface TrackerService extends PortletService {

    public static final String TRACK_PARAM = "gs.trackme";
    public static final String REDIRECT_URL = "gs.url";

    /**
     * Persists a tracking label to the GridSphere database
     *
     * @param label a label used in identifying the action invoked
     * @param userAgent the user agent string provided by the web browser
     * @param userOid the user oid
     */ 
    public void trackURL(String label, String userAgent, String userOid);


}
