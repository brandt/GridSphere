package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import java.util.ArrayList;

public class ActionFormTag extends ActionTag {

    protected boolean isMultipart = false;
    protected String method = "POST";
    protected String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int doEndTag() throws JspTagException {
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
            if (!name.equals("")) {
                out.print(" name=\""+name+"\"");
            }
            out.println(">");

            bodyContent.writeOut(getPreviousOut());
            out.print("</form>");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }

}
