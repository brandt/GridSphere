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

    public PortletTabPage() {}

    public PortletTabPage(String title, PortletPanel panel) {
        this.title = title;
        this.panel = panel;
    }

    public List init(List list) {
        COMPONENT_ID = list.size();
        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletLifecycle(this);
        compId.setComponentID(list.size());
        compId.setClassName(this.getClass().getName());
        list.add(compId);
        return panel.init(list);
    }

    public void destroy() {}

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

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        setSelected(true);
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        panel.doRender(event);
    }
}
