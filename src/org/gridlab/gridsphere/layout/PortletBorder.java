/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;

public class PortletBorder implements PortletRender {

    private String borderColor = "#336699";
    private String thickness = "1";

    public PortletBorder() {}

    public void setBorderColor(String color) {
        this.borderColor = color;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {

    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {

    }

}
