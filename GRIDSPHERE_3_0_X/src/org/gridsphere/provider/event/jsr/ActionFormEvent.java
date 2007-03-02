/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: ActionFormEvent.java 4687 2006-03-29 06:12:09Z novotny $
 */
package org.gridsphere.provider.event.jsr;

import org.gridsphere.portletcontainer.DefaultPortletAction;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * An <code>ActionFormEvent</code> is sent by the portlet container when an HTTP request is received that is
 * associated with an action.
 */
public interface ActionFormEvent extends FormEvent {

    /**
     * Returns the event action
     *
     * @return the portlet action
     */
    public DefaultPortletAction getAction();

    /**
     * Return the action request associated with this action event
     *
     * @return the <code>PortletRequest</code>
     */
    public ActionRequest getActionRequest();

    /**
     * Return the action response associated with this action event
     *
     * @return the <code>PortletResponse</code>
     */
    public ActionResponse getActionResponse();

}
