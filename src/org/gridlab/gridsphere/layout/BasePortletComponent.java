/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;

public abstract class BasePortletComponent extends BasePortletLifecycle implements PortletComponent {

    protected String width = new String();
    protected String height = new String();
    protected String name = new String();
    protected PortletBorder border;
    protected PortletInsets insets;
    protected boolean isVisible = true;

    public PortletBorder getPortletBorder() {
        return border;
    }

    public PortletInsets getPortletInsets() {
        return insets;
    }

    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public void setPortletBorder(PortletBorder border) {
        this.border = border;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setPortletInsets(PortletInsets insets) {
        this.insets = insets;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.actionPerformed(event);
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        SportletRequest req = event.getSportletRequest();
        String sid = new Integer(COMPONENT_ID).toString();
        req.setAttribute(LayoutProperties.ID, sid);
        req.setAttribute(LayoutProperties.NAME, name);
        req.setAttribute(LayoutProperties.HEIGHT, height);
        req.setAttribute(LayoutProperties.WIDTH, width);
    }

}
