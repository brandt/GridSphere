package org.gridlab.gridsphere.provider.portletui.tags.gs;

import org.gridlab.gridsphere.provider.portletui.beans.MessageBoxBean;
import org.gridlab.gridsphere.provider.portletui.tags.gs.BaseComponentTagImpl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class MessageBoxTagImpl extends BaseComponentTagImpl {

    protected MessageBoxBean messageBoxBean = null;

    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        if (!beanId.equals("")) {
            messageBoxBean = (MessageBoxBean) pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (messageBoxBean == null) {
                messageBoxBean = new MessageBoxBean();
                this.setBaseComponentBean(messageBoxBean);
            } else {
                this.updateBaseComponentBean(messageBoxBean);
            }
        } else {
            messageBoxBean = new MessageBoxBean();
            this.setBaseComponentBean(messageBoxBean);
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(messageBoxBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }


        return EVAL_PAGE;
    }


}
