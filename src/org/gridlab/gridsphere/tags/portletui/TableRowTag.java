package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.beans.TableRowBean;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

import javax.servlet.jsp.JspException;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableRowTag extends BaseBeanTag {

    protected TableRowBean trb = null;

    public void setTableRowBean(TableRowBean tableRowBean) {
        trb = tableRowBean;
    }

    public TableRowBean getTableRowBean() {
        return trb;
    }

    public int doStartTag() throws JspException {
        trb = new TableRowBean();
        System.err.println("in TableRowTag:doStartTag");
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        System.err.println("in TableRowTag:doEndTag");
        TableTag tableTag = (TableTag)getParent();
        if (tableTag != null) {
            System.err.println("in TableRowTag: this is the rowbean" + trb.toString());

            DefaultTableModel tableModel = tableTag.getTableModel();
            tableModel.addTableRowBean(trb);
        }

        return EVAL_PAGE;
    }
}
