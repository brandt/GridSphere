package org.gridlab.gridsphere.tags.portletui;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

public class FileFormTag extends ActionFormTag {

    public int doStartTag() throws JspException {
        this.isMultipart = true;
        return super.doStartTag();
    }

    public int doEndTag() throws JspTagException {
        return super.doEndTag();
    }

}
