/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

import java.io.IOException;

/**
 * <code>BasePortletComponent</code> represents an abstract portlet component with a particular
 * size, layout and theme and is subclasses by concrete portlet component instances.
 */
public abstract class BasePortletComponent extends BaseComponentLifecycle implements PortletComponent {

    protected String width = "";
    protected String height = "";
    protected String name = new String();
    protected String theme = GridSphereConfig.getProperty(GridSphereConfigProperties.GRIDSPHERE_DEFAULT_THEME);
    protected boolean isVisible = true;

    /**
     * Returns the portlet component name
     *
     * @return the portlet component name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the portlet component name
     *
     * @param name the portlet component name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the portlet component height
     *
     * @return the portlet component height
     */
    public String getHeight() {
        return height;
    }

    /**
     * Sets the portlet component height
     *
     * @param height the portlet component height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * Sets the portlet component width
     *
     * @param width the portlet component width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Returns the portlet component width
     *
     * @return the portlet component width
     */
    public String getWidth() {
        return width;
    }

    /**
     * When set to true the portlet component is visible and will be rendered
     *
     * @param isVisible if <code>true</code> portlet component is rendered,
     * <code>false</code> otherwise
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * Return true if the portlet component visibility is true
     *
     * @return the portlet component visibility
     */
    public boolean getVisible() {
        return isVisible;
    }

    /**
     * Sets the theme of this portlet component
     *
     * @param theme the theme of this portlet component
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Return the theme of this portlet component
     *
     * @return the theme of this portlet component
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Performs an action on this portlet component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.actionPerformed(event);
    }

    /**
     * Renders the portlet component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletRequest req = event.getPortletRequest();
        req.setAttribute(GridSphereProperties.COMPONENT_ID, componentIDStr);
    }

}
