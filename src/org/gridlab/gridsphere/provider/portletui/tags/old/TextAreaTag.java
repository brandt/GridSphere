/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.tags.old;

import org.gridlab.gridsphere.provider.ui.beans.TextAreaBean;
import org.gridlab.gridsphere.provider.portletui.tags.old.BaseTag;

import javax.servlet.jsp.JspException;

public class TextAreaTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new TextAreaBean(name, value, isDisabled, isReadonly, rows, cols);
        }
        return super.doStartTag();
    }

}
