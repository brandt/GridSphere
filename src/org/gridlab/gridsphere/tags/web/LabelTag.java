/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.LabelBean;

import javax.servlet.jsp.JspException;

public class LabelTag extends TextTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new LabelBean(text);
        }
        return super.doStartTag();
    }

}
