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

    protected String key = "";

    protected ActionSubmitBean actionSubmitBean = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int doStartTag() throws JspException {
        actionSubmitBean = new ActionSubmitBean();
        if (!beanId.equals("")) {
            actionSubmitBean = (ActionSubmitBean)pageContext.getAttribute(getBeanKey());
        }
        if (actionSubmitBean == null) actionSubmitBean = new ActionSubmitBean();
        paramBeans = new ArrayList();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        actionSubmitBean.setName(createActionURI());
        if (!key.equals("")) {
            actionSubmitBean.setKey(key);
            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            String localizedText = bundle.getString(actionSubmitBean.getKey());
            if (localizedText != null) {
                value = localizedText;
            }
        }

        if (!beanId.equals("")) {
            this.updateBaseComponentBean(actionSubmitBean);
        } else {
            this.setBaseComponentBean(actionSubmitBean);
        }

        actionSubmitBean.setAction(action);

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
