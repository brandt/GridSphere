/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TabTag.java 4666 2006-03-27 17:47:56Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.TabBean;

import javax.servlet.jsp.JspException;

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
                tabBean = (TabBean) getTagBean();
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
