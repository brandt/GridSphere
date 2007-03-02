package org.gridsphere.provider.portletui.tags;

import javax.servlet.jsp.JspException;

/**
 * The <code>TableRowTag</code> represents a table row element that is conatined within a <code>TableTag</code>
 * and itself may contain <code>TableCellTag</code>s
 */
public class DialogLinkTag extends DialogTag {

    public int doStartTag() throws JspException {
        isLink = true;
        super.doStartTag();
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
