package org.gridlab.gridsphere.tags.portletui;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
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
        System.err.println("ActionFormTag: inDoStartTag");
        paramBeans = new ArrayList();

        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        System.err.println("ActionFormTag: inDoEndTag");
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
            String noName = (String)pageContext.getAttribute("gs_formNumber", PageContext.REQUEST_SCOPE);
            if (name == null) {
                // use a counter to continually increase form number to provide unique form name
                int ctr = 0;
                if (noName == null) {
                    ctr = 1;
                } else {

                    ctr = new Integer(noName).intValue() + 1;
                }
                noName = String.valueOf(ctr);

                pageContext.setAttribute("gs_formNumber", noName, PageContext.REQUEST_SCOPE);
                name = "form" + noName;
            }

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
        name = null;
        return EVAL_PAGE;
    }

}
