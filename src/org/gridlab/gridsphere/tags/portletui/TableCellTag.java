package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TableRowBean;
import org.gridlab.gridsphere.provider.portletui.beans.TagBeanContainer;
import org.gridlab.gridsphere.provider.portletui.beans.TagBean;
import org.gridlab.gridsphere.provider.portletui.beans.TableCellBean;

import javax.servlet.jsp.JspException;
import java.util.List;
import java.util.Iterator;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableCellTag extends ContainerTag {

    protected TableCellBean cellBean = null;

    public void setCellBean(TableCellBean cellBean) {
        this.cellBean = cellBean;
    }

    public TableCellBean getCellBean() {
        return cellBean;
    }

    public int doStartTag() throws JspException {
        // VERY IMPORTANT that cellBean initialized for ech new tag-- do not set anything
        // as global variable!

        super.doStartTag();
        cellBean = new TableCellBean();
        System.err.println("in TableCellTag:doStartTag");
        TableRowTag rowTag = (TableRowTag)getParent();
        if (rowTag == null) return SKIP_BODY;
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        System.err.println("in TableCellTag:doEndTag");
        TableRowTag rowTag = (TableRowTag)getParent();
        if (rowTag != null) {
            TableRowBean trb = rowTag.getTableRowBean();
            System.err.println("setting tablecells in tablerow");

            Iterator it = list.iterator();
            while (it.hasNext()) {
                TagBean tagBean = (TagBean)it.next();
                System.err.println("adding tagbean " + tagBean.toString());
                cellBean.addTagBean(tagBean);
                cellBean.setCssStyle(tagBean.getCssStyle());
            }
            trb.addTableCellBean(cellBean);
            rowTag.setTableRowBean(trb);
        }
        return EVAL_BODY_INCLUDE;
    }
}
