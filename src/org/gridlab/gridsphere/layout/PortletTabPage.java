/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;

import java.util.List;
import java.util.Iterator;
import java.io.IOException;


public class PortletTabPage extends BasePortletLifecycle {

    private String title;
    private PortletPanel panel;
    private boolean selected = false;
    private PortletTabBar parent;
    private LayoutManager layoutManager;

    public PortletTabPage() {}

    public PortletTabPage(PortletTabBar parent, String title, PortletPanel panel) {
        this.parent = parent;
        this.title = title;
        this.panel = panel;
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

    public PortletPanel getPortletPanel() {
        return panel;
    }

    public void setPortletPanel(PortletPanel panel) {
        this.panel = panel;
    }

}
