package org.gridlab.gridsphere.tags.ui;

import org.gridlab.gridsphere.provider.ui.beans.TextFieldBean;

import javax.servlet.jsp.JspException;

public class TextFieldTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new TextFieldBean(name, value, isDisabled, isReadonly, size, maxLength);
        }
        return super.doStartTag();
    }

}
