/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletConfig;

import javax.servlet.ServletConfig;
import java.io.PrintWriter;
import java.io.IOException;

public class PortletLayoutEngine {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletLayoutEngine.class);
    private static PortletLayoutEngine instance = null;
    private PortletConfig config = null;
    private PortletLayoutDescriptor layout = null;
    private boolean reload = false;
    private String error = "";

    private PortletLayoutEngine(PortletConfig config) throws PortletLayoutDescriptorException {
        this.config = config;
        layout = new PortletLayoutDescriptor(config);
    }

    public static PortletLayoutEngine getInstance(PortletConfig config) throws PortletLayoutDescriptorException {
        if (instance == null) {
            instance = new PortletLayoutEngine(config);
        }
        return instance;
    }

    public void setAutomaticReload(boolean reload) {
        this.reload = reload;
    }

    public boolean getAutomaticReload() {
        return reload;
    }

    public void doRender(PortletRequest req, PortletResponse res) throws PortletLayoutException {
        // for now just render
        log.debug("in doRender()");
        if (reload) {
            layout.reload();
        }
        PrintWriter out = null;
        PortletContainer pc = layout.getPortletContainer();

        try {
            out = res.getWriter();
            if (layout == null) {
               doRenderError(req, res);
            }
            pc.doRender(config.getContext(), req, res);
        } catch (IOException e) {
            error = e.getMessage();
            log.error("Caught IOException: ", e);
            throw new PortletLayoutException("Caught IOException", e);
        }
    }

    public void doRenderError(PortletRequest req, PortletResponse res) {
        PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException e) {
            log.error("in doRenderError: ", e);
        }
        out.println("<h>Portlet Layout Engine unable to render!</h>");
        out.println("<b>" + error + "</b>");
    }

}
