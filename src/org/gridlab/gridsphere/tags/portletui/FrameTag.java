package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TableBean;
import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
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
public class FrameTag extends TableTag {

    public int doStartTag() throws JspException {

        if (!beanId.equals("")) {
            tableBean = (FrameBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (tableBean != null) {
                return SKIP_BODY;
            } else {
                tableBean = new FrameBean();
            }
        } else {
            tableBean = new FrameBean();
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        return super.doEndTag();
    }
}
