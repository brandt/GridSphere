/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class TableCellBean extends BeanContainer implements TagBean {

    protected String cellSpacing = "";
    protected String width = "";
    protected String TABLE_CELL_STYLE = "portlet-frame-text";

    public TableCellBean() {
        super();
        this.cssStyle = TABLE_CELL_STYLE;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

    public void setCellSpacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    public String getCellSpacing() {
        return cellSpacing;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<td ");
        if (!cssStyle.equals("")) sb.append("class=\"" + cssStyle + "\"");
        if (!cellSpacing.equals("")) sb.append(" cellspacing=\"" + cellSpacing + "\"");
        if (!width.equals("")) sb.append(" width=\"" + width + "\"");
        sb.append(">");
        sb.append(super.toString());
        sb.append("</td>");
        return sb.toString();
    }

}
