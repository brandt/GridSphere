/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TableRowTag.java 4797 2006-05-17 20:49:45Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.TableRowBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

/**
 * The <code>TableRowTag</code> represents a table row element that is conatined within a <code>TableTag</code>
 * and itself may contain <code>TableCellTag</code>s
 */
public class TableRowTag extends BaseComponentTag {

    protected TableRowBean rowBean = null;
    protected boolean isHeader = false;
    protected String align = null;
    protected String valign = null;
    protected boolean isZebra = false;

    /**
     * Sets the table row bean
     *
     * @param tableRowBean the table row bean
     */
    public void setTableRowBean(TableRowBean tableRowBean) {
        rowBean = tableRowBean;
    }

    /**
     * Returns the table row bean
     *
     * @return the table row bean
     */
    public TableRowBean getTableRowBean() {
        return rowBean;
    }

    /**
     * Indicates if this table row is a table header
     *
     * @param isHeader is true if this row is a table header
     */
    public void setHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    /**
     * Indicates if this table row is a table header
     *
     * @return true if this row is a table header, false otherwise
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

    public void release() {
        rowBean = null;
        isHeader = false;
        align = null;
        valign = null;
        isZebra = false;
        super.release();
    }

    public int doStartTag() throws JspException {

        Tag parent = this.getParent();
        if (parent instanceof TableTag) {
            TableTag tableTag = (TableTag) parent;
            int maxrows = tableTag.getMaxrows();
            boolean filter = tableTag.getFilter();
            // logic to determine if alternate (darkened row) should be set
            if (tableTag.getZebra()) {
                if ((tableTag.getRowCount() % 2) == 0) {
                    isZebra = true;
                } else {
                    isZebra = false;
                }
            }

            // logic to determine what rows to display if table is broken into pages
            if (!isHeader) {
                tableTag.incrementRowCount();
            }

            // need to determine which rows to display
            int currpage = tableTag.getCurrentPage();
            if (!isHeader) {
                if (maxrows > 0) {
                    if (!filter) {
                        if ((tableTag.getRowCount() <= maxrows * currpage) || (tableTag.getRowCount() > maxrows * (currpage + 1))) {
                            return EVAL_PAGE;
                        }
                    }
                }
            }
        }

        if (!beanId.equals("")) {
            rowBean = (TableRowBean) getTagBean();
            if (rowBean == null) rowBean = new TableRowBean();
        } else {
            rowBean = new TableRowBean();
            rowBean.setHeader(isHeader);
            if (align != null) rowBean.setAlign(align);
            if (valign != null) rowBean.setValign(valign);
        }

        rowBean.setZebra(isZebra);

        try {
            JspWriter out = pageContext.getOut();
            out.print(rowBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print(rowBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        isZebra = false;
        super.doEndTag();
        return EVAL_PAGE;
    }
}
