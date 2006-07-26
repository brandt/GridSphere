/*
 * @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
 * @version $Id: RowLayout.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.layout.view.brush;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.view.BaseRender;
import org.gridlab.gridsphere.layout.view.Render;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

public class RowLayout extends BaseRender implements Render {

    public StringBuffer doStart(GridSphereEvent event, PortletComponent p) {
        return new StringBuffer("\n<div class=\"gridsphere-layout-row\"> <!-- +++++++++++++++++++++++++++++++++ START ROW -->\n");
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return new StringBuffer("\n</div> <!-- +++++++++++++++++++++++++++++++++ END ROW -->\n");
    }
}
 


