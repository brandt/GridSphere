/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

public class TableFrameBean extends TableBean implements TagBean {

    public static final String TABLE_FRAME_STYLE = "portlet-frame";

    public TableFrameBean() {
        super(TABLE_FRAME_STYLE);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table class=\"" + TABLE_FRAME_STYLE + "\">");
        sb.append(defaultModel.toString());
        sb.append("</table>");
        return sb.toString();
    }
}
