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

    public int doEndTag() throws JspException {

        if (!beanId.equals("")) {
            hidden = (HiddenFieldBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (hidden == null) {
                System.err.println("did not find hidden bean!! " + getBeanKey());
                hidden = new HiddenFieldBean(beanId);

                this.setBaseComponentBean(hidden);
            } else {
                System.err.println("found hidden bean!! " + getBeanKey());
                System.err.println("name = " + hidden.getName());
                this.updateBaseComponentBean(hidden);
            }
        } else {
            hidden = new HiddenFieldBean();
            this.setBaseComponentBean(hidden);
        }

        //debug();

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(hidden);
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(hidden.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

}
