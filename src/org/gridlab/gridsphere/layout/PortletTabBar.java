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

public class PortletTabBar extends BasePortletComponent {

    private PortletPanel selectedPanel = null;
    private List tabPages = new ArrayList();

    public void addTabPage(String title, PortletPanel panel) {
        PortletTabPage tabPage = new PortletTabPage(this, title, panel);
        tabPages.add(tabPage);
    }

    public PortletPanel getPortletPanelAt(int index) {
        PortletTabPage tabPage = (PortletTabPage)tabPages.get(index);
        return tabPage.getPortletPanel();
    }

    public PortletPanel getSelectedPortletPanel() {
        for (int i = 0; i < tabPages.size(); i++) {
            PortletTabPage tabPage = (PortletTabPage)tabPages.get(i);
            if (tabPage.isSelected())
                return tabPage.getPortletPanel();
        }
        return null;
    }

    public int getSelectedIndex() {
        for (int i = 0; i < tabPages.size(); i++) {
            PortletTabPage tabPage = (PortletTabPage)tabPages.get(i);
            if (tabPage.isSelected())
                return i;
        }
        return -1;
    }

    public PortletTabPage getSelectedTabPage() {
        for (int i = 0; i < tabPages.size(); i++) {
            PortletTabPage tabPage = (PortletTabPage)tabPages.get(i);
            if (tabPage.isSelected())
                return tabPage;
        }
        return null;
    }

    public int getTabPageCount() {
        return tabPages.size();
    }

    public String getTitleAt(int index) {
        PortletTabPage tabPage = (PortletTabPage)tabPages.get(index);
        return tabPage.getTitle();
    }

    public int indexOfPortletPanel(PortletPanel panel) {
        boolean found = false;
        int i;
        for (i = 0; i < tabPages.size(); i++) {
            PortletTabPage tabPage = (PortletTabPage)tabPages.get(i);
            if (tabPage.getPortletPanel().equals(panel)) {
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
        for (i = 0; i < tabPages.size(); i++) {
            PortletTabPage tabPage = (PortletTabPage)tabPages.get(i);
            if (tabPage.getTitle().equals(title)) {
                found = true;
                break;
            }
        }
        if (found)
            return i;
        return -1;
    }

    public void insertTabPage(String title, PortletPanel panel, int index) {
        PortletTabPage tabPage = new PortletTabPage(this, title, panel);
        tabPages.add(index, tabPage);
    }

    public void remove(PortletPanel panel) {
        for (int i = 0; i < tabPages.size(); i++) {
            PortletTabPage tabPage = (PortletTabPage)tabPages.get(i);
            if (tabPage.getPortletPanel().equals(panel)) {
                tabPages.remove(tabPage);
            }
        }
    }

    public void remove(int index) {
        tabPages.remove(index);
    }

    public void removeAll() {
        int i;
        for (i = 0; i < tabPages.size(); i++) {
            tabPages.remove(i);
        }
    }

    public void removeTabPageAt(int index) {
        tabPages.remove(index);
    }

    public void setPortletPanelAt(int index, PortletPanel panel) {
        PortletTabPage tabPage = (PortletTabPage)tabPages.get(index);
        tabPage.setPortletPanel(panel);
    }

    public void setSelectedPortletPanel(PortletPanel c) {
        int i;
        unselectLastTab();
        for (i = 0; i < tabPages.size(); i++) {
            PortletTabPage tabPage = (PortletTabPage)tabPages.get(i);
            if (tabPage.getPortletPanel().equals(c)) {
                tabPage.setSelected(true);
                break;
            }
        }
    }

    public void setSelectedIndex(int index) {
        unselectLastTab();
        PortletTabPage tabPage = (PortletTabPage)tabPages.get(index);
        tabPage.setSelected(true);
    }

    public void setSelectedTab(PortletTab tab) {
        unselectLastTab();
        for (int i = 0; i < tabPages.size(); i++) {
            PortletTabPage t = (PortletTabPage)tabPages.get(i);
            if (t.equals(tab)) {
                t.setSelected(true);
                break;
            }
        }
    }

    public void setTitleAt(int index, String title) {
        PortletTabPage tabPage = (PortletTabPage)tabPages.get(index);
        tabPage.setTitle(title);
    }

    private void unselectLastTab() {
        for (int i = 0; i < tabPages.size(); i++) {
            PortletTabPage tabPage = (PortletTabPage)tabPages.get(i);
            if (tabPage.isSelected())
                tabPage.setSelected(false);
        }
    }

    public void setPortletTabPages(ArrayList tabs) {
        this.tabPages = tabPages;
    }

    public List getPortletTabPages() {
        return tabPages;
    }

    public List init(List list) {
        list = super.init(list);
        selectedPanel = getSelectedPortletPanel();
        PortletPanel panel = null;
        ComponentIdentifier compId;
        for (int i = 0; i < getTabPageCount(); i++) {
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
        SportletRequest req = event.getSportletRequest();
        String tabchange = req.getParameter(GridSphereProperties.PORTLETTAB);
        if (tabchange != null) {
            int idx = indexOfTabPage(tabchange);
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
        String[] tabLinks = new String[tabPages.size()];

        PortletURI sportletURI;
        String modeString;
        for (i = 0; i < tabPages.size(); i++) {
            sportletURI = event.createNewAction(GridSphereEvent.Action.LAYOUT_ACTION, COMPONENT_ID, null);

            PortletTabPage tab = (PortletTabPage)tabPages.get(i);
            sportletURI.addParameter(GridSphereProperties.PORTLETTAB, tab.getTitle());
            tabLinks[i] = sportletURI.toString();
        }
        req.setAttribute(LayoutProperties.TABLINKS, tabLinks);

        // Render tabs titles
        out.println("<div id=\"tab-pane\">");
        out.println("<div class=\"tab-menu\">");

        PortletTabPage tabPage;
        for (i = 0; i < tabPages.size(); i++) {
            String title = getTitleAt(i);
            tabPage = (PortletTabPage)tabPages.get(i);
            if (tabPage.isSelected()) {
                out.println("<span id=\"tab-active\">" + title + "</span>");
            } else {
                out.println("<span id=\"tab-inactive\"><a class=\"tab-menu\" href=\"" + tabLinks[i] + "\" >" +  title + "</a></span>");
            }
        }

        out.println("</div></div><div id=\"tab-bar\"></div>");


        LayoutManager layoutManager = selectedPanel.getLayoutManager();
        layoutManager.doRender(event);

    }

}
