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

    public int doStartTag() throws JspException {
        TableBean tb = new TableBean();
        pageContext.setAttribute("_table", tb);
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        // do all the rendering for this table
        try {
            JspWriter out = pageContext.getOut();
            TableBean table = (TableBean)pageContext.getAttribute("_table");
            if (table != null) {
                System.err.println("printing table " + table.toString());
                out.print(table.toString());
            } else {
                System.err.println("no  table found to print!!!!");
            }
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
