/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletContext;

import java.io.IOException;

public interface PortletRender {

    /**
     * @deprecated
     */
    public void doRender(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException;

    public void doRenderFirst(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException;

    public void doRenderLast(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException;

}
