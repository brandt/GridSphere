/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: TableRowBean.java 4884 2006-06-26 23:52:48Z novotny $
 */

package org.gridsphere.provider.portletui.beans;

import java.util.Iterator;

public class TableRowBean extends BeanContainer implements TagBean {

    protected boolean isHeader = false;
    public static final String TABLE_HEADER_STYLE = "portlet-section-header";
    public static final String TABLE_NORMAL_STYLE = "portlet-section-body";
    public static final String TABLE_ALTERNATE_STYLE = "portlet-section-alternate";
    public static final String TABLE_ROLLOVER_STYLE = "portlet-section-rollover";

    protected String align = null;
    protected String valign = null;
    protected boolean isZebra = false;

    /**
     * Constructs a default table row bean
     */
    public TableRowBean() {
        super();
    }

    /**
     * Constructs a default table row bean
     */
    public TableRowBean(BaseComponentBean compBean) {
        super();
        this.addBean(compBean);
    }

    /**
     * Constructs a table row bean from a supplied portlet request and bean identifier
     *
     * @param beanId the bean identifier
     */
    public TableRowBean(String beanId) {
        super();
        this.beanId = beanId;
    }

    /**
     * Indicates if this table row is a header
     *
     * @param isHeader true if this table row is a header, false otherwise
     */
    public void setHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    /**
     * Returns true if this table row is a header, false otherwise
     *
     * @return true if this table row is a header, false otherwise
     */
    public boolean getHeader() {
        return isHeader;
    }

    /**
     * Sets the table alignment e.g. "left", "center" or "right"
     *
     * @param align the table alignment
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     * Returns the table alignment e.g. "left", "center" or "right"
     *
     * @return the table alignment
     */
    public String getAlign() {
        return align;
    }

    /**
     * Sets the table vertical alignment e.g. "top", "middle", "bottom" or "baseline"
     *
     * @param valign the table vertical alignment
     */
    public void setValign(String valign) {
        this.valign = valign;
    }

    /**
     * Returns the table vertical alignment e.g. "top", "middle", "bottom" or "baseline"
     *
     * @return the table vertical alignment
     */
    public String getValign() {
        return valign;
    }

    public void setZebra(boolean isZebra) {
        this.isZebra = isZebra;
    }

    public boolean getZebra() {
        return isZebra;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<tr");
        if (align != null)
            sb.append(" align=\"").append(align).append("\"");   // todo check usage of 'layout' instead of 'align' ??
        if (valign != null) sb.append(" valign=\"" + valign + "\"");
        if (isHeader) {
            sb.append(" class=\"" + TABLE_HEADER_STYLE + "\"");
        } else {
            if (isZebra) {

                sb.append(" onmouseover=\"this.className='" + TABLE_ROLLOVER_STYLE + "'\" onmouseout=\"this.className='" + TABLE_NORMAL_STYLE + "'\" class=\"'" + TABLE_ALTERNATE_STYLE + "'\"");
            }
        }
        sb.append(getFormattedCss());
        sb.append(">");
        Iterator it = container.iterator();
        while (it.hasNext()) {
            TableCellBean cellBean = (TableCellBean) it.next();
            sb.append(cellBean.toStartString());
            sb.append(cellBean.toEndString());
        }
        return sb.toString();
    }

    public String toEndString() {
        return "</tr>";
    }

}
