/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.tags.ui.BaseTag;
import org.gridlab.gridsphere.provider.portletui.beans.ActionSubmitBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class ActionSubmitTag extends BaseBeanTag {

    protected ActionSubmitBean actionSubmitBean = new ActionSubmitBean();

    public void setActionSubmitBean(ActionSubmitBean actionSubmitBean) {
        this.actionSubmitBean = actionSubmitBean;
    }

    public ActionSubmitBean getActionSubmitbean() {
        return actionSubmitBean;
    }

    public String getName() {
        return actionSubmitBean.getName();
    }

    public void setName(String name) {
        this.actionSubmitBean.setName(name);

    }

    public String getValue() {
        return actionSubmitBean.getValue();
    }

    public void setValue(String value) {
        this.actionSubmitBean.setValue(value);
    }

    public String getAction() {
        return actionSubmitBean.getAction();
    }

    public void setAction(String action) {
        this.actionSubmitBean.setAction(action);
    }

    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        FormTag formTag = (FormTag)getParent();
        if (formTag != null) {
            formTag.setAction(actionSubmitBean.getAction());
        }
        try {
            JspWriter out = pageContext.getOut();
            out.print(actionSubmitBean.toString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
