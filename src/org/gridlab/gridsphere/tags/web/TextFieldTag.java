package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.InputField;
import org.gridlab.gridsphere.tags.web.element.Element;

import javax.servlet.jsp.JspException;

public class TextFieldTag extends InputTag {

    public int doStartTag() throws JspException {
        if (element.equals(null)) {
            this.htmlelement = new InputField(name, value, isDisabled,  isReadonly, size, maxLength);
        }
        return super.doStartTag();
    }

}
