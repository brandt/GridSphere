/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.TextArea;

import javax.servlet.jsp.JspException;

public class TextAreaTag extends InputTag {

    public int doStartTag() throws JspException {
        if (element.equals(null)) {
            this.htmlelement = new TextArea(name, value, isDisabled,  isReadonly, rows, cols);
        }
        return super.doStartTag();
    }

}
