/*
 * @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
 * @version $Id: ColumnLayout.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.view.brush;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.Render;
import org.gridsphere.portletcontainer.GridSphereEvent;


public class ColumnLayout extends BaseRender implements Render {

    private static final StringBuffer COL_LAYOUT = new StringBuffer("\n<div class=\"gridsphere-layout-column\"");

    public ColumnLayout() {
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        StringBuffer cssStyle = new StringBuffer();
        if (!comp.getWidth().equals("")) {
            cssStyle.append(" width: ").append(comp.getWidth()).append(";");
        }
        if (!comp.getStyle().equals("")) {
            cssStyle.append(comp.getStyle());
        }
        if (cssStyle.length() > 0) cssStyle.append(COL_LAYOUT).append(" style=\"").append(cssStyle).append("\"");
        cssStyle.append("> <!-- ========================== start column -->\n");
        return cssStyle;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return new StringBuffer("\n</div> <!--  ========================== end column -->\n");
    }

}



