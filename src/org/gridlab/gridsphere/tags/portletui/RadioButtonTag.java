/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.RadioButtonBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class RadioButtonTag extends BaseComponentTag {

    public static final String RADIO_STYLE = "portlet-frame-text";

    protected RadioButtonBean radiobutton = null;
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
            radiobutton = (RadioButtonBean)pageContext.getSession().getAttribute(getBeanKey());
            if (radiobutton == null) {
                radiobutton = new RadioButtonBean();
            }
        } else {
            radiobutton = new RadioButtonBean();
        }
        radiobutton.setCssStyle(RADIO_STYLE);
        radiobutton.setSelected(selected);
        this.setBaseComponentBean(radiobutton);

        if (!beanId.equals("")) {
            //System.err.println("storing bean in the session");
            store(getBeanKey(), radiobutton);
        }
        //debug();

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(radiobutton);
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(radiobutton.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

}
