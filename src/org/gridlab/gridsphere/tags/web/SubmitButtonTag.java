/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web;

import javax.servlet.jsp.JspException;

public class SubmitButtonTag extends InputTag {

    public int doStartTag() throws JspException {
        type = SUBMIT;
        name = "submit:" + name;
        return super.doStartTag();
    }
}
