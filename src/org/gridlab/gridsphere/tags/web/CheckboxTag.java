package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.CheckBoxBean;

import javax.servlet.jsp.JspException;

public class CheckboxTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (tagBean.equals("")) {
            this.htmlelement = new CheckBoxBean(name, value, isChecked, isDisabled);
        }
        return super.doStartTag();
    }

}
