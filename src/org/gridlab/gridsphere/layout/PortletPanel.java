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

    private PortletLayout layout;

    public PortletPanel() {}

    public List init(List list) {
        super.init(list);
        return layout.init(list);
    }

    public void destroy() {}

    public PortletPanel(PortletLayout layout) {
        this.layout = layout;
    }

    public void setLayout(PortletLayout layout) {
        this.layout = layout;
    }

    public PortletLayout getLayout() {
        return layout;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        if (layout != null) layout.doRender(event);
    }
}
