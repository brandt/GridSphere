/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.old;

import org.gridlab.gridsphere.provider.portletui.tags.old.BaseTag;

import javax.servlet.jsp.JspException;

public class PasswordFieldTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new org.gridlab.gridsphere.provider.ui.beans.PasswordBean(name, value, isDisabled, isReadonly, size, maxLength);
        }
        return super.doStartTag();
    }

}
