package org.gridlab.gridsphere.provider.portletui.tags.old;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

public class FileFormTag extends FormTag {

    public int doStartTag() throws JspException {
        this.isMultipart = true;
        return super.doStartTag();
    }

    public int doEndTag() throws JspTagException {
        return super.doEndTag();
    }

}
