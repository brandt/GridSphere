/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.event.WindowListener;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class PortletGridLayout extends BaseLayoutManager {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletGridLayout.class);

    private int rows = 1;
    private int cols = 1;

    public PortletGridLayout() {}

    public PortletGridLayout(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public String getClassName() {
        return PortletGridLayout.class.getName();
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return rows;
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

        int i = 0, j = 0, k = 0;
        if (components.size() > rows*cols)
            throw new PortletLayoutException("Number of components specified exceeds rows * columns");

        int max = components.size();
        PortletComponent p = null;

        int gwidth = 100 / cols;

        out.println("<table width=\"" + gwidth + "%\"  border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\">");

        while ((i < rows) && (k < max)) {

            out.println("<tr>");
            //insets.doRenderFirst(ctx,req,res);
            //insets.doRenderLast(ctx,req,res);
            while ((j < cols) && (k < max)) {
                out.println("<td>");
                p = (PortletComponent)components.get(k);
                if (p.isVisible()) {
                    p.doRender(event);
                }
                j++; k++;
                out.println("</td>");
            }
            j = 0; i++;

           out.println("</tr>");
        }

        out.println("</table>");
    }

}

