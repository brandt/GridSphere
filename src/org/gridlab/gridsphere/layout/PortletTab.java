/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class PortletTab extends BasePortletLifecycle {

    private String title;
    private boolean selected = false;
    private PortletTabbedPane tabbedPane;
    private PortletTabBar portletTabBar;

    public PortletTab() {}

    public PortletTab(PortletTabbedPane tabbedPane, String title, PortletTabBar portletTabBar) {
        this.tabbedPane = tabbedPane;
        this.title = title;
        this.portletTabBar = portletTabBar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setPortletTabBar(PortletTabBar portletTabBar) {
        this.portletTabBar = portletTabBar;
    }

    public PortletTabBar getPortletTabBar() {
        return portletTabBar;
    }

    public List init(List list) {
        return portletTabBar.init(list);
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        portletTabBar.actionPerformed(event);
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {

        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();
        portletTabBar.doRender(event);
    }

}
