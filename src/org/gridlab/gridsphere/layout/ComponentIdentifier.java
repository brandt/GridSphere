/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

/**
 * A <code>ComponentIdentifier</code> contains meta information about a {@link PortletComponent}
 */
public class ComponentIdentifier implements Cloneable {

    private int id = -1;
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
     *
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * If this component encaspulates a portlet class  such as PortletFrame
     * than return true
     *
     * @return <code>true</code> if this component encaspulates a portlet class,
     * <code>false</code> otherwise
     */
    public boolean hasPortlet() {
        if (portletClass != null) return true;
        return false;
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
        ComponentIdentifier c = (ComponentIdentifier)super.clone();
        c.component = (this.component == null) ? null : (PortletComponent)this.component.clone();
        c.className = (this.className == null) ? null : this.className;
        c.id = this.id;
        c.portletClass = (this.portletClass == null) ? null : this.portletClass;
        return c;
    }
}
