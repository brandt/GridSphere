/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.ui;

import org.gridlab.gridsphere.provider.ui.beans.LabelBean;

import javax.servlet.jsp.JspException;

public class LabelTag extends TextTag {

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            this.htmlelement = new LabelBean(text);
        }
        return super.doStartTag();
    }

}
