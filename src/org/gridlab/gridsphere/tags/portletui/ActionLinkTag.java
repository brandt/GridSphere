/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.ui.beans.ActionLinkBean;
import org.gridlab.gridsphere.provider.portletui.beans.ParamBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import java.util.*;

public class ActionLinkTag extends ActionTag {

    protected ActionLinkBean actionlink = new ActionLinkBean();
    protected String link = "";

    public void setActionLinkBean(ActionLinkBean actionlink) {
        this.actionlink = actionlink;
    }

    public ActionLinkBean getActionLinkBean() {
        return actionlink;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public int doStartTag() throws JspException {
        paramBeans.clear();
        createActionURI();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspTagException {

        Iterator it = paramBeans.iterator();
        while (it.hasNext()) {
            ParamBean pbean = (ParamBean)it.next();
            portletAction.addParameter(pbean.getName(), pbean.getValue());
        }

        actionlink.setLink(createActionURI());
        actionlink.setText(link);
        try {
            JspWriter out = pageContext.getOut();
            out.println(actionlink);
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
