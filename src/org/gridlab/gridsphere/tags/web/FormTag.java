package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Jan 10, 2003
 * Time: 11:11:04 AM
 * To change this template use Options | File Templates.
 */
public class FormTag extends TagSupport {

    private String action;
    private String method = "POST";

    public void setAction(String action) {
        this.action =action;
    }

    public String getAction() {
        return action;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public String makeActionURI() {
        /*
        PortletURI someURI = pageContext.getResponse().createURI();
        DefaultPortletAction anAction = new DefaultPortletAction(action);
        someURI.addAction(anAction);
        return someURI.toString());
        */
        return "h";
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("<form ");
            out.print("action=\"");
            out.print(action);
            out.print("\" method=\"");
            out.print(method);
            out.print("\">");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();

            out.print("</form>");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }

}
