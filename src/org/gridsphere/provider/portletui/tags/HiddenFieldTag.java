/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: HiddenFieldTag.java 4666 2006-03-27 17:47:56Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.HiddenFieldBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * A <code>HiddenFieldTag</code> represents a hidden form field element
 */
public class HiddenFieldTag extends BaseComponentTag {

    protected HiddenFieldBean hidden = null;

    public int doStartTag() throws JspException {

        if (!beanId.equals("")) {
            hidden = (HiddenFieldBean) getTagBean();
            if (hidden == null) {
                hidden = new HiddenFieldBean(beanId);
                this.setBaseComponentBean(hidden);
            } else {
                this.updateBaseComponentBean(hidden);
            }
        } else {
            hidden = new HiddenFieldBean();
            this.setBaseComponentBean(hidden);
        }

        //debug();

        try {
            JspWriter out = pageContext.getOut();
            out.print(hidden.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }

}
