/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.event;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;

/**
 * An <code>ActionEvent</code> is sent by the portlet container when an HTTP request is received that is
 * associated with an action.
 */
public interface ActionEvent extends Event {

    /**
     * Event identifier indicating that portlet request has been received that one or more actions associated with it.
     * Each action will result in a separate event being fired.
     * An event with this id is fired when an action HAS BEEN performed.
     */
    public final static int ACTION_PERFORMED = 1;

    /**
     * Returns the action that this action event carries.
     *
     * @return the portlet action
     */
    public DefaultPortletAction getAction();

    /**
     * Returns the action that this action event carries.
     *
     * @return the portlet action
     */
    public String getActionString();

    /**
     * Return the portlet response associated with this action event
     *
     * @return the <code>PortletResponse</code>
     */
    public PortletResponse getPortletResponse();

}
