/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class ListBoxItemTag extends BaseComponentTag {

    protected ListBoxItemBean listboxitem = null;
    protected boolean selected = false;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

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
