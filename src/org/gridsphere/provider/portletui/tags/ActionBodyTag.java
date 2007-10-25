/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridsphere.provider.portletui.tags;

import javax.servlet.jsp.JspException;

/**
 * Represents the body of a selection on an action menu.
 */
public class ActionBodyTag extends BaseComponentTag {

    public int doStartTag() throws JspException {

        // Print the string
        try {
            pageContext.getOut().print("<div id=\"actionbody\">");
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        try {
            pageContext.getOut().print("</div>");
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
