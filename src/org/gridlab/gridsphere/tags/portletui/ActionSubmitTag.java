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
import java.util.ArrayList;

public class ActionSubmitTag extends ActionTag {

    public static final String SUBMIT_STYLE = "portlet-frame-text";

    protected String key = "";

    protected ActionSubmitBean actionSubmitBean = null;

    public void setActionSubmitBean(ActionSubmitBean actionSubmitBean) {
        this.actionSubmitBean = actionSubmitBean;
    }

    public ActionSubmitBean getActionSubmitbean() {
        return actionSubmitBean;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public int doStartTag() throws JspException {
        actionSubmitBean = new ActionSubmitBean();
        if (!beanId.equals("")) {
            actionSubmitBean = (ActionSubmitBean)pageContext.getSession().getAttribute(getBeanKey());
            if (actionSubmitBean == null) actionSubmitBean = new ActionSubmitBean();
        }
        actionSubmitBean.setCssStyle(SUBMIT_STYLE);
        paramBeans = new ArrayList();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {



        /*
        ActionFormTag formTag = (ActionFormTag)findAncestorWithClass(this, ActionFormTag.class);
        if (formTag != null) {
            formTag.setAction(actionSubmitBean.getAction());
        }
        */

        if (!key.equals("")) {
            actionSubmitBean.setKey(key);

            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            String localizedText = bundle.getString(actionSubmitBean.getKey());
            if (localizedText != null) {
                actionSubmitBean.setValue(localizedText);
            }
        }

        actionSubmitBean.setAction(action);
        this.setBaseComponentBean(actionSubmitBean);

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(actionSubmitBean);
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(actionSubmitBean.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_PAGE;
    }
}
