package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TableBean;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

import javax.servlet.jsp.JspException;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableModelTag extends BaseBeanTag {

    public int doStartTag() throws JspException {
        System.err.println("in TableModelTag:doStartTag");
        DefaultTableModel tm = (DefaultTableModel)pageContext.getSession().getAttribute(getBeanKey());
        if (tm == null) {
            tm = new DefaultTableModel();
        }
        pageContext.setAttribute("_tablemodel", tm);
        return super.doStartTag();
    }

    public int doEndTag() throws JspException {
        System.err.println("in TableModelTag:doEndTag");
        TableBean table = (TableBean)pageContext.getAttribute("_table");
        if (table != null) {
            System.err.println("setting tablemodel in table");
            DefaultTableModel tm = (DefaultTableModel)pageContext.getAttribute("_tablemodel");
            if (tm != null) {
                System.err.println("!!!!!!!!!!   setting tablemodel in session " + tm.toString());
                table.setDefaultTableModel(tm);
                store(getBeanKey(), tm);
            }
        }
        System.err.println("after TableModelTag:doEndTag");
        return super.doEndTag();
    }
}
