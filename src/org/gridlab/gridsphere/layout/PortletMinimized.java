/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.PrintWriter;

public class PortletMinimized extends BasePortletComponent {

    private PortletImage image;
    private String title;
    private String portletClass;

    public PortletMinimized() {}

    public PortletMinimized(String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPortletImage(PortletImage image) {
        this.image = image;
    }

    public PortletImage getPortletImage() {
        return image;
    }

    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public String getPortletClass() {
        return portletClass;
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        SportletResponse res = event.getSportletResponse();
        PrintWriter out = res.getWriter();
        out.println("portlet minimized");
    }

}
