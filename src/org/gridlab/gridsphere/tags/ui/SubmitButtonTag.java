/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.ui;

import org.gridlab.gridsphere.tags.ui.BaseTag;

import javax.servlet.jsp.JspException;

public class SubmitButtonTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new org.gridlab.gridsphere.provider.ui.beans.SubmitButtonBean(name, value);
        }
        return super.doStartTag();
    }
}
