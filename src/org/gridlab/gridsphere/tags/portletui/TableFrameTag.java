package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TableBean;
import org.gridlab.gridsphere.provider.portletui.beans.TableFrameBean;
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
public class TableFrameTag extends TableTag {

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            tableBean = (TableFrameBean)pageContext.getSession().getAttribute(getBeanKey());
            if (tableBean != null) {
                System.err.println("Found a non-null tableframebean");
                return SKIP_BODY;
            }
        }
        System.err.println("creating new tableframebean");
        tableBean = new TableFrameBean();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        return super.doEndTag();
    }
}
