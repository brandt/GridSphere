/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.util.Vector;
import java.util.List;

public class PortletTabbedPane extends PortletComponent {

    private int selectedIndex = -1;
    private PortletComponent selectedComponent;
    private List titles;
    private List components;
    private Vector pages;

    private class Page {

        String title;
        String background;
        String foreground;
        PortletComponent component;
        boolean enabled = true;
        PortletTabbedPane parent;

        public Page(PortletTabbedPane parent, String title, PortletComponent component) {
            this.parent = parent;
            this.title = title;
            this.component = component;
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

        public PortletComponent getComponent() {
            return component;
        }

        public void setComponent(PortletComponent component) {
            this.component = component;
        }

    }

    public PortletTabbedPane() {
        pages = new Vector();
    }

    public void add(PortletComponent component) {
        addTab(component.getName(), component);
    }

    public void add(PortletComponent component, int index) {
        insertTab(component.getName(), component, index);
    }

    public void add(String title, PortletComponent component) {
        addTab(title, component);
    }

    public void addTab(String title, PortletComponent component) {
        Page page = new Page(this, title, component);
        pages.add(page);
    }

    public String getBackgroundAt(int index)  {
        Page page = (Page)pages.get(index);
        return page.getBackground();
    }

    public PortletComponent getPortletComponentAt(int index)  {
        Page page = (Page)pages.get(index);
        return page.getComponent();
    }

    public String getForegroundAt(int index) {
        Page page = (Page)pages.get(index);
        return page.getForeground();
    }

    public PortletComponent getSelectedPortletComponent() {
        return selectedComponent;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public int getTabCount() {
        return pages.size();
    }

    public String getTitleAt(int index) {
        Page page = (Page)pages.get(index);
        return page.getTitle();
    }

    public int indexOfPortletComponent(PortletComponent component) {
        boolean found = false;
        int i;
        for (i = 0; i < pages.size(); i++) {
            Page page = (Page)pages.get(i);
            if (page.getComponent().equals(component)) {
                found = true;
                break;
            }
        }
        if (found)
            return i;
        return -1;
    }

    public int indexOfTab(String title) {
        boolean found = false;
        int i;
        for (i = 0; i < pages.size(); i++) {
            Page page = (Page)pages.get(i);
            if (page.getTitle().equals(title)) {
                found = true;
                break;
            }
        }
        if (found)
            return i;
        return -1;
    }

    public void insertTab(String title, PortletComponent component, int index) {
        Page page = new Page(this, title, component);
        pages.add(index, page);
    }

    public void remove(PortletComponent component) {
        int i;
        for (i = 0; i < pages.size(); i++) {
            Page page = (Page)pages.get(i);
            if (page.getComponent().equals(component)) {
                pages.remove(page);
            }
        }
    }

    public void remove(int index) {
        pages.remove(index);
    }

    public void removeAll() {
        int i;
        for (i = 0; i < pages.size(); i++) {
            pages.remove(i);
        }
    }

    public void removeTabAt(int index) {
        pages.remove(index);
    }

    public void setBackgroundAt(int index, String background) {
        Page page = (Page)pages.get(index);
        page.setBackground(background);
    }

    public void setPortletComponentAt(int index, PortletComponent component) {
        Page page = (Page)pages.get(index);
        page.setComponent(component);
    }

    public void setForegroundAt(int index, String foreground) {
        Page page = (Page)pages.get(index);
        page.setForeground(foreground);
    }

    public void setSelectedPortletComponent(PortletComponent c) {
        int i;
        for (i = 0; i < pages.size(); i++) {
            Page page = (Page)pages.get(i);
            if (page.getComponent().equals(c)) {
                selectedComponent = c;
                selectedIndex = i;
                break;
            }
        }
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
        Page page = (Page)pages.get(index);
        selectedComponent = page.getComponent();
    }

    public void setTitleAt(int index, String title) {
        Page page = (Page)pages.get(index);
        page.setTitle(title);
    }

    public void doLayout() {

        // XXX: RENDER THIS SHIT
    }

}
