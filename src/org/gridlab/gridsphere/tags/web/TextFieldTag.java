package org.gridlab.gridsphere.tags.web;

import javax.servlet.jsp.JspException;

public class TextFieldTag extends InputTag {

    public int doStartTag() throws JspException {
        type = TEXT;
        return super.doStartTag();
    }

}
