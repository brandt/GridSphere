/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web;

import javax.servlet.jsp.JspException;

public class PasswordFieldTag extends InputTag {

    public int doStartTag() throws JspException {
        type = PASSWORD;
        return super.doStartTag();
    }

}
