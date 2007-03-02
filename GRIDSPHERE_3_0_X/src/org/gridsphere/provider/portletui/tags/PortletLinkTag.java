package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.PortletBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * The <code>TableRowTag</code> represents a table row element that is conatined within a <code>TableTag</code>
 * and itself may contain <code>TableCellTag</code>s
 */
public class PortletLinkTag extends DialogTag {

    protected String theme = "default";
    protected String renderKit = "brush";
    protected String portletId = "";

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getRenderKit() {
        return renderKit;
    }

    public void setRenderKit(String renderKit) {
        this.renderKit = renderKit;
    }

    public String getPortletId() {
        return portletId;
    }

    public void setPortletId(String portletId) {
        this.portletId = portletId;
    }

    public int doStartTag() throws JspException {
        isLink = true;
        PortletBean portlet = new PortletBean();
      
        setProperties(portlet);

        portlet.setRenderKit(renderKit);
        portlet.setTheme(theme);
        portlet.setPortletId(portletId);

        JspWriter out;
        try {
            out = pageContext.getOut();
            out.print(portlet.toStartString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }


}
