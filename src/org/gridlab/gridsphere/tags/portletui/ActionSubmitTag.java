/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.ActionSubmitBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.Locale;
import java.util.ResourceBundle;

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

    public String getKey() {
        return actionSubmitBean.getKey();
    }

    public void setKey(String key) {
        this.actionSubmitBean.setKey(key);
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
        FormTag formTag = (FormTag)findAncestorWithClass(this, FormTag.class);
        if (formTag != null) {
            formTag.setAction(actionSubmitBean.getAction());
        }

        if (!actionSubmitBean.getKey().equals("")) {
            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            String localizedText = bundle.getString(actionSubmitBean.getKey());
            if (localizedText != null) {
                actionSubmitBean.setValue(localizedText);
            }
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
