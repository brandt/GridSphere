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
import java.util.List;
import java.util.Iterator;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class FrameTag extends TableTag {

    public static final String FRAME_WIDTH = "100%";
    public static final String FRAME_SPACING = "1";

    public int doStartTag() throws JspException {
        this.width = FRAME_WIDTH;
        this.cellSpacing = FRAME_SPACING;

        if (!beanId.equals("")) {
            tableBean = (FrameBean)pageContext.getSession().getAttribute(getBeanKey());
            if (tableBean != null) {
                //System.err.println("Found a non-null tableframebean");
                return SKIP_BODY;
            } else {
                tableBean = new FrameBean();
            }
        } else {
            //System.err.println("creating new tableframebean");
            tableBean = new FrameBean();
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        return super.doEndTag();
    }
}
