/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.util.List;

public abstract class BaseComponentLifecycle implements ComponentLifecycle {

    protected int COMPONENT_ID = -1;

    public List init(List list) { return list; }

    public void destroy() {}

    public int getComponentID() {
        return COMPONENT_ID;
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {

    }

    public abstract void doRender(GridSphereEvent event) throws PortletLayoutException, IOException;

}
