/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.SubmitButtonBean;

import javax.servlet.jsp.JspException;

public class SubmitButtonTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new SubmitButtonBean(name, value);
        }
        return super.doStartTag();
    }
}
