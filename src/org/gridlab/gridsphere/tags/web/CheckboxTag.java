package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.CheckBox;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

public class CheckboxTag extends InputTag {

    public int doStartTag() throws JspException {
        if (element.equals("")) {
            this.htmlelement = new CheckBox(name, value, isChecked, isDisabled);
        }
        return super.doStartTag();
    }

}
