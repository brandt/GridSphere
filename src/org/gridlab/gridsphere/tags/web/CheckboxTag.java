package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.CheckBoxBean;

import javax.servlet.jsp.JspException;

public class CheckboxTag extends BaseTag {

    public int doStartTag() throws JspException {
        System.out.println("CB BEAN called " + name + " " + value);
        if (bean.equals("")) {
            System.out.println("CB BEAN called " + name + " " + value);
            this.htmlelement = new CheckBoxBean(name, value, isChecked, isDisabled);
        }
        return super.doStartTag();
    }

}
