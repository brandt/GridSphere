/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.tags.web.element.ActionLinkBean;
import org.gridlab.gridsphere.tags.web.element.RadioButtonBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.ArrayList;

public class ActionLinkTag extends BaseTag {

    private String action = "action";
    private String label = "label";
    //private PortletURI someURI;
    //private ActionLinkBean actionlink = new ActionLinkBean();

    public void setActionName(String action) {
        this.action = action;
    }

    public String getActionName() {
        return action;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int doStartTag() throws JspException {
        PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
        if (bean.equals("")) {
            this.htmlelement = new ActionLinkBean(action, label, new ArrayList(), res);
        }
        return super.doStartTag();
    }
}
