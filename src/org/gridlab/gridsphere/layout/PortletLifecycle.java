/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.event.WindowListener;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PortletLifecycle extends PortletRender {

    public List init(List list);

    public void login();

    public void logout();

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException;

    public void destroy();

}
