/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PortletRender {

    /**
     * @deprecated
     */
    //public void doRender(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException;

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException;

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException;

}
