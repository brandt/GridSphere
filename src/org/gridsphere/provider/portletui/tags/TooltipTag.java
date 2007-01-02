package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.TooltipBean;

import javax.portlet.RenderResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * The <code>TableRowTag</code> represents a table row element that is conatined within a <code>TableTag</code>
 * and itself may contain <code>TableCellTag</code>s
 */
public class TooltipTag extends BaseComponentTag {

    public void release() {
        super.release();
    }

    public int doStartTag() throws JspException {
        JspWriter out;
        if (key != null) value = getLocalizedText(key);
        try {
            out = pageContext.getOut();
            TooltipBean tooltip = new TooltipBean();
            RenderResponse res = (RenderResponse) pageContext.getAttribute("renderResponse");
            tooltip.setRenderResponse(res);
            tooltip.setId(id);
            tooltip.setName(name);
            tooltip.setValue(value);
            out.print(tooltip.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        release();
        return EVAL_PAGE;
    }
}
