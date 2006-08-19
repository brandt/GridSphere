/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: MessageEventImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.event.impl;

import org.gridsphere.event.MessageEvent;
import org.gridsphere.portlet.PortletMessage;
import org.gridsphere.portlet.PortletRequest;

import java.util.EventObject;

/**
 * An <code>MessageEvent</code> is sent by the portlet container when an HTTP request
 * is received that is associated with an action.
 */
public class MessageEventImpl extends EventObject implements MessageEvent {

    private PortletMessage message;
    private PortletRequest req;

    /**
     * Constructs an instance of MessageEventImpl given a portlet request and message
     *
     * @param req     the <code>PortletRequest</code>
     * @param message the <code>PortletMessage</code>
     */
    public MessageEventImpl(PortletRequest req, PortletMessage message) {
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
