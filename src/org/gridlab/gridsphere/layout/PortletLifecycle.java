/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.util.List;

public interface PortletLifecycle extends PortletRender {

    public List init(List list);

    public void login(GridSphereEvent event);

    public void logout(GridSphereEvent event);

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException;

    public void destroy();

    public int getComponentID();

}
