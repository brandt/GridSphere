/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;



public class PortletTab {

    private String title;
    private String background;
    private String foreground;
    private PortletPanel panel;
    private boolean selected = false;
    private PortletTabbedPane parent;
    private LayoutManager layoutManager;

    public PortletTab() {}

    public PortletTab(PortletTabbedPane parent, String title, PortletPanel panel) {
        this.parent = parent;
        this.title = title;
        this.panel = panel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public PortletPanel getPortletPanel() {
        return panel;
    }

    public void setPortletPanel(PortletPanel panel) {
        this.panel = panel;
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

}
