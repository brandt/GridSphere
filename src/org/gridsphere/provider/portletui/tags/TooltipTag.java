package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.SportletProperties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.portlet.RenderResponse;

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
            RenderResponse renderResponse = (RenderResponse)pageContext.getAttribute("renderResponse");
            String contextPath = "/" + SportletProperties.getInstance().getProperty("gridsphere.deploy");
            renderResponse.setProperty("CSS_HREF", contextPath + "/css/yahoo/container.css");
            renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/dom.js");
            renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/event.js");
            renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/yahoo/container.js");
            out.println("<script type=\"text/javascript\">");
            out.println("var " + id + " = new YAHOO.widget.Tooltip(\"" + id + "\", { context:\"" + name + "\", text:\"" + value + "\" } );");
            out.println("</script>");
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
