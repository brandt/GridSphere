/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.PasswordField;

import javax.servlet.jsp.JspException;

public class PasswordFieldTag extends InputTag {

    public int doStartTag() throws JspException {
        if (element.equals(null)) {
            this.htmlelement = new PasswordField(name, value, isDisabled,  isReadonly, size, maxLength);
        } else {
            this.htmlelement = (PasswordField)pageContext.getRequest().getAttribute(element);
        }
        return super.doStartTag();
    }

}
