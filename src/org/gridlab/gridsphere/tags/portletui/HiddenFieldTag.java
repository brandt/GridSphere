/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.HiddenFieldBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class HiddenFieldTag extends BaseComponentTag {

    protected HiddenFieldBean hidden = null;
    protected boolean selected = false;

    /**
     * Sets the selected status of the bean.
     * @param flag status of the bean
     */
    public void setSelected(boolean flag) {
        this.selected = flag;
    }

    /**
     * Returns the selected status of the bean
     * @return selected status
     */
    public boolean isSelected() {
        return selected;
    }

    public int doStartTag() throws JspException {

        if (!beanId.equals("")) {
            hidden = (HiddenFieldBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
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
