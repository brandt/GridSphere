/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.tags.web.element.ActionLinkBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ActionTag extends TagSupport {

    private String action;
    private String label;
    private PortletURI someURI;
    private ActionLinkBean actionlink = new ActionLinkBean();

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

    public ActionLinkBean getActionlink() {
        return actionlink;
    }

    public void setActionlink(ActionLinkBean actionlink) {
        this.actionlink = actionlink;
    }

    public void createActionURI() {
        PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
        someURI = res.createURI();
        DefaultPortletAction portletAction = new DefaultPortletAction(action);
        pageContext.setAttribute("_action", portletAction);
    }

    public int doStartTag() throws JspException {
        createActionURI();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspTagException {
        DefaultPortletAction action = (DefaultPortletAction) pageContext.getAttribute("_action");
        someURI.addAction(action);
        actionlink.setLink(someURI.toString());
        actionlink.setLabel(label);
        try {
            JspWriter out = pageContext.getOut();
            out.println(actionlink);
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
