/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.util.List;

public class PortletPanel extends BasePortletComponent {

    private LayoutManager layoutManager;

    public PortletPanel() {}

    public String getClassName() {
        return PortletPanel.class.getName();
    }

    public List init(List list) {
        list = super.init(list);
        PortletLifecycle cycle = (PortletLifecycle)layoutManager;
        list.add(cycle);
        list = cycle.init(list);
        return list;
    }

    public void destroy() {
        layoutManager.destroy();
    }

    public PortletPanel(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        layoutManager.actionPerformed(event);
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        layoutManager.doRender(event);
    }

}
