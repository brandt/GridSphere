/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;
import java.util.Collections;

/**
 *
 */
public class PortletRowLayout extends PortletFrameLayout implements Cloneable, Serializable {

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletResponse res = event.getPortletResponse();
        PrintWriter out = res.getWriter();

        PortletComponent p = null;

        // starting of the gridtable
        out.println(" <!-- START ROW --> ");
        out.println("<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("<tbody><tr>") ;
        List scomponents = Collections.synchronizedList(components);
        synchronized(scomponents) {
        for (int i=0;i<scomponents.size();i++) {
            p = (PortletComponent) scomponents.get(i);
            //System.out.println("\n\n\n\n\n WIDTH "+p.getWidth()+"\n\n\n\n");
            out.println("<td valign=\"top\" width=\""+p.getWidth()+"\">");
            if (p.getVisible()) {
                p.doRender(event);
            }
            out.println("</td>");
        }
        out.println("</tr></tbody></table>");
        out.println("<!-- END ROW -->");
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletRowLayout g = (PortletRowLayout)super.clone();
        return g;
    }
}
 


