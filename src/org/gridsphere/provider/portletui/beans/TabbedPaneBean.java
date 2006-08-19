/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TabbedPaneBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portletui.beans;

/**
 * A <code>TableBean</code> provides a table element
 */
public class TabbedPaneBean extends BeanContainer implements TagBean {


    protected String border = null;
    protected String width = null;

    protected int currentTab;

    protected String paneId = null;

    private int tabCount = 0;

    /**
     * Constructs a default table bean
     */
    public TabbedPaneBean() {
        super();
    }

    public String getPaneId() {
        return paneId;
    }

    public void setPaneId(String paneId) {
        this.paneId = paneId;
    }

    public void setCurrentTab(int currentTab) {
        this.currentTab = currentTab;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    /**
     * Sets the table width
     *
     * @param width the table width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Returns the table width
     *
     * @return the table width
     */
    public String getWidth() {
        return width;
    }

    public void setTabCount(int tabCount) {
        this.tabCount = tabCount;
    }

    public int getTabCount() {
        return tabCount;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table>");
        return sb.toString();
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();
        sb.append("</table>");
        return sb.toString();
    }

}
