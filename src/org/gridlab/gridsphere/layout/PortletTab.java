/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.util.Vector;
import java.util.List;

public class PortletTab {

    private String title;
    private String background;
    private String foreground;
    private PortletPanel panel;
    private boolean selected = false;
    private PortletTabbedPane parent;

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

    public String getBackground() {
        return background != null? background : parent.getBackground();
    }

    public void setBackground(String c) {
        background = c;
    }

    public String getForeground() {
        return foreground != null? foreground : parent.getForeground();
    }

    public void setForeground(String c) {
        foreground = c;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return selected;
    }

    public PortletPanel getPortletPanel() {
        return panel;
    }

    public void setPortletPanel(PortletPanel panel) {
        this.panel = panel;
    }

}
