package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.beans.TableRowBean;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

import javax.servlet.jsp.JspException;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableRowTag extends BaseBeanTag {

    public int doStartTag() throws JspException {
        System.err.println("in TableRowTag:doStartTag");
        TableRowBean trb = new TableRowBean();
        pageContext.setAttribute("_tablerow", trb);
        return super.doStartTag();
    }

    public int doEndTag() throws JspException {
        System.err.println("in TableRowTag:doEndTag");

        DefaultTableModel tm = (DefaultTableModel)pageContext.getAttribute("_tablemodel");
        if (tm != null) {
            System.err.println("setting tablerow in tablemodel");
            TableRowBean trb = (TableRowBean)pageContext.getAttribute("_tablerow");
            if (trb != null) {
                tm.addTableRowBean(trb);
                pageContext.setAttribute("_tablemodel", tm);
            }
        }
        return super.doEndTag();
    }
}
