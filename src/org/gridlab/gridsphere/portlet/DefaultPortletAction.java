/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.Hashtable;
import java.util.Map;

/**
 * The DefaultPortletAction is a portlet action with default parameters.
 * You can use this portlet action to pass parameters in your action or create your own portlet action.
 * This default implementation demonstrates how to implement it.
 */
public final class DefaultPortletAction implements PortletAction {

    private Map store = new Hashtable();
    private String name;
    private String portletID;

    /**
     * Constructor creates portlet action from action name
     */
    public DefaultPortletAction(String name) {
        this.name = name;
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

}
