/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.ActionLinkBean;
import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import java.util.*;

public class ActionLinkTag extends ActionTag {

    protected ActionLinkBean actionlink = null;
    protected String value = "";

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the label of the beans.
     * @return label of the beans
     */
    public String getValue() {
        return value;
    }

    public void setActionLinkBean(ActionLinkBean actionlink) {
        this.actionlink = actionlink;
    }

    public ActionLinkBean getActionLinkBean() {
        return actionlink;
    }

    public int doStartTag() throws JspException {
        paramBeans = new ArrayList();
        createActionURI();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspTagException {

        if (!beanId.equals("")) {
            actionlink = (ActionLinkBean)pageContext.getSession().getAttribute(getBeanKey());
            //System.err.println("storing bean in the session");
            store(getBeanKey(), actionlink);
        }
        if (actionlink == null) {
            actionlink = new ActionLinkBean();
        }
        Iterator it = paramBeans.iterator();
        while (it.hasNext()) {
            ActionParamBean pbean = (ActionParamBean)it.next();
            portletAction.addParameter(pbean.getName(), pbean.getValue());
        }

        actionlink.setAction(createActionURI());
        actionlink.setValue(value);
        try {
            JspWriter out = pageContext.getOut();
            out.println(actionlink);
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
