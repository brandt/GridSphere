/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

public class PortletBorderFactory {

    private static PortletLineBorder lineBorder = null;
    private static PortletTitleBorder titleBorder = null;

    public static PortletTitleBorder createTitleBorder() {
        if (titleBorder == null) {
            titleBorder = new PortletTitleBorder("");
        }
        return titleBorder;
    }

    public static PortletLineBorder createLineBorder() {
        if (lineBorder == null) {
            lineBorder = new PortletLineBorder("black");
        }
        return lineBorder;
    }


}
