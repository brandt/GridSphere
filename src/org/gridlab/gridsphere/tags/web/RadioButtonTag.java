/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.RadioButtonBean;

import javax.servlet.jsp.JspException;

public class RadioButtonTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (tagBean.equals("")) {
            this.htmlelement = new RadioButtonBean(name, value, isChecked, isDisabled);
        }
        return super.doStartTag();
    }

}
