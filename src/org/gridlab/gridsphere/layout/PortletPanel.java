/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PortletPanel {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletPanel.class);

    private LayoutManager layoutManager;

    public PortletPanel() {}

    public PortletPanel(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        log.debug("in doRenderFirst()");
        layoutManager.doRenderFirst(ctx, req, res);
    }

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        log.debug("in doRenderLast()");
        layoutManager.doRenderLast(ctx, req, res);
    }

}
