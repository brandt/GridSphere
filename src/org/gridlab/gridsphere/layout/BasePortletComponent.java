/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.awt.*;
import java.io.PrintWriter;

public class BasePortletComponent extends PortletContainer implements PortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(BasePortletComponent.class);

    protected int width;
    protected int height;
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

    public PortletBorder getBorder() {
        return border;
    }

    public PortletInsets getInsets() {
        return insets;
    }

    public String getName() {
        return name;
    }

    public void doBeginRender(PrintWriter out) {
        // XXX: FILL ME IN
        log.debug("in doBeginRender()");
    }

    public void doEndRender(PrintWriter out) {
        // XXX: FILL ME IN
        log.debug("in doEndRender()");
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setBackground(String bgColor) {
        this.bgColor = bgColor;
    }

    public void setForeground(String ggColor) {
        this.fgColor = fgColor;
    }

    public void setBorder(PortletBorder border) {
        this.border = border;
    }

    public void setName(String name) {
        this.name = name;
    }

}
