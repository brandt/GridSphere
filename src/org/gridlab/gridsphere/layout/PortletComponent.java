/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.awt.*;

public abstract class PortletComponent {

    protected int width;
    protected int height;
    protected String fgColor;
    protected String bgColor;
    protected PortletBorder border;
    protected PortletInsets insets;

    public PortletBorder getBorder() {
        return border;
    }

    public PortletInsets getInsets() {
        return insets;
    }

    public PortletDimension getMaximumSize() {
        return null;
    }

    public PortletDimension getMinimumSize() {
        return null;
    }

    public abstract void doLayout();

    public PortletDimension getSize(PortletDimension rv) {
        if (rv == null) rv = new PortletDimension();
        rv.setSize(width, height);
        return rv;
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

    public void setBorder(PortletBorder border) {
        this.border = border;
    }

}
