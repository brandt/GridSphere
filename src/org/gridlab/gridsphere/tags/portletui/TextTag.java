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
    protected boolean isError = false;

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public boolean getError() {
        return isError;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int doStartTag() throws JspException {

        if (!beanId.equals("")) {
           //System.err.println("in TextTag: gettung text bean from session");
           textBean = (TextBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
           if (textBean == null) {
               textBean = new TextBean();
           } else {
               key = textBean.getKey();
               value = textBean.getValue();
           }
       } else {
            textBean = new TextBean();
       }
       return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        //System.err.println("in TextTag:doEnd");
        if (!beanId.equals("")) {
            if ((key != null) && (textBean.getKey() == null)) {
                textBean.setKey(key);
            }
            this.updateBaseComponentBean(textBean);
        } else {
            if (key != null) textBean.setKey(key);
            textBean.setError(isError);
            this.setBaseComponentBean(textBean);
        }
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

        return EVAL_BODY_INCLUDE;
    }

}
