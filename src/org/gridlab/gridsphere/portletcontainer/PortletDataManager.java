/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletData;
import org.gridlab.gridsphere.portlet.User;

public interface PortletDataManager {
    /**
 * Returns the users portlet data for the specified portlet
 *
 * @param User the user
 * @param portletID the concrete portlet id
 * @return the PortletData for this portlet or null if none exists.
 */
    PortletData getPortletData(User user, String portletID);

    /**
     * Makes the users persistent portlet data persistent
     *
     * @param User the user
     * @param portletID the concrete portlet id
     * @param data the PortletData
     */
    void setPortletData(User user, String portletID, PortletData data);
}
