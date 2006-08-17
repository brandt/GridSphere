/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.event.impl;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;

import java.util.EventObject;

/**
 * An <code>ActionEvent</code> is sent by the portlet container when an HTTP request is
 * received that is associated with an action.
 */
public class ActionEventImpl extends EventObject implements ActionEvent {

    private DefaultPortletAction action;
    private PortletRequest request;
    private PortletResponse response;

    /**
     * Constructs an instance of ActionEventImpl given an action, request and response
     *
     * @param action   a <code>DefaultPortletAction</code>
     * @param request  the <code>PortletRequest</code>
     * @param response the <code>PortletResponse</code>
     */
    public ActionEventImpl(DefaultPortletAction action, PortletRequest request, PortletResponse response) {
        super(action);
        this.action = action;
        this.request = request;
        this.response = response;
    }

    /**
     * Returns the action that this action event carries.
     *
     * @return the portlet action
     */
    public DefaultPortletAction getAction() {
        return action;
    }

    /**
     * Returns the action that this action event carries.
     *
     * @return the portlet action
     */
    public String getActionString() {
        return action.getName();
    }

    /**
     * Return the portlet request associated with this action event
     *
     * @return portletRequest the <code>PortletRequest</code>
     */
    public PortletRequest getPortletRequest() {
        return request;
    }

    /**
     * Return the portlet response associated with this action event
     *
     * @return portletResponse the <code>PortletResponse</code>
     */
    public PortletResponse getPortletResponse() {
        return response;
    }

}
