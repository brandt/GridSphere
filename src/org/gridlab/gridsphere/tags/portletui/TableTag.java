package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TableBean;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.provider.ui.beans.TableCellBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableTag extends BaseComponentTag {

    protected TableBean tableBean = null;
    //protected DefaultTableModel model = null;
    protected String cellSpacing = null;
    protected String width = null;

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableBean.setTableModel(tableModel);
    }

    public DefaultTableModel getTableModel() {
        return tableBean.getTableModel();
    }

    public void setTableBean(TableBean tableBean) {
        this.tableBean = tableBean;
    }

    public TableBean getTableBean() {
        return tableBean;
    }

     public void setCellSpacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    public String getCellSpacing() {
        return cellSpacing;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

    public int doStartTag() throws JspException {

        if (!beanId.equals("")) {
            tableBean = (TableBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (tableBean == null) {
                tableBean = new TableBean();
            }
        } else {
            tableBean = new TableBean();

            if (width != null) tableBean.setWidth(width);
            if (cellSpacing != null) tableBean.setCellSpacing(cellSpacing);
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(tableBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            out.print(tableBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
