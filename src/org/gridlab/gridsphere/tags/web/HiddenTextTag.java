/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.HiddenTextBean;

import javax.servlet.jsp.JspException;

public class HiddenTextTag extends BaseTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new HiddenTextBean (name, value);
        }
        return super.doStartTag();
    }

}
