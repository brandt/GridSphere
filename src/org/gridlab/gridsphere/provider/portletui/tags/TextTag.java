/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A <code>TextTag</code> represents text to be displayed
 */
public class TextTag extends BaseComponentTag {

    protected TextBean textBean = null;
    protected String key = null;
    protected String style = TextBean.MSG_INFO;

    /**
     * Sets the style of the text: Available styles are
     * <ul>
     * <li>error</li>
     * <li>info</li>
     * <li>status</li>
     * <li>alert</li>
     * <li>success</li>
     *
     * @param style the text style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Returns the style of the text: Available styles are
     * <ul>
     * <li>error</li>
     * <li>info</li>
     * <li>status</li>
     * <li>alert</li>
     * <li>success</li>
     *
     * @return the text style
     */
    public String getStyle() {
        return style;
    }


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
     * @param key the key used to identify localized text
     */
    public void setKey(String key) {
        this.key = key;
    }

    public int doEndTag() throws JspException {

        if (!beanId.equals("")) {
            textBean = (TextBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (textBean == null) {
                return EVAL_PAGE;
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
