package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class FrameTag extends TableTag {

    protected String style = TextBean.TEXT_LABEL_STYLE;
    protected String key = null;
    protected String value = null;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public int doStartTag() throws JspException {

        if (!beanId.equals("")) {
            tableBean = (FrameBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (tableBean != null) {
                return SKIP_BODY;
            } else {
                tableBean = new FrameBean();
            }
        } else {
            tableBean = new FrameBean();
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        if (key != null) {
            tableBean.setKey(key);
            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            tableBean.setValue(bundle.getString(tableBean.getKey()));
            tableBean.setCssStyle(style);
        }
        if (value != null) {
            tableBean.setValue(value);
            tableBean.setCssStyle(style);
        }

        return super.doEndTag();
    }
}
