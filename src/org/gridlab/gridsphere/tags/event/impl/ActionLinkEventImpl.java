/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.tags.event.impl;


import org.gridlab.gridsphere.tags.event.ActionTagEvent;
import org.gridlab.gridsphere.tags.event.ActionLinkEvent;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;

import java.util.List;

public class ActionLinkEventImpl extends ActionTagEventImpl implements ActionLinkEvent {

    protected DefaultPortletAction portletAction = null;

    public ActionLinkEventImpl(ActionEvent event) {
        super(event);
        portletAction = event.getAction();
    }

    /**
     * Return the list of action parameter beans
     *
     * @return the list of action parameter beans
     */
    public List getActionParamBeans() {
        return null;
    }

    /**
     * Return the action parameter value associated with the action name
     *
     * @param name the action parameter name
     * @return the action parameter value
     */
    public String getActionParamValue(String name) {
        return null;
    }

    /**
     * Returns the action name . This is the same as
     * {@link org.gridlab.gridsphere.portlet.DefaultPortletAction#getName}
     *
     * @return the action name
     */
    public String getActionName() {
        return portletAction.getName();
    }

}
