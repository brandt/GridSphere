/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;

import javax.swing.border.TitledBorder;
import java.io.PrintWriter;

public class PortletTitleBorder extends BasePortletComponent implements PortletBorder {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletTitleBorder.class);

    private String title = "";
    private String titlecolor = "blue";
    private String font = "";

    public PortletTitleBorder() {}

    public PortletTitleBorder(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleColor() {
        return titlecolor;
    }

    public String getTitleFont() {
        return font;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleColor(String color) {
        this.titlecolor = titlecolor;
    }

    public void setTitleFont(String font) {
        this.font = font;
    }

    public void doRender(PrintWriter out) {
        log.debug("in doRender()");
    }

}
