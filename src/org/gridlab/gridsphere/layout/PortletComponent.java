/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletRole;


/**
 * The <code>PortletComponent</code> defines the interfaces provided by a portlet component.
 */
public interface PortletComponent extends ComponentLifecycle {

    /**
     * Returns the portlet component name
     *
     * @return the portlet component name
     */
    public String getName();

    /**
     * Sets the portlet component name
     *
     * @param name the portlet component name
     */
    public void setName(String name);

    /**
     * Returns the portlet component label
     *
     * @return the portlet component label
     */
    public String getLabel();

    /**
     * Sets the portlet component label
     *
     * @param label the portlet component label
     */
    public void setLabel(String label);

    /**
     * Sets the portlet component width
     *
     * @param width the portlet component width
     */
    public void setWidth(String width);

    /**
     * Returns the portlet component width
     *
     * @return the portlet component width
     */
    public String getWidth();

    /**
     * Returns the default portlet component width
     *
     * @return the default portlet component width
     */
    public String getDefaultWidth();

    /**
     * When set to true the portlet component is visible and will be rendered
     *
     * @param isVisible if <code>true</code> portlet component is rendered,
     * <code>false</code> otherwise
     */
    public void setVisible(boolean isVisible);

    /**
     * When isVisible is true the portlet component is visible and will be rendered
     *
     * @return the portlet component visibility
     */
    public boolean getVisible();

    /**
     * Sets the theme of this portlet component
     *
     * @param theme the theme of this portlet component
     */
    public void setTheme(String theme);

    /**
     * Return the theme of this portlet component
     *
     * @return the theme of this portlet component
     */
    public String getTheme();

    /**
     * Allows a required role to be associated with viewing this portlet
     *
     * @param requiredRole the required portlet role expresses as a <code>String</code>
     */
    public void setRequiredRole(PortletRole requiredRole);

    /**
     * Allows a required role to be associated with viewing this portlet
     *
     * @return the required portlet role expresses as a <code>PortletRole</code>
     */
    public PortletRole getRequiredRole();

    public void addComponentListener(PortletComponent component);
}
