/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface LayoutActionListener {

    /**
     * Called first to process component changes from Layout events including window state and portlet mode changes
     */
    //public void doProcessEvents(GridSphereEvent gsEvt) throws PortletLayoutException, IOException;
    public void doLayoutAction(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException;

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException;

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException;

}
