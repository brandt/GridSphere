/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletLog;

import javax.servlet.ServletConfig;
import java.io.PrintWriter;
import java.io.IOException;

public class PortletLayoutEngine {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletLayoutEngine.class);
    private static PortletLayoutEngine instance = null;
    private PortletLayoutDescriptor layout = null;
    private String error = "";

    private PortletLayoutEngine(ServletConfig config) throws PortletLayoutDescriptorException {
        layout = new PortletLayoutDescriptor(config);
    }

    public static PortletLayoutEngine getInstance(ServletConfig config) throws PortletLayoutDescriptorException {
        if (instance == null) {
            instance = new PortletLayoutEngine(config);
        }
        return instance;
    }

    public void doRender(PortletResponse response) throws PortletLayoutException {
        // for now just render
        log.debug("in doRender()");
        PrintWriter out = null;
        PortletContainer pc = layout.getPortletContainer();

        try {
            out = response.getWriter();
            if (layout == null) {
               doRenderError(out);
            }
            pc.doRender(out);
        } catch (IOException e) {
            error = e.getMessage();
            log.error("Caught IOException: ", e);
            doRenderError(out);
            throw new PortletLayoutException("Caught IOException", e);
        }
    }

    public void doRenderError(PrintWriter out) {
        out.println("<h>Portlet Layout Engine unable to render!</h>");
        out.println("<b>" + error + "</b>");
    }

}
