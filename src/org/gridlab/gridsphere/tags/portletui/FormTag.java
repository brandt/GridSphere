package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.Map;

public class FormTag extends BodyTagSupport {

    protected boolean isMultipart = false;
    protected String action = new String();
    protected String method = "POST";
    protected PortletURI someURI;
    protected String name = new String();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAction(String action) {
        this.action = action;
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

    public void setMultiPartFormData(boolean isMultipart) {
        this.isMultipart = isMultipart;
    }

    public boolean getMultiPartFormData() {
        return isMultipart;
    }

    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspTagException {
        PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
        someURI = res.createURI();

        DefaultPortletAction portletAction = null;
        if (action != null) {

            portletAction = new DefaultPortletAction(action);

            Map paramMap = (Map)pageContext.getAttribute("_params");
            if (paramMap != null) {
                portletAction.setParameters(paramMap);
            }
        } else {
            portletAction = new DefaultPortletAction("");
        }

        try {
            JspWriter out = pageContext.getOut();

            out.print("<form ");
            out.print("action=\"");
            //DefaultPortletAction action = (DefaultPortletAction) pageContext.getAttribute("_action");
            someURI.addAction(portletAction);
            if (someURI != null) out.print(someURI.toString());
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
