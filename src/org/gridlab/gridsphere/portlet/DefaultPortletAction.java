/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import org.gridlab.gridsphere.event.ActionEvent;

import java.util.Map;
import java.util.Hashtable;

/**
 * The DefaultPortletAction is a portlet action with default parameters.
 * You can use this portlet action to pass parameters in your action or create your own portlet action.
 * This default implementation demonstrates how to implement it.
 */
public final class DefaultPortletAction implements PortletAction {

    private AbstractPortlet portlet;
    private Map store;
    private String name;
    private String portletID;

    /**
     * Constructor creates portlet action from action name
     */
    public DefaultPortletAction(AbstractPortlet portlet, String name) {
        this.portlet = portlet;
        this.name = name;
        store = new Hashtable();
    }

    /**
     * Returns the name of this action
     *
     * @return the name of this action
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a parameters to this action
     *
     * @param name the name of the parameter
     * @param value the value of the parameter
     */
    public void addParameter(String name, Object value) {
        store.put(name, value);
    }

    /**
     * Returns all parameters
     *
     * @return all parameters as a map
     */
    public Map getParameters() {
        return store;
    }

    public void setPortletID(String portletID) {
        this.portletID = portletID;
    }

    public String getPortletID() {
        return portletID;
    }

}
