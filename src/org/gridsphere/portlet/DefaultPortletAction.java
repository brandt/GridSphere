/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: DefaultPortletAction.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The <code>DefaultPortletAction</code> is a portlet action with default parameters.
 * You can use this portlet action to pass parameters in your action or create your own portlet action.
 * This default implementation demonstrates how to implement it.
 */
public final class DefaultPortletAction implements PortletAction, Serializable {

    private Map store = new HashMap();
    private String name;
    public static final String DEFAULT_PORTLET_ACTION = "gs_action";
    
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
     * @param name  the name of the parameter
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

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(DEFAULT_PORTLET_ACTION + "=");
        buffer.append(name);
        Object[] parameterNames = store.keySet().toArray();
        for (int ii = 0; ii < parameterNames.length; ++ii) {
            String parameterName = (String) parameterNames[ii];
            Object o = store.get(parameterName);
            if (o instanceof String) {
                String parameterValue = (String) o;
                buffer.append("&amp;"); // special character replaced with its corresponding entity for XHTML 1.0 Strict compliance
                buffer.append(parameterName);
                buffer.append("=");
                buffer.append(parameterValue);
            }
        }
        return buffer.toString();
    }
}
