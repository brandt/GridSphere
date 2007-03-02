package org.gridsphere.layout.view.brush;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.PortletContainer;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.Render;
import org.gridsphere.portletcontainer.GridSphereEvent;

/**
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
public class Container extends BaseRender implements Render {


    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent comp) {
        StringBuffer buffer = new StringBuffer();
        PortletContainer pc = (PortletContainer) comp;
        String style = pc.getStyle();
        if (style == null) return buffer;
        if (style.equals(PortletContainer.STYLE_HEADER)) {
            buffer.append("\n   <b class=\"rtop\"><b class=\"r1\"></b><b class=\"r2\"></b><b class=\"r3\"></b><b class=\"r4\"></b></b>\n");
            buffer.append("\n   <div id=\"gridsphere-layout-head\">\n");
        }
        if (style.equals(PortletContainer.STYLE_FOOTER)) {
            buffer.append("\n   <div id=\"gridsphere-layout-footer\">\n");
        }
        return buffer;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        StringBuffer buffer = new StringBuffer();
        PortletContainer pc = (PortletContainer) comp;
        String style = pc.getStyle();
        if (style == null) return buffer;
        buffer.append("\n   </div> <!-- END gridsphere-layout-{header|footer}  -->\n");
        if (style.equals(PortletContainer.STYLE_FOOTER)) {
            buffer.append("\n   <b class=\"rbottom\"><b class=\"r4\"></b><b class=\"r3\"></b><b class=\"r2\"></b><b class=\"r1\"></b></b>\n");
        }
        return buffer;
    }

}
