/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import javax.swing.border.TitledBorder;

public class PortletTitleBorder implements PortletBorder {

    private String title = "";
    private String titlecolor = "blue";
    private String font = "";

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

}
