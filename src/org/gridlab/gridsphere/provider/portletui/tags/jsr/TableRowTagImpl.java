/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.jsr;

import org.gridlab.gridsphere.provider.portletui.beans.TableRowBean;
import org.gridlab.gridsphere.provider.portletui.tags.jsr.BaseComponentTagImpl;
import org.gridlab.gridsphere.provider.portletui.tags.TableRowTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * The <code>TableRowTag</code> represents a table row element that is conatined within a <code>TableTag</code>
 * and itself may contain <code>TableCellTag</code>s
 */
public class TableRowTagImpl extends BaseComponentTagImpl implements TableRowTag {

    protected TableRowBean rowBean = null;
    protected boolean isHeader = false;
    protected String align = null;
    protected String valign = null;

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

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            rowBean = (TableRowBean) pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (rowBean == null) rowBean = new TableRowBean();
        } else {
            rowBean = new TableRowBean();
            rowBean.setHeader(isHeader);
            if (align != null) rowBean.setAlign(align);
            if (valign != null) rowBean.setValign(valign);
            rowBean.setCssClass(this.cssClass);
            rowBean.setCssStyle(this.cssStyle);
        }



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

        return EVAL_PAGE;
    }
}
