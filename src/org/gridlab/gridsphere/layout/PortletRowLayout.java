/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class PortletRowLayout extends PortletFrameLayout implements Cloneable, Serializable {

    //protected StringBuffer row = null;

	public void doRender(GridSphereEvent event) throws PortletLayoutException,IOException {
    	String markupName=event.getPortletRequest().getClient().getMarkupName();
    	if (markupName.equals("html")){
    		doRenderHTML(event);
    	} else {
    		doRenderWML(event);
    	}
	}

    public void doRenderWML(GridSphereEvent event) throws PortletLayoutException,IOException {
        PortletResponse res = event.getPortletResponse();
        PrintWriter out = res.getWriter();
        //System.err.println("\t\tin render RowLayout");
        PortletComponent p = null;

        out.println("<p/>");
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
            for (int i = 0; i < scomponents.size(); i++) {
                p = (PortletComponent) scomponents.get(i);
                out.println("<p/>");
                if (p.getVisible()) {
                    p.doRender(event);
                }
            }
            out.println("<p/>");
        }
    }

    public void doRenderHTML(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletResponse res = event.getPortletResponse();

        //System.err.println("\t\tin render RowLayout");
        PortletComponent p = null;

        StringBuffer row = new StringBuffer();
        // starting of the gridtable
        row.append(" <!-- START ROW --> ");
        row.append("<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">");
        row.append("<tbody><tr>");
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
            for (int i = 0; i < scomponents.size(); i++) {
                p = (PortletComponent) scomponents.get(i);
                row.append("<td valign=\"top\" width=\"" + p.getWidth() + "\">");
                if (p.getVisible()) {
                    p.doRender(event);
                    row.append(p.getBufferedOutput(event.getPortletRequest()));
                }
                row.append("</td>");
            }
            row.append("</tr></tbody></table>");
            row.append("<!-- END ROW -->");
        }
        event.getPortletRequest().setAttribute(SportletProperties.RENDER_OUTPUT + componentIDStr, row);
    }


    public Object clone() throws CloneNotSupportedException {
        PortletRowLayout g = (PortletRowLayout) super.clone();
        return g;
    }
}
 


