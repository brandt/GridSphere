/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 20, 2002
 * Time: 4:00:34 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;

public class PortletTabbedPane extends BasePortletComponent {

    private PortletTabBar selectedTabBar = null;
    private List tabs = new ArrayList();

    public PortletTabbedPane() {}

    public void addTab(String title, PortletTabBar tabBar) {
        PortletTab tab = new PortletTab(this, title, tabBar);
        tabs.add(tab);
    }

    public PortletTabBar getPortletTabBarAt(int index) {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getPortletTabBar();
    }

    public PortletTabBar getSelectedPortletTabBar() {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.isSelected())
                return tab.getPortletTabBar();
        }
        return null;
    }

    public int getSelectedIndex() {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.isSelected())
                return i;
        }
        return -1;
    }

    public PortletTab getSelectedTab() {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.isSelected())
                return tab;
        }
        return null;
    }

    public int getTabCount() {
        return tabs.size();
    }

    public String getTitleAt(int index) {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getTitle();
    }

    public int indexOfPortletTabBar(PortletTabBar tabBar) {
        boolean found = false;
        int i;
        for (i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getPortletTabBar().equals(tabBar)) {
                found = true;
                break;
            }
        }
        if (found)
            return i;
        return -1;
    }

    public int indexOfTabPage(String title) {
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

    public void insertTabPage(String title, PortletTabBar tabBar, int index) {
        PortletTab tab = new PortletTab(this, title, tabBar);
        tabs.add(index, tab);
    }

    public void remove(PortletTabBar tabBar) {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getPortletTabBar().equals(tabBar)) {
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

    public void removeTabPageAt(int index) {
        tabs.remove(index);
    }

    public void setPortletTabBarAt(int index, PortletTabBar tabBar) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setPortletTabBar(tabBar);
    }

    public void setSelectedPortletTabBar(PortletTabBar c) {
        int i;
        unselectLastTab();
        for (i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getPortletTabBar().equals(c)) {
                tab.setSelected(true);
                break;
            }
        }
    }

    public void setSelectedIndex(int index) {
        unselectLastTab();
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setSelected(true);
    }

    public void setSelectedTab(PortletTab tab) {
        unselectLastTab();
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab t = (PortletTab)tabs.get(i);
            if (t.equals(tab)) {
                t.setSelected(true);
                break;
            }
        }
    }

    public void setTitleAt(int index, String title) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setTitle(title);
    }

    private void unselectLastTab() {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.isSelected())
                tab.setSelected(false);
        }
    }

    public void setPortletTabs(ArrayList tabs) {
        this.tabs = tabs;
    }

    public List getPortletTabs() {
        return tabs;
    }

    public List init(List list) {
        list = super.init(list);
        selectedTabBar = getSelectedPortletTabBar();
        PortletTabBar tabBar = null;
        ComponentIdentifier compId;
        for (int i = 0; i < getTabCount(); i++) {
            compId = new ComponentIdentifier();
            tabBar = getPortletTabBarAt(i);
            compId.setPortletLifecycle(tabBar);
            compId.setComponentID(list.size());
            compId.setClassName(tabBar.getClass().getName());
            list.add(compId);
            list = tabBar.init(list);
        }
        return list;
    }

    public void login(GridSphereEvent event) {
        super.login(event);
        PortletTabBar tabBar = null;
        for (int i = 0; i < getTabCount(); i++) {
            tabBar = getPortletTabBarAt(i);
            tabBar.login(event);
        }
    }

    public void logout(GridSphereEvent event) {
        super.logout(event);
        PortletTabBar tabBar = null;
        for (int i = 0; i < getTabCount(); i++) {
            tabBar = getPortletTabBarAt(i);
            tabBar.logout(event);
        }
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        SportletRequest req = event.getSportletRequest();
        String tabchange = req.getParameter(GridSphereProperties.PORTLETTAB);
        if (tabchange != null) {
            int idx = indexOfTabPage(tabchange);
            setSelectedIndex(idx);
        }
        selectedTabBar = getSelectedPortletTabBar();
        selectedTabBar.actionPerformed(event);
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);

        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();

        int i;
        PrintWriter out = res.getWriter();

        // Make tab links
        String[] tabLinks = new String[tabs.size()];

        PortletURI sportletURI;
        String modeString;
        for (i = 0; i < tabs.size(); i++) {
            sportletURI = event.createNewAction(GridSphereEvent.Action.LAYOUT_ACTION, COMPONENT_ID, null);
            //try {
            // Create portlet link Href
            PortletTab tab = (PortletTab)tabs.get(i);
            sportletURI.addParameter(GridSphereProperties.PORTLETTAB, tab.getTitle());
            tabLinks[i] = sportletURI.toString();
            //} catch (Exception e) {
            //log.error("Unable to create portlet tab link: " + e.getMessage());
            //}
        }
        req.setAttribute(LayoutProperties.TABLINKS, tabLinks);

        // Render tabs titles

        ///out.println("<div class=\"tab-pane\">");
        ///out.println("<div class=\"tab-menu\">");
        ///out.println("<span class=\"tab-empty\">&nbsp;</span>");

        out.println("<table class=\"tab-pane\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("<tr>");

        PortletTab tab;
        for (i = 0; i < tabs.size(); i++) {
            String title = getTitleAt(i);
            tab = (PortletTab)tabs.get(i);
            if (tab.isSelected()) {
           ///     out.println("<span class=\"tab-active\">" + title + "</span>");

                out.println("<td class=\"tab-active\">" + title + "</td>");
            } else {
           ///     out.println("<span class=\"tab-inactive\"><a class=\"tab-menu\" href=\"" + tabLinks[i] + "\" >" +  title + "</a></span>");
                out.println("<td class=\"tab-inactive\"><a class=\"tab-menu\" href=\"" + tabLinks[i] + "\" >" +  title + "</a>");
            }
            out.println("<td class=\"tab-empty\">&nbsp;</td>");
        }

        ///out.println("</div></div>");//<div id=\"tab-bar\"></div>");
        out.println("</tr></table>");

        selectedTabBar.doRender(event);
    }

}
