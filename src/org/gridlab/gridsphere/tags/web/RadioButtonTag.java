/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.RadioButton;

import javax.servlet.jsp.JspException;

public class RadioButtonTag extends InputTag {

    private RadioButton radiobutton;

    public int doStartTag() throws JspException {
        this.htmlelement = new RadioButton(name, value, isChecked, isDisabled);
        return super.doStartTag();
    }

}
