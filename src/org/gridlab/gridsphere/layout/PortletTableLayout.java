/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Collections;

/**
 * The <code>PortletTableLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of columns.
 * Portlets are arranged in column-wise order starting from the left most column.
 */
public class PortletTableLayout extends PortletFrameLayout implements Cloneable {

    /** css Style of the table */
    protected String style = null;

    protected boolean canModify = false;
    /**
     * Constructs an instance of PortletTableLayout
     */
    public PortletTableLayout() {
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }

    public boolean getCanModify() {
        return canModify;
    }
    /**
     * Returns the CSS style name for the grid-layout.
     *
     * @return css style name
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the CSS style name for the grid-layout.
     * This needs to be set if you want to have transparent portlets, if there is
     * no background there can't be a real transparent portlet.
     * Most likely one sets just the background in that one.
     *
     * @param style css style of the that layout
     */
    public void setStyle(String style) {
        this.style = style;
    }

    private PortletComponent getMaximizedComponent(List components) {
        PortletComponent p = null;
        List scomponents = Collections.synchronizedList(components);
        synchronized(scomponents) {
            for (int i=0;i<scomponents.size();i++) {
                p = (PortletComponent)scomponents.get(i);
                if (p instanceof PortletLayout) {
                    PortletComponent layout = this.getMaximizedComponent(((PortletLayout)p).getPortletComponents());
                    if (layout!=null) {
                        p = layout;
                    }
                }
                if (p.getWidth().equals("100%")) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Renders the portlet grid layout component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException            if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletResponse res = event.getPortletResponse();
        PrintWriter out = res.getWriter();

        PortletComponent p = null;

        // check if one window is maximized
        /*
        StringBuffer frame = new StringBuffer();
        StringWriter storedWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(storedWriter);
        PortletResponse wrappedResponse = new StoredPortletResponseImpl(res, writer);
        */
        List scomponents = Collections.synchronizedList(components);
        synchronized(scomponents) {
            for (int i=0;i<scomponents.size();i++) {
                p = (PortletComponent)scomponents.get(i);
                if (p instanceof PortletLayout) {
                    PortletComponent maxi = getMaximizedComponent(scomponents);
                    if (maxi!=null) {
                        out.println("<table border=\"0\" width=\"100%\" cellspacing=\"2\" cellpadding=\"0\"><tbody><tr><td>");
                        maxi.doRender(event);
                        out.println("</td></tr></tbody></table>");
                        return;
                    }
                }
            }

            // starting of the gridtable
            out.println("<table ");
            if (this.style!=null) {
                out.print("class=\""+this.style+"\" ");
            }
            out.println("border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody>");

            for (int i=0;i<scomponents.size();i++) {
                p = (PortletComponent) scomponents.get(i);
                out.println("<tr><td valign=\"top\" width=\"100%\">");
                if (p.getVisible()) {
                    p.doRender(event);
                    //out.println("grid comp: "+i);
                }
                out.println("</td> </tr>");
            }

            /** setup bottom add portlet listbox */
            if (canModify) {
                out.println("<tr>");
                for (int i=0;i<scomponents.size();i++) {

                    out.println("<td valign=\"top\" width=\"100%\">");

                    out.println("<table><tr><td><b>hello</b></td></tr></table>");

                    out.println("</td>");
                }
                out.println("</tr>");
            }
            out.println("</tbody></table>");
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletTableLayout g = (PortletTableLayout)super.clone();
        g.style = this.style;
        return g;
    }

}