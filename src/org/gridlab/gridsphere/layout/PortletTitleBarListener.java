/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.event.WindowEvent;

/**
 * The WindowListener interface is an addition to the Portlet interface. If an object wishes to receive
 * events from a portlet window, this interface has to be implemented additionally to the Portlet interface.
 */
public interface PortletTitleBarListener {

    /**
     * Notifies this listener that a portlet window has been maximized.
     *
     * @param event the window event
     */
    public void handleTitleBarEvent(PortletTitleBarEvent event) throws PortletLayoutException;


}
