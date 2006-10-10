/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: TextTag.java 4883 2006-06-26 23:52:13Z novotny $
 */

package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.BaseComponentBean;
import org.gridsphere.provider.portletui.beans.MessageStyle;
import org.gridsphere.provider.portletui.beans.TextBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.Locale;

/**
 * A <code>TextTag</code> represents text to be displayed
 */
public class TextTag extends BaseComponentTag {

    protected TextBean textBean = null;
    protected String key = null;
    protected String style = MessageStyle.MSG_INFO;
    protected String var = null;

    /**
     * Sets the name of the variable to export as a RenderURL object
     *
     * @param var the name of the variable to export as a RenderURL object
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * Returns the name of the exported RenderURL object
     *
     * @return the exported variable
     */
    public String getVar() {
        return var;
    }

    /**
     * Sets the style of the text: Available styles are
     * <ul>
     * <li>nostyle</li>
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
     * <li>nostyle</li>
     * <li>error</li>
     * <li>info</li>
     * <li>status</li>
     * <li>alert</li>
     * <li>success</li>
     * <li>bold</li>
     * <li>italic</li>
     * <li>underline</li>
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
     *
     * @param key the key used to identify localized text
     */
    public void setKey(String key) {
        this.key = key;
    }

    protected void setBaseComponentBean(BaseComponentBean componentBean) {
        super.setBaseComponentBean(componentBean);
        if (style != null) ((TextBean)componentBean).setStyle(style);
    }

    protected void updateBaseComponentBean(BaseComponentBean componentBean) {
        super.setBaseComponentBean(componentBean);
        if (style != null && ((TextBean)componentBean).getStyle() == null) {
            ((TextBean)componentBean).setStyle(style);
        }
    }

    protected void overrideBaseComponentBean(BaseComponentBean componentBean) {
        super.setBaseComponentBean(componentBean);
        // 1st of property defined in tag put it in bean
        //if (style != null) ((TextBean)componentBean).setStyle(style);
        // 2nd if property exists in bean then use it
        if (((TextBean)componentBean).getStyle() != null) {
            style = ((TextBean)componentBean).getStyle();
        } else {
            ((TextBean)componentBean).setStyle(style);
        }
    }

    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        if (!beanId.equals("")) {
            textBean = (TextBean)getTagBean();
            if (textBean == null) {
                textBean = new TextBean();
                this.setBaseComponentBean(textBean);
            } else {
                //this.overrideBaseComponentBean(textBean);
                this.setBaseComponentBean(textBean);
                textBean.setStyle(style);
                key = textBean.getValue();
            }
        } else {
            textBean = new TextBean();
            this.setBaseComponentBean(textBean);
            textBean.setStyle(style);
            textBean.setCssClass(this.cssClass);
            textBean.setCssStyle(this.cssStyle);
        }

        Locale locale = getLocale();
        textBean.setLocale(locale);

        if (key != null) {
            textBean.setValue(getLocalizedText(key));
        }


        if ((this.getBodyContent() != null) && (value == null)) {
            textBean.setValue(this.getBodyContent().getString());
        }

        if (var == null) {
            try {
                JspWriter out = pageContext.getOut();
                out.print(textBean.toEndString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        } else {
            if (key != null) value = getLocalizedText(key);
            pageContext.setAttribute(var, value, PageContext.PAGE_SCOPE);
        }

        return EVAL_PAGE;
    }

}
