/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.old;

import org.gridlab.gridsphere.provider.ui.beans.FileInputBean;

import javax.servlet.jsp.JspException;

public class FileInputTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new FileInputBean(name, value, isDisabled, isReadonly, size, maxLength);

        }
        return super.doStartTag();
    }

}
