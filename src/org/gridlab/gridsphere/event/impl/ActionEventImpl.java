/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.event.impl;

import org.gridlab.gridsphere.portlet.PortletAction;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.event.Event;
import org.gridlab.gridsphere.event.ActionEvent;

import java.util.EventObject;

/**
 * An ActionEvent is sent by the portlet container when an HTTP request is received that is associated with an action.
 */
public class ActionEventImpl extends EventObject implements ActionEvent {

    private int eventType;
    private DefaultPortletAction action;
    private PortletRequest request;
    private PortletResponse response;

    public ActionEventImpl(DefaultPortletAction action, int eventType, PortletRequest request, PortletResponse response) {
        super(action);
        this.eventType = eventType;
        this.request = request;
        this.response = response;
    }

    /**
     * Returns the action that this action event carries.
     *
     * @return the portlet action
     */
    public PortletAction getAction() {
        return action;
    }

    /**
     * Sets the type of action event either ACTION_PERFORMED or ACTION_NOTYETPERFORMED
     *
     * @param eventType the event type
     */
    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    /**
     * Returns the type of action event either ACTION_PERFORMED or ACTION_NOTYETPERFORMED
     *
     * @return the event type
     */
    public int getEventType() {
        return eventType;
    }

    /**
     * Return the portlet request associated with this action event
     *
     * @return portletRequest the PortletRequest
     */
    public PortletRequest getPortletRequest() {
        return request;
    }

    /**
     * Return the portlet response associated with this action event
     *
     * @return portletResponse the PortletResponse
     */
    public PortletResponse getPortletResponse() {
        return response;
    }

}
