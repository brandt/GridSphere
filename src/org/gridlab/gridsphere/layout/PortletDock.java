/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletContext;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import java.util.Vector;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.io.PrintWriter;
import java.io.IOException;

public class PortletDock extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletDock.class);

    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private int orientation;
    private String title = "";
    private PortletInsets margin;
    //private List components = new Vector();

    public PortletDock() {}

    public PortletDock(String title) {
        this.title = title;
    }

    public PortletDock(int orientation) {
        this.orientation = orientation;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getComponentIndex(PortletComponent c) {
        return components.indexOf(c);
    }

    public PortletComponent getComponentAtIndex(int i) {
        return (PortletComponent)components.get(i);
    }

    public void setMargin(PortletInsets margin) {
        this.margin = margin;
    }

    public PortletInsets getMargin() {
        return margin;
    }

    public void doRenderFirst(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderFirst(ctx, req, res);
        log.debug("in doRenderFirst()");
        PrintWriter out = res.getWriter();
        out.write("<tr><td>" + title);
        ListIterator compIt = components.listIterator();
        while (compIt.hasNext()) {
            PortletComponent comp = (PortletComponent)compIt.next();
            comp.doRenderFirst(ctx, req, res);
            margin.doRenderFirst(ctx, req, res);
            margin.doRenderLast(ctx, req, res);
            comp.doRenderLast(ctx, req, res);
        }
        out.write("</td></tr>");
    }

    public void doRenderLast(ServletContext ctx, HttpServletRequest req, HttpServletResponse res) throws PortletLayoutException, IOException {
        super.doRenderLast(ctx, req, res);
    }


}







