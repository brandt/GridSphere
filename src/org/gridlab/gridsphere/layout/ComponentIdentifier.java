/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

/**
 * A <code>ComponentIdentifier</code> contains meta information about a {@link PortletComponent}
 */
public class ComponentIdentifier {

    private int id;
    private String className;
    private String portletClass;
    private PortletComponent component;

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
     * @return true if this component encaspulates a portlet class, false otherwise
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

}
