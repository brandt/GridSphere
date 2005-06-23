package org.gridlab.gridsphere.services.core.tracker;

import org.gridlab.gridsphere.portlet.service.PortletService;

public interface TrackerService extends PortletService {

    public static final String TRACK_PARAM = "trackme";

    public void trackURL(String trackme, String userAgent, String userOid);


}
