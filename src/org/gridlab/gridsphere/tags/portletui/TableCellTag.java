package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableCellTag extends BaseComponentTag {

    protected TableCellBean cellBean = null;
    protected String width = null;
    protected String cellSpacing = null;
    protected String style = TextBean.TEXT_LABEL_STYLE;

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void setCellSpacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    public String getCellSpacing() {
        return cellSpacing;
    }

    public void setCellBean(TableCellBean cellBean) {
        this.cellBean = cellBean;
    }

    public TableCellBean getCellBean() {
        return cellBean;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            cellBean = (TableCellBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (cellBean == null) cellBean = new TableCellBean();
        } else {
            cellBean = new TableCellBean();
            if (cellSpacing != null) cellBean.setCellSpacing(cellSpacing);
            if (width != null) cellBean.setWidth(width);
        }

        TableRowTag rowTag = (TableRowTag)getParent();
        if (rowTag.getHeader()) {
            cellBean.setCssStyle(TableRowBean.TABLE_HEADER_STYLE);
        }
        try {
            JspWriter out = pageContext.getOut();
            out.print(cellBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            out.print(cellBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
