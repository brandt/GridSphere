/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;

public class PortletDock extends BaseLayoutManager {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletDock.class);

    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private int orientation;
    private String title = "";

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

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        SportletRequest req = event.getSportletRequest();
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();
        out.write("<tr><td>" + title);
        ListIterator compIt = components.listIterator();
        while (compIt.hasNext()) {
            PortletRender render = (PortletRender)compIt.next();
            render.doRender(event);
            insets.doRender(event);
        }
        out.write("</td></tr>");
    }

}







