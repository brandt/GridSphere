package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletResponse;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Jan 10, 2003
 * Time: 11:11:04 AM
 * To change this template use Options | File Templates.
 */
public class ActionTag extends TagSupport {

    private String action;
    private String label;
    private PortletURI someURI;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void createActionURI() {
        PortletResponse res = (PortletResponse)pageContext.getAttribute("portletResponse");
        someURI = res.createURI();
        DefaultPortletAction portletAction = new DefaultPortletAction(action);
        pageContext.setAttribute("_action", portletAction);
    }

    public int doStartTag() throws JspException {
        createActionURI();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("<a href= \"");
            DefaultPortletAction action = (DefaultPortletAction)pageContext.getAttribute("_action");
            someURI.addAction(action);
            if (someURI != null) out.print(someURI.toString());
            out.print("\">");
            out.print(label);
            out.print("</a>");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }

}
