package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TableBean;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.provider.ui.beans.TableCellBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import java.util.List;
import java.util.Iterator;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableTag extends BaseBeanTag {

    protected TableBean tableBean = null;

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

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            tableBean = (TableBean)pageContext.getSession().getAttribute(getBeanKey());

            if (tableBean != null) {
                System.err.println("Found a non-null tablebean");
                return SKIP_BODY;
            }
        }
        System.err.println("creating new tablebean");
        tableBean = new TableBean();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        // do all the rendering for this table
        System.err.println("in TableTag:doEndTag");

        if (!beanId.equals("")) {
            System.err.println("setting tablemodel in table");
            System.err.println("!!!!!!!!!!   setting tablemodel in session ");
            store(getBeanKey(), tableBean);
        }

        try {
            JspWriter out = pageContext.getOut();
            System.err.println("printing table " + tableBean.toString());
            out.print(tableBean.toString());
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
