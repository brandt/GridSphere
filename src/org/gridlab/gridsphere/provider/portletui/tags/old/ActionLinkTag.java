/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.old;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.provider.ui.beans.ActionLinkBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

public class ActionLinkTag extends BaseTag {

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

    /*
    public ActionLinkBean getActionLink() {
        return actionlink;
    }

    public void setActionLink(ActionLinkBean actionlink) {
        this.actionlink = actionlink;
    }
    */

    public void createActionURI() {
        PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
        someURI = res.createURI();
        if (bean.equals("")) {
            this.htmlelement = new ActionLinkBean(someURI, action, label);
        }
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
        actionlink.setText(label);
        try {
            JspWriter out = pageContext.getOut();
            out.println(actionlink);
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
