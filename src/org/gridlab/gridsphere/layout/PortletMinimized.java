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
import java.io.PrintWriter;

public class PortletMinimized extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletMinimized.class);

    private PortletImage image;
    private String title;
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

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderFirst(ctx, req, res);
        log.debug("in doRenderFirst()");
        PrintWriter out = res.getWriter();
        out.println("portlet minimized");
    }

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderLast(ctx, req, res);
        log.debug("in doRenderLast()");
    }

}
