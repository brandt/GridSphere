/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletContext;

import java.io.IOException;

public class BasePortletComponent extends PortletContainer implements PortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(BasePortletComponent.class);

    protected String width;
    protected String height;
    protected String name;
    protected String fgColor;
    protected String bgColor;
    protected PortletBorder border;
    protected PortletInsets insets;

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

    public void doRender(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {
        req.setAttribute("name", name);
        req.setAttribute("bgcolor", bgColor);
        req.setAttribute("fgcolor", fgColor);
        req.setAttribute("height", height);
        req.setAttribute("width", width);
    }
}
