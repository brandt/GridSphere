package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.beans.*;
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
public class TablePaneTag extends ContainerTag {

    protected TablePaneBean tablePaneBean = null;

    public void setTablePaneBean(TablePaneBean tablePaneBean) {
        this.tablePaneBean = tablePaneBean;
    }

    public TablePaneBean getTablePaneBean() {
        return tablePaneBean;
    }

    public int doStartTag() throws JspException {
        super.doStartTag();
        tablePaneBean = new TablePaneBean();
        if (!beanId.equals("")) {
            TablePaneBean tpb = (TablePaneBean)pageContext.getSession().getAttribute(getBeanKey());
            if (tpb != null) {
                System.err.println("Found a non-null tablebean");
                tablePaneBean = tpb;
                return SKIP_BODY;
            }
        }
        System.err.println("creating new tablebean");
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        // do all the rendering for this table
        System.err.println("in TablePaneTag:doEndTag");

        // add all components to pane
        Iterator it = list.iterator();
        while (it.hasNext()) {
            TagBean t = (TagBean)it.next();
            tablePaneBean.addTagBean(t);
        }

        if (!beanId.equals("")) {
            System.err.println("setting tablemodel in table");
            System.err.println("!!!!!!!!!!   setting tablemodel in session ");
            store(getBeanKey(), tablePaneBean);
        }

        try {
            JspWriter out = pageContext.getOut();
            System.err.println("printing table pane " + tablePaneBean.toString());
            out.print(tablePaneBean.toString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }
}
