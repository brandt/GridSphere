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

    public void setAction(String action) {
        this.action =action;
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
        PortletURI someURI = res.createURI();
        DefaultPortletAction anAction = new DefaultPortletAction(action);
        someURI.addAction(anAction);
        pageContext.setAttribute("_uri", someURI);
    }

    public int doStartTag() throws JspException {
        createActionURI();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("<a href= \"");
            PortletURI someURI = (PortletURI)pageContext.getAttribute("_uri");
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
