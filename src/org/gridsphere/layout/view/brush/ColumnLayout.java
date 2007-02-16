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

    public ColumnLayout() {
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        StringBuffer sb = new StringBuffer();
        StringBuffer temp = new StringBuffer("\n<div class=\"gridsphere-layout-column\"");
        if (!comp.getWidth().equals("")) {
            sb.append(" width: ").append(comp.getWidth()).append(";");
        }
        if (!comp.getStyle().equals("")) {
            sb.append(comp.getStyle());
        }

        if (sb.length() > 0) temp.append(" style=\"").append(sb).append("\"");
        temp.append("> <!-- ========================== start column -->\n");
        return temp;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return new StringBuffer("\n</div> <!--  ========================== end column -->\n");
    }

}



