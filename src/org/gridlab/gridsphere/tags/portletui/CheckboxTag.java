/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class CheckboxTag extends BaseComponentTag {

    public static final String CHECKBOX_STYLE = "portlet-frame-text";

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
            checkbox = (CheckBoxBean)pageContext.getSession().getAttribute(getBeanKey());
            if (checkbox == null) {
                checkbox = new CheckBoxBean();
            }
        } else {
            checkbox = new CheckBoxBean();
        }
        checkbox.setCssStyle(CHECKBOX_STYLE);
        checkbox.setSelected(selected);
        this.setBaseComponentBean(checkbox);

        if (!beanId.equals("")) {
            //System.err.println("storing bean in the session");
            store(getBeanKey(), checkbox);
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
