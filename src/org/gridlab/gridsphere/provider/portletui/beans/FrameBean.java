/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public class FrameBean extends TableBean implements TagBean {

    public static final String TABLE_FRAME_STYLE = "portlet-frame";
    public static final String TABLE_FRAME_WIDTH = "100%";
    public static final String TABLE_FRAME_SPACING = "1";

    public FrameBean() {
        super(TABLE_FRAME_STYLE);
        this.width = TABLE_FRAME_WIDTH;
        this.cellSpacing = TABLE_FRAME_SPACING;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table class=\"" + TABLE_FRAME_STYLE + "\" ");
        sb.append(" cellspacing=\"" + cellSpacing + "\" ");
        sb.append(" width=\"" + width + "\" ");
        sb.append(defaultModel.toString());
        sb.append("</table>");
        return sb.toString();
    }
}
