/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PortletInsets implements PortletRender {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletInsets.class);

    public int width, height;

    public PortletInsets() {}

    public PortletInsets(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        log.debug("in doRenderFirst()");
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();
        out.write("<img SRC=/images/spacer.gif" + " WIDTH=" + width + " HEIGHT=" + height + " BORDER=0>");
    }


}

