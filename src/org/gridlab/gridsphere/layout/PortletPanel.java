/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class PortletPanel extends BasePortletComponent {

    private LayoutManager layoutManager;

    public PortletPanel() {}

    public List init(List list) {
        super.init(list);
        return layoutManager.init(list);
    }

    public void destroy() {}

    public PortletPanel(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        layoutManager.doRender(event);
    }
}
