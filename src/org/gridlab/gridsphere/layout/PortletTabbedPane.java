/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PortletTabbedPane extends BasePortletComponent {

    private PortletPanel selectedPanel = null;
    private List tabs = new ArrayList();

    public PortletTabbedPane() {}

    public void addTab(String title, PortletPanel panel) {
        PortletTab tab = new PortletTab(this, title, panel);
        tabs.add(tab);
    }

    public PortletPanel getPortletPanelAt(int index)  {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getPortletPanel();
    }

    public PortletPanel getSelectedPortletPanel() {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.isSelected())
                return tab.getPortletPanel();
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

    public int indexOfPortletPanel(PortletPanel panel) {
        boolean found = false;
        int i;
        for (i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getPortletPanel().equals(panel)) {
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

    public void insertTab(String title, PortletPanel panel, int index) {
        PortletTab tab = new PortletTab(this, title, panel);
        tabs.add(index, tab);
    }

    public void remove(PortletPanel panel) {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getPortletPanel().equals(panel)) {
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

    public void setPortletPanelAt(int index, PortletPanel panel) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setPortletPanel(panel);
    }

    public void setSelectedPortletPanel(PortletPanel c) {
        int i;
        unselectLastTab();
        for (i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getPortletPanel().equals(c)) {
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

    public String getClassName() {
        return PortletTabbedPane.class.getName();
    }

    public List init(List list) {
        list = super.init(list);
        System.err.println("COMP ID for tabbed pane: " + COMPONENT_ID);
        selectedPanel = getSelectedPortletPanel();
        PortletPanel panel = null;
        ComponentIdentifier compId;
        for (int i = 0; i < getTabCount(); i++) {
            compId = new ComponentIdentifier();
            panel = getPortletPanelAt(i);
            LayoutManager manager = panel.getLayoutManager();
            compId.setPortletLifecycle(manager);
            compId.setComponentID(list.size());
            compId.setClassName(manager.getClass().getName());
            list.add(compId);
            list = manager.init(list);
        }
        return list;
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        System.err.println("Doing an action in tabbed pane!!!!!!!!!!!!!!!!");
        SportletRequest req = event.getSportletRequest();
        String tabchange = req.getParameter(GridSphereProperties.PORTLETTAB);
        if (tabchange != null) {
            int idx = indexOfTab(tabchange);
            setSelectedIndex(idx);
        }
        selectedPanel = getSelectedPortletPanel();
        LayoutManager layoutManager = selectedPanel.getLayoutManager();
        layoutManager.actionPerformed(event);
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
        out.println("<div id=\"tab-pane\">");
        out.println("<div class=\"tab-menu\">");

        PortletTab tab;
        for (i = 0; i < tabs.size(); i++) {
            String title = getTitleAt(i);
            tab = (PortletTab)tabs.get(i);
            if (tab.isSelected()) {
                out.println("<span id=\"tab-active\">" + title + "</span>");
            } else {
                out.println("<span id=\"tab-inactive\"><a class=\"tab-menu\" href=\"" + tabLinks[i] + "\" >" +  title + "</a></span>");
            }
        }

        out.println("</div></div><div id=\"tab-bar\"></div>");

        ///////////// OLD STUFF
        //out.println("<table width=\"100%\">");
        /*
        for (i = 0; i < tabs.size(); i++) {
            String title = getTitleAt(i);
            out.println("<th><b>" + "<a href=\"" + tabLinks[i] + "\" >" +  title + "</a></b></th>");
        }
        out.println("</table>");

        out.println("<table width=\"100%\">");
        out.println("<tr><td>");
        */
        LayoutManager layoutManager = selectedPanel.getLayoutManager();
        layoutManager.doRender(event);
        //out.println("</td></tr></table>");

    }


}
