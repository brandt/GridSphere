/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.TabBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * The <code>TableRowTag</code> represents a table row element that is conatined within a <code>TableTag</code>
 * and itself may contain <code>TableCellTag</code>s
 */
public class TabTag extends BaseComponentTag {

    protected TabBean tabBean = null;
    protected boolean isActive = false;
    protected String title = "";
    protected String page = null;

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getActive() {
        return isActive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void release() {
        tabBean = null;
        super.release();
    }

    public int doStartTag() throws JspException {
        System.err.println("tabtag in doStartTag");
        TabbedPaneTag tabbedPaneTag = (TabbedPaneTag) findAncestorWithClass(this, TabbedPaneTag.class);
        if (tabbedPaneTag != null) {

            // need to determine which rows to display
            if (!beanId.equals("")) {
                tabBean = (TabBean) pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
                if (tabBean == null) tabBean = new TabBean();
            } else {
                tabBean = new TabBean();
            }
            tabBean.setActive(isActive);
            tabBean.setTitle(title);
            tabBean.setPage(page);
            tabbedPaneTag.addTab(tabBean);
        }

        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        System.err.println("tabtag in doEndTag");
        super.doEndTag();
        return EVAL_PAGE;
    }
}
