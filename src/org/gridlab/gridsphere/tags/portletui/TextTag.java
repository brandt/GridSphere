/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.Locale;
import java.util.ResourceBundle;

public class TextTag extends BaseComponentTag {

    protected TextBean textBean = null;
    protected String key = null;
    protected String style = TextBean.MSG_INFO;

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int doEndTag() throws JspException {

        if (!beanId.equals("")) {
            textBean = (TextBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (textBean == null) {
                textBean = new TextBean(beanId);
                this.setBaseComponentBean(textBean);
                textBean.setStyle(style);
            }
        } else {
            textBean = new TextBean();
            this.setBaseComponentBean(textBean);
            textBean.setStyle(style);
        }


        if (key != null) {
            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            textBean.setValue(bundle.getString(key));
        }

        if ((bodyContent != null) && (value == null)) {
            textBean.setValue(bodyContent.getString());
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(textBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }

}
