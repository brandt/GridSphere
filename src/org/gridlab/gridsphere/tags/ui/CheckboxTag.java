package org.gridlab.gridsphere.tags.ui;

import org.gridlab.gridsphere.provider.ui.beans.CheckBoxBean;

import javax.servlet.jsp.JspException;

public class CheckboxTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new CheckBoxBean(name, value, isChecked, isDisabled);
        }
        return super.doStartTag();
    }

}
