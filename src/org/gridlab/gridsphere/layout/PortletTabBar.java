/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;

public class PortletTabBar extends BasePortletComponent {

    protected transient static PortletLog log = SportletLog.getInstance(PortletTabBar.class);

    private List tabPages = new ArrayList();
    //private PortletTabPage selectedTabPage;
    private int selectedIndex =- 1;

    public void addTabPage(String title, PortletPanel panel) {
        PortletTabPage tabPage = new PortletTabPage(title, panel);
        tabPages.add(tabPage);
    }

    public void setSelectedIndex(int index) {
        this.selectedIndex = index;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public PortletTabPage getTabPageAt(int index) {
        return (PortletTabPage)tabPages.get(index);
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

    public void removeAll() {
        for (int i = 0; i < tabPages.size(); i++) {
            tabPages.remove(i);
        }
    }

    public void removeTabPageAt(int index) {
        tabPages.remove(index);
    }

    public void setTitleAt(int index, String title) {
        PortletTabPage tabPage = (PortletTabPage)tabPages.get(index);
        tabPage.setTitle(title);
    }

    public List getPortletTabPages() {
        return tabPages;
    }

    public List init(List list) {
        if (selectedIndex < 0) selectedIndex = 0;
        PortletTabPage tabPage = null;
        for (int i = 0; i < getTabPageCount(); i++) {
            tabPage = getTabPageAt(i);
            list = tabPage.init(list);
        }
        return list;
    }

    public void login(GridSphereEvent event) {
        super.login(event);
        PortletTabPage tabPage = null;
        for (int i = 0; i < getTabPageCount(); i++) {
            tabPage = getTabPageAt(i);
            tabPage.login(event);
        }
    }

    public void logout(GridSphereEvent event) {
        super.logout(event);
        PortletTabPage tabPage = null;
        for (int i = 0; i < getTabPageCount(); i++) {
            tabPage = getTabPageAt(i);
            tabPage.logout(event);
        }
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
        for (i = 0; i < tabPages.size(); i++) {
            PortletTabPage tabPage = (PortletTabPage)tabPages.get(i);
            sportletURI = event.createNewAction(GridSphereEvent.Action.LAYOUT_ACTION, tabPage.getComponentID(), null);
            sportletURI.addParameter(GridSphereProperties.PORTLETTAB, tabPage.getTitle());
            tabLinks[i] = sportletURI.toString();
        }
        req.setAttribute(LayoutProperties.TABLINKS, tabLinks);


        out.println("<table border=\"0\" class=\"tab-sub-pane\" width=\"100%\"><tr><td>");

        out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr>");
        PortletTabPage tabPage;
        for (i = 0; i < tabPages.size(); i++) {
            String title = getTitleAt(i);
            tabPage = (PortletTabPage)tabPages.get(i);
            if (tabPage.isSelected()) {
                ///out.println("<span class=\"tab-sub-active\">" + title + "</span>");
                out.println("<td> <span class=\"tab-sub-active\">"+title+"</span></td>");
            } else {
                ///out.println("<span class=\"tab-sub-inactive\"><a class=\"tab-sub-menu\" href=\"" + tabLinks[i] + "\" >" +  title + "</a></span>");
                out.println("<td> <span class=\"tab-sub-inactive\">");
                out.println("<a class=\"tab-sub-menu\" href=\"" + tabLinks[i] + "\" >" +  title + "</a>");
                out.println("</span></td>");
            }
        }

        //out.println("</div></div><div class=\"tab-bar\"></div>");
        PortletTabPage selectedTabPage = (PortletTabPage)tabPages.get(selectedIndex);
        if (selectedTabPage != null)
            selectedTabPage.doRender(event);
        out.println("</tr></table>");

        out.println("</td></tr></table>");
        LayoutManager layoutManager = selectedPanel.getLayoutManager();
        layoutManager.doRender(event);

    }

}
