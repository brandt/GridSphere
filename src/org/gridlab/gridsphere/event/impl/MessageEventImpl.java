/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.event.impl;

import org.gridlab.gridsphere.event.WindowEvent;
import org.gridlab.gridsphere.event.MessageEvent;
import org.gridlab.gridsphere.portlet.*;

import java.util.EventObject;

/**
 * An ActionEvent is sent by the portlet container when an HTTP request is received that is associated with an action.
 */
public class MessageEventImpl extends EventObject implements MessageEvent {

    private PortletMessage message;
    private PortletRequest req;

    public MessageEventImpl(PortletRequest req, DefaultPortletMessage message) {
        super(req);
        this.req = req;
        this.message = message;
    }

    /**
     * Returns the message that this event carries.
     * The message format is entirely open and has to be defined and documented by the source portlet.
     *
     * @return the portlet message
     */
    public PortletMessage getMessage() {
        return message;
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
