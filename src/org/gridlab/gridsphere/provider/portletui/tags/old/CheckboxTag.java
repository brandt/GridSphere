/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.old;

import org.gridlab.gridsphere.provider.ui.beans.CheckBoxBean;
import org.gridlab.gridsphere.provider.portletui.tags.old.BaseTag;

import javax.servlet.jsp.JspException;

public class CheckboxTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new CheckBoxBean(name, value, isChecked, isDisabled);
        }
        return super.doStartTag();
    }

}
