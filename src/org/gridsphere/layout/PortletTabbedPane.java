/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
* @version $Id: PortletTabbedPane.java 5032 2006-08-17 18:15:06Z novotny $
*/

package org.gridsphere.layout;

import org.gridsphere.layout.event.PortletComponentEvent;
import org.gridsphere.layout.event.PortletTabEvent;
import org.gridsphere.layout.event.PortletTabListener;
import org.gridsphere.layout.view.TabbedPaneView;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.services.core.persistence.PersistenceManagerException;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The <code>PortletTabbedPane</code> represents the visual portlet tabbed pane interface
 * and is a container for a {@link PortletTab}.
 */
public class PortletTabbedPane extends BasePortletComponent implements Serializable, PortletTabListener, Cloneable {


    private List tabs = new ArrayList();
    private String style = "menu";
    private String layoutDescriptor = null;

    private transient TabbedPaneView tabbedPaneView = null;

    /**
     * Constructs an instance of PortletTabbedPane
     */
    public PortletTabbedPane() {
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
                portletTab.setSelected(true);
            } else {
                portletTab.setSelected(false);
            }
        }
    }

    /**
     * Sets the selected portlet tab in this tabbed pane
     *
     * @param tab the selected portlet tab
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
        Iterator it = tabs.iterator();
        while (it.hasNext()) {
            PortletTab tab = (PortletTab) it.next();
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
        Iterator it = tabs.iterator();
        while (it.hasNext()) {
            PortletTab atab = (PortletTab) it.next();
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
    public void setPortletTabs(List tabs) {
        this.tabs = tabs;

    }

    /**
     * Returns a list containing the portlet tabs
     *
     * @return a list containing the portlet tabs
     */
    public List getPortletTabs() {
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
     * @see ComponentIdentifier
     */
    public List init(PortletRequest req, List list) {

        list = super.init(req, list);

        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setComponentID(list.size());
        compId.setComponentLabel(label);
        compId.setClassName(this.getClass().getName());
        list.add(compId);

        PortletTab tab;

        tabbedPaneView = (TabbedPaneView)getRenderClass(req, "TabbedPane");
        Iterator it = tabs.iterator();

        while (it.hasNext()) {
            tab = (PortletTab) it.next();
            list = tab.init(req, list);
            tab.addComponentListener(this);
            tab.setParentComponent(this);
        }


        tab = this.getSelectedTab();

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
        if (event.getAction() == PortletTabEvent.TabAction.TAB_SELECTED) {
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
    public void doRender(GridSphereEvent event) {
        super.doRender(event);
        StringBuffer pane = new StringBuffer();
        PortletRequest req = event.getPortletRequest();

        List userRoles = req.getRoles();

        log.debug("in tabbed pane: my comp is=" + componentIDStr);
        pane.append(tabbedPaneView.doStart(event, this));

        PortletTab tab;
        List tabs = getPortletTabs();
        for (int i = 0; i < tabs.size(); i++) {
            tab = (PortletTab) tabs.get(i);
            String tabRole = tab.getRequiredRole();
            if (tabRole.equals("") || (userRoles.contains(tabRole))) {
                pane.append(tabbedPaneView.doRenderTab(event, this, tab));
            } else {
                // if role is < required role we try selecting the next possible tab
                //System.err.println("in PortletTabbedPane menu: role is < required role we try selecting the next possible tab");
                if (tab.isSelected()) {
                    int index = (i + 1);
                    if (index < tabs.size()) {
                        PortletTab newtab = (PortletTab) tabs.get(index);
                        setSelectedPortletTab(newtab);
                    }
                }
            }
        }


        if (req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE) != null) {
            log.debug("in tabbed paneedit mode: my comp is=" + componentIDStr);
            pane.append(tabbedPaneView.doRenderEditTab(event, this, false));
        }


        pane.append(tabbedPaneView.doEndBorder(event, this));

        // render the selected tab
        if (!tabs.isEmpty()) {
            PortletTab selectedTab = getSelectedTab();
            if (selectedTab != null) {
                selectedTab.doRender(event);
                pane.append(selectedTab.getBufferedOutput(req));
            }
        }

        pane.append(tabbedPaneView.doEnd(event, this));

//        req.setAttribute(SportletProperties.RENDER_OUTPUT + componentIDStr, pane);
        setBufferedOutput(req, pane);

    }

    public void remove(PortletComponent pc, PortletRequest req) {
        tabs.remove(pc);
        if (tabs.isEmpty()) parent.remove(this, req);
    }

    public void save(ServletContext ctx) throws IOException {
        try {
            String layoutMappingFile = ctx.getRealPath("/WEB-INF/mapping/layout-mapping.xml");
            PortletLayoutDescriptor.savePortletTabbedPane(this, layoutDescriptor, layoutMappingFile);
        } catch (PersistenceManagerException e) {
            throw new IOException("Unable to save user's tabbed pane: " + e.getMessage());
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletTabbedPane t = (PortletTabbedPane) super.clone();
        t.style = this.style;
        t.tabs = new ArrayList(tabs.size());
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab) tabs.get(i);
            t.tabs.add(tab.clone());
        }
        return t;
    }

}
