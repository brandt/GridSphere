/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import java.util.HashMap;
import java.util.Map;

/**
 * The <code>DefaultPortletAction</code> is a portlet action with default parameters.
 * You can use this portlet action to pass parameters in your action or create your own portlet action.
 * This default implementation demonstrates how to implement it.
 */
public final class DefaultPortletAction implements PortletAction {

    private Map store = new HashMap();
    private String name;

    /**
     * Constructs an instance of DefaultPortletAction with the provided name
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
    public String getParameter(String name) {
        return (String)store.get(name);
    }

    /**
     * Returns all parameters
     *
     * @return all parameters as a map
     */
    public Map getParameters() {
        return store;
    }

    /**
     * Sets all parameters
     *
     * @param store the parameters as a map
     */
    public void setParameters(Map store) {
        this.store = store;
    }

    /**
     * Tests the equality of another DefaultPortletAction object
     *
     * @return <code>true</code> if they are equal, <code>false</code> otherwise
     */
    public boolean equals(Object obj) {
        if ((obj != null) && (obj.getClass().equals(this.getClass()))) {
            return (((DefaultPortletAction) obj).getName().equals(this.getName()));
        }
        return false;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("action=");
        buffer.append(name);
        Object[] parameterNames = store.keySet().toArray();
        for (int ii = 0; ii < parameterNames.length; ++ii) {
            String parameterName = (String)parameterNames[ii];
            Object o = store.get(parameterName);
            if (o instanceof String) {
                String parameterValue = (String)o;
                buffer.append("&");
                buffer.append(parameterName);
                buffer.append("=");
                buffer.append(parameterValue);
            }
        }
        return buffer.toString();
    }
}
