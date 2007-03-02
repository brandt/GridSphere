package org.gridsphere.provider.portletui.tags;

import javax.servlet.jsp.JspException;

/**
 * The <code>TableRowTag</code> represents a table row element that is conatined within a <code>TableTag</code>
 * and itself may contain <code>TableCellTag</code>s
 */
public class DialogButtonTag extends DialogTag {

    public int doStartTag() throws JspException {
        isLink = false;
        return super.doStartTag();
    }

    public int doEndTag() throws JspException {
        return super.doEndTag();
    }
}
