/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;

public class PortletGridLayout extends BaseLayoutManager {

    private int cols = 1;

    public PortletGridLayout() {}

    public PortletGridLayout(int cols) {
        this.cols = cols;
    }

    public String getClassName() {
        return PortletGridLayout.class.getName();
    }

    public void setColumns(int cols) {
        this.cols = cols;
    }

    public int getColumns() {
        return cols;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();

        if (insets == null) insets = new PortletInsets();

        int j = 0, k = 0;

        //out.println("row: "+rows+" columns "+cols);

        int max = components.size();
        PortletComponent p = null;

        //int gwidth = 100 / cols;

        //out.println("<table width=\"" + gwidth + "%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\">");
        out.println("<table border=\"0\" width=\"100%\">");

        while (k < max) {
            out.println("<tr>");
            //insets.doRenderFirst(ctx,req,res);
            //insets.doRenderLast(ctx,req,res);
            while (j < cols) {
                p = (PortletComponent)components.get(k);
                //@todo need that for defined with in the porlet component (oliver)

                if (p.getWidth().equals("100%")) {
                    out.println("<td width=\"100%\" valign=\"top\">");
                } else {
                    out.println("<td valign=\"top\">");
                }
                if (p.isVisible()) {
                    p.doRender(event);
                }
                j++; k++;
                out.println("</td>");
            }
            j = 0;
           out.println("</tr>");
        }

        out.println("</table>");
    }

}

