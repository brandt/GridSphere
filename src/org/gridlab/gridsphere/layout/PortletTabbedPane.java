/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletURI;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.event.WindowListener;
import org.gridlab.gridsphere.event.WindowEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

public class PortletTabbedPane extends BasePortletComponent {

    private static PortletLog log = SportletLog.getInstance(PortletTabbedPane.class);

    protected String name = PortletTabbedPane.class.getName();

    private PortletPanel selectedPanel = null;
    private Vector tabs = new Vector();

    public PortletTabbedPane() {}

    public void addTab(String title, PortletPanel panel) {
        PortletTab tab = new PortletTab(this, title, panel);
        tabs.add(tab);
    }

    public String getBackgroundAt(int index)  {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getBackground();
    }

    public PortletPanel getPortletPanelAt(int index)  {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getPortletPanel();
    }

    public String getForegroundAt(int index) {
        PortletTab tab = (PortletTab)tabs.get(index);
        return tab.getForeground();
    }

    public PortletPanel getSelectedPortletPanel() {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getSelected())
                return tab.getPortletPanel();
        }
        return null;
    }

    public int getSelectedIndex() {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getSelected())
                return i;
        }
        return -1;
    }

    public PortletTab getSelectedTab() {
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab)tabs.get(i);
            if (tab.getSelected())
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

    public void setBackgroundAt(int index, String background) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setBackground(background);
    }

    public void setPortletPanelAt(int index, PortletPanel panel) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setPortletPanel(panel);
    }

    public void setForegroundAt(int index, String foreground) {
        PortletTab tab = (PortletTab)tabs.get(index);
        tab.setForeground(foreground);
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
            if (tab.getSelected())
                tab.setSelected(false);
        }
    }

    public void setPortletTabs(Vector tabs) {
        this.tabs = tabs;
    }

    public List getPortletTabs() {
        return tabs;
    }

    public String getClassName() {
        return PortletTabbedPane.class.getName();
    }

    public List init(List list) {
        log.info("in init()");
        this.id = list.size();
        selectedPanel = getSelectedPortletPanel();
        PortletPanel panel = null;
        for (int i = 0; i < getTabCount(); i++) {
            panel = getPortletPanelAt(i);
            list.add((PortletComponent)panel);
            list = panel.init(list);
        }
        return list;
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        SportletRequest req = event.getSportletRequest();
        String tabchange = req.getParameter(GridSphereProperties.PORTLETTAB);
        if (tabchange != null) {
            int idx = indexOfTab(tabchange);
            setSelectedIndex(idx);
        }
        selectedPanel = getSelectedPortletPanel();
        selectedPanel.actionPerformed(event);
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
            sportletURI = event.createNewAction(GridSphereEvent.Action.LAYOUT_ACTION, this.id, null);
            try {
                // Create portlet link Href
                PortletTab tab = (PortletTab)tabs.get(i);
                sportletURI.addParameter(GridSphereProperties.PORTLETTAB, tab.getTitle());
                tabLinks[i] = sportletURI.toString();
            } catch (Exception e) {
                log.error("Unable to create portlet tab link: " + e.getMessage());
            }
        }
        req.setAttribute(LayoutProperties.TABLINKS, tabLinks);


        // Render tabs titles
        out.println("<table width=\"100%\">");
        for (i = 0; i < tabs.size(); i++) {
            String title = getTitleAt(i);
            out.println("<th><b>" + "<a href=\"" + tabLinks[i] + "\" >" +  title + "</a></b></th>");
        }
        out.println("</table>");

        out.println("<table width=\"100%\">");
        out.println("<tr><td>");
        selectedPanel.doRender(event);
        out.println("</td></tr></table>");
    }


}
