/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.event.impl;

import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.portlet.PortletAction;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;

import java.util.EventObject;

/**
 * An ActionEvent is sent by the portlet container when an HTTP request is received that is associated with an action.
 */
public class WindowEventImpl extends EventObject implements WindowEvent {

    private int event = -1;
    private PortletRequest req;

    public WindowEventImpl(PortletRequest req, int windowEvent) {
        super(req);
        this.req = req;
        this.event = windowEvent;
    }

    /**
     * Returns the identifier of the current window event
     *
     * @return the window event identifier
     */
    public int getEventId() {
        return event;
    }

    /**
     * Returns the portlet request that has caused this event. If this event is not triggered by a request,
     * this methods returns null
     *
     * @return the portlet request
     */
    public PortletRequest getPortletRequest() {
        return req;
    }

}
