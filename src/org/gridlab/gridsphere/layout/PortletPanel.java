/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;

import java.io.PrintWriter;

public class PortletPanel extends BasePortletComponent {

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

    public void doRender(PrintWriter out) {
        log.debug("in doRender()");
    }

}
