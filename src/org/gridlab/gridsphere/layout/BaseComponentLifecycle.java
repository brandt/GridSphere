/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

import java.io.IOException;
import java.util.List;

public abstract class BaseComponentLifecycle implements ComponentLifecycle {

    protected int COMPONENT_ID = -1;
    protected String componentIDStr = "-1";

    public List init(List list) {
        this.COMPONENT_ID = list.size();
        componentIDStr = String.valueOf(COMPONENT_ID);
        return list;
    }

    public void destroy() {
    }

    public int getComponentID() {
        return COMPONENT_ID;
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletRequest req = event.getPortletRequest();
        req.setAttribute(GridSphereProperties.COMPONENT_ID, componentIDStr);
    }

    public abstract void doRender(GridSphereEvent event) throws PortletLayoutException, IOException;

}
