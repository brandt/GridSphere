/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;

public class PortletImage extends BasePortletComponent {

    private String image;

    public PortletImage() {}

    public PortletImage(String image) {
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();
        out.println("<table width=\"100%\"><td width=\"1\">");
        out.println("<spacer type=block width=\"100\"></td><td>");
        out.println("<img src=\"" + image + "\" align=\"right\">");
        out.println("</td></table>");
    }

}
