/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.PasswordBean;

import javax.servlet.jsp.JspException;

public class PasswordFieldTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new PasswordBean(name, value, isDisabled, isReadonly, size, maxLength);
        }
        return super.doStartTag();
    }

}
