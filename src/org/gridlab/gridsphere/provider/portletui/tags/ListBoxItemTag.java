/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * A <code>ListBoxItemtag</code> represents a list box element
 */
public class ListBoxItemTag extends BaseComponentTag {

    protected ListBoxItemBean listboxitem = null;
    protected boolean selected = false;

    /**
     * Sets the element to be selected
     *
     * @param selected is true if the element is selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Return true if this element is selected
     *
     * @return true if this element is selected, false otherwise
     */
    public boolean getSelected() {
        return selected;
    }

    public int doStartTag() throws JspException {
        listboxitem = new ListBoxItemBean();
        this.setBaseComponentBean(listboxitem);
        try {
            JspWriter out = pageContext.getOut();
            out.print(listboxitem.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

}
