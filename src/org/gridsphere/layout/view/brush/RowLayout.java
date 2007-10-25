/*
 * @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridsphere.layout.view.brush;

import org.gridsphere.layout.PortletComponent;
import org.gridsphere.layout.view.BaseRender;
import org.gridsphere.layout.view.Render;
import org.gridsphere.portletcontainer.GridSphereEvent;

public class RowLayout extends BaseRender implements Render {

    public StringBuffer doStart(GridSphereEvent event, PortletComponent p) {
        return new StringBuffer("\n<div class=\"gridsphere-layout-row\"> <!-- +++++++++++++++++++++++++++++++++ START ROW -->\n");
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return new StringBuffer("\n</div> <!-- +++++++++++++++++++++++++++++++++ END ROW -->\n");
    }
}
 


