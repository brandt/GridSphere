/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;

import java.io.PrintWriter;

public class PortletLineBorder extends BasePortletComponent implements PortletBorder {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletLineBorder.class);

    public String color = "black";
    private static PortletLineBorder blackInstance = new PortletLineBorder("black");
    private static PortletLineBorder blueInstance = new PortletLineBorder("blue");

    public int thickness = 1;

    public PortletLineBorder() {}

    public PortletLineBorder(String color) {
        this.color = color;
    }

    public PortletLineBorder(String color, int thickness) {
        this.color = color;
        this.thickness = thickness;
    }

    public static PortletBorder createBlackLineBorder() {
        return blackInstance;
    }

    public static PortletBorder createBlueLineBorder() {
        return blueInstance;
    }

    public void setLineColor(String color) {
        this.color = color;
    }

    public String getLineColor() {
        return color;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public void doRender(PrintWriter out) {
        log.debug("in doRender()");
    }

}
