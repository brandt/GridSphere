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

public class PortletGridLayout extends BasePortletComponent implements PortletFrameListener, LayoutManager  {

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

    public List init(List list) {
        this.id = list.size();
        Iterator it = components.iterator();
        PortletComponent p = null;
        while (it.hasNext()) {
            p = (PortletComponent)it.next();
            //PortletRender a = (PortletRender)p;
            list.add(p);
            // invoke init on each component
            list = p.init(list);
            // If the component is a frame we want to be notified
            if (p instanceof PortletFrame) {
                PortletFrame f = (PortletFrame)p;
                f.addFrameListener(this);
            }
        }
        return list;
    }

    public void handleFrameMaximized(PortletFrameEvent event) {
        Iterator it = components.iterator();
        PortletComponent p = null;
        int id = event.getID();

        while (it.hasNext()) {
            p = (PortletComponent)it.next();

            // check for the frame that has been maximized
            if (p.getID() == id) {
                p.setWidth("100%");
            } else {
                // If this is not the right frame, make it invisible
                p.setVisible(false);
            }
        }
    }

    public void handleFrameMinimized(PortletFrameEvent event) {
        Iterator it = components.iterator();
        PortletComponent p = null;
        int id = event.getID();

        while (it.hasNext()) {
            p = (PortletComponent)it.next();
            p.setVisible(true);
        }
    }

    public void handleFrameEvent(PortletFrameEvent event) throws PortletLayoutException {
        if (event.getAction() == PortletFrameEvent.Action.FRAME_MAXIMIZED) {
            handleFrameMaximized(event);
        } else if (event.getAction() == PortletFrameEvent.Action.FRAME_MINIMIZED) {
            handleFrameMinimized(event);
        }
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
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

