/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;
import java.util.Collections;

/**
 * The <code>PortletColumnLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a column.
 */
public class PortletColumnLayout extends PortletFrameLayout implements Cloneable, Serializable {

    public PortletColumnLayout() {
    }

    /**
     * Renders the component
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletResponse res = event.getPortletResponse();
        PortletRequest req = event.getPortletRequest();
        PrintWriter out = res.getWriter();

        PortletComponent p = null;

        // starting of the gridtable

        if (!components.isEmpty()) {
            out.println("<table width=\"100%\" cellspacing=\"2\" cellpadding=\"0\"> <!-- START COLUMN -->");

            out.println("<tbody>");
            List scomponents = Collections.synchronizedList(components);
            synchronized(scomponents) {
            for (int i=0;i<scomponents.size();i++) {
                out.print("<tr><td valign=\"top\">");

                p = (PortletComponent) scomponents.get(i);

                if (p.getVisible()) {
                    p.doRender(event);
                    //out.println("comp: "+i);
                }

                out.println("</td></tr>");
            }
            }
            out.println("</tbody>");
            out.println("</table> <!-- END COLUMN -->");
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletColumnLayout g = (PortletColumnLayout)super.clone();
        return g;
    }

}



