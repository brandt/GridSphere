/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

import java.util.Iterator;

public class TableRowBean extends BeanContainer {

    protected boolean isHeader = false;
    public static final String TABLE_HEADER_STYLE = "portlet-section-header";
    protected String align = null;
    protected String valign = null;

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
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public TableRowBean(PortletRequest req, String beanId) {
        super();
        this.request = req;
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

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<tr");
        if (align != null) sb.append(" layout=\"" + align + "\"");
        if (valign != null) sb.append(" valign=\"" + valign + "\"");
        sb.append(">");
        if (isHeader) {
            Iterator it = container.iterator();
            while (it.hasNext()) {
                BaseComponentBean tagBean = (BaseComponentBean) it.next();
                tagBean.setCssClass(TABLE_HEADER_STYLE);
            }
        }
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
