package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.TextFieldBean;
import org.gridlab.gridsphere.tags.web.element.ElementBean;

import javax.servlet.jsp.JspException;

public class TextFieldTag extends InputTag {

    public int doStartTag() throws JspException {
        if (element.equals("")) {
            this.htmlelement = new TextFieldBean(name, value, isDisabled,  isReadonly, size, maxLength);
        }
        return super.doStartTag();
    }

}
