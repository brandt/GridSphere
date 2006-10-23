package org.gridsphere.layout.view.brush;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.Render;
import org.gridsphere.portletcontainer.GridSphereEvent;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public class Bar extends BaseRender implements Render {

    private static StringBuffer START_BAR = new StringBuffer("<div id=\"gridsphere-layout-navigation\"><ul id=\"gridsphere-nav\">" +
            "<li id=\"gridsphere-nav-border-right\">&nbsp;</li></ul>" +
            "<ul id=\"gridsphere-sub-nav\">" +
            "<li id=\"gridsphere-sub-nav-border-right\">&nbsp;</li></ul>");

    private static StringBuffer END_BAR = new StringBuffer("\n</div> <!-- END gridsphere-layout-body -->\n");

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        return START_BAR;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return END_BAR;
    }
}


