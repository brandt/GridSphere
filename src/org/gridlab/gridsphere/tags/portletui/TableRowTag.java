package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.beans.TableRowBean;
import org.gridlab.gridsphere.provider.portletui.beans.TagBean;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

import javax.servlet.jsp.JspException;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableRowTag extends ContainerTag {

    protected TableRowBean rowBean = null;

    public void setTableRowBean(TableRowBean tableRowBean) {
        rowBean = tableRowBean;
    }

    public TableRowBean getTableRowBean() {
        return rowBean;
    }

    public int doStartTag() throws JspException {
        System.err.println("in TableRowTag:doStartTag");
        list = new Vector();

        rowBean = new TableRowBean();
        if (!beanId.equals("")) {
            rowBean = (TableRowBean)pageContext.getSession().getAttribute(getBeanKey());
            if (rowBean == null) rowBean = new TableRowBean();
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        //System.err.println("in TableRowTag:doEndTag");
        TableTag tableTag = (TableTag)getParent();
        if (tableTag != null) {
            DefaultTableModel tableModel = tableTag.getTableModel();

            Iterator it = list.iterator();
            while (it.hasNext()) {
                TagBean tagBean = (TagBean)it.next();
                rowBean.addTagBean(tagBean);
                rowBean.setCssStyle(tagBean.getCssStyle());
            }

            tableModel.addTableRowBean(rowBean);

            tableTag.setTableModel(tableModel);
        }

        return EVAL_PAGE;
    }
}
