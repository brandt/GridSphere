package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import java.util.ArrayList;

public class ActionFormTag extends ActionTag {

    protected boolean isMultipart = false;
    protected String method = "POST";

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMultiPartFormData(boolean isMultipart) {
        this.isMultipart = isMultipart;
    }

    public boolean getMultiPartFormData() {
        return isMultipart;
    }

    public int doStartTag() throws JspException {
        paramBeans = new ArrayList();
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();

            out.print("<form ");
            out.print("action=\"");
            out.print(createActionURI());
            out.print("\" method=\"");
            out.print(method);
            out.print("\"");
            if (isMultipart) {
                out.print(" enctype=\"multipart/form-data\"");
            }
            if (name == null) name = "aform";

            out.print(" name=\""+name+"\"");

            out.println(">");
            // add JS info
            out.println("<input name=\"JavaScript\" value=\"\" type=\"hidden\">");

            out.println("<script language=\"JavaScript\">");
            out.println("document." + name + ".JavaScript.value = \"enabled\";");
            out.println("</script>");

            // write out rest of body
            bodyContent.writeOut(getPreviousOut());
            // end form
            out.print("</form>");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }

}
