/*
 * @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
 * @version $Id: ColumnLayout.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.layout.view.brush;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.view.BaseRender;
import org.gridlab.gridsphere.layout.view.Render;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;


public class ColumnLayout extends BaseRender implements Render {

    public ColumnLayout() {
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        StringBuffer temp = new StringBuffer("\n<div class=\"gridsphere-layout-column\"");
        if (!comp.getWidth().equals("")) {
            temp.append(" style=\"width: ").append(comp.getWidth()).append("\"");
        }
        temp.append("> <!-- ========================== start column -->\n");
        return temp;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return new StringBuffer("\n</div> <!--  ========================== end column -->\n");
    }

}



