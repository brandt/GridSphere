package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.FrameBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TableBean;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ArrayList;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class FrameTag extends TableTag {

    protected String style = TextBean.MSG_INFO;
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
            if (tableBean == null) {
                tableBean = new FrameBean(beanId);
                this.setBaseComponentBean(tableBean);
            } else {
                String key = tableBean.getKey();
                if (key != null) {

                    Locale locale = pageContext.getRequest().getLocale();
                    ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
                    tableBean.setValue(bundle.getString(tableBean.getKey()));
                    tableBean.setCssStyle(style);
                }

            }
        } else {
            tableBean = new FrameBean();

            this.setBaseComponentBean(tableBean);
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
        }
        try {
            JspWriter out = pageContext.getOut();
            out.print(tableBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            out.print(tableBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
