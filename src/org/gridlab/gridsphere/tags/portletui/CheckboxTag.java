/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class CheckboxTag extends BaseComponentTag {

    protected CheckBoxBean checkbox = null;
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
            checkbox = (CheckBoxBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (checkbox == null) {
                System.err.println("did not find checkbox bean!! " + getBeanKey());
                checkbox = new CheckBoxBean(beanId);
                checkbox.setSelected(selected);
                this.setBaseComponentBean(checkbox);
            } else {
                System.err.println("found checkbox bean!! " + getBeanKey());
                System.err.println("isselected = " + checkbox.isSelected());
                System.err.println("name = " + checkbox.getName());
                this.updateBaseComponentBean(checkbox);
            }
        } else {
            checkbox = new CheckBoxBean();
            checkbox.setSelected(selected);
            this.setBaseComponentBean(checkbox);
        }

        //debug();

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(checkbox);
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(checkbox.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

}
