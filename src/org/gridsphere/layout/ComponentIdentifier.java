/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.layout;

import java.io.Serializable;

/**
 * A <code>ComponentIdentifier</code> contains meta information about a {@link PortletComponent}
 */
public class ComponentIdentifier implements Serializable, Cloneable {

    private int id = -1;
    private String label = "";
    private String className = null;
    private String portletClass = null;
    private PortletComponent component = null;

    /**
     * Constructs an instance of ComponentIdentifier
     */
    public ComponentIdentifier() {
    }

    /**
     * Sets the portlet component id
     *
     * @param id the portlet component id
     */
    public void setComponentID(int id) {
        this.id = id;
    }

    /**
     * Returns the portlet component id
     *
     * @return the portlet component id
     */
    public int getComponentID() {
        return id;
    }

    /**
     * Returns the component label of this component or null if none exists
     *
     * @return the component label of this component or null if none exists
     */
    public String getComponentLabel() {
        return label;
    }

    /**
     * Sets the component label of this component or null if none exists
     *
     * @param label the component label of this component or null if none exists
     */
    public void setComponentLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the concrete portlet component class name
     *
     * @return the concrete portlet component class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the concrete portlet component class name
     *
     * @param className the concrete portlet component class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * If this component encaspulates a portlet class  such as PortletFrame
     * than return true
     *
     * @return <code>true</code> if this component encaspulates a portlet class,
     *         <code>false</code> otherwise
     */
    public boolean hasPortlet() {
        return (portletClass != null);
    }

    /**
     * Returns the portlet class contained by this component or null if none exists
     *
     * @return the portlet class contained by this component or null if none exists
     */
    public String getPortletClass() {
        return portletClass;
    }

    /**
     * Sets the portlet class contained by this component
     *
     * @param portletClass the portlet class contained by this component
     */
    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    /**
     * Sets the portlet component
     *
     * @param component the portlet component
     */
    public void setPortletComponent(PortletComponent component) {
        this.component = component;
    }

    /**
     * Returns the portlet component
     *
     * @return the portlet component
     */
    public PortletComponent getPortletComponent() {
        return component;
    }

    public Object clone() throws CloneNotSupportedException {
        ComponentIdentifier c = (ComponentIdentifier) super.clone();
        c.component = (this.component == null) ? null : (PortletComponent) this.component.clone();
        c.className = (this.className == null) ? null : this.className;
        c.id = this.id;
        c.label = (this.label.equals(label)) ? null : this.label;
        c.portletClass = (this.portletClass == null) ? null : this.portletClass;
        return c;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\nid=").append(id);
        sb.append("\nlabel=").append(label);
        sb.append("\ntype=").append(className);
        return sb.toString();
    }
}

