package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.InputField;

import javax.servlet.jsp.JspException;

public class TextFieldTag extends InputTag {

    public int doStartTag() throws JspException {
        if (element.equals(null)) {
            this.htmlelement = new InputField(name, value, isDisabled,  isReadonly, size, maxLength);
        } else {
            this.htmlelement = (InputField)pageContext.getRequest().getAttribute(element);
        }
        return super.doStartTag();
    }

}
