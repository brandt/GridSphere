/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.portletui.tags;


import org.gridsphere.provider.portletui.beans.TabBean;

import javax.servlet.jsp.JspException;

/**
 * The <code>TableRowTag</code> represents a table row element that is conatined within a <code>TableTag</code>
 * and itself may contain <code>TableCellTag</code>s
 */
public class TabTag extends BaseComponentTag {

    protected boolean isActive = false;

    protected String label = "";
    protected String page = null;

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void release() {
        super.release();
    }

    public int doStartTag() throws JspException {
        TabbedPaneTag tabbedPaneTag = (TabbedPaneTag) findAncestorWithClass(this, TabbedPaneTag.class);
        if (tabbedPaneTag != null) {
            TabBean tab = new TabBean();
            tab.setPage(page);
            if (key != null) {
                value = getLocalizedText(key);
            }
            tab.setValue(value);
            tab.setLabel(label);
            tabbedPaneTag.addTabBean(tab);
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
