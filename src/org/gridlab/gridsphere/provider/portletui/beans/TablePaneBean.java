/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public class TablePaneBean extends TagBeanContainer implements TagBean {

    public static final String TABLE_PANE_STYLE = "portlet-pane";

    public TablePaneBean() {
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table class=\"" + TABLE_PANE_STYLE + "\">");
        sb.append("<tr><td>");
        sb.append(super.toString());
        sb.append("</td></tr>");
        sb.append("</table>");
        return sb.toString();
    }
}
