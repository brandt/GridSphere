/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class TextTag extends BaseBeanTag {

    protected TextBean textBean = null;
    protected String key = null;
    protected String text = "";

    public static final String TEXT_STYLE = "portlet-frame-label";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int doStartTag() throws JspException {
        System.err.println("in TextTag:doStart");
        textBean = new TextBean();
        textBean.setText(text);
        textBean.setKey(key);
        textBean.setCssStyle(TEXT_STYLE);
        TextBean tb = (TextBean)pageContext.getSession().getAttribute(getBeanKey());
        if (tb != null) {
            System.err.println("found a text bean in the session");
            textBean = tb;
        }
        if (!beanId.equals("")) {
            System.err.println("storing bean in the session");
            store(getBeanKey(), tb);
        }
        //debug();

        if (textBean.getKey() != null) {
            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            textBean.setText(bundle.getString(textBean.getKey()));
        }

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(textBean);
            System.err.println("inTextTag: adding " + textBean.toString());
        } else {
            try {
                System.err.println("writing text");
                JspWriter out = pageContext.getOut();
                out.print(textBean.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

}
