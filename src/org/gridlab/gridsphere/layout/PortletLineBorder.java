/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

public class PortletLineBorder implements PortletBorder {

    public String color = "black";
    private static PortletLineBorder blackInstance = new PortletLineBorder("black");
    private static PortletLineBorder blueInstance = new PortletLineBorder("blue");

    public int thickness = 1;

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

    public String getLineColor() {
        return color;
    }

    public int getThickness() {
        return thickness;
    }

}
