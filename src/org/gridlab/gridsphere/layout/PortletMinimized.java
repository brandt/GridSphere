/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.*;

import java.io.PrintWriter;
import java.io.IOException;

public class PortletMinimized extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletMinimized.class);

    private PortletImage image;
    private String title;
    private String color;
    private String portletClass;

    public PortletMinimized() {}

    public PortletMinimized(String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setPortletImage(PortletImage image) {
        this.image = image;
    }

    public PortletImage getPortletImage() {
        return image;
    }

    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public String getPortletClass() {
        return portletClass;
    }

    public void doRender(PortletContext ctx, PortletRequest req, PortletResponse res) throws PortletLayoutException, IOException {
        log.debug("in doRender()");
        try {
            req.setAttribute("title", title);
            req.setAttribute("color", color);
            ctx.include("/WEB-INF/conf/layout/portlet-minimized.jsp", req, res);
        } catch (PortletException e) {
            log.error("Unable to include component JSP", e);
            throw new PortletLayoutException("Unable to include PortletMinimized component JSP", e);
        }
    }
}
