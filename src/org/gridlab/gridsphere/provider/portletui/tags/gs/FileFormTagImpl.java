/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.gs;

import org.gridlab.gridsphere.provider.portletui.tags.gs.ActionFormTagImpl;
import org.gridlab.gridsphere.provider.portletui.tags.gs.ActionFormTagImpl;
import org.gridlab.gridsphere.provider.portletui.tags.gs.ActionFormTagImpl;

import javax.servlet.jsp.JspException;

/**
 * A <code>FileFormTag</code> represents a specialized <code>ActionFormTag</code> to be used for uploading files using
 * a <code>FileInputTag</code>
 */
public class FileFormTagImpl extends ActionFormTagImpl {

    public int doStartTag() throws JspException {
        this.isMultipart = true;
        return super.doStartTag();
    }

    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

}
