/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
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
