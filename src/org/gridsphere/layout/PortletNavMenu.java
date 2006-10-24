package org.gridsphere.layout;

import org.gridsphere.layout.event.PortletTabListener;
import org.gridsphere.layout.event.PortletTabEvent;
import org.gridsphere.layout.event.PortletComponentEvent;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.services.core.persistence.PersistenceManagerException;

import javax.portlet.PortletRequest;
import java.io.Serializable;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The <code>PortletTabbedPane</code> represents the visual portlet tabbed pane interface
 * and is a container for a {@link org.gridsphere.layout.PortletTab}.
 */
public abstract class PortletNavMenu extends BasePortletComponent implements Serializable, PortletTabListener, Cloneable {

    private String style = "menu";
    private List<PortletTab> tabs = new ArrayList<PortletTab>();

    private String layoutDescriptor = null;

    //private transient TabbedPaneView tabbedPaneView = null;

    /**
     * Constructs an instance of PortletTabbedPane
     */
    public PortletNavMenu() {
    }

    public void setLayoutDescriptor(String layoutDescriptor) {
        this.layoutDescriptor = layoutDescriptor;
    }

    public String getLayoutDescriptor() {
        return layoutDescriptor;
    }

    /**
     * Sets the tabbed pane style. Currently supported styles are "menu"
     * and "sub-menu"
     *
     * @param style the tabbed pane style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Returns the tabbed pane style. Currently supported styles are "menu"
     * and "sub-menu"
     *
     * @return the tabbed pane style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Returns the selected tab if none exists, return null
     *
     * @return the selected portlet tab
     */
    public PortletTab getSelectedTab() {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab) tabs.get(i);
            if (tab.isSelected()) {
                return tab;
            }
        }
        return null;
    }

    /**
     * Returns the index of the supplied tab or -1 if not found
     *
     * @param tab the tab to get the index of
     * @return the index of the supplied tab
     */
    public int getIndexOfTab(PortletTab tab) {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab atab = (PortletTab) tabs.get(i);
            if (tab.equals(atab)) return i;
        }
        return -1;
    }

    /**
     * Sets the selected portlet tab in this tabbed pane
     *
     * @param tab the selected portlet tab
     */
    public void setSelectedPortletTab(PortletTab tab) {
        PortletTab portletTab;
        for (int i = 0; i < tabs.size(); i++) {
            portletTab = (PortletTab) tabs.get(i);
            if (portletTab.getComponentID() == tab.getComponentID()) {
                //System.err.println("set tab=" + portletTab.toString());
                portletTab.setSelected(true);
            } else {
                portletTab.setSelected(false);
            }
        }
    }

    /**
     * Sets the selected portlet tab in this tabbed pane
     *
     * @param index tab the selected portlet tab index
     */
    public void setSelectedPortletTabIndex(int index) {
        PortletTab portletTab;
        for (int i = 0; i < tabs.size(); i++) {
            portletTab = (PortletTab) tabs.get(i);
            if (index == i) {
                portletTab.setSelected(true);
            } else {
                portletTab.setSelected(false);
            }
        }
    }

    /**
     * Returns the tab with the supplied title
     *
     * @param label the tab label
     * @return the tab associated with this title
     */
    public PortletTab getPortletTab(String label) {
        for (PortletTab tab : tabs) {
            if (tab.getLabel().equals(label)) return tab;
        }
        return null;
    }

    /**
     * Return the tab contained by this tabbed pane by index
     *
     * @param index the tab index
     * @return the portlet tab
     */
    public PortletTab getPortletTabAt(int index) {
        if (index >= tabs.size()) return null;
        return (PortletTab) tabs.get(index);
    }

    public int getTabCount() {
        return tabs.size();
    }

    public void insertTab(PortletTab tab, int index) {
        if ((index >= 0) || index < tabs.size()) tabs.add(index, tab);
    }

    /**
     * Adds a new portlet tab to the tabbed pane
     *
     * @param tab a portlet tab to add
     */
    public void addTab(PortletTab tab) {
        tabs.add(tab);
    }

    /**
     * Removes a portlet tab from the tabbed pane
     *
     * @param tab the portlet tab to remove
     */
    public void removeTab(PortletTab tab) {
        Iterator<PortletTab> it = tabs.iterator();
        while (it.hasNext()) {
            PortletTab atab = it.next();
            if (tab.getLabel().equals(atab.getLabel())) it.remove();
        }
    }

    /**
     * Removes a portlet tab from the tabbed pane at the specified index
     *
     * @param index the index of the tab to remove
     */
    public synchronized void removeTabAt(int index) {
        tabs.remove(index);
    }

    /**
     * Removes all portlet tabs from the tabbed pane
     */
    public synchronized void removeAll() {
        for (int i = 0; i < tabs.size(); i++) {
            tabs.remove(i);
        }
    }

    /**
     * Sets the portlet tabs in the tabbed pane
     *
     * @param tabs an ArrayList containing the portlet tabs to add
     */
    public void setPortletTabs(List<PortletTab> tabs) {
        this.tabs = tabs;

    }

    /**
     * Returns a list containing the portlet tabs
     *
     * @return a list containing the portlet tabs
     */
    public List<PortletTab> getPortletTabs() {
        return tabs;
    }

    public PortletTab getLastPortletTab() {
        return (PortletTab) tabs.get(tabs.size() - 1);
    }

    /**
     * Initializes the portlet tabbed pane component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see org.gridsphere.layout.ComponentIdentifier
     */
    public List init(PortletRequest req, List list) {

        list = super.init(req, list);

        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setComponentID(list.size());
        compId.setComponentLabel(label);
        compId.setClassName(this.getClass().getName());
        list.add(compId);

        for (PortletTab tab : tabs) {
            list = tab.init(req, list);
            tab.addComponentListener(this);
            tab.setParentComponent(this);
        }


        PortletTab tab = this.getSelectedTab();

        if (tab == null) {
            tab = this.getPortletTabAt(0);
            if (tab != null) this.setSelectedPortletTab(tab);
        }

        return list;
    }

    /**
     * Gives notification that a portlet tab event has occured
     *
     * @param event the portlet tab event
     */
    public void handlePortletTabEvent(PortletTabEvent event) {
        if (event.getAction().equals(PortletTabEvent.TabAction.TAB_SELECTED)) {
            PortletTab selectedTab = (PortletTab) event.getPortletComponent();
            this.setSelectedPortletTab(selectedTab);
        }
    }

    /**
     * Gives notification that a portlet tab event has occured
     *
     * @param event the portlet tab event
     */
    public void actionPerformed(GridSphereEvent event) {

        super.actionPerformed(event);

        PortletComponentEvent compEvt = event.getLastRenderEvent();
        if ((compEvt != null) && (compEvt instanceof PortletTabEvent)) {
            PortletTabEvent tabEvent = (PortletTabEvent) compEvt;
            handlePortletTabEvent(tabEvent);
        }

        Iterator it = listeners.iterator();
        PortletComponent comp;

        while (it.hasNext()) {
            comp = (PortletComponent) it.next();
            event.addNewRenderEvent(compEvt);
            comp.actionPerformed(event);
        }

    }

    /**
     * Renders the portlet frame component
     *
     * @param event a gridsphere event
     */
    public abstract void doRender(GridSphereEvent event);

    public void remove(PortletComponent pc, PortletRequest req) {
        if (pc instanceof PortletTab) {
            tabs.remove((PortletTab)pc);
            if (tabs.isEmpty()) parent.remove(this, req);
        }
    }

    public void save() throws IOException {
        try {
            PortletLayoutDescriptor.saveLayoutComponent(this, layoutDescriptor, LAYOUT_MAPPING_PATH);
        } catch (PersistenceManagerException e) {
            throw new IOException("Unable to save user's tabbed pane: " + e.getMessage());
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletNavMenu t = (PortletNavMenu) super.clone();
        t.style = this.style;
        t.tabs = new ArrayList<PortletTab>(tabs.size());
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab) tabs.get(i);
            t.tabs.add((PortletTab)tab.clone());
        }
        return t;
    }

}
