/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;

import java.io.PrintWriter;

public class PortletInsets {

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

    public void doRender(PrintWriter out) {
        log.debug("in doRender()");
        out.write("<img SRC=/images/spacer.gif" + " WIDTH=" + width + " HEIGHT=" + height + " BORDER=0>");
    }
}
