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

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableTag extends BaseBeanTag {

    protected TableBean tableBean = null;
    //protected DefaultTableModel model = null;
    protected String cellSpacing = "1";
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

            if (tableBean != null) {
                //System.err.println("Found a non-null tablebean");
                return SKIP_BODY;
            } else {
                tableBean = new TableBean();
            }
        } else {
            tableBean = new TableBean();
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        // do all the rendering for this table
        //System.err.println("in TableTag:doEndTag");

        tableBean.setWidth(width);
        tableBean.setCellSpacing(cellSpacing);

        //tableBean.setTableModel();
        /*
        if (!beanId.equals("")) {
            //System.err.println("setting tablemodel in table");
            store(getBeanKey(), tableBean);
        }
        */

        Object parent = getParent();
        if (parent instanceof ContainerTag) {
            ContainerTag t = (ContainerTag)parent;
            t.addTagBean(tableBean);
        } else {

            try {
                JspWriter out = pageContext.getOut();
                //System.err.println("printing table " + tableBean.toString());
                out.print(tableBean.toString());
            } catch (Exception e) {
                throw new JspException(e);
            }
        }
        return EVAL_PAGE;
    }
}
