/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class BasePortletComponent extends PortletContainer implements PortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(BasePortletComponent.class);

    protected int id = 0;
    protected String width;
    protected String height;
    protected String name;
    protected String fgColor;
    protected String bgColor = "#CCCCCC";
    protected PortletBorder border;
    protected PortletInsets insets;
    protected boolean isVisible = true;
    protected String className = BasePortletComponent.class.getName();

    public String getClassName() {
        return className;
    }

    public int getID() {
        return id;
    }

    public String getBackground() {
        return bgColor;
    }

    public String getForeground() {
        return fgColor;
    }

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

    public void setBackground(String bgColor) {
        this.bgColor = bgColor;
    }

    public void setForeground(String ggColor) {
        this.fgColor = fgColor;
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

    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        SportletRequest req = event.getSportletRequest();
        String sid = new Integer(id).toString();
        req.setAttribute(LayoutProperties.ID, sid);
        req.setAttribute(LayoutProperties.NAME, name);
        req.setAttribute(LayoutProperties.BGCOLOR, bgColor);
        req.setAttribute(LayoutProperties.FGCOLOR, fgColor);
        req.setAttribute(LayoutProperties.HEIGHT, height);
        req.setAttribute(LayoutProperties.WIDTH, width);
    }

}
