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
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;

public class PortletTabbedPane extends BasePortletComponent implements PortletTabListener {

    private List tabs = new ArrayList();
    private int selectedIndex = -1;
    private String style = "menu";

    public PortletTabbedPane() {}

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void addTab(String title, PortletComponent comp) {
        PortletTab tab = new PortletTab(title, comp);
        tabs.add(tab);
    }

    public void setSelectedIndex(int index) {
        this.selectedIndex = index;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public PortletTab getSelectedTab() {
        return (PortletTab)tabs.get(selectedIndex);
    }

    public PortletTab getPortletTabAt(int index) {
        return (PortletTab)tabs.get(index);
    }

    public int getTabCount() {
        return tabs.size();
    }

    public String getTitleAt(int index) {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getTitle();
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

    public void insertTab(PortletTab tab, int index) {
        tabs.add(index, tab);
    }

    public void removeTab(PortletTab tab) {
        if (tabs.contains(tab)) tabs.remove(tab);
    }

    public void removeTabAt(int index) {
        tabs.remove(index);
    }

    public void removeAll() {
        for (int i = 0; i < tabs.size(); i++) {
            tabs.remove(i);
        }
    }

    public void setTitleAt(int index, String title) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setTitle(title);
    }

    public void setPortletTabs(ArrayList tabs) {
        this.tabs = tabs;

    }

    public void setSelectedPortletTab(PortletTab tab) {
        PortletTab portletTab;
        for (int i = 0; i < tabs.size(); i++) {
            portletTab = (PortletTab)tabs.get(i);
            if (portletTab.getComponentID() == tab.getComponentID()) {
                selectedIndex = i;
                break;
            }
        }
    }

    public List getPortletTabs() {
        return tabs;
    }

    public List init(List list) {
        if (selectedIndex < 0) selectedIndex = 0;
        PortletTab tab = null;
        for (int i = 0; i < getTabCount(); i++) {
            tab = getPortletTabAt(i);
            tab.addPortletTabListener(this);
            list = tab.init(list);
        }
        return list;
    }

    public void login(GridSphereEvent event) {
        super.login(event);
        PortletTab tab = null;
        for (int i = 0; i < getTabCount(); i++) {
            tab = getPortletTabAt(i);
            tab.login(event);
        }
    }

    public void logout(GridSphereEvent event) {
        super.logout(event);
        PortletTab tab = null;
        for (int i = 0; i < getTabCount(); i++) {
            tab = getPortletTabAt(i);
            tab.logout(event);
        }
    }

    public void handlePortletTabEvent(PortletTabEvent event) {
        if (event.getAction() == PortletTabEvent.Action.TAB_SELECTED) {
            PortletTab selectedTab = event.getPortletTab();
            setSelectedPortletTab(selectedTab);
        }
    }

    public String[] makeTabLinks(GridSphereEvent event) throws IOException {
        SportletRequest req = event.getSportletRequest();

        // Make tab links
        String[] tabLinks = new String[tabs.size()];

        PortletURI sportletURI;
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            sportletURI = event.createNewAction(GridSphereEvent.Action.LAYOUT_ACTION, tab.getComponentID(), null);

            sportletURI.addParameter(GridSphereProperties.PORTLETTAB, tab.getTitle());
            tabLinks[i] = sportletURI.toString();
        }
        req.setAttribute(LayoutProperties.TABLINKS, tabLinks);
        return tabLinks;
    }

    public void doRenderMenu(GridSphereEvent event, String[] links) throws PortletLayoutException, IOException {
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();

        // Render tabs titles

        ///out.println("<div class=\"tab-pane\">");
        ///out.println("<div class=\"tab-menu\">");
        ///out.println("<span class=\"tab-empty\">&nbsp;</span>");

        out.println("<table class=\"tab-pane\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("<tr>");

        PortletTab tab;
        for (int i = 0; i < tabs.size(); i++) {
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

        out.println("</div></div>");
        PortletTab selectedTab = (PortletTab)tabs.get(selectedIndex);
        if (selectedTab != null)
            selectedTab.doRender(event);

    }

    public void doRenderSubMenu(GridSphereEvent event, String[] links) throws PortletLayoutException, IOException {
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();

        // Render tabs titles
        out.println("<div class=\"tab-sub-pane\">");
        out.println("<div class=\"tab-sub-menu\">");

        PortletTab tab;
        for (int i = 0; i < tabs.size(); i++) {
            String title = getTitleAt(i);
            tab = (PortletTab)tabs.get(i);
            if (tab.isSelected()) {
                out.println("<span class=\"tab-sub-active\">" + title + "</span>");
            } else {
                out.println("<span class=\"tab-sub-inactive\"><a class=\"tab-sub-menu\" href=\"" + links[i] + "\" >" +  title + "</a></span>");
            }
        }

        out.println("</div></div><div class=\"tab-bar\"></div>");
        PortletTab selectedTab = (PortletTab)tabs.get(selectedIndex);
        if (selectedTab != null)
            selectedTab.doRender(event);
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);

        String[] links = makeTabLinks(event);

        System.err.println("in tabbed pane: style=" + style);
        if (style.equals("sub-menu")) {
            doRenderSubMenu(event, links);
        } else {
            doRenderMenu(event, links);
        }
        ///out.println("</div></div>");//<div id=\"tab-bar\"></div>");
        out.println("</tr></table>");

    }

}
