package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.MessageBoxBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class MessageBoxTag extends BaseComponentTag {

    protected MessageBoxBean messageBoxBean = null;
    protected String key = null;

    /**
     * Returns the key used to identify localized text
     *
     * @return the key used to identify localized text
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key used to identify localized text
     *
     * @param key the key used to identify localized text
     */
    public void setKey(String key) {
        this.key = key;
    }

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
                key = messageBoxBean.getKey();
            }
        } else {
            messageBoxBean = new MessageBoxBean();
            this.setBaseComponentBean(messageBoxBean);
        }

        if (key != null) {
            messageBoxBean.setValue(getLocalizedText(key));
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
