/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;

public class PortletFlowLayout extends BaseLayoutManager {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int CENTER = 3;

    private int align = LEFT;
    private int hgap = 1;
    private int vgap = 1;

    public PortletFlowLayout() {}

    public PortletFlowLayout(int align) {
        this.align = align;
    }

    public PortletFlowLayout(int align, int hgap, int vgap) {
        this.align = align;
        this.hgap = hgap;
        this.vgap = vgap;
    }

    public void setAlignment(int align) {
        this.align = align;
    }

    public int getAlignment() {
        return align;
    }

    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    public int getHgap() {
        return hgap;
    }

    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    public int getVgap() {
        return vgap;
    }

/*
    public void doLayoutAction(GridSphereEvent event) throws PortletLayoutException, IOException {
        Iterator it = components.iterator();
        PortletRender p = null;
        while (it.hasNext()) {
            p = (PortletRender)it.next();
            p.doLayoutAction(ctx, req, res);
        }
    }
    */

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();

        int i = 0;
        int max = components.size();
        PortletRender p = null;

        int gwidth = 100 / max;

        out.println("<table width=\"" + gwidth + "%\"  border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\">");

        while (i < max) {

            out.println("<tr>");

                out.println("<td>");
                p = (PortletRender)components.get(i);
                p.doRender(event);

                out.println("</td>");
             i++;

           out.println("</tr>");
        }

        out.println("</table>");

    }


}

