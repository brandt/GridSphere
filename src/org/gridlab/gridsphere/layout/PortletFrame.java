/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;

import java.io.PrintWriter;

public class PortletFrame extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletFrame.class);

    private PortletBorder portletBorder;
    private String portletClass;

    public PortletFrame() {}

    public void setPortletBorder(PortletBorder portletBorder) {
        this.portletBorder = portletBorder;
    }

    public PortletBorder getPortletBorder() {
        return portletBorder;
    }

    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public String getPortletClass() {
        return portletClass;
    }

    public void doRender(PrintWriter out) {
        log.debug("in doRender()");
    }
}
