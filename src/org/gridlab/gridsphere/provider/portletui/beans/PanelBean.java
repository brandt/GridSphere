/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public class PanelBean extends BeanContainer implements TagBean {

    public static final String TABLE_PANE_STYLE = "portlet-pane";
    public static final String TABLE_PANE_WIDTH = "200";
    public static final String TABLE_PANE_SPACING = "1";

    protected String cellSpacing = TABLE_PANE_SPACING;
    protected String rows = "";
    protected String cols = "";
    protected String width = TABLE_PANE_WIDTH;

    public PanelBean() {
        this.cssStyle = TABLE_PANE_STYLE;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getRows() {
        return rows;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public String getCols() {
        return cols;
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

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table class=\"" + TABLE_PANE_STYLE + "\" ");
        sb.append(" cellspacing=\"" + cellSpacing + "\" ");
        sb.append(" width=\"" + width + "\" ");
        sb.append(">");
        return sb.toString();
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();
        if (rows.equals("")) {
            rows = new Integer(container.size()).toString();
        }
        int numRows = 0;
        int numCols = 0;
        try {
            numRows = new Integer(rows).intValue();
            numCols = new Integer(cols).intValue();
        } catch (Exception e) {
            // forget it
        }
        int i = 0;
        //System.err.println("in PanelBean: # of tags: " + container.size());
        while (i < container.size()) {
            sb.append("<tr>");
            int j = 0;
            while ((j < numRows) && (i < container.size())) {
                //sb.append("<td>");
                TagBean tagBean = (TagBean)container.get(i);
                //System.err.println("in PanelBean: its " + tagBean.toString());
                sb.append(tagBean.toStartString());
                sb.append(tagBean.toEndString());
                //sb.append("</td>");
                j++; i++;
            }
            sb.append("</tr>");
        }

        sb.append("</table>");
        return sb.toString();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table class=\"" + TABLE_PANE_STYLE + "\" ");
        sb.append(" cellspacing=\"" + cellSpacing + "\" ");
        sb.append(" width=\"" + width + "\" ");
        sb.append(">");
        //sb.append("<tr><td>");


        if (rows.equals("")) {
            rows = new Integer(container.size()).toString();
        }
        int numRows = 0;
        int numCols = 0;
        try {
            numRows = new Integer(rows).intValue();
            numCols = new Integer(cols).intValue();
        } catch (Exception e) {
           // forget it
        }
        int i = 0;
        //System.err.println("in PanelBean: # of tags: " + container.size());
        while (i < container.size()) {
            sb.append("<tr>");
            int j = 0;
            while ((j < numRows) && (i < container.size())) {
                //sb.append("<td>");
                TagBean tagBean = (TagBean)container.get(i);
                //System.err.println("in PanelBean: its " + tagBean.toString());
                sb.append(tagBean.toString());
                //sb.append("</td>");
                j++; i++;
            }
            sb.append("</tr>");
        }

        //sb.append(super.toString());
        //sb.append("</td></tr>");
        sb.append("</table>");
        return sb.toString();
    }
}
