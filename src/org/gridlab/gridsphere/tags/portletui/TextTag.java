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

public class TextTag extends BaseComponentTag {

    protected TextBean textBean = null;
    protected String key = null;

    public static final String TEXT_STYLE = "portlet-frame-label";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int doStartTag() throws JspException {
        this.cssStyle = TEXT_STYLE;
        textBean = new TextBean();
        if (!beanId.equals("")) {
           //System.err.println("in TextTag: gettung text bean from session");
           textBean = (TextBean)pageContext.getSession().getAttribute(getBeanKey());
           if (textBean == null) {
               textBean = new TextBean();
           } else {
               key = textBean.getKey();
               value = textBean.getValue();
           }
       }
       return EVAL_BODY_INCLUDE;
    }

   public int doEndTag() throws JspException {

       //System.err.println("in TextTag:doEnd");
       if (value != null) textBean.setValue(value);
       if (key != null) textBean.setKey(key);
       this.setBaseComponentBean(textBean);

       //debug();

        if (textBean.getKey() != null) {
            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            textBean.setValue(bundle.getString(textBean.getKey()));
        }

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(textBean);
            //System.err.println("inTextTag: adding " + textBean.toString());
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(textBean.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }

        if (!beanId.equals("")) {
            //System.err.println("storing bean in the session");
            store(getBeanKey(), textBean);
        }
        return EVAL_BODY_INCLUDE;
    }

}
