package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.InputFieldBean;
import org.gridlab.gridsphere.tags.web.element.ElementBean;

import javax.servlet.jsp.JspException;

public class TextFieldTag extends InputTag {

    public int doStartTag() throws JspException {
        if (element.equals("")) {
            this.htmlelement = new InputFieldBean(name, value, isDisabled,  isReadonly, size, maxLength);
        }
        return super.doStartTag();
    }

}
