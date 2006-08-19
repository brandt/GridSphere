/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: FileFormTag.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portletui.tags;

import javax.servlet.jsp.JspException;

/**
 * A <code>FileFormTag</code> represents a specialized <code>ActionFormTag</code> to be used for uploading files using
 * a <code>FileInputTag</code>
 */
public class FileFormTag extends ActionFormTag {

    public int doStartTag() throws JspException {
        this.isMultipart = true;
        return super.doStartTag();
    }

    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

}
