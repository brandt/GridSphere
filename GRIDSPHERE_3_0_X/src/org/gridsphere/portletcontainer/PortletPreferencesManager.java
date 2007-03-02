package org.gridsphere.portletcontainer;

import javax.portlet.PortletPreferences;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface PortletPreferencesManager {

    public void setPortletId(String portletId);

    public void setUserId(String userId);

    public void setRender(boolean isRender);

    public PortletPreferences getPortletPreferences();

}
