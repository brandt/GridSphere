/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletContext;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import java.util.Hashtable;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;

public class PortletGridLayout extends BasePortletComponent implements LayoutManager {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletGridLayout.class);

    private int rows = 1;
    private int cols = 1;

    public PortletGridLayout() {}

    public PortletGridLayout(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
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

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderFirst(ctx, req, res);
        log.debug("in doRenderFirst()");
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
                p.doRenderFirst(ctx, req, res);
                p.doRenderLast(ctx, req, res);
                j++; k++;
                out.println("</td>");
            }
            j = 0; i++;

           out.println("</tr>");
        }

        out.println("</table>");
    }

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderLast(ctx, req, res);
        log.debug("in doRenderLast()");
    }

}

