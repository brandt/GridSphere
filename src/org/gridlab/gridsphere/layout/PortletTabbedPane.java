/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.Vector;
import java.util.List;
import java.io.PrintWriter;

public class PortletTabbedPane extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletTabbedPane.class);

    private int selectedIndex = -1;
    private PortletComponent selectedComponent;
    private Vector tabs = new Vector();

    public PortletTabbedPane() {}

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
        PortletTab tab = new PortletTab(this, title, component);
        tabs.add(tab);
    }

    public String getBackgroundAt(int index)  {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getBackground();
    }

    public PortletComponent getPortletComponentAt(int index)  {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getComponent();
    }

    public String getForegroundAt(int index) {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getForeground();
    }

    public PortletComponent getSelectedPortletComponent() {
        return selectedComponent;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public int getTabCount() {
        return tabs.size();
    }

    public String getTitleAt(int index) {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getTitle();
    }

    public int indexOfPortletComponent(PortletComponent component) {
        boolean found = false;
        int i;
        for (i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getComponent().equals(component)) {
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
        for (i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getTitle().equals(title)) {
                found = true;
                break;
            }
        }
        if (found)
            return i;
        return -1;
    }

    public void insertTab(String title, PortletComponent component, int index) {
        PortletTab tab = new PortletTab(this, title, component);
        tabs.add(index, tab);
    }

    public void remove(PortletComponent component) {
        int i;
        for (i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getComponent().equals(component)) {
                tabs.remove(tab);
            }
        }
    }

    public void remove(int index) {
        tabs.remove(index);
    }

    public void removeAll() {
        int i;
        for (i = 0; i < tabs.size(); i++) {
            tabs.remove(i);
        }
    }

    public void removeTabAt(int index) {
        tabs.remove(index);
    }

    public void setBackgroundAt(int index, String background) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setBackground(background);
    }

    public void setPortletComponentAt(int index, PortletComponent component) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setComponent(component);
    }

    public void setForegroundAt(int index, String foreground) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setForeground(foreground);
    }

    public void setSelectedPortletComponent(PortletComponent c) {
        int i;
        for (i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getComponent().equals(c)) {
                selectedComponent = c;
                selectedIndex = i;
                break;
            }
        }
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
        PortletTab tab = (PortletTab)tabs.get(index);
        selectedComponent = tab.getComponent();
    }

    public void setTitleAt(int index, String title) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setTitle(title);
    }

    public void setPortletTabs(Vector tabs) {
        this.tabs = tabs;
    }

    public List getPortletTabs() {
        return tabs;
    }

    public void doRender(PrintWriter out) {
        log.debug("in doRender()");
    }

}
