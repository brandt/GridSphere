/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portlet.PortletWindow;

/**
 * The SportletRequest encapsulates the request sent by the client to the portlet.
 */
public interface SportletRequest extends PortletRequest {

    /**
     * Sets the mode that the portlet is running in.
     *
     * @param the portlet mode
     */
    public void setMode(Portlet.Mode mode);

    /**
     * Sets the mode that the portlet was running at last, or null if no previous mode exists.
     *
     * @param the previous portlet mode
     */
    public void setPreviousMode(Portlet.Mode mode);

    /**
     * Sets the PortletSettings object of the concrete portlet.
     *
     * @param portletSettings the portlet settings
     */
    public void setPortletSettings(PortletSettings portletSettings);

    /**
     * Sets the window that the portlet is running in.
     *
     * @param portletWindow the portlet window
     */
    public void setWindow(PortletWindow portletWindow);

}
