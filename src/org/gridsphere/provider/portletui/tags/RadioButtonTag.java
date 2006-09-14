/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: RadioButtonTag.java 4666 2006-03-27 17:47:56Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.RadioButtonBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.List;

/**
 * A <code>RadioButtonTag</code> represents a radio button element
 */
public class RadioButtonTag extends BaseComponentTag {

    protected RadioButtonBean radiobutton = null;
    protected boolean selected = false;
    protected String onClick = null;

    /**
     * Sets the selected status of the bean
     *
     * @param flag status of the bean
     */
    public void setSelected(boolean flag) {
        this.selected = flag;
    }

    /**
     * Returns the selected status of the bean
     *
     * @return selected status
     */
    public boolean isSelected() {
        return selected;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public int doStartTag() throws JspException {

        if (!beanId.equals("")) {
            radiobutton = (RadioButtonBean) getTagBean();
            if (radiobutton == null) {
                radiobutton = new RadioButtonBean();
                this.setBaseComponentBean(radiobutton);
            } else {
                this.setBaseComponentBean(radiobutton);
                //this.updateBaseComponentBean(radiobutton);
                //this.overrideBaseComponentBean(radiobutton);
            }
            List vals = radiobutton.getSelectedValues();
            if (vals.contains(value)) {
                radiobutton.setSelected(true);
            } else {
                //if (!selected) selected = radiobutton.isSelected();
                if (vals.isEmpty()) radiobutton.setSelected(selected);
            }

        } else {
            radiobutton = new RadioButtonBean();
            radiobutton.setSelected(selected);
            this.setBaseComponentBean(radiobutton);
        }

        if (onClick !=null) radiobutton.setOnClick(onClick);

        //debug();


        try {
            JspWriter out = pageContext.getOut();
            out.print(radiobutton.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }

}
