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

    public String name = null;
    public String key = null;
    public String value = "";
    public String id = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void release() {
        super.release();
    }

    public int doStartTag() throws JspException {
        JspWriter out;
        if (key != null) value = getLocalizedText(key);
        try {
            out = pageContext.getOut();
            TooltipBean tooltip = new TooltipBean();
            RenderResponse res = (RenderResponse)pageContext.getAttribute("renderResponse");
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
        return EVAL_PAGE;
    }
}
