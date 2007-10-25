/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.event.jsr.impl;

import org.gridsphere.portletcontainer.DefaultPortletAction;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.portletui.beans.TagBean;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.util.HashMap;

/**
 * An <code>ActionFormEvent</code> is sent by the portlet container when an HTTP request is
 * received that is associated with an action.
 */
public class ActionFormEventImpl extends BaseFormEventImpl implements ActionFormEvent {

    private DefaultPortletAction action;

    /**
     * Constructs an instance of ActionEventImpl given an action, request and response
     *
     * @param action   a <code>DefaultPortletAction</code>
     * @param request  the <code>PortletRequest</code>
     * @param response the <code>PortletResponse</code>
     */
    public ActionFormEventImpl(DefaultPortletAction action, ActionRequest request, ActionResponse response) {
        super(request, response);
        this.action = action;
        // Unless tagBeans is null, don't recreate them
        if (tagBeans == null) {
            tagBeans = new HashMap<String, TagBean>();
            createTagBeans();
        }
        //logRequestParameters();
        //logTagBeans();
    }

    /**
     * Returns the event action
     *
     * @return the portlet action
     */
    public DefaultPortletAction getAction() {
        return action;
    }

    /**
     * Return the action request associated with this action event
     *
     * @return the <code>PortletRequest</code>
     */
    public ActionRequest getActionRequest() {
        return (ActionRequest) portletRequest;
    }

    /**
     * Return the action response associated with this action event
     *
     * @return the <code>PortletResponse</code>
     */
    public ActionResponse getActionResponse() {
        return (ActionResponse) portletResponse;
    }

}
