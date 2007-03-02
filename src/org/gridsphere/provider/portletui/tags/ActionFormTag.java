/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: ActionFormTag.java 4883 2006-06-26 23:52:13Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.RenderResponseImpl;
import org.gridsphere.portlet.impl.SportletProperties;

import javax.portlet.RenderResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;

/**
 * The <code>ActionFormTag</code> provides a UI form tag that can also include <code>ActionParam</code> tags
 * nested within it.
 */
public class ActionFormTag extends ActionTag {

    protected boolean isMultipart = false;
    // 'POST' replaced by 'post' for XHTML 1.0 Strict compliance
    protected String method = "post";

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
            //if (onSubmit != null) out.print(" onsubmit=\"" + onSubmit + "\" ");

            out.print("action=\"");

            // if using JSR then create render link
            RenderResponse res = (RenderResponse) pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);
            String actionStr;
            if (res != null) {
                if (res instanceof RenderResponseImpl) {
                    actionStr = createRenderURI();
                } else {
                    // if non-GS container this will have to be an ActionURL
                    actionStr = createActionURI();
                }
            } else {
                actionStr = createActionURI();
            }

            out.print(actionStr);


            out.print("\" method=\"");
            out.print(method);
            out.print("\"");
            if (isMultipart) {
                out.print(" enctype=\"multipart/form-data\"");
            }
            if (name == null) {
                name = "form" + this.getUniqueId("gs_formNumber");
            }

            if (this.cssStyle != null) {
                out.print(" style=\"" + this.cssStyle + "\"");
            }
            if (this.cssClass != null) {
                out.print(" class=\"" + this.cssClass + "\"");
            }

            out.print(" id=\"" + name + "\"");
            out.print(" name=\"" + name + "\"");
            if (onSubmit == null) {
                out.print(" onsubmit=\"return validate( " + name + " );\" ");
            } else {
                out.print(" onsubmit=\"" + onSubmit + "\" ");
            }
            if (onReset != null) out.print(" onreset=\"" + onReset + "\" ");
            out.println(">");

            // write out rest of body
            bodyContent.writeOut(getPreviousOut());
            // end form
            out.print("</form>");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        // nulling these out is ABSOLUTELY NECESSARY or weird
        // interactins happen with actionsubmit nested inside actionform!
        name = null;
        action = null;
        return EVAL_PAGE;
    }

    public void release() {
        super.release();
    }

}
