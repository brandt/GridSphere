/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.ui;

import org.gridlab.gridsphere.provider.ui.beans.TextAreaBean;

import javax.servlet.jsp.JspException;

public class TextAreaTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new TextAreaBean(name, value, isDisabled, isReadonly, rows, cols);
        }
        return super.doStartTag();
    }

}
