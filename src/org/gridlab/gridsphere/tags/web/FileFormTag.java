package org.gridlab.gridsphere.tags.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class FileFormTag extends FormTag {

    public int doStartTag() throws JspException {
        this.isMultipart = true;
        return super.doStartTag();
    }

    public int doEndTag() throws JspTagException {
        return super.doEndTag();
    }

}
