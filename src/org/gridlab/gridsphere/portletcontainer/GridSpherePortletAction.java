/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;

import java.util.Map;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * The DefaultPortletAction is a portlet action with default parameters.
 * You can use this portlet action to pass parameters in your action or create your own portlet action.
 * This default implementation demonstrates how to implement it.
 */
public final class GridSpherePortletAction implements PortletAction {

    private PortletRequest req = null;

    /**
     * Constructs a GridSpherePortletAction from the PortletRequest
     */
    public GridSpherePortletAction(PortletRequest req) {
        this.req = req;
    }

    public boolean hasAction() {
        if (getName() != null)
            return true;
        return false;
    }

    public String getConcretePortletID() {
        return req.getParameter(GridSphereProperties.PORTLETID);
    }

    /**
     * Returns the name of this action
     *
     * @return the name of this action
     */
    public String getName() {
        return req.getParameter(GridSphereProperties.ACTION);
    }

    /**
     * Returns the constructed application portlet action.
     * Reconstructs the PortletAction attributes store from request names (painful!)
     */
    public DefaultPortletAction getPortletAction() {
        // Here is some code for handling actions
        DefaultPortletAction a = new DefaultPortletAction(getName());
        /* Need to reclaim parameter values
        Enumeration enum = req.getParameterNames();
        String action = getName();
        String value = null;
        int index = action.length() + 1;
        String name = null;
        String newname = null;
        while (enum.hasMoreElements()) {
            name = (String)enum.nextElement();
            if (name.startsWith(action)) {
                newname = name.substring(index, name.length());
                value = req.getParameter(name);
                a.addParameter(newname, value);
            }
        }
        */
        return a;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("GridSpherePortletAction");
        sb.append("Action Name: " + getName());
        sb.append("Concrete Portlet ID: " + getConcretePortletID());
        return sb.toString();
    }
}
