/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.gs;

import org.gridlab.gridsphere.provider.portletui.tags.ActionFormTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;

/**
 * The <code>ActionFormTag</code> provides a UI form tag that can also include <code>ActionParam</code> tags
 * nested within it.
 */
public class ActionFormTagImpl extends ActionTagImpl implements ActionFormTag {

    protected boolean isMultipart = false;
    protected String method = "POST";

    /**
     * Sets the form method attribute e.g. POST
     *
     * @param method the form method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Returns the form method attribute e.g. POST
     *
     * @return the form method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets whether this form supports multi-part form data as in the case of file uploading
     *
     * @param isMultipart is true if this form supports multi-part form data, false otherwise
     */
    public void setMultiPartFormData(boolean isMultipart) {
        this.isMultipart = isMultipart;
    }

    /**
     * Returns whether this form supports multi-part form data as in the case of file uploading
     *
     * @return true if this form supports multi-part form data, false otherwise
     */
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
            out.print(createActionURI().toString());
            out.print("\" method=\"");
            out.print(method);
            out.print("\"");
            if (isMultipart) {
                out.print(" enctype=\"multipart/form-data\"");
            }
            String noName = (String) pageContext.getAttribute("gs_formNumber", PageContext.REQUEST_SCOPE);
            if (name == null) {
                // use a counter to continually increase form number to provide unique form name
                int ctr = 0;
                if (noName == null) {
                    ctr = 1;
                } else {
                    ctr = Integer.parseInt(noName) + 1;
                }
                noName = String.valueOf(ctr);

                pageContext.setAttribute("gs_formNumber", noName, PageContext.REQUEST_SCOPE);
                name = "form" + noName;
            }

            out.print(" name=\"" + name + "\"");

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
