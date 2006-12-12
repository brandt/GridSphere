package org.gridsphere.portletcontainer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The <code>DefaultPortletAction</code> is a portlet action with default parameters.
 * You can use this portlet action to pass parameters in your action or create your own portlet action.
 * This default implementation demonstrates how to implement it.
 */
public abstract class DefaultPortletPhase implements Serializable {

    protected Map<String, String> store = new HashMap<String, String>();
    protected String name;

    /**
     * Constructs an instance of DefaultPortletPhase with the provided name
     *
     * @param name the name of the phase
     */
    public DefaultPortletPhase(String name) {
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
     * @param name  the name of the parameter
     * @param value the value of the parameter
     */
    public void addParameter(String name, String value) {
        store.put(name, value);
    }

    /**
     * Returns parameter value given a supplied name
     *
     * @param name the parameter name
     * @return all parameters as a map
     */
    public String getParameter(String name) {
        return (String) store.get(name);
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
    public void setParameters(Map<String, String> store) {
        this.store = store;
    }

    /**
     * Tests the equality of another DefaultPortletAction object
     *
     * @return <code>true</code> if they are equal, <code>false</code> otherwise
     */
    public boolean equals(Object obj) {
        return ((obj != null) && ((DefaultPortletPhase) obj).getName().equals(this.getName()));
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString(String phase) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(phase).append("=");
        buffer.append(name);
        Object[] parameterNames = store.keySet().toArray();
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = (String) parameterNames[i];
            String parameterValue = store.get(parameterName);
            buffer.append("&amp;"); // special character replaced with its corresponding entity for XHTML 1.0 Strict compliance
            buffer.append(parameterName);
            buffer.append("=");
            buffer.append(parameterValue);
        }
        return buffer.toString();
    }
}
