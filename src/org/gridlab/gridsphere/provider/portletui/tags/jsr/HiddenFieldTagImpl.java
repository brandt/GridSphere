/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.jsr;

import org.gridlab.gridsphere.provider.portletui.beans.HiddenFieldBean;
import org.gridlab.gridsphere.provider.portletui.tags.jsr.BaseComponentTagImpl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * A <code>HiddenFieldTag</code> represents a hidden form field element
 */
public class HiddenFieldTagImpl extends BaseComponentTagImpl {

    protected HiddenFieldBean hidden = null;

    public int doStartTag() throws JspException {

        if (!beanId.equals("")) {
            hidden = (HiddenFieldBean) pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
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
