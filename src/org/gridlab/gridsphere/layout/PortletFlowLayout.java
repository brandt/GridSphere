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
import java.util.Iterator;

public class PortletFlowLayout extends BasePortletComponent implements LayoutManager {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletFlowLayout.class);

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

    public void doLayoutAction(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        Iterator it = components.iterator();
        LayoutActionListener p = null;
        while (it.hasNext()) {
            p = (LayoutActionListener)it.next();
            p.doLayoutAction(ctx, req, res);
        }
    }

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderFirst(ctx, req, res);
        PrintWriter out = res.getWriter();

        int i = 0;
        int max = components.size();
        LayoutActionListener p = null;

        int gwidth = 100 / max;

        out.println("<table width=\"" + gwidth + "%\"  border=\"0\" cellspacing=\"2\" cellpadding=\"0\" bgcolor=\"#FFFFFF\">");

        while (i < max) {

            out.println("<tr>");

                out.println("<td>");
                p = (LayoutActionListener)components.get(i);
                p.doRenderFirst(ctx, req, res);
                p.doRenderLast(ctx, req, res);
                out.println("</td>");
             i++;

           out.println("</tr>");
        }

        out.println("</table>");

    }

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderLast(ctx, req, res);
    }


}

